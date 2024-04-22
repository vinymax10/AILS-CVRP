package Solution;

import Data.Instance;
import SearchMethod.Config;

public class Route implements Comparable<Route>
{
	public int  capacidade;
	public int deposito;

	public Node inicio;
	public boolean podeMelhorar;
	public boolean alterada;
	public int demandaTotal=0;
	public double fRoute=0,prevF;
	double acrescimoF;
	public int numElements=0;
	public int nomeRoute;
	Node prev,next;
	int contCaminho;
	
	//busca local best
	public double menorCusto=0;
	Node aux;
	
	//------------------------------------------------
	public double custo,custoRemocao;
	public Node bestCusto;
	public double distancia,menorDist;
	//------------------------------------------------
	public boolean update;
	
    Instance instance;
    int limiteAdj;
    
	public Route(Instance instance, Config config, Node deposito,int nomeRoute) 
	{
		this.instance=instance;
		this.capacidade = instance.getCapacity();
		this.deposito=deposito.name;
		this.nomeRoute=nomeRoute;
		this.inicio=null;
		this.limiteAdj=config.getVarphi();
		addNoFinal(deposito.clone());
	}
	
    public Node findBestPosition(Node no)
	{
		bestCusto=inicio;
		aux=inicio.next;
		menorCusto=instance.dist(inicio.name,no.name)+instance.dist(no.name,inicio.next.name)-instance.dist(inicio.name,inicio.next.name);
		do
		{
			custo=instance.dist(aux.name,no.name)+instance.dist(no.name,aux.next.name)-instance.dist(aux.name,aux.next.name);
			if(custo<menorCusto)
			{
				menorCusto=custo;
				bestCusto=aux;
			}
			aux=aux.next;
		}
		while(aux!=inicio);
		return bestCusto;
	}
    
    public Node findBestPositionExceto(Node no,Node excecao)
  	{
  		bestCusto=inicio;
  		aux=inicio.next;
  		
  		menorCusto=instance.dist(inicio.name,no.name)+instance.dist(no.name,inicio.next.name)-instance.dist(inicio.name,inicio.next.name);
  		do
  		{
  			custo=instance.dist(aux.name,no.name)+instance.dist(no.name,aux.next.name)-instance.dist(aux.name,aux.next.name);
  			if(custo<menorCusto&&aux.name!=excecao.name)
  			{
  				menorCusto=custo;
  				bestCusto=aux;
  			}
  			aux=aux.next;
  		}
  		while(aux!=inicio);
  		return bestCusto;
  	}
    
    public Node findBestPositionExcetoKNN(Node no,Node excecao, Node solution[])
   	{
    	menorCusto=Double.MAX_VALUE;
    	
   		bestCusto=inicio;
   		
   		if(numElements>limiteAdj)
   		{
   			for (int j = 0; j < limiteAdj; j++) 
   			{
   	   			if(no.getKnn()[j]==0)
   	   			{
   	   				aux=inicio;
   	   				custo=instance.dist(aux.name,no.name)+instance.dist(no.name,aux.next.name)-instance.dist(aux.name,aux.next.name);
   	   				if(custo<menorCusto)
   	   				{
   	   					menorCusto=custo;
   	   					bestCusto=aux;
   	   				}   				
   	   			}
   	   			else if(no.getKnn()[j]!=excecao.name&&solution[no.getKnn()[j]-1].route==this)
   	   			{
   	   				aux=solution[no.getKnn()[j]-1];
   	   				custo=instance.dist(aux.name,no.name)+instance.dist(no.name,aux.next.name)-instance.dist(aux.name,aux.next.name);
   	   				if(custo<menorCusto)
   	   				{
   	   					menorCusto=custo;
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
    
	public void limpar()
	{
		inicio.prev=inicio;
		inicio.next=inicio;
		
		demandaTotal=0;
		fRoute=0;
		numElements=1;
		alterada=true;
	}
	
	
	public void setDemandaAcumulada()
	{
		inicio.demandaAcumulada=0;
		aux=inicio.next;
		do
		{
			aux.demandaAcumulada=aux.prev.demandaAcumulada+aux.demanda;
			aux=aux.next;
		}
		while(aux!=inicio);
	}
	
	public void inverterRoute()
	{
		aux=inicio;
		Node prev=inicio.prev;
		Node next=inicio.next;
		do
		{
			aux.prev=next;
			aux.next=prev;
			aux=next;
			prev=aux.prev;
			next=aux.next;
		}
		while(aux!=inicio);
	}
	
	public double F()
	{
		double f=0;
		Node no=inicio;
		Node next=no.next;
		do
		{
			f+=instance.dist(no.name,next.name);
			no=next;
			next=no.next;
		}
		while(next!=inicio);
		
		f+=instance.dist(no.name,next.name);
		return f;
	}
	
	public void findErro()
	{
		int cont=0;
		Node aux=inicio;
		
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
		while(aux!=inicio);
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
		str+=" alterada: "+alterada+" = ";

		Node aux=inicio;
		do
		{
			str+=aux+"->";
//			System.out.println(str);
			aux=aux.next;
		}
		while(aux!=inicio);
		
		return str;
	}
	
	public String toString2() 
	{
		String str="Route #"+(nomeRoute+1)+": ";
		Node aux=inicio.next;
		do
		{
			str+=aux.name+" ";
			aux=aux.next;
		}
		while(aux!=inicio);
		
		return str;
	}
	
	public boolean isFactivel()
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
		double custo=no.custoRemocao();
		
		if(no==inicio)
			inicio=no.prev;
		
		alterada=true;
		no.alterado=true;
		no.next.alterado=true;
		no.prev.alterado=true;
		
		no.jaInserido=false;
		
		fRoute+=custo;
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
		
		return custo;
	}

	//------------------------Add No-------------------------
	
	public double addNoFinal(Node no)
	{
		no.route=this;
		if(inicio==null)
		{
			inicio=no;
			inicio.prev=no;
			inicio.next=no;
			numElements++;
			demandaTotal=0;
			fRoute=0;
			return 0;
		}
		else if(no.name==0)
		{
			aux=inicio.prev;
			inicio=no;
			return addDepois(no, aux); 
		}
		else 
		{
			aux=inicio.prev;
			return addDepois(no, aux); 
		}
	}
	
	public double addDepois(Node no1, Node no2) 
	{
		acrescimoF=no1.custoInserirApos(no2);
		alterada=true;
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
			inicio=no1;
		
//		setDemandaAcumulada();
		
		return acrescimoF;
	}
}

