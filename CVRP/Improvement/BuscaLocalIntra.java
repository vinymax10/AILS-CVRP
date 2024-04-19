package Improvement;

import Data.Instance;
import Evaluators.AvaliadorCusto;
import Evaluators.ExecutaMovimento;
import SearchMethod.Config;
import Solution.Node;
import Solution.Rota;

public class BuscaLocalIntra 
{
	private  NoPosMel melhora;
	Node inicio;
	int numElements=0;
	int tipoMov;
	int iterador;
	double menorCusto;
	double antF;
	Node auxSai,auxEntra;
	double custo;
	boolean trocou=false;
	AvaliadorCusto avaliadorCusto;
	ExecutaMovimento executaMovimento;
	int limiteAdj;
	
	public BuscaLocalIntra(Instance instancia,Config config)
	{
		this.avaliadorCusto=new AvaliadorCusto(instancia);
		this.executaMovimento=new ExecutaMovimento(instancia);
		this.melhora=new NoPosMel(avaliadorCusto);
		this.limiteAdj=config.getVarphi();
	}
	
	private void setRota(Rota rota,Node solucao[]) 
	{
		this.antF=rota.fRota;
		this.inicio=rota.inicio;
		this.numElements=rota.numElements;
	}

	public double buscaLocalIntra(Rota rota,Node solucao[])
	{
		setRota(rota,solucao);
		
		iterador=0;
		trocou=true;
		while(trocou)
		{
			iterador++;
			trocou=false;
			menorCusto=0;
			auxSai=inicio;
			do
			{
				if(auxSai.alterado)
				{
					for (int j = 0; j < limiteAdj; j++) 
					{
						if(auxSai.getKnn()[j]==0)
							auxEntra=inicio;
						else
							auxEntra=solucao[auxSai.getKnn()[j]-1];
						
						if(auxSai.rota.nomeRota==auxEntra.rota.nomeRota)
						{
							//2Opt
							if(auxSai!=auxEntra&&auxEntra!=auxSai.next)
							{
								custo=avaliadorCusto.custo2Opt(auxSai,auxEntra);
								if(menorCusto>custo)
								{
									menorCusto=custo;
									tipoMov=10;
									melhora.setNoMelhora(menorCusto, tipoMov, auxSai, auxEntra,0,0,menorCusto);
									trocou=true;
								}
							}
							
							//SHIFT
							if(numElements>2&&auxSai!=auxEntra&&auxSai!=auxEntra.next)
							{
								custo=avaliadorCusto.custoSHIFT(auxSai,auxEntra);
								if(menorCusto>custo)
								{
									menorCusto=custo;
									tipoMov=7;
									melhora.setNoMelhora(menorCusto, tipoMov, auxSai, auxEntra,0,0,menorCusto);
									trocou=true;
								}
								
								if(auxEntra!=auxSai.next)
								{
									custo=avaliadorCusto.custoSHIFT(auxEntra,auxSai);
									if(menorCusto>custo)
									{
										menorCusto=custo;
										tipoMov=7;
										melhora.setNoMelhora(menorCusto, tipoMov, auxEntra, auxSai,0,0,menorCusto);
										trocou=true;
									}
								}
							}
							
							//SWAP
							if(numElements>2&&auxEntra!=auxSai&&auxEntra.next!=auxSai)
							{
								custo=avaliadorCusto.custoSWAP(auxSai,auxEntra);
								if(menorCusto>custo)
								{
									menorCusto=custo;
									tipoMov=4;
									melhora.setNoMelhora(menorCusto, tipoMov, auxSai, auxEntra,0,0,menorCusto);
									trocou=true;
								}
							}
						}
					}
				}
				
				auxSai=auxSai.next;
			}
			while(auxSai!=inicio);
			
			if(trocou)
				executaMovimento.aplicar(melhora);
		}
		
		return rota.fRota-antF;
	}
	
}
