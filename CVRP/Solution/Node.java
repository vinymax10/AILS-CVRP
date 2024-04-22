package Solution;

import Data.Instance;
import Data.Point;

public class Node implements Cloneable
{
	public Node prev,prevOld;
	public Node next,nextOld;
	
	public Route route;
	public int name;
	public int demanda;

	public int knn[];
	public int demandaAcumulada=0;
	public boolean jaInserido;
	public boolean fixoRoute;
	public boolean fixoPos;
	Point ponto;
	Instance instance;
	public boolean alterado;
	
	public int nomeRouteDestino;
	public int nomeRouteOrigem;
	public int priority;
	public Route routeDestino;
	
	public Node(Point ponto, Instance instance) 
	{
		this.ponto=ponto;
		this.instance=instance;
		this.name = ponto.name;
		this.demanda = ponto.demand;

		this.next=null;
		this.prev=null;
		this.route=null;
		this.knn=instance.getKnn()[name];
	}
	
	public double dist(Node x)
	{
		return instance.dist(x.name,this.name);
	}
	
	public void limpar()
	{
		this.jaInserido=false;
		this.route=null;
	}
	
	 public Node clone() 
	 {
		 Node clone = new Node(ponto, instance);
		 clone.prev=prev;
		 clone.next=next;
		 clone.route=route;
		 return clone; 
	 }
	
	 public double custoRemocao()
	 {
		 return instance.dist(prev.name,next.name)-instance.dist(name,prev.name)-instance.dist(name,next.name);
	 }
	 
	 public double custoInserirApos(Node no)
	 {
		 if(no==null)
			 System.out.println("no null");
		 
		 return -instance.dist(no.name,no.next.name)+instance.dist(name,no.name)+instance.dist(name,no.next.name);
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