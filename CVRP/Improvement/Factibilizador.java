package Improvement;

import java.util.Arrays;

import Data.Instance;
import Evaluators.CostEvaluation;
import Evaluators.FeasibilityEvaluation;
import Evaluators.ExecuteMovement;
import Evaluators.TipoMovimento;
import SearchMethod.Config;
import Solution.Node;
import Solution.Route;
import Solution.Solution;

public class Factibilizador 
{
	private Route routes[];
	private  CandidateNode melhoras[];
	private  CandidateNode matrixMelhoras[][];

	private CandidateNode noMelhora;

	private int topMelhores=0;
	private int numRoutes;
	
	Node bestPrevNoRouteI,bestPrevNoRouteJ;

	double f=0;
	
	Node auxSai,auxEntra;
	int gain;
	double custo;
	double custoAvaliacao;
	Route routeA,routeB;
	
	CostEvaluation avaliadorCusto;
	FeasibilityEvaluation feasibilityEvaluation;
	ExecuteMovement executaMovimento;
	Node solution[];
	int limiteAdj;
	BuscaLocalIntra buscaLocalIntra;
	double epsilon;
	int contAtivos;
	
	public Factibilizador(Instance instance,Config config, BuscaLocalIntra buscaLocalIntra)
	{
		this.avaliadorCusto=new CostEvaluation(instance);
		this.feasibilityEvaluation=new FeasibilityEvaluation();
		this.executaMovimento=new ExecuteMovement(instance);
		this.melhoras=new CandidateNode[instance.getMaxNumberRoutes()*(instance.getMaxNumberRoutes()-1)/2];
		this.matrixMelhoras=new CandidateNode[instance.getMaxNumberRoutes()][instance.getMaxNumberRoutes()];
		
		for (int i = 0; i < matrixMelhoras.length; i++)
		{
			for (int j = 0; j < matrixMelhoras.length; j++) 
			{
				matrixMelhoras[i][j]=new CandidateNode(avaliadorCusto);
				matrixMelhoras[j][i]=matrixMelhoras[i][j];
			}
		}
		
		this.limiteAdj=Math.min(config.getVarphi(), instance.getSize()-1);
		
		this.buscaLocalIntra=buscaLocalIntra;
		this.epsilon=config.getEpsilon();
	}
	
	private boolean factivel() 
	{
		for (int i = 0; i < numRoutes; i++)
		{
			if(routes[i].availableCapacity()<0)
				return false;
		}
		return true;
	}
	
	private void setSolution(Solution solution) 
	{
		this.numRoutes=solution.numRoutes;
		this.solution=solution.getSolution();
		this.f=solution.f;
		this.routes=solution.routes;
	}

	private void passaResultado(Solution solution) 
	{
		solution.numRoutes=this.numRoutes;
		solution.f=this.f;
	}

	public void factibilizar(Solution solution)
	{
		setSolution(solution);
		boolean factivel=false;
		
		do
		{
			topMelhores=0;
			for (int i = 0; i < numRoutes; i++) 
				routes[i].setDemandaAcumulada();
			
			for (int i = 0; i < numRoutes; i++) 
			{
				if(routes[i].alterada)
					varrerRoutes(routes[i]); 
			}
			
			if(topMelhores>0)
				executa();

			if(factivel())
			{
				BuscaLocalIntra();
				
				passaResultado(solution);
				solution.removeRoutesVazias();
				factivel=true;
			}
			else
			{
				numRoutes++;
				routes[numRoutes-1].limpar();
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
			
			routeA=melhoras[0].routeA;
			routeB=melhoras[0].routeB;
			f+=executaMovimento.aplicar(melhoras[0]);
			
			BuscaLocalIntra(routeA);
			BuscaLocalIntra(routeB);
			
			routeA.setDemandaAcumulada();
			routeB.setDemandaAcumulada();
			
			contAtivos=0;
			for (int k = 0; k < topMelhores; k++) 
			{
				if(melhoras[k].routeA==routeA||melhoras[k].routeA==routeB||melhoras[k].routeB==routeA||melhoras[k].routeB==routeB)
				{
					melhoras[k].limpar();
					contAtivos++;
				}
			}
	
			Arrays.sort(melhoras,0,topMelhores);
			topMelhores-=contAtivos;
			
			varrerRoutes(routeA);
			varrerRoutes(routeB);
		}
	}
	
	public void varrerRoutes(Route route)
	{
		if(route.getNumElements()>1)
		{
			procuraBestSHIFT(route);
			procuraBestSWAPEstrelaKNN(route);
			procuraBestCross(route);
		}
	}
	
	public void calcCusto()
	{
		if(custo>=0)
			custoAvaliacao=((double)custo+1)/gain;
		else
			custoAvaliacao=(double)custo/gain;
	}
	
	private void procuraBestSWAPEstrelaKNN(Route route)
	{
		auxSai=route.inicio.next;
		do
		{
			if(auxSai.alterado)
			{
				for (int j = 0; j < limiteAdj; j++) 
				{
					if(auxSai.getKnn()[j]!=0&&auxSai.route.isFactivel()^solution[auxSai.getKnn()[j]-1].route.isFactivel())
					{
						auxEntra=solution[auxSai.getKnn()[j]-1];
						gain=feasibilityEvaluation.gainSWAP(auxSai, auxEntra);
						if(gain>0)
						{
							
							bestPrevNoRouteI=auxEntra.route.findBestPositionExcetoKNN(auxSai,auxEntra,solution);
							bestPrevNoRouteJ=auxSai.route.findBestPositionExcetoKNN(auxEntra,auxSai,solution);
							
							custo=avaliadorCusto.costSwapStar(auxSai,auxEntra,bestPrevNoRouteI,bestPrevNoRouteJ);
							calcCusto();
							
							noMelhora=matrixMelhoras[auxSai.route.nomeRoute][auxEntra.route.nomeRoute];
							
							if(custoAvaliacao<noMelhora.custoAvaliacao)
							{
								if(!noMelhora.ativo)
									melhoras[topMelhores++]=noMelhora;
								
								noMelhora.setNoMelhora(custo, TipoMovimento.SWAPEstrela, auxSai, auxEntra,bestPrevNoRouteI, bestPrevNoRouteJ, custoAvaliacao, gain);
							}
						}
					}
				}
			}
			auxSai=auxSai.next;
		}
		while(auxSai!=route.inicio);
	}
	
	private void procuraBestSHIFT(Route route)
	{
		auxSai=route.inicio.next;
		do
		{
			if(auxSai.alterado)
			{
				for (int i = 0; i < numRoutes; i++) 
				{
					if(!auxSai.route.isFactivel()&&routes[i].isFactivel())
					{
						auxEntra=routes[i].inicio;
						gain=feasibilityEvaluation.gainSHIFT(auxSai, auxEntra);
						if(gain>0)
						{
							custo=avaliadorCusto.costSHIFT(auxSai, auxEntra);
							calcCusto();
							noMelhora=matrixMelhoras[auxSai.route.nomeRoute][auxEntra.route.nomeRoute];
							
							if(custoAvaliacao<noMelhora.custoAvaliacao)
							{
								if(!noMelhora.ativo)
									melhoras[topMelhores++]=noMelhora;
								
								noMelhora.setNoMelhora(custo, TipoMovimento.SHIFT, auxSai, auxEntra,custoAvaliacao,gain);
							}
						}
					}
				}
				
				for (int j = 0; j < limiteAdj; j++) 
				{
					if(auxSai.getKnn()[j]!=0&&!auxSai.route.isFactivel()&&solution[auxSai.getKnn()[j]-1].route.isFactivel())
					{
						auxEntra=solution[auxSai.getKnn()[j]-1];
						gain=feasibilityEvaluation.gainSHIFT(auxSai, auxEntra);
						if(gain>0)
						{
							custo=avaliadorCusto.costSHIFT(auxSai, auxEntra);
							calcCusto();
							noMelhora=matrixMelhoras[auxSai.route.nomeRoute][auxEntra.route.nomeRoute];
							
							if(custoAvaliacao<noMelhora.custoAvaliacao)
							{
								if(!noMelhora.ativo)
									melhoras[topMelhores++]=noMelhora;
								
								noMelhora.setNoMelhora(custo, TipoMovimento.SHIFT, auxSai, auxEntra,custoAvaliacao,gain);
							}
						}
					}
					
					if(auxSai.getKnn()[j]!=0&&auxSai.route.isFactivel()&&!solution[auxSai.getKnn()[j]-1].route.isFactivel())
					{
						auxEntra=solution[auxSai.getKnn()[j]-1];
						gain=feasibilityEvaluation.gainSHIFT(auxEntra, auxSai);
						if(gain>0)
						{
							custo=avaliadorCusto.costSHIFT(auxEntra, auxSai);
							calcCusto();
							
							noMelhora=matrixMelhoras[auxSai.route.nomeRoute][auxEntra.route.nomeRoute];
							
							if(custoAvaliacao<noMelhora.custoAvaliacao)
							{
								if(!noMelhora.ativo)
									melhoras[topMelhores++]=noMelhora;
								
								noMelhora.setNoMelhora(custo, TipoMovimento.SHIFT, auxEntra ,auxSai ,custoAvaliacao,gain);
							}
						}
					}
				}
			}
			auxSai=auxSai.next;
		}
		while(auxSai!=route.inicio);
	}
	
	
	private void procuraBestCross(Route route)
	{
		auxSai=route.inicio;
		do
		{
			if(auxSai.alterado)
			{
				for (int i = 0; i < numRoutes; i++) 
				{
					if(auxSai.route.isFactivel()^routes[i].isFactivel())
					{
						auxEntra=routes[i].inicio;
						gain=feasibilityEvaluation.gainCross(auxSai, auxEntra);
						if(gain>0)
						{
							custo=avaliadorCusto.costCross(auxSai, auxEntra);
							calcCusto();
							
							noMelhora=matrixMelhoras[auxSai.route.nomeRoute][auxEntra.route.nomeRoute];
							
							if(custoAvaliacao<noMelhora.custoAvaliacao)
							{
								if(!noMelhora.ativo)
									melhoras[topMelhores++]=noMelhora;
								
								noMelhora.setNoMelhora(custo, TipoMovimento.Cross, auxSai, auxEntra,custoAvaliacao,gain);
							}
							
						}
						gain=feasibilityEvaluation.gainCrossInverted(auxSai, auxEntra);
						if(gain>0)
						{
							custo=avaliadorCusto.inversedCostCross(auxSai, auxEntra);
							calcCusto();
							
							noMelhora=matrixMelhoras[auxSai.route.nomeRoute][auxEntra.route.nomeRoute];
							
							if(custoAvaliacao<noMelhora.custoAvaliacao)
							{
								if(!noMelhora.ativo)
									melhoras[topMelhores++]=noMelhora;
								
								noMelhora.setNoMelhora(custo, TipoMovimento.CrossInverted, auxSai, auxEntra,custoAvaliacao,gain);
							}
						}
					}
				}
				
				for (int j = 0; j < limiteAdj; j++) 
				{
					if(auxSai.getKnn()[j]!=0&&auxSai.route.isFactivel()^solution[auxSai.getKnn()[j]-1].route.isFactivel())
					{
						auxEntra=solution[auxSai.getKnn()[j]-1];
						gain=feasibilityEvaluation.gainCross(auxSai, auxEntra);
						if(gain>0)
						{
							custo=avaliadorCusto.costCross(auxSai, auxEntra);
							calcCusto();
							noMelhora=matrixMelhoras[auxSai.route.nomeRoute][auxEntra.route.nomeRoute];
							
							if(custoAvaliacao<noMelhora.custoAvaliacao)
							{
								if(!noMelhora.ativo)
									melhoras[topMelhores++]=noMelhora;
								
								noMelhora.setNoMelhora(custo, TipoMovimento.Cross, auxSai, auxEntra,custoAvaliacao,gain);
							}
						}
						gain=feasibilityEvaluation.gainCrossInverted(auxSai, auxEntra);
						if(gain>0)
						{
							custo=avaliadorCusto.inversedCostCross(auxSai, auxEntra);
							calcCusto();
							noMelhora=matrixMelhoras[auxSai.route.nomeRoute][auxEntra.route.nomeRoute];
							
							if(custoAvaliacao<noMelhora.custoAvaliacao)
							{
								if(!noMelhora.ativo)
									melhoras[topMelhores++]=noMelhora;
								
								noMelhora.setNoMelhora(custo, TipoMovimento.CrossInverted, auxSai, auxEntra,custoAvaliacao,gain);
							}
						}
					}
				}
			}
			auxSai=auxSai.next;
		}
		while(auxSai!=route.inicio);
	}
	
	private void BuscaLocalIntra(Route route)
	{
		f+=buscaLocalIntra.buscaLocalIntra(route, solution);
	}
	
	private void BuscaLocalIntra()
	{
		for (int i = 0; i < numRoutes; i++)
		{
			if(routes[i].alterada)
				f+=buscaLocalIntra.buscaLocalIntra(routes[i], solution);
		}
	}
	
}
