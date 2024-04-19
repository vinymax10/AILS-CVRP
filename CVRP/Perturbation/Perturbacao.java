package Perturbation;

import java.util.HashMap;
import java.util.Random;

import Data.Instance;
import DiversityControl.AjusteOmega;
import Improvement.BuscaLocalIntra;
import SearchMethod.Config;
import Solution.Node;
import Solution.Rota;
import Solution.Solution;

public abstract class Perturbacao 
{
	protected Rota rotas[];
	protected int NumRotas;
	protected Node solucao[];
	protected double f=0;
	protected Random rand=new Random();
	public double omega;
	AjusteOmega configuradorOmegaEscolhido;
	Config config;
	protected Node candidatos[];
	protected int contCandidatos;

	HeuristicaAdicao[]heuristicasAdicao;
	public HeuristicaAdicao heuristicaAdicaoEscolhida;
	
	Node no;
	
	public TipoPerturbacao tipoPerturbacao;
	int size;
	HashMap<String, AjusteOmega> configuradoresOmega;
	
	double bestCusto,bestDist;
	int numIterUpdate;
	int posHeuEscolhida;
	
	double custo,dist;
	double custoAnt;
	int indexA,indexB;
	Node bestNo,bestNoDist,aux;
	Instance instancia;
	int limiteAdj;
	
	BuscaLocalIntra buscaLocalIntra;
	
	public Perturbacao(Instance instancia,Config config,
	HashMap<String, AjusteOmega> configuradoresOmega, BuscaLocalIntra buscaLocalIntra) 
	{
		this.config=config;
		this.instancia=instancia;
		this.heuristicasAdicao=config.getHeuristicasAdicao();
		this.size=instancia.getSize()-1;
		this.candidatos=new Node[size];
		this.configuradoresOmega=configuradoresOmega;
		this.numIterUpdate=config.getGamma();
		this.limiteAdj=config.getVarphi();
		this.buscaLocalIntra=buscaLocalIntra;
	}
	
	public void estabelecerOrdem()
	{
		Node aux;
		for (int i = 0; i < contCandidatos; i++)
		{
			indexA=rand.nextInt(contCandidatos);
			indexB=rand.nextInt(contCandidatos);
			
			aux=candidatos[indexA];
			candidatos[indexA]=candidatos[indexB];
			candidatos[indexB]=aux;
		}
	}
	
	public void perturbar(Solution s){}
	
	protected void setSolucao(Solution s)
	{
		this.NumRotas=s.getNumRotas();
		this.rotas=s.rotas;
		this.solucao=s.getSolution();
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
	
	protected void passaSolucao(Solution s)
	{
		s.f=f;
		s.NumRotas=this.NumRotas;
	}
	
	protected Node getNo(Node no)
	{
		switch(heuristicaAdicaoEscolhida)
		{
			case Distance: return getBestKNNNo2(no,1);
			case Cost: return getBestKNNNo2(no,limiteAdj);
		}
		return null;
	}
	
	protected Node getBestKNNNo2(Node no,int limite)
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
					custo=instancia.dist(aux.name,no.name)+instancia.dist(no.name,aux.next.name)-instancia.dist(aux.name,aux.next.name);
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
					custo=instancia.dist(aux.name,no.name)+instancia.dist(no.name,aux.next.name)-instancia.dist(aux.name,aux.next.name);
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
					custo=instancia.dist(aux.name,no.name)+instancia.dist(no.name,aux.next.name)-instancia.dist(aux.name,aux.next.name);
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
					custo=instancia.dist(aux.name,no.name)+instancia.dist(no.name,aux.next.name)-instancia.dist(aux.name,aux.next.name);
					if(custo<bestCusto)
					{
						bestCusto=custo;
						bestNo=aux;
					}
				}
			}
		}
		
		custo=instancia.dist(bestNo.name,no.name)+instancia.dist(no.name,bestNo.next.name)-instancia.dist(bestNo.name,bestNo.next.name);
		custoAnt=instancia.dist(bestNo.prev.name,no.name)+instancia.dist(no.name,bestNo.name)-instancia.dist(bestNo.prev.name,bestNo.name);
		if(custo<custoAnt)
		{
			return bestNo;
		}
		else
		{
			return bestNo.prev;
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