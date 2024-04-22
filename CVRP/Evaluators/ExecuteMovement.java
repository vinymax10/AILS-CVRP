package Evaluators;

import Data.Instance;
import Improvement.CandidateNode;
import Solution.Node;
import Solution.Route;

public class ExecuteMovement
{
	Route aRoute, bRoute;
	Node aPrev, bPrev, aNext, bNext;
	Instance instance;

	public ExecuteMovement(Instance instance)
	{
		this.instance = instance;
	}

	public void SWAP(Node a, Node b)
	{
		aRoute = a.route;
		bRoute = b.route;

		if(a.next == b || a.prev == b)
		{
			if(a.next == b)
			{
				aRoute.remove(a);
				aRoute.addDepois(a, b);
			}
			else
			{
				aRoute.remove(b);
				aRoute.addDepois(b, a);
			}
		}
		else
		{
			Node myPrev = a.prev;
			Node noPrev = b.prev;

			aRoute.remove(a);
			bRoute.remove(b);

			bRoute.addDepois(a, noPrev);
			aRoute.addDepois(b, myPrev);
		}
	}

	public void SHIFT(Node a, Node b)
	{
		aRoute = a.route;
		bRoute = b.route;

		aRoute.remove(a);
		bRoute.addDepois(a, b);
	}

	public void executa2Opt(Node a, Node b)
	{
		double custo = custo2Opt(a, b);

		aNext = a.next;
		bNext = b.next;
		Node aux = a;
		aux.next = b;
		Node prev = aux.prev;
		while(aux.next != aNext)
		{
			prev = aux;
			aux = aux.next;
			aux.next = aux.prev;
			aux.prev = prev;
		}

		prev = aux;
		aux = aux.next;
		aux.next = bNext;
		aux.prev = prev;

		prev = aux;
		aux = aux.next;
		aux.prev = prev;

		a.route.fRoute += custo;
		a.route.setDemandaAcumulada();
		a.route.alterada = true;
	}

	public double custo2Opt(Node a, Node b)
	{
		return -(instance.dist(a.name, a.next.name) + instance.dist(b.name, b.next.name))
		+ (instance.dist(a.name, b.name) + instance.dist(a.next.name, b.next.name));
	}

	public void Cross(Node a, Node b)
	{
		aRoute = a.route;
		bRoute = b.route;

		Node aux = a.next;
		Node prev = b;

		while(aux != aRoute.inicio)
		{
			aRoute.remove(aux);
			bRoute.addDepois(aux, prev);
			prev = aux;
			aux = a.next;
		}

		Node ultimo = prev;
		aux = ultimo.next;
		prev = a;
		while(aux != bRoute.inicio)
		{
			bRoute.remove(aux);
			aRoute.addDepois(aux, prev);
			prev = aux;
			aux = ultimo.next;
		}
	}

	public void CrossInverted(Node a, Node b)
	{
		b.route.inverterRoute();
		Cross(a, b.prev);
	}

	public double aplicar(CandidateNode no)
	{
		switch(no.tipoMov) {
		case SWAP:
			SWAP(no.a, no.b);
			break;
		case SHIFT:
			SHIFT(no.a, no.b);
			break;
		case TwoOpt:
			executa2Opt(no.a, no.b);
			break;
		case Cross:
			Cross(no.a, no.b);
			break;
		case CrossInverted:
			CrossInverted(no.a, no.b);
			break;
		case SWAPEstrela:
			SHIFT(no.a, no.prevA);
			SHIFT(no.b, no.prevB);
			break;

		}
		return no.custo;
	}
}
