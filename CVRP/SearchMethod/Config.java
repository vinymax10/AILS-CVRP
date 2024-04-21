package SearchMethod;

import java.text.DecimalFormat;
import java.util.Arrays;

import Perturbation.HeuristicaAdicao;
import Perturbation.PerturbationType;

public class Config implements Cloneable
{
	DecimalFormat deci=new DecimalFormat("0.000");
	double etaMin,etaMax;
	int dMin,dMax;	
	
	int gamma;
	PerturbationType perturbacao[];
	HeuristicaAdicao[]heuristicasAdicao;
	//--------------------PR-------------------
	int varphi;
	double epsilon;
	int knnLimit;
	StoppingCriterionType stoppingCriterionType;
	
	public Config() 
	{
//		----------------------------Main----------------------------
		this.stoppingCriterionType=StoppingCriterionType.Time;
		this.dMin=15;
		this.dMax=30;
		this.gamma=30; 
		this.knnLimit=100;
		this.varphi=40;
		
		
		this.epsilon=0.01;
		this.etaMin=0.01;
		this.etaMax=1;
		
		this.perturbacao=new PerturbationType[2];
		this.perturbacao[0]=PerturbationType.Sequential;
		this.perturbacao[1]=PerturbationType.Concentric;
		
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
		+"\nstoppingCriterionType: "+stoppingCriterionType
		+"\netaMax: "+deci.format(etaMax)
		+"\netaMin: "+deci.format(etaMin)
		+"\ngamma: "+gamma
		+"\ndMin: "+dMin
		+"\ndMax: "+dMax
		+"\nvarphi: "+varphi
		+"\nepsilon: " + deci.format(epsilon)
		+"\nperturbacao: "+Arrays.toString(perturbacao)
		+"\nheuristicasAdicao: "+Arrays.toString(heuristicasAdicao)
		+"\nlimiteKnn: "+knnLimit
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

	public PerturbationType[] getPerturbacao() {
		return perturbacao;
	}

	public void setPerturbacao(PerturbationType[] perturbacao) {
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
		if(knnLimit<varphi)
			knnLimit=varphi;
		
		this.varphi = varphi;
	}

	public double getEpsilon() {
		return epsilon;
	}

	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}

	public int getKnnLimit() {
		return knnLimit;
	}

	public void setKnnLimit(int knnLimit) {
		this.knnLimit = knnLimit;
	}

	public StoppingCriterionType getStoppingCriterionType() {
		return stoppingCriterionType;
	}

	public void setStoppingCriterionType(StoppingCriterionType stoppingCriterionType) {
		this.stoppingCriterionType = stoppingCriterionType;
	}


}
