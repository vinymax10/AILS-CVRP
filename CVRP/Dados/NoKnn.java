package Dados;

public class NoKnn implements Comparable<NoKnn>
{
	public int nome;
	public double dist;
	
	
	
	@Override
	public String toString() {
		return "NoKnn [nome=" + nome + ", dist=" + dist + "]";
	}

	public int compareTo(NoKnn x) 
	{
		if(this.dist<x.dist)
			return -1;
		else if(this.dist>x.dist)
			return 1;
		return 0;
	}
}
