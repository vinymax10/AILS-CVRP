package Solution;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import Data.File;
import Data.Instance;
import Data.Point;
import Improvement.IntraLocalSearch;
import SearchMethod.Config;

public class Solution
{
	private Point pontos[];
	Instance instance;
	Config config;
	protected int size;
	Node solution[];

	protected int first;
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

	IntraLocalSearch intraLocalSearch;

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

		int count = 0;
		for(int i = 0; i < (solution.length + 1); i++)
		{
			if(i != deposito)
			{
				solution[count] = new Node(pontos[i], instance);
				count++;
			}
		}
	}

	public void clone(Solution reference)
	{
		this.numRoutes = reference.numRoutes;
		this.f = reference.f;

		for(int i = 0; i < routes.length; i++)
		{
			routes[i].nomeRoute = i;
			reference.routes[i].nomeRoute = i;
		}

		for(int i = 0; i < routes.length; i++)
		{
			routes[i].totalDemand = reference.routes[i].totalDemand;
			routes[i].fRoute = reference.routes[i].fRoute;
			routes[i].numElements = reference.routes[i].numElements;
			routes[i].modified = reference.routes[i].modified;

			if(reference.routes[i].first.prev == null)
				routes[i].first.prev = null;
			else if(reference.routes[i].first.prev.name == 0)
				routes[i].first.prev = routes[i].first;
			else
				routes[i].first.prev = solution[reference.routes[i].first.prev.name - 1];

			if(reference.routes[i].first.next == null)
				routes[i].first.next = null;
			else if(reference.routes[i].first.next.name == 0)
				routes[i].first.next = routes[i].first;
			else
				routes[i].first.next = solution[reference.routes[i].first.next.name - 1];
		}

		for(int i = 0; i < solution.length; i++)
		{
			solution[i].route = routes[reference.solution[i].route.nomeRoute];
			solution[i].nodeBelong = reference.solution[i].nodeBelong;

			if(reference.solution[i].prev.name == 0)
				solution[i].prev = routes[reference.solution[i].prev.route.nomeRoute].first;
			else
				solution[i].prev = solution[reference.solution[i].prev.name - 1];

			if(reference.solution[i].next.name == 0)
				solution[i].next = routes[reference.solution[i].next.route.nomeRoute].first;
			else
				solution[i].next = solution[reference.solution[i].next.name - 1];
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
		int capViolation = 0;
		for(int i = 0; i < numRoutes; i++)
		{
			if(routes[i].availableCapacity() < 0)
				capViolation += routes[i].availableCapacity();
		}
		return capViolation;
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

			if(routeVazia && routes[i].first == routes[i].first.next)
			{
				System.out.println("-------------------" + local + " ERRO-------------------" + "Route vazia: " + routes[i].toString());
				erro = true;
			}

			if(routes[i].first.name != 0)
			{
				System.out.println("-------------------" + local + " ERRO-------------------" + "Route iniciando sem deposito: " + routes[i].toString());
				erro = true;
			}

			if(factibildiade && !routes[i].isFeasible())
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

	public boolean feasible()
	{
		for(int i = 0; i < numRoutes; i++)
		{
			if(routes[i].availableCapacity() < 0)
				return false;
		}
		return true;
	}

	public void removeEmptyRoutes()
	{
		for(int i = 0; i < numRoutes; i++)
		{
			if(routes[i].first == routes[i].first.next)
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
