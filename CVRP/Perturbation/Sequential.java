package Perturbation;

import java.util.HashMap;

import Data.Instance;
import DiversityControl.OmegaAdjustment;
import Improvement.BuscaLocalIntra;
import SearchMethod.Config;
import Solution.Node;
import Solution.Solution;

//Remocao sequencial todos de uma vez depois adiciona o restante

public class Sequential extends Perturbacao
{
	
	public Sequential(Instance instance, Config config, 
	HashMap<String, OmegaAdjustment> configuradoresOmega, BuscaLocalIntra buscaLocalIntra)
	{
		super(instance, config,configuradoresOmega,buscaLocalIntra);
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
				
				no.antOld=no.prev;
				no.proxOld=no.next;
				
				f+=no.rota.remove(no);
			}
			while(noInicial.name!=no.name&&contSizeString<sizeString);
		}
		
		estabelecerOrdem();
		
		adicionarCandidatos();
		
		passaSolucao(s);
	}

}
