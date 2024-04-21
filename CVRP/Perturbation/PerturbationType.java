package Perturbation;

public enum PerturbationType 
{
	Sequential(0),
	Concentric(1);
	
	final int tipo;
	
	PerturbationType(int tipo)
	{
		this.tipo=tipo;
	}

}
