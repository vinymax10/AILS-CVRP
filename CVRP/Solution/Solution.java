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
	Instance instance;
	Config config;
	protected int size;
	Node solution[];

	protected int inicio;
	protected Node deposito;
	int capacidade;
	public Route routes[];
	public int numRoutes;
	protected int numRoutesMin;
	protected int numRoutesMax;
	public double f = 0;
	public int distancia;
	double epsilon;
//	-----------Comparadores-----------

	BuscaLocalIntra buscaLocalIntra;

	public Solution(Instance instance, Config config)
	{
		this.instance = instance;
		this.config = config;
		this.pontos = instance.getPoints();
		int deposito = instance.getDepot();
		this.capacidade = instance.getCapacity();
		this.size = instance.getSize() - 1;
		this.solution = new Node[size];
		this.numRoutesMin = instance.getMinNumberRoutes();
		this.numRoutes = numRoutesMin;
		this.numRoutesMax = instance.getMaxNumberRoutes();
		this.deposito = new Node(pontos[deposito], instance);
		this.epsilon = config.getEpsilon();

		this.routes = new Route[numRoutesMax];

		for(int i = 0; i < routes.length; i++)
			routes[i] = new Route(instance, config, this.deposito, i);

		int cont = 0;
		for(int i = 0; i < (solution.length + 1); i++)
		{
			if(i != deposito)
			{
				solution[cont] = new Node(pontos[i], instance);
				cont++;
			}
		}
	}

	public void clone(Solution referencia)
	{
		this.numRoutes = referencia.numRoutes;
		this.f = referencia.f;

		for(int i = 0; i < routes.length; i++)
		{
			routes[i].nomeRoute = i;
			referencia.routes[i].nomeRoute = i;
		}

		for(int i = 0; i < routes.length; i++)
		{
			routes[i].demandaTotal = referencia.routes[i].demandaTotal;
			routes[i].fRoute = referencia.routes[i].fRoute;
			routes[i].numElements = referencia.routes[i].numElements;
			routes[i].alterada = referencia.routes[i].alterada;

			if(referencia.routes[i].inicio.prev == null)
				routes[i].inicio.prev = null;
			else if(referencia.routes[i].inicio.prev.name == 0)
				routes[i].inicio.prev = routes[i].inicio;
			else
				routes[i].inicio.prev = solution[referencia.routes[i].inicio.prev.name - 1];

			if(referencia.routes[i].inicio.next == null)
				routes[i].inicio.next = null;
			else if(referencia.routes[i].inicio.next.name == 0)
				routes[i].inicio.next = routes[i].inicio;
			else
				routes[i].inicio.next = solution[referencia.routes[i].inicio.next.name - 1];
		}

		for(int i = 0; i < solution.length; i++)
		{
			solution[i].route = routes[referencia.solution[i].route.nomeRoute];
			solution[i].jaInserido = referencia.solution[i].jaInserido;

			if(referencia.solution[i].prev.name == 0)
				solution[i].prev = routes[referencia.solution[i].prev.route.nomeRoute].inicio;
			else
				solution[i].prev = solution[referencia.solution[i].prev.name - 1];

			if(referencia.solution[i].next.name == 0)
				solution[i].next = routes[referencia.solution[i].next.route.nomeRoute].inicio;
			else
				solution[i].next = solution[referencia.solution[i].next.name - 1];
		}
	}

	// ------------------------Visualizacao-------------------------

	public String toStringMeu()
	{
		String str = "size: " + size;
		str += "\n" + "deposito: " + deposito;
		str += "\nNumRoutes: " + numRoutes;
		str += "\nCapacidade: " + capacidade;

		str += "\nf: " + f;
//		System.out.println(str);
		for(int i = 0; i < numRoutes; i++)
		{
//			System.out.println(str);
			str += "\n" + routes[i];
		}

		return str;
	}

	@Override
	public String toString()
	{
		String str = "";
		for(int i = 0; i < numRoutes; i++)
		{
			str += routes[i].toString2() + "\n";
		}
		str += "Cost " + f + "\n";
		return str;
	}

	public int infactibilidade()
	{
		int infac = 0;
		for(int i = 0; i < numRoutes; i++)
		{
			if(routes[i].availableCapacity() < 0)
				infac += routes[i].availableCapacity();
		}
		return infac;
	}

	public boolean auditoria(String local, boolean factibildiade, boolean routeVazia)
	{
		double f;
		double somaF = 0;
		int somaNumElements = 0;
		boolean erro = false;

		for(int i = 0; i < numRoutes; i++)
		{
			routes[i].findErro();
			f = routes[i].F();
			somaF += f;
			somaNumElements += routes[i].numElements;

			if(Math.abs(f - routes[i].fRoute) > epsilon)
			{
				System.out.println("-------------------" + local + " ERRO-------------------" + "\n" + routes[i].toString() + "\nf esperado: " + f);
				erro = true;
			}

			if(routeVazia && routes[i].inicio == routes[i].inicio.next)
			{
				System.out.println("-------------------" + local + " ERRO-------------------" + "Route vazia: " + routes[i].toString());
				erro = true;
			}

			if(routes[i].inicio.name != 0)
			{
				System.out.println("-------------------" + local + " ERRO-------------------" + "Route iniciando sem deposito: " + routes[i].toString());
				erro = true;
			}

			if(factibildiade && !routes[i].isFactivel())
			{
				System.out.println("-------------------" + local + " ERRO-------------------" + "Route infactivel: " + routes[i].toString());
				erro = true;
			}

		}
		if(Math.abs(somaF - this.f) > epsilon)
		{
			erro = true;
			System.out.println("-------------------" + local + " ERRO somatorio Total-------------------");
			System.out.println("Espedado: " + somaF + " obtido: " + this.f);
			System.out.println(this.toStringMeu());
		}

		if((somaNumElements - numRoutes) != size)
		{
			erro = true;
			System.out.println("-------------------" + local + " ERRO quantidade Elementos-------------------");
			System.out.println("Espedado: " + size + " obtido: " + (somaNumElements - numRoutes));

			System.out.println(this);
		}
		return erro;
	}

	public boolean factivel()
	{
		for(int i = 0; i < numRoutes; i++)
		{
			if(routes[i].availableCapacity() < 0)
				return false;
		}
		return true;
	}

	public void removeRoutesVazias()
	{
		for(int i = 0; i < numRoutes; i++)
		{
			if(routes[i].inicio == routes[i].inicio.next)
			{
				removeRoute(i);
				i--;
			}
		}
	}

	private void removeRoute(int index)
	{
		Route aux = routes[index];
		if(index != numRoutes - 1)
		{
			routes[index] = routes[numRoutes - 1];

			routes[numRoutes - 1] = aux;
		}
		numRoutes--;
	}

	// ------------------------carregaSolution-------------------------

	public void carregaSolution(String nome)
	{
		BufferedReader in;
		try
		{
			in = new BufferedReader(new FileReader(nome));
			String str[] = null;
			String linha;

			linha = in.readLine();
			str = linha.split(" ");

//			int custo=Integer.valueOf(str[0]);
			for(int i = 0; i < 3; i++)
				in.readLine();

			int indexRoute = 0;
			linha = in.readLine();
			str = linha.split(" ");

			System.out.println("-------------- str.length: " + str.length);
			for(int i = 0; i < str.length; i++)
			{
				System.out.print(str[i] + "-");
			}
			System.out.println();

			do
			{
				routes[indexRoute].addNoFinal(deposito.clone());
				for(int i = 9; i < str.length - 1; i++)
				{
					System.out.println("add: " + solution[Integer.valueOf(str[i].trim()) - 1] + " na route: " + routes[indexRoute].nomeRoute);
					f += routes[indexRoute].addNoFinal(solution[Integer.valueOf(str[i]) - 1]);
				}
				indexRoute++;
				linha = in.readLine();
				if(linha != null)
					str = linha.split(" ");
			}
			while(linha != null);

//			System.out.println("f: "+f+" esperado: "+custo);
		}
		catch(IOException e)
		{
			System.out.println("Erro ao Ler Arquivo");
		}
	}

	public void carregaSolution1(String nome)
	{
		BufferedReader in;
		try
		{
			in = new BufferedReader(new FileReader(nome));
			String str[] = null;

			str = in.readLine().split(" ");
			int indexRoute = 0;
			while(!str[0].equals("Cost"))
			{
				for(int i = 2; i < str.length; i++)
				{
					f += routes[indexRoute].addNoFinal(solution[Integer.valueOf(str[i]) - 1]);
				}
				indexRoute++;
				str = in.readLine().split(" ");
			}
		}
		catch(IOException e)
		{
			System.out.println("Erro ao Ler Arquivo");
		}
	}

	public Route[] getRoutes()
	{
		return routes;
	}

	public int getNumRoutes()
	{
		return numRoutes;
	}

	public Node getDeposito()
	{
		return deposito;
	}

	public Node[] getSolution()
	{
		return solution;
	}

	public int getNumRoutesMax()
	{
		return numRoutesMax;
	}

	public void setNumRoutesMax(int numRoutesMax)
	{
		this.numRoutesMax = numRoutesMax;
	}

	public int getNumRoutesMin()
	{
		return numRoutesMin;
	}

	public void setNumRoutesMin(int numRoutesMin)
	{
		this.numRoutesMin = numRoutesMin;
	}

	public void escrecerSolucao(String end)
	{
		File arq = new File(end);
		arq.write(this.toString());
		arq.close();
	}

}
