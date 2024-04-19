package Improvement;

import java.util.Arrays;

import Data.Instance;
import Evaluators.AvaliadorCusto;
import Evaluators.AvaliadorFac;
import Evaluators.ExecutaMovimento;
import SearchMethod.Config;
import Solution.Node;
import Solution.Rota;
import Solution.Solution;


public class BuscaLocal 
{
	private Rota rotas[];
	private  NoPosMel melhoras[];
	private  NoPosMel matrixMelhoras[][];
	private NoPosMel noMelhora;
	private int topMelhores=0;
	private int NumRotas;
	
	Node bestAntNoRotaI,bestAntNoRotaJ;

	double f=0;
	
	double custo;
	Node auxRotaI,auxRotaJ;
	Rota rotaA,rotaB;
	AvaliadorCusto avaliadorCusto;
	AvaliadorFac avaliadorFac;
	ExecutaMovimento executaMovimento;
	Node solucao[];
	int limiteAdj;
	BuscaLocalIntra buscaLocalIntra;
	double epsilon;
	int contAtivos;
	
	public BuscaLocal(Instance instancia,Config config, BuscaLocalIntra buscaLocalIntra)
	{
		this.avaliadorCusto=new AvaliadorCusto(instancia);
		this.avaliadorFac=new AvaliadorFac();
		this.executaMovimento=new ExecutaMovimento(instancia);
		this.melhoras=new NoPosMel[instancia.getNumRotasMax()*(instancia.getNumRotasMax()-1)/2];
		this.matrixMelhoras=new NoPosMel[instancia.getNumRotasMax()][instancia.getNumRotasMax()];
		
		for (int i = 0; i < matrixMelhoras.length; i++)
		{
			for (int j = i+1; j < matrixMelhoras.length; j++) 
			{
				matrixMelhoras[i][j]=new NoPosMel(avaliadorCusto);
				matrixMelhoras[j][i]=matrixMelhoras[i][j];
			}
		}
		
		this.limiteAdj=Math.min(config.getVarphi(), instancia.getSize()-1);
		this.buscaLocalIntra=buscaLocalIntra;
		this.epsilon=config.getEpsilon();
	}
	
	private void setSolucao(Solution solucao) 
	{
		this.NumRotas=solucao.NumRotas;
		this.solucao=solucao.getSolution();
		this.f=solucao.f;
		this.rotas=solucao.rotas;
	}

	private void passaResultado(Solution solucao) 
	{
		solucao.NumRotas=this.NumRotas;
		solucao.f=this.f;
	}

	public void buscaLocal(Solution solucao,boolean removerRotasVazias)
	{
		setSolucao(solucao);
		topMelhores=0;
			
		for (int i = 0; i < NumRotas; i++) 
			rotas[i].setDemandaAcumulada();
		
		for (int i = 0; i < NumRotas; i++) 
		{
			if(rotas[i].numElements>1&&rotas[i].alterada)
				varrerRotas(rotas[i]);
		}
		
		if(topMelhores>0)
			executa();
		
		passaResultado(solucao);
		if(removerRotasVazias)
			solucao.removeRotasVazias();
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
				if(melhoras[k].rotaA==rotaA||melhoras[k].rotaB==rotaA||melhoras[k].rotaA==rotaB||melhoras[k].rotaB==rotaB)
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
		if(rota.numElements>1)
		{
			procuraBestSHIFT(rota);
			procuraBestSWAPEstrelaKNN(rota);
			procuraBestCross(rota);
		}
	}
	
	private void procuraBestSWAPEstrelaKNN(Rota rota)
	{
		auxRotaI=rota.inicio.next;
		do
		{
			if(auxRotaI.alterado)
			{
				for (int j = 0; j < limiteAdj; j++) 
				{
					if(auxRotaI.getKnn()[j]!=0&&solucao[auxRotaI.getKnn()[j]-1].rota!=rota)
					{
						auxRotaJ=solucao[auxRotaI.getKnn()[j]-1];
						if(avaliadorFac.ganhoSWAP(auxRotaI, auxRotaJ)>=0)
						{
							bestAntNoRotaI=auxRotaJ.rota.findBestPositionExcetoKNN(auxRotaI,auxRotaJ,solucao);
							bestAntNoRotaJ=auxRotaI.rota.findBestPositionExcetoKNN(auxRotaJ,auxRotaI,solucao);
							
							custo=avaliadorCusto.custoSwapEstrela(auxRotaI,auxRotaJ,bestAntNoRotaI,bestAntNoRotaJ);
							noMelhora=matrixMelhoras[auxRotaI.rota.nomeRota][auxRotaJ.rota.nomeRota];
							
							if(((custo-noMelhora.custo)<-epsilon)&&((custo-0)<-epsilon))
							{
								if(!noMelhora.ativo)
									melhoras[topMelhores++]=noMelhora;
								
								noMelhora.setNoMelhora(custo, 25, auxRotaI, auxRotaJ,bestAntNoRotaI, bestAntNoRotaJ, custo);
							}
						}
					}
				}
			}
			
			auxRotaI=auxRotaI.next;
		}
		while(auxRotaI!=rota.inicio);
	}
	
	private void procuraBestSHIFT(Rota rota)
	{
		auxRotaI=rota.inicio.next;
		do
		{
			if(auxRotaI.alterado)
			{
				for (int j = 0; j < limiteAdj; j++) 
				{
					if(auxRotaI.getKnn()[j]==0)
					{
						for (int i = 0; i < NumRotas; i++) 
						{
							if(rotas[i]!=rota)
							{
								auxRotaJ=rotas[i].inicio;
								if(avaliadorFac.ganhoSHIFT(auxRotaI, auxRotaJ)>=0)
								{
									custo=avaliadorCusto.custoSHIFT(auxRotaI,auxRotaJ);
									noMelhora=matrixMelhoras[auxRotaI.rota.nomeRota][auxRotaJ.rota.nomeRota];
									
									if(((custo-noMelhora.custo)<-epsilon)&&((custo-0)<-epsilon))
									{
										if(!noMelhora.ativo)
											melhoras[topMelhores++]=noMelhora;
										
										noMelhora.setNoMelhora(custo, 7, auxRotaI, auxRotaJ,custo);
									}
								}
							}
						}
					}
					else if(solucao[auxRotaI.getKnn()[j]-1].rota!=rota)
					{
						auxRotaJ=solucao[auxRotaI.getKnn()[j]-1];
						if(avaliadorFac.ganhoSHIFT(auxRotaI, auxRotaJ)>=0)
						{
							custo=avaliadorCusto.custoSHIFT(auxRotaI,auxRotaJ);
							noMelhora=matrixMelhoras[auxRotaI.rota.nomeRota][auxRotaJ.rota.nomeRota];
							
							if(((custo-noMelhora.custo)<-epsilon)&&((custo-0)<-epsilon))
							{
								if(!noMelhora.ativo)
									melhoras[topMelhores++]=noMelhora;
								
								noMelhora.setNoMelhora(custo, 7, auxRotaI, auxRotaJ,custo);
							}
						}
						
						if(avaliadorFac.ganhoSHIFT(auxRotaJ, auxRotaI)>=0)
						{
							custo=avaliadorCusto.custoSHIFT(auxRotaJ, auxRotaI);
							noMelhora=matrixMelhoras[auxRotaI.rota.nomeRota][auxRotaJ.rota.nomeRota];
							
							if(((custo-noMelhora.custo)<-epsilon)&&((custo-0)<-epsilon))
							{
								if(!noMelhora.ativo)
									melhoras[topMelhores++]=noMelhora;
								
								noMelhora.setNoMelhora(custo, 7, auxRotaJ,auxRotaI, custo);
							}
						}
					}
				}
			}
			
			auxRotaI=auxRotaI.next;
		}
		while(auxRotaI!=rota.inicio);
	}
	
	private void procuraBestCross(Rota rota)
	{
		auxRotaI=rota.inicio;
		do
		{
			if(auxRotaI.alterado)
			{
				for (int j = 0; j < limiteAdj; j++) 
				{
					if(auxRotaI.getKnn()[j]==0)
					{
						for (int i = 0; i < NumRotas; i++) 
						{
							if(rotas[i]!=rota)
							{
								auxRotaJ=rotas[i].inicio;
								if(avaliadorFac.ganhoCross(auxRotaI, auxRotaJ)>=0)
								{
									custo=avaliadorCusto.custoCross(auxRotaI,auxRotaJ);
									noMelhora=matrixMelhoras[auxRotaI.rota.nomeRota][auxRotaJ.rota.nomeRota];
									
									if(((custo-noMelhora.custo)<-epsilon)&&((custo-0)<-epsilon))
									{
										if(!noMelhora.ativo)
											melhoras[topMelhores++]=noMelhora;
										
										noMelhora.setNoMelhora(custo, 11, auxRotaI, auxRotaJ,custo);
									}
								}
								
								if(avaliadorFac.ganhoCrossInvertido(auxRotaI, auxRotaJ)>=0)
								{
									custo=avaliadorCusto.custoCrossInvertido(auxRotaI,auxRotaJ);
									noMelhora=matrixMelhoras[auxRotaI.rota.nomeRota][auxRotaJ.rota.nomeRota];
									
									if(((custo-noMelhora.custo)<-epsilon)&&((custo-0)<-epsilon))
									{
										if(!noMelhora.ativo)
											melhoras[topMelhores++]=noMelhora;
										
										noMelhora.setNoMelhora(custo, 12, auxRotaI, auxRotaJ,custo);
									}
								}
							}
						}
					}
					else if(solucao[auxRotaI.getKnn()[j]-1].rota!=rota)
					{
						auxRotaJ=solucao[auxRotaI.getKnn()[j]-1];
						if(avaliadorFac.ganhoCross(auxRotaI, auxRotaJ)>=0)
						{
							custo=avaliadorCusto.custoCross(auxRotaI,auxRotaJ);
							noMelhora=matrixMelhoras[auxRotaI.rota.nomeRota][auxRotaJ.rota.nomeRota];
							
							if(((custo-noMelhora.custo)<-epsilon)&&((custo-0)<-epsilon))
							{
								if(!noMelhora.ativo)
									melhoras[topMelhores++]=noMelhora;
								
								noMelhora.setNoMelhora(custo, 11, auxRotaI, auxRotaJ,custo);
							}
						}
						if(avaliadorFac.ganhoCrossInvertido(auxRotaI, auxRotaJ)>=0)
						{
							custo=avaliadorCusto.custoCrossInvertido(auxRotaI,auxRotaJ);
							noMelhora=matrixMelhoras[auxRotaI.rota.nomeRota][auxRotaJ.rota.nomeRota];
							
							if(((custo-noMelhora.custo)<-epsilon)&&((custo-0)<-epsilon))
							{
								if(!noMelhora.ativo)
									melhoras[topMelhores++]=noMelhora;
								
								noMelhora.setNoMelhora(custo, 12, auxRotaI, auxRotaJ,custo);
							}
						}
					}
				}
			}
			
			auxRotaI=auxRotaI.next;
		}
		while(auxRotaI!=rota.inicio);
	}
	
	private void BuscaLocalIntra(Rota rota)
	{
		f+=buscaLocalIntra.buscaLocalIntra(rota, solucao);
	}
	
}
