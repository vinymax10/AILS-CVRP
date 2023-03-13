package Perturbation;

public enum HeuristicaAdicao 
{
	Distance(1),
	Cost(2);
	
	final int heuristica;
	
	HeuristicaAdicao(int heuristica)
	{
		this.heuristica=heuristica;
	}
}
