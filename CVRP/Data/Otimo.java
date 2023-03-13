package Data;

public class Otimo 
{
	private double otimo;

	public Otimo(double otimo)
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
