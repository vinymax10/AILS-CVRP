package Solution;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import Data.File;
import Data.Instance;
import Data.Point;
import Improvement.BuscaLocalIntra;
import SearchMethod.Config;

public class Solution 
{
	private Point pontos[];
	Instance instancia;
	Config config;
	protected int size;
	Node solucao[];

	protected int inicio;
	protected Node deposito;
	int capacidade;
	public Rota rotas[];
	public int NumRotas;
	protected int NumRotasMin;
	protected int NumRotasMax;
	public double f=0;
	public int distancia;
	double epsilon;
//	-----------Comparadores-----------
	
	BuscaLocalIntra buscaLocalIntra;
	
	public Solution(Instance instancia,Config config) 
	{
		this.instancia=instancia;
		this.config=config;
		this.pontos=instancia.getPontos();
		int deposito=instancia.getDeposito();
		this.capacidade=instancia.getCapacidade();
		this.size=instancia.getSize()-1;
		this.solucao=new Node[size];
		this.NumRotasMin=instancia.getNumRotasMin();
		this.NumRotas=NumRotasMin;
		this.NumRotasMax=instancia.getNumRotasMax();
		this.deposito=new Node(pontos[deposito],instancia);
		this.epsilon=config.getEpsilon();

		this.rotas=new Rota[NumRotasMax];
		
		for (int i = 0; i < rotas.length; i++) 
			rotas[i]=new Rota(instancia,config, this.deposito,i);
		
		int cont=0;
		for (int i = 0; i < (solucao.length+1); i++)
		{
			if(i!=deposito)
			{
				solucao[cont]=new Node(pontos[i],instancia);
				cont++;
			}
		}
	}


	public void clone(Solution referencia)
	{
		this.NumRotas=referencia.NumRotas;
		this.f=referencia.f;
		
		for (int i = 0; i < rotas.length; i++)
		{
			rotas[i].nomeRota=i;
			referencia.rotas[i].nomeRota=i;
		}
		
		for (int i = 0; i < rotas.length; i++)
		{
			rotas[i].demandaTotal=referencia.rotas[i].demandaTotal;
			rotas[i].fRota=referencia.rotas[i].fRota;
			rotas[i].numElements=referencia.rotas[i].numElements;
			rotas[i].alterada=referencia.rotas[i].alterada;

			if(referencia.rotas[i].inicio.prev==null)
				rotas[i].inicio.prev=null;
			else if(referencia.rotas[i].inicio.prev.name==0)
				rotas[i].inicio.prev=rotas[i].inicio;
			else
				rotas[i].inicio.prev=solucao[referencia.rotas[i].inicio.prev.name-1];
			
			if(referencia.rotas[i].inicio.next==null)
				rotas[i].inicio.next=null;
			else if(referencia.rotas[i].inicio.next.name==0)
				rotas[i].inicio.next=rotas[i].inicio;
			else
				rotas[i].inicio.next=solucao[referencia.rotas[i].inicio.next.name-1];
		}
		
		for (int i = 0; i < solucao.length; i++)
		{
			solucao[i].rota=rotas[referencia.solucao[i].rota.nomeRota];
			solucao[i].jaInserido=referencia.solucao[i].jaInserido;
			
			if(referencia.solucao[i].prev.name==0)
				solucao[i].prev=rotas[referencia.solucao[i].prev.rota.nomeRota].inicio;
			else
				solucao[i].prev=solucao[referencia.solucao[i].prev.name-1];
				
			if(referencia.solucao[i].next.name==0)
				solucao[i].next=rotas[referencia.solucao[i].next.rota.nomeRota].inicio;
			else
				solucao[i].next=solucao[referencia.solucao[i].next.name-1];
		}
	}
	
	//------------------------Visualizacao-------------------------
	
	public String toStringMeu() 
	{
		String str="size: " + size;
		str+="\n"+"deposito: " + deposito;
		str+="\nNumRotas: "+NumRotas;
		str+="\nCapacidade: "+capacidade;

		str+="\nf: "+f;
//		System.out.println(str);
		for (int i = 0; i < NumRotas; i++) 
		{
//			System.out.println(str);
			str+="\n"+rotas[i];
		}
		
		return str;
	}
	
	
	@Override
	public String toString() 
	{
		String str="";
		for (int i = 0; i < NumRotas; i++) 
		{
			str+=rotas[i].toString2()+"\n";
		}
		str+="Cost "+f+"\n";
		return str;
	}
	
	public int infactibilidade()
	{
		int infac=0;
		for (int i = 0; i < NumRotas; i++)
		{
			if(rotas[i].espacoLivre()<0)
				infac+=rotas[i].espacoLivre();
		}
		return infac;
	}
	
	public boolean auditoria(String local, boolean factibildiade, boolean rotaVazia)
	{
		double f;
		double somaF=0;
		int somaNumElements=0;
		boolean erro=false;
		
		for (int i = 0; i < NumRotas; i++)
		{
			rotas[i].findErro();
			f=rotas[i].F();
			somaF+=f;
			somaNumElements+=rotas[i].numElements;
			
			if(Math.abs(f-rotas[i].fRota)>epsilon)
			{
				System.out.println("-------------------"+local+" ERRO-------------------"
				+ "\n"+rotas[i].toString()+"\nf esperado: "+f);
				erro=true;
			}
			
			if(rotaVazia&&rotas[i].inicio==rotas[i].inicio.next)
			{
				System.out.println("-------------------"+local+" ERRO-------------------"
				+"Rota vazia: "+rotas[i].toString());
				erro=true;
			}
			
			if(rotas[i].inicio.name!=0)
			{
				System.out.println("-------------------"+local+" ERRO-------------------"
				+"Rota iniciando sem deposito: "+rotas[i].toString());
				erro=true;
			}
			
			if(factibildiade&&!rotas[i].isFactivel())
			{
				System.out.println("-------------------"+local+" ERRO-------------------"
				+"Rota infactivel: "+rotas[i].toString());
				erro=true;
			}

		}
		if(Math.abs(somaF-this.f)>epsilon)
		{
			erro=true;
			System.out.println("-------------------"+local+" ERRO somatorio Total-------------------");
			System.out.println("Espedado: "+somaF+" obtido: "+this.f);
			System.out.println(this.toStringMeu());
		}
		
		if((somaNumElements-NumRotas)!=size)
		{
			erro=true;
			System.out.println("-------------------"+local+" ERRO quantidade Elementos-------------------");
			System.out.println("Espedado: "+size+" obtido: "+(somaNumElements-NumRotas));

			System.out.println(this);
		}
		return erro;
	}
	
	public boolean factivel() 
	{
		for (int i = 0; i < NumRotas; i++)
		{
			if(rotas[i].espacoLivre()<0)
				return false;
		}
		return true;
	}
	
	public void removeRotasVazias()
	{
		for (int i = 0; i < NumRotas; i++) 
		{
			if(rotas[i].inicio==rotas[i].inicio.next)
			{
				removeRota(i);
				i--;
			}
		}
	}
	
	private void removeRota(int index)
	{
		Rota aux=rotas[index];
		if(index!=NumRotas-1)
		{
			rotas[index]=rotas[NumRotas-1];
			
			rotas[NumRotas-1]=aux;
		}
		NumRotas--;
	}
	
	//------------------------carregaSolution-------------------------

	public void carregaSolution(String nome)
	{
		BufferedReader in;
		try 
		{
			in = new BufferedReader(new FileReader(nome));
			String str []= null;
			String linha;
			
			linha=in.readLine();
			str=linha.split(" ");
			
//			int custo=Integer.valueOf(str[0]);
			for (int i = 0; i < 3; i++) 
				in.readLine();

			int indexRota=0;
			linha=in.readLine();
			str=linha.split(" ");
			
			System.out.println("-------------- str.length: "+str.length);
			for (int i = 0; i < str.length; i++) 
			{
				System.out.print(str[i]+"-");
			}
			System.out.println();
			
			do
			{
				rotas[indexRota].addNoFinal(deposito.clone());
				for (int i = 9; i < str.length-1; i++)
				{
					System.out.println("add: "+solucao[Integer.valueOf(str[i].trim())-1]+" na rota: "+rotas[indexRota].nomeRota);
					f+=rotas[indexRota].addNoFinal(solucao[Integer.valueOf(str[i])-1]);
				}
				indexRota++;
				linha=in.readLine();
				if(linha!=null)
					str=linha.split(" ");
			}
			while(linha!=null);
			
//			System.out.println("f: "+f+" esperado: "+custo);
		} 
		catch (IOException e) {
	    	System.out.println("Erro ao Ler Arquivo");
	    }
	}
	
	public void carregaSolution1(String nome)
	{
		BufferedReader in;
		try 
		{
			in = new BufferedReader(new FileReader(nome));
			String str []= null;
			
			str=in.readLine().split(" ");
			int indexRota=0;
			while(!str[0].equals("Cost"))
			{
				for (int i = 2; i < str.length; i++)
				{
//					System.out.println("add: "+solucao[Integer.valueOf(str[i])-1]+" na rota: "+rotas[indexRota].nomeRota);
					f+=rotas[indexRota].addNoFinal(solucao[Integer.valueOf(str[i])-1]);
				}
				indexRota++;
				str=in.readLine().split(" ");
			}
//			int cost=Integer.valueOf(str[1]);
			
//			System.out.println("f: "+f+" esperado: "+cost);
		} 
		catch (IOException e) {
	    	System.out.println("Erro ao Ler Arquivo");
	    }
	}
	
	public Rota[] getRotas() {
		return rotas;
	}

	public int getNumRotas() {
		return NumRotas;
	}

	public Node getDeposito() {
		return deposito;
	}


	public Node[] getSolution() {
		return solucao;
	}

	public int getNumRotasMax() {
		return NumRotasMax;
	}

	public void setNumRotasMax(int numRotasMax) {
		NumRotasMax = numRotasMax;
	}

	public int getNumRotasMin() {
		return NumRotasMin;
	}

	public void setNumRotasMin(int numRotasMin) {
		NumRotasMin = numRotasMin;
	}

	public void escrecerSolucao(String end)
	{
		File arq=new File(end);
		arq.write(this.toString());
		arq.close();
	}
	
}
