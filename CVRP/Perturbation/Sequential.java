package Perturbation;

import java.util.HashMap;

import Data.Instancia;
import DiversityControl.AjusteOmega;
import Improvement.BuscaLocalIntra;
import SearchMethod.Config;
import Solution.No;
import Solution.Solucao;

//Remocao sequencial todos de uma vez depois adiciona o restante

public class Sequential extends Perturbacao
{
	
	public Sequential(Instancia instancia, Config config, 
	HashMap<String, AjusteOmega> configuradoresOmega, BuscaLocalIntra buscaLocalIntra)
	{
		super(instancia, config,configuradoresOmega,buscaLocalIntra);
		this.tipoPerturbacao=TipoPerturbacao.Sequential;
	}

	public void perturbar(Solucao s)
	{
		setSolucao(s);
		
//		---------------------------------------------------------------------
		int contSizeString;
		double sizeString;
		No noInicial;
		
		while(contCandidatos<(int)omega)
		{
			sizeString=Math.min(Math.max(1, size),(int)omega-contCandidatos);
			
			no=solucao[rand.nextInt(size)];
			while(!no.jaInserido)
				no=solucao[rand.nextInt(size)];
			
			noInicial=no;
			
			contSizeString=0;
			do
			{
				contSizeString++;
				no=noInicial.prox;
				if(no.nome==0)
					no=no.prox;
				
				candidatos[contCandidatos++]=no;
				
				no.antOld=no.ant;
				no.proxOld=no.prox;
				
				f+=no.rota.remove(no);
			}
			while(noInicial.nome!=no.nome&&contSizeString<sizeString);
		}
		
		estabelecerOrdem();
		
		adicionarCandidatos();
		
		passaSolucao(s);
	}

}
