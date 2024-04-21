package SearchMethod;

public enum StoppingCriterionType 
{
	Time(1),
	Iteration(2);
	
	final int tipo;
	
	StoppingCriterionType(int tipo)
	{
		this.tipo=tipo;
	}

}
