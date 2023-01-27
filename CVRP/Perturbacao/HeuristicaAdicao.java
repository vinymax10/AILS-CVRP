package Perturbacao;

public enum HeuristicaAdicao 
{
	DISTGLOBAL(1),
	BESTGLOBAL(2);
	
	final int heuristica;
	
	HeuristicaAdicao(int heuristica)
	{
		this.heuristica=heuristica;
	}
}
