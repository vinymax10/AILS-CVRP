package Perturbation;

import java.util.HashMap;

import Data.Instance;
import DiversityControl.OmegaAdjustment;
import Improvement.IntraLocalSearch;
import SearchMethod.Config;
import Solution.Node;
import Solution.Solution;

//Remocao sequencial todos de uma vez depois adiciona o restante

public class Sequential extends Perturbation
{
	
	public Sequential(Instance instance, Config config, 
	HashMap<String, OmegaAdjustment> omegaSetup, IntraLocalSearch intraLocalSearch)
	{
		super(instance, config,omegaSetup,intraLocalSearch);
		this.perturbationType=PerturbationType.Sequential;
	}

	public void applyPerturbation(Solution s)
	{
		setSolution(s);
		
//		---------------------------------------------------------------------
		int contSizeString;
		double sizeString;
		Node noInicial;
		
		while(countCandidates<(int)omega)
		{
			sizeString=Math.min(Math.max(1, size),(int)omega-countCandidates);
			
			node=solution[rand.nextInt(size)];
			while(!node.nodeBelong)
				node=solution[rand.nextInt(size)];
			
			noInicial=node;
			
			contSizeString=0;
			do
			{
				contSizeString++;
				node=noInicial.next;
				if(node.name==0)
					node=node.next;
				
				candidates[countCandidates++]=node;
				
				node.prevOld=node.prev;
				node.nextOld=node.next;
				
				f+=node.route.remove(node);
			}
			while(noInicial.name!=node.name&&contSizeString<sizeString);
		}
		
		setOrder();
		
		addCandidates();
		
		assignSolution(s);
	}

}
