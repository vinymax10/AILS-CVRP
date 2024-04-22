package Perturbation;

import java.util.HashMap;

import Data.Instance;
import DiversityControl.OmegaAdjustment;
import Improvement.IntraLocalSearch;
import SearchMethod.Config;
import Solution.Node;
import Solution.Solution;

//Remocao sequencial todos de uma vez depois adiciona o restante

public class Sequential extends Perturbacao
{
	
	public Sequential(Instance instance, Config config, 
	HashMap<String, OmegaAdjustment> configuradoresOmega, IntraLocalSearch intraLocalSearch)
	{
		super(instance, config,configuradoresOmega,intraLocalSearch);
		this.perturbationType=PerturbationType.Sequential;
	}

	public void perturbar(Solution s)
	{
		setSolution(s);
		
//		---------------------------------------------------------------------
		int contSizeString;
		double sizeString;
		Node noInicial;
		
		while(contCandidatos<(int)omega)
		{
			sizeString=Math.min(Math.max(1, size),(int)omega-contCandidatos);
			
			no=solution[rand.nextInt(size)];
			while(!no.jaInserido)
				no=solution[rand.nextInt(size)];
			
			noInicial=no;
			
			contSizeString=0;
			do
			{
				contSizeString++;
				no=noInicial.next;
				if(no.name==0)
					no=no.next;
				
				candidatos[contCandidatos++]=no;
				
				no.prevOld=no.prev;
				no.nextOld=no.next;
				
				f+=no.route.remove(no);
			}
			while(noInicial.name!=no.name&&contSizeString<sizeString);
		}
		
		estabelecerOrdem();
		
		adicionarCandidatos();
		
		passaSolucao(s);
	}

}
