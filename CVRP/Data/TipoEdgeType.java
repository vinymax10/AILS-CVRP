package Data;

public enum TipoEdgeType 
{
	EUC_2D(0),
	Coord(1),
	EXPLICIT(2);
	
	final int tipo;
	
	TipoEdgeType(int tipo)
	{
		this.tipo=tipo;
	}

}
