package Data;

public class Best 
{
	private double otimo;

	public Best(double otimo)
	{
		this.otimo = otimo;
	}

	public double getOtimo() {
		return otimo;
	}

	public synchronized void setOtimo(double d) {
		this.otimo = d;
	}

	
}
