package SearchMethod;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Random;

import Auxiliary.Distancia;
import Data.Instance;
import DiversityControl.AjusteDist;
import DiversityControl.AjusteOmega;
import DiversityControl.CriterioAceitacao;
import DiversityControl.DistIdeal;
import Improvement.BuscaLocal;
import Improvement.BuscaLocalIntra;
import Improvement.Factibilizador;
import Perturbation.HeuristicaAdicao;
import Perturbation.Perturbacao;
import Solution.Solucao;

public class AILS 
{
	//----------Problema------------
	Solucao solucao,solucaoReferencia,melhorSolucao;
	
	Instance instancia;
	Distancia distEntreSolucoes;
	double melhorF=Double.MAX_VALUE;
	double limiteMaximoExecucao;
	double otimo;
	
	//----------caculoLimiar------------
	int numIterUpdate;

	//----------Metricas------------
	int iterador,iteradorMF;
	long inicio,ini;
	double tempoMF,tempoTotal,tempo;
	
	Random rand=new Random();
	
	HashMap<String,AjusteOmega>configuradoresOmega=new HashMap<String,AjusteOmega>();

	double distanciaBL;
	
	Perturbacao[] perturbadores;
	Perturbacao perturbacaoEscolhida;
	
	Factibilizador factibilizador;
	ConstrutorSolucao construtorSolucao;
	
	BuscaLocal buscaLocal;

	HeuristicaAdicao heuristicaAdicao;
	BuscaLocalIntra buscaLocalIntra;
	CriterioAceitacao criterioAceitacao;
//	----------Mare------------
	AjusteDist ajusteDist;
//	---------Print----------
	boolean print=true;
	DistIdeal distIdeal;
	
	double epsilon;
	DecimalFormat deci=new DecimalFormat("0.0000");
	TipoCriterioParada tipoCriterioParada;
	
	public AILS(Instance instancia,InputParameters leitor)
	{ 
		this.instancia=instancia;
		Config config=leitor.getConfig();
		this.otimo=leitor.getBest();
		this.limiteMaximoExecucao=leitor.getTimeLimit();
		
		this.epsilon=config.getEpsilon();
		this.tipoCriterioParada=config.getTipoCriterioParada();
		this.distIdeal=new DistIdeal();
		this.solucao =new Solucao(instancia,config);
		this.solucaoReferencia =new Solucao(instancia,config);
		this.melhorSolucao =new Solucao(instancia,config);
		this.numIterUpdate=config.getGamma();
		
		this.distEntreSolucoes=new Distancia();
		
		this.perturbadores=new Perturbacao[config.getPerturbacao().length];
		
		this.ajusteDist=new AjusteDist( distIdeal, config, limiteMaximoExecucao);
		
		this.buscaLocalIntra=new BuscaLocalIntra(instancia,config);
		
		this.buscaLocal=new BuscaLocal(instancia,config,buscaLocalIntra);
		
		this.factibilizador=new Factibilizador(instancia,config,buscaLocalIntra);
		
		this.construtorSolucao=new ConstrutorSolucao(instancia,config);
		
		AjusteOmega novo;
		for (int i = 0; i < config.getPerturbacao().length; i++) 
		{
			novo=new AjusteOmega(config.getPerturbacao()[i], config,instancia.getSize(),distIdeal);
			configuradoresOmega.put(config.getPerturbacao()[i]+"", novo);
		}
		
		this.criterioAceitacao=new CriterioAceitacao(instancia,config,limiteMaximoExecucao);

		try 
		{
			for (int i = 0; i < perturbadores.length; i++) 
			{
				this.perturbadores[i]=(Perturbacao) Class.forName("Perturbation."+config.getPerturbacao()[i]).
				getConstructor(Instance.class,Config.class,HashMap.class,BuscaLocalIntra.class).
				newInstance(instancia,config,configuradoresOmega,buscaLocalIntra);
			}
			
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException
				| ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	public void search()
	{
		iterador=0;
		inicio=System.currentTimeMillis();
		solucaoReferencia.NumRotas=instancia.getNumRotasMin();
		construtorSolucao.construir(solucaoReferencia);

		factibilizador.factibilizar(solucaoReferencia);
		buscaLocal.buscaLocal(solucaoReferencia,true);
		melhorSolucao.clone(solucaoReferencia);
		while(!criterioParada())
		{
			iterador++;

			solucao.clone(solucaoReferencia);
			
			perturbacaoEscolhida=perturbadores[rand.nextInt(perturbadores.length)];
			perturbacaoEscolhida.perturbar(solucao);
			factibilizador.factibilizar(solucao);
			buscaLocal.buscaLocal(solucao,true);
			distanciaBL=distEntreSolucoes.distanciaEdge(solucao,solucaoReferencia);
			
			analisaSolucao();
			ajusteDist.ajusteDist();
			
			perturbacaoEscolhida.getConfiguradorOmegaEscolhido().setDistancia(distanciaBL);//update
			
			if(criterioAceitacao.aceitaSolucao(solucao))
				solucaoReferencia.clone(solucao);
		}
		
		tempoTotal=(double)(System.currentTimeMillis()-inicio)/1000;
	}
	
	public void analisaSolucao()
	{
		if((solucao.f-melhorF)<-epsilon)
		{		
			melhorF=solucao.f;
			
			melhorSolucao.clone(solucao);
			iteradorMF=iterador;
			tempoMF=(double)(System.currentTimeMillis()-inicio)/1000;
				
			if(print)
			{
				System.out.println("solution quality: "+melhorF
				+" gap: "+deci.format(getGap())+"%"
				+" K: "+solucao.NumRotas
				+" iteration: "+iterador
				+" eta: "+deci.format(criterioAceitacao.getEta())
				+" omega: "+deci.format(perturbacaoEscolhida.omega)
				+" time: "+tempoMF
				);
			}
		}
	}
	
	private boolean criterioParada()
	{
		switch(tipoCriterioParada)
		{
			case Iteration: 	if(melhorF<=otimo||limiteMaximoExecucao<=iterador)
									return true;
								break;
							
			case Time: 	if(melhorF<=otimo||limiteMaximoExecucao<(System.currentTimeMillis()-inicio)/1000)
							return true;
						break;
		}
		return false;
	}
	
	public static void main(String[] args) 
	{
		InputParameters reader=new InputParameters();
		reader.readingInput(args);
		
		Instance instance=new Instance(reader);
		
		AILS ails=new AILS(instance,reader);
		
		ails.search();
	}
	
	public Solucao getMelhorSolucao() {
		return melhorSolucao;
	}

	public double getMelhorF() {
		return melhorF;
	}

	public double getGap()
	{
		return 100*((melhorF-otimo)/otimo);
	}
	
	public boolean isPrint() {
		return print;
	}

	public void setPrint(boolean print) {
		this.print = print;
	}

	public Solucao getSolucao() {
		return solucao;
	}

	public int getIterador() {
		return iterador;
	}

	public String printOmegas()
	{
		String str="";
		for (int i = 0; i < perturbadores.length; i++) 
		{
			str+="\n"+configuradoresOmega.get(this.perturbadores[i].tipoPerturbacao+""+solucaoReferencia.NumRotas);
		}
		return str;
	}
	
	public Perturbacao[] getPerturbadores() {
		return perturbadores;
	}
	
	public double getTempoTotal() {
		return tempoTotal;
	}
	
	public double getTemoPorIteracao() 
	{
		return tempoTotal/iterador;
	}

	public double getTempoMF() {
		return tempoMF;
	}

	public int getIteradorMF() {
		return iteradorMF;
	}
	
	public double getConvergenciaIteracao()
	{
		return (double)iteradorMF/iterador;
	}
	
	public double convergenciaTempo()
	{
		return (double)tempoMF/tempoTotal;
	}
	
}
