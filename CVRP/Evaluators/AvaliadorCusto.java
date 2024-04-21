package Evaluators;

import Data.Instance;
import Solution.Node;

public class AvaliadorCusto 
{
	Instance instance;
	double custo;
	public AvaliadorCusto(Instance instance)
	{
		this.instance=instance;
	}
	
//	-------------------------------SHIFT------------------------------
	
	public double custoSHIFT(Node a, Node b)
	{
		 return instance.dist(a.prev.name,a.next.name)-instance.dist(a.name,a.prev.name)-instance.dist(a.name,a.next.name)+
				instance.dist(a.name,b.name)+instance.dist(a.name,b.next.name)-instance.dist(b.name,b.next.name);
	}
		
	public double custoSHIFT2Adj(Node a, Node b)
	{
	return -instance.dist(a.prev.name,a.name)-instance.dist(a.next.name,a.next.next.name)-instance.dist(b.name,b.next.name)
			+instance.dist(a.prev.name,a.next.next.name)+instance.dist(b.name,a.name)+instance.dist(a.next.name,b.next.name);
	}
	 
	public double custoSHIFT2AdjIvertido(Node a, Node b)
	{
		return 	-(instance.dist(a.prev.name,a.name)+instance.dist(a.next.name,a.next.next.name)+instance.dist(b.name,b.next.name))+
				instance.dist(a.prev.name,a.next.next.name)+instance.dist(b.name,a.next.name)+instance.dist(a.name,b.next.name);
	}
	 
	public double custoSHIFT3Adj(Node a, Node b)
	{
		return 	-instance.dist(a.prev.name,a.name)-instance.dist(a.next.next.name,a.next.next.next.name)-instance.dist(b.name,b.next.name)
				+instance.dist(a.prev.name,a.next.next.next.name)+instance.dist(b.name,a.name)+instance.dist(a.next.next.name,b.next.name);
	}
	 
	public double custoSHIFT3AdjIvertido(Node a, Node b)
	{
		return 	-(instance.dist(a.prev.name,a.name)+instance.dist(a.next.next.name,a.next.next.next.name)+instance.dist(b.name,b.next.name))+
				instance.dist(a.prev.name,a.next.next.next.name)+instance.dist(b.name,a.next.next.name)+instance.dist(a.name,b.next.name);
	}

//	-------------------------------SWAP------------------------------
	
	public double custoSWAP(Node a, Node b)
	{
		if(a.next!=b&&a.prev!=b)
		{
			return 	-(instance.dist(a.name,a.prev.name)+instance.dist(a.name,a.next.name)+
					instance.dist(b.name,b.prev.name)+instance.dist(b.name,b.next.name))+				
					(instance.dist(a.name,b.prev.name)+instance.dist(a.name,b.next.name)+
					instance.dist(b.name,a.prev.name)+instance.dist(b.name,a.next.name));
		}
		else
		{
			if(a.next==b)
				return 	-(instance.dist(a.name,a.prev.name)+instance.dist(b.name,b.next.name))+
					(instance.dist(a.name,b.next.name)+instance.dist(b.name,a.prev.name));
			else
				return 	-(instance.dist(b.name,b.prev.name)+instance.dist(a.name,a.next.name))+
						(instance.dist(b.name,a.next.name)+instance.dist(a.name,b.prev.name));
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
				
				return	-instance.dist(antA.name,b.name)
						-instance.dist(b.name,b.next.name)
						+instance.dist(antA.name,a.name)
						+instance.dist(b.next.name,a.name)
						
						-instance.dist(antB.name,antB.next.name)
						+instance.dist(antB.name,b.name)
						+instance.dist(b.name,antB.next.name)
						
						-instance.dist(a.prev.name,a.name)
						-instance.dist(a.name,a.next.name)
						+instance.dist(a.prev.name,a.next.name);
						
			}
			
			if(antA.next.name!=b.name&&antB.next.name==a.name)
			{
//				return Double.MAX_VALUE;
				
				return	-instance.dist(antB.name,a.name)
						-instance.dist(a.name,a.next.name)
						+instance.dist(antB.name,b.name)
						+instance.dist(a.next.name,b.name)
						
						-instance.dist(antA.name,antA.next.name)
						+instance.dist(antA.name,a.name)
						+instance.dist(a.name,antA.next.name)
						
						-instance.dist(b.prev.name,b.name)
						-instance.dist(b.name,b.next.name)
						+instance.dist(b.prev.name,b.next.name);
			}
			
			custoSWAP(a, b);
		}
		return Double.MAX_VALUE;
	}
	
	public double custoSWAP2Adj1(Node a, Node b)
	{
	 	if(a.next.next!=b&&a.prev!=b)
	 	{
	 		return 	-(instance.dist(a.prev.name,a.name)+instance.dist(a.next.name,a.next.next.name)+instance.dist(b.prev.name,b.name)+instance.dist(b.name,b.next.name))+				
	 				(instance.dist(b.prev.name,a.name)+instance.dist(a.next.name,b.next.name)+instance.dist(a.prev.name,b.name)+instance.dist(b.name,a.next.next.name));
	 	}
		else
		{
			if(a.next.next==b)
				return 	-(instance.dist(a.name,a.prev.name)+instance.dist(a.next.name,a.next.next.name)+instance.dist(b.name,b.next.name))+
						(instance.dist(a.name,b.name)+instance.dist(a.next.name,b.next.name)+instance.dist(b.name,a.prev.name));
			else
				return 	-(instance.dist(b.name,b.prev.name)+instance.dist(b.name,a.name)+instance.dist(a.next.name,a.next.next.name))+
						(instance.dist(b.name,a.next.name)+instance.dist(b.name,a.next.next.name)+instance.dist(a.name,b.prev.name));
		}
	}
	 
	public double custoSWAP2Adj1Ivertido(Node a, Node b)
	{
		 if(a.next.next!=b&&a.prev!=b)
		 {
			 return 	-(instance.dist(a.prev.name,a.name)+instance.dist(a.next.name,a.next.next.name)+instance.dist(b.prev.name,b.name)+instance.dist(b.name,b.next.name))+				
					 (instance.dist(b.prev.name,a.next.name)+instance.dist(a.name,b.next.name)+instance.dist(a.prev.name,b.name)+instance.dist(b.name,a.next.next.name));
		 }
		 else
		 {
			 if(a.next.next==b)
					return 	-(instance.dist(a.name,a.prev.name)+instance.dist(a.next.name,a.next.next.name)+instance.dist(b.name,b.next.name))+
							(instance.dist(a.next.name,b.name)+instance.dist(a.name,b.next.name)+instance.dist(b.name,a.prev.name));
				else
					return 	-(instance.dist(b.name,b.prev.name)+instance.dist(b.name,a.name)+instance.dist(a.next.name,a.next.next.name))+
							(instance.dist(b.name,a.next.next.name)+instance.dist(b.name,a.next.name)+instance.dist(a.name,b.prev.name));
		 }
	}
	 
	public double custoSWAP2IdaAdj2Ida(Node a, Node b)
	{

		 if(a.next.next!=b&&a.prev!=b.next)
		 {
			 return -(instance.dist(a.prev.name,a.name)+instance.dist(a.next.name,a.next.next.name)+instance.dist(b.prev.name,b.name)+instance.dist(b.next.name,b.next.next.name))+				
					 (instance.dist(b.prev.name,a.name)+instance.dist(a.next.name,b.next.next.name)+instance.dist(a.prev.name,b.name)+instance.dist(b.next.name,a.next.next.name));
		 }
		 else
		 {
			 if(a.next.next==b)
				 return -(instance.dist(a.prev.name,a.name)+instance.dist(a.next.name,a.next.next.name)+instance.dist(b.next.name,b.next.next.name))+				
						(instance.dist(a.next.name,b.next.next.name)+instance.dist(a.prev.name,b.name)+instance.dist(b.next.name,a.name));
			 else
				 return -(instance.dist(b.prev.name,b.name)+instance.dist(b.next.name,b.next.next.name)+instance.dist(a.next.name,a.next.next.name))+				
							(instance.dist(b.next.name,a.next.next.name)+instance.dist(b.prev.name,a.name)+instance.dist(a.next.name,b.name));
		 }
	}
	 
	public double custoSWAP2IdaAdj2Volta(Node a, Node b)
	{
		 if(a.next.next!=b&&a.prev!=b.next)
		 {
			 return -(instance.dist(a.prev.name,a.name)+instance.dist(a.next.name,a.next.next.name)+instance.dist(b.prev.name,b.name)+instance.dist(b.next.name,b.next.next.name))+				
					 (instance.dist(b.prev.name,a.name)+instance.dist(a.next.name,b.next.next.name)+instance.dist(a.prev.name,b.next.name)+instance.dist(b.name,a.next.next.name));
		 }
		 else
		 {
			 if(a.next.next==b)
				 return -(instance.dist(a.prev.name,a.name)+instance.dist(a.next.name,a.next.next.name)+instance.dist(b.next.name,b.next.next.name))+				
						(instance.dist(a.next.name,b.next.next.name)+instance.dist(a.prev.name,b.next.name)+instance.dist(b.name,a.name));
 			 else
 				 return -(instance.dist(b.prev.name,b.name)+instance.dist(b.next.name,b.next.next.name)+instance.dist(a.next.name,a.next.next.name))+				
 						(instance.dist(b.name,a.next.next.name)+instance.dist(b.prev.name,a.name)+instance.dist(a.next.name,b.next.name));
		 }
	}
	 
	public double custoSWAP2VoltaAdj2Ida(Node a, Node b)
	{
		 if(a.next.next!=b&&a.prev!=b.next)
		 {
			return 	-(instance.dist(a.prev.name,a.name)+instance.dist(a.next.name,a.next.next.name)+instance.dist(b.prev.name,b.name)+instance.dist(b.next.name,b.next.next.name))+				
					(instance.dist(b.prev.name,a.next.name)+instance.dist(a.name,b.next.next.name)+instance.dist(a.prev.name,b.name)+instance.dist(b.next.name,a.next.next.name));
		 }
		 else
		 {
			 if(a.next.next==b)
				 return	-(instance.dist(a.prev.name,a.name)+instance.dist(a.next.name,a.next.next.name)+instance.dist(b.next.name,b.next.next.name))+				
						(instance.dist(a.name,b.next.next.name)+instance.dist(a.prev.name,b.name)+instance.dist(b.next.name,a.next.name));
			 else
				 return	-(instance.dist(b.prev.name,b.name)+instance.dist(b.next.name,b.next.next.name)+instance.dist(a.next.name,a.next.next.name))+				
						(instance.dist(a.name,b.name)+instance.dist(b.prev.name,a.next.name)+instance.dist(b.next.name,a.next.next.name));
		 }
	}
	 
	public double custoSWAP2VoltaAdj2Volta(Node a, Node b)
	{
		 if(a.next.next!=b&&a.prev!=b.next)
		 {
			 return 	-(instance.dist(a.prev.name,a.name)+instance.dist(a.next.name,a.next.next.name)+instance.dist(b.prev.name,b.name)+instance.dist(b.next.name,b.next.next.name))+				
					 (instance.dist(b.prev.name,a.next.name)+instance.dist(a.name,b.next.next.name)+instance.dist(a.prev.name,b.next.name)+instance.dist(b.name,a.next.next.name));
		 }
		 else
		 {
			 if(a.next.next==b)
				 return -(instance.dist(a.prev.name,a.name)+instance.dist(a.next.name,a.next.next.name)+instance.dist(b.next.name,b.next.next.name))+				
						(instance.dist(a.name,b.next.next.name)+instance.dist(a.prev.name,b.next.name)+instance.dist(b.name,a.next.name));
		 	else
		 		return -(instance.dist(b.prev.name,b.name)+instance.dist(b.next.name,b.next.next.name)+instance.dist(a.next.name,a.next.next.name))+				
						(instance.dist(b.name,a.next.next.name)+instance.dist(b.prev.name,a.next.name)+instance.dist(a.name,b.next.name));
		 }
	}
	 
	public double custoSWAP3Adj1(Node a, Node b)
	{
	 	if(a.next.next.next!=b&&a.prev!=b)
	 	{
	 		return 	-(instance.dist(a.prev.name,a.name)+instance.dist(a.next.next.name,a.next.next.next.name)+instance.dist(b.prev.name,b.name)+instance.dist(b.name,b.next.name))+				
	 				(instance.dist(b.prev.name,a.name)+instance.dist(a.next.next.name,b.next.name)+instance.dist(a.prev.name,b.name)+instance.dist(b.name,a.next.next.next.name));
	 	}
		else
		{
			if(a.next.next.next==b)
				return 	-(instance.dist(a.name,a.prev.name)+instance.dist(a.next.next.name,a.next.next.next.name)+instance.dist(b.name,b.next.name))+
						(instance.dist(a.name,b.name)+instance.dist(a.next.next.name,b.next.name)+instance.dist(b.name,a.prev.name));
			else
				return 	-(instance.dist(b.name,b.prev.name)+instance.dist(a.next.next.name,a.next.next.next.name)+instance.dist(a.name,b.name))+
						(instance.dist(b.name,a.next.next.next.name)+instance.dist(a.next.next.name,b.name)+instance.dist(a.name,b.prev.name));
		}
	}
	
	public double custoSWAP3Adj1Ivertido(Node a, Node b)
	{
		 if(a.next.next.next!=b&&a.prev!=b)
		 {
			 return 	-(instance.dist(a.prev.name,a.name)+instance.dist(a.next.next.name,a.next.next.next.name)+instance.dist(b.prev.name,b.name)+instance.dist(b.name,b.next.name))+				
					 (instance.dist(b.prev.name,a.next.next.name)+instance.dist(a.name,b.next.name)+instance.dist(a.prev.name,b.name)+instance.dist(b.name,a.next.next.next.name));
		 }
		 else
		 {
			 if(a.next.next.next==b)
					return 	-(instance.dist(a.name,a.prev.name)+instance.dist(a.next.next.name,a.next.next.next.name)+instance.dist(b.name,b.next.name))+
							(instance.dist(a.next.next.name,b.name)+instance.dist(a.name,b.next.name)+instance.dist(b.name,a.prev.name));
				else
					return 	-(instance.dist(b.name,b.prev.name)+instance.dist(a.next.next.name,a.next.next.next.name)+instance.dist(a.name,b.name))+
							(instance.dist(b.name,a.name)+instance.dist(b.name,a.next.next.next.name)+instance.dist(a.next.next.name,b.prev.name));
		 }
	}
	 
	public double custoSWAP3IdaAdj2Ida(Node a, Node b)
	{

		 if(a.next.next.next!=b&&a.prev!=b.next)
		 {
			 return -(instance.dist(a.prev.name,a.name)+instance.dist(a.next.next.name,a.next.next.next.name)+instance.dist(b.prev.name,b.name)+instance.dist(b.next.name,b.next.next.name))+				
					 (instance.dist(b.prev.name,a.name)+instance.dist(a.next.next.name,b.next.next.name)+instance.dist(a.prev.name,b.name)+instance.dist(b.next.name,a.next.next.next.name));
		 }
		 else
		 {
			 if(a.next.next.next==b)
				 return -(instance.dist(a.prev.name,a.name)+instance.dist(a.next.next.name,a.next.next.next.name)+instance.dist(b.next.name,b.next.next.name))+				
						(instance.dist(a.next.next.name,b.next.next.name)+instance.dist(a.prev.name,b.name)+instance.dist(b.next.name,a.name));
			 else
				 return -(instance.dist(b.prev.name,b.name)+instance.dist(b.next.name,b.next.next.name)+instance.dist(a.next.next.name,a.next.next.next.name))+				
							(instance.dist(b.next.name,a.next.next.next.name)+instance.dist(b.prev.name,a.name)+instance.dist(a.next.next.name,b.name));
		 }
	}
	 
	public double custoSWAP3IdaAdj2Volta(Node a, Node b)
	{
		 if(a.next.next.next!=b&&a.prev!=b.next)
		 {
			 return -(instance.dist(a.prev.name,a.name)+instance.dist(a.next.next.name,a.next.next.next.name)+instance.dist(b.prev.name,b.name)+instance.dist(b.next.name,b.next.next.name))+				
					 (instance.dist(b.prev.name,a.name)+instance.dist(a.next.next.name,b.next.next.name)+instance.dist(a.prev.name,b.next.name)+instance.dist(b.name,a.next.next.next.name));
		 }
		 else
		 {
			 if(a.next.next.next==b)
				 return -(instance.dist(a.prev.name,a.name)+instance.dist(a.next.next.name,a.next.next.next.name)+instance.dist(b.next.name,b.next.next.name))+				
						(instance.dist(a.next.next.name,b.next.next.name)+instance.dist(a.prev.name,b.next.name)+instance.dist(b.name,a.name));
 			 else
 				 return -(instance.dist(b.prev.name,b.name)+instance.dist(b.next.name,b.next.next.name)+instance.dist(a.next.next.name,a.next.next.next.name))+	
  						(instance.dist(b.next.name,a.next.next.next.name)+instance.dist(b.prev.name,a.name)+instance.dist(a.next.next.name,b.name));

		 }
	}
	 
	public double custoSWAP3VoltaAdj2Ida(Node a, Node b)
	{
		 if(a.next.next.next!=b&&a.prev!=b.next)
		 {
			return 	-(instance.dist(a.prev.name,a.name)+instance.dist(a.next.next.name,a.next.next.next.name)+instance.dist(b.prev.name,b.name)+instance.dist(b.next.name,b.next.next.name))+				
					(instance.dist(b.prev.name,a.next.next.name)+instance.dist(a.name,b.next.next.name)+instance.dist(a.prev.name,b.name)+instance.dist(b.next.name,a.next.next.next.name));
		 }
		 else
		 {
			 if(a.next.next.next==b)
				 return	-(instance.dist(a.prev.name,a.name)+instance.dist(a.next.next.name,a.next.next.next.name)+instance.dist(b.next.name,b.next.next.name))+				
						(instance.dist(a.name,b.next.next.name)+instance.dist(a.prev.name,b.name)+instance.dist(b.next.name,a.next.next.name));
			 else
				 return	-(instance.dist(b.prev.name,b.name)+instance.dist(b.next.name,b.next.next.name)+instance.dist(a.next.next.name,a.next.next.next.name))+				
						(instance.dist(a.name,b.name)+instance.dist(b.prev.name,a.next.next.name)+instance.dist(b.next.name,a.next.next.next.name));
		 }
	}
	 
	public double custoSWAP3VoltaAdj2Volta(Node a, Node b)
	{
		 if(a.next.next.next!=b&&a.prev!=b.next)
		 {
			 return 	-(instance.dist(a.prev.name,a.name)+instance.dist(a.next.next.name,a.next.next.next.name)+instance.dist(b.prev.name,b.name)+instance.dist(b.next.name,b.next.next.name))+				
					 (instance.dist(b.prev.name,a.next.next.name)+instance.dist(a.name,b.next.next.name)+instance.dist(a.prev.name,b.next.name)+instance.dist(b.name,a.next.next.next.name));
		 }
		 else
		 {
			 if(a.next.next.next==b)
				 return -(instance.dist(a.prev.name,a.name)+instance.dist(a.next.next.name,a.next.next.next.name)+instance.dist(b.next.name,b.next.next.name))+				
						(instance.dist(a.name,b.next.next.name)+instance.dist(a.prev.name,b.next.name)+instance.dist(b.name,a.next.next.name));
		 	else
		 		return -(instance.dist(b.prev.name,b.name)+instance.dist(b.next.name,b.next.next.name)+instance.dist(a.next.next.name,a.next.next.next.name))+				
						(instance.dist(b.name,a.next.next.next.name)+instance.dist(b.prev.name,a.next.next.name)+instance.dist(a.name,b.next.name));
		 }
	}
	
	public double custoSWAP3IdaAdj3Ida(Node a, Node b)
	{

		 if(a.next.next.next!=b&&a!=b.next.next.next)
		 {
			 return -(instance.dist(a.prev.name,a.name)+instance.dist(a.next.next.name,a.next.next.next.name)+instance.dist(b.prev.name,b.name)+instance.dist(b.next.next.name,b.next.next.next.name))+				
					 (instance.dist(b.prev.name,a.name)+instance.dist(a.next.next.name,b.next.next.next.name)+instance.dist(a.prev.name,b.name)+instance.dist(b.next.next.name,a.next.next.next.name));
		 }
		 else
		 {
			 if(a.next.next.next==b)
				 return -(instance.dist(a.prev.name,a.name)+instance.dist(a.next.next.name,a.next.next.next.name)+instance.dist(b.next.next.name,b.next.next.next.name))+				
						(instance.dist(a.next.next.name,b.next.next.next.name)+instance.dist(a.prev.name,b.name)+instance.dist(b.next.next.name,a.name));
			 else
				 return -(instance.dist(b.prev.name,b.name)+instance.dist(b.next.next.name,b.next.next.next.name)+instance.dist(a.next.next.name,a.next.next.next.name))+				
							(instance.dist(b.next.next.name,a.next.next.next.name)+instance.dist(b.prev.name,a.name)+instance.dist(a.next.next.name,b.name));
		 }
	}
	 
	public double custoSWAP3IdaAdj3Volta(Node a, Node b)
	{
		 if(a.next.next.next!=b&&a!=b.next.next.next)
		 {
			 return -(instance.dist(a.prev.name,a.name)+instance.dist(a.next.next.name,a.next.next.next.name)+instance.dist(b.prev.name,b.name)+instance.dist(b.next.next.name,b.next.next.next.name))+				
					 (instance.dist(b.prev.name,a.name)+instance.dist(a.next.next.name,b.next.next.next.name)+instance.dist(a.prev.name,b.next.next.name)+instance.dist(b.name,a.next.next.next.name));
		 }
		 else
		 {
			 if(a.next.next.next==b)
				 return -(instance.dist(a.prev.name,a.name)+instance.dist(a.next.next.name,a.next.next.next.name)+instance.dist(b.next.next.name,b.next.next.next.name))+				
						(instance.dist(a.next.next.name,b.next.next.next.name)+instance.dist(a.prev.name,b.next.next.name)+instance.dist(b.name,a.name));
 			 else
 				 return -(instance.dist(b.prev.name,b.name)+instance.dist(b.next.next.name,b.next.next.next.name)+instance.dist(a.next.next.name,a.next.next.next.name))+				
   						(instance.dist(b.next.next.name,a.next.next.next.name)+instance.dist(b.prev.name,a.name)+instance.dist(a.next.next.name,b.name));

		 }
	}
	 
	public double custoSWAP3VoltaAdj3Ida(Node a, Node b)
	{
		 if(a.next.next.next!=b&&a!=b.next.next.next)
		 {
			return 	-(instance.dist(a.prev.name,a.name)+instance.dist(a.next.next.name,a.next.next.next.name)+instance.dist(b.prev.name,b.name)+instance.dist(b.next.next.name,b.next.next.next.name))+				
					(instance.dist(b.prev.name,a.next.next.name)+instance.dist(a.name,b.next.next.next.name)+instance.dist(a.prev.name,b.name)+instance.dist(b.next.next.name,a.next.next.next.name));
		 }
		 else
		 {
			 if(a.next.next.next==b)
				 return	-(instance.dist(a.prev.name,a.name)+instance.dist(a.next.next.name,a.next.next.next.name)+instance.dist(b.next.next.name,b.next.next.next.name))+				
						(instance.dist(a.name,b.next.next.next.name)+instance.dist(a.prev.name,b.name)+instance.dist(b.next.next.name,a.next.next.name));
			 else
				 return	-(instance.dist(b.prev.name,b.name)+instance.dist(b.next.next.name,b.next.next.next.name)+instance.dist(a.next.next.name,a.next.next.next.name))+				
						(instance.dist(a.name,b.name)+instance.dist(b.prev.name,a.next.next.name)+instance.dist(b.next.next.name,a.next.next.next.name));
		 }
	}
	 
	public double custoSWAP3VoltaAdj3Volta(Node a, Node b)
	{
		 if(a.next.next.next!=b&&a!=b.next.next.next)
		 {
			 return 	-(instance.dist(a.prev.name,a.name)+instance.dist(a.next.next.name,a.next.next.next.name)+instance.dist(b.prev.name,b.name)+instance.dist(b.next.next.name,b.next.next.next.name))+				
					 (instance.dist(b.prev.name,a.next.next.name)+instance.dist(a.name,b.next.next.next.name)+instance.dist(a.prev.name,b.next.next.name)+instance.dist(b.name,a.next.next.next.name));
		 }
		 else
		 {
			 if(a.next.next.next==b)
				 return -(instance.dist(a.prev.name,a.name)+instance.dist(a.next.next.name,a.next.next.next.name)+instance.dist(b.next.next.name,b.next.next.next.name))+				
						(instance.dist(a.name,b.next.next.next.name)+instance.dist(a.prev.name,b.next.next.name)+instance.dist(b.name,a.next.next.name));
		 	else
		 		return -(instance.dist(b.prev.name,b.name)+instance.dist(b.next.next.name,b.next.next.next.name)+instance.dist(a.next.next.name,a.next.next.next.name))+				
						(instance.dist(b.name,a.next.next.next.name)+instance.dist(b.prev.name,a.next.next.name)+instance.dist(a.name,b.next.next.name));
		 }
	}
	
//		-------------------------------CROSS------------------------------

	public double custoCross(Node a, Node b)
	{
		 return -(instance.dist(a.name,a.next.name)+instance.dist(b.name,b.next.name))
				 +(instance.dist(a.name,b.next.name)+instance.dist(b.name,a.next.name));
	}
	 
	public double custoCrossInvertido(Node a, Node b)
	{
		 return -(instance.dist(a.name,a.next.name)+instance.dist(b.name,b.next.name))
				 +(instance.dist(a.name,b.name)+instance.dist(b.next.name,a.next.name));
	}
	 
	public double custo2Opt(Node a, Node b)
	{
		return 	-(instance.dist(a.name,a.next.name)+instance.dist(b.name,b.next.name))+				
				(instance.dist(a.name,b.name)+instance.dist(a.next.name,b.next.name));
	}
	 
}
