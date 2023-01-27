package Avaliadores;

import Solucao.No;

public class AvaliadorFac 
{
	int infac=0;
	int newinfac=0;
	 
//	-------------------------------SHIFT------------------------------

	public int ganhoSHIFT(No a, No b)
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
	
	public int ganhoSHIFT2Adj(No a, No b)
	{
		 infac=0;
		 newinfac=0;
		 
		 if(a.rota.espacoLivre()<0)
			infac+=a.rota.espacoLivre();
		 
		 if(b.rota.espacoLivre()<0)
			infac+=b.rota.espacoLivre();
		 
		 if(a.rota.espacoLivre()+(a.demanda+a.prox.demanda)<0)
			 newinfac+=a.rota.espacoLivre()+(a.demanda+a.prox.demanda);
			 
		 if(b.rota.espacoLivre()-(a.demanda+a.prox.demanda)<0)
			 newinfac+=b.rota.espacoLivre()-(a.demanda+a.prox.demanda);
		 
		 return -infac+newinfac;
	}
	
	public int ganhoSHIFT3Adj(No a, No b)
	{
		 infac=0;
		 newinfac=0;
		 
		 if(a.rota.espacoLivre()<0)
			infac+=a.rota.espacoLivre();
		 
		 if(b.rota.espacoLivre()<0)
			infac+=b.rota.espacoLivre();
		 
		 if(a.rota.espacoLivre()+(a.demanda+a.prox.demanda+a.prox.prox.demanda)<0)
			 newinfac+=a.rota.espacoLivre()+(a.demanda+a.prox.demanda+a.prox.prox.demanda);
			 
		 if(b.rota.espacoLivre()-(a.demanda+a.prox.demanda+a.prox.prox.demanda)<0)
			 newinfac+=b.rota.espacoLivre()-(a.demanda+a.prox.demanda+a.prox.prox.demanda);
		 
		 return -infac+newinfac;
	}
	
	
//	-------------------------------SWAP------------------------------
	
	public int ganhoSWAP(No a, No b)
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
	
	public int ganhoSWAP2Adj1(No a, No b)
	{
		 infac=0;
		 newinfac=0;
		 
		 if(a.rota.espacoLivre()<0)
			infac+=a.rota.espacoLivre();
		 
		 if(b.rota.espacoLivre()<0)
			infac+=b.rota.espacoLivre();
		 
		 if(a.rota.espacoLivre()+(a.demanda+a.prox.demanda-b.demanda)<0)
			 newinfac+=a.rota.espacoLivre()+(a.demanda+a.prox.demanda-b.demanda);
			 
		 if(b.rota.espacoLivre()-(a.demanda+a.prox.demanda-b.demanda)<0)
			 newinfac+=b.rota.espacoLivre()-(a.demanda+a.prox.demanda-b.demanda);
		 
		 return -infac+newinfac;
	}
	
	public int ganhoSWAP2Adj2(No a, No b)
	{
		 infac=0;
		 newinfac=0;
		 
		 if(a.rota.espacoLivre()<0)
			infac+=a.rota.espacoLivre();
		 
		 if(b.rota.espacoLivre()<0)
			infac+=b.rota.espacoLivre();
		 
		 if(a.rota.espacoLivre()+(a.demanda+a.prox.demanda-b.demanda-b.prox.demanda)<0)
			 newinfac+=a.rota.espacoLivre()+(a.demanda+a.prox.demanda-b.demanda-b.prox.demanda);
			 
		 if(b.rota.espacoLivre()-(a.demanda+a.prox.demanda-b.demanda-b.prox.demanda)<0)
			 newinfac+=b.rota.espacoLivre()-(a.demanda+a.prox.demanda-b.demanda-b.prox.demanda);
		 
		 return -infac+newinfac;
	}
	
	public int ganhoSWAP3Adj1(No a, No b)
	{
		 infac=0;
		 newinfac=0;
		 
		 if(a.rota.espacoLivre()<0)
			infac+=a.rota.espacoLivre();
		 
		 if(b.rota.espacoLivre()<0)
			infac+=b.rota.espacoLivre();
		 
		 if(a.rota.espacoLivre()+(a.demanda+a.prox.demanda+a.prox.prox.demanda-b.demanda)<0)
			 newinfac+=a.rota.espacoLivre()+(a.demanda+a.prox.demanda+a.prox.prox.demanda-b.demanda);
			 
		 if(b.rota.espacoLivre()-(a.demanda+a.prox.demanda+a.prox.prox.demanda-b.demanda)<0)
			 newinfac+=b.rota.espacoLivre()-(a.demanda+a.prox.demanda+a.prox.prox.demanda-b.demanda);
		 
		 return -infac+newinfac;
	}
	
	public int ganhoSWAP3Adj2(No a, No b)
	{
		 infac=0;
		 newinfac=0;
		 
		 if(a.rota.espacoLivre()<0)
			infac+=a.rota.espacoLivre();
		 
		 if(b.rota.espacoLivre()<0)
			infac+=b.rota.espacoLivre();
		 
		 if(a.rota.espacoLivre()+(a.demanda+a.prox.demanda+a.prox.prox.demanda-b.demanda-b.prox.demanda)<0)
			 newinfac+=a.rota.espacoLivre()+(a.demanda+a.prox.demanda+a.prox.prox.demanda-b.demanda-b.prox.demanda);
			 
		 if(b.rota.espacoLivre()-(a.demanda+a.prox.demanda+a.prox.prox.demanda-b.demanda-b.prox.demanda)<0)
			 newinfac+=b.rota.espacoLivre()-(a.demanda+a.prox.demanda+a.prox.prox.demanda-b.demanda-b.prox.demanda);
		 
		 return -infac+newinfac;
	}
	
	public int ganhoSWAP3Adj3(No a, No b)
	{
		 infac=0;
		 newinfac=0;
		 
		 if(a.rota.espacoLivre()<0)
			infac+=a.rota.espacoLivre();
		 
		 if(b.rota.espacoLivre()<0)
			infac+=b.rota.espacoLivre();
		 
		 if(a.rota.espacoLivre()+(a.demanda+a.prox.demanda+a.prox.prox.demanda-b.demanda-b.prox.demanda-b.prox.prox.demanda)<0)
			 newinfac+=a.rota.espacoLivre()+(a.demanda+a.prox.demanda+a.prox.prox.demanda-b.demanda-b.prox.demanda-b.prox.prox.demanda);
			 
		 if(b.rota.espacoLivre()-(a.demanda+a.prox.demanda+a.prox.prox.demanda-b.demanda-b.prox.demanda-b.prox.prox.demanda)<0)
			 newinfac+=b.rota.espacoLivre()-(a.demanda+a.prox.demanda+a.prox.prox.demanda-b.demanda-b.prox.demanda-b.prox.prox.demanda);
		 
		 return -infac+newinfac;
	}
	
//	-------------------------------CROSS------------------------------

	
	public int ganhoCross(No a, No b)
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
	
	public int ganhoCrossInvertido(No a, No b)
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
