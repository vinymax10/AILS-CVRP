package Solution;

import Data.Instance;
import Data.Point;

public class Node implements Cloneable
{
	public Node prev,prevOld;
	public Node next,nextOld;
	
	public Route route;
	public int name;
	public int demand;

	public int knn[];
	public int accumulatedDemand=0;
	public boolean nodeBelong;
	public boolean fixoRoute;
	public boolean fixoPos;
	Point ponto;
	Instance instance;
	public boolean modified;
	
	public int nomeRouteDestino;
	public int nomeRouteOrigem;
	public int priority;
	public Route routeDestino;
	
	public Node(Point ponto, Instance instance) 
	{
		this.ponto=ponto;
		this.instance=instance;
		this.name = ponto.name;
		this.demand = ponto.demand;

		this.next=null;
		this.prev=null;
		this.route=null;
		this.knn=instance.getKnn()[name];
	}
	
	public double dist(Node x)
	{
		return instance.dist(x.name,this.name);
	}
	
	public void clean()
	{
		this.nodeBelong=false;
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
	
	 public double costRemocao()
	 {
		 return instance.dist(prev.name,next.name)-instance.dist(name,prev.name)-instance.dist(name,next.name);
	 }
	 
	 public double costInserirApos(Node node)
	 {
		 if(node==null)
			 System.out.println("no null");
		 
		 return -instance.dist(node.name,node.next.name)+instance.dist(name,node.name)+instance.dist(name,node.next.name);
	 }
	 
	@Override
	public String toString() 
	{
		return "|n: "+name+" d: "+demand+"|";
	}

	public int getDemand() {
		return demand;
	}

	public int[] getKnn() {
		return knn;
	}
	
	
}