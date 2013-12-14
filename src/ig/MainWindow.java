package ig;

import java.io.IOException;
import java.util.TreeMap;

import context.IndiceOfCalcul;
import algorithme.AlgoGlouton;
import algorithme.AlgoGloutonThread;
import fileRW.ReadFile;

/**
 * @author Mickael
 *
 */
public class MainWindow
{
	private static IndiceOfCalcul tabIndices[];
	private static AlgoGloutonThread agt;
	
	private final static int NB_PROC_MINIM_TO_THREAD=4;
	public static TreeMap<Integer,AlgoGlouton> allResult;
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		int nbProc = Runtime.getRuntime().availableProcessors();
		int sizeGraphe = 0;
		ReadFile readFile = new ReadFile();
		allResult = new TreeMap<Integer,AlgoGlouton>();
		try 
		{
			
			//readFile.readLines("./src/doc/simple4.txt");
	//		sizeGraphe = readFile.readLines("./src/doc/graphe_125.txt");
//			sizeGraphe = readFile.readLines("./src/doc/graphe_250.txt");
			sizeGraphe = readFile.readLines("./src/doc/graphe_500.txt");
//			sizeGraphe = readFile.readLines("./src/doc/graphe_500_5.txt");
//			readFile.readLines("./src/doc/brock200_2.txt");
//			readFile.readLines("./src/doc/DSJC500_5.txt");
//			readFile.readLines("./src/doc/gen400_p0.9_75.txt");//47/75
//			readFile.readLines("./src/doc/hamming8-4.txt");//resultat parfait algo2
//			readFile.readLines("./src/doc/keller4.txt");//resultat parfait algo2
//			readFile.readLines("./src/doc/MANN_a27.txt");//125/126 algo2
			//readFile.readLines("./src/doc/p_hat300-1.tkt");//resultat parfait algo2
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		System.err.println(readFile.getGraphe().toString());

		long startTime = System.currentTimeMillis();
		
		if(nbProc>= NB_PROC_MINIM_TO_THREAD)
		{
			tabIndices = new IndiceOfCalcul[nbProc];
			int sizeCut = sizeGraphe/nbProc;
			tabIndices[0]=new IndiceOfCalcul(0, sizeCut);
			for (int i = 1; i < nbProc; i++)
			{
				tabIndices[i]=new IndiceOfCalcul(tabIndices[i-1].getFin()+1, tabIndices[i-1].getFin()+sizeCut);
			}
			
			for (int i = 0; i < tabIndices.length; i++)
			{
				agt = new AlgoGloutonThread(readFile, tabIndices[i]);
				agt.start();
			}
		}
		else
		{
			tabIndices[0]=new IndiceOfCalcul(0, sizeGraphe);
		}

		
		while(!Thread.State.TERMINATED.equals(agt.getState()));
		
		allResult.lastEntry().getValue().display();
		
		long endTime = System.currentTimeMillis();
		System.err.println("Algo execute en "+(endTime-startTime)+" ms");
	}

}
