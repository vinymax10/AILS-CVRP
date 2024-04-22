package Perturbation;

import java.util.HashMap;
import java.util.Random;

import Data.Instance;
import DiversityControl.OmegaAdjustment;
import Improvement.IntraLocalSearch;
import SearchMethod.Config;
import Solution.Node;
import Solution.Route;
import Solution.Solution;

public abstract class Perturbacao 
{
	protected Route routes[];
	protected int numRoutes;
	protected Node solution[];
	protected double f=0;
	protected Random rand=new Random();
	public double omega;
	OmegaAdjustment configuradorOmegaEscolhido;
	Config config;
	protected Node candidatos[];
	protected int contCandidatos;

	HeuristicaAdicao[]heuristicasAdicao;
	public HeuristicaAdicao heuristicaAdicaoEscolhida;
	
	Node no;
	
	public PerturbationType perturbationType;
	int size;
	HashMap<String, OmegaAdjustment> configuradoresOmega;
	
	double bestCusto,bestDist;
	int numIterUpdate;
	int posHeuEscolhida;
	
	double cost,dist;
	double costPrev;
	int indexA,indexB;
	Node bestNo,bestNoDist,aux;
	Instance instance;
	int limitAdj;
	
	IntraLocalSearch intraLocalSearch;
	
	public Perturbacao(Instance instance,Config config,
	HashMap<String, OmegaAdjustment> configuradoresOmega, IntraLocalSearch intraLocalSearch) 
	{
		this.config=config;
		this.instance=instance;
		this.heuristicasAdicao=config.getHeuristicasAdicao();
		this.size=instance.getSize()-1;
		this.candidatos=new Node[size];
		this.configuradoresOmega=configuradoresOmega;
		this.numIterUpdate=config.getGamma();
		this.limitAdj=config.getVarphi();
		this.intraLocalSearch=intraLocalSearch;
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
	
	protected void setSolution(Solution s)
	{
		this.numRoutes=s.getNumRoutes();
		this.routes=s.routes;
		this.solution=s.getSolution();
		this.f=s.f;
		for (int i = 0; i < numRoutes; i++) 
		{
			routes[i].modified=false;
			routes[i].first.alterado=false;
		}
		
		for (int i = 0; i < size; i++) 
			solution[i].alterado=false;
	
		posHeuEscolhida=rand.nextInt(heuristicasAdicao.length);
		heuristicaAdicaoEscolhida=heuristicasAdicao[posHeuEscolhida];
		
		configuradorOmegaEscolhido=configuradoresOmega.get(perturbationType+"");
		omega=configuradorOmegaEscolhido.getActualOmega();
		omega=Math.min(omega, size);
		
		contCandidatos=0;
	}
	
	protected void passaSolucao(Solution s)
	{
		s.f=f;
		s.numRoutes=this.numRoutes;
	}
	
	protected Node getNo(Node no)
	{
		switch(heuristicaAdicaoEscolhida)
		{
			case Distance: return getBestKNNNo2(no,1);
			case Cost: return getBestKNNNo2(no,limitAdj);
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
				for (int j = 0; j < numRoutes; j++) 
				{
					aux=routes[j].first;
					entrou=true;
					cost=instance.dist(aux.name,no.name)+instance.dist(no.name,aux.next.name)-instance.dist(aux.name,aux.next.name);
					if(cost<bestCusto)
					{
						bestCusto=cost;
						bestNo=aux;
					}
				}
				if(entrou)
					cont++;
			}
			else
			{
				aux=solution[no.knn[i]-1];
				if(aux.jaInserido)
				{
					cont++;
					cost=instance.dist(aux.name,no.name)+instance.dist(no.name,aux.next.name)-instance.dist(aux.name,aux.next.name);
					if(cost<bestCusto)
					{
						bestCusto=cost;
						bestNo=aux;
					}
				}
			}
		}
		
		if(bestNo==null)
		{
			for (int i = 0; i < solution.length; i++) 
			{
				aux=solution[i];
				if(aux.jaInserido)
				{
					cost=instance.dist(aux.name,no.name)+instance.dist(no.name,aux.next.name)-instance.dist(aux.name,aux.next.name);
					if(cost<bestCusto)
					{
						bestCusto=cost;
						bestNo=aux;
					}
				}
			}
		}
		
		if(bestNo==null)
		{
			for (int i = 0; i < solution.length; i++) 
			{
				aux=solution[i];
				if(aux.jaInserido)
				{
					cost=instance.dist(aux.name,no.name)+instance.dist(no.name,aux.next.name)-instance.dist(aux.name,aux.next.name);
					if(cost<bestCusto)
					{
						bestCusto=cost;
						bestNo=aux;
					}
				}
			}
		}
		
		cost=instance.dist(bestNo.name,no.name)+instance.dist(no.name,bestNo.next.name)-instance.dist(bestNo.name,bestNo.next.name);
		costPrev=instance.dist(bestNo.prev.name,no.name)+instance.dist(no.name,bestNo.name)-instance.dist(bestNo.prev.name,bestNo.name);
		if(cost<costPrev)
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
			
			f+=bestNo.route.addDepois(no, bestNo);
		}
	}
	
	public int getPosHeuEscolhida() {
		return posHeuEscolhida;
	}

	public OmegaAdjustment getConfiguradorOmegaEscolhido() {
		return configuradorOmegaEscolhido;
	}
	
	public PerturbationType getPerturbationType() {
		return perturbationType;
	}
	
}