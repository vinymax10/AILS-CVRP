package Dados;

public interface Instancia 
{

	public Ponto[] getPontos();

	public int getNumRotasMin();

	public int getNumRotasMax();

	public int getSize();

	public int getCapacidade();

	public int getDeposito();

	public double getMaiorDist();
	
	public double getMenorDist();

	public String getNome();

	public int[][] getKnn();
	
	public double dist(int i,int j);
	
	public double porcentagem();
}
