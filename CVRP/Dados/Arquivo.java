package Dados;

import java.io.*;

public class Arquivo 
{
	PrintWriter outputStream;
	
	public Arquivo(String nome)
	{
		outputStream = null;
		try {
			outputStream=new PrintWriter(new FileWriter(nome)); 
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public void escrever(String text) 
	{
		outputStream.println(text);
	}
	
	public void finalizar()
	{
		if (outputStream != null) 
			outputStream.close();
	}
}

