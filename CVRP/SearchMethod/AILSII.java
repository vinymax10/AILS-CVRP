package SearchMethod;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Random;

import Auxiliary.Distance;
import Data.Instance;
import DiversityControl.DistAdjustment;
import DiversityControl.OmegaAdjustment;
import DiversityControl.AcceptanceCriterion;
import DiversityControl.IdealDist;
import Improvement.LocalSearch;
import Improvement.IntraLocalSearch;
import Improvement.FeasibilityPhase;
import Perturbation.InsertionHeuristic;
import Perturbation.Perturbation;
import Solution.Solution;

public class AILSII 
{
	//----------Problema------------
	Solution solution,referenceSolution,melhorSolucao;
	
	Instance instance;
	Distance distEntreSolucoes;
	double melhorF=Double.MAX_VALUE;
	double executionMaximumLimit;
	double otimo;
	
	//----------caculoLimiar------------
	int numIterUpdate;

	//----------Metricas------------
	int iterator,iteratorMF;
	long first,ini;
	double tempoMF,tempoTotal,tempo;
	
	Random rand=new Random();
	
	HashMap<String,OmegaAdjustment>omegaSetup=new HashMap<String,OmegaAdjustment>();

	double distanciaBL;
	
	Perturbation[] perturbadores;
	Perturbation perturbacaoEscolhida;
	
	FeasibilityPhase factibilizador;
	ConstrutorSolucao construtorSolucao;
	
	LocalSearch localSearch;

	InsertionHeuristic insertionHeuristic;
	IntraLocalSearch intraLocalSearch;
	AcceptanceCriterion acceptanceCriterion;
//	----------Mare------------
	DistAdjustment distAdjustment;
//	---------Print----------
	boolean print=true;
	IdealDist idealDist;
	
	double epsilon;
	DecimalFormat deci=new DecimalFormat("0.0000");
	StoppingCriterionType stoppingCriterionType;
	
	public AILSII(Instance instance,InputParameters leitor)
	{ 
		this.instance=instance;
		Config config=leitor.getConfig();
		this.otimo=leitor.getBest();
		this.executionMaximumLimit=leitor.getTimeLimit();
		
		this.epsilon=config.getEpsilon();
		this.stoppingCriterionType=config.getStoppingCriterionType();
		this.idealDist=new IdealDist();
		this.solution =new Solution(instance,config);
		this.referenceSolution =new Solution(instance,config);
		this.melhorSolucao =new Solution(instance,config);
		this.numIterUpdate=config.getGamma();
		
		this.distEntreSolucoes=new Distance();
		
		this.perturbadores=new Perturbation[config.getPerturbacao().length];
		
		this.distAdjustment=new DistAdjustment( idealDist, config, executionMaximumLimit);
		
		this.intraLocalSearch=new IntraLocalSearch(instance,config);
		
		this.localSearch=new LocalSearch(instance,config,intraLocalSearch);
		
		this.factibilizador=new FeasibilityPhase(instance,config,intraLocalSearch);
		
		this.construtorSolucao=new ConstrutorSolucao(instance,config);
		
		OmegaAdjustment novo;
		for (int i = 0; i < config.getPerturbacao().length; i++) 
		{
			novo=new OmegaAdjustment(config.getPerturbacao()[i], config,instance.getSize(),idealDist);
			omegaSetup.put(config.getPerturbacao()[i]+"", novo);
		}
		
		this.acceptanceCriterion=new AcceptanceCriterion(instance,config,executionMaximumLimit);

		try 
		{
			for (int i = 0; i < perturbadores.length; i++) 
			{
				this.perturbadores[i]=(Perturbation) Class.forName("Perturbation."+config.getPerturbacao()[i]).
				getConstructor(Instance.class,Config.class,HashMap.class,IntraLocalSearch.class).
				newInstance(instance,config,omegaSetup,intraLocalSearch);
			}
			
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException
				| ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	public void search()
	{
		iterator=0;
		first=System.currentTimeMillis();
		referenceSolution.numRoutes=instance.getMinNumberRoutes();
		construtorSolucao.construir(referenceSolution);

		factibilizador.makeFeasible(referenceSolution);
		localSearch.localSearch(referenceSolution,true);
		melhorSolucao.clone(referenceSolution);
		while(!criterioParada())
		{
			iterator++;

			solution.clone(referenceSolution);
			
			perturbacaoEscolhida=perturbadores[rand.nextInt(perturbadores.length)];
			perturbacaoEscolhida.applyPerturbation(solution);
			factibilizador.makeFeasible(solution);
			localSearch.localSearch(solution,true);
			distanciaBL=distEntreSolucoes.pairwiseSolutionDistance(solution,referenceSolution);
			
			analisaSolucao();
			distAdjustment.distAdjustment();
			
			perturbacaoEscolhida.getChosenOmega().setDistance(distanciaBL);//update
			
			if(acceptanceCriterion.aceitaSolucao(solution))
				referenceSolution.clone(solution);
		}
		
		tempoTotal=(double)(System.currentTimeMillis()-first)/1000;
	}
	
	public void analisaSolucao()
	{
		if((solution.f-melhorF)<-epsilon)
		{		
			melhorF=solution.f;
			
			melhorSolucao.clone(solution);
			iteratorMF=iterator;
			tempoMF=(double)(System.currentTimeMillis()-first)/1000;
				
			if(print)
			{
				System.out.println("solution quality: "+melhorF
				+" gap: "+deci.format(getGap())+"%"
				+" K: "+solution.numRoutes
				+" iteration: "+iterator
				+" eta: "+deci.format(acceptanceCriterion.getEta())
				+" omega: "+deci.format(perturbacaoEscolhida.omega)
				+" time: "+tempoMF
				);
			}
		}
	}
	
	private boolean criterioParada()
	{
		switch(stoppingCriterionType)
		{
			case Iteration: 	if(melhorF<=otimo||executionMaximumLimit<=iterator)
									return true;
								break;
							
			case Time: 	if(melhorF<=otimo||executionMaximumLimit<(System.currentTimeMillis()-first)/1000)
							return true;
						break;
		}
		return false;
	}
	
	public static void main(String[] args) 
	{
		InputParameters reader=new InputParameters();
		reader.readingInput(args);
		
		Instance instance=new Instance(reader);
		
		AILSII ailsII=new AILSII(instance,reader);
		
		ailsII.search();
	}
	
	public Solution getMelhorSolucao() {
		return melhorSolucao;
	}

	public double getMelhorF() {
		return melhorF;
	}

	public double getGap()
	{
		return 100*((melhorF-otimo)/otimo);
	}
	
	public boolean isPrint() {
		return print;
	}

	public void setPrint(boolean print) {
		this.print = print;
	}

	public Solution getSolution() {
		return solution;
	}

	public int getIterator() {
		return iterator;
	}

	public String printOmegas()
	{
		String str="";
		for (int i = 0; i < perturbadores.length; i++) 
		{
			str+="\n"+omegaSetup.get(this.perturbadores[i].perturbationType+""+referenceSolution.numRoutes);
		}
		return str;
	}
	
	public Perturbation[] getPerturbadores() {
		return perturbadores;
	}
	
	public double getTempoTotal() {
		return tempoTotal;
	}
	
	public double getTemoPorIteracao() 
	{
		return tempoTotal/iterator;
	}

	public double getTempoMF() {
		return tempoMF;
	}

	public int getIteratorMF() {
		return iteratorMF;
	}
	
	public double getConvergenciaIteracao()
	{
		return (double)iteratorMF/iterator;
	}
	
	public double convergenciaTempo()
	{
		return (double)tempoMF/tempoTotal;
	}
	
}
