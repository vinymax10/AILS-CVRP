package Auxiliary;

import java.text.DecimalFormat;

public class Media 
{
	int numIterUpdate;
	double mediaDinam=0,mediaGlobal;
	int iteracoes=0;
	DecimalFormat deci2=new DecimalFormat("0.00");

	public Media(int numIterUpdate)
	{
		this.numIterUpdate=numIterUpdate;
	}
	
	public void setValor(double valor)
	{
		iteracoes++;
		if(iteracoes<numIterUpdate)
			mediaDinam=(mediaDinam*(iteracoes-1)+valor)/iteracoes;
		else
			mediaDinam=((mediaDinam*(1-((double)1/numIterUpdate)))+((valor)*((double)1/numIterUpdate)));
	
		mediaGlobal=(mediaGlobal*(iteracoes-1)+valor)/iteracoes;
	}

	@Override
	public String toString() {
		return "D: " + deci2.format(mediaDinam) + " G: " + deci2.format(mediaGlobal);
	}

	public double getMediaDinam() {
		return mediaDinam;
	}

	public double getMediaGlobal() {
		return mediaGlobal;
	}
}
