package Improvement;

import java.util.Arrays;

import Avaliadores.AvaliadorCusto;
import Avaliadores.AvaliadorFac;
import Avaliadores.ExecutaMovimento;
import Dados.Instancia;
import MetodoBusca.Config;
import Solucao.No;
import Solucao.Rota;
import Solucao.Solucao;

public class Factibilizador 
{
	private Rota rotas[];
	private  NoPosMel melhoras[];
	private  NoPosMel matrixMelhoras[][];

	private NoPosMel noMelhora;

	private int topMelhores=0;
	private int NumRotas;
	
	Rota rotaI,rotaJ;
	No bestAntNoRotaI,bestAntNoRotaJ;

	double f=0;
	
	No auxSai,auxEntra;
	int ganho;
	double custo;
	double custoAvaliacao;
	Rota rotaA,rotaB;
	
	AvaliadorCusto avaliadorCusto;
	AvaliadorFac avaliadorFac;
	ExecutaMovimento executaMovimento;
	No solucao[];
	int limiteAdj;
	BuscaLocalIntra buscaLocalIntra;
	double epsilon;
	int contAtivos;
	
	public Factibilizador(Instancia instancia,Config config, BuscaLocalIntra buscaLocalIntra)
	{
		this.avaliadorCusto=new AvaliadorCusto(instancia);
		this.avaliadorFac=new AvaliadorFac();
		this.executaMovimento=new ExecutaMovimento(instancia);
		this.melhoras=new NoPosMel[instancia.getNumRotasMax()*(instancia.getNumRotasMax()-1)/2];
		this.matrixMelhoras=new NoPosMel[instancia.getNumRotasMax()][instancia.getNumRotasMax()];
		
		for (int i = 0; i < matrixMelhoras.length; i++)
		{
			for (int j = 0; j < matrixMelhoras.length; j++) 
			{
				matrixMelhoras[i][j]=new NoPosMel(avaliadorCusto);
				matrixMelhoras[j][i]=matrixMelhoras[i][j];
			}
		}
		
		this.limiteAdj=Math.min(config.getLimiteAdj(), instancia.getSize()-1);
		
		this.buscaLocalIntra=buscaLocalIntra;
		this.epsilon=config.getEpsilon();
	}
	
	private boolean factivel() 
	{
		for (int i = 0; i < NumRotas; i++)
		{
			if(rotas[i].espacoLivre()<0)
				return false;
		}
		return true;
	}
	
	private void setSolucao(Solucao solucao) 
	{
		this.NumRotas=solucao.NumRotas;
		this.solucao=solucao.getSolucao();
		this.f=solucao.f;
		this.rotas=solucao.rotas;
	}

	private void passaResultado(Solucao solucao) 
	{
		solucao.NumRotas=this.NumRotas;
		solucao.f=this.f;
	}

	public void factibilizar(Solucao solucao)
	{
		setSolucao(solucao);
		boolean factivel=false;
		
		do
		{
			topMelhores=0;
			for (int i = 0; i < NumRotas; i++) 
				rotas[i].setDemandaAcumulada();
			
			for (int i = 0; i < NumRotas; i++) 
			{
				if(rotas[i].alterada)
					varrerRotas(rotas[i]); 
			}
			
			if(topMelhores>0)
				executa();

			if(factivel())
			{
				BuscaLocalIntra();
				
				passaResultado(solucao);
				solucao.removeRotasVazias();
				factivel=true;
			}
			else
			{
				NumRotas++;
				rotas[NumRotas-1].limpar();
			}
		}
		while(!factivel);
		
	}
	
	//-----------------------------------Factibilizando com o Best Improviment-------------------------------------------
	
	private void executa() 
	{
		while(topMelhores>0)
		{
			Arrays.sort(melhoras,0,topMelhores);
			
			rotaA=melhoras[0].rotaA;
			rotaB=melhoras[0].rotaB;
			f+=executaMovimento.aplicar(melhoras[0]);
			
			BuscaLocalIntra(rotaA);
			BuscaLocalIntra(rotaB);
			
			rotaA.setDemandaAcumulada();
			rotaB.setDemandaAcumulada();
			
			contAtivos=0;
			for (int k = 0; k < topMelhores; k++) 
			{
				if(melhoras[k].rotaA==rotaA||melhoras[k].rotaA==rotaB||melhoras[k].rotaB==rotaA||melhoras[k].rotaB==rotaB)
				{
					melhoras[k].limpar();
					contAtivos++;
				}
			}
	
			Arrays.sort(melhoras,0,topMelhores);
			topMelhores-=contAtivos;
			
			varrerRotas(rotaA);
			varrerRotas(rotaB);
		}
	}
	
	public void varrerRotas(Rota rota)
	{
		if(rota.getNumElements()>1)
		{
			procuraBestSHIFT(rota);
			procuraBestSWAPEstrelaKNN(rota);
			procuraBestCross(rota);
		}
	}
	
	public void calcCusto()
	{
		if(custo>=0)
			custoAvaliacao=((double)custo+1)/ganho;
		else
			custoAvaliacao=(double)custo/ganho;
	}
	
	private void procuraBestSWAPEstrelaKNN(Rota rota)
	{
		auxSai=rota.inicio.prox;
		do
		{
			if(auxSai.alterado)
			{
				for (int j = 0; j < limiteAdj; j++) 
				{
					if(auxSai.getKnn()[j]!=0&&auxSai.rota.isFactivel()^solucao[auxSai.getKnn()[j]-1].rota.isFactivel())
					{
						auxEntra=solucao[auxSai.getKnn()[j]-1];
						ganho=avaliadorFac.ganhoSWAP(auxSai, auxEntra);
						if(ganho>0)
						{
							
							bestAntNoRotaI=auxEntra.rota.findBestPositionExcetoKNN(auxSai,auxEntra,solucao);
							bestAntNoRotaJ=auxSai.rota.findBestPositionExcetoKNN(auxEntra,auxSai,solucao);
							
							custo=avaliadorCusto.custoSwapEstrela(auxSai,auxEntra,bestAntNoRotaI,bestAntNoRotaJ);
							calcCusto();
							
							noMelhora=matrixMelhoras[auxSai.rota.nomeRota][auxEntra.rota.nomeRota];
							
							if(custoAvaliacao<noMelhora.custoAvaliacao)
							{
								if(!noMelhora.ativo)
									melhoras[topMelhores++]=noMelhora;
								
								noMelhora.setNoMelhora(custo, 25, auxSai, auxEntra,bestAntNoRotaI, bestAntNoRotaJ, custoAvaliacao, ganho);
							}
						}
					}
				}
			}
			auxSai=auxSai.prox;
		}
		while(auxSai!=rota.inicio);
	}
	
	private void procuraBestSHIFT(Rota rota)
	{
		auxSai=rota.inicio.prox;
		do
		{
			if(auxSai.alterado)
			{
				for (int i = 0; i < NumRotas; i++) 
				{
					if(!auxSai.rota.isFactivel()&&rotas[i].isFactivel())
					{
						auxEntra=rotas[i].inicio;
						ganho=avaliadorFac.ganhoSHIFT(auxSai, auxEntra);
						if(ganho>0)
						{
							custo=avaliadorCusto.custoSHIFT(auxSai, auxEntra);
							calcCusto();
							noMelhora=matrixMelhoras[auxSai.rota.nomeRota][auxEntra.rota.nomeRota];
							
							if(custoAvaliacao<noMelhora.custoAvaliacao)
							{
								if(!noMelhora.ativo)
									melhoras[topMelhores++]=noMelhora;
								
								noMelhora.setNoMelhora(custo, 7, auxSai, auxEntra,custoAvaliacao,ganho);
							}
						}
					}
				}
				
				for (int j = 0; j < limiteAdj; j++) 
				{
					if(auxSai.getKnn()[j]!=0&&!auxSai.rota.isFactivel()&&solucao[auxSai.getKnn()[j]-1].rota.isFactivel())
					{
						auxEntra=solucao[auxSai.getKnn()[j]-1];
						ganho=avaliadorFac.ganhoSHIFT(auxSai, auxEntra);
						if(ganho>0)
						{
							custo=avaliadorCusto.custoSHIFT(auxSai, auxEntra);
							calcCusto();
							noMelhora=matrixMelhoras[auxSai.rota.nomeRota][auxEntra.rota.nomeRota];
							
							if(custoAvaliacao<noMelhora.custoAvaliacao)
							{
								if(!noMelhora.ativo)
									melhoras[topMelhores++]=noMelhora;
								
								noMelhora.setNoMelhora(custo, 7, auxSai, auxEntra,custoAvaliacao,ganho);
							}
						}
					}
					
					if(auxSai.getKnn()[j]!=0&&auxSai.rota.isFactivel()&&!solucao[auxSai.getKnn()[j]-1].rota.isFactivel())
					{
						auxEntra=solucao[auxSai.getKnn()[j]-1];
						ganho=avaliadorFac.ganhoSHIFT(auxEntra, auxSai);
						if(ganho>0)
						{
							custo=avaliadorCusto.custoSHIFT(auxEntra, auxSai);
							calcCusto();
							
							noMelhora=matrixMelhoras[auxSai.rota.nomeRota][auxEntra.rota.nomeRota];
							
							if(custoAvaliacao<noMelhora.custoAvaliacao)
							{
								if(!noMelhora.ativo)
									melhoras[topMelhores++]=noMelhora;
								
								noMelhora.setNoMelhora(custo, 7, auxEntra ,auxSai ,custoAvaliacao,ganho);
							}
						}
					}
				}
			}
			auxSai=auxSai.prox;
		}
		while(auxSai!=rota.inicio);
	}
	
	
	private void procuraBestCross(Rota rota)
	{
		auxSai=rota.inicio;
		do
		{
			if(auxSai.alterado)
			{
				for (int i = 0; i < NumRotas; i++) 
				{
					if(auxSai.rota.isFactivel()^rotas[i].isFactivel())
					{
						auxEntra=rotas[i].inicio;
						ganho=avaliadorFac.ganhoCross(auxSai, auxEntra);
						if(ganho>0)
						{
							custo=avaliadorCusto.custoCross(auxSai, auxEntra);
							calcCusto();
							
							noMelhora=matrixMelhoras[auxSai.rota.nomeRota][auxEntra.rota.nomeRota];
							
							if(custoAvaliacao<noMelhora.custoAvaliacao)
							{
								if(!noMelhora.ativo)
									melhoras[topMelhores++]=noMelhora;
								
								noMelhora.setNoMelhora(custo, 11, auxSai, auxEntra,custoAvaliacao,ganho);
							}
							
						}
						ganho=avaliadorFac.ganhoCrossInvertido(auxSai, auxEntra);
						if(ganho>0)
						{
							custo=avaliadorCusto.custoCrossInvertido(auxSai, auxEntra);
							calcCusto();
							
							noMelhora=matrixMelhoras[auxSai.rota.nomeRota][auxEntra.rota.nomeRota];
							
							if(custoAvaliacao<noMelhora.custoAvaliacao)
							{
								if(!noMelhora.ativo)
									melhoras[topMelhores++]=noMelhora;
								
								noMelhora.setNoMelhora(custo, 12, auxSai, auxEntra,custoAvaliacao,ganho);
							}
						}
					}
				}
				
				for (int j = 0; j < limiteAdj; j++) 
				{
					if(auxSai.getKnn()[j]!=0&&auxSai.rota.isFactivel()^solucao[auxSai.getKnn()[j]-1].rota.isFactivel())
					{
						auxEntra=solucao[auxSai.getKnn()[j]-1];
						ganho=avaliadorFac.ganhoCross(auxSai, auxEntra);
						if(ganho>0)
						{
							custo=avaliadorCusto.custoCross(auxSai, auxEntra);
							calcCusto();
							noMelhora=matrixMelhoras[auxSai.rota.nomeRota][auxEntra.rota.nomeRota];
							
							if(custoAvaliacao<noMelhora.custoAvaliacao)
							{
								if(!noMelhora.ativo)
									melhoras[topMelhores++]=noMelhora;
								
								noMelhora.setNoMelhora(custo, 11, auxSai, auxEntra,custoAvaliacao,ganho);
							}
						}
						ganho=avaliadorFac.ganhoCrossInvertido(auxSai, auxEntra);
						if(ganho>0)
						{
							custo=avaliadorCusto.custoCrossInvertido(auxSai, auxEntra);
							calcCusto();
							noMelhora=matrixMelhoras[auxSai.rota.nomeRota][auxEntra.rota.nomeRota];
							
							if(custoAvaliacao<noMelhora.custoAvaliacao)
							{
								if(!noMelhora.ativo)
									melhoras[topMelhores++]=noMelhora;
								
								noMelhora.setNoMelhora(custo, 12, auxSai, auxEntra,custoAvaliacao,ganho);
							}
						}
					}
				}
			}
			auxSai=auxSai.prox;
		}
		while(auxSai!=rota.inicio);
	}
	
	private void BuscaLocalIntra(Rota rota)
	{
		f+=buscaLocalIntra.buscaLocalIntra(rota, solucao);
	}
	
	private void BuscaLocalIntra()
	{
		for (int i = 0; i < NumRotas; i++)
		{
			if(rotas[i].alterada)
				f+=buscaLocalIntra.buscaLocalIntra(rotas[i], solucao);
		}
	}
	
}
