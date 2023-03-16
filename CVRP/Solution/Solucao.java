package Solution;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import Data.Arquivo;
import Data.Instance;
import Data.Ponto;
import Improvement.BuscaLocalIntra;
import SearchMethod.Config;

public class Solucao 
{
	private Ponto pontos[];
	Instance instancia;
	Config config;
	protected int size;
	No solucao[];

	protected int inicio;
	protected No deposito;
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
	
	public Solucao(Instance instancia,Config config) 
	{
		this.instancia=instancia;
		this.config=config;
		this.pontos=instancia.getPontos();
		int deposito=instancia.getDeposito();
		this.capacidade=instancia.getCapacidade();
		this.size=instancia.getSize()-1;
		this.solucao=new No[size];
		this.NumRotasMin=instancia.getNumRotasMin();
		this.NumRotas=NumRotasMin;
		this.NumRotasMax=instancia.getNumRotasMax();
		this.deposito=new No(pontos[deposito],instancia);
		this.epsilon=config.getEpsilon();

		this.rotas=new Rota[NumRotasMax];
		
		for (int i = 0; i < rotas.length; i++) 
			rotas[i]=new Rota(instancia,config, this.deposito,i);
		
		int cont=0;
		for (int i = 0; i < (solucao.length+1); i++)
		{
			if(i!=deposito)
			{
				solucao[cont]=new No(pontos[i],instancia);
				cont++;
			}
		}
	}


	public void clone(Solucao referencia)
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

			if(referencia.rotas[i].inicio.ant==null)
				rotas[i].inicio.ant=null;
			else if(referencia.rotas[i].inicio.ant.nome==0)
				rotas[i].inicio.ant=rotas[i].inicio;
			else
				rotas[i].inicio.ant=solucao[referencia.rotas[i].inicio.ant.nome-1];
			
			if(referencia.rotas[i].inicio.prox==null)
				rotas[i].inicio.prox=null;
			else if(referencia.rotas[i].inicio.prox.nome==0)
				rotas[i].inicio.prox=rotas[i].inicio;
			else
				rotas[i].inicio.prox=solucao[referencia.rotas[i].inicio.prox.nome-1];
		}
		
		for (int i = 0; i < solucao.length; i++)
		{
			solucao[i].rota=rotas[referencia.solucao[i].rota.nomeRota];
			solucao[i].jaInserido=referencia.solucao[i].jaInserido;
			
			if(referencia.solucao[i].ant.nome==0)
				solucao[i].ant=rotas[referencia.solucao[i].ant.rota.nomeRota].inicio;
			else
				solucao[i].ant=solucao[referencia.solucao[i].ant.nome-1];
				
			if(referencia.solucao[i].prox.nome==0)
				solucao[i].prox=rotas[referencia.solucao[i].prox.rota.nomeRota].inicio;
			else
				solucao[i].prox=solucao[referencia.solucao[i].prox.nome-1];
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
			
			if(rotaVazia&&rotas[i].inicio==rotas[i].inicio.prox)
			{
				System.out.println("-------------------"+local+" ERRO-------------------"
				+"Rota vazia: "+rotas[i].toString());
				erro=true;
			}
			
			if(rotas[i].inicio.nome!=0)
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
			if(rotas[i].inicio==rotas[i].inicio.prox)
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

	public No getDeposito() {
		return deposito;
	}


	public No[] getSolucao() {
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
		Arquivo arq=new Arquivo(end);
		arq.escrever(this.toString());
		arq.finalizar();
	}
	
}
