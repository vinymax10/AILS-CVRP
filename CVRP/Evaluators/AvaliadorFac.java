package Evaluators;

import Solution.Node;

public class AvaliadorFac 
{
	int infac=0;
	int newinfac=0;
	 
//	-------------------------------SHIFT------------------------------

	public int ganhoSHIFT(Node a, Node b)
	{
		 infac=0;
		 newinfac=0;
		 
		 if(a.rota.espacoLivre()<0)
			infac+=a.rota.espacoLivre();
		 
		 if(b.rota.espacoLivre()<0)
			infac+=b.rota.espacoLivre();
		 
		 if((a.rota.espacoLivre()+a.demanda)<0)
			 newinfac+=(a.rota.espacoLivre()+a.demanda);
			 
		 if((b.rota.espacoLivre()-a.demanda)<0)
			 newinfac+=(b.rota.espacoLivre()-a.demanda);
		 
		 return -infac+newinfac;
	}
	
	public int ganhoSHIFT2Adj(Node a, Node b)
	{
		 infac=0;
		 newinfac=0;
		 
		 if(a.rota.espacoLivre()<0)
			infac+=a.rota.espacoLivre();
		 
		 if(b.rota.espacoLivre()<0)
			infac+=b.rota.espacoLivre();
		 
		 if(a.rota.espacoLivre()+(a.demanda+a.next.demanda)<0)
			 newinfac+=a.rota.espacoLivre()+(a.demanda+a.next.demanda);
			 
		 if(b.rota.espacoLivre()-(a.demanda+a.next.demanda)<0)
			 newinfac+=b.rota.espacoLivre()-(a.demanda+a.next.demanda);
		 
		 return -infac+newinfac;
	}
	
	public int ganhoSHIFT3Adj(Node a, Node b)
	{
		 infac=0;
		 newinfac=0;
		 
		 if(a.rota.espacoLivre()<0)
			infac+=a.rota.espacoLivre();
		 
		 if(b.rota.espacoLivre()<0)
			infac+=b.rota.espacoLivre();
		 
		 if(a.rota.espacoLivre()+(a.demanda+a.next.demanda+a.next.next.demanda)<0)
			 newinfac+=a.rota.espacoLivre()+(a.demanda+a.next.demanda+a.next.next.demanda);
			 
		 if(b.rota.espacoLivre()-(a.demanda+a.next.demanda+a.next.next.demanda)<0)
			 newinfac+=b.rota.espacoLivre()-(a.demanda+a.next.demanda+a.next.next.demanda);
		 
		 return -infac+newinfac;
	}
	
	
//	-------------------------------SWAP------------------------------
	
	public int ganhoSWAP(Node a, Node b)
	{
		 infac=0;
		 newinfac=0;
		 
		 if(a.rota.espacoLivre()<0)
			infac+=a.rota.espacoLivre();
		 
		 if(b.rota.espacoLivre()<0)
			infac+=b.rota.espacoLivre();
		 
		 if((a.rota.espacoLivre()+(a.demanda-b.demanda))<0)
			 newinfac+=(a.rota.espacoLivre()+(a.demanda-b.demanda));
			 
		 if((b.rota.espacoLivre()-(a.demanda-b.demanda))<0)
			 newinfac+=(b.rota.espacoLivre()-(a.demanda-b.demanda));
		 
		 return -infac+newinfac;
	}
	
	public int ganhoSWAP2Adj1(Node a, Node b)
	{
		 infac=0;
		 newinfac=0;
		 
		 if(a.rota.espacoLivre()<0)
			infac+=a.rota.espacoLivre();
		 
		 if(b.rota.espacoLivre()<0)
			infac+=b.rota.espacoLivre();
		 
		 if(a.rota.espacoLivre()+(a.demanda+a.next.demanda-b.demanda)<0)
			 newinfac+=a.rota.espacoLivre()+(a.demanda+a.next.demanda-b.demanda);
			 
		 if(b.rota.espacoLivre()-(a.demanda+a.next.demanda-b.demanda)<0)
			 newinfac+=b.rota.espacoLivre()-(a.demanda+a.next.demanda-b.demanda);
		 
		 return -infac+newinfac;
	}
	
	public int ganhoSWAP2Adj2(Node a, Node b)
	{
		 infac=0;
		 newinfac=0;
		 
		 if(a.rota.espacoLivre()<0)
			infac+=a.rota.espacoLivre();
		 
		 if(b.rota.espacoLivre()<0)
			infac+=b.rota.espacoLivre();
		 
		 if(a.rota.espacoLivre()+(a.demanda+a.next.demanda-b.demanda-b.next.demanda)<0)
			 newinfac+=a.rota.espacoLivre()+(a.demanda+a.next.demanda-b.demanda-b.next.demanda);
			 
		 if(b.rota.espacoLivre()-(a.demanda+a.next.demanda-b.demanda-b.next.demanda)<0)
			 newinfac+=b.rota.espacoLivre()-(a.demanda+a.next.demanda-b.demanda-b.next.demanda);
		 
		 return -infac+newinfac;
	}
	
	public int ganhoSWAP3Adj1(Node a, Node b)
	{
		 infac=0;
		 newinfac=0;
		 
		 if(a.rota.espacoLivre()<0)
			infac+=a.rota.espacoLivre();
		 
		 if(b.rota.espacoLivre()<0)
			infac+=b.rota.espacoLivre();
		 
		 if(a.rota.espacoLivre()+(a.demanda+a.next.demanda+a.next.next.demanda-b.demanda)<0)
			 newinfac+=a.rota.espacoLivre()+(a.demanda+a.next.demanda+a.next.next.demanda-b.demanda);
			 
		 if(b.rota.espacoLivre()-(a.demanda+a.next.demanda+a.next.next.demanda-b.demanda)<0)
			 newinfac+=b.rota.espacoLivre()-(a.demanda+a.next.demanda+a.next.next.demanda-b.demanda);
		 
		 return -infac+newinfac;
	}
	
	public int ganhoSWAP3Adj2(Node a, Node b)
	{
		 infac=0;
		 newinfac=0;
		 
		 if(a.rota.espacoLivre()<0)
			infac+=a.rota.espacoLivre();
		 
		 if(b.rota.espacoLivre()<0)
			infac+=b.rota.espacoLivre();
		 
		 if(a.rota.espacoLivre()+(a.demanda+a.next.demanda+a.next.next.demanda-b.demanda-b.next.demanda)<0)
			 newinfac+=a.rota.espacoLivre()+(a.demanda+a.next.demanda+a.next.next.demanda-b.demanda-b.next.demanda);
			 
		 if(b.rota.espacoLivre()-(a.demanda+a.next.demanda+a.next.next.demanda-b.demanda-b.next.demanda)<0)
			 newinfac+=b.rota.espacoLivre()-(a.demanda+a.next.demanda+a.next.next.demanda-b.demanda-b.next.demanda);
		 
		 return -infac+newinfac;
	}
	
	public int ganhoSWAP3Adj3(Node a, Node b)
	{
		 infac=0;
		 newinfac=0;
		 
		 if(a.rota.espacoLivre()<0)
			infac+=a.rota.espacoLivre();
		 
		 if(b.rota.espacoLivre()<0)
			infac+=b.rota.espacoLivre();
		 
		 if(a.rota.espacoLivre()+(a.demanda+a.next.demanda+a.next.next.demanda-b.demanda-b.next.demanda-b.next.next.demanda)<0)
			 newinfac+=a.rota.espacoLivre()+(a.demanda+a.next.demanda+a.next.next.demanda-b.demanda-b.next.demanda-b.next.next.demanda);
			 
		 if(b.rota.espacoLivre()-(a.demanda+a.next.demanda+a.next.next.demanda-b.demanda-b.next.demanda-b.next.next.demanda)<0)
			 newinfac+=b.rota.espacoLivre()-(a.demanda+a.next.demanda+a.next.next.demanda-b.demanda-b.next.demanda-b.next.next.demanda);
		 
		 return -infac+newinfac;
	}
	
//	-------------------------------CROSS------------------------------

	
	public int ganhoCross(Node a, Node b)
	{
		 infac=0;
		 newinfac=0;
		 
		 if(a.rota.espacoLivre()<0)
			infac+=a.rota.espacoLivre();
		 
		 if(b.rota.espacoLivre()<0)
			infac+=b.rota.espacoLivre();
		 
		 if(a.rota.espacoLivre()+((a.rota.demandaTotal-a.demandaAcumulada)-(b.rota.demandaTotal-b.demandaAcumulada))<0)
			 newinfac+=a.rota.espacoLivre()+((a.rota.demandaTotal-a.demandaAcumulada)-(b.rota.demandaTotal-b.demandaAcumulada));
			 
		 if(b.rota.espacoLivre()-((a.rota.demandaTotal-a.demandaAcumulada)-(b.rota.demandaTotal-b.demandaAcumulada))<0)
			 newinfac+=b.rota.espacoLivre()-((a.rota.demandaTotal-a.demandaAcumulada)-(b.rota.demandaTotal-b.demandaAcumulada));
		 
		 return -infac+newinfac;
	}
	
	public int ganhoCrossInvertido(Node a, Node b)
	{
		 infac=0;
		 newinfac=0;
		 
		 if(a.rota.espacoLivre()<0)
			infac+=a.rota.espacoLivre();
		 
		 if(b.rota.espacoLivre()<0)
			infac+=b.rota.espacoLivre();
		 
		 if(a.rota.espacoLivre()+((a.rota.demandaTotal-a.demandaAcumulada)-b.demandaAcumulada)<0)
			 newinfac+=a.rota.espacoLivre()+((a.rota.demandaTotal-a.demandaAcumulada)-b.demandaAcumulada);
			 
		 if(b.rota.espacoLivre()-((a.rota.demandaTotal-a.demandaAcumulada)-b.demandaAcumulada)<0)
			 newinfac+=b.rota.espacoLivre()-((a.rota.demandaTotal-a.demandaAcumulada)-b.demandaAcumulada);
		 
		 return -infac+newinfac;
	}
	 
}
