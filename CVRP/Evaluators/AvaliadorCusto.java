package Evaluators;

import Data.Instance;
import Solution.Node;

public class AvaliadorCusto 
{
	Instance instancia;
	double custo;
	public AvaliadorCusto(Instance instancia)
	{
		this.instancia=instancia;
	}
	
//	-------------------------------SHIFT------------------------------
	
	public double custoSHIFT(Node a, Node b)
	{
		 return instancia.dist(a.prev.name,a.next.name)-instancia.dist(a.name,a.prev.name)-instancia.dist(a.name,a.next.name)+
				instancia.dist(a.name,b.name)+instancia.dist(a.name,b.next.name)-instancia.dist(b.name,b.next.name);
	}
		
	public double custoSHIFT2Adj(Node a, Node b)
	{
	return -instancia.dist(a.prev.name,a.name)-instancia.dist(a.next.name,a.next.next.name)-instancia.dist(b.name,b.next.name)
			+instancia.dist(a.prev.name,a.next.next.name)+instancia.dist(b.name,a.name)+instancia.dist(a.next.name,b.next.name);
	}
	 
	public double custoSHIFT2AdjIvertido(Node a, Node b)
	{
		return 	-(instancia.dist(a.prev.name,a.name)+instancia.dist(a.next.name,a.next.next.name)+instancia.dist(b.name,b.next.name))+
				instancia.dist(a.prev.name,a.next.next.name)+instancia.dist(b.name,a.next.name)+instancia.dist(a.name,b.next.name);
	}
	 
	public double custoSHIFT3Adj(Node a, Node b)
	{
		return 	-instancia.dist(a.prev.name,a.name)-instancia.dist(a.next.next.name,a.next.next.next.name)-instancia.dist(b.name,b.next.name)
				+instancia.dist(a.prev.name,a.next.next.next.name)+instancia.dist(b.name,a.name)+instancia.dist(a.next.next.name,b.next.name);
	}
	 
	public double custoSHIFT3AdjIvertido(Node a, Node b)
	{
		return 	-(instancia.dist(a.prev.name,a.name)+instancia.dist(a.next.next.name,a.next.next.next.name)+instancia.dist(b.name,b.next.name))+
				instancia.dist(a.prev.name,a.next.next.next.name)+instancia.dist(b.name,a.next.next.name)+instancia.dist(a.name,b.next.name);
	}

//	-------------------------------Dsit SHIFT------------------------------
	
//	public double distSHIFT(No a, No b)
//	{
//		 return -instancia.dist(a.nome,a.ant.nome)-instancia.dist(a.nome,a.prox.nome)+
//				instancia.dist(a.nome,b.nome)+instancia.dist(a.nome,b.prox.nome);
//	}

	
//	-------------------------------SWAP------------------------------

	
	public double custoSWAP(Node a, Node b)
	{
		if(a.next!=b&&a.prev!=b)
		{
			return 	-(instancia.dist(a.name,a.prev.name)+instancia.dist(a.name,a.next.name)+
					instancia.dist(b.name,b.prev.name)+instancia.dist(b.name,b.next.name))+				
					(instancia.dist(a.name,b.prev.name)+instancia.dist(a.name,b.next.name)+
					instancia.dist(b.name,a.prev.name)+instancia.dist(b.name,a.next.name));
		}
		else
		{
			if(a.next==b)
				return 	-(instancia.dist(a.name,a.prev.name)+instancia.dist(b.name,b.next.name))+
					(instancia.dist(a.name,b.next.name)+instancia.dist(b.name,a.prev.name));
			else
				return 	-(instancia.dist(b.name,b.prev.name)+instancia.dist(a.name,a.next.name))+
						(instancia.dist(b.name,a.next.name)+instancia.dist(a.name,b.prev.name));
		}
	}
	
	public double custoSwapEstrela(Node a, Node b,Node antA, Node antB)
	{
		if(antA.next.name!=b.name&&antB.next.name!=a.name)
		{
			return custoSHIFT(a,antA)+custoSHIFT(b,antB);
		}
		else 
		{
			if(antA.next.name==b.name&&antB.next.name!=a.name)
			{
//				return Double.MAX_VALUE;
				
				return	-instancia.dist(antA.name,b.name)
						-instancia.dist(b.name,b.next.name)
						+instancia.dist(antA.name,a.name)
						+instancia.dist(b.next.name,a.name)
						
						-instancia.dist(antB.name,antB.next.name)
						+instancia.dist(antB.name,b.name)
						+instancia.dist(b.name,antB.next.name)
						
						-instancia.dist(a.prev.name,a.name)
						-instancia.dist(a.name,a.next.name)
						+instancia.dist(a.prev.name,a.next.name);
						
			}
			
			if(antA.next.name!=b.name&&antB.next.name==a.name)
			{
//				return Double.MAX_VALUE;
				
				return	-instancia.dist(antB.name,a.name)
						-instancia.dist(a.name,a.next.name)
						+instancia.dist(antB.name,b.name)
						+instancia.dist(a.next.name,b.name)
						
						-instancia.dist(antA.name,antA.next.name)
						+instancia.dist(antA.name,a.name)
						+instancia.dist(a.name,antA.next.name)
						
						-instancia.dist(b.prev.name,b.name)
						-instancia.dist(b.name,b.next.name)
						+instancia.dist(b.prev.name,b.next.name);
			}
			
			custoSWAP(a, b);
		}
		return Double.MAX_VALUE;
//		custo=instancia.dist(a.ant.nome,a.prox.nome)+instancia.dist(b.ant.nome,b.prox.nome);
//		
//		 return instancia.dist(a.ant.nome,a.prox.nome)-instancia.dist(a.nome,a.ant.nome)-instancia.dist(a.nome,a.prox.nome)+
//				instancia.dist(a.nome,b.nome)+instancia.dist(a.nome,b.prox.nome)-instancia.dist(b.nome,b.prox.nome);
	}
	
	public double custoSWAP2Adj1(Node a, Node b)
	{
	 	if(a.next.next!=b&&a.prev!=b)
	 	{
	 		return 	-(instancia.dist(a.prev.name,a.name)+instancia.dist(a.next.name,a.next.next.name)+instancia.dist(b.prev.name,b.name)+instancia.dist(b.name,b.next.name))+				
	 				(instancia.dist(b.prev.name,a.name)+instancia.dist(a.next.name,b.next.name)+instancia.dist(a.prev.name,b.name)+instancia.dist(b.name,a.next.next.name));
	 	}
		else
		{
			if(a.next.next==b)
				return 	-(instancia.dist(a.name,a.prev.name)+instancia.dist(a.next.name,a.next.next.name)+instancia.dist(b.name,b.next.name))+
						(instancia.dist(a.name,b.name)+instancia.dist(a.next.name,b.next.name)+instancia.dist(b.name,a.prev.name));
			else
				return 	-(instancia.dist(b.name,b.prev.name)+instancia.dist(b.name,a.name)+instancia.dist(a.next.name,a.next.next.name))+
						(instancia.dist(b.name,a.next.name)+instancia.dist(b.name,a.next.next.name)+instancia.dist(a.name,b.prev.name));
		}
	}
	 
	public double custoSWAP2Adj1Ivertido(Node a, Node b)
	{
		 if(a.next.next!=b&&a.prev!=b)
		 {
			 return 	-(instancia.dist(a.prev.name,a.name)+instancia.dist(a.next.name,a.next.next.name)+instancia.dist(b.prev.name,b.name)+instancia.dist(b.name,b.next.name))+				
					 (instancia.dist(b.prev.name,a.next.name)+instancia.dist(a.name,b.next.name)+instancia.dist(a.prev.name,b.name)+instancia.dist(b.name,a.next.next.name));
		 }
		 else
		 {
			 if(a.next.next==b)
					return 	-(instancia.dist(a.name,a.prev.name)+instancia.dist(a.next.name,a.next.next.name)+instancia.dist(b.name,b.next.name))+
							(instancia.dist(a.next.name,b.name)+instancia.dist(a.name,b.next.name)+instancia.dist(b.name,a.prev.name));
				else
					return 	-(instancia.dist(b.name,b.prev.name)+instancia.dist(b.name,a.name)+instancia.dist(a.next.name,a.next.next.name))+
							(instancia.dist(b.name,a.next.next.name)+instancia.dist(b.name,a.next.name)+instancia.dist(a.name,b.prev.name));
		 }
	}
	 
	public double custoSWAP2IdaAdj2Ida(Node a, Node b)
	{

		 if(a.next.next!=b&&a.prev!=b.next)
		 {
			 return -(instancia.dist(a.prev.name,a.name)+instancia.dist(a.next.name,a.next.next.name)+instancia.dist(b.prev.name,b.name)+instancia.dist(b.next.name,b.next.next.name))+				
					 (instancia.dist(b.prev.name,a.name)+instancia.dist(a.next.name,b.next.next.name)+instancia.dist(a.prev.name,b.name)+instancia.dist(b.next.name,a.next.next.name));
		 }
		 else
		 {
			 if(a.next.next==b)
				 return -(instancia.dist(a.prev.name,a.name)+instancia.dist(a.next.name,a.next.next.name)+instancia.dist(b.next.name,b.next.next.name))+				
						(instancia.dist(a.next.name,b.next.next.name)+instancia.dist(a.prev.name,b.name)+instancia.dist(b.next.name,a.name));
			 else
				 return -(instancia.dist(b.prev.name,b.name)+instancia.dist(b.next.name,b.next.next.name)+instancia.dist(a.next.name,a.next.next.name))+				
							(instancia.dist(b.next.name,a.next.next.name)+instancia.dist(b.prev.name,a.name)+instancia.dist(a.next.name,b.name));
		 }
	}
	 
	public double custoSWAP2IdaAdj2Volta(Node a, Node b)
	{
		 if(a.next.next!=b&&a.prev!=b.next)
		 {
			 return -(instancia.dist(a.prev.name,a.name)+instancia.dist(a.next.name,a.next.next.name)+instancia.dist(b.prev.name,b.name)+instancia.dist(b.next.name,b.next.next.name))+				
					 (instancia.dist(b.prev.name,a.name)+instancia.dist(a.next.name,b.next.next.name)+instancia.dist(a.prev.name,b.next.name)+instancia.dist(b.name,a.next.next.name));
		 }
		 else
		 {
			 if(a.next.next==b)
				 return -(instancia.dist(a.prev.name,a.name)+instancia.dist(a.next.name,a.next.next.name)+instancia.dist(b.next.name,b.next.next.name))+				
						(instancia.dist(a.next.name,b.next.next.name)+instancia.dist(a.prev.name,b.next.name)+instancia.dist(b.name,a.name));
 			 else
 				 return -(instancia.dist(b.prev.name,b.name)+instancia.dist(b.next.name,b.next.next.name)+instancia.dist(a.next.name,a.next.next.name))+				
 						(instancia.dist(b.name,a.next.next.name)+instancia.dist(b.prev.name,a.name)+instancia.dist(a.next.name,b.next.name));
		 }
	}
	 
	public double custoSWAP2VoltaAdj2Ida(Node a, Node b)
	{
		 if(a.next.next!=b&&a.prev!=b.next)
		 {
			return 	-(instancia.dist(a.prev.name,a.name)+instancia.dist(a.next.name,a.next.next.name)+instancia.dist(b.prev.name,b.name)+instancia.dist(b.next.name,b.next.next.name))+				
					(instancia.dist(b.prev.name,a.next.name)+instancia.dist(a.name,b.next.next.name)+instancia.dist(a.prev.name,b.name)+instancia.dist(b.next.name,a.next.next.name));
		 }
		 else
		 {
			 if(a.next.next==b)
				 return	-(instancia.dist(a.prev.name,a.name)+instancia.dist(a.next.name,a.next.next.name)+instancia.dist(b.next.name,b.next.next.name))+				
						(instancia.dist(a.name,b.next.next.name)+instancia.dist(a.prev.name,b.name)+instancia.dist(b.next.name,a.next.name));
			 else
				 return	-(instancia.dist(b.prev.name,b.name)+instancia.dist(b.next.name,b.next.next.name)+instancia.dist(a.next.name,a.next.next.name))+				
						(instancia.dist(a.name,b.name)+instancia.dist(b.prev.name,a.next.name)+instancia.dist(b.next.name,a.next.next.name));
		 }
	}
	 
	public double custoSWAP2VoltaAdj2Volta(Node a, Node b)
	{
		 if(a.next.next!=b&&a.prev!=b.next)
		 {
			 return 	-(instancia.dist(a.prev.name,a.name)+instancia.dist(a.next.name,a.next.next.name)+instancia.dist(b.prev.name,b.name)+instancia.dist(b.next.name,b.next.next.name))+				
					 (instancia.dist(b.prev.name,a.next.name)+instancia.dist(a.name,b.next.next.name)+instancia.dist(a.prev.name,b.next.name)+instancia.dist(b.name,a.next.next.name));
		 }
		 else
		 {
			 if(a.next.next==b)
				 return -(instancia.dist(a.prev.name,a.name)+instancia.dist(a.next.name,a.next.next.name)+instancia.dist(b.next.name,b.next.next.name))+				
						(instancia.dist(a.name,b.next.next.name)+instancia.dist(a.prev.name,b.next.name)+instancia.dist(b.name,a.next.name));
		 	else
		 		return -(instancia.dist(b.prev.name,b.name)+instancia.dist(b.next.name,b.next.next.name)+instancia.dist(a.next.name,a.next.next.name))+				
						(instancia.dist(b.name,a.next.next.name)+instancia.dist(b.prev.name,a.next.name)+instancia.dist(a.name,b.next.name));
		 }
	}
	 
	public double custoSWAP3Adj1(Node a, Node b)
	{
	 	if(a.next.next.next!=b&&a.prev!=b)
	 	{
	 		return 	-(instancia.dist(a.prev.name,a.name)+instancia.dist(a.next.next.name,a.next.next.next.name)+instancia.dist(b.prev.name,b.name)+instancia.dist(b.name,b.next.name))+				
	 				(instancia.dist(b.prev.name,a.name)+instancia.dist(a.next.next.name,b.next.name)+instancia.dist(a.prev.name,b.name)+instancia.dist(b.name,a.next.next.next.name));
	 	}
		else
		{
			if(a.next.next.next==b)
				return 	-(instancia.dist(a.name,a.prev.name)+instancia.dist(a.next.next.name,a.next.next.next.name)+instancia.dist(b.name,b.next.name))+
						(instancia.dist(a.name,b.name)+instancia.dist(a.next.next.name,b.next.name)+instancia.dist(b.name,a.prev.name));
			else
				return 	-(instancia.dist(b.name,b.prev.name)+instancia.dist(a.next.next.name,a.next.next.next.name)+instancia.dist(a.name,b.name))+
						(instancia.dist(b.name,a.next.next.next.name)+instancia.dist(a.next.next.name,b.name)+instancia.dist(a.name,b.prev.name));
		}
	}
	
	public double custoSWAP3Adj1Ivertido(Node a, Node b)
	{
		 if(a.next.next.next!=b&&a.prev!=b)
		 {
			 return 	-(instancia.dist(a.prev.name,a.name)+instancia.dist(a.next.next.name,a.next.next.next.name)+instancia.dist(b.prev.name,b.name)+instancia.dist(b.name,b.next.name))+				
					 (instancia.dist(b.prev.name,a.next.next.name)+instancia.dist(a.name,b.next.name)+instancia.dist(a.prev.name,b.name)+instancia.dist(b.name,a.next.next.next.name));
		 }
		 else
		 {
			 if(a.next.next.next==b)
					return 	-(instancia.dist(a.name,a.prev.name)+instancia.dist(a.next.next.name,a.next.next.next.name)+instancia.dist(b.name,b.next.name))+
							(instancia.dist(a.next.next.name,b.name)+instancia.dist(a.name,b.next.name)+instancia.dist(b.name,a.prev.name));
				else
					return 	-(instancia.dist(b.name,b.prev.name)+instancia.dist(a.next.next.name,a.next.next.next.name)+instancia.dist(a.name,b.name))+
							(instancia.dist(b.name,a.name)+instancia.dist(b.name,a.next.next.next.name)+instancia.dist(a.next.next.name,b.prev.name));
		 }
	}
	 
	public double custoSWAP3IdaAdj2Ida(Node a, Node b)
	{

		 if(a.next.next.next!=b&&a.prev!=b.next)
		 {
			 return -(instancia.dist(a.prev.name,a.name)+instancia.dist(a.next.next.name,a.next.next.next.name)+instancia.dist(b.prev.name,b.name)+instancia.dist(b.next.name,b.next.next.name))+				
					 (instancia.dist(b.prev.name,a.name)+instancia.dist(a.next.next.name,b.next.next.name)+instancia.dist(a.prev.name,b.name)+instancia.dist(b.next.name,a.next.next.next.name));
		 }
		 else
		 {
			 if(a.next.next.next==b)
				 return -(instancia.dist(a.prev.name,a.name)+instancia.dist(a.next.next.name,a.next.next.next.name)+instancia.dist(b.next.name,b.next.next.name))+				
						(instancia.dist(a.next.next.name,b.next.next.name)+instancia.dist(a.prev.name,b.name)+instancia.dist(b.next.name,a.name));
			 else
				 return -(instancia.dist(b.prev.name,b.name)+instancia.dist(b.next.name,b.next.next.name)+instancia.dist(a.next.next.name,a.next.next.next.name))+				
							(instancia.dist(b.next.name,a.next.next.next.name)+instancia.dist(b.prev.name,a.name)+instancia.dist(a.next.next.name,b.name));
		 }
	}
	 
	public double custoSWAP3IdaAdj2Volta(Node a, Node b)
	{
		 if(a.next.next.next!=b&&a.prev!=b.next)
		 {
			 return -(instancia.dist(a.prev.name,a.name)+instancia.dist(a.next.next.name,a.next.next.next.name)+instancia.dist(b.prev.name,b.name)+instancia.dist(b.next.name,b.next.next.name))+				
					 (instancia.dist(b.prev.name,a.name)+instancia.dist(a.next.next.name,b.next.next.name)+instancia.dist(a.prev.name,b.next.name)+instancia.dist(b.name,a.next.next.next.name));
		 }
		 else
		 {
			 if(a.next.next.next==b)
				 return -(instancia.dist(a.prev.name,a.name)+instancia.dist(a.next.next.name,a.next.next.next.name)+instancia.dist(b.next.name,b.next.next.name))+				
						(instancia.dist(a.next.next.name,b.next.next.name)+instancia.dist(a.prev.name,b.next.name)+instancia.dist(b.name,a.name));
 			 else
 				 return -(instancia.dist(b.prev.name,b.name)+instancia.dist(b.next.name,b.next.next.name)+instancia.dist(a.next.next.name,a.next.next.next.name))+	
  						(instancia.dist(b.next.name,a.next.next.next.name)+instancia.dist(b.prev.name,a.name)+instancia.dist(a.next.next.name,b.name));

// 						(instancia.dist(b.nome,a.prox.prox.prox.nome)+instancia.dist(b.ant.nome,a.nome)+instancia.dist(a.prox.prox.nome,b.prox.nome));
		 }
	}
	 
	public double custoSWAP3VoltaAdj2Ida(Node a, Node b)
	{
		 if(a.next.next.next!=b&&a.prev!=b.next)
		 {
			return 	-(instancia.dist(a.prev.name,a.name)+instancia.dist(a.next.next.name,a.next.next.next.name)+instancia.dist(b.prev.name,b.name)+instancia.dist(b.next.name,b.next.next.name))+				
					(instancia.dist(b.prev.name,a.next.next.name)+instancia.dist(a.name,b.next.next.name)+instancia.dist(a.prev.name,b.name)+instancia.dist(b.next.name,a.next.next.next.name));
		 }
		 else
		 {
			 if(a.next.next.next==b)
				 return	-(instancia.dist(a.prev.name,a.name)+instancia.dist(a.next.next.name,a.next.next.next.name)+instancia.dist(b.next.name,b.next.next.name))+				
						(instancia.dist(a.name,b.next.next.name)+instancia.dist(a.prev.name,b.name)+instancia.dist(b.next.name,a.next.next.name));
			 else
				 return	-(instancia.dist(b.prev.name,b.name)+instancia.dist(b.next.name,b.next.next.name)+instancia.dist(a.next.next.name,a.next.next.next.name))+				
						(instancia.dist(a.name,b.name)+instancia.dist(b.prev.name,a.next.next.name)+instancia.dist(b.next.name,a.next.next.next.name));
		 }
	}
	 
	public double custoSWAP3VoltaAdj2Volta(Node a, Node b)
	{
		 if(a.next.next.next!=b&&a.prev!=b.next)
		 {
			 return 	-(instancia.dist(a.prev.name,a.name)+instancia.dist(a.next.next.name,a.next.next.next.name)+instancia.dist(b.prev.name,b.name)+instancia.dist(b.next.name,b.next.next.name))+				
					 (instancia.dist(b.prev.name,a.next.next.name)+instancia.dist(a.name,b.next.next.name)+instancia.dist(a.prev.name,b.next.name)+instancia.dist(b.name,a.next.next.next.name));
		 }
		 else
		 {
			 if(a.next.next.next==b)
				 return -(instancia.dist(a.prev.name,a.name)+instancia.dist(a.next.next.name,a.next.next.next.name)+instancia.dist(b.next.name,b.next.next.name))+				
						(instancia.dist(a.name,b.next.next.name)+instancia.dist(a.prev.name,b.next.name)+instancia.dist(b.name,a.next.next.name));
		 	else
		 		return -(instancia.dist(b.prev.name,b.name)+instancia.dist(b.next.name,b.next.next.name)+instancia.dist(a.next.next.name,a.next.next.next.name))+				
						(instancia.dist(b.name,a.next.next.next.name)+instancia.dist(b.prev.name,a.next.next.name)+instancia.dist(a.name,b.next.name));
		 }
	}
	
	public double custoSWAP3IdaAdj3Ida(Node a, Node b)
	{

		 if(a.next.next.next!=b&&a!=b.next.next.next)
		 {
			 return -(instancia.dist(a.prev.name,a.name)+instancia.dist(a.next.next.name,a.next.next.next.name)+instancia.dist(b.prev.name,b.name)+instancia.dist(b.next.next.name,b.next.next.next.name))+				
					 (instancia.dist(b.prev.name,a.name)+instancia.dist(a.next.next.name,b.next.next.next.name)+instancia.dist(a.prev.name,b.name)+instancia.dist(b.next.next.name,a.next.next.next.name));
		 }
		 else
		 {
			 if(a.next.next.next==b)
				 return -(instancia.dist(a.prev.name,a.name)+instancia.dist(a.next.next.name,a.next.next.next.name)+instancia.dist(b.next.next.name,b.next.next.next.name))+				
						(instancia.dist(a.next.next.name,b.next.next.next.name)+instancia.dist(a.prev.name,b.name)+instancia.dist(b.next.next.name,a.name));
			 else
				 return -(instancia.dist(b.prev.name,b.name)+instancia.dist(b.next.next.name,b.next.next.next.name)+instancia.dist(a.next.next.name,a.next.next.next.name))+				
							(instancia.dist(b.next.next.name,a.next.next.next.name)+instancia.dist(b.prev.name,a.name)+instancia.dist(a.next.next.name,b.name));
		 }
	}
	 
	public double custoSWAP3IdaAdj3Volta(Node a, Node b)
	{
		 if(a.next.next.next!=b&&a!=b.next.next.next)
		 {
			 return -(instancia.dist(a.prev.name,a.name)+instancia.dist(a.next.next.name,a.next.next.next.name)+instancia.dist(b.prev.name,b.name)+instancia.dist(b.next.next.name,b.next.next.next.name))+				
					 (instancia.dist(b.prev.name,a.name)+instancia.dist(a.next.next.name,b.next.next.next.name)+instancia.dist(a.prev.name,b.next.next.name)+instancia.dist(b.name,a.next.next.next.name));
		 }
		 else
		 {
			 if(a.next.next.next==b)
				 return -(instancia.dist(a.prev.name,a.name)+instancia.dist(a.next.next.name,a.next.next.next.name)+instancia.dist(b.next.next.name,b.next.next.next.name))+				
						(instancia.dist(a.next.next.name,b.next.next.next.name)+instancia.dist(a.prev.name,b.next.next.name)+instancia.dist(b.name,a.name));
 			 else
 				 return -(instancia.dist(b.prev.name,b.name)+instancia.dist(b.next.next.name,b.next.next.next.name)+instancia.dist(a.next.next.name,a.next.next.next.name))+				
   						(instancia.dist(b.next.next.name,a.next.next.next.name)+instancia.dist(b.prev.name,a.name)+instancia.dist(a.next.next.name,b.name));

// 						 (instancia.dist(b.nome,a.prox.prox.prox.nome)+instancia.dist(b.ant.nome,a.nome)+instancia.dist(a.prox.prox.nome,b.prox.prox.nome));
		 }
	}
	 
	public double custoSWAP3VoltaAdj3Ida(Node a, Node b)
	{
		 if(a.next.next.next!=b&&a!=b.next.next.next)
		 {
			return 	-(instancia.dist(a.prev.name,a.name)+instancia.dist(a.next.next.name,a.next.next.next.name)+instancia.dist(b.prev.name,b.name)+instancia.dist(b.next.next.name,b.next.next.next.name))+				
					(instancia.dist(b.prev.name,a.next.next.name)+instancia.dist(a.name,b.next.next.next.name)+instancia.dist(a.prev.name,b.name)+instancia.dist(b.next.next.name,a.next.next.next.name));
		 }
		 else
		 {
			 if(a.next.next.next==b)
				 return	-(instancia.dist(a.prev.name,a.name)+instancia.dist(a.next.next.name,a.next.next.next.name)+instancia.dist(b.next.next.name,b.next.next.next.name))+				
						(instancia.dist(a.name,b.next.next.next.name)+instancia.dist(a.prev.name,b.name)+instancia.dist(b.next.next.name,a.next.next.name));
			 else
				 return	-(instancia.dist(b.prev.name,b.name)+instancia.dist(b.next.next.name,b.next.next.next.name)+instancia.dist(a.next.next.name,a.next.next.next.name))+				
						(instancia.dist(a.name,b.name)+instancia.dist(b.prev.name,a.next.next.name)+instancia.dist(b.next.next.name,a.next.next.next.name));
		 }
	}
	 
	public double custoSWAP3VoltaAdj3Volta(Node a, Node b)
	{
		 if(a.next.next.next!=b&&a!=b.next.next.next)
		 {
			 return 	-(instancia.dist(a.prev.name,a.name)+instancia.dist(a.next.next.name,a.next.next.next.name)+instancia.dist(b.prev.name,b.name)+instancia.dist(b.next.next.name,b.next.next.next.name))+				
					 (instancia.dist(b.prev.name,a.next.next.name)+instancia.dist(a.name,b.next.next.next.name)+instancia.dist(a.prev.name,b.next.next.name)+instancia.dist(b.name,a.next.next.next.name));
		 }
		 else
		 {
			 if(a.next.next.next==b)
				 return -(instancia.dist(a.prev.name,a.name)+instancia.dist(a.next.next.name,a.next.next.next.name)+instancia.dist(b.next.next.name,b.next.next.next.name))+				
						(instancia.dist(a.name,b.next.next.next.name)+instancia.dist(a.prev.name,b.next.next.name)+instancia.dist(b.name,a.next.next.name));
		 	else
		 		return -(instancia.dist(b.prev.name,b.name)+instancia.dist(b.next.next.name,b.next.next.next.name)+instancia.dist(a.next.next.name,a.next.next.next.name))+				
						(instancia.dist(b.name,a.next.next.next.name)+instancia.dist(b.prev.name,a.next.next.name)+instancia.dist(a.name,b.next.next.name));
		 }
	}
	
//		-------------------------------CROSS------------------------------

	public double custoCross(Node a, Node b)
	{
		 return -(instancia.dist(a.name,a.next.name)+instancia.dist(b.name,b.next.name))
				 +(instancia.dist(a.name,b.next.name)+instancia.dist(b.name,a.next.name));
	}
	 
	public double custoCrossInvertido(Node a, Node b)
	{
		 return -(instancia.dist(a.name,a.next.name)+instancia.dist(b.name,b.next.name))
				 +(instancia.dist(a.name,b.name)+instancia.dist(b.next.name,a.next.name));
	}
	 
	public double custo2Opt(Node a, Node b)
	{
		return 	-(instancia.dist(a.name,a.next.name)+instancia.dist(b.name,b.next.name))+				
				(instancia.dist(a.name,b.name)+instancia.dist(a.next.name,b.next.name));
	}
	 
}
