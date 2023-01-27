package Perturbacao;

import java.util.HashMap;

import ControleDiversidade.AjusteOmega;
import Dados.Instancia;
import Improvement.BuscaLocalIntra;
import MetodoBusca.Config;
import Solucao.No;
import Solucao.Solucao;

//Remocao concentrica todos de uma vez e depois adiciona todos

public class Perturbacao28 extends Perturbacao
{
	public Perturbacao28(Instancia instancia, Config config,
	HashMap<String, AjusteOmega> configuradoresOmega, BuscaLocalIntra buscaLocalIntra)
	{
		super(instancia, config, configuradoresOmega,buscaLocalIntra);
		this.tipoPerturbacao=TipoPerturbacao.Perturbacao28;
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
