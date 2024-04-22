package Improvement;

import Evaluators.CostEvaluation;
import Evaluators.TipoMovimento;
import Solution.Node;
import Solution.Route;

public class CandidateNode implements Comparable<CandidateNode>
{
	public boolean ativo;
	public double custo;
	public double custoAvaliacao;
	public TipoMovimento tipoMov;
	public Node a,b,prevA,prevB;
	public int indexARoute, indexBRoute;
	public Route routeA,routeB;
	public boolean intra;
	public int gain;
	CostEvaluation avaliadorCusto;
	
	public CandidateNode(CostEvaluation avaliadorCusto)
	{
		this.avaliadorCusto=avaliadorCusto;
		limpar();
	}
	
	public void setNoMelhora(double custo, TipoMovimento tipoMov, Node a, Node b,int indexARoute,int indexBRoute,double custoAvaliacao) 
	{
		this.ativo = true;
		this.custo = custo;
		this.tipoMov = tipoMov;
		this.a = a;
		this.b = b;
		this.indexARoute=indexARoute;
		this.indexBRoute=indexBRoute;
		this.custoAvaliacao=custoAvaliacao;
	}
	
	public void setNoMelhora(double custo, TipoMovimento tipoMov, Node a, Node b,double custoAvaliacao) 
	{
		this.ativo = true;
		this.custo = custo;
		this.tipoMov = tipoMov;
		this.a = a;
		this.b = b;
		this.routeA=a.route;
		this.routeB=b.route;
		this.custoAvaliacao=custoAvaliacao;
	}
	
	public void setNoMelhora(double custo, TipoMovimento tipoMov, Node a, Node b, Node prevA, Node prevB, double custoAvaliacao) 
	{
		this.ativo = true;
		this.custo = custo;
		this.tipoMov = tipoMov;
		this.a = a;
		this.b = b;
		this.prevA = prevA;
		this.prevB = prevB;
		this.routeA=a.route;
		this.routeB=b.route;
		this.custoAvaliacao=custoAvaliacao;
	}
	
	public void setNoMelhora(double custo, TipoMovimento tipoMov, Node a, Node b, Node prevA, Node prevB, double custoAvaliacao,int gain) 
	{
		this.ativo = true;
		this.custo = custo;
		this.tipoMov = tipoMov;
		this.a = a;
		this.b = b;
		this.prevA = prevA;
		this.prevB = prevB;
		this.routeA=a.route;
		this.routeB=b.route;
		this.custoAvaliacao=custoAvaliacao;
		this.gain=gain;
	}
	
	public void setNoMelhora(double custo, TipoMovimento tipoMov, Node a, Node b,double custoAvaliacao,int gain) 
	{
		this.ativo = true;
		this.custo = custo;
		this.tipoMov = tipoMov;
		this.a = a;
		this.b = b;
		this.routeA=a.route;
		this.routeB=b.route;
		this.custoAvaliacao=custoAvaliacao;
		this.gain=gain;
	}
	
	public void clone(CandidateNode x) 
	{
		this.ativo = x.ativo;
		this.custo = x.custo;
		this.tipoMov = x.tipoMov;
		this.a = x.a;
		this.b = x.b;
		this.routeA=a.route;
		this.routeB=b.route;
		this.indexARoute=x.indexARoute;
		this.indexBRoute=x.indexBRoute;
		this.custoAvaliacao=x.custoAvaliacao;
	}
	
	public void limpar()
	{
		this.custoAvaliacao=Integer.MAX_VALUE;
		this.custo=Integer.MAX_VALUE;
		this.ativo=false;
	}

	@Override
	public String toString() 
	{
		if(ativo)
			return "CandidateNode [ativo=" + ativo + ", custo=" + custo + ", custoAvaliacao=" + custoAvaliacao + ", tipoMov="
				+ tipoMov + ", a=" + a + ", b=" + b + ", nomeRouteA=" + a.route.nomeRoute + ", nomeRouteB=" + b.route.nomeRoute
				+ ", intra=" + intra + ", gain=" + gain + "]";
		else
			return "CandidateNode [ativo=" + ativo + ", custo=" + custo + ", custoAvaliacao=" + custoAvaliacao;
	}

	public int compareTo(CandidateNode x) 
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
			if( this.gain>x.gain)
				return -1;
			
			if( this.gain<x.gain)
				return 1;
			
		}
		return 0;
	}
}
