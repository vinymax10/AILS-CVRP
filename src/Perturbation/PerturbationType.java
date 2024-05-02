package Perturbation;

public enum PerturbationType 
{
	Sequential(0),
	Concentric(1);
	
	final int type;
	
	PerturbationType(int type)
	{
		this.type=type;
	}

}
