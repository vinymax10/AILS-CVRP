package Perturbation;

import java.util.HashMap;

import Data.Instance;
import DiversityControl.AjusteOmega;
import Improvement.BuscaLocalIntra;
import SearchMethod.Config;
import Solution.No;
import Solution.Solucao;

//Remocao concentrica todos de uma vez e depois adiciona todos

public class Concentric extends Perturbacao
{
	public Concentric(Instance instancia, Config config,
	HashMap<String, AjusteOmega> configuradoresOmega, BuscaLocalIntra buscaLocalIntra)
	{
		super(instancia, config, configuradoresOmega,buscaLocalIntra);
		this.tipoPerturbacao=TipoPerturbacao.Concentric;
	}

	public void perturbar(Solucao s)
	{
		setSolucao(s);
		
//		---------------------------------------------------------------------
		No referencia=solucao[rand.nextInt(solucao.length)];
		for (int i = 0; i < omega&&i < referencia.knn.length&&contCandidatos<omega; i++) 
		{
			if(referencia.knn[i]!=0)
			{
				no=solucao[referencia.knn[i]-1];
				candidatos[contCandidatos]=no;
				contCandidatos++;
				no.antOld=no.ant;
				no.proxOld=no.prox;
				f+=no.rota.remove(no);
			}
		}
		
		estabelecerOrdem();
		
		adicionarCandidatos();
		
		passaSolucao(s);
	}
}
