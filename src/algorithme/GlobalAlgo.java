package algorithme;

import java.io.IOException;
import java.util.TreeMap;

import context.IndiceOfCalcul;
import fileRW.ReadFile;

public class GlobalAlgo 
{
	private static IndiceOfCalcul tabIndices[];
	private static AlgoGloutonThread agt;
	
	private final static int NB_PROC_MINIM_TO_THREAD=4;
	public static TreeMap<Integer,AlgoGlouton> allResult;
	
	
//	public static String pathFile="./src/doc/simple4.txt";
//	public static String pathFile="./src/doc/graphe_125.txt";
	public static String pathFile="./src/doc/graphe_250.txt";
//	public static String pathFile="./src/doc/graphe_500.txt";
//	public static String pathFile="./src/doc/graphe_500_5.txt";
//	public static String pathFile="./src/doc/brock200_2.txt";
//	public static String pathFile="./src/doc/DSJC500_5.txt";
//	public static String pathFile="./src/doc/gen400_p0.9_75.txt";//47/75
//	public static String pathFile="./src/doc/hamming8-4.txt";//resultat parfait algo2
//	public static String pathFile="./src/doc/keller4.txt";//resultat parfait algo2
//	public static String pathFile="./src/doc/MANN_a27.txt";//125/126 algo2
//	public static String pathFile="./src/doc/p_hat300-1.tkt";//resultat parfait algo2
//	
	/**
	 * @param args
	 */
	public static void launchAlgo()
	{
		int nbProc = Runtime.getRuntime().availableProcessors();
		int sizeGraphe = 0;
		ReadFile readFile = new ReadFile();
		allResult = new TreeMap<Integer,AlgoGlouton>();
		try 
		{
			sizeGraphe = readFile.readLines(pathFile);

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
	
	
	public GlobalAlgo() 
	{
	}
}
