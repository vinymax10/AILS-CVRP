package MetodoBusca;

public enum TipoCriterioParada 
{
	TempoTotal(1),
	TempoSemMelhora(2),
	Iteracao(3),
	IteracaoSemMelhora(4);
	
	final int tipo;
	
	TipoCriterioParada(int tipo)
	{
		this.tipo=tipo;
	}

}
