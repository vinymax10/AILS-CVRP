package Evaluators;

public enum MovementType
{
	SWAP(0),
	SHIFT(1),
	Cross(2),
	CrossInverted(3),
	SWAPEstrela(4),
	TwoOpt(5);
	
	final int tipo;
	
	MovementType(int tipo)
	{
		this.tipo=tipo;
	}
}
