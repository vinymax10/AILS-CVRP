package Perturbation;

public enum TipoPerturbacao 
{
	Sequential(0),
	Concentric(1);
	
	final int tipo;
	
	TipoPerturbacao(int tipo)
	{
		this.tipo=tipo;
	}

}
