package SearchMethod;

import java.util.Random;

import Data.Instance;
import Solution.No;
import Solution.Rota;
import Solution.Solucao;


public class ConstrutorSolucao 
{
	private Rota rotas[];
	private double f=0;
	private int NumRotas;
	private No []solucao;
	protected Random rand=new Random();
	protected int size;
	Instance instancia;
	No naoInseridos[];
	int contNaoInseridos=0;
	
	public ConstrutorSolucao(Instance instancia,Config config)
	{
		this.instancia=instancia;
		this.rotas=new Rota[instancia.getNumRotasMax()];
		this.size=instancia.getSize()-1;
		this.naoInseridos=new No[size];
	}
	
	private void setSolucao(Solucao solucao) 
	{
		this.NumRotas=solucao.NumRotas;
		this.solucao=solucao.getSolucao();
		this.f=solucao.f;
		for (int i = 0; i < rotas.length; i++) 
			this.rotas[i]=solucao.rotas[i];
	}

	private void passaResultado(Solucao solucao) 
	{
		solucao.NumRotas=this.NumRotas;
		solucao.f=this.f;
		for (int i = 0; i < rotas.length; i++) 
			solucao.rotas[i]=this.rotas[i];
	}

	public void construir(Solucao s)
	{
		setSolucao(s);
		
		for (int i = 0; i < rotas.length; i++)
			rotas[i].limpar();
		
		int index;
		No no,bestNo;
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
	
	protected No getBestKNNNo(No no)
	{
		double bestCusto=Double.MAX_VALUE;
		No aux,bestNo=null;
		double custo,custoAnt;
		
		for (int i = 0; i < solucao.length; i++) 
		{
			aux=solucao[i];
			if(aux.jaInserido)
			{
				custo=instancia.dist(aux.nome,no.nome)+instancia.dist(no.nome,aux.prox.nome)-instancia.dist(aux.nome,aux.prox.nome);
				if(custo<bestCusto)
				{
					bestCusto=custo;
					bestNo=aux;
				}
			}
		}
		custo=instancia.dist(bestNo.nome,no.nome)+instancia.dist(no.nome,bestNo.prox.nome)-instancia.dist(bestNo.nome,bestNo.prox.nome);
		custoAnt=instancia.dist(bestNo.ant.nome,no.nome)+instancia.dist(no.nome,bestNo.nome)-instancia.dist(bestNo.ant.nome,bestNo.nome);
		if(custo<custoAnt)
			return bestNo;
		
		return bestNo.ant;
	}
	
	protected No getBestNoRotas(No no)
	{
		double bestCusto=Double.MAX_VALUE;
		No aux,bestNo=null;
		
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
