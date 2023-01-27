package Auxiliar;

import Solucao.No;
import Solucao.Solucao;

public class Distancia 
{
	No solucaoB[];
	No solucaoA[];
	
	public int distanciaEdge(Solucao a, Solucao b)
	{
		this.solucaoB=b.getSolucao();
		this.solucaoA=a.getSolucao();
		
		int viz;
		int dist=0;
		for (int i = 0; i < solucaoA.length; i++)
		{
			viz=solucaoA[i].prox.nome;
			if(solucaoB[i].prox.nome!=viz&&viz!=solucaoB[i].ant.nome)
				dist++;

			if(solucaoA[i].ant.nome==0)
			{
				if(solucaoB[i].prox.nome!=0&&0!=solucaoB[i].ant.nome)
					dist++;
			}
		}
		
		return dist;
	}
	
	public int distanciaEdgeMaior(Solucao a, Solucao b,double distMin)
	{
		this.solucaoB=b.getSolucao();
		this.solucaoA=a.getSolucao();
		
		int viz;
		int dist=0;
		for (int i = 0; i < solucaoA.length &&dist<distMin; i++)
		{
			viz=solucaoA[i].prox.nome;
			if(solucaoB[i].prox.nome!=viz&&viz!=solucaoB[i].ant.nome)
				dist++;

			if(solucaoA[i].ant.nome==0)
			{
				if(solucaoB[i].prox.nome!=0&&0!=solucaoB[i].ant.nome)
					dist++;
			}
			
		}
		
		return dist;
	}
	
}
