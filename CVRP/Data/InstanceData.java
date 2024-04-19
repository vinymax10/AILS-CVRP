package Data;

public class InstanceData
{
	public String name;
	public Best bestSolution;
	public Best bestSolutionSave;
	public boolean best;
	public boolean alreadyHaveSolution;
	public boolean rounded;

	public InstanceData(String name, double bestSolution, boolean rounded, boolean best, int bestSolutionSave, boolean alreadyHaveSolution)
	{
		super();
		this.name = name;
		this.bestSolution = new Best(bestSolution);
		this.rounded = rounded;
		this.best = best;
		this.alreadyHaveSolution = alreadyHaveSolution;
		this.bestSolutionSave = new Best(bestSolutionSave);
	}

	public InstanceData(String name, double bestSolution, boolean rounded, boolean best, boolean alreadyHaveSolution)
	{
		super();
		this.name = name;
		this.bestSolution = new Best(bestSolution);
		this.bestSolutionSave = new Best(bestSolution);
		this.rounded = rounded;
		this.best = best;
		this.alreadyHaveSolution = alreadyHaveSolution;
	}

	@Override
	public String toString()
	{
		return "name: " + name + "\tbestSolution: " + bestSolution.getOtimo() + "\totimmo: " + best + "\tjaTemosSolucaoSalva: " + alreadyHaveSolution;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Best getBestSolution()
	{
		return bestSolution;
	}

	public void setBestSolution(Best bestSolution)
	{
		this.bestSolution = bestSolution;
	}

	public boolean isBest()
	{
		return best;
	}

	public void setBest(boolean best)
	{
		this.best = best;
	}

	public boolean isAlreadyHaveSolution()
	{
		return alreadyHaveSolution;
	}

	public void setAlreadyHaveSolution(boolean alreadyHaveSolution)
	{
		this.alreadyHaveSolution = alreadyHaveSolution;
	}

	public Best getBestSolutionSave()
	{
		return bestSolutionSave;
	}

	public void setBestSolutionSalva(Best bestSolutionSave)
	{
		this.bestSolutionSave = bestSolutionSave;
	}

}
