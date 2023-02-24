package Dados;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import MetodoBusca.Config;
import MetodoBusca.LeituraParametros;

public class Instancia 
{
	private int size;
	private double mediaPorRota;
	private int knn[][];
	private float dist[][];
	private NoKnn[]VizKnn;
	private int capacidade;
	private int deposito;
	private int numRotasMin;
	private int numRotasMax;
	private Ponto pontos[];
	double coord[][];
	private String nome;
	private double maiorDist=0;
	private double menorDist=Integer.MAX_VALUE;
	TipoEdgeType tipoEdgeType;
	double distancia;
	boolean rounded;
	double somaDemanda;
	String str[];
	Config config;
	boolean print = false;
	
	public Instancia(LeituraParametros leitor) 
	{
		this.nome=leitor.getFile();
		this.config=leitor.getConfig();
		this.rounded=leitor.isRounded();
		
		BufferedReader in;
		try 
		{
			in = new BufferedReader(new FileReader(nome));
			
			str=in.readLine().split(":");
			while(!str[0].trim().equals("EOF"))
			{
				switch(str[0].trim())
				{
					case "DIMENSION": size=Integer.valueOf(str[1].trim());break;
					case "EDGE_WEIGHT_TYPE":
					case "EDGE_WEIGHT_FORMAT": setTipoCoord(str[1].trim());break;
					case "CAPACITY": capacidade=Integer.valueOf(str[1].trim());break;
					case "NODE_COORD_SECTION": leituraCoord(in);break;
					case "EDGE_WEIGHT_SECTION": leituraMatrizDist(in);break;
					case "DEMAND_SECTION": leituraDemanda(in);break;
					case "DEPOT_SECTION": leituraDeposito(in);break;
				}
				str=in.readLine().split(":");
			}
				
			numRotasMin=(int) Math.ceil(somaDemanda/capacidade);
			mediaPorRota=(double)size/numRotasMin;
			double porcent=numRotasMin*((double)1/mediaPorRota);
			numRotasMax=numRotasMin+(3+(int)Math.ceil(porcent));
			
		} 
		catch (IOException e) {
	    	System.out.println("Erro ao Ler Arquivo");
	    }
		VizKnn=null;
	}

	public double dist(int i,int j)
	{
		return dist[i][j];
	}
	
	public double distCalc(int i,int j)
	{
		if(i!=j)
		{
			if(tipoEdgeType==TipoEdgeType.EUC_2D)
			{
				if(rounded)
					return distanciaRounded(pontos[i].x,pontos[i].y,pontos[j].x,pontos[j].y);
				else
					return distancia(pontos[i].x,pontos[i].y,pontos[j].x,pontos[j].y);
			}
			else
				return distanciaGeo(coord[i][0],coord[i][1],coord[j][0],coord[j][1]);
		}
		else
			return 0;
	}
	
	private double distancia(double x1,double y1,double x2,double y2)
	{
		return Math.sqrt(((x1-x2)*(x1-x2))+((y1-y2)*(y1-y2)));
	}
	
	private int distanciaRounded(double x1,double y1,double x2,double y2)
	{
		return (short) Math.round(Math.sqrt(((x1-x2)*(x1-x2))+((y1-y2)*(y1-y2))));
	}
	
	private void setTipoCoord(String str)
	{
		if(str.equals("EUC_2D"))
			tipoEdgeType=TipoEdgeType.EUC_2D;
		else if(str.equals("EXPLICIT"))
			tipoEdgeType=TipoEdgeType.EXPLICIT;
		else
		{
			tipoEdgeType=TipoEdgeType.Coord;
			coord=new double[size][2];
		}
	}
	
	private void leituraCoord(BufferedReader in)
	{
		if(print)
			System.out.println("leituraCoord");
		
		pontos=new Ponto [size];
		for (int i = 0; i < pontos.length; i++) 
			pontos[i]=new Ponto(i);
		
		try 
		{
			for (int i = 0; i < size; i++) 
			{
				if(tipoEdgeType==TipoEdgeType.EUC_2D)
				{
					str=in.readLine().trim().replaceAll("\\s+"," ").split("[' '|'\t']");
					pontos[i].x=Double.valueOf(str[1].trim());
					pontos[i].y=Double.valueOf(str[2].trim());
				}
				else
				{
					str=in.readLine().split("[' '|'\t']");
					coord[i][0]=Double.valueOf(str[1].trim());
					coord[i][1]=Double.valueOf(str[2].trim());
					pontos[i].x=Double.valueOf((int) (coord[i][0]*10000));
					pontos[i].y=Double.valueOf((int) (coord[i][1]*10000));
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(tipoEdgeType==TipoEdgeType.EUC_2D)
		{
			//calculando Distancias
			int limiteKnn=Math.min(config.getLimiteKnn(), size-1);
			knn=new int[size][limiteKnn];
			VizKnn=new NoKnn[size-1];
			
			for (int i = 0; i < VizKnn.length; i++) 
				VizKnn[i]=new NoKnn();
			
			dist=new float[size][size];
			
			int cont=0;
			for (int i = 0; i < size; i++) 
			{
				cont=0;
				for (int j = 0; j < size; j++) 
				{
					distancia=distCalc(i,j);
					
					dist[i][j]=(float)distancia;
					
					if(i<j)
					{
						if(maiorDist<distancia)
							maiorDist=distancia;
						
						if(menorDist>distancia)
							menorDist=distancia;
					}
					
					if(i!=j)
					{
						VizKnn[cont].dist=distancia;
						VizKnn[cont].nome=j;
						cont++;
					}
				}
				
				Arrays.sort(VizKnn);
				
				for (int j = 0; j < limiteKnn; j++) 
					knn[i][j]=VizKnn[j].nome;
			}
		}
	}
	
	private void leituraMatrizDist(BufferedReader in)
	{
		//calculando Distancias
		int limiteKnn=Math.min(config.getLimiteKnn(), size-1);
		knn=new int[size][limiteKnn];
		VizKnn=new NoKnn[size-1];
		
		for (int i = 0; i < VizKnn.length; i++) 
			VizKnn[i]=new NoKnn();
		
		dist=new float[size][size];
				
		try 
		{
			for (int i = 1; i < size; i++) 
			{
				str=in.readLine().trim().replaceAll("\\s+"," ").split("[' '|'\t']");
				for (int j = 0; j < str.length; j++) 
				{
					distancia=Double.valueOf(str[j].trim());
//					System.out.print(distancia+" ");
					dist[i][j]=(short) distancia;
					dist[j][i]=dist[i][j];
				}
//				System.out.println();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int cont=0;
		for (int i = 0; i < size; i++) 
		{
			cont=0;
			for (int j = 0; j < size; j++) 
			{
				distancia=dist[i][j];
				
				if(i<j)
				{
					if(maiorDist<distancia)
						maiorDist=distancia;
					
					if(menorDist>distancia)
						menorDist=distancia;
				}
				
				if(i!=j)
				{
					VizKnn[cont].dist=distancia;
					VizKnn[cont].nome=j;
					cont++;
				}
			}
			
			Arrays.sort(VizKnn);
			
			for (int j = 0; j < limiteKnn; j++) 
				knn[i][j]=VizKnn[j].nome;
			
		}
	}
	
	private void leituraDemanda(BufferedReader in)
	{
		if(print)
			System.out.println("leituraDemanda");
		
		try {
			somaDemanda=0;
			for (int i = 0; i < size; i++) 
			{
				str=in.readLine().trim().replaceAll("\\s+"," ").split("[' '|'\t']");
				pontos[i].demanda=Integer.valueOf(str[1]);
				somaDemanda+=pontos[i].demanda;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void leituraDeposito(BufferedReader in)
	{
		try {
				deposito=Integer.valueOf(in.readLine().trim())-1;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private int distanciaGeo(double Lat1,double Long1,double Lat2,double Long2)
	{
		// Convers�o de graus pra radianos das latitudes
		double firstLatToRad = Math.toRadians(Lat1);
		double secondLatToRad = Math.toRadians(Lat2);

		// Diferen�a das longitudes
		double deltaLongitudeInRad = Math.toRadians(Long2
		- Long1);

		// C�lcula da dist�ncia entre os pontos
		return (int) Math.round(Math.acos(Math.cos(firstLatToRad) * Math.cos(secondLatToRad)
		* Math.cos(deltaLongitudeInRad) + Math.sin(firstLatToRad)
		* Math.sin(secondLatToRad))
		* 6378100);
	}
	
	@Override
	public String toString() {
		return "size=" + size + "\n capacidade=" + capacidade
				+ "\ndeposito="+ deposito +" numRotasMin: "+numRotasMin;
	}

	public Ponto[] getPontos() {
		return pontos;
	}


	public void setPontos(Ponto[] pontos) {
		this.pontos = pontos;
	}

	public int getNumRotasMin() {
		return numRotasMin;
	}

	public int getNumRotasMax() {
		return numRotasMax;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(int capacidade) {
		this.capacidade = capacidade;
	}

	public int getDeposito() {
		return deposito;
	}

	public void setDeposito(int deposito) {
		this.deposito = deposito;
	}

	public double getMaiorDist() {
		return maiorDist;
	}
	
	public double getMenorDist() {
		return menorDist;
	}

	public String getNome() {
		return nome;
	}

	public int[][] getKnn() {
		return knn;
	}
	
}
