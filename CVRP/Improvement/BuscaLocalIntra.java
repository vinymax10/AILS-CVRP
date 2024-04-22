package Improvement;

import Data.Instance;
import Evaluators.CostEvaluation;
import Evaluators.ExecuteMovement;
import Evaluators.TipoMovimento;
import SearchMethod.Config;
import Solution.Node;
import Solution.Route;

public class BuscaLocalIntra 
{
	private  CandidateNode melhora;
	Node inicio;
	int numElements=0;
	TipoMovimento tipoMov;
	int iterator;
	double menorCusto;
	double prevF;
	Node auxSai,auxEntra;
	double custo;
	boolean trocou=false;
	CostEvaluation avaliadorCusto;
	ExecuteMovement executaMovimento;
	int limiteAdj;
	
	public BuscaLocalIntra(Instance instance,Config config)
	{
		this.avaliadorCusto=new CostEvaluation(instance);
		this.executaMovimento=new ExecuteMovement(instance);
		this.melhora=new CandidateNode(avaliadorCusto);
		this.limiteAdj=config.getVarphi();
	}
	
	private void setRoute(Route route,Node solution[]) 
	{
		this.prevF=route.fRoute;
		this.inicio=route.inicio;
		this.numElements=route.numElements;
	}

	public double buscaLocalIntra(Route route,Node solution[])
	{
		setRoute(route,solution);
		
		iterator=0;
		trocou=true;
		while(trocou)
		{
			iterator++;
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
							auxEntra=solution[auxSai.getKnn()[j]-1];
						
						if(auxSai.route.nomeRoute==auxEntra.route.nomeRoute)
						{
							//2Opt
							if(auxSai!=auxEntra&&auxEntra!=auxSai.next)
							{
								custo=avaliadorCusto.cost2Opt(auxSai,auxEntra);
								if(menorCusto>custo)
								{
									menorCusto=custo;
									tipoMov=TipoMovimento.TwoOpt;
									melhora.setNoMelhora(menorCusto, tipoMov, auxSai, auxEntra,0,0,menorCusto);
									trocou=true;
								}
							}
							
							//SHIFT
							if(numElements>2&&auxSai!=auxEntra&&auxSai!=auxEntra.next)
							{
								custo=avaliadorCusto.costSHIFT(auxSai,auxEntra);
								if(menorCusto>custo)
								{
									menorCusto=custo;
									tipoMov=TipoMovimento.SHIFT;
									melhora.setNoMelhora(menorCusto, tipoMov, auxSai, auxEntra,0,0,menorCusto);
									trocou=true;
								}
								
								if(auxEntra!=auxSai.next)
								{
									custo=avaliadorCusto.costSHIFT(auxEntra,auxSai);
									if(menorCusto>custo)
									{
										menorCusto=custo;
										tipoMov=TipoMovimento.SHIFT;
										melhora.setNoMelhora(menorCusto, tipoMov, auxEntra, auxSai,0,0,menorCusto);
										trocou=true;
									}
								}
							}
							
							//SWAP
							if(numElements>2&&auxEntra!=auxSai&&auxEntra.next!=auxSai)
							{
								custo=avaliadorCusto.costSWAP(auxSai,auxEntra);
								if(menorCusto>custo)
								{
									menorCusto=custo;
									tipoMov=TipoMovimento.SWAP;
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
		
		return route.fRoute-prevF;
	}
	
}
