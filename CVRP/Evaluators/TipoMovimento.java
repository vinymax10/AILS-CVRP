package Evaluators;

public enum TipoMovimento
{
	SWAP(0),
	SHIFT(1),
	Cross(2),
	CrossInverted(3),
	SWAPEstrela(4),
	TwoOpt(5);
	
	final int tipo;
	
	TipoMovimento(int tipo)
	{
		this.tipo=tipo;
	}
}
