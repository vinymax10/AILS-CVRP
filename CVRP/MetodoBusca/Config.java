package MetodoBusca;

import java.text.DecimalFormat;
import java.util.Arrays;

import Dados.TipoInstancia;
import Perturbacao.HeuristicaAdicao;
import Perturbacao.TipoPerturbacao;

public class Config implements Cloneable
{
	DecimalFormat deci=new DecimalFormat("0.000");
	double eta,etaMin,etaMax;
	double ebEta,ebDistM;
	double fluxoIdeal;
	double distLearning;
	int distElite;
	int distMMin,distMMax;	
	
	double variacao;
	int sizeElite;
	int numIterUpdate;
	TipoPerturbacao perturbacao[];
	double alfa;
	HeuristicaAdicao[]heuristicasAdicao;
	double ebDistDPM;
	//--------------------PR-------------------
	int limiteAdj;
	int numChilds;
	double fatorStocastic;
	int numIterAlta;
	int numIterBaixa;
	int numIterMareCiclo;
	double porcentFCFBL;
	double distTabu,distTriangulacao;
	int omegaFixo,omegaMin,omegaMax;
	int numPR;
	double epsilon;
	double teta;
	double sa;
	double relative;
	int iteStartFase2;
	int limiteKnn;
	double probRemoverRota;
	int periodocidadeSA;
	int sizePath;
	int sizeJanela;
	int nLimiteDist;
	TipoInstancia tipoInstancia;
	TipoCriterioParada tipoCriterioParada;
	int sizeCaminhoSwap;
	
	public Config() 
	{
//		----------------------------Main----------------------------
		this.nLimiteDist=16100;
		this.tipoInstancia=TipoInstancia.MatrizShort;
		this.tipoCriterioParada=TipoCriterioParada.TempoTotal;
		
		this.iteStartFase2=2000;
		
		this.distTriangulacao=4;
		this.distTabu=7;
		this.sizePath=3;
		this.numChilds=6;
		
		this.distMMin=15;
		this.distMMax=30;
		
		this.epsilon=0.01;
		this.omegaFixo=20;
		this.omegaMin=0;
		this.omegaMax=30;
		this.relative=0.03;
		
		this.probRemoverRota=0.2;
		this.sizeCaminhoSwap = 30;
		
		
		this.periodocidadeSA=200000;
		
		this.eta=0.2; 
		this.fluxoIdeal=0.3;
		this.etaMin=0.01;
		this.etaMax=1;
		this.distLearning=0.5;
		this.teta=0.005;
		this.sa=0.9999;
		
		
		this.limiteKnn=100;
		this.limiteAdj=40;
		this.sizeElite=60;
		this.numIterUpdate=30; 
		
		this.distElite=15;
//		----------------------------eta----------------------------
		 
		this.ebEta=1;
		this.ebDistM=0.5;
		
		this.fatorStocastic=10;
		
		this.numIterAlta=200;
		this.numIterBaixa=3000;
//		----------------------------diversidade----------------------------
		
		this.alfa=1;
		this.ebDistDPM=0.1;
		
//		----------------------------FAC----------------------------
		this.variacao=0;

		this.numIterMareCiclo=2000;
		
//		----------------------------BL----------------------------
		
//		----------------------------PR----------------------------
		
		this.numPR=1;
		
//		----------------------------Heuristica----------------------------
		
		this.perturbacao=new TipoPerturbacao[2];
		this.perturbacao[0]=TipoPerturbacao.Perturbacao27;
		this.perturbacao[1]=TipoPerturbacao.Perturbacao28;
		
		this.heuristicasAdicao=new HeuristicaAdicao[2];
		heuristicasAdicao[0]=HeuristicaAdicao.DISTGLOBAL;
		heuristicasAdicao[1]=HeuristicaAdicao.BESTGLOBAL;
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
	
	//---------gets and sets-----------
	
	public int getSizeElite() {
		return sizeElite;
	}

	public void setSizeElite(int sizeElite) {
		this.sizeElite = sizeElite;
	}

	@Override
	public String toString() 
	{
		return "Config "
		+"\n----------------------------Main----------------------------"
		+"\ntipoCriterioParada: "+tipoCriterioParada
		+"\nnLimiteDist: "+nLimiteDist
		+"\netaMax: "+deci.format(etaMax)
		+"\netaMin: "+deci.format(etaMin)
		+"\nnumIterUpdate: "+numIterUpdate
		+"\ndistMMin: "+distMMin
		+"\ndistMMax: "+distMMax
		+"\nlimiteAdj: "+limiteAdj
		+"\nsizeElite: " + sizeElite
		+"\nepsilon: " + deci.format(epsilon)
		+"\ntipoInstancia: "+tipoInstancia
		+"\nprobRemoverRota: "+probRemoverRota
		+"\nsizeCaminhoSwap: "+sizeCaminhoSwap
		
		+"\n----------------------------Heuristica----------------------------"
		+"\nperturbacao: "+Arrays.toString(perturbacao)
		+"\nheuristicasAdicao: "+Arrays.toString(heuristicasAdicao)

		+"\n----------------------------BL----------------------------"

		+"\n----------------------------FAC----------------------------"
		
		+"\n----------------------------Sem uso----------------------------"
		+"\n----------------------------PR----------------------------"
		+"\nnumPR: " + numPR
		+"\ndistElite: "+distElite
		
		+"\n----------------------------eta----------------------------"
		
		+"\nfluxoIdeal: " + deci.format(fluxoIdeal)
		+"\neta: " + deci.format(eta)
		+"\nebEta: "+deci.format(ebEta)
		+"\ndistLearning: "+deci.format(distLearning)
		+"\nnumChilds: "+deci.format(numChilds)
		+"\nfatorStocastic: "+deci.format(fatorStocastic)
		+"\nnumIterAlta: "+numIterAlta
		+"\nnumIterBaixa: "+numIterBaixa
		+"\ndistTabu: "+distTabu
		+"\nteta: "+teta
		+"\nsa: "+sa
		
		+"\n----------------------------diversidade----------------------------"
		+"\nnumIterMareCiclo: "+numIterMareCiclo
		+"\nebDistM: "+ebDistM
		+"\nalfa: "+deci.format(alfa)
		+"\nebDistDPM: "+deci.format(ebDistDPM)
		+"\nporcentFCFBL: "+deci.format(porcentFCFBL)
		+"\nomegaFixo: "+omegaFixo
		+"\nomegaMin: "+omegaMin
		+"\nomegaMax: "+omegaMax
		+"\nrelative: "+relative
		+"\niteStartFase2: "+iteStartFase2
		+"\nlimiteKnn: "+limiteKnn
		+"\nperiodocidadeSA: "+periodocidadeSA
		+"\nvariacao: " + deci.format(variacao)
		+"\nsizePath: "+sizePath
		+"\nsizeJanela: "+sizeJanela
		+"\ndistTriangulacao: " + deci.format(distTriangulacao)
		;  
	}

	public int getSizePath() {
		return sizePath;
	}

	public void setSizePath(int sizePath) {
		this.sizePath = sizePath;
	}

	public DecimalFormat getDeci() {
		return deci;
	}

	public void setDeci(DecimalFormat deci) {
		this.deci = deci;
	}

	public double getEta() {
		return eta;
	}

	public void setEta(double eta) {
		this.eta = eta;
	}


	public double getFluxoIdeal() {
		return fluxoIdeal;
	}

	public void setFluxoIdeal(double fluxoIdeal) {
		this.fluxoIdeal = fluxoIdeal;
	}

	public double getVariacao() {
		return variacao;
	}

	public void setVariacao(double variacao) {
		this.variacao = variacao;
	}

	public TipoPerturbacao[] getPerturbacao() {
		return perturbacao;
	}

	public void setPerturbacao(TipoPerturbacao[] perturbacao) {
		this.perturbacao = perturbacao;
	}

	public int getNumIterUpdate() {
		return numIterUpdate;
	}

	public void setNumIterUpdate(int numIterUpdate) {
		this.numIterUpdate = numIterUpdate;
	}

	public double getEbEta() {
		return ebEta;
	}

	public void setEbEta(double ebEta) {
		this.ebEta = ebEta;
	}

	public int getDistElite() {
		return distElite;
	}

	public void setDistElite(int distElite) {
		this.distElite = distElite;
	}

	public HeuristicaAdicao[] getHeuristicasAdicao() {
		return heuristicasAdicao;
	}

	public void setHeuristicasAdicao(HeuristicaAdicao[] heuristicas) {
		this.heuristicasAdicao = heuristicas;
	}

	public double getAlfa() {
		return alfa;
	}

	public void setAlfa(double alfa) {
		this.alfa = alfa;
	}

	public double getEbDistDPM() {
		return ebDistDPM;
	}

	public void setEbDistDPM(double ebDistDPM) {
		this.ebDistDPM = ebDistDPM;
	}

	public double getEtaMin() {
		return etaMin;
	}

	public void setEtaMin(double etaMin) {
		this.etaMin = etaMin;
	}

	public double getDistLearning() {
		return distLearning;
	}

	public void setDistLearning(double distLearning) {
		this.distLearning = distLearning;
	}

	public double getEbDistM() {
		return ebDistM;
	}

	public void setEbDistM(double ebDistM) {
		this.ebDistM = ebDistM;
	}

	public int getLimiteAdj() {
		return limiteAdj;
	}

	public void setLimiteAdj(int limiteAdj) {
		this.limiteAdj = limiteAdj;
	}

	public int getNumChilds() {
		return numChilds;
	}

	public void setNumChilds(int numChilds) {
		this.numChilds = numChilds;
	}

	public double getFatorStocastic() {
		return fatorStocastic;
	}

	public void setFatorStocastic(double fatorStocastic) {
		this.fatorStocastic = fatorStocastic;
	}

	public int getNumIterAlta() {
		return numIterAlta;
	}

	public void setNumIterAlta(int numIterAlta) {
		this.numIterAlta = numIterAlta;
	}

	public int getNumIterBaixa() {
		return numIterBaixa;
	}

	public void setNumIterBaixa(int numIterBaixa) {
		this.numIterBaixa = numIterBaixa;
	}

	public int getNumIterMareCiclo() {
		return numIterMareCiclo;
	}

	public void setNumIterMareCiclo(int numIterMareCiclo) {
		this.numIterMareCiclo = numIterMareCiclo;
	}

	public double getPorcentFCFBL() {
		return porcentFCFBL;
	}

	public void setPorcentFCFBL(double porcentFCFBL) {
		this.porcentFCFBL = porcentFCFBL;
	}

	public double getDistTabu() {
		return distTabu;
	}

	public void setDistTabu(double distTabu) {
		this.distTabu = distTabu;
	}

	public int getOmegaFixo() {
		return omegaFixo;
	}

	public void setOmegaFixo(int omegaFixo) {
		this.omegaFixo = omegaFixo;
	}

	public int getNumPR() {
		return numPR;
	}

	public void setNumPR(int numPR) {
		this.numPR = numPR;
	}

	public double getEpsilon() {
		return epsilon;
	}

	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}

	public double getDistTriangulacao() {
		return distTriangulacao;
	}

	public void setDistTriangulacao(double distTriangulacao) {
		this.distTriangulacao = distTriangulacao;
	}

	public double getTeta() {
		return teta;
	}

	public void setTeta(double teta) {
		this.teta = teta;
	}

	public double getSa() {
		return sa;
	}

	public void setSa(double sa) {
		this.sa = sa;
	}

	public int getOmegaMin() {
		return omegaMin;
	}

	public void setOmegaMin(int omegaMin) {
		this.omegaMin = omegaMin;
	}

	public int getOmegaMax() {
		return omegaMax;
	}

	public void setOmegaMax(int omegaMax) {
		this.omegaMax = omegaMax;
	}

	public double getRelative() {
		return relative;
	}

	public void setRelative(double relative) {
		this.relative = relative;
	}

	public int getIteStartFase2() {
		return iteStartFase2;
	}

	public void setIteStartFase2(int iteStartFase2) {
		this.iteStartFase2 = iteStartFase2;
	}

	public int getLimiteKnn() {
		return limiteKnn;
	}

	public void setLimiteKnn(int limiteKnn) {
		this.limiteKnn = limiteKnn;
	}

	public double getEtaMax() {
		return etaMax;
	}

	public void setEtaMax(double etaMax) {
		this.etaMax = etaMax;
	}

	public int getPeriodocidadeSA() {
		return periodocidadeSA;
	}

	public void setPeriodocidadeSA(int periodocidadeSA) {
		this.periodocidadeSA = periodocidadeSA;
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

	public int getSizeJanela() {
		return sizeJanela;
	}

	public void setSizeJanela(int sizeJanela) {
		this.sizeJanela = sizeJanela;
	}

	public int getnLimiteDist() {
		return nLimiteDist;
	}

	public void setnLimiteDist(int nLimiteDist) {
		this.nLimiteDist = nLimiteDist;
	}

	public TipoInstancia getTipoInstancia() {
		return tipoInstancia;
	}

	public void setTipoInstancia(TipoInstancia tipoInstancia) {
		this.tipoInstancia = tipoInstancia;
	}

	public TipoCriterioParada getTipoCriterioParada() {
		return tipoCriterioParada;
	}

	public void setTipoCriterioParada(TipoCriterioParada tipoCriterioParada) {
		this.tipoCriterioParada = tipoCriterioParada;
	}

	public double getProbRemoverRota() {
		return probRemoverRota;
	}

	public void setProbRemoverRota(double probRemoverRota) {
		this.probRemoverRota = probRemoverRota;
	}

	public int getSizeCaminhoSwap() {
		return sizeCaminhoSwap;
	}

	public void setSizeCaminhoSwap(int sizeCaminhoSwap) {
		this.sizeCaminhoSwap = sizeCaminhoSwap;
	}

}
