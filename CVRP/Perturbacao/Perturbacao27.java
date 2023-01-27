package Perturbacao;

import java.util.HashMap;

import ControleDiversidade.AjusteOmega;
import Dados.Instancia;
import Improvement.BuscaLocalIntra;
import MetodoBusca.Config;
import Solucao.No;
import Solucao.Solucao;

//Remocao sequencial todos de uma vez depois adiciona o restante

public class Perturbacao27 extends Perturbacao
{
	
	public Perturbacao27(Instancia instancia, Config config, 
	HashMap<String, AjusteOmega> configuradoresOmega, BuscaLocalIntra buscaLocalIntra)
	{
		super(instancia, config,configuradoresOmega,buscaLocalIntra);
		this.tipoPerturbacao=TipoPerturbacao.Perturbacao27;
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
