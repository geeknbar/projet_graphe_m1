package algorithme;

import java.io.IOException;
import java.util.ArrayList;

import context.Graphe;
import context.Sommet;
import fileRW.ReadFile;

/**
 * @author Mickael
 *
 */
public class AlgoGlouton 
{
	private ArrayList<Sommet> resultatPartiel;
	private ArrayList<Sommet> sommetAdjacents;
	private ArrayList<Sommet> sommetRestants;
	private ArrayList<Sommet> resultatFinal;
	
	
	private Sommet sommetInitial;
	private Graphe graphe;
	
	
	public AlgoGlouton()
	{
		ReadFile readFile = new ReadFile();
		try 
		{
			readFile.readLines("./src/doc/graphe_hao.txt");
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		graphe= readFile.getGraphe();
		sommetRestants=graphe.getAllSommet();
		resultatPartiel = new ArrayList<Sommet>();
		sommetAdjacents = new ArrayList<Sommet>();
		resultatFinal = new ArrayList<Sommet>();
	}
	
	
	
	
	
	public void cliqueMaxGlouton()
	{
		//Pour tous les sommets du graphes
		for (int i = 0; i < graphe.getAllSommet().size(); i++) 
		{
			sommetInitial = sommetRestants.get(i);
			resultatPartiel.add(sommetInitial);
		}
	}



	public void findCliqueGlouton()
	{
		//Pour tous les sommets du résultat partiel
		for (int k = 0; k < resultatPartiel.size(); k++) 
		{
			//Pour tous les sommets restants 
			for (int j = 0; j < sommetRestants.size(); j++) 
			{
				Sommet sommetTmp = sommetRestants.get(j);


			}	
		}
	}


	public ArrayList<Sommet> getResultatPartiel() 
	{
		return resultatPartiel;
	}


	public void setResultatPartiel(ArrayList<Sommet> resultatPartiel)
	{
		this.resultatPartiel = resultatPartiel;
	}


	public ArrayList<Sommet> getSommetAdjacents() 
	{
		return sommetAdjacents;
	}


	public void setSommetAdjacents(ArrayList<Sommet> sommetAdjacents) 
	{
		this.sommetAdjacents = sommetAdjacents;
	}


	public ArrayList<Sommet> getSommetRestants() 
	{
		return sommetRestants;
	}


	public void setSommetRestants(ArrayList<Sommet> sommetRestants) 
	{
		this.sommetRestants = sommetRestants;
	}


	public Graphe getGraphe()
	{
		return graphe;
	}


	public void setGraphe(Graphe graphe) 
	{
		this.graphe = graphe;
	}


	public ArrayList<Sommet> getResultatFinal() 
	{
		return resultatFinal;
	}


	public void setResultatFinal(ArrayList<Sommet> resultatFinal)
	{
		this.resultatFinal = resultatFinal;
	}
}
