package Improvement;

import Avaliadores.AvaliadorCusto;
import Solucao.No;
import Solucao.Rota;

public class NoPosMel implements Comparable<NoPosMel>
{
	public boolean ativo;
	public double custo;
	public double custoAvaliacao;
	public int tipoMov=0;
	public No a,b,antA,antB;
	public int indexARota, indexBRota;
	public Rota rotaA,rotaB;
	public boolean intra;
	public int ganho;
	AvaliadorCusto avaliadorCusto;
	
	public NoPosMel(AvaliadorCusto avaliadorCusto)
	{
		this.avaliadorCusto=avaliadorCusto;
		limpar();
	}
	
	public void setNoMelhora(double custo, int tipoMov, No a, No b,int indexARota,int indexBRota,double custoAvaliacao) 
	{
		this.ativo = true;
		this.custo = custo;
		this.tipoMov = tipoMov;
		this.a = a;
		this.b = b;
		this.indexARota=indexARota;
		this.indexBRota=indexBRota;
		this.custoAvaliacao=custoAvaliacao;
	}
	
	public void setNoMelhora(double custo, int tipoMov, No a, No b,double custoAvaliacao) 
	{
		this.ativo = true;
		this.custo = custo;
		this.tipoMov = tipoMov;
		this.a = a;
		this.b = b;
		this.rotaA=a.rota;
		this.rotaB=b.rota;
		this.custoAvaliacao=custoAvaliacao;
	}
	
	public void setNoMelhora(double custo, int tipoMov, No a, No b, No antA, No antB, double custoAvaliacao) 
	{
		this.ativo = true;
		this.custo = custo;
		this.tipoMov = tipoMov;
		this.a = a;
		this.b = b;
		this.antA = antA;
		this.antB = antB;
		this.rotaA=a.rota;
		this.rotaB=b.rota;
		this.custoAvaliacao=custoAvaliacao;
	}
	
	public void setNoMelhora(double custo, int tipoMov, No a, No b, No antA, No antB, double custoAvaliacao,int ganho) 
	{
		this.ativo = true;
		this.custo = custo;
		this.tipoMov = tipoMov;
		this.a = a;
		this.b = b;
		this.antA = antA;
		this.antB = antB;
		this.rotaA=a.rota;
		this.rotaB=b.rota;
		this.custoAvaliacao=custoAvaliacao;
		this.ganho=ganho;
	}
	
	public void setNoMelhora(double custo, int tipoMov, No a, No b,double custoAvaliacao,int ganho) 
	{
		this.ativo = true;
		this.custo = custo;
		this.tipoMov = tipoMov;
		this.a = a;
		this.b = b;
		this.rotaA=a.rota;
		this.rotaB=b.rota;
		this.custoAvaliacao=custoAvaliacao;
		this.ganho=ganho;
	}
	
	public void clone(NoPosMel x) 
	{
		this.ativo = x.ativo;
		this.custo = x.custo;
		this.tipoMov = x.tipoMov;
		this.a = x.a;
		this.b = x.b;
		this.rotaA=a.rota;
		this.rotaB=b.rota;
		this.indexARota=x.indexARota;
		this.indexBRota=x.indexBRota;
		this.custoAvaliacao=x.custoAvaliacao;
	}
	
	public void limpar()
	{
		this.custoAvaliacao=Integer.MAX_VALUE;
		this.custo=Integer.MAX_VALUE;
		this.ativo=false;
	}

	public void update2()
	{
		switch(tipoMov)
		{
			case 4: 
					if(b!=a&&b.prox!=a)
					{
						custo=avaliadorCusto.custoSWAP(a, b);	
						if(custo<0)
							this.ativo=true;
						else
							limpar();
					}
					else
						limpar();
					break;
				
			case 7: 
					if(a!=b&&a!=b.prox)
					{
						custo=avaliadorCusto.custoSHIFT(a, b);	
						if(custo<0)
							this.ativo=true;
						else
							limpar();
					}
					else
						limpar();
					break;
				
			case 10: 
					if(a!=b&&b!=a.prox) 
					{
						custo=avaliadorCusto.custo2Opt(a, b);	
						if(custo<0)
							this.ativo=true;
						else
							limpar();
					 }
					 else
						 limpar();
					 break;
					 
				
		}
	}


	@Override
	public String toString() 
	{
		if(ativo)
			return "NoPosMel [ativo=" + ativo + ", custo=" + custo + ", custoAvaliacao=" + custoAvaliacao + ", tipoMov="
				+ tipoMov + ", a=" + a + ", b=" + b + ", nomeRotaA=" + a.rota.nomeRota + ", nomeRotaB=" + b.rota.nomeRota
				+ ", intra=" + intra + ", ganho=" + ganho + "]";
		else
			return "NoPosMel [ativo=" + ativo + ", custo=" + custo + ", custoAvaliacao=" + custoAvaliacao;
	}

	public int compareTo(NoPosMel x) 
	{
		if(this.custoAvaliacao!=x.custoAvaliacao)
		{
			if( this.custoAvaliacao>x.custoAvaliacao)
				return 1;
			
			if( this.custoAvaliacao<x.custoAvaliacao)
				return -1;
		}
		else
		{
			if( this.ganho>x.ganho)
				return -1;
			
			if( this.ganho<x.ganho)
				return 1;
			
		}
		return 0;
	}
}
