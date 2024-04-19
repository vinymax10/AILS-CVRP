package Data;

import java.io.*;

public class File 
{
	PrintWriter outputStream;
	
	public File(String name)
	{
		outputStream = null;
		try {
			outputStream=new PrintWriter(new FileWriter(name)); 
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public void write(String text) 
	{
		outputStream.println(text);
	}
	
	public void finish()
	{
		if (outputStream != null) 
			outputStream.close();
	}
}

