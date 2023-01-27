package MetodoBusca;

import java.text.DecimalFormat;
import java.util.Arrays;

import Perturbacao.HeuristicaAdicao;
import Perturbacao.TipoPerturbacao;

public class Config implements Cloneable
{
	DecimalFormat deci=new DecimalFormat("0.000");
	double etaMin,etaMax;
	int distMMin,distMMax;	
	
	int numIterUpdate;
	TipoPerturbacao perturbacao[];
	HeuristicaAdicao[]heuristicasAdicao;
	//--------------------PR-------------------
	int limiteAdj;
	double epsilon;
	int limiteKnn;
	TipoCriterioParada tipoCriterioParada;
	
	public Config() 
	{
//		----------------------------Main----------------------------
		this.tipoCriterioParada=TipoCriterioParada.Time;
		this.distMMin=15;
		this.distMMax=30;
		this.numIterUpdate=30; 
		this.limiteKnn=100;
		this.limiteAdj=40;
		
		
		this.epsilon=0.01;
		this.etaMin=0.01;
		this.etaMax=1;
		
		this.perturbacao=new TipoPerturbacao[2];
		this.perturbacao[0]=TipoPerturbacao.Sequential;
		this.perturbacao[1]=TipoPerturbacao.Concentric;
		
		this.heuristicasAdicao=new HeuristicaAdicao[2];
		heuristicasAdicao[0]=HeuristicaAdicao.Distance;
		heuristicasAdicao[1]=HeuristicaAdicao.Cost;
	}
	
	public Config clone()
	{
		try {
			return (Config) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String toString() 
	{
		return "Config "
		+"\ntipoCriterioParada: "+tipoCriterioParada
		+"\netaMax: "+deci.format(etaMax)
		+"\netaMin: "+deci.format(etaMin)
		+"\nnumIterUpdate: "+numIterUpdate
		+"\ndistMMin: "+distMMin
		+"\ndistMMax: "+distMMax
		+"\nlimiteAdj: "+limiteAdj
		+"\nepsilon: " + deci.format(epsilon)
		+"\nperturbacao: "+Arrays.toString(perturbacao)
		+"\nheuristicasAdicao: "+Arrays.toString(heuristicasAdicao)
		+"\nlimiteKnn: "+limiteKnn
		;  
	}

	public DecimalFormat getDeci() {
		return deci;
	}

	public void setDeci(DecimalFormat deci) {
		this.deci = deci;
	}

	public double getEtaMin() {
		return etaMin;
	}

	public void setEtaMin(double etaMin) {
		this.etaMin = etaMin;
	}

	public double getEtaMax() {
		return etaMax;
	}

	public void setEtaMax(double etaMax) {
		this.etaMax = etaMax;
	}

	public int getDistMMin() {
		return distMMin;
	}

	public void setDistMMin(int distMMin) {
		this.distMMin = distMMin;
	}

	public int getDistMMax() {
		return distMMax;
	}

	public void setDistMMax(int distMMax) {
		this.distMMax = distMMax;
	}

	public int getNumIterUpdate() {
		return numIterUpdate;
	}

	public void setNumIterUpdate(int numIterUpdate) {
		this.numIterUpdate = numIterUpdate;
	}

	public TipoPerturbacao[] getPerturbacao() {
		return perturbacao;
	}

	public void setPerturbacao(TipoPerturbacao[] perturbacao) {
		this.perturbacao = perturbacao;
	}

	public HeuristicaAdicao[] getHeuristicasAdicao() {
		return heuristicasAdicao;
	}

	public void setHeuristicasAdicao(HeuristicaAdicao[] heuristicasAdicao) {
		this.heuristicasAdicao = heuristicasAdicao;
	}

	public int getLimiteAdj() {
		return limiteAdj;
	}

	public void setLimiteAdj(int limiteAdj)
	{
		if(limiteKnn<limiteAdj)
			limiteKnn=limiteAdj;
		
		this.limiteAdj = limiteAdj;
	}

	public double getEpsilon() {
		return epsilon;
	}

	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}

	public int getLimiteKnn() {
		return limiteKnn;
	}

	public void setLimiteKnn(int limiteKnn) {
		this.limiteKnn = limiteKnn;
	}

	public TipoCriterioParada getTipoCriterioParada() {
		return tipoCriterioParada;
	}

	public void setTipoCriterioParada(TipoCriterioParada tipoCriterioParada) {
		this.tipoCriterioParada = tipoCriterioParada;
	}


}
