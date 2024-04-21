package SearchMethod;

import java.util.Random;

import Data.Instance;
import Solution.Node;
import Solution.Rota;
import Solution.Solution;


public class ConstrutorSolucao 
{
	private Rota rotas[];
	private double f=0;
	private int NumRotas;
	private Node []solution;
	protected Random rand=new Random();
	protected int size;
	Instance instance;
	Node naoInseridos[];
	int contNaoInseridos=0;
	
	public ConstrutorSolucao(Instance instance,Config config)
	{
		this.instance=instance;
		this.rotas=new Rota[instance.getMaxNumberRoutes()];
		this.size=instance.getSize()-1;
		this.naoInseridos=new Node[size];
	}
	
	private void setSolution(Solution solution) 
	{
		this.NumRotas=solution.NumRotas;
		this.solution=solution.getSolution();
		this.f=solution.f;
		for (int i = 0; i < rotas.length; i++) 
			this.rotas[i]=solution.rotas[i];
	}

	private void passaResultado(Solution solution) 
	{
		solution.NumRotas=this.NumRotas;
		solution.f=this.f;
		for (int i = 0; i < rotas.length; i++) 
			solution.rotas[i]=this.rotas[i];
	}

	public void construir(Solution s)
	{
		setSolution(s);
		
		for (int i = 0; i < rotas.length; i++)
			rotas[i].limpar();
		
		int index;
		Node no,bestNo;
		f=0;
		
		for (int i = 0; i < size; i++) 
			naoInseridos[contNaoInseridos++]=solution[i];
		
		for (int i = 0; i < NumRotas; i++)
		{
			index=rand.nextInt(contNaoInseridos);
			f+=rotas[i].addNoFinal(naoInseridos[index]);
			
			no=naoInseridos[index];
			naoInseridos[index]=naoInseridos[contNaoInseridos-1];
			naoInseridos[--contNaoInseridos]=no;
		}
		
		while(contNaoInseridos>0) 
		{
			index=rand.nextInt(contNaoInseridos);
			no=naoInseridos[index];
			bestNo=getBestNoRotas(no);
			f+=bestNo.rota.addDepois(no, bestNo);
			naoInseridos[index]=naoInseridos[contNaoInseridos-1];
			naoInseridos[--contNaoInseridos]=no;
		}
		
		passaResultado(s);
		s.removeRotasVazias();
	}
	
	protected Node getBestKNNNo(Node no)
	{
		double bestCusto=Double.MAX_VALUE;
		Node aux,bestNo=null;
		double custo,custoAnt;
		
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
		custoAnt=instance.dist(bestNo.prev.name,no.name)+instance.dist(no.name,bestNo.name)-instance.dist(bestNo.prev.name,bestNo.name);
		if(custo<custoAnt)
			return bestNo;
		
		return bestNo.prev;
	}
	
	protected Node getBestNoRotas(Node no)
	{
		double bestCusto=Double.MAX_VALUE;
		Node aux,bestNo=null;
		
		for (int i = 0; i < NumRotas; i++) 
		{
			aux=rotas[i].findBestPosition(no);
			if(rotas[i].menorCusto<bestCusto)
			{
				bestCusto=rotas[i].menorCusto;
				bestNo=aux;
			}
		}
		
		return bestNo;
	}
	
}
