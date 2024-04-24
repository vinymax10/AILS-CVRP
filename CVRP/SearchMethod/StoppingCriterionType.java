package SearchMethod;

public enum StoppingCriterionType 
{
	Time(1),
	Iteration(2);
	
	final int type;
	
	StoppingCriterionType(int type)
	{
		this.type=type;
	}

}
