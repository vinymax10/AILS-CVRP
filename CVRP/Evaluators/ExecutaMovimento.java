package Evaluators;

import Data.Instance;
import Improvement.NoPosMel;
import Solution.No;
import Solution.Rota;

public class ExecutaMovimento 
{
	Rota aRota, bRota;
	No aAnt, bAnt, aProx, aProxProx, bProx, bProxProx;
	Instance instancia;
	
	public ExecutaMovimento(Instance instancia)
	{
		this.instancia=instancia;
	}
	
	public void SWAP2IdaAdj2Ida(No a, No b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aAnt=a.ant;
		 bAnt=b.ant;
		 aProx=a.prox;
		 bProx=b.prox;
		 
		 if(a.prox.prox==b||a.ant==b.prox)
		 {
			 if(a.prox.prox==b)
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
				 
				 bRota.addDepois(a, b.ant);
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
	
	 public void SWAP2IdaAdj2Volta(No a, No b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aAnt=a.ant;
		 bAnt=b.ant;
		 aProx=a.prox;
		 bProx=b.prox;
		 
		 if(a.prox.prox==b||a.ant==b.prox)
		 {
			 if(a.prox.prox==b)
			 {
				 aRota.remove(b);
				 aRota.remove(bProx);
				 
				 bRota.addDepois(bProx, a.ant);
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
	 
	 public void SWAP2VoltaAdj2Ida(No a, No b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aAnt=a.ant;
		 bAnt=b.ant;
		 aProx=a.prox;
		 bProx=b.prox;
		 if(a.prox.prox==b||a.ant==b.prox)
		 {
			 if(a.prox.prox==b)
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
	 
	 public void SWAP2VoltaAdj2Volta(No a, No b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aAnt=a.ant;
		 bAnt=b.ant;
		 aProx=a.prox;
		 bProx=b.prox;
		 if(a.prox.prox==b||a.ant==b.prox)
		 {
			 if(a.prox.prox==b)
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
	 
	 public void SWAP3IdaAdj2Ida(No a, No b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aAnt=a.ant;
		 bAnt=b.ant;
		 aProx=a.prox;
		 aProxProx=a.prox.prox;
		 bProx=b.prox;
		 
		 if(a.prox.prox.prox==b||a.ant==b.prox)
		 {
			 if(a.prox.prox.prox==b)
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

				 bRota.addDepois(a, b.ant);
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
	 
	 public void SWAP3IdaAdj2Volta(No a, No b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aAnt=a.ant;
		 bAnt=b.ant;
		 aProx=a.prox;
		 aProxProx=a.prox.prox;
		 bProx=b.prox;
		 
		 if(a.prox.prox.prox==b||a.ant==b.prox)
		 {
			 if(a.prox.prox.prox==b)
			 {
				 aRota.remove(b);
				 aRota.remove(bProx);
				 
				 bRota.addDepois(bProx, a.ant);
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
	 
	 public void SWAP3VoltaAdj2Ida(No a, No b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aAnt=a.ant;
		 bAnt=b.ant;
		 aProx=a.prox;
		 aProxProx=a.prox.prox;
		 
		 bProx=b.prox;
		 if(a.prox.prox.prox==b||a.ant==b.prox)
		 {
			 if(a.prox.prox.prox==b)
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
	 
	 public void SWAP3VoltaAdj2Volta(No a, No b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aAnt=a.ant;
		 bAnt=b.ant;
		 aProx=a.prox;
		 aProxProx=a.prox.prox;
		 bProx=b.prox;
		 if(a.prox.prox.prox==b||a.ant==b.prox)
		 {
			 if(a.prox.prox.prox==b)
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
	 
	 public void SWAP3IdaAdj3Ida(No a, No b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aAnt=a.ant;
		 bAnt=b.ant;
		 aProx=a.prox;
		 aProxProx=a.prox.prox;
		 bProx=b.prox;
		 bProxProx=b.prox.prox;
		 
		 if(a.prox.prox.prox==b||a==b.prox.prox.prox)
		 {
			 if(a.prox.prox.prox==b)
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

				 bRota.addDepois(a, b.ant);
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
	 
	 public void SWAP3IdaAdj3Volta(No a, No b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aAnt=a.ant;
		 bAnt=b.ant;
		 aProx=a.prox;
		 aProxProx=a.prox.prox;
		 bProx=b.prox;
		 bProxProx=b.prox.prox;

		 if(a.prox.prox.prox==b||a==b.prox.prox.prox)
		 {
			 if(a.prox.prox.prox==b)
			 {
				 aRota.remove(b);
				 aRota.remove(bProx);
				 aRota.remove(bProxProx);

				 bRota.addDepois(bProxProx, a.ant);
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
	 
	 public void SWAP3VoltaAdj3Ida(No a, No b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aAnt=a.ant;
		 bAnt=b.ant;
		 aProx=a.prox;
		 aProxProx=a.prox.prox;
		 
		 bProx=b.prox;
		 bProxProx=b.prox.prox;

		 if(a.prox.prox.prox==b||a==b.prox.prox.prox)
		 {
			 if(a.prox.prox.prox==b)
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
	 
	 public void SWAP3VoltaAdj3Volta(No a, No b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aAnt=a.ant;
		 bAnt=b.ant;
		 aProx=a.prox;
		 aProxProx=a.prox.prox;
		 bProx=b.prox;
		 bProxProx=b.prox.prox;

		 if(a.prox.prox.prox==b||a==b.prox.prox.prox)
		 {
			 if(a.prox.prox.prox==b)
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
	 
	 public void SWAP(No a, No b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 if(a.prox==b||a.ant==b)
		 {
			 if(a.prox==b)
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
			 No myAnt=a.ant;
			 No noAnt=b.ant;
			 
			 aRota.remove(a);
			 bRota.remove(b);
			 
			 bRota.addDepois(a, noAnt);
			 aRota.addDepois(b,myAnt);
		 }
	 }
	 
	 public void SWAP2Adj1(No a, No b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aAnt=a.ant;
		 bAnt=b.ant;
		 aProx=a.prox;
		 
		 if(a.prox.prox==b||a.ant==b)
		 {
			 if(a.prox.prox==b)
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
				 
				 bRota.addDepois(a, b.ant);
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
	 
	 public void SWAP2Adj1Ivertido(No a, No b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aAnt=a.ant;
		 bAnt=b.ant;
		 aProx=a.prox;
		 
		 if(a.prox.prox==b||a.ant==b)
		 {
			 if(a.prox.prox==b)
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
				 
				 bRota.addDepois(aProx, b.ant);
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
	 
	 public void SWAP3Adj1(No a, No b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aAnt=a.ant;
		 bAnt=b.ant;
		 aProx=a.prox;
		 aProxProx=a.prox.prox;
		 
		 if(a.prox.prox.prox==b||a.ant==b)
		 {
			 if(a.prox.prox.prox==b)
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

				 bRota.addDepois(a, b.ant);
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
	 
	 public void SWAP3Adj1Ivertido(No a, No b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aAnt=a.ant;
		 bAnt=b.ant;
		 aProx=a.prox;
		 aProxProx=a.prox.prox;
		 
		 if(a.prox.prox.prox==b||a.ant==b)
		 {
			 if(a.prox.prox.prox==b)
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
				 
				 bRota.addDepois(aProxProx, b.ant);
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
	 
	 public void SHIFT(No a, No b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aRota.remove(a);
		 bRota.addDepois(a, b);
	 }
	 
	 public void SHIFT2Adj(No a, No b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aProx=a.prox;
		 
		 aRota.remove(a);
		 aRota.remove(aProx);
		
		 bRota.addDepois(a, b);
		 bRota.addDepois(aProx, a);
	 }
	 
	 public void SHIFT3Adj(No a, No b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aProx=a.prox;
		 aProxProx=a.prox.prox;
		 
		 aRota.remove(a);
		 aRota.remove(aProx);
		 aRota.remove(aProxProx);

		 bRota.addDepois(a, b);
		 bRota.addDepois(aProx, a);
		 bRota.addDepois(aProxProx, aProx);
	 }
	 
	 public void SHIFT2AdjInvertido(No a, No b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aProx=a.prox;
		 
		 aRota.remove(a);
		 aRota.remove(aProx);
		
		 bRota.addDepois(aProx, b);
		 bRota.addDepois(a,aProx);
	 }
	 
	 public void SHIFT3AdjInvertido(No a, No b)
	 {
		 aRota=a.rota;
		 bRota=b.rota;
		 
		 aProx=a.prox;
		 aProxProx=a.prox.prox;

		 aRota.remove(a);
		 aRota.remove(aProx);
		 aRota.remove(aProxProx);
		 
		 bRota.addDepois(aProxProx, b);
		 bRota.addDepois(aProx, aProxProx);
		 bRota.addDepois(a,aProx);
	 }
	 
	 public void executa2Opt(No a, No b)
	 {
		 double custo=custo2Opt(a,b);
		 
		 aProx=a.prox;
		 bProx=b.prox;
		 No aux=a;
		 aux.prox=b;
		 No ant=aux.ant;
		 while(aux.prox!=aProx)
		 {
			 ant=aux;
			 aux=aux.prox;
			 aux.prox=aux.ant;
			 aux.ant=ant;
		 }
		 
		 ant=aux;
		 aux=aux.prox;
		 aux.prox=bProx;
		 aux.ant=ant;

		 ant=aux;
		 aux=aux.prox;
		 aux.ant=ant;
		 
		 a.rota.fRota+=custo;
		 a.rota.setDemandaAcumulada();
		 a.rota.alterada=true;
	 }
	 
	public double custo2Opt(No a, No b)
	{
		return 	-(instancia.dist(a.nome,a.prox.nome)+instancia.dist(b.nome,b.prox.nome))+				
				(instancia.dist(a.nome,b.nome)+instancia.dist(a.prox.nome,b.prox.nome));
	}
	 
	 public void Cross(No a, No b)
	 {
		aRota=a.rota;
		bRota=b.rota;
		
		No aux=a.prox;
		No ant=b;
		
		while(aux!=aRota.inicio)
		{
			aRota.remove(aux);
			bRota.addDepois(aux, ant);
			ant=aux;
			aux=a.prox;
		}
		
		No ultimo=ant;
		aux=ultimo.prox;
		ant=a;
		while(aux!=bRota.inicio)
		{
			bRota.remove(aux);
			aRota.addDepois(aux, ant);
			ant=aux;
			aux=ultimo.prox;
		}
	 }
	 
	 
	 public void CrossInvertido(No a, No b)
	 {
		b.rota.inverterRota();
		Cross(a, b.ant);
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
