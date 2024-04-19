package Solution;

import Data.Instance;
import Data.Ponto;

public class Node implements Cloneable
{
	public Node prev,antOld;
	public Node next,proxOld;
	
	public Rota rota;
	public int name;
	public int demanda;

	public int knn[];
	public int demandaAcumulada=0;
	public boolean jaInserido;
	public boolean fixoRota;
	public boolean fixoPos;
	Ponto ponto;
	Instance instancia;
	public boolean alterado;
	
	public int nomeRotaDestino;
	public int nomeRotaOrigem;
	public int priority;
	public Rota rotaDestino;
	
	public Node(Ponto ponto, Instance instancia) 
	{
		this.ponto=ponto;
		this.instancia=instancia;
		this.name = ponto.nome;
		this.demanda = ponto.demanda;

		this.next=null;
		this.prev=null;
		this.rota=null;
		this.knn=instancia.getKnn()[name];
	}
	
	public double dist(Node x)
	{
		return instancia.dist(x.name,this.name);
	}
	
	public void limpar()
	{
		this.jaInserido=false;
		this.rota=null;
	}
	
	 public Node clone() 
	 {
		 Node clone = new Node(ponto, instancia);
		 clone.prev=prev;
		 clone.next=next;
		 clone.rota=rota;
		 return clone; 
	 }
	
	 public double custoRemocao()
	 {
		 return instancia.dist(prev.name,next.name)-instancia.dist(name,prev.name)-instancia.dist(name,next.name);
	 }
	 
	 public double custoInserirApos(Node no)
	 {
		 if(no==null)
			 System.out.println("no null");
		 
		 return -instancia.dist(no.name,no.next.name)+instancia.dist(name,no.name)+instancia.dist(name,no.next.name);
	 }
	 
	@Override
	public String toString() 
	{
		return "|n: "+name+" d: "+demanda+"|";
	}

	public int getDemanda() {
		return demanda;
	}

	public int[] getKnn() {
		return knn;
	}
	
	
}