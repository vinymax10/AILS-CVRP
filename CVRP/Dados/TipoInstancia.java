package Dados;

public enum TipoInstancia 
{
	Matriz(0),
	MatrizShort(1),
	Hash(2),
	Esparca(3);
	
	final int tipo;
	
	TipoInstancia(int tipo)
	{
		this.tipo=tipo;
	}

}
