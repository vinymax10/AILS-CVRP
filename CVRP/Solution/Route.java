package Solution;

import Data.Instance;
import SearchMethod.Config;

public class Route implements Comparable<Route>
{
	public int  capacidade;
	public int deposito;

	public Node first;
	public boolean podeMelhorar;
	public boolean modified;
	public int demandaTotal=0;
	public double fRoute=0,prevF;
	double acrescimoF;
	public int numElements=0;
	public int nomeRoute;
	Node prev,next;
	int contCaminho;
	
	//busca local best
	public double lowestCost=0;
	Node aux;
	
	//------------------------------------------------
	public double cost,custoRemocao;
	public Node bestCusto;
	public double distancia,menorDist;
	//------------------------------------------------
	public boolean update;
	
    Instance instance;
    int limitAdj;
    
	public Route(Instance instance, Config config, Node deposito,int nomeRoute) 
	{
		this.instance=instance;
		this.capacidade = instance.getCapacity();
		this.deposito=deposito.name;
		this.nomeRoute=nomeRoute;
		this.first=null;
		this.limitAdj=config.getVarphi();
		addNoFinal(deposito.clone());
	}
	
    public Node findBestPosition(Node no)
	{
		bestCusto=first;
		aux=first.next;
		lowestCost=instance.dist(first.name,no.name)+instance.dist(no.name,first.next.name)-instance.dist(first.name,first.next.name);
		do
		{
			cost=instance.dist(aux.name,no.name)+instance.dist(no.name,aux.next.name)-instance.dist(aux.name,aux.next.name);
			if(cost<lowestCost)
			{
				lowestCost=cost;
				bestCusto=aux;
			}
			aux=aux.next;
		}
		while(aux!=first);
		return bestCusto;
	}
    
    public Node findBestPositionExceto(Node no,Node excecao)
  	{
  		bestCusto=first;
  		aux=first.next;
  		
  		lowestCost=instance.dist(first.name,no.name)+instance.dist(no.name,first.next.name)-instance.dist(first.name,first.next.name);
  		do
  		{
  			cost=instance.dist(aux.name,no.name)+instance.dist(no.name,aux.next.name)-instance.dist(aux.name,aux.next.name);
  			if(cost<lowestCost&&aux.name!=excecao.name)
  			{
  				lowestCost=cost;
  				bestCusto=aux;
  			}
  			aux=aux.next;
  		}
  		while(aux!=first);
  		return bestCusto;
  	}
    
    public Node findBestPositionExcetoKNN(Node no,Node excecao, Node solution[])
   	{
    	lowestCost=Double.MAX_VALUE;
    	
   		bestCusto=first;
   		
   		if(numElements>limitAdj)
   		{
   			for (int j = 0; j < limitAdj; j++) 
   			{
   	   			if(no.getKnn()[j]==0)
   	   			{
   	   				aux=first;
   	   				cost=instance.dist(aux.name,no.name)+instance.dist(no.name,aux.next.name)-instance.dist(aux.name,aux.next.name);
   	   				if(cost<lowestCost)
   	   				{
   	   					lowestCost=cost;
   	   					bestCusto=aux;
   	   				}   				
   	   			}
   	   			else if(no.getKnn()[j]!=excecao.name&&solution[no.getKnn()[j]-1].route==this)
   	   			{
   	   				aux=solution[no.getKnn()[j]-1];
   	   				cost=instance.dist(aux.name,no.name)+instance.dist(no.name,aux.next.name)-instance.dist(aux.name,aux.next.name);
   	   				if(cost<lowestCost)
   	   				{
   	   					lowestCost=cost;
   	   					bestCusto=aux;
   	   				}
   	   			}
   	   			
   			}
   		}
   		else
   		{
   			findBestPositionExceto(no,excecao);
   		}
   		
   		return bestCusto;
   	}
    
	public void clean()
	{
		first.prev=first;
		first.next=first;
		
		demandaTotal=0;
		fRoute=0;
		numElements=1;
		modified=true;
	}
	
	
	public void setDemandaAcumulada()
	{
		first.demandaAcumulada=0;
		aux=first.next;
		do
		{
			aux.demandaAcumulada=aux.prev.demandaAcumulada+aux.demanda;
			aux=aux.next;
		}
		while(aux!=first);
	}
	
	public void inverterRoute()
	{
		aux=first;
		Node prev=first.prev;
		Node next=first.next;
		do
		{
			aux.prev=next;
			aux.next=prev;
			aux=next;
			prev=aux.prev;
			next=aux.next;
		}
		while(aux!=first);
	}
	
	public double F()
	{
		double f=0;
		Node no=first;
		Node next=no.next;
		do
		{
			f+=instance.dist(no.name,next.name);
			no=next;
			next=no.next;
		}
		while(next!=first);
		
		f+=instance.dist(no.name,next.name);
		return f;
	}
	
	public void findErro()
	{
		int cont=0;
		Node aux=first;
		
		do
		{
			if(aux.prev==null||aux.next==null)
			{
				System.out.println("Erro no null No: "+aux+" em:\n"+this.toString());
				System.out.println(this);
			}
			if(aux.route!=this)
				System.out.println("Erro no : "+aux+" com route Errada:\n"+this.toString());

			cont++;
			aux=aux.next;
		}
		while(aux!=first);
		if(cont!=numElements)
			System.out.println("Erro no numElements \n"+this.toString());

//		if(cont==0)
//			System.out.println("Erro so tem um elemento\n"+this.toString());
	}
	
	//------------------------Visualizacao-------------------------

	@Override
	public String toString() 
	{
		String str="Route: "+nomeRoute;
		str+=" f: "+fRoute;
		str+=" espaco: "+availableCapacity();
		str+=" size: "+numElements+" = ";
		str+=" modified: "+modified+" = ";

		Node aux=first;
		do
		{
			str+=aux+"->";
//			System.out.println(str);
			aux=aux.next;
		}
		while(aux!=first);
		
		return str;
	}
	
	public String toString2() 
	{
		String str="Route #"+(nomeRoute+1)+": ";
		Node aux=first.next;
		do
		{
			str+=aux.name+" ";
			aux=aux.next;
		}
		while(aux!=first);
		
		return str;
	}
	
	public boolean isFeasible()
	{
		if((capacidade-demandaTotal)>=0)
			return true;
		return false;
	}
	
	public int availableCapacity()
	{
		return capacidade-demandaTotal;
	}
	
	public void setAcrescimoF(int acrescimoF) {
		this.acrescimoF = acrescimoF;
	}

	public int getNumElements() {
		return numElements;
	}

	public void setNumElements(int numElements) {
		this.numElements = numElements;
	}
	
	@Override
	public int compareTo(Route x) 
	{
		return nomeRoute-x.nomeRoute;
	}

	public double remove(Node no) 
	{
		double cost=no.custoRemocao();
		
		if(no==first)
			first=no.prev;
		
		modified=true;
		no.alterado=true;
		no.next.alterado=true;
		no.prev.alterado=true;
		
		no.jaInserido=false;
		
		fRoute+=cost;
		numElements--;
		
		prev=no.prev;
		next=no.next;
		
		prev.next=next;
		next.prev=prev;
		
		demandaTotal-=no.demanda;
		
		no.route=null;
		no.prev=null;
		no.next=null;
		
//		setDemandaAcumulada();
		
		return cost;
	}

	//------------------------Add No-------------------------
	
	public double addNoFinal(Node no)
	{
		no.route=this;
		if(first==null)
		{
			first=no;
			first.prev=no;
			first.next=no;
			numElements++;
			demandaTotal=0;
			fRoute=0;
			return 0;
		}
		else if(no.name==0)
		{
			aux=first.prev;
			first=no;
			return addDepois(no, aux); 
		}
		else 
		{
			aux=first.prev;
			return addDepois(no, aux); 
		}
	}
	
	public double addDepois(Node no1, Node no2) 
	{
		acrescimoF=no1.custoInserirApos(no2);
		modified=true;
		no1.jaInserido=true;
		no1.alterado=true;
		no2.next.alterado=true;
		no2.prev.alterado=true;
		
		numElements++;
		fRoute+=acrescimoF;
		
		demandaTotal+=no1.demanda;

		no1.route=this;
		
		next=no2.next;
		no2.next=no1;
		no1.prev=no2;
		
		next.prev=no1;
		no1.next=next;
		
		if(no1.name==0)
			first=no1;
		
//		setDemandaAcumulada();
		
		return acrescimoF;
	}
}

