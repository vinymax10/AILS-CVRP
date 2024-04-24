package Improvement;

import Data.Instance;
import Evaluators.CostEvaluation;
import Evaluators.ExecuteMovement;
import Evaluators.MovementType;
import SearchMethod.Config;
import Solution.Node;
import Solution.Route;

public class IntraLocalSearch 
{
	private  CandidateNode improve;
	Node first;
	int numElements=0;
	MovementType moveType;
	int iterator;
	double lowestCost;
	double prevF;
	Node auxSai,auxEntra;
	double cost;
	boolean changeFlag=false;
	CostEvaluation evaluateCost;
	ExecuteMovement executeMovement;
	int limitAdj;
	
	public IntraLocalSearch(Instance instance,Config config)
	{
		this.evaluateCost=new CostEvaluation(instance);
		this.executeMovement=new ExecuteMovement(instance);
		this.improve=new CandidateNode(evaluateCost);
		this.limitAdj=config.getVarphi();
	}
	
	private void setRoute(Route route,Node solution[]) 
	{
		this.prevF=route.fRoute;
		this.first=route.first;
		this.numElements=route.numElements;
	}

	public double intraLocalSearch(Route route,Node solution[])
	{
		setRoute(route,solution);
		
		iterator=0;
		changeFlag=true;
		while(changeFlag)
		{
			iterator++;
			changeFlag=false;
			lowestCost=0;
			auxSai=first;
			do
			{
				if(auxSai.modified)
				{
					for (int j = 0; j < limitAdj; j++) 
					{
						if(auxSai.getKnn()[j]==0)
							auxEntra=first;
						else
							auxEntra=solution[auxSai.getKnn()[j]-1];
						
						if(auxSai.route.nomeRoute==auxEntra.route.nomeRoute)
						{
							//2Opt
							if(auxSai!=auxEntra&&auxEntra!=auxSai.next)
							{
								cost=evaluateCost.cost2Opt(auxSai,auxEntra);
								if(lowestCost>cost)
								{
									lowestCost=cost;
									moveType=MovementType.TwoOpt;
									improve.setImprovedNode(lowestCost, moveType, auxSai, auxEntra,0,0,lowestCost);
									changeFlag=true;
								}
							}
							
							//SHIFT
							if(numElements>2&&auxSai!=auxEntra&&auxSai!=auxEntra.next)
							{
								cost=evaluateCost.costSHIFT(auxSai,auxEntra);
								if(lowestCost>cost)
								{
									lowestCost=cost;
									moveType=MovementType.SHIFT;
									improve.setImprovedNode(lowestCost, moveType, auxSai, auxEntra,0,0,lowestCost);
									changeFlag=true;
								}
								
								if(auxEntra!=auxSai.next)
								{
									cost=evaluateCost.costSHIFT(auxEntra,auxSai);
									if(lowestCost>cost)
									{
										lowestCost=cost;
										moveType=MovementType.SHIFT;
										improve.setImprovedNode(lowestCost, moveType, auxEntra, auxSai,0,0,lowestCost);
										changeFlag=true;
									}
								}
							}
							
							//SWAP
							if(numElements>2&&auxEntra!=auxSai&&auxEntra.next!=auxSai)
							{
								cost=evaluateCost.costSWAP(auxSai,auxEntra);
								if(lowestCost>cost)
								{
									lowestCost=cost;
									moveType=MovementType.SWAP;
									improve.setImprovedNode(lowestCost, moveType, auxSai, auxEntra,0,0,lowestCost);
									changeFlag=true;
								}
							}
						}
					}
				}
				
				auxSai=auxSai.next;
			}
			while(auxSai!=first);
			
			if(changeFlag)
				executeMovement.aplicar(improve);
		}
		
		return route.fRoute-prevF;
	}
	
}
