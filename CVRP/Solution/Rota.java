package Solution;

import Data.Instance;
import SearchMethod.Config;

public class Rota implements Comparable<Rota>
{
	public int  capacidade;
	public int deposito;

	public Node inicio;
	public boolean podeMelhorar;
	public boolean alterada;
	public int demandaTotal=0;
	public double fRota=0,antF;
	double acrescimoF;
	public int numElements=0;
	public int nomeRota;
	Node ant,prox;
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
	
    Instance instancia;
    int limiteAdj;
    
	public Rota(Instance instancia, Config config, Node deposito,int nomeRota) 
	{
		this.instancia=instancia;
		this.capacidade = instancia.getCapacidade();
		this.deposito=deposito.name;
		this.nomeRota=nomeRota;
		this.inicio=null;
		this.limiteAdj=config.getVarphi();
		addNoFinal(deposito.clone());
	}
	
    public Node findBestPosition(Node no)
	{
		bestCusto=inicio;
		aux=inicio.next;
		menorCusto=instancia.dist(inicio.name,no.name)+instancia.dist(no.name,inicio.next.name)-instancia.dist(inicio.name,inicio.next.name);
		do
		{
			custo=instancia.dist(aux.name,no.name)+instancia.dist(no.name,aux.next.name)-instancia.dist(aux.name,aux.next.name);
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
  		
  		menorCusto=instancia.dist(inicio.name,no.name)+instancia.dist(no.name,inicio.next.name)-instancia.dist(inicio.name,inicio.next.name);
  		do
  		{
  			custo=instancia.dist(aux.name,no.name)+instancia.dist(no.name,aux.next.name)-instancia.dist(aux.name,aux.next.name);
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
    
    public Node findBestPositionExcetoKNN(Node no,Node excecao, Node solucao[])
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
   	   				custo=instancia.dist(aux.name,no.name)+instancia.dist(no.name,aux.next.name)-instancia.dist(aux.name,aux.next.name);
   	   				if(custo<menorCusto)
   	   				{
   	   					menorCusto=custo;
   	   					bestCusto=aux;
   	   				}   				
   	   			}
   	   			else if(no.getKnn()[j]!=excecao.name&&solucao[no.getKnn()[j]-1].rota==this)
   	   			{
   	   				aux=solucao[no.getKnn()[j]-1];
   	   				custo=instancia.dist(aux.name,no.name)+instancia.dist(no.name,aux.next.name)-instancia.dist(aux.name,aux.next.name);
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
		fRota=0;
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
	
	public void inverterRota()
	{
		aux=inicio;
		Node ant=inicio.prev;
		Node prox=inicio.next;
		do
		{
			aux.prev=prox;
			aux.next=ant;
			aux=prox;
			ant=aux.prev;
			prox=aux.next;
		}
		while(aux!=inicio);
	}
	
	public double F()
	{
		double f=0;
		Node no=inicio;
		Node prox=no.next;
		do
		{
			f+=instancia.dist(no.name,prox.name);
			no=prox;
			prox=no.next;
		}
		while(prox!=inicio);
		
		f+=instancia.dist(no.name,prox.name);
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
			if(aux.rota!=this)
				System.out.println("Erro no : "+aux+" com rota Errada:\n"+this.toString());

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
		String str="Rota: "+nomeRota;
		str+=" f: "+fRota;
		str+=" espaco: "+espacoLivre();
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
		String str="Route #"+(nomeRota+1)+": ";
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
	
	public int espacoLivre()
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
	public int compareTo(Rota x) 
	{
		return nomeRota-x.nomeRota;
	}

	public double remove(Node no) 
	{
		double custo=no.custoRemocao();
//		System.out.println("custo: "+custo+" fRota: "+fRota);
		
		if(no==inicio)
			inicio=no.prev;
		
		alterada=true;
		no.alterado=true;
		no.next.alterado=true;
		no.prev.alterado=true;
		
		no.jaInserido=false;
		
		fRota+=custo;
		numElements--;
		
		ant=no.prev;
		prox=no.next;
		
		ant.next=prox;
		prox.prev=ant;
		
		demandaTotal-=no.demanda;
		
		no.rota=null;
		no.prev=null;
		no.next=null;
		
//		setDemandaAcumulada();
		
		return custo;
	}

	//------------------------Add No-------------------------
	
	public double addNoFinal(Node no)
	{
		no.rota=this;
		if(inicio==null)
		{
			inicio=no;
			inicio.prev=no;
			inicio.next=no;
			numElements++;
			demandaTotal=0;
			fRota=0;
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
		fRota+=acrescimoF;
		
		demandaTotal+=no1.demanda;

		no1.rota=this;
		
		prox=no2.next;
		no2.next=no1;
		no1.prev=no2;
		
		prox.prev=no1;
		no1.next=prox;
		
		if(no1.name==0)
			inicio=no1;
		
//		setDemandaAcumulada();
		
		return acrescimoF;
	}
}

