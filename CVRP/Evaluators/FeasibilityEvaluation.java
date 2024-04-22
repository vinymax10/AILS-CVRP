package Evaluators;

import Solution.Node;

public class FeasibilityEvaluation 
{
	int infac=0;
	int newinfac=0;
	 
//	-------------------------------SHIFT------------------------------

	public int gainSHIFT(Node a, Node b)
	{
		 infac=0;
		 newinfac=0;
		 
		 if(a.route.availableCapacity()<0)
			infac+=a.route.availableCapacity();
		 
		 if(b.route.availableCapacity()<0)
			infac+=b.route.availableCapacity();
		 
		 if((a.route.availableCapacity()+a.demanda)<0)
			 newinfac+=(a.route.availableCapacity()+a.demanda);
			 
		 if((b.route.availableCapacity()-a.demanda)<0)
			 newinfac+=(b.route.availableCapacity()-a.demanda);
		 
		 return -infac+newinfac;
	}
	
//	-------------------------------SWAP------------------------------
	
	public int gainSWAP(Node a, Node b)
	{
		 infac=0;
		 newinfac=0;
		 
		 if(a.route.availableCapacity()<0)
			infac+=a.route.availableCapacity();
		 
		 if(b.route.availableCapacity()<0)
			infac+=b.route.availableCapacity();
		 
		 if((a.route.availableCapacity()+(a.demanda-b.demanda))<0)
			 newinfac+=(a.route.availableCapacity()+(a.demanda-b.demanda));
			 
		 if((b.route.availableCapacity()-(a.demanda-b.demanda))<0)
			 newinfac+=(b.route.availableCapacity()-(a.demanda-b.demanda));
		 
		 return -infac+newinfac;
	}
	
//	-------------------------------CROSS------------------------------

	
	public int gainCross(Node a, Node b)
	{
		 infac=0;
		 newinfac=0;
		 
		 if(a.route.availableCapacity()<0)
			infac+=a.route.availableCapacity();
		 
		 if(b.route.availableCapacity()<0)
			infac+=b.route.availableCapacity();
		 
		 if(a.route.availableCapacity()+((a.route.demandaTotal-a.demandaAcumulada)-(b.route.demandaTotal-b.demandaAcumulada))<0)
			 newinfac+=a.route.availableCapacity()+((a.route.demandaTotal-a.demandaAcumulada)-(b.route.demandaTotal-b.demandaAcumulada));
			 
		 if(b.route.availableCapacity()-((a.route.demandaTotal-a.demandaAcumulada)-(b.route.demandaTotal-b.demandaAcumulada))<0)
			 newinfac+=b.route.availableCapacity()-((a.route.demandaTotal-a.demandaAcumulada)-(b.route.demandaTotal-b.demandaAcumulada));
		 
		 return -infac+newinfac;
	}
	
	public int gainCrossInverted(Node a, Node b)
	{
		 infac=0;
		 newinfac=0;
		 
		 if(a.route.availableCapacity()<0)
			infac+=a.route.availableCapacity();
		 
		 if(b.route.availableCapacity()<0)
			infac+=b.route.availableCapacity();
		 
		 if(a.route.availableCapacity()+((a.route.demandaTotal-a.demandaAcumulada)-b.demandaAcumulada)<0)
			 newinfac+=a.route.availableCapacity()+((a.route.demandaTotal-a.demandaAcumulada)-b.demandaAcumulada);
			 
		 if(b.route.availableCapacity()-((a.route.demandaTotal-a.demandaAcumulada)-b.demandaAcumulada)<0)
			 newinfac+=b.route.availableCapacity()-((a.route.demandaTotal-a.demandaAcumulada)-b.demandaAcumulada);
		 
		 return -infac+newinfac;
	}
	 
}
