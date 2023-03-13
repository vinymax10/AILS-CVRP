package Data;

public class DadosInstancia
{
	public String nome;
	public Otimo bestSolution;
	public Otimo bestSolutionSalva;
	public boolean otimo;
	public boolean jaTemosSolucaoSalva;
	public boolean rounded;
	
	public DadosInstancia(String nome, double bestSolution,boolean rounded, boolean otimo,int bestSolutionSalva,boolean jaTemosSolucaoSalva) 
	{
		super();
		this.nome = nome;
		this.bestSolution = new Otimo(bestSolution);
		this.rounded=rounded;
		this.otimo = otimo;
		this.jaTemosSolucaoSalva=jaTemosSolucaoSalva;
		this.bestSolutionSalva=new Otimo(bestSolutionSalva);
	}
	
	public DadosInstancia(String nome, double bestSolution,boolean rounded, boolean otimo,boolean jaTemosSolucaoSalva) 
	{
		super();
		this.nome = nome;
		this.bestSolution = new Otimo(bestSolution);
		this.bestSolutionSalva=new Otimo(bestSolution);
		this.rounded=rounded;
		this.otimo = otimo;
		this.jaTemosSolucaoSalva=jaTemosSolucaoSalva;
	}
	
	@Override
	public String toString() {
		return "nome: " + nome + "\tbestSolution: " + bestSolution.getOtimo() + "\totimmo: " + otimo+"\tjaTemosSolucaoSalva: "+jaTemosSolucaoSalva;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Otimo getBestSolution() {
		return bestSolution;
	}
	public void setBestSolution(Otimo bestSolution) {
		this.bestSolution = bestSolution;
	}
	public boolean isOtimmo() {
		return otimo;
	}
	public void setOtimmo(boolean otimmo) {
		this.otimo = otimmo;
	}
	public boolean isOtimo() {
		return otimo;
	}
	public void setOtimo(boolean otimo) {
		this.otimo = otimo;
	}
	public boolean isJaTemosSolucaoSalva() {
		return jaTemosSolucaoSalva;
	}
	public void setJaTemosSolucaoSalva(boolean jaTemosSolucaoSalva) {
		this.jaTemosSolucaoSalva = jaTemosSolucaoSalva;
	}

	public Otimo getBestSolutionSalva() {
		return bestSolutionSalva;
	}

	public void setBestSolutionSalva(Otimo bestSolutionSalva) {
		this.bestSolutionSalva = bestSolutionSalva;
	}
	
	
	
	
}
