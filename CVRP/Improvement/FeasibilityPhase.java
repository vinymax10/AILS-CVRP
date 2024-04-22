package Improvement;

import java.util.Arrays;

import Data.Instance;
import Evaluators.CostEvaluation;
import Evaluators.FeasibilityEvaluation;
import Evaluators.ExecuteMovement;
import Evaluators.MovementType;
import SearchMethod.Config;
import Solution.Node;
import Solution.Route;
import Solution.Solution;

public class FeasibilityPhase 
{
	private Route routes[];
	private  CandidateNode improvedMoves[];
	private  CandidateNode improvedMatrix[][];

	private CandidateNode improvedNode;

	private int topBest=0;
	private int numRoutes;
	
	Node bestPrevNoRouteI,bestPrevNoRouteJ;

	double f=0;
	
	Node auxSai,auxEntra;
	int gain;
	double cost;
	double evaluationCost;
	Route routeA,routeB;
	
	CostEvaluation evaluateCost;
	FeasibilityEvaluation feasibilityEvaluation;
	ExecuteMovement executeMovement;
	Node solution[];
	int limitAdj;
	IntraLocalSearch intraLocalSearch;
	double epsilon;
	int activeNodesCounter;
	
	public FeasibilityPhase(Instance instance,Config config, IntraLocalSearch intraLocalSearch)
	{
		this.evaluateCost=new CostEvaluation(instance);
		this.feasibilityEvaluation=new FeasibilityEvaluation();
		this.executeMovement=new ExecuteMovement(instance);
		this.improvedMoves=new CandidateNode[instance.getMaxNumberRoutes()*(instance.getMaxNumberRoutes()-1)/2];
		this.improvedMatrix=new CandidateNode[instance.getMaxNumberRoutes()][instance.getMaxNumberRoutes()];
		
		for (int i = 0; i < improvedMatrix.length; i++)
		{
			for (int j = 0; j < improvedMatrix.length; j++) 
			{
				improvedMatrix[i][j]=new CandidateNode(evaluateCost);
				improvedMatrix[j][i]=improvedMatrix[i][j];
			}
		}
		
		this.limitAdj=Math.min(config.getVarphi(), instance.getSize()-1);
		
		this.intraLocalSearch=intraLocalSearch;
		this.epsilon=config.getEpsilon();
	}
	
	private boolean feasible() 
	{
		for (int i = 0; i < numRoutes; i++)
		{
			if(routes[i].availableCapacity()<0)
				return false;
		}
		return true;
	}
	
	private void setSolution(Solution solution) 
	{
		this.numRoutes=solution.numRoutes;
		this.solution=solution.getSolution();
		this.f=solution.f;
		this.routes=solution.routes;
	}

	private void assignResult(Solution solution) 
	{
		solution.numRoutes=this.numRoutes;
		solution.f=this.f;
	}

	public void makeFeasible(Solution solution)
	{
		setSolution(solution);
		boolean feasible=false;
		
		do
		{
			topBest=0;
			for (int i = 0; i < numRoutes; i++) 
				routes[i].setDemandaAcumulada();
			
			for (int i = 0; i < numRoutes; i++) 
			{
				if(routes[i].modified)
					browseRoutes(routes[i]); 
			}
			
			if(topBest>0)
				execute();

			if(feasible())
			{
				intraLocalSearch();
				
				assignResult(solution);
				solution.removeEmptyRoutes();
				feasible=true;
			}
			else
			{
				numRoutes++;
				routes[numRoutes-1].clean();
			}
		}
		while(!feasible);
		
	}
	
	//-----------------------------------Factibilizando com o Best Improviment-------------------------------------------
	
	private void execute() 
	{
		while(topBest>0)
		{
			Arrays.sort(improvedMoves,0,topBest);
			
			routeA=improvedMoves[0].routeA;
			routeB=improvedMoves[0].routeB;
			f+=executeMovement.aplicar(improvedMoves[0]);
			
			intraLocalSearch(routeA);
			intraLocalSearch(routeB);
			
			routeA.setDemandaAcumulada();
			routeB.setDemandaAcumulada();
			
			activeNodesCounter=0;
			for (int k = 0; k < topBest; k++) 
			{
				if(improvedMoves[k].routeA==routeA||improvedMoves[k].routeA==routeB||improvedMoves[k].routeB==routeA||improvedMoves[k].routeB==routeB)
				{
					improvedMoves[k].clean();
					activeNodesCounter++;
				}
			}
	
			Arrays.sort(improvedMoves,0,topBest);
			topBest-=activeNodesCounter;
			
			browseRoutes(routeA);
			browseRoutes(routeB);
		}
	}
	
	public void browseRoutes(Route route)
	{
		if(route.getNumElements()>1)
		{
			searchBestSHIFT(route);
			searchBestSwapStarKnn(route);
			searchBestCross(route);
		}
	}
	
	public void calculateCost()
	{
		if(cost>=0)
			evaluationCost=((double)cost+1)/gain;
		else
			evaluationCost=(double)cost/gain;
	}
	
	private void searchBestSwapStarKnn(Route route)
	{
		auxSai=route.first.next;
		do
		{
			if(auxSai.alterado)
			{
				for (int j = 0; j < limitAdj; j++) 
				{
					if(auxSai.getKnn()[j]!=0&&auxSai.route.isFeasible()^solution[auxSai.getKnn()[j]-1].route.isFeasible())
					{
						auxEntra=solution[auxSai.getKnn()[j]-1];
						gain=feasibilityEvaluation.gainSWAP(auxSai, auxEntra);
						if(gain>0)
						{
							
							bestPrevNoRouteI=auxEntra.route.findBestPositionExcetoKNN(auxSai,auxEntra,solution);
							bestPrevNoRouteJ=auxSai.route.findBestPositionExcetoKNN(auxEntra,auxSai,solution);
							
							cost=evaluateCost.costSwapStar(auxSai,auxEntra,bestPrevNoRouteI,bestPrevNoRouteJ);
							calculateCost();
							
							improvedNode=improvedMatrix[auxSai.route.nomeRoute][auxEntra.route.nomeRoute];
							
							if(evaluationCost<improvedNode.evaluationCost)
							{
								if(!improvedNode.active)
									improvedMoves[topBest++]=improvedNode;
								
								improvedNode.setImprovedNode(cost, MovementType.SWAPEstrela, auxSai, auxEntra,bestPrevNoRouteI, bestPrevNoRouteJ, evaluationCost, gain);
							}
						}
					}
				}
			}
			auxSai=auxSai.next;
		}
		while(auxSai!=route.first);
	}
	
	private void searchBestSHIFT(Route route)
	{
		auxSai=route.first.next;
		do
		{
			if(auxSai.alterado)
			{
				for (int i = 0; i < numRoutes; i++) 
				{
					if(!auxSai.route.isFeasible()&&routes[i].isFeasible())
					{
						auxEntra=routes[i].first;
						gain=feasibilityEvaluation.gainSHIFT(auxSai, auxEntra);
						if(gain>0)
						{
							cost=evaluateCost.costSHIFT(auxSai, auxEntra);
							calculateCost();
							improvedNode=improvedMatrix[auxSai.route.nomeRoute][auxEntra.route.nomeRoute];
							
							if(evaluationCost<improvedNode.evaluationCost)
							{
								if(!improvedNode.active)
									improvedMoves[topBest++]=improvedNode;
								
								improvedNode.setImprovedNode(cost, MovementType.SHIFT, auxSai, auxEntra,evaluationCost,gain);
							}
						}
					}
				}
				
				for (int j = 0; j < limitAdj; j++) 
				{
					if(auxSai.getKnn()[j]!=0&&!auxSai.route.isFeasible()&&solution[auxSai.getKnn()[j]-1].route.isFeasible())
					{
						auxEntra=solution[auxSai.getKnn()[j]-1];
						gain=feasibilityEvaluation.gainSHIFT(auxSai, auxEntra);
						if(gain>0)
						{
							cost=evaluateCost.costSHIFT(auxSai, auxEntra);
							calculateCost();
							improvedNode=improvedMatrix[auxSai.route.nomeRoute][auxEntra.route.nomeRoute];
							
							if(evaluationCost<improvedNode.evaluationCost)
							{
								if(!improvedNode.active)
									improvedMoves[topBest++]=improvedNode;
								
								improvedNode.setImprovedNode(cost, MovementType.SHIFT, auxSai, auxEntra,evaluationCost,gain);
							}
						}
					}
					
					if(auxSai.getKnn()[j]!=0&&auxSai.route.isFeasible()&&!solution[auxSai.getKnn()[j]-1].route.isFeasible())
					{
						auxEntra=solution[auxSai.getKnn()[j]-1];
						gain=feasibilityEvaluation.gainSHIFT(auxEntra, auxSai);
						if(gain>0)
						{
							cost=evaluateCost.costSHIFT(auxEntra, auxSai);
							calculateCost();
							
							improvedNode=improvedMatrix[auxSai.route.nomeRoute][auxEntra.route.nomeRoute];
							
							if(evaluationCost<improvedNode.evaluationCost)
							{
								if(!improvedNode.active)
									improvedMoves[topBest++]=improvedNode;
								
								improvedNode.setImprovedNode(cost, MovementType.SHIFT, auxEntra ,auxSai ,evaluationCost,gain);
							}
						}
					}
				}
			}
			auxSai=auxSai.next;
		}
		while(auxSai!=route.first);
	}
	
	
	private void searchBestCross(Route route)
	{
		auxSai=route.first;
		do
		{
			if(auxSai.alterado)
			{
				for (int i = 0; i < numRoutes; i++) 
				{
					if(auxSai.route.isFeasible()^routes[i].isFeasible())
					{
						auxEntra=routes[i].first;
						gain=feasibilityEvaluation.gainCross(auxSai, auxEntra);
						if(gain>0)
						{
							cost=evaluateCost.costCross(auxSai, auxEntra);
							calculateCost();
							
							improvedNode=improvedMatrix[auxSai.route.nomeRoute][auxEntra.route.nomeRoute];
							
							if(evaluationCost<improvedNode.evaluationCost)
							{
								if(!improvedNode.active)
									improvedMoves[topBest++]=improvedNode;
								
								improvedNode.setImprovedNode(cost, MovementType.Cross, auxSai, auxEntra,evaluationCost,gain);
							}
							
						}
						gain=feasibilityEvaluation.gainCrossInverted(auxSai, auxEntra);
						if(gain>0)
						{
							cost=evaluateCost.inversedCostCross(auxSai, auxEntra);
							calculateCost();
							
							improvedNode=improvedMatrix[auxSai.route.nomeRoute][auxEntra.route.nomeRoute];
							
							if(evaluationCost<improvedNode.evaluationCost)
							{
								if(!improvedNode.active)
									improvedMoves[topBest++]=improvedNode;
								
								improvedNode.setImprovedNode(cost, MovementType.CrossInverted, auxSai, auxEntra,evaluationCost,gain);
							}
						}
					}
				}
				
				for (int j = 0; j < limitAdj; j++) 
				{
					if(auxSai.getKnn()[j]!=0&&auxSai.route.isFeasible()^solution[auxSai.getKnn()[j]-1].route.isFeasible())
					{
						auxEntra=solution[auxSai.getKnn()[j]-1];
						gain=feasibilityEvaluation.gainCross(auxSai, auxEntra);
						if(gain>0)
						{
							cost=evaluateCost.costCross(auxSai, auxEntra);
							calculateCost();
							improvedNode=improvedMatrix[auxSai.route.nomeRoute][auxEntra.route.nomeRoute];
							
							if(evaluationCost<improvedNode.evaluationCost)
							{
								if(!improvedNode.active)
									improvedMoves[topBest++]=improvedNode;
								
								improvedNode.setImprovedNode(cost, MovementType.Cross, auxSai, auxEntra,evaluationCost,gain);
							}
						}
						gain=feasibilityEvaluation.gainCrossInverted(auxSai, auxEntra);
						if(gain>0)
						{
							cost=evaluateCost.inversedCostCross(auxSai, auxEntra);
							calculateCost();
							improvedNode=improvedMatrix[auxSai.route.nomeRoute][auxEntra.route.nomeRoute];
							
							if(evaluationCost<improvedNode.evaluationCost)
							{
								if(!improvedNode.active)
									improvedMoves[topBest++]=improvedNode;
								
								improvedNode.setImprovedNode(cost, MovementType.CrossInverted, auxSai, auxEntra,evaluationCost,gain);
							}
						}
					}
				}
			}
			auxSai=auxSai.next;
		}
		while(auxSai!=route.first);
	}
	
	private void intraLocalSearch(Route route)
	{
		f+=intraLocalSearch.intraLocalSearch(route, solution);
	}
	
	private void intraLocalSearch()
	{
		for (int i = 0; i < numRoutes; i++)
		{
			if(routes[i].modified)
				f+=intraLocalSearch.intraLocalSearch(routes[i], solution);
		}
	}
	
}
