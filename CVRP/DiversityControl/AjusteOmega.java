package DiversityControl;

import java.text.DecimalFormat;
import java.util.Random;

import Auxiliary.Media;
import Perturbation.TipoPerturbacao;
import SearchMethod.Config;

public class AjusteOmega 
{
	double omega,omegaMin,omegaMax;
	Media distBLMedia;
	int iterador=0,lastIteradorMF;
	DecimalFormat deci2=new DecimalFormat("0.00");
	double distObtida;
	
	Media omegaMedio;
	
	double omegaReal;
	Random rand=new Random();
	TipoPerturbacao tipoPerturbacao;
	int numIterUpdate;
	DistIdeal distIdeal;
	
	public AjusteOmega(TipoPerturbacao tipoPerturbacao, Config config, Integer size,DistIdeal distIdeal) 
	{
		this.tipoPerturbacao = tipoPerturbacao;
		this.omega = distIdeal.distIdeal;
		this.numIterUpdate = config.getNumIterUpdate();
		this.omegaMin=1;
		this.omegaMax=size-2;
		this.omegaMedio=new Media(config.getNumIterUpdate());
		this.distBLMedia=new Media(config.getNumIterUpdate());

		this.distIdeal=distIdeal;
	}
	
	public void ajusteOmega()
	{
		distObtida=distBLMedia.getMediaDinam();

		omega+=((omega/distObtida*distIdeal.distIdeal)-omega);

		omega=Math.min(omegaMax, Math.max(omega, omegaMin));
		
		omegaMedio.setValor(omega);
		
		iterador=0;
	}
	
	public void setDistancia(double distBL)
	{
		iterador++;
		
		distBLMedia.setValor(distBL);

		if(iterador%numIterUpdate==0)
			ajusteOmega();
	}
	
	public double getOmegaReal() 
	{
		omegaReal=omega;
		omegaReal=Math.min(omegaMax, Math.max(omegaReal, omegaMin));
		return omegaReal;
	}

	@Override
	public String toString() {
		return 
		"o"+String.valueOf(tipoPerturbacao).substring(4)+": " + deci2.format(omega) 
		+ " dBLM"+String.valueOf(tipoPerturbacao).substring(4)+": " + distBLMedia
		+ " dMI"+String.valueOf(tipoPerturbacao).substring(4)+": " + deci2.format(distIdeal.distIdeal)
		+ " omegaReal: "+deci2.format(omegaReal)
		+ " distObtida: "+distObtida
		+ " oM"+String.valueOf(tipoPerturbacao).substring(4)+": " + omegaMedio;
	}
	
	public Media getOmegaMedio() 
	{
		return omegaMedio;
	}

	public TipoPerturbacao getTipoPerturbacao() {
		return tipoPerturbacao;
	}

	public void setOmegaReal(double omegaReal) {
		this.omegaReal = omegaReal;
	}

}
