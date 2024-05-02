package Data;

public enum EdgeType 
{
	EUC_2D(0),
	Coord(1),
	EXPLICIT(2);
	
	final int type;
	
	EdgeType(int type)
	{
		this.type=type;
	}

}
