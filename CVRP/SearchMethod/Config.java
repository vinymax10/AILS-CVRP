package SearchMethod;

import java.text.DecimalFormat;
import java.util.Arrays;

import Perturbation.HeuristicaAdicao;
import Perturbation.TipoPerturbacao;

public class Config implements Cloneable
{
	DecimalFormat deci=new DecimalFormat("0.000");
	double etaMin,etaMax;
	int dMin,dMax;	
	
	int gamma;
	TipoPerturbacao perturbacao[];
	HeuristicaAdicao[]heuristicasAdicao;
	//--------------------PR-------------------
	int varphi;
	double epsilon;
	int limiteKnn;
	TipoCriterioParada tipoCriterioParada;
	
	public Config() 
	{
//		----------------------------Main----------------------------
		this.tipoCriterioParada=TipoCriterioParada.Time;
		this.dMin=15;
		this.dMax=30;
		this.gamma=30; 
		this.limiteKnn=100;
		this.varphi=40;
		
		
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
		+"\ngamma: "+gamma
		+"\ndMin: "+dMin
		+"\ndMax: "+dMax
		+"\nvarphi: "+varphi
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

	public int getDMin() {
		return dMin;
	}

	public void setDMin(int dMin) {
		this.dMin = dMin;
	}

	public int getDMax() {
		return dMax;
	}

	public void setDMax(int dMax) {
		this.dMax = dMax;
	}

	public int getGamma() {
		return gamma;
	}

	public void setGamma(int gamma) {
		this.gamma = gamma;
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

	public int getVarphi() {
		return varphi;
	}

	public void setVarphi(int varphi)
	{
		if(limiteKnn<varphi)
			limiteKnn=varphi;
		
		this.varphi = varphi;
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
