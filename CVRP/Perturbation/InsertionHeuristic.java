package Perturbation;

public enum InsertionHeuristic 
{
	Distance(1),
	Cost(2);
	
	final int heuristica;
	
	InsertionHeuristic(int heuristica)
	{
		this.heuristica=heuristica;
	}
}
