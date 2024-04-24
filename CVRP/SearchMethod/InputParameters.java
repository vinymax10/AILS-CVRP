package SearchMethod;

import java.io.File;
import java.util.Arrays;

public class InputParameters 
{
	
	private String file="";
	private boolean rounded=true;
	private double limit=Double.MAX_VALUE;
	private double best=0;
	private Config config =new Config();
	
	public void readingInput(String[] args)
	{
		try 
		{
			for (int i = 0; i < args.length-1; i+=2) 
			{
				switch(args[i])
				{
					case "-file": file=getAddress(args[i+1]);break;
					case "-rounded": rounded=getRound(args[i+1]);break;
					case "-limit": limit=getLimit(args[i+1]);break;
					case "-best": best=getBest(args[i+1]);break;
					case "-stoppingCriterion": config.setStoppingCriterionType(getStoppingCriterion(args[i+1]));break;
					case "-dMax": config.setDMax(getDMax(args[i+1]));break;
					case "-dMin": config.setDMin(getDMin(args[i+1]));break;
					case "-gamma": config.setGamma(getGamma(args[i+1]));break;
					case "-varphi": config.setVarphi(getVarphi(args[i+1]));break;
					
				}
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("File: "+file);
		System.out.println("Rounded: "+rounded);
		System.out.println("limit: "+limit);
		System.out.println("Best: "+best);
		System.out.println("LimitTime: "+limit);
		System.out.println(config);
	}
	
	
	public String getAddress(String text)
	{
		try 
		{
			File file=new File(text);
			if(file.exists()&&!file.isDirectory())
				return text;
			else
				System.err.println("The -file parameter must contain the address of a valid file.");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return "";	
	}
	
	public boolean getRound(String text)
	{
		rounded=true;
		try 
		{
			if(text.equals("false")||text.equals("true"))
				rounded=Boolean.valueOf(text);
			else
				System.err.println("The -rounded parameter must have the values false or true.");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return rounded;
	}
	
	public double getLimit(String text)
	{
		try 
		{
			limit=Double.valueOf(text);
		} 
		catch (java.lang.NumberFormatException e) {
			System.err.println("The -limit parameter must contain a valid real value.");
		}
		return limit;
	}
	
	public double getBest(String text)
	{
		try 
		{
			best=Double.valueOf(text);
		} 
		catch (java.lang.NumberFormatException e) {
			System.err.println("The -best parameter must contain a valid real value.");
		}
		return best;
	}
	
	public int getVarphi(String text)
	{
		int varphi=40;
		try 
		{
			varphi=Integer.valueOf(text);
		} 
		catch (java.lang.NumberFormatException e) {
			System.err.println("The -varphi parameter must contain a valid integer value.");
		}
		return varphi;
	}
	
	public int getGamma(String text)
	{
		int gamma=30;
		try 
		{
			gamma=Integer.valueOf(text);
		} 
		catch (java.lang.NumberFormatException e) {
			System.err.println("The -gamma parameter must contain a valid integer value.");
		}
		return gamma;
	}
	
	public int getDMax(String text)
	{
		int dMax=30;
		try 
		{
			dMax=Integer.valueOf(text);
		} 
		catch (java.lang.NumberFormatException e) {
			System.err.println("The -dMax parameter must contain a valid integer value.");
		}
		return dMax;
	}
	
	public int getDMin(String text)
	{
		int dMin=15;
		try 
		{
			dMin=Integer.valueOf(text);
		} 
		catch (java.lang.NumberFormatException e) {
			System.err.println("The -dMin parameter must contain a valid integer value.");
		}
		return dMin;
	}
	
	public StoppingCriterionType getStoppingCriterion(String texo)
	{
		StoppingCriterionType stoppingCriterion=StoppingCriterionType.Time;
		try 
		{
			stoppingCriterion=StoppingCriterionType.valueOf(texo);
		} 
		catch (java.lang.IllegalArgumentException e) 
		{
			System.err.println("The -stoppingCriterion parameter must have the values "+Arrays.toString(StoppingCriterionType.values())+".");
		}
		return stoppingCriterion;
	}

	public String getFile() {
		return file;
	}

	public boolean isRounded() {
		return rounded;
	}

	public double getTimeLimit() {
		return limit;
	}

	public double getBest() {
		return best;
	}


	public Config getConfig() {
		return config;
	}
	
}
