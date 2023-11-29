package DiversityControl;

import SearchMethod.Config;
import SearchMethod.TipoCriterioParada;

public class AjusteDist 
{
	double ebDistDPM=0.1;
	int distMMin;
	int distMMax;
	int iterador;
	long ini;
	double limiteMaximoExecucao;
	double alfa=1;
	TipoCriterioParada tipoCriterioParada;
	DistIdeal distIdeal;

	public AjusteDist(DistIdeal distIdeal,Config config,double limiteMaximoExecucao) 
	{
		this.distIdeal=distIdeal;
		this.limiteMaximoExecucao=limiteMaximoExecucao;
		this.distMMin=config.getDMin();
		this.distMMax=config.getDMax();
		this.distIdeal.distIdeal=distMMax;
		this.tipoCriterioParada=config.getTipoCriterioParada();
	}

	public void ajusteDist()
	{
		if(iterador==0)
			ini=System.currentTimeMillis();
		
		iterador++;
		
		switch(tipoCriterioParada)
		{
			case Iteration: 	ajusteIteracao(); break;
			case Time: ajusteTempo(); break;
			default:
				break;
								
		}
		
		distIdeal.distIdeal*=alfa;
		distIdeal.distIdeal= Math.min(distMMax, Math.max(distIdeal.distIdeal, distMMin));
		
	}
	
	private void ajusteIteracao()
	{
		alfa=Math.pow((double)distMMin/(double)distMMax, (double) 1/limiteMaximoExecucao);
	}
	
	private void ajusteTempo()
	{
		double atual=(double)(System.currentTimeMillis()-ini)/1000;
		double porcentagemTempo=atual/limiteMaximoExecucao;
		double total=(double)iterador/porcentagemTempo;
		alfa=Math.pow((double)distMMin/(double)distMMax, (double) 1/total);
	}
}
