package algorithme;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import context.Graphe;
import context.Sommet;
import fileRW.ReadFile;

/**
 * @author Mickael
 *
 */
public class AlgoGlouton 
{
	int toto=0;
	private ArrayList<Sommet> resultatPartiel;
	private ArrayList<Sommet> sommetAdjacents;
	private ArrayList<Sommet> sommetRestants;
	private ArrayList<Sommet> sommetAParcourir;
	private ArrayList<Sommet> resultatFinal;
	private HashMap<Integer, ArrayList<Sommet>> mapSommetTemp;
	
	private Graphe graphe;
	
	
	public AlgoGlouton(ReadFile readFile)
	{
		graphe= readFile.getGraphe();
		sommetAParcourir=graphe.getAllSommet();
		sommetAdjacents = new ArrayList<Sommet>();
		resultatFinal = new ArrayList<Sommet>();
	}
	
	
	
	
	
	public void cliqueMaximumGlouton()
	{
		//Pour tous les sommets du graphes
		for (int i = 0; i < graphe.getAllSommet().size(); i++) 
		{
			//on prend tous les sommet dans la liste des résultats restants
			sommetRestants=new ArrayList<Sommet>(graphe.getAllSommet());

			//on réinitialise la liste des résultat partiel a chaque fois 
			//que l'on veut trouver une clic maximal
			resultatPartiel = new ArrayList<Sommet>();
			
			//on initialise la liste des résultats partiel avec le sommet pris au hasard*
			//*hasard : nous les prenons du premier au dernier. 
			resultatPartiel.add(new Sommet(sommetRestants.get(i).getValue()));
			
			//on supprime le sommet inital courant.
			//sommetRestants.remove(i);
			
			//initialisation de la map temporaire
			mapSommetTemp = new HashMap<Integer, ArrayList<Sommet>>();
			
			//lancement du traitement de la clic maximum pour chacun des sommets
			findCliqueGlouton();

			
			//si la clic est plus grande que la précédente, on la prend comme resultat final
			if(resultatPartiel.size() > resultatFinal.size())
				resultatFinal= new ArrayList<Sommet>(resultatPartiel);
		}
		
		System.out.println(resultatFinal.toString());
	}


	// pour trouver une clic, nous choisissons de prendre le premier
	public void findCliqueGlouton()
	{
		while(true)
		{
			ArrayList<Sommet> listRestTemp = new ArrayList<Sommet>();
			//Pour tous les sommets restants 
			for (int j = 0; j < sommetRestants.size(); j++) 
			{
				Sommet sommetTmp = sommetRestants.get(j);

				if(graphe.isEdgeInGraphe(sommetTmp, resultatPartiel.get(resultatPartiel.size()-1)))
				{
					listRestTemp.add(sommetTmp);
				}
				mapSommetTemp.put(new Integer(resultatPartiel.get(resultatPartiel.size()-1).getValue()), listRestTemp);
			}	
			findSommetToAddClicMaximum();
		}
	}

	public void findSommetToAddClicMaximum()
	{
		//si on trouve un sommet qui aggrandie la clic maximal, 
		//on sort de la méthode pour y revenir au tour suivant
		boolean clicFind = false;
		
		for(Entry<Integer, ArrayList<Sommet>> entry : mapSommetTemp.entrySet()) 
		{
		    Integer cle = entry.getKey();
		    sommetAdjacents = entry.getValue();
		    
		    for (int i = 0; i < sommetAdjacents.size(); i++) 
		    {
		    	int cptIsContenu = 0;
		    	for(Entry<Integer, ArrayList<Sommet>> entryTemp : mapSommetTemp.entrySet()) 
				{
		    		Integer cleTmp = entry.getKey();
				    ArrayList<Sommet> sommetsAdjacentsTmp = entry.getValue();
				    
		    		if(cle!=cleTmp)
		    		{
		    			if(!sommetsAdjacentsTmp.contains(sommetAdjacents.get(i)))
		    			{
		    				cptIsContenu++;
		    			}
		    			
		    		}
				}
		    	
		    	if(cptIsContenu==0)
		    	{
		    		//ajouter new sommet de sommetsAdjacents.get(i) au bleu (c'est a dire a Resultatpartiel)
		    		if(sommetAdjacents.get(i)!=null)
		    		{
		    			resultatPartiel.add(new Sommet(sommetAdjacents.get(i).getValue()));
		    			//sommetRestants.remove(sommetAdjacents.get(i));
		    		}
//		    		System.out.println("sommetsAdjacents.size() : "+sommetsAdjacents.size());
//		    		System.out.println("sommetAParcourir.size() : "+sommetAParcourir.size());
//		    		System.out.println("sommetRestants.size() : "+sommetRestants.size());

		    		clicFind=true;		    		
		    	}
			    System.out.println(clicFind);
		    	if(clicFind)
		    	{
		    		break;
		    	}
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
