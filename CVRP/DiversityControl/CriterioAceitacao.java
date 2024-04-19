package DiversityControl;

import Auxiliary.Mean;
import Data.Instance;
import SearchMethod.Config;
import SearchMethod.TipoCriterioParada;
import Solution.Solution;

public class CriterioAceitacao 
{
	double limiarF;
	double eta,etaMin,etaMax;
	long ini;
	double alfa=1;
	int iteradorGlobal=0;
	TipoCriterioParada tipoCriterioParada;
	double limiteMaximoExecucao;
	int numIterUpdate;
	double teto=Integer.MAX_VALUE,tetoNovo=Integer.MAX_VALUE;
	Mean mFBL;

	public CriterioAceitacao(Instance instancia, Config config, Double limiteMaximoExecucao)
	{
		this.eta=config.getEtaMax();
		this.etaMin=config.getEtaMin();
		this.etaMax=config.getEtaMax();
		this.tipoCriterioParada=config.getTipoCriterioParada();
		this.mFBL=new Mean(config.getGamma());
		this.numIterUpdate=config.getGamma();
		this.limiteMaximoExecucao=limiteMaximoExecucao;
	}
	
	public boolean aceitaSolucao(Solution solucao)
	{
		if(iteradorGlobal==0)
			ini=System.currentTimeMillis();
		
		mFBL.setValor(solucao.f);
		
		iteradorGlobal++;
		
		if(iteradorGlobal%(numIterUpdate)==0)
		{
			teto=tetoNovo;
			tetoNovo=Integer.MAX_VALUE;
		}
		
		if(solucao.f<tetoNovo)
			tetoNovo=solucao.f;
			
		if(solucao.f<teto)
			teto=solucao.f;
		
//		--------------------------------------------
		
		switch(tipoCriterioParada)
		{
			case Iteration: 	if(iteradorGlobal%numIterUpdate==0)
							{
								alfa=Math.pow(etaMin/etaMax, (double) 1/limiteMaximoExecucao);
							}
							break;
							
			case Time: 	if(iteradorGlobal%numIterUpdate==0)
								{
									double tempoMaximo=limiteMaximoExecucao;
									double atual=(double)(System.currentTimeMillis()-ini)/1000;
									double porcentagemTempo=atual/tempoMaximo;
									double total=(double)iteradorGlobal/porcentagemTempo;
									
									alfa=Math.pow(etaMin/etaMax, (double) 1/total);
								}
								break;
			default:
				break;
								
		}
		
		eta*=alfa;
		eta=Math.max(eta, etaMin);
		
		limiarF=(int)(teto+(eta*(mFBL.getMediaDinam()-teto)));
		if(solucao.f<=limiarF)
			return true;
		else
			return false;
	}
	
	public double getEta() {
		return eta;
	}

	public double getLimiarF() {
		return limiarF;
	}

	public void setEta(double eta) {
		this.eta = eta;
	}
	
	public void setFluxoIdeal(double fluxoIdeal) {}
}
