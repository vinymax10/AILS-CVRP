package Evaluators;

public enum MovementType
{
	SWAP(0),
	SHIFT(1),
	Cross(2),
	CrossInverted(3),
	SWAPStar(4),
	TwoOpt(5);
	
	final int type;
	
	MovementType(int type)
	{
		this.type=type;
	}
}
