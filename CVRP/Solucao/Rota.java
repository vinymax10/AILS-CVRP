package Solucao;

import Dados.Instancia;
import MetodoBusca.Config;

public class Rota implements Comparable<Rota>
{
	public int  capacidade;
	public int deposito;

	public No inicio;
	public boolean podeMelhorar;
	public boolean alterada;
	public int demandaTotal=0;
	public double fRota=0,antF;
	double acrescimoF;
	public int numElements=0;
	public int nomeRota;
	No ant,prox;
	int contCaminho;
	int sizeCaminhoSwap;
	
	//busca local best
	public double menorCusto=0;
	No aux;
	
	//------------------------------------------------
	public double custo,custoRemocao;
	public No bestCusto;
	public double distancia,menorDist;
	//------------------------------------------------
	public boolean update;
	
    Instancia instancia;
    int limiteAdj;
    
	public Rota(Instancia instancia, Config config, No deposito,int nomeRota) 
	{
		this.instancia=instancia;
		this.capacidade = instancia.getCapacidade();
		this.deposito=deposito.nome;
		this.nomeRota=nomeRota;
		this.inicio=null;
		this.limiteAdj=config.getLimiteAdj();
		this.sizeCaminhoSwap=config.getSizeCaminhoSwap();
		addNoFinal(deposito.clone());
	}
	
	public No findDistPosition(No no,No solucao[])
	{
        bestCusto=inicio;
        aux=inicio.prox;
        menorCusto=instancia.dist(inicio.nome,no.nome)+instancia.dist(no.nome,inicio.prox.nome)-instancia.dist(inicio.nome,inicio.prox.nome);
        int cont=0;
        for (int i = 0; i < no.knn.length; i++) 
        {
            if(no.knn[i]!=0)
            {
                aux=solucao[no.knn[i]-1];
                if(aux.rota.nomeRota==nomeRota)
                {
                    custo=instancia.dist(aux.nome,no.nome)+instancia.dist(no.nome,aux.prox.nome)-instancia.dist(aux.nome,aux.prox.nome);
                    if(custo<menorCusto)
                    {
                        menorCusto=custo;
                        bestCusto=aux;
                    }
                    cont++;
                    if(cont>=3)
                        break;
                }
            }
        }
        return bestCusto;
	}
        
    public No findBestPosition(No no)
	{
		bestCusto=inicio;
		aux=inicio.prox;
		menorCusto=instancia.dist(inicio.nome,no.nome)+instancia.dist(no.nome,inicio.prox.nome)-instancia.dist(inicio.nome,inicio.prox.nome);
		do
		{
			custo=instancia.dist(aux.nome,no.nome)+instancia.dist(no.nome,aux.prox.nome)-instancia.dist(aux.nome,aux.prox.nome);
			if(custo<menorCusto)
			{
				menorCusto=custo;
				bestCusto=aux;
			}
			aux=aux.prox;
		}
		while(aux!=inicio);
		return bestCusto;
	}
    
    public No findBestPositionExceto(No no,No excecao)
  	{
  		bestCusto=inicio;
  		aux=inicio.prox;
  		
  		menorCusto=instancia.dist(inicio.nome,no.nome)+instancia.dist(no.nome,inicio.prox.nome)-instancia.dist(inicio.nome,inicio.prox.nome);
  		do
  		{
  			custo=instancia.dist(aux.nome,no.nome)+instancia.dist(no.nome,aux.prox.nome)-instancia.dist(aux.nome,aux.prox.nome);
  			if(custo<menorCusto&&aux.nome!=excecao.nome)
  			{
  				menorCusto=custo;
  				bestCusto=aux;
  			}
  			aux=aux.prox;
  		}
  		while(aux!=inicio);
  		return bestCusto;
  	}
    
    public No findBestPositionExcetoKNN(No no,No excecao, No solucao[])
   	{
    	menorCusto=Double.MAX_VALUE;
    	
   		bestCusto=inicio;
   		
   		if(numElements>limiteAdj)
   		{
   			for (int j = 0; j < limiteAdj; j++) 
   			{
   	   			if(no.getKnn()[j]==0)
   	   			{
   	   				aux=inicio;
   	   				custo=instancia.dist(aux.nome,no.nome)+instancia.dist(no.nome,aux.prox.nome)-instancia.dist(aux.nome,aux.prox.nome);
   	   				if(custo<menorCusto)
   	   				{
   	   					menorCusto=custo;
   	   					bestCusto=aux;
   	   				}   				
   	   			}
   	   			else if(no.getKnn()[j]!=excecao.nome&&solucao[no.getKnn()[j]-1].rota==this)
   	   			{
   	   				aux=solucao[no.getKnn()[j]-1];
   	   				custo=instancia.dist(aux.nome,no.nome)+instancia.dist(no.nome,aux.prox.nome)-instancia.dist(aux.nome,aux.prox.nome);
   	   				if(custo<menorCusto)
   	   				{
   	   					menorCusto=custo;
   	   					bestCusto=aux;
   	   				}
   	   			}
   	   			
   			}
   		}
   		else
   		{
   			findBestPositionExceto(no,excecao);
   		}
   		
   		return bestCusto;
   	}
    
    public No findBestPositionExcetoPath(No no,No excecao)
   	{
    	menorCusto=Double.MAX_VALUE;
    	
   		bestCusto=inicio;
   		
   		if(numElements>sizeCaminhoSwap)
   		{
   			aux=excecao.prox;
   			contCaminho=0;
   	  		do
   	  		{
   	  			contCaminho++;
   	  			custo=instancia.dist(aux.nome,no.nome)+instancia.dist(no.nome,aux.prox.nome)-instancia.dist(aux.nome,aux.prox.nome);
   	  			if(custo<menorCusto)
   	  			{
   	  				menorCusto=custo;
   	  				bestCusto=aux;
   	  			}
   	  			aux=aux.prox;
   	  		}
   	  		while(contCaminho<(sizeCaminhoSwap/2));
   	  		
   	  		aux=excecao.ant;
			contCaminho=0;
	  		do
	  		{
	  			contCaminho++;
	  			custo=instancia.dist(aux.nome,no.nome)+instancia.dist(no.nome,aux.prox.nome)-instancia.dist(aux.nome,aux.prox.nome);
	  			if(custo<menorCusto)
	  			{
	  				menorCusto=custo;
	  				bestCusto=aux;
	  			}
	  			aux=aux.ant;
	  		}
	  		while(contCaminho<(sizeCaminhoSwap/2));
   		}
   		else
   		{
   			findBestPositionExceto(no,excecao);
   		}
   		return bestCusto;
   	}
	
	public void limpar()
	{
		inicio.ant=inicio;
		inicio.prox=inicio;
		
		demandaTotal=0;
		fRota=0;
		numElements=1;
		alterada=true;
	}
	
	
	public void setDemandaAcumulada()
	{
		inicio.demandaAcumulada=0;
		aux=inicio.prox;
		do
		{
			aux.demandaAcumulada=aux.ant.demandaAcumulada+aux.demanda;
			aux=aux.prox;
		}
		while(aux!=inicio);
	}
	
	public void inverterRota()
	{
		aux=inicio;
		No ant=inicio.ant;
		No prox=inicio.prox;
		do
		{
			aux.ant=prox;
			aux.prox=ant;
			aux=prox;
			ant=aux.ant;
			prox=aux.prox;
		}
		while(aux!=inicio);
	}
	
	public double F()
	{
		double f=0;
		No no=inicio;
		No prox=no.prox;
		do
		{
			f+=instancia.dist(no.nome,prox.nome);
			no=prox;
			prox=no.prox;
		}
		while(prox!=inicio);
		
		f+=instancia.dist(no.nome,prox.nome);
		return f;
	}
	
	public void findErro()
	{
		int cont=0;
		No aux=inicio;
		
		do
		{
			if(aux.ant==null||aux.prox==null)
			{
				System.out.println("Erro no null No: "+aux+" em:\n"+this.toString());
				System.out.println(this);
			}
			if(aux.rota!=this)
				System.out.println("Erro no : "+aux+" com rota Errada:\n"+this.toString());

			cont++;
			aux=aux.prox;
		}
		while(aux!=inicio);
		if(cont!=numElements)
			System.out.println("Erro no numElements \n"+this.toString());

//		if(cont==0)
//			System.out.println("Erro so tem um elemento\n"+this.toString());
	}
	
	//------------------------Visualizacao-------------------------

	@Override
	public String toString() 
	{
		String str="Rota: "+nomeRota;
		str+=" f: "+fRota;
		str+=" espaco: "+espacoLivre();
		str+=" size: "+numElements+" = ";
		str+=" alterada: "+alterada+" = ";

		No aux=inicio;
		do
		{
			str+=aux+"->";
//			System.out.println(str);
			aux=aux.prox;
		}
		while(aux!=inicio);
		
		return str;
	}
	
	public String toString2() 
	{
		String str="Route #"+(nomeRota+1)+": ";
		No aux=inicio.prox;
		do
		{
			str+=aux.nome+" ";
			aux=aux.prox;
		}
		while(aux!=inicio);
		
		return str;
	}
	
	public boolean isFactivel()
	{
		if((capacidade-demandaTotal)>=0)
			return true;
		return false;
	}
	
	public int espacoLivre()
	{
		return capacidade-demandaTotal;
	}
	
	public void setAcrescimoF(int acrescimoF) {
		this.acrescimoF = acrescimoF;
	}

	public int getNumElements() {
		return numElements;
	}

	public void setNumElements(int numElements) {
		this.numElements = numElements;
	}
	
	@Override
	public int compareTo(Rota x) 
	{
		return nomeRota-x.nomeRota;
	}

	public double remove(No no) 
	{
		double custo=no.custoRemocao();
//		System.out.println("custo: "+custo+" fRota: "+fRota);
		
		if(no==inicio)
			inicio=no.ant;
		
		alterada=true;
		no.alterado=true;
		no.prox.alterado=true;
		no.ant.alterado=true;
		
		no.jaInserido=false;
		
		fRota+=custo;
		numElements--;
		
		ant=no.ant;
		prox=no.prox;
		
		ant.prox=prox;
		prox.ant=ant;
		
		demandaTotal-=no.demanda;
		
		no.rota=null;
		no.ant=null;
		no.prox=null;
		
//		setDemandaAcumulada();
		
		return custo;
	}

	//------------------------Add No-------------------------
	
	public double addNoFinal(No no)
	{
		no.rota=this;
		if(inicio==null)
		{
			inicio=no;
			inicio.ant=no;
			inicio.prox=no;
			numElements++;
			demandaTotal=0;
			fRota=0;
			return 0;
		}
		else if(no.nome==0)
		{
			aux=inicio.ant;
			inicio=no;
			return addDepois(no, aux); 
		}
		else 
		{
			aux=inicio.ant;
			return addDepois(no, aux); 
		}
	}
	
	public double addDepois(No no1, No no2) 
	{
		acrescimoF=no1.custoInserirApos(no2);
		alterada=true;
		no1.jaInserido=true;
		no1.alterado=true;
		no2.prox.alterado=true;
		no2.ant.alterado=true;
		
		numElements++;
		fRota+=acrescimoF;
		
		demandaTotal+=no1.demanda;

		no1.rota=this;
		
		prox=no2.prox;
		no2.prox=no1;
		no1.ant=no2;
		
		prox.ant=no1;
		no1.prox=prox;
		
		if(no1.nome==0)
			inicio=no1;
		
//		setDemandaAcumulada();
		
		return acrescimoF;
	}
}

