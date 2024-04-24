package Perturbation;

public enum InsertionHeuristic 
{
	Distance(1),
	Cost(2);
	
	final int heuristic;
	
	InsertionHeuristic(int heuristic)
	{
		this.heuristic=heuristic;
	}
}
