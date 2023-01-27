package Dados;

public class Ponto 
{
	public int nome;
	public Double x;
	public Double y;
	public int demanda;
	
	public Ponto(int nome) {
		super();
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "Ponto [nome=" + nome + ", x=" + x + ", y=" + y + ", demanda=" + demanda + "]";
	}
	
}
