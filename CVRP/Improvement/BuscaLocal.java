package Improvement;

import java.util.Arrays;

import Data.Instance;
import Evaluators.CostEvaluation;
import Evaluators.FeasibilityEvaluation;
import Evaluators.ExecuteMovement;
import Evaluators.TipoMovimento;
import SearchMethod.Config;
import Solution.Node;
import Solution.Route;
import Solution.Solution;


public class BuscaLocal 
{
	private Route routes[];
	private  CandidateNode melhoras[];
	private  CandidateNode matrixMelhoras[][];
	private CandidateNode noMelhora;
	private int topMelhores=0;
	private int numRoutes;
	
	Node bestPrevNoRouteI,bestPrevNoRouteJ;

	double f=0;
	
	double custo;
	Node auxRouteI,auxRouteJ;
	Route routeA,routeB;
	CostEvaluation avaliadorCusto;
	FeasibilityEvaluation feasibilityEvaluation;
	ExecuteMovement executaMovimento;
	Node solution[];
	int limiteAdj;
	BuscaLocalIntra buscaLocalIntra;
	double epsilon;
	int contAtivos;
	
	public BuscaLocal(Instance instance,Config config, BuscaLocalIntra buscaLocalIntra)
	{
		this.avaliadorCusto=new CostEvaluation(instance);
		this.feasibilityEvaluation=new FeasibilityEvaluation();
		this.executaMovimento=new ExecuteMovement(instance);
		this.melhoras=new CandidateNode[instance.getMaxNumberRoutes()*(instance.getMaxNumberRoutes()-1)/2];
		this.matrixMelhoras=new CandidateNode[instance.getMaxNumberRoutes()][instance.getMaxNumberRoutes()];
		
		for (int i = 0; i < matrixMelhoras.length; i++)
		{
			for (int j = i+1; j < matrixMelhoras.length; j++) 
			{
				matrixMelhoras[i][j]=new CandidateNode(avaliadorCusto);
				matrixMelhoras[j][i]=matrixMelhoras[i][j];
			}
		}
		
		this.limiteAdj=Math.min(config.getVarphi(), instance.getSize()-1);
		this.buscaLocalIntra=buscaLocalIntra;
		this.epsilon=config.getEpsilon();
	}
	
	private void setSolution(Solution solution) 
	{
		this.numRoutes=solution.numRoutes;
		this.solution=solution.getSolution();
		this.f=solution.f;
		this.routes=solution.routes;
	}

	private void passaResultado(Solution solution) 
	{
		solution.numRoutes=this.numRoutes;
		solution.f=this.f;
	}

	public void buscaLocal(Solution solution,boolean removerRoutesVazias)
	{
		setSolution(solution);
		topMelhores=0;
			
		for (int i = 0; i < numRoutes; i++) 
			routes[i].setDemandaAcumulada();
		
		for (int i = 0; i < numRoutes; i++) 
		{
			if(routes[i].numElements>1&&routes[i].alterada)
				varrerRoutes(routes[i]);
		}
		
		if(topMelhores>0)
			executa();
		
		passaResultado(solution);
		if(removerRoutesVazias)
			solution.removeRoutesVazias();
	}
	
	//-----------------------------------Factibilizando com o Best Improviment-------------------------------------------
	
	private void executa() 
	{
		while(topMelhores>0)
		{
			Arrays.sort(melhoras,0,topMelhores);
			routeA=melhoras[0].routeA;
			routeB=melhoras[0].routeB;
			
			f+=executaMovimento.aplicar(melhoras[0]);

			BuscaLocalIntra(routeA);
			BuscaLocalIntra(routeB);
			
			routeA.setDemandaAcumulada();
			routeB.setDemandaAcumulada();
			
			contAtivos=0;
			for (int k = 0; k < topMelhores; k++) 
			{
				if(melhoras[k].routeA==routeA||melhoras[k].routeB==routeA||melhoras[k].routeA==routeB||melhoras[k].routeB==routeB)
				{
					melhoras[k].limpar();
					contAtivos++;
				}
			}
	
			Arrays.sort(melhoras,0,topMelhores);
			topMelhores-=contAtivos;
			
			varrerRoutes(routeA);
			varrerRoutes(routeB);
		}
	}
	
	public void varrerRoutes(Route route)
	{
		if(route.numElements>1)
		{
			procuraBestSHIFT(route);
			procuraBestSWAPEstrelaKNN(route);
			procuraBestCross(route);
		}
	}
	
	private void procuraBestSWAPEstrelaKNN(Route route)
	{
		auxRouteI=route.inicio.next;
		do
		{
			if(auxRouteI.alterado)
			{
				for (int j = 0; j < limiteAdj; j++) 
				{
					if(auxRouteI.getKnn()[j]!=0&&solution[auxRouteI.getKnn()[j]-1].route!=route)
					{
						auxRouteJ=solution[auxRouteI.getKnn()[j]-1];
						if(feasibilityEvaluation.gainSWAP(auxRouteI, auxRouteJ)>=0)
						{
							bestPrevNoRouteI=auxRouteJ.route.findBestPositionExcetoKNN(auxRouteI,auxRouteJ,solution);
							bestPrevNoRouteJ=auxRouteI.route.findBestPositionExcetoKNN(auxRouteJ,auxRouteI,solution);
							
							custo=avaliadorCusto.costSwapStar(auxRouteI,auxRouteJ,bestPrevNoRouteI,bestPrevNoRouteJ);
							noMelhora=matrixMelhoras[auxRouteI.route.nomeRoute][auxRouteJ.route.nomeRoute];
							
							if(((custo-noMelhora.custo)<-epsilon)&&((custo-0)<-epsilon))
							{
								if(!noMelhora.ativo)
									melhoras[topMelhores++]=noMelhora;
								
								noMelhora.setNoMelhora(custo, TipoMovimento.SWAPEstrela, auxRouteI, auxRouteJ,bestPrevNoRouteI, bestPrevNoRouteJ, custo);
							}
						}
					}
				}
			}
			
			auxRouteI=auxRouteI.next;
		}
		while(auxRouteI!=route.inicio);
	}
	
	private void procuraBestSHIFT(Route route)
	{
		auxRouteI=route.inicio.next;
		do
		{
			if(auxRouteI.alterado)
			{
				for (int j = 0; j < limiteAdj; j++) 
				{
					if(auxRouteI.getKnn()[j]==0)
					{
						for (int i = 0; i < numRoutes; i++) 
						{
							if(routes[i]!=route)
							{
								auxRouteJ=routes[i].inicio;
								if(feasibilityEvaluation.gainSHIFT(auxRouteI, auxRouteJ)>=0)
								{
									custo=avaliadorCusto.costSHIFT(auxRouteI,auxRouteJ);
									noMelhora=matrixMelhoras[auxRouteI.route.nomeRoute][auxRouteJ.route.nomeRoute];
									
									if(((custo-noMelhora.custo)<-epsilon)&&((custo-0)<-epsilon))
									{
										if(!noMelhora.ativo)
											melhoras[topMelhores++]=noMelhora;
										
										noMelhora.setNoMelhora(custo, TipoMovimento.SHIFT, auxRouteI, auxRouteJ,custo);
									}
								}
							}
						}
					}
					else if(solution[auxRouteI.getKnn()[j]-1].route!=route)
					{
						auxRouteJ=solution[auxRouteI.getKnn()[j]-1];
						if(feasibilityEvaluation.gainSHIFT(auxRouteI, auxRouteJ)>=0)
						{
							custo=avaliadorCusto.costSHIFT(auxRouteI,auxRouteJ);
							noMelhora=matrixMelhoras[auxRouteI.route.nomeRoute][auxRouteJ.route.nomeRoute];
							
							if(((custo-noMelhora.custo)<-epsilon)&&((custo-0)<-epsilon))
							{
								if(!noMelhora.ativo)
									melhoras[topMelhores++]=noMelhora;
								
								noMelhora.setNoMelhora(custo, TipoMovimento.SHIFT, auxRouteI, auxRouteJ,custo);
							}
						}
						
						if(feasibilityEvaluation.gainSHIFT(auxRouteJ, auxRouteI)>=0)
						{
							custo=avaliadorCusto.costSHIFT(auxRouteJ, auxRouteI);
							noMelhora=matrixMelhoras[auxRouteI.route.nomeRoute][auxRouteJ.route.nomeRoute];
							
							if(((custo-noMelhora.custo)<-epsilon)&&((custo-0)<-epsilon))
							{
								if(!noMelhora.ativo)
									melhoras[topMelhores++]=noMelhora;
								
								noMelhora.setNoMelhora(custo, TipoMovimento.SHIFT, auxRouteJ,auxRouteI, custo);
							}
						}
					}
				}
			}
			
			auxRouteI=auxRouteI.next;
		}
		while(auxRouteI!=route.inicio);
	}
	
	private void procuraBestCross(Route route)
	{
		auxRouteI=route.inicio;
		do
		{
			if(auxRouteI.alterado)
			{
				for (int j = 0; j < limiteAdj; j++) 
				{
					if(auxRouteI.getKnn()[j]==0)
					{
						for (int i = 0; i < numRoutes; i++) 
						{
							if(routes[i]!=route)
							{
								auxRouteJ=routes[i].inicio;
								if(feasibilityEvaluation.gainCross(auxRouteI, auxRouteJ)>=0)
								{
									custo=avaliadorCusto.costCross(auxRouteI,auxRouteJ);
									noMelhora=matrixMelhoras[auxRouteI.route.nomeRoute][auxRouteJ.route.nomeRoute];
									
									if(((custo-noMelhora.custo)<-epsilon)&&((custo-0)<-epsilon))
									{
										if(!noMelhora.ativo)
											melhoras[topMelhores++]=noMelhora;
										
										noMelhora.setNoMelhora(custo, TipoMovimento.Cross, auxRouteI, auxRouteJ,custo);
									}
								}
								
								if(feasibilityEvaluation.gainCrossInverted(auxRouteI, auxRouteJ)>=0)
								{
									custo=avaliadorCusto.inversedCostCross(auxRouteI,auxRouteJ);
									noMelhora=matrixMelhoras[auxRouteI.route.nomeRoute][auxRouteJ.route.nomeRoute];
									
									if(((custo-noMelhora.custo)<-epsilon)&&((custo-0)<-epsilon))
									{
										if(!noMelhora.ativo)
											melhoras[topMelhores++]=noMelhora;
										
										noMelhora.setNoMelhora(custo, TipoMovimento.CrossInverted, auxRouteI, auxRouteJ,custo);
									}
								}
							}
						}
					}
					else if(solution[auxRouteI.getKnn()[j]-1].route!=route)
					{
						auxRouteJ=solution[auxRouteI.getKnn()[j]-1];
						if(feasibilityEvaluation.gainCross(auxRouteI, auxRouteJ)>=0)
						{
							custo=avaliadorCusto.costCross(auxRouteI,auxRouteJ);
							noMelhora=matrixMelhoras[auxRouteI.route.nomeRoute][auxRouteJ.route.nomeRoute];
							
							if(((custo-noMelhora.custo)<-epsilon)&&((custo-0)<-epsilon))
							{
								if(!noMelhora.ativo)
									melhoras[topMelhores++]=noMelhora;
								
								noMelhora.setNoMelhora(custo, TipoMovimento.Cross, auxRouteI, auxRouteJ,custo);
							}
						}
						if(feasibilityEvaluation.gainCrossInverted(auxRouteI, auxRouteJ)>=0)
						{
							custo=avaliadorCusto.inversedCostCross(auxRouteI,auxRouteJ);
							noMelhora=matrixMelhoras[auxRouteI.route.nomeRoute][auxRouteJ.route.nomeRoute];
							
							if(((custo-noMelhora.custo)<-epsilon)&&((custo-0)<-epsilon))
							{
								if(!noMelhora.ativo)
									melhoras[topMelhores++]=noMelhora;
								
								noMelhora.setNoMelhora(custo, TipoMovimento.CrossInverted, auxRouteI, auxRouteJ,custo);
							}
						}
					}
				}
			}
			
			auxRouteI=auxRouteI.next;
		}
		while(auxRouteI!=route.inicio);
	}
	
	private void BuscaLocalIntra(Route route)
	{
		f+=buscaLocalIntra.buscaLocalIntra(route, solution);
	}
	
}
