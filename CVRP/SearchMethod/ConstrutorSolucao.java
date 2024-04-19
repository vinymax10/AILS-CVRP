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
	private Node []solucao;
	protected Random rand=new Random();
	protected int size;
	Instance instancia;
	Node naoInseridos[];
	int contNaoInseridos=0;
	
	public ConstrutorSolucao(Instance instancia,Config config)
	{
		this.instancia=instancia;
		this.rotas=new Rota[instancia.getNumRotasMax()];
		this.size=instancia.getSize()-1;
		this.naoInseridos=new Node[size];
	}
	
	private void setSolucao(Solution solucao) 
	{
		this.NumRotas=solucao.NumRotas;
		this.solucao=solucao.getSolution();
		this.f=solucao.f;
		for (int i = 0; i < rotas.length; i++) 
			this.rotas[i]=solucao.rotas[i];
	}

	private void passaResultado(Solution solucao) 
	{
		solucao.NumRotas=this.NumRotas;
		solucao.f=this.f;
		for (int i = 0; i < rotas.length; i++) 
			solucao.rotas[i]=this.rotas[i];
	}

	public void construir(Solution s)
	{
		setSolucao(s);
		
		for (int i = 0; i < rotas.length; i++)
			rotas[i].limpar();
		
		int index;
		Node no,bestNo;
		f=0;
		
		for (int i = 0; i < size; i++) 
			naoInseridos[contNaoInseridos++]=solucao[i];
		
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
		
		for (int i = 0; i < solucao.length; i++) 
		{
			aux=solucao[i];
			if(aux.jaInserido)
			{
				custo=instancia.dist(aux.name,no.name)+instancia.dist(no.name,aux.next.name)-instancia.dist(aux.name,aux.next.name);
				if(custo<bestCusto)
				{
					bestCusto=custo;
					bestNo=aux;
				}
			}
		}
		custo=instancia.dist(bestNo.name,no.name)+instancia.dist(no.name,bestNo.next.name)-instancia.dist(bestNo.name,bestNo.next.name);
		custoAnt=instancia.dist(bestNo.prev.name,no.name)+instancia.dist(no.name,bestNo.name)-instancia.dist(bestNo.prev.name,bestNo.name);
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
