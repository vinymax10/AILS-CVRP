package SearchMethod;

import java.util.Random;

import Data.Instance;
import Solution.Node;
import Solution.Route;
import Solution.Solution;


public class ConstrutorSolucao 
{
	private Route routes[];
	private double f=0;
	private int numRoutes;
	private Node []solution;
	protected Random rand=new Random();
	protected int size;
	Instance instance;
	Node naoInseridos[];
	int contNaoInseridos=0;
	
	public ConstrutorSolucao(Instance instance,Config config)
	{
		this.instance=instance;
		this.routes=new Route[instance.getMaxNumberRoutes()];
		this.size=instance.getSize()-1;
		this.naoInseridos=new Node[size];
	}
	
	private void setSolution(Solution solution) 
	{
		this.numRoutes=solution.numRoutes;
		this.solution=solution.getSolution();
		this.f=solution.f;
		for (int i = 0; i < routes.length; i++) 
			this.routes[i]=solution.routes[i];
	}

	private void passaResultado(Solution solution) 
	{
		solution.numRoutes=this.numRoutes;
		solution.f=this.f;
		for (int i = 0; i < routes.length; i++) 
			solution.routes[i]=this.routes[i];
	}

	public void construir(Solution s)
	{
		setSolution(s);
		
		for (int i = 0; i < routes.length; i++)
			routes[i].limpar();
		
		int index;
		Node no,bestNo;
		f=0;
		
		for (int i = 0; i < size; i++) 
			naoInseridos[contNaoInseridos++]=solution[i];
		
		for (int i = 0; i < numRoutes; i++)
		{
			index=rand.nextInt(contNaoInseridos);
			f+=routes[i].addNoFinal(naoInseridos[index]);
			
			no=naoInseridos[index];
			naoInseridos[index]=naoInseridos[contNaoInseridos-1];
			naoInseridos[--contNaoInseridos]=no;
		}
		
		while(contNaoInseridos>0) 
		{
			index=rand.nextInt(contNaoInseridos);
			no=naoInseridos[index];
			bestNo=getBestNoRoutes(no);
			f+=bestNo.route.addDepois(no, bestNo);
			naoInseridos[index]=naoInseridos[contNaoInseridos-1];
			naoInseridos[--contNaoInseridos]=no;
		}
		
		passaResultado(s);
		s.removeRoutesVazias();
	}
	
	protected Node getBestKNNNo(Node no)
	{
		double bestCusto=Double.MAX_VALUE;
		Node aux,bestNo=null;
		double custo,custoPrev;
		
		for (int i = 0; i < solution.length; i++) 
		{
			aux=solution[i];
			if(aux.jaInserido)
			{
				custo=instance.dist(aux.name,no.name)+instance.dist(no.name,aux.next.name)-instance.dist(aux.name,aux.next.name);
				if(custo<bestCusto)
				{
					bestCusto=custo;
					bestNo=aux;
				}
			}
		}
		custo=instance.dist(bestNo.name,no.name)+instance.dist(no.name,bestNo.next.name)-instance.dist(bestNo.name,bestNo.next.name);
		custoPrev=instance.dist(bestNo.prev.name,no.name)+instance.dist(no.name,bestNo.name)-instance.dist(bestNo.prev.name,bestNo.name);
		if(custo<custoPrev)
			return bestNo;
		
		return bestNo.prev;
	}
	
	protected Node getBestNoRoutes(Node no)
	{
		double bestCusto=Double.MAX_VALUE;
		Node aux,bestNo=null;
		
		for (int i = 0; i < numRoutes; i++) 
		{
			aux=routes[i].findBestPosition(no);
			if(routes[i].menorCusto<bestCusto)
			{
				bestCusto=routes[i].menorCusto;
				bestNo=aux;
			}
		}
		
		return bestNo;
	}
	
}
