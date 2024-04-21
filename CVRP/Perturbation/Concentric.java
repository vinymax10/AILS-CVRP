package Perturbation;

import java.util.HashMap;

import Data.Instance;
import DiversityControl.OmegaAdjustment;
import Improvement.BuscaLocalIntra;
import SearchMethod.Config;
import Solution.Node;
import Solution.Solution;

//Remocao concentrica todos de uma vez e depois adiciona todos

public class Concentric extends Perturbacao
{
	public Concentric(Instance instance, Config config,
	HashMap<String, OmegaAdjustment> configuradoresOmega, BuscaLocalIntra buscaLocalIntra)
	{
		super(instance, config, configuradoresOmega,buscaLocalIntra);
		this.perturbationType=PerturbationType.Concentric;
	}

	public void perturbar(Solution s)
	{
		setSolution(s);
		
//		---------------------------------------------------------------------
		Node referencia=solution[rand.nextInt(solution.length)];
		for (int i = 0; i < omega&&i < referencia.knn.length&&contCandidatos<omega; i++) 
		{
			if(referencia.knn[i]!=0)
			{
				no=solution[referencia.knn[i]-1];
				candidatos[contCandidatos]=no;
				contCandidatos++;
				no.antOld=no.prev;
				no.proxOld=no.next;
				f+=no.rota.remove(no);
			}
		}
		
		estabelecerOrdem();
		
		adicionarCandidatos();
		
		passaSolucao(s);
	}
}
