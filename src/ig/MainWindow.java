package ig;

import java.io.IOException;

import algorithme.AlgoGlouton;
import fileRW.ReadFile;

/**
 * @author Mickael
 *
 */
public class MainWindow
{

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		ReadFile readFile = new ReadFile();
		try 
		{
			
//			readFile.readLines("./src/doc/simple4.txt");
//			readFile.readLines("./src/doc/graphe_125.txt");
//			readFile.readLines("./src/doc/graphe_250.txt");
//			readFile.readLines("./src/doc/graphe_500.txt");
			readFile.readLines("./src/doc/graphe_500_5.txt");
			//readFile.readLines("./src/doc/graphe_2000.txt");
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		System.err.println(readFile.getGraphe().toString());

		AlgoGlouton a = new AlgoGlouton(readFile);
		a.cliqueMaximumGlouton();
		
	}

}
