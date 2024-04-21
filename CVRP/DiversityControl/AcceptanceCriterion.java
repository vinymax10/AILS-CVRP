package DiversityControl;

import Auxiliary.Mean;
import Data.Instance;
import SearchMethod.Config;
import SearchMethod.StoppingCriterionType;
import Solution.Solution;

public class AcceptanceCriterion 
{
	double thresholdOF;
	double eta,etaMin,etaMax;
	long ini;
	double alpha=1;
	int globalIterator=0;
	StoppingCriterionType stoppingCriterionType;
	double executionMaximumLimit;
	int numIterUpdate;
	double upperLimit=Integer.MAX_VALUE,updatedUpperLimit=Integer.MAX_VALUE;
	Mean averageLSfunction;

	public AcceptanceCriterion(Instance instance, Config config, Double executionMaximumLimit)
	{
		this.eta=config.getEtaMax();
		this.etaMin=config.getEtaMin();
		this.etaMax=config.getEtaMax();
		this.stoppingCriterionType=config.getStoppingCriterionType();
		this.averageLSfunction=new Mean(config.getGamma());
		this.numIterUpdate=config.getGamma();
		this.executionMaximumLimit=executionMaximumLimit;
	}
	
	public boolean aceitaSolucao(Solution solution)
	{
		if(globalIterator==0)
			ini=System.currentTimeMillis();
		
		averageLSfunction.setValor(solution.f);
		
		globalIterator++;
		
		if(globalIterator%(numIterUpdate)==0)
		{
			upperLimit=updatedUpperLimit;
			updatedUpperLimit=Integer.MAX_VALUE;
		}
		
		if(solution.f<updatedUpperLimit)
			updatedUpperLimit=solution.f;
			
		if(solution.f<upperLimit)
			upperLimit=solution.f;
		
//		--------------------------------------------
		
		switch(stoppingCriterionType)
		{
			case Iteration: 	if(globalIterator%numIterUpdate==0)
							{
								alpha=Math.pow(etaMin/etaMax, (double) 1/executionMaximumLimit);
							}
							break;
							
			case Time: 	if(globalIterator%numIterUpdate==0)
								{
									double maxTime=executionMaximumLimit;
									double current=(double)(System.currentTimeMillis()-ini)/1000;
									double timePercentage=current/maxTime;
									double total=(double)globalIterator/timePercentage;
									
									alpha=Math.pow(etaMin/etaMax, (double) 1/total);
								}
								break;
			default:
				break;
								
		}
		
		eta*=alpha;
		eta=Math.max(eta, etaMin);
		
		thresholdOF=(int)(upperLimit+(eta*(averageLSfunction.getMediaDinam()-upperLimit)));
		if(solution.f<=thresholdOF)
			return true;
		else
			return false;
	}
	
	public double getEta() {
		return eta;
	}

	public double getThresholdOF() {
		return thresholdOF;
	}

	public void setEta(double eta) {
		this.eta = eta;
	}
	
	public void setIdealFlow(double idealFlow) {}
}
