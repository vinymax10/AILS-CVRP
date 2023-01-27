package MetodoBusca;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Random;

import Auxiliar.Distancia;
import ControleDiversidade.AjusteDist;
import ControleDiversidade.AjusteOmega;
import ControleDiversidade.CriterioAceitacao;
import ControleDiversidade.DistIdeal;
import Dados.Instancia;
import Dados.Instancias;
import Improvement.BuscaLocal;
import Improvement.BuscaLocalIntra;
import Improvement.Factibilizador;
import Perturbacao.HeuristicaAdicao;
import Perturbacao.Perturbacao;
import Solucao.Solucao;

public class AILS 
{
	//----------Problema------------
	Solucao solucao,solucaoReferencia,melhorSolucao;
	
	Instancia instancia;
	Distancia distEntreSolucoes;
	double melhorF;
	double limiteMaximoExecucao;
	double otimo;
	
	//----------IGAS------------
	Config config;
	
	//----------caculoLimiar------------
	int numIterUpdate;
	double fBL,fHeu,fConstr;
	//----------Metricas------------
	int iterador,iteradorMF;
	long inicio,ini;
	double tempoMF,tempoTotal,tempo;
	
	Random rand=new Random();
	
	HashMap<String,AjusteOmega>configuradoresOmega=new HashMap<String,AjusteOmega>();

	double distanciaBLEdge;
	
	Perturbacao[] perturbadores;
	Perturbacao perturbacaoEscolhida;
	
	Factibilizador factibilizador;
	ConstrutorSolucao construtorSolucao;
	
	BuscaLocal buscaLocal;

	boolean aceitoCriterio,isMelhorSolucao;
	HeuristicaAdicao heuristicaAdicao;
	BuscaLocalIntra buscaLocalIntra;
	CriterioAceitacao criterioAceitacao;
//	----------Mare------------
	AjusteDist ajusteDist;
//	---------Print----------
	boolean dimacs=false;
	boolean print=true;
	DistIdeal distIdeal;
	
	double epsilon;
	DecimalFormat deci=new DecimalFormat("0.0000");
	TipoCriterioParada tipoCriterioParada;
	
	public AILS(Instancia instancia,Config config,double d,double limiteMaximoExecucao)
	{
		this.instancia=instancia;
		this.epsilon=config.getEpsilon();
		this.tipoCriterioParada=config.getTipoCriterioParada();
		this.distIdeal=new DistIdeal();

		this.solucao =new Solucao(instancia,config);
		this.solucaoReferencia =new Solucao(instancia,config);
		this.melhorSolucao =new Solucao(instancia,config);
		
		this.numIterUpdate=config.getNumIterUpdate();
		this.config=config;
		this.otimo=d;
		this.limiteMaximoExecucao=limiteMaximoExecucao;
		this.melhorF=Integer.MAX_VALUE;
		
		this.distEntreSolucoes=new Distancia();
		
		this.perturbadores=new Perturbacao[config.getPerturbacao().length];
		
		this.ajusteDist=new AjusteDist( distIdeal, config, limiteMaximoExecucao);
		
		this.buscaLocalIntra=new BuscaLocalIntra(instancia,config);
		
		this.buscaLocal=new BuscaLocal(instancia,config,buscaLocalIntra);
		
		this.factibilizador=new Factibilizador(instancia,config,buscaLocalIntra);
		
		this.construtorSolucao=new ConstrutorSolucao(instancia,config,buscaLocalIntra);
		
		AjusteOmega novo;
		for (int i = 0; i < config.getPerturbacao().length; i++) 
		{
			novo=new AjusteOmega(config.getPerturbacao()[i], config,instancia.getSize(),distIdeal);
			configuradoresOmega.put(config.getPerturbacao()[i]+"", novo);
		}
		
		this.criterioAceitacao=new CriterioAceitacao(instancia,config,distEntreSolucoes,limiteMaximoExecucao);

		try 
		{
			for (int i = 0; i < perturbadores.length; i++) 
			{
				this.perturbadores[i]=(Perturbacao) Class.forName("Perturbacao."+config.getPerturbacao()[i]).
				getConstructor(Instancia.class,Config.class,HashMap.class,BuscaLocalIntra.class).
				newInstance(instancia,config,configuradoresOmega,buscaLocalIntra);
			}
			
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException
				| ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	public void procurar()
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

			isMelhorSolucao=false;
			
//			Escolhe referencia
			
			solucao.clone(solucaoReferencia);
			
//			Perturbacao
			perturbacaoEscolhida=perturbadores[rand.nextInt(perturbadores.length)];
			
			perturbacaoEscolhida.perturbar(solucao);
			fHeu=solucao.f;
			
//			FAC
			factibilizador.factibilizar(solucao);
			fConstr=solucao.f;
			
//			BL
			buscaLocal.buscaLocal(solucao,true);
			fBL=solucao.f;
			distanciaBLEdge=distEntreSolucoes.distanciaEdge(solucao,solucaoReferencia);
			
			
//			Analise solucao
			analisaSolucao();
			ajusteDist.ajusteDist();
			
//			update
			perturbacaoEscolhida.getConfiguradorOmegaEscolhido().setDistancia(
			distanciaBLEdge);
			
//			criterio aceitacao
			if(criterioAceitacao.aceitaSolucao(solucao,distanciaBLEdge))
			{
				aceitoCriterio=true;
				solucaoReferencia.clone(solucao);
			}
			else
				aceitoCriterio=false;
		}
		
		tempoTotal=(double)(System.currentTimeMillis()-inicio)/1000;
	}
	
	public void analisaSolucao()
	{
		if((solucao.f-melhorF)<-epsilon)
		{		
			isMelhorSolucao=true;
			melhorF=solucao.f;
			
			melhorSolucao.clone(solucao);
			iteradorMF=iterador;
			tempoMF=(double)(System.currentTimeMillis()-inicio)/1000;
				
			if(print)
			{
				System.out.println("melhorF: "+melhorF
				+" gap: "+getGap()
				+" K: "+solucao.NumRotas
				+" iterador: "+iterador
				+" eta: "+criterioAceitacao.getEta()
				+" omega: "+perturbacaoEscolhida.omega
				+" tempoMF: "+tempoMF
				);
			}
		}
	}
	
	private boolean criterioParada()
	{
		switch(tipoCriterioParada)
		{
			case Iteracao: 	if(melhorF<=otimo||limiteMaximoExecucao<=iterador)
								return true;
							break;
							
			case IteracaoSemMelhora: 	if(melhorF<=otimo||limiteMaximoExecucao<=(iterador-iteradorMF))
											return true;
										break;
										
			case TempoSemMelhora: 	if(melhorF<=otimo||limiteMaximoExecucao<(((System.currentTimeMillis()-inicio)/1000)-tempoMF))
										return true;
									break;
									
			case TempoTotal: 	if(melhorF<=otimo||limiteMaximoExecucao<(System.currentTimeMillis()-inicio)/1000)
									return true;
								break;
								
		}
		return false;
	}
	
	public static void main(String[] args) 
	{
		Instancias instancias=new Instancias();
		
		int pos=24;	
		Config config =new Config();
		Instancia instancia=new Instancia("Instancias//"+instancias.instancias[pos].nome+".vrp",
		config,instancias.instancias[pos].rounded);
		
		System.out.print(instancias.instancias[pos].nome
		+" otimo: "+instancias.instancias[pos].bestSolution.getOtimo());
		System.out.print(" "+instancia.getSize()/instancia.getNumRotasMin()+"\n");

		AILS igas=new AILS(instancia,config,instancias.instancias[pos].bestSolution.getOtimo(),
		200);
		
		igas.procurar();
		System.out.println("tempo: "+igas.tempoTotal+" iterador: "+igas.iterador
		+" iteradorMF: "+igas.iteradorMF);
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
//		return PI;
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
	
	public void setDimacs(boolean dimacs) {
		this.dimacs = dimacs;
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
