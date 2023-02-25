package Perturbacao;

import java.util.HashMap;
import java.util.Random;

import ControleDiversidade.AjusteOmega;
import Dados.Instancia;
import Improvement.BuscaLocalIntra;
import MetodoBusca.Config;
import Solucao.No;
import Solucao.Rota;
import Solucao.Solucao;

public abstract class Perturbacao 
{
	protected Rota rotas[];
	protected int NumRotas;
	protected No solucao[];
	protected double f=0;
	protected Random rand=new Random();
	public double omega;
	AjusteOmega configuradorOmegaEscolhido;
	Config config;
	protected No candidatos[];
	protected int contCandidatos;

	HeuristicaAdicao[]heuristicasAdicao;
	public HeuristicaAdicao heuristicaAdicaoEscolhida;
	
	No no;
	
	public TipoPerturbacao tipoPerturbacao;
	int size;
	HashMap<String, AjusteOmega> configuradoresOmega;
	
	double bestCusto,bestDist;
	int numIterUpdate;
	int posHeuEscolhida;
	
	double custo,dist;
	double custoAnt;
	int indexA,indexB;
	No bestNo,bestNoDist,aux;
	Instancia instancia;
	int limiteAdj;
	
	BuscaLocalIntra buscaLocalIntra;
	
	public Perturbacao(Instancia instancia,Config config,
	HashMap<String, AjusteOmega> configuradoresOmega, BuscaLocalIntra buscaLocalIntra) 
	{
		this.config=config;
		this.instancia=instancia;
		this.heuristicasAdicao=config.getHeuristicasAdicao();
		this.size=instancia.getSize()-1;
		this.candidatos=new No[size];
		this.configuradoresOmega=configuradoresOmega;
		this.numIterUpdate=config.getNumIterUpdate();
		this.limiteAdj=config.getLimiteAdj();
		this.buscaLocalIntra=buscaLocalIntra;
	}
	
	public void estabelecerOrdem()
	{
		No aux;
		for (int i = 0; i < contCandidatos; i++)
		{
			indexA=rand.nextInt(contCandidatos);
			indexB=rand.nextInt(contCandidatos);
			
			aux=candidatos[indexA];
			candidatos[indexA]=candidatos[indexB];
			candidatos[indexB]=aux;
		}
	}
	
	public void perturbar(Solucao s){}
	
	protected void setSolucao(Solucao s)
	{
		this.NumRotas=s.getNumRotas();
		this.rotas=s.rotas;
		this.solucao=s.getSolucao();
		this.f=s.f;
		for (int i = 0; i < NumRotas; i++) 
		{
			rotas[i].alterada=false;
			rotas[i].inicio.alterado=false;
		}
		
		for (int i = 0; i < size; i++) 
			solucao[i].alterado=false;
	
		posHeuEscolhida=rand.nextInt(heuristicasAdicao.length);
		heuristicaAdicaoEscolhida=heuristicasAdicao[posHeuEscolhida];
		
		configuradorOmegaEscolhido=configuradoresOmega.get(tipoPerturbacao+"");
		omega=configuradorOmegaEscolhido.getOmegaReal();
		omega=Math.min(omega, size);
		
		contCandidatos=0;
	}
	
	protected void passaSolucao(Solucao s)
	{
		s.f=f;
		s.NumRotas=this.NumRotas;
	}
	
	protected No getNo(No no)
	{
		switch(heuristicaAdicaoEscolhida)
		{
			case Distance: return getBestKNNNo2(no,1);
			case Cost: return getBestKNNNo2(no,limiteAdj);
		}
		return null;
	}
	
	protected No getBestKNNNo2(No no,int limite)
	{
		bestCusto=Double.MAX_VALUE;
		boolean entrou=false;
		bestNo=null;
		
		int cont=0;
		entrou=false;
		for (int i = 0; i < no.knn.length&&cont<limite; i++) 
		{
			if(no.knn[i]==0)
			{
				for (int j = 0; j < NumRotas; j++) 
				{
					aux=rotas[j].inicio;
					entrou=true;
					custo=instancia.dist(aux.nome,no.nome)+instancia.dist(no.nome,aux.prox.nome)-instancia.dist(aux.nome,aux.prox.nome);
					if(custo<bestCusto)
					{
						bestCusto=custo;
						bestNo=aux;
					}
				}
				if(entrou)
					cont++;
			}
			else
			{
				aux=solucao[no.knn[i]-1];
				if(aux.jaInserido)
				{
					cont++;
					custo=instancia.dist(aux.nome,no.nome)+instancia.dist(no.nome,aux.prox.nome)-instancia.dist(aux.nome,aux.prox.nome);
					if(custo<bestCusto)
					{
						bestCusto=custo;
						bestNo=aux;
					}
				}
			}
		}
		
		if(bestNo==null)
		{
			for (int i = 0; i < solucao.length; i++) 
			{
				aux=solucao[i];
				if(aux.jaInserido)
				{
					custo=instancia.dist(aux.nome,no.nome)+instancia.dist(no.nome,aux.prox.nome)-instancia.dist(aux.nome,aux.prox.nome);
					if(custo<bestCusto)
					{
						bestCusto=custo;
						bestNo=aux;
					}
				}
			}
		}
		
		if(bestNo==null)
		{
			for (int i = 0; i < solucao.length; i++) 
			{
				aux=solucao[i];
				if(aux.jaInserido)
				{
					custo=instancia.dist(aux.nome,no.nome)+instancia.dist(no.nome,aux.prox.nome)-instancia.dist(aux.nome,aux.prox.nome);
					if(custo<bestCusto)
					{
						bestCusto=custo;
						bestNo=aux;
					}
				}
			}
		}
		
		custo=instancia.dist(bestNo.nome,no.nome)+instancia.dist(no.nome,bestNo.prox.nome)-instancia.dist(bestNo.nome,bestNo.prox.nome);
		custoAnt=instancia.dist(bestNo.ant.nome,no.nome)+instancia.dist(no.nome,bestNo.nome)-instancia.dist(bestNo.ant.nome,bestNo.nome);
		if(custo<custoAnt)
		{
			return bestNo;
		}
		else
		{
			return bestNo.ant;
		}
	}
	
	public void adicionarCandidatos() 
	{
		for (int i = 0; i < contCandidatos; i++) 
		{
			no=candidatos[i];
			bestNo=getNo(no);
			
			f+=bestNo.rota.addDepois(no, bestNo);
		}
	}
	
	public int getPosHeuEscolhida() {
		return posHeuEscolhida;
	}

	public AjusteOmega getConfiguradorOmegaEscolhido() {
		return configuradorOmegaEscolhido;
	}
	
	public TipoPerturbacao getTipoPerturbacao() {
		return tipoPerturbacao;
	}
	
}