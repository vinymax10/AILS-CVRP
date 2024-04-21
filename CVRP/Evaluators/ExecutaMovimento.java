package Evaluators;

import Data.Instance;
import Improvement.NoPosMel;
import Solution.Node;
import Solution.Rota;

public class ExecutaMovimento 
{
	Rota aRota, bRota;
	Node aAnt, bAnt, aProx, aProxProx, bProx, bProxProx;
	Instance instance;
	
	public ExecutaMovimento(Instance instance)
	{
		this.instance=instance;
	}
	
	public void SWAP2IdaAdj2Ida(Node a, Node b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aAnt=a.prev;
		 bAnt=b.prev;
		 aProx=a.next;
		 bProx=b.next;
		 
		 if(a.next.next==b||a.prev==b.next)
		 {
			 if(a.next.next==b)
			 {
				 aRota.remove(a);
				 aRota.remove(aProx);
				 
				 bRota.addDepois(a, bProx);
				 bRota.addDepois(aProx, a);
			 }
			 else
			 {
				 aRota.remove(a);
				 aRota.remove(aProx);
				 
				 bRota.addDepois(a, b.prev);
				 bRota.addDepois(aProx, a);
			 }
		 }
		 else
		 {
			 aRota.remove(a);
			 aRota.remove(aProx);
			 bRota.remove(b);
			 bRota.remove(bProx);
			 
			 bRota.addDepois(a, bAnt);
			 bRota.addDepois(aProx, a);
			 
			 aRota.addDepois(b,aAnt);
			 aRota.addDepois(bProx,b);
		 }
	 }
	
	 public void SWAP2IdaAdj2Volta(Node a, Node b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aAnt=a.prev;
		 bAnt=b.prev;
		 aProx=a.next;
		 bProx=b.next;
		 
		 if(a.next.next==b||a.prev==b.next)
		 {
			 if(a.next.next==b)
			 {
				 aRota.remove(b);
				 aRota.remove(bProx);
				 
				 bRota.addDepois(bProx, a.prev);
				 bRota.addDepois(b, bProx);
			 }
			 else
			 {
				 aRota.remove(b);
				 aRota.remove(bProx);
				 
				 bRota.addDepois(bProx, aProx);
				 bRota.addDepois(b, bProx);
			 }
		 }
		 else
		 {
			 aRota.remove(a);
			 aRota.remove(aProx);
			 bRota.remove(b);
			 bRota.remove(bProx);
			 
			 bRota.addDepois(a, bAnt);
			 bRota.addDepois(aProx, a);
			 
			 aRota.addDepois(bProx,aAnt);
			 aRota.addDepois(b,bProx);
		 }
	 }
	 
	 public void SWAP2VoltaAdj2Ida(Node a, Node b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aAnt=a.prev;
		 bAnt=b.prev;
		 aProx=a.next;
		 bProx=b.next;
		 if(a.next.next==b||a.prev==b.next)
		 {
			 if(a.next.next==b)
			 {
				 aRota.remove(a);
				 aRota.remove(aProx);
				 
				 bRota.addDepois(aProx, bProx);
				 bRota.addDepois(a, aProx);
			 }
			 else
			 {
				 aRota.remove(a);
				 aRota.remove(aProx);
				 
				 bRota.addDepois(aProx, bAnt);
				 bRota.addDepois(a, aProx);
			 }
		 }
		 else
		 {
			 aRota.remove(a);
			 aRota.remove(aProx);
			 bRota.remove(b);
			 bRota.remove(bProx);
			 
			 bRota.addDepois(aProx, bAnt);
			 bRota.addDepois(a, aProx);
			 
			 aRota.addDepois(b,aAnt);
			 aRota.addDepois(bProx,b);
		 }
	 }
	 
	 public void SWAP2VoltaAdj2Volta(Node a, Node b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aAnt=a.prev;
		 bAnt=b.prev;
		 aProx=a.next;
		 bProx=b.next;
		 if(a.next.next==b||a.prev==b.next)
		 {
			 if(a.next.next==b)
			 {
				 aRota.remove(a);
				 aRota.remove(aProx);
				 aRota.remove(b);
				 aRota.remove(bProx);
				 
				 bRota.addDepois(bProx, aAnt);
				 bRota.addDepois(b, bProx);
				 bRota.addDepois(aProx, b);
				 bRota.addDepois(a, aProx);
			 }
			 else
			 {
				 aRota.remove(a);
				 aRota.remove(aProx);
				 aRota.remove(b);
				 aRota.remove(bProx);
				 
				 bRota.addDepois(aProx, bAnt);
				 bRota.addDepois(a, aProx);
				 bRota.addDepois(bProx, a);
				 bRota.addDepois(b, bProx);
			 }
		 }
		 else
		 {
			 aRota.remove(a);
			 aRota.remove(aProx);
			 bRota.remove(b);
			 bRota.remove(bProx);
			 
			 bRota.addDepois(aProx, bAnt);
			 bRota.addDepois(a, aProx);

			 aRota.addDepois(bProx,aAnt);
			 aRota.addDepois(b,bProx);
		 }
	 }
	 
	 public void SWAP3IdaAdj2Ida(Node a, Node b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aAnt=a.prev;
		 bAnt=b.prev;
		 aProx=a.next;
		 aProxProx=a.next.next;
		 bProx=b.next;
		 
		 if(a.next.next.next==b||a.prev==b.next)
		 {
			 if(a.next.next.next==b)
			 {
				 aRota.remove(a);
				 aRota.remove(aProx);
				 aRota.remove(aProxProx);
				 
				 bRota.addDepois(a, bProx);
				 bRota.addDepois(aProx, a);
				 bRota.addDepois(aProxProx, aProx);
			 }
			 else
			 {
				 aRota.remove(a);
				 aRota.remove(aProx);
				 aRota.remove(aProxProx);

				 bRota.addDepois(a, b.prev);
				 bRota.addDepois(aProx, a);
				 bRota.addDepois(aProxProx, aProx);
			 }
		 }
		 else
		 {
			 aRota.remove(a);
			 aRota.remove(aProx);
			 aRota.remove(aProxProx);
			 bRota.remove(b);
			 bRota.remove(bProx);
			 
			 bRota.addDepois(a, bAnt);
			 bRota.addDepois(aProx, a);
			 bRota.addDepois(aProxProx, aProx);

			 aRota.addDepois(b,aAnt);
			 aRota.addDepois(bProx,b);
		 }
	 }
	 
	 public void SWAP3IdaAdj2Volta(Node a, Node b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aAnt=a.prev;
		 bAnt=b.prev;
		 aProx=a.next;
		 aProxProx=a.next.next;
		 bProx=b.next;
		 
		 if(a.next.next.next==b||a.prev==b.next)
		 {
			 if(a.next.next.next==b)
			 {
				 aRota.remove(b);
				 aRota.remove(bProx);
				 
				 bRota.addDepois(bProx, a.prev);
				 bRota.addDepois(b, bProx);
			 }
			 else
			 {
				 aRota.remove(b);
				 aRota.remove(bProx);
				 
				 bRota.addDepois(bProx, aProx);
				 bRota.addDepois(b, bProx);
			 }
		 }
		 else
		 {
			 aRota.remove(a);
			 aRota.remove(aProx);
			 aRota.remove(aProxProx);
			 
			 bRota.remove(b);
			 bRota.remove(bProx);
			 
			 bRota.addDepois(a, bAnt);
			 bRota.addDepois(aProx, a);
			 bRota.addDepois(aProxProx, aProx);
			 
			 aRota.addDepois(bProx,aAnt);
			 aRota.addDepois(b,bProx);
		 }
	 }
	 
	 public void SWAP3VoltaAdj2Ida(Node a, Node b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aAnt=a.prev;
		 bAnt=b.prev;
		 aProx=a.next;
		 aProxProx=a.next.next;
		 
		 bProx=b.next;
		 if(a.next.next.next==b||a.prev==b.next)
		 {
			 if(a.next.next.next==b)
			 {
				 aRota.remove(a);
				 aRota.remove(aProx);
				 aRota.remove(aProxProx);
				 
				 bRota.addDepois(aProxProx, bProx);
				 bRota.addDepois(aProx, aProxProx);
				 bRota.addDepois(a, aProx);
			 }
			 else
			 {
				 aRota.remove(a);
				 aRota.remove(aProx);
				 aRota.remove(aProxProx);

				 bRota.addDepois(aProxProx, bAnt);
				 bRota.addDepois(aProx, aProxProx);
				 bRota.addDepois(a, aProx);
			 }
		 }
		 else
		 {
			 aRota.remove(a);
			 aRota.remove(aProx);
			 aRota.remove(aProxProx);
			 bRota.remove(b);
			 bRota.remove(bProx);
			 
			 bRota.addDepois(aProxProx, bAnt);
			 bRota.addDepois(aProx, aProxProx);
			 bRota.addDepois(a, aProx);
			 
			 aRota.addDepois(b,aAnt);
			 aRota.addDepois(bProx,b);
		 }
	 }
	 
	 public void SWAP3VoltaAdj2Volta(Node a, Node b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aAnt=a.prev;
		 bAnt=b.prev;
		 aProx=a.next;
		 aProxProx=a.next.next;
		 bProx=b.next;
		 if(a.next.next.next==b||a.prev==b.next)
		 {
			 if(a.next.next.next==b)
			 {
				 aRota.remove(a);
				 aRota.remove(aProx);
				 aRota.remove(aProxProx);
				 aRota.remove(b);
				 aRota.remove(bProx);
				 
				 bRota.addDepois(bProx, aAnt);
				 bRota.addDepois(b, bProx);
				 
				 bRota.addDepois(aProxProx, b);
				 bRota.addDepois(aProx, aProxProx);
				 bRota.addDepois(a, aProx);
			 }
			 else
			 {
				 aRota.remove(a);
				 aRota.remove(aProx);
				 aRota.remove(aProxProx);

				 aRota.remove(b);
				 aRota.remove(bProx);
				 
				 bRota.addDepois(aProxProx, bAnt);
				 bRota.addDepois(aProx, aProxProx);
				 bRota.addDepois(a, aProx);
				 bRota.addDepois(bProx, a);
				 bRota.addDepois(b, bProx);
			 }
		 }
		 else
		 {
			 aRota.remove(a);
			 aRota.remove(aProx);
			 aRota.remove(aProxProx);

			 bRota.remove(b);
			 bRota.remove(bProx);
			 
			 bRota.addDepois(aProxProx, bAnt);
			 bRota.addDepois(aProx, aProxProx);
			 bRota.addDepois(a, aProx);

			 aRota.addDepois(bProx,aAnt);
			 aRota.addDepois(b,bProx);
		 }
	 }
	 
	 public void SWAP3IdaAdj3Ida(Node a, Node b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aAnt=a.prev;
		 bAnt=b.prev;
		 aProx=a.next;
		 aProxProx=a.next.next;
		 bProx=b.next;
		 bProxProx=b.next.next;
		 
		 if(a.next.next.next==b||a==b.next.next.next)
		 {
			 if(a.next.next.next==b)
			 {
				 aRota.remove(a);
				 aRota.remove(aProx);
				 aRota.remove(aProxProx);
				 
				 bRota.addDepois(a, bProxProx);
				 bRota.addDepois(aProx, a);
				 bRota.addDepois(aProxProx, aProx);
			 }
			 else
			 {
				 aRota.remove(a);
				 aRota.remove(aProx);
				 aRota.remove(aProxProx);

				 bRota.addDepois(a, b.prev);
				 bRota.addDepois(aProx, a);
				 bRota.addDepois(aProxProx, aProx);
			 }
		 }
		 else
		 {
			 aRota.remove(a);
			 aRota.remove(aProx);
			 aRota.remove(aProxProx);
			 bRota.remove(b);
			 bRota.remove(bProx);
			 bRota.remove(bProxProx);

			 bRota.addDepois(a, bAnt);
			 bRota.addDepois(aProx, a);
			 bRota.addDepois(aProxProx, aProx);

			 aRota.addDepois(b,aAnt);
			 aRota.addDepois(bProx,b);
			 aRota.addDepois(bProxProx,bProx);
		 }
	 }
	 
	 public void SWAP3IdaAdj3Volta(Node a, Node b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aAnt=a.prev;
		 bAnt=b.prev;
		 aProx=a.next;
		 aProxProx=a.next.next;
		 bProx=b.next;
		 bProxProx=b.next.next;

		 if(a.next.next.next==b||a==b.next.next.next)
		 {
			 if(a.next.next.next==b)
			 {
				 aRota.remove(b);
				 aRota.remove(bProx);
				 aRota.remove(bProxProx);

				 bRota.addDepois(bProxProx, a.prev);
				 bRota.addDepois(bProx, bProxProx);
				 bRota.addDepois(b, bProx);
			 }
			 else
			 {
				 aRota.remove(b);
				 aRota.remove(bProx);
				 aRota.remove(bProxProx);

				 bRota.addDepois(bProxProx, aProx);
				 bRota.addDepois(bProx, bProxProx);
				 bRota.addDepois(b, bProx);
			 }
		 }
		 else
		 {
			 aRota.remove(a);
			 aRota.remove(aProx);
			 aRota.remove(aProxProx);
			 
			 bRota.remove(b);
			 bRota.remove(bProx);
			 bRota.remove(bProxProx);

			 bRota.addDepois(a, bAnt);
			 bRota.addDepois(aProx, a);
			 bRota.addDepois(aProxProx, aProx);
			 
			 aRota.addDepois(bProxProx,aAnt);
			 aRota.addDepois(bProx,bProxProx);
			 aRota.addDepois(b,bProx);
		 }
	 }
	 
	 public void SWAP3VoltaAdj3Ida(Node a, Node b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aAnt=a.prev;
		 bAnt=b.prev;
		 aProx=a.next;
		 aProxProx=a.next.next;
		 
		 bProx=b.next;
		 bProxProx=b.next.next;

		 if(a.next.next.next==b||a==b.next.next.next)
		 {
			 if(a.next.next.next==b)
			 {
				 aRota.remove(a);
				 aRota.remove(aProx);
				 aRota.remove(aProxProx);
				 
				 bRota.addDepois(aProxProx, bProxProx);
				 bRota.addDepois(aProx, aProxProx);
				 bRota.addDepois(a, aProx);
			 }
			 else
			 {
				 aRota.remove(a);
				 aRota.remove(aProx);
				 aRota.remove(aProxProx);

				 bRota.addDepois(aProxProx, bAnt);
				 bRota.addDepois(aProx, aProxProx);
				 bRota.addDepois(a, aProx);
			 }
		 }
		 else
		 {
			 aRota.remove(a);
			 aRota.remove(aProx);
			 aRota.remove(aProxProx);
			 bRota.remove(b);
			 bRota.remove(bProx);
			 bRota.remove(bProxProx);

			 bRota.addDepois(aProxProx, bAnt);
			 bRota.addDepois(aProx, aProxProx);
			 bRota.addDepois(a, aProx);
			 
			 aRota.addDepois(b,aAnt);
			 aRota.addDepois(bProx,b);
			 aRota.addDepois(bProxProx,bProx);
		 }
	 }
	 
	 public void SWAP3VoltaAdj3Volta(Node a, Node b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aAnt=a.prev;
		 bAnt=b.prev;
		 aProx=a.next;
		 aProxProx=a.next.next;
		 bProx=b.next;
		 bProxProx=b.next.next;

		 if(a.next.next.next==b||a==b.next.next.next)
		 {
			 if(a.next.next.next==b)
			 {
				 aRota.remove(a);
				 aRota.remove(aProx);
				 aRota.remove(aProxProx);
				 aRota.remove(b);
				 aRota.remove(bProx);
				 aRota.remove(bProxProx);

				 bRota.addDepois(bProxProx, aAnt);
				 bRota.addDepois(bProx, bProxProx);
				 bRota.addDepois(b, bProx);
				 
				 bRota.addDepois(aProxProx, b);
				 bRota.addDepois(aProx, aProxProx);
				 bRota.addDepois(a, aProx);
			 }
			 else
			 {
				 aRota.remove(a);
				 aRota.remove(aProx);
				 aRota.remove(aProxProx);

				 aRota.remove(b);
				 aRota.remove(bProx);
				 aRota.remove(bProxProx);

				 bRota.addDepois(aProxProx, bAnt);
				 bRota.addDepois(aProx, aProxProx);
				 bRota.addDepois(a, aProx);

				 bRota.addDepois(bProxProx, a);
				 bRota.addDepois(bProx, bProxProx);
				 bRota.addDepois(b, bProx);
			 }
		 }
		 else
		 {
			 aRota.remove(a);
			 aRota.remove(aProx);
			 aRota.remove(aProxProx);

			 bRota.remove(b);
			 bRota.remove(bProx);
			 bRota.remove(bProxProx);

			 bRota.addDepois(aProxProx, bAnt);
			 bRota.addDepois(aProx, aProxProx);
			 bRota.addDepois(a, aProx);

			 aRota.addDepois(bProxProx,aAnt);
			 aRota.addDepois(bProx,bProxProx);
			 aRota.addDepois(b,bProx);
		 }
	 }
	 
	 public void SWAP(Node a, Node b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 if(a.next==b||a.prev==b)
		 {
			 if(a.next==b)
			 {
				 aRota.remove(a);
				 aRota.addDepois(a, b);
			 }
			 else
			 {
				 aRota.remove(b);
				 aRota.addDepois(b, a);
			 }
		 }
		 else
		 {
			 Node myAnt=a.prev;
			 Node noAnt=b.prev;
			 
			 aRota.remove(a);
			 bRota.remove(b);
			 
			 bRota.addDepois(a, noAnt);
			 aRota.addDepois(b,myAnt);
		 }
	 }
	 
	 public void SWAP2Adj1(Node a, Node b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aAnt=a.prev;
		 bAnt=b.prev;
		 aProx=a.next;
		 
		 if(a.next.next==b||a.prev==b)
		 {
			 if(a.next.next==b)
			 {
				 aRota.remove(a);
				 aRota.remove(aProx);
				 
				 bRota.addDepois(a, b);
				 bRota.addDepois(aProx, a);
			 }
			 else
			 {
				 aRota.remove(a);
				 aRota.remove(aProx);
				 
				 bRota.addDepois(a, b.prev);
				 bRota.addDepois(aProx, a);
			 }
		 }
		 else
		 {
			 aRota.remove(a);
			 aRota.remove(aProx);
			 bRota.remove(b);
			 
			 bRota.addDepois(a, bAnt);
			 bRota.addDepois(aProx, a);
			 
			 aRota.addDepois(b,aAnt);
		 }
	 }
	 
	 public void SWAP2Adj1Ivertido(Node a, Node b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aAnt=a.prev;
		 bAnt=b.prev;
		 aProx=a.next;
		 
		 if(a.next.next==b||a.prev==b)
		 {
			 if(a.next.next==b)
			 {
				 aRota.remove(a);
				 aRota.remove(aProx);
				 
				 bRota.addDepois(aProx, b);
				 bRota.addDepois(a, aProx);
			 }
			 else
			 {
				 aRota.remove(a);
				 aRota.remove(aProx);
				 
				 bRota.addDepois(aProx, b.prev);
				 bRota.addDepois(a, aProx);
			 }
		 }
		 else
		 {
			 aRota.remove(a);
			 aRota.remove(aProx);
			 bRota.remove(b);
			 
			 bRota.addDepois(aProx, bAnt);
			 bRota.addDepois(a,aProx);
			 
			 aRota.addDepois(b,aAnt);
		 }
	 }
	 
	 public void SWAP3Adj1(Node a, Node b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aAnt=a.prev;
		 bAnt=b.prev;
		 aProx=a.next;
		 aProxProx=a.next.next;
		 
		 if(a.next.next.next==b||a.prev==b)
		 {
			 if(a.next.next.next==b)
			 {
				 aRota.remove(a);
				 aRota.remove(aProx);
				 aRota.remove(aProxProx);
				 
				 bRota.addDepois(a, b);
				 bRota.addDepois(aProx, a);
				 bRota.addDepois(aProxProx, aProx);
			 }
			 else
			 {
				 aRota.remove(a);
				 aRota.remove(aProx);
				 aRota.remove(aProxProx);

				 bRota.addDepois(a, b.prev);
				 bRota.addDepois(aProx, a);
				 bRota.addDepois(aProxProx, aProx);
			 }
		 }
		 else
		 {
			 aRota.remove(a);
			 aRota.remove(aProx);
			 aRota.remove(aProxProx);

			 bRota.remove(b);
			 
			 bRota.addDepois(a, bAnt);
			 bRota.addDepois(aProx, a);
			 bRota.addDepois(aProxProx, aProx);
			 
			 aRota.addDepois(b,aAnt);
		 }
	 }
	 
	 public void SWAP3Adj1Ivertido(Node a, Node b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aAnt=a.prev;
		 bAnt=b.prev;
		 aProx=a.next;
		 aProxProx=a.next.next;
		 
		 if(a.next.next.next==b||a.prev==b)
		 {
			 if(a.next.next.next==b)
			 {
				 aRota.remove(a);
				 aRota.remove(aProx);
				 aRota.remove(aProxProx);
				 
				 bRota.addDepois(aProxProx, b);
				 bRota.addDepois(aProx, aProxProx);
				 bRota.addDepois(a, aProx);
			 }
			 else
			 {
				 aRota.remove(a);
				 aRota.remove(aProx);
				 aRota.remove(aProxProx);
				 
				 bRota.addDepois(aProxProx, b.prev);
				 bRota.addDepois(aProx, aProxProx);
				 bRota.addDepois(a, aProx);
				 
			 }
		 }
		 else
		 {
			 aRota.remove(a);
			 aRota.remove(aProx);
			 aRota.remove(aProxProx);
			 bRota.remove(b);
			 
			 bRota.addDepois(aProxProx, bAnt);
			 bRota.addDepois(aProx, aProxProx);
			 bRota.addDepois(a,aProx);
			 
			 aRota.addDepois(b,aAnt);
		 }
	 }
	 
	 public void SHIFT(Node a, Node b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aRota.remove(a);
		 bRota.addDepois(a, b);
	 }
	 
	 public void SHIFT2Adj(Node a, Node b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aProx=a.next;
		 
		 aRota.remove(a);
		 aRota.remove(aProx);
		
		 bRota.addDepois(a, b);
		 bRota.addDepois(aProx, a);
	 }
	 
	 public void SHIFT3Adj(Node a, Node b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aProx=a.next;
		 aProxProx=a.next.next;
		 
		 aRota.remove(a);
		 aRota.remove(aProx);
		 aRota.remove(aProxProx);

		 bRota.addDepois(a, b);
		 bRota.addDepois(aProx, a);
		 bRota.addDepois(aProxProx, aProx);
	 }
	 
	 public void SHIFT2AdjInvertido(Node a, Node b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aProx=a.next;
		 
		 aRota.remove(a);
		 aRota.remove(aProx);
		
		 bRota.addDepois(aProx, b);
		 bRota.addDepois(a,aProx);
	 }
	 
	 public void SHIFT3AdjInvertido(Node a, Node b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aProx=a.next;
		 aProxProx=a.next.next;

		 aRota.remove(a);
		 aRota.remove(aProx);
		 aRota.remove(aProxProx);
		 
		 bRota.addDepois(aProxProx, b);
		 bRota.addDepois(aProx, aProxProx);
		 bRota.addDepois(a,aProx);
	 }
	 
	 public void executa2Opt(Node a, Node b)
	 {
		 double custo=custo2Opt(a,b);
		 
		 aProx=a.next;
		 bProx=b.next;
		 Node aux=a;
		 aux.next=b;
		 Node ant=aux.prev;
		 while(aux.next!=aProx)
		 {
			 ant=aux;
			 aux=aux.next;
			 aux.next=aux.prev;
			 aux.prev=ant;
		 }
		 
		 ant=aux;
		 aux=aux.next;
		 aux.next=bProx;
		 aux.prev=ant;

		 ant=aux;
		 aux=aux.next;
		 aux.prev=ant;
		 
		 a.rota.fRota+=custo;
		 a.rota.setDemandaAcumulada();
		 a.rota.alterada=true;
	 }
	 
	public double custo2Opt(Node a, Node b)
	{
		return 	-(instance.dist(a.name,a.next.name)+instance.dist(b.name,b.next.name))+				
				(instance.dist(a.name,b.name)+instance.dist(a.next.name,b.next.name));
	}
	 
	 public void Cross(Node a, Node b)
	 {
		aRota=a.rota;
		bRota=b.rota;
		
		Node aux=a.next;
		Node ant=b;
		
		while(aux!=aRota.inicio)
		{
			aRota.remove(aux);
			bRota.addDepois(aux, ant);
			ant=aux;
			aux=a.next;
		}
		
		Node ultimo=ant;
		aux=ultimo.next;
		ant=a;
		while(aux!=bRota.inicio)
		{
			bRota.remove(aux);
			aRota.addDepois(aux, ant);
			ant=aux;
			aux=ultimo.next;
		}
	 }
	 
	 
	 public void CrossInvertido(Node a, Node b)
	 {
		b.rota.inverterRota();
		Cross(a, b.prev);
	 }
	 
	 public double aplicar(NoPosMel no) 
	 {
		switch(no.tipoMov)
		{
			case 0:	SWAP2IdaAdj2Ida(no.a, no.b); break;	
			case 1: SWAP2IdaAdj2Volta(no.a, no.b); break;
			case 2: SWAP2VoltaAdj2Ida(no.a, no.b);	break;
			case 3: SWAP2VoltaAdj2Volta(no.a, no.b); break;
			case 4: SWAP(no.a, no.b); break;
			case 5: SWAP2Adj1(no.a, no.b);	break;
			case 6: SWAP2Adj1Ivertido(no.a, no.b); break;
			case 7: SHIFT(no.a, no.b); break;
			case 8: SHIFT2Adj(no.a, no.b); break;
			case 9: SHIFT2AdjInvertido(no.a, no.b); break;
			case 10: executa2Opt(no.a, no.b);break;
			case 11: Cross(no.a, no.b); break;
			case 12: CrossInvertido(no.a, no.b); break;
			case 13: SHIFT3Adj(no.a, no.b); break;
			case 14: SHIFT3AdjInvertido(no.a, no.b); break;
			case 15: SWAP3Adj1(no.a, no.b);	break;
			case 16: SWAP3Adj1Ivertido(no.a, no.b); break;
			case 17: SWAP3IdaAdj2Ida(no.a, no.b); break;	
			case 18: SWAP3IdaAdj2Volta(no.a, no.b); break;
			case 19: SWAP3VoltaAdj2Ida(no.a, no.b);	break;
			case 20: SWAP3VoltaAdj2Volta(no.a, no.b); break;
			case 21: SWAP3IdaAdj3Ida(no.a, no.b); break;
			case 22: SWAP3IdaAdj3Volta(no.a, no.b); break;
			case 23: SWAP3VoltaAdj3Ida(no.a, no.b);	break;
			case 24: SWAP3VoltaAdj3Volta(no.a, no.b); break;
			case 25: SHIFT(no.a, no.antA);
					 SHIFT(no.b, no.antB);
					 break;


		}
		return no.custo;
	}
}
