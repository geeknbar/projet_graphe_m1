package algorithme;

import ig.MainWindow;
import context.IndiceOfCalcul;
import fileRW.ReadFile;

public class AlgoGloutonThread extends Thread 
{
	private ReadFile file;
	IndiceOfCalcul indiceToCalcul;
	
	public AlgoGloutonThread(ReadFile f, IndiceOfCalcul i) 
	{
		this.file=f;
		indiceToCalcul = i;
	}

	public void run() 
	{
		AlgoGlouton a = new AlgoGlouton(file, indiceToCalcul);
		a.cliqueMaximumGlouton();
		MainWindow.allResult.put(a.getResultatFinal().size(),a);
	}
}
