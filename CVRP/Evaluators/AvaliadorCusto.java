package Evaluators;

import Data.Instance;
import Solution.No;

public class AvaliadorCusto 
{
	Instance instancia;
	double custo;
	public AvaliadorCusto(Instance instancia)
	{
		this.instancia=instancia;
	}
	
//	-------------------------------SHIFT------------------------------
	
	public double custoSHIFT(No a, No b)
	{
		 return instancia.dist(a.ant.nome,a.prox.nome)-instancia.dist(a.nome,a.ant.nome)-instancia.dist(a.nome,a.prox.nome)+
				instancia.dist(a.nome,b.nome)+instancia.dist(a.nome,b.prox.nome)-instancia.dist(b.nome,b.prox.nome);
	}
		
	public double custoSHIFT2Adj(No a, No b)
	{
	return -instancia.dist(a.ant.nome,a.nome)-instancia.dist(a.prox.nome,a.prox.prox.nome)-instancia.dist(b.nome,b.prox.nome)
			+instancia.dist(a.ant.nome,a.prox.prox.nome)+instancia.dist(b.nome,a.nome)+instancia.dist(a.prox.nome,b.prox.nome);
	}
	 
	public double custoSHIFT2AdjIvertido(No a, No b)
	{
		return 	-(instancia.dist(a.ant.nome,a.nome)+instancia.dist(a.prox.nome,a.prox.prox.nome)+instancia.dist(b.nome,b.prox.nome))+
				instancia.dist(a.ant.nome,a.prox.prox.nome)+instancia.dist(b.nome,a.prox.nome)+instancia.dist(a.nome,b.prox.nome);
	}
	 
	public double custoSHIFT3Adj(No a, No b)
	{
		return 	-instancia.dist(a.ant.nome,a.nome)-instancia.dist(a.prox.prox.nome,a.prox.prox.prox.nome)-instancia.dist(b.nome,b.prox.nome)
				+instancia.dist(a.ant.nome,a.prox.prox.prox.nome)+instancia.dist(b.nome,a.nome)+instancia.dist(a.prox.prox.nome,b.prox.nome);
	}
	 
	public double custoSHIFT3AdjIvertido(No a, No b)
	{
		return 	-(instancia.dist(a.ant.nome,a.nome)+instancia.dist(a.prox.prox.nome,a.prox.prox.prox.nome)+instancia.dist(b.nome,b.prox.nome))+
				instancia.dist(a.ant.nome,a.prox.prox.prox.nome)+instancia.dist(b.nome,a.prox.prox.nome)+instancia.dist(a.nome,b.prox.nome);
	}

//	-------------------------------Dsit SHIFT------------------------------
	
//	public double distSHIFT(No a, No b)
//	{
//		 return -instancia.dist(a.nome,a.ant.nome)-instancia.dist(a.nome,a.prox.nome)+
//				instancia.dist(a.nome,b.nome)+instancia.dist(a.nome,b.prox.nome);
//	}

	
//	-------------------------------SWAP------------------------------

	
	public double custoSWAP(No a, No b)
	{
		if(a.prox!=b&&a.ant!=b)
		{
			return 	-(instancia.dist(a.nome,a.ant.nome)+instancia.dist(a.nome,a.prox.nome)+
					instancia.dist(b.nome,b.ant.nome)+instancia.dist(b.nome,b.prox.nome))+				
					(instancia.dist(a.nome,b.ant.nome)+instancia.dist(a.nome,b.prox.nome)+
					instancia.dist(b.nome,a.ant.nome)+instancia.dist(b.nome,a.prox.nome));
		}
		else
		{
			if(a.prox==b)
				return 	-(instancia.dist(a.nome,a.ant.nome)+instancia.dist(b.nome,b.prox.nome))+
					(instancia.dist(a.nome,b.prox.nome)+instancia.dist(b.nome,a.ant.nome));
			else
				return 	-(instancia.dist(b.nome,b.ant.nome)+instancia.dist(a.nome,a.prox.nome))+
						(instancia.dist(b.nome,a.prox.nome)+instancia.dist(a.nome,b.ant.nome));
		}
	}
	
	public double custoSwapEstrela(No a, No b,No antA, No antB)
	{
		if(antA.prox.nome!=b.nome&&antB.prox.nome!=a.nome)
		{
			return custoSHIFT(a,antA)+custoSHIFT(b,antB);
		}
		else 
		{
			if(antA.prox.nome==b.nome&&antB.prox.nome!=a.nome)
			{
//				return Double.MAX_VALUE;
				
				return	-instancia.dist(antA.nome,b.nome)
						-instancia.dist(b.nome,b.prox.nome)
						+instancia.dist(antA.nome,a.nome)
						+instancia.dist(b.prox.nome,a.nome)
						
						-instancia.dist(antB.nome,antB.prox.nome)
						+instancia.dist(antB.nome,b.nome)
						+instancia.dist(b.nome,antB.prox.nome)
						
						-instancia.dist(a.ant.nome,a.nome)
						-instancia.dist(a.nome,a.prox.nome)
						+instancia.dist(a.ant.nome,a.prox.nome);
						
			}
			
			if(antA.prox.nome!=b.nome&&antB.prox.nome==a.nome)
			{
//				return Double.MAX_VALUE;
				
				return	-instancia.dist(antB.nome,a.nome)
						-instancia.dist(a.nome,a.prox.nome)
						+instancia.dist(antB.nome,b.nome)
						+instancia.dist(a.prox.nome,b.nome)
						
						-instancia.dist(antA.nome,antA.prox.nome)
						+instancia.dist(antA.nome,a.nome)
						+instancia.dist(a.nome,antA.prox.nome)
						
						-instancia.dist(b.ant.nome,b.nome)
						-instancia.dist(b.nome,b.prox.nome)
						+instancia.dist(b.ant.nome,b.prox.nome);
			}
			
			custoSWAP(a, b);
		}
		return Double.MAX_VALUE;
//		custo=instancia.dist(a.ant.nome,a.prox.nome)+instancia.dist(b.ant.nome,b.prox.nome);
//		
//		 return instancia.dist(a.ant.nome,a.prox.nome)-instancia.dist(a.nome,a.ant.nome)-instancia.dist(a.nome,a.prox.nome)+
//				instancia.dist(a.nome,b.nome)+instancia.dist(a.nome,b.prox.nome)-instancia.dist(b.nome,b.prox.nome);
	}
	
	public double custoSWAP2Adj1(No a, No b)
	{
	 	if(a.prox.prox!=b&&a.ant!=b)
	 	{
	 		return 	-(instancia.dist(a.ant.nome,a.nome)+instancia.dist(a.prox.nome,a.prox.prox.nome)+instancia.dist(b.ant.nome,b.nome)+instancia.dist(b.nome,b.prox.nome))+				
	 				(instancia.dist(b.ant.nome,a.nome)+instancia.dist(a.prox.nome,b.prox.nome)+instancia.dist(a.ant.nome,b.nome)+instancia.dist(b.nome,a.prox.prox.nome));
	 	}
		else
		{
			if(a.prox.prox==b)
				return 	-(instancia.dist(a.nome,a.ant.nome)+instancia.dist(a.prox.nome,a.prox.prox.nome)+instancia.dist(b.nome,b.prox.nome))+
						(instancia.dist(a.nome,b.nome)+instancia.dist(a.prox.nome,b.prox.nome)+instancia.dist(b.nome,a.ant.nome));
			else
				return 	-(instancia.dist(b.nome,b.ant.nome)+instancia.dist(b.nome,a.nome)+instancia.dist(a.prox.nome,a.prox.prox.nome))+
						(instancia.dist(b.nome,a.prox.nome)+instancia.dist(b.nome,a.prox.prox.nome)+instancia.dist(a.nome,b.ant.nome));
		}
	}
	 
	public double custoSWAP2Adj1Ivertido(No a, No b)
	{
		 if(a.prox.prox!=b&&a.ant!=b)
		 {
			 return 	-(instancia.dist(a.ant.nome,a.nome)+instancia.dist(a.prox.nome,a.prox.prox.nome)+instancia.dist(b.ant.nome,b.nome)+instancia.dist(b.nome,b.prox.nome))+				
					 (instancia.dist(b.ant.nome,a.prox.nome)+instancia.dist(a.nome,b.prox.nome)+instancia.dist(a.ant.nome,b.nome)+instancia.dist(b.nome,a.prox.prox.nome));
		 }
		 else
		 {
			 if(a.prox.prox==b)
					return 	-(instancia.dist(a.nome,a.ant.nome)+instancia.dist(a.prox.nome,a.prox.prox.nome)+instancia.dist(b.nome,b.prox.nome))+
							(instancia.dist(a.prox.nome,b.nome)+instancia.dist(a.nome,b.prox.nome)+instancia.dist(b.nome,a.ant.nome));
				else
					return 	-(instancia.dist(b.nome,b.ant.nome)+instancia.dist(b.nome,a.nome)+instancia.dist(a.prox.nome,a.prox.prox.nome))+
							(instancia.dist(b.nome,a.prox.prox.nome)+instancia.dist(b.nome,a.prox.nome)+instancia.dist(a.nome,b.ant.nome));
		 }
	}
	 
	public double custoSWAP2IdaAdj2Ida(No a, No b)
	{

		 if(a.prox.prox!=b&&a.ant!=b.prox)
		 {
			 return -(instancia.dist(a.ant.nome,a.nome)+instancia.dist(a.prox.nome,a.prox.prox.nome)+instancia.dist(b.ant.nome,b.nome)+instancia.dist(b.prox.nome,b.prox.prox.nome))+				
					 (instancia.dist(b.ant.nome,a.nome)+instancia.dist(a.prox.nome,b.prox.prox.nome)+instancia.dist(a.ant.nome,b.nome)+instancia.dist(b.prox.nome,a.prox.prox.nome));
		 }
		 else
		 {
			 if(a.prox.prox==b)
				 return -(instancia.dist(a.ant.nome,a.nome)+instancia.dist(a.prox.nome,a.prox.prox.nome)+instancia.dist(b.prox.nome,b.prox.prox.nome))+				
						(instancia.dist(a.prox.nome,b.prox.prox.nome)+instancia.dist(a.ant.nome,b.nome)+instancia.dist(b.prox.nome,a.nome));
			 else
				 return -(instancia.dist(b.ant.nome,b.nome)+instancia.dist(b.prox.nome,b.prox.prox.nome)+instancia.dist(a.prox.nome,a.prox.prox.nome))+				
							(instancia.dist(b.prox.nome,a.prox.prox.nome)+instancia.dist(b.ant.nome,a.nome)+instancia.dist(a.prox.nome,b.nome));
		 }
	}
	 
	public double custoSWAP2IdaAdj2Volta(No a, No b)
	{
		 if(a.prox.prox!=b&&a.ant!=b.prox)
		 {
			 return -(instancia.dist(a.ant.nome,a.nome)+instancia.dist(a.prox.nome,a.prox.prox.nome)+instancia.dist(b.ant.nome,b.nome)+instancia.dist(b.prox.nome,b.prox.prox.nome))+				
					 (instancia.dist(b.ant.nome,a.nome)+instancia.dist(a.prox.nome,b.prox.prox.nome)+instancia.dist(a.ant.nome,b.prox.nome)+instancia.dist(b.nome,a.prox.prox.nome));
		 }
		 else
		 {
			 if(a.prox.prox==b)
				 return -(instancia.dist(a.ant.nome,a.nome)+instancia.dist(a.prox.nome,a.prox.prox.nome)+instancia.dist(b.prox.nome,b.prox.prox.nome))+				
						(instancia.dist(a.prox.nome,b.prox.prox.nome)+instancia.dist(a.ant.nome,b.prox.nome)+instancia.dist(b.nome,a.nome));
 			 else
 				 return -(instancia.dist(b.ant.nome,b.nome)+instancia.dist(b.prox.nome,b.prox.prox.nome)+instancia.dist(a.prox.nome,a.prox.prox.nome))+				
 						(instancia.dist(b.nome,a.prox.prox.nome)+instancia.dist(b.ant.nome,a.nome)+instancia.dist(a.prox.nome,b.prox.nome));
		 }
	}
	 
	public double custoSWAP2VoltaAdj2Ida(No a, No b)
	{
		 if(a.prox.prox!=b&&a.ant!=b.prox)
		 {
			return 	-(instancia.dist(a.ant.nome,a.nome)+instancia.dist(a.prox.nome,a.prox.prox.nome)+instancia.dist(b.ant.nome,b.nome)+instancia.dist(b.prox.nome,b.prox.prox.nome))+				
					(instancia.dist(b.ant.nome,a.prox.nome)+instancia.dist(a.nome,b.prox.prox.nome)+instancia.dist(a.ant.nome,b.nome)+instancia.dist(b.prox.nome,a.prox.prox.nome));
		 }
		 else
		 {
			 if(a.prox.prox==b)
				 return	-(instancia.dist(a.ant.nome,a.nome)+instancia.dist(a.prox.nome,a.prox.prox.nome)+instancia.dist(b.prox.nome,b.prox.prox.nome))+				
						(instancia.dist(a.nome,b.prox.prox.nome)+instancia.dist(a.ant.nome,b.nome)+instancia.dist(b.prox.nome,a.prox.nome));
			 else
				 return	-(instancia.dist(b.ant.nome,b.nome)+instancia.dist(b.prox.nome,b.prox.prox.nome)+instancia.dist(a.prox.nome,a.prox.prox.nome))+				
						(instancia.dist(a.nome,b.nome)+instancia.dist(b.ant.nome,a.prox.nome)+instancia.dist(b.prox.nome,a.prox.prox.nome));
		 }
	}
	 
	public double custoSWAP2VoltaAdj2Volta(No a, No b)
	{
		 if(a.prox.prox!=b&&a.ant!=b.prox)
		 {
			 return 	-(instancia.dist(a.ant.nome,a.nome)+instancia.dist(a.prox.nome,a.prox.prox.nome)+instancia.dist(b.ant.nome,b.nome)+instancia.dist(b.prox.nome,b.prox.prox.nome))+				
					 (instancia.dist(b.ant.nome,a.prox.nome)+instancia.dist(a.nome,b.prox.prox.nome)+instancia.dist(a.ant.nome,b.prox.nome)+instancia.dist(b.nome,a.prox.prox.nome));
		 }
		 else
		 {
			 if(a.prox.prox==b)
				 return -(instancia.dist(a.ant.nome,a.nome)+instancia.dist(a.prox.nome,a.prox.prox.nome)+instancia.dist(b.prox.nome,b.prox.prox.nome))+				
						(instancia.dist(a.nome,b.prox.prox.nome)+instancia.dist(a.ant.nome,b.prox.nome)+instancia.dist(b.nome,a.prox.nome));
		 	else
		 		return -(instancia.dist(b.ant.nome,b.nome)+instancia.dist(b.prox.nome,b.prox.prox.nome)+instancia.dist(a.prox.nome,a.prox.prox.nome))+				
						(instancia.dist(b.nome,a.prox.prox.nome)+instancia.dist(b.ant.nome,a.prox.nome)+instancia.dist(a.nome,b.prox.nome));
		 }
	}
	 
	public double custoSWAP3Adj1(No a, No b)
	{
	 	if(a.prox.prox.prox!=b&&a.ant!=b)
	 	{
	 		return 	-(instancia.dist(a.ant.nome,a.nome)+instancia.dist(a.prox.prox.nome,a.prox.prox.prox.nome)+instancia.dist(b.ant.nome,b.nome)+instancia.dist(b.nome,b.prox.nome))+				
	 				(instancia.dist(b.ant.nome,a.nome)+instancia.dist(a.prox.prox.nome,b.prox.nome)+instancia.dist(a.ant.nome,b.nome)+instancia.dist(b.nome,a.prox.prox.prox.nome));
	 	}
		else
		{
			if(a.prox.prox.prox==b)
				return 	-(instancia.dist(a.nome,a.ant.nome)+instancia.dist(a.prox.prox.nome,a.prox.prox.prox.nome)+instancia.dist(b.nome,b.prox.nome))+
						(instancia.dist(a.nome,b.nome)+instancia.dist(a.prox.prox.nome,b.prox.nome)+instancia.dist(b.nome,a.ant.nome));
			else
				return 	-(instancia.dist(b.nome,b.ant.nome)+instancia.dist(a.prox.prox.nome,a.prox.prox.prox.nome)+instancia.dist(a.nome,b.nome))+
						(instancia.dist(b.nome,a.prox.prox.prox.nome)+instancia.dist(a.prox.prox.nome,b.nome)+instancia.dist(a.nome,b.ant.nome));
		}
	}
	
	public double custoSWAP3Adj1Ivertido(No a, No b)
	{
		 if(a.prox.prox.prox!=b&&a.ant!=b)
		 {
			 return 	-(instancia.dist(a.ant.nome,a.nome)+instancia.dist(a.prox.prox.nome,a.prox.prox.prox.nome)+instancia.dist(b.ant.nome,b.nome)+instancia.dist(b.nome,b.prox.nome))+				
					 (instancia.dist(b.ant.nome,a.prox.prox.nome)+instancia.dist(a.nome,b.prox.nome)+instancia.dist(a.ant.nome,b.nome)+instancia.dist(b.nome,a.prox.prox.prox.nome));
		 }
		 else
		 {
			 if(a.prox.prox.prox==b)
					return 	-(instancia.dist(a.nome,a.ant.nome)+instancia.dist(a.prox.prox.nome,a.prox.prox.prox.nome)+instancia.dist(b.nome,b.prox.nome))+
							(instancia.dist(a.prox.prox.nome,b.nome)+instancia.dist(a.nome,b.prox.nome)+instancia.dist(b.nome,a.ant.nome));
				else
					return 	-(instancia.dist(b.nome,b.ant.nome)+instancia.dist(a.prox.prox.nome,a.prox.prox.prox.nome)+instancia.dist(a.nome,b.nome))+
							(instancia.dist(b.nome,a.nome)+instancia.dist(b.nome,a.prox.prox.prox.nome)+instancia.dist(a.prox.prox.nome,b.ant.nome));
		 }
	}
	 
	public double custoSWAP3IdaAdj2Ida(No a, No b)
	{

		 if(a.prox.prox.prox!=b&&a.ant!=b.prox)
		 {
			 return -(instancia.dist(a.ant.nome,a.nome)+instancia.dist(a.prox.prox.nome,a.prox.prox.prox.nome)+instancia.dist(b.ant.nome,b.nome)+instancia.dist(b.prox.nome,b.prox.prox.nome))+				
					 (instancia.dist(b.ant.nome,a.nome)+instancia.dist(a.prox.prox.nome,b.prox.prox.nome)+instancia.dist(a.ant.nome,b.nome)+instancia.dist(b.prox.nome,a.prox.prox.prox.nome));
		 }
		 else
		 {
			 if(a.prox.prox.prox==b)
				 return -(instancia.dist(a.ant.nome,a.nome)+instancia.dist(a.prox.prox.nome,a.prox.prox.prox.nome)+instancia.dist(b.prox.nome,b.prox.prox.nome))+				
						(instancia.dist(a.prox.prox.nome,b.prox.prox.nome)+instancia.dist(a.ant.nome,b.nome)+instancia.dist(b.prox.nome,a.nome));
			 else
				 return -(instancia.dist(b.ant.nome,b.nome)+instancia.dist(b.prox.nome,b.prox.prox.nome)+instancia.dist(a.prox.prox.nome,a.prox.prox.prox.nome))+				
							(instancia.dist(b.prox.nome,a.prox.prox.prox.nome)+instancia.dist(b.ant.nome,a.nome)+instancia.dist(a.prox.prox.nome,b.nome));
		 }
	}
	 
	public double custoSWAP3IdaAdj2Volta(No a, No b)
	{
		 if(a.prox.prox.prox!=b&&a.ant!=b.prox)
		 {
			 return -(instancia.dist(a.ant.nome,a.nome)+instancia.dist(a.prox.prox.nome,a.prox.prox.prox.nome)+instancia.dist(b.ant.nome,b.nome)+instancia.dist(b.prox.nome,b.prox.prox.nome))+				
					 (instancia.dist(b.ant.nome,a.nome)+instancia.dist(a.prox.prox.nome,b.prox.prox.nome)+instancia.dist(a.ant.nome,b.prox.nome)+instancia.dist(b.nome,a.prox.prox.prox.nome));
		 }
		 else
		 {
			 if(a.prox.prox.prox==b)
				 return -(instancia.dist(a.ant.nome,a.nome)+instancia.dist(a.prox.prox.nome,a.prox.prox.prox.nome)+instancia.dist(b.prox.nome,b.prox.prox.nome))+				
						(instancia.dist(a.prox.prox.nome,b.prox.prox.nome)+instancia.dist(a.ant.nome,b.prox.nome)+instancia.dist(b.nome,a.nome));
 			 else
 				 return -(instancia.dist(b.ant.nome,b.nome)+instancia.dist(b.prox.nome,b.prox.prox.nome)+instancia.dist(a.prox.prox.nome,a.prox.prox.prox.nome))+	
  						(instancia.dist(b.prox.nome,a.prox.prox.prox.nome)+instancia.dist(b.ant.nome,a.nome)+instancia.dist(a.prox.prox.nome,b.nome));

// 						(instancia.dist(b.nome,a.prox.prox.prox.nome)+instancia.dist(b.ant.nome,a.nome)+instancia.dist(a.prox.prox.nome,b.prox.nome));
		 }
	}
	 
	public double custoSWAP3VoltaAdj2Ida(No a, No b)
	{
		 if(a.prox.prox.prox!=b&&a.ant!=b.prox)
		 {
			return 	-(instancia.dist(a.ant.nome,a.nome)+instancia.dist(a.prox.prox.nome,a.prox.prox.prox.nome)+instancia.dist(b.ant.nome,b.nome)+instancia.dist(b.prox.nome,b.prox.prox.nome))+				
					(instancia.dist(b.ant.nome,a.prox.prox.nome)+instancia.dist(a.nome,b.prox.prox.nome)+instancia.dist(a.ant.nome,b.nome)+instancia.dist(b.prox.nome,a.prox.prox.prox.nome));
		 }
		 else
		 {
			 if(a.prox.prox.prox==b)
				 return	-(instancia.dist(a.ant.nome,a.nome)+instancia.dist(a.prox.prox.nome,a.prox.prox.prox.nome)+instancia.dist(b.prox.nome,b.prox.prox.nome))+				
						(instancia.dist(a.nome,b.prox.prox.nome)+instancia.dist(a.ant.nome,b.nome)+instancia.dist(b.prox.nome,a.prox.prox.nome));
			 else
				 return	-(instancia.dist(b.ant.nome,b.nome)+instancia.dist(b.prox.nome,b.prox.prox.nome)+instancia.dist(a.prox.prox.nome,a.prox.prox.prox.nome))+				
						(instancia.dist(a.nome,b.nome)+instancia.dist(b.ant.nome,a.prox.prox.nome)+instancia.dist(b.prox.nome,a.prox.prox.prox.nome));
		 }
	}
	 
	public double custoSWAP3VoltaAdj2Volta(No a, No b)
	{
		 if(a.prox.prox.prox!=b&&a.ant!=b.prox)
		 {
			 return 	-(instancia.dist(a.ant.nome,a.nome)+instancia.dist(a.prox.prox.nome,a.prox.prox.prox.nome)+instancia.dist(b.ant.nome,b.nome)+instancia.dist(b.prox.nome,b.prox.prox.nome))+				
					 (instancia.dist(b.ant.nome,a.prox.prox.nome)+instancia.dist(a.nome,b.prox.prox.nome)+instancia.dist(a.ant.nome,b.prox.nome)+instancia.dist(b.nome,a.prox.prox.prox.nome));
		 }
		 else
		 {
			 if(a.prox.prox.prox==b)
				 return -(instancia.dist(a.ant.nome,a.nome)+instancia.dist(a.prox.prox.nome,a.prox.prox.prox.nome)+instancia.dist(b.prox.nome,b.prox.prox.nome))+				
						(instancia.dist(a.nome,b.prox.prox.nome)+instancia.dist(a.ant.nome,b.prox.nome)+instancia.dist(b.nome,a.prox.prox.nome));
		 	else
		 		return -(instancia.dist(b.ant.nome,b.nome)+instancia.dist(b.prox.nome,b.prox.prox.nome)+instancia.dist(a.prox.prox.nome,a.prox.prox.prox.nome))+				
						(instancia.dist(b.nome,a.prox.prox.prox.nome)+instancia.dist(b.ant.nome,a.prox.prox.nome)+instancia.dist(a.nome,b.prox.nome));
		 }
	}
	
	public double custoSWAP3IdaAdj3Ida(No a, No b)
	{

		 if(a.prox.prox.prox!=b&&a!=b.prox.prox.prox)
		 {
			 return -(instancia.dist(a.ant.nome,a.nome)+instancia.dist(a.prox.prox.nome,a.prox.prox.prox.nome)+instancia.dist(b.ant.nome,b.nome)+instancia.dist(b.prox.prox.nome,b.prox.prox.prox.nome))+				
					 (instancia.dist(b.ant.nome,a.nome)+instancia.dist(a.prox.prox.nome,b.prox.prox.prox.nome)+instancia.dist(a.ant.nome,b.nome)+instancia.dist(b.prox.prox.nome,a.prox.prox.prox.nome));
		 }
		 else
		 {
			 if(a.prox.prox.prox==b)
				 return -(instancia.dist(a.ant.nome,a.nome)+instancia.dist(a.prox.prox.nome,a.prox.prox.prox.nome)+instancia.dist(b.prox.prox.nome,b.prox.prox.prox.nome))+				
						(instancia.dist(a.prox.prox.nome,b.prox.prox.prox.nome)+instancia.dist(a.ant.nome,b.nome)+instancia.dist(b.prox.prox.nome,a.nome));
			 else
				 return -(instancia.dist(b.ant.nome,b.nome)+instancia.dist(b.prox.prox.nome,b.prox.prox.prox.nome)+instancia.dist(a.prox.prox.nome,a.prox.prox.prox.nome))+				
							(instancia.dist(b.prox.prox.nome,a.prox.prox.prox.nome)+instancia.dist(b.ant.nome,a.nome)+instancia.dist(a.prox.prox.nome,b.nome));
		 }
	}
	 
	public double custoSWAP3IdaAdj3Volta(No a, No b)
	{
		 if(a.prox.prox.prox!=b&&a!=b.prox.prox.prox)
		 {
			 return -(instancia.dist(a.ant.nome,a.nome)+instancia.dist(a.prox.prox.nome,a.prox.prox.prox.nome)+instancia.dist(b.ant.nome,b.nome)+instancia.dist(b.prox.prox.nome,b.prox.prox.prox.nome))+				
					 (instancia.dist(b.ant.nome,a.nome)+instancia.dist(a.prox.prox.nome,b.prox.prox.prox.nome)+instancia.dist(a.ant.nome,b.prox.prox.nome)+instancia.dist(b.nome,a.prox.prox.prox.nome));
		 }
		 else
		 {
			 if(a.prox.prox.prox==b)
				 return -(instancia.dist(a.ant.nome,a.nome)+instancia.dist(a.prox.prox.nome,a.prox.prox.prox.nome)+instancia.dist(b.prox.prox.nome,b.prox.prox.prox.nome))+				
						(instancia.dist(a.prox.prox.nome,b.prox.prox.prox.nome)+instancia.dist(a.ant.nome,b.prox.prox.nome)+instancia.dist(b.nome,a.nome));
 			 else
 				 return -(instancia.dist(b.ant.nome,b.nome)+instancia.dist(b.prox.prox.nome,b.prox.prox.prox.nome)+instancia.dist(a.prox.prox.nome,a.prox.prox.prox.nome))+				
   						(instancia.dist(b.prox.prox.nome,a.prox.prox.prox.nome)+instancia.dist(b.ant.nome,a.nome)+instancia.dist(a.prox.prox.nome,b.nome));

// 						 (instancia.dist(b.nome,a.prox.prox.prox.nome)+instancia.dist(b.ant.nome,a.nome)+instancia.dist(a.prox.prox.nome,b.prox.prox.nome));
		 }
	}
	 
	public double custoSWAP3VoltaAdj3Ida(No a, No b)
	{
		 if(a.prox.prox.prox!=b&&a!=b.prox.prox.prox)
		 {
			return 	-(instancia.dist(a.ant.nome,a.nome)+instancia.dist(a.prox.prox.nome,a.prox.prox.prox.nome)+instancia.dist(b.ant.nome,b.nome)+instancia.dist(b.prox.prox.nome,b.prox.prox.prox.nome))+				
					(instancia.dist(b.ant.nome,a.prox.prox.nome)+instancia.dist(a.nome,b.prox.prox.prox.nome)+instancia.dist(a.ant.nome,b.nome)+instancia.dist(b.prox.prox.nome,a.prox.prox.prox.nome));
		 }
		 else
		 {
			 if(a.prox.prox.prox==b)
				 return	-(instancia.dist(a.ant.nome,a.nome)+instancia.dist(a.prox.prox.nome,a.prox.prox.prox.nome)+instancia.dist(b.prox.prox.nome,b.prox.prox.prox.nome))+				
						(instancia.dist(a.nome,b.prox.prox.prox.nome)+instancia.dist(a.ant.nome,b.nome)+instancia.dist(b.prox.prox.nome,a.prox.prox.nome));
			 else
				 return	-(instancia.dist(b.ant.nome,b.nome)+instancia.dist(b.prox.prox.nome,b.prox.prox.prox.nome)+instancia.dist(a.prox.prox.nome,a.prox.prox.prox.nome))+				
						(instancia.dist(a.nome,b.nome)+instancia.dist(b.ant.nome,a.prox.prox.nome)+instancia.dist(b.prox.prox.nome,a.prox.prox.prox.nome));
		 }
	}
	 
	public double custoSWAP3VoltaAdj3Volta(No a, No b)
	{
		 if(a.prox.prox.prox!=b&&a!=b.prox.prox.prox)
		 {
			 return 	-(instancia.dist(a.ant.nome,a.nome)+instancia.dist(a.prox.prox.nome,a.prox.prox.prox.nome)+instancia.dist(b.ant.nome,b.nome)+instancia.dist(b.prox.prox.nome,b.prox.prox.prox.nome))+				
					 (instancia.dist(b.ant.nome,a.prox.prox.nome)+instancia.dist(a.nome,b.prox.prox.prox.nome)+instancia.dist(a.ant.nome,b.prox.prox.nome)+instancia.dist(b.nome,a.prox.prox.prox.nome));
		 }
		 else
		 {
			 if(a.prox.prox.prox==b)
				 return -(instancia.dist(a.ant.nome,a.nome)+instancia.dist(a.prox.prox.nome,a.prox.prox.prox.nome)+instancia.dist(b.prox.prox.nome,b.prox.prox.prox.nome))+				
						(instancia.dist(a.nome,b.prox.prox.prox.nome)+instancia.dist(a.ant.nome,b.prox.prox.nome)+instancia.dist(b.nome,a.prox.prox.nome));
		 	else
		 		return -(instancia.dist(b.ant.nome,b.nome)+instancia.dist(b.prox.prox.nome,b.prox.prox.prox.nome)+instancia.dist(a.prox.prox.nome,a.prox.prox.prox.nome))+				
						(instancia.dist(b.nome,a.prox.prox.prox.nome)+instancia.dist(b.ant.nome,a.prox.prox.nome)+instancia.dist(a.nome,b.prox.prox.nome));
		 }
	}
	
//		-------------------------------CROSS------------------------------

	public double custoCross(No a, No b)
	{
		 return -(instancia.dist(a.nome,a.prox.nome)+instancia.dist(b.nome,b.prox.nome))
				 +(instancia.dist(a.nome,b.prox.nome)+instancia.dist(b.nome,a.prox.nome));
	}
	 
	public double custoCrossInvertido(No a, No b)
	{
		 return -(instancia.dist(a.nome,a.prox.nome)+instancia.dist(b.nome,b.prox.nome))
				 +(instancia.dist(a.nome,b.nome)+instancia.dist(b.prox.nome,a.prox.nome));
	}
	 
	public double custo2Opt(No a, No b)
	{
		return 	-(instancia.dist(a.nome,a.prox.nome)+instancia.dist(b.nome,b.prox.nome))+				
				(instancia.dist(a.nome,b.nome)+instancia.dist(a.prox.nome,b.prox.nome));
	}
	 
}
