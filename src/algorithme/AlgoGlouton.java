package algorithme;

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
	private ArrayList<Sommet> resultatFinal;
	private HashMap<Integer, ArrayList<Sommet>> mapSommetTemp;



	private Graphe graphe;
	private boolean endOfMainLoop;

	/**
	 * Constructeur
	 * @param readFile
	 */
	public AlgoGlouton(ReadFile readFile)
	{
		graphe= readFile.getGraphe();
		sommetAdjacents = new ArrayList<Sommet>();
		resultatFinal = new ArrayList<Sommet>();
		endOfMainLoop=false;
	}




	/**
	 * 
	 */
	public void cliqueMaximumGlouton()
	{
		//Pour tous les sommets du graphes
		for (int i = 0; i < 3; i++) 
		{
			System.out.println("tour : "+ i);
			//on prend tous les sommet dans la liste des r�sultats restants
			sommetRestants=new ArrayList<Sommet>(graphe.getAllSommet());

			//on r�initialise la liste des r�sultat partiel a chaque fois 
			//que l'on veut trouver une clic maximal
			resultatPartiel = new ArrayList<Sommet>();

			//on initialise la liste des r�sultats partiel avec le sommet pris au hasard*
			//*hasard : nous les prenons du premier au dernier. 
			resultatPartiel.add(new Sommet(sommetRestants.get(i).getValue()));

			//on supprime le sommet inital courant.
			//sommetRestants.remove(i);

			//initialisation de la map temporaire
			mapSommetTemp = new HashMap<Integer, ArrayList<Sommet>>();

			//lancement du traitement de la clic maximum pour chacun des sommets
			findCliqueGlouton();

			endOfMainLoop = false;

			//si la clic est plus grande que la pr�c�dente, on la prend comme resultat final
			if(resultatPartiel.size() > resultatFinal.size())
				resultatFinal= new ArrayList<Sommet>(resultatPartiel);
		}

		System.out.println(resultatFinal.toString());
		System.out.println("La clique est de : "+resultatFinal.size() + "sommets");
	}


	/**
	 *  pour trouver une clic, nous choisissons de prendre le premier
	 */
	public void findCliqueGlouton()
	{
		while(!endOfMainLoop)
		{
			ArrayList<Sommet> listRestTemp = new ArrayList<Sommet>();

			//on recupere le dernier sommet de la liste partiel (bleu)
			Sommet lastElementPartialList= new Sommet(resultatPartiel.get(resultatPartiel.size()-1));

			sommetRestants=new ArrayList<Sommet>(graphe.getAllSommet());
			//on enleve le sommet en cours parmis tous les sommets 
			deleteSommetToRestant(lastElementPartialList.getValue());

			//Pour tous les sommets restants on cherche les sommet adjacents 
			//au sommet 'lastElementPartialList'
			for (int j = 0; j < sommetRestants.size(); j++) 
			{
				//				if(!isInSommetToResultat(sommetRestants.get(j))){
				Sommet sommetTmp = sommetRestants.get(j);
				if(graphe.isEdgeInGraphe(sommetTmp, resultatPartiel.get(resultatPartiel.size()-1)))
				{
					listRestTemp.add(sommetTmp);
				}

				//				}
			}	
			mapSommetTemp.put(new Integer(resultatPartiel.get(resultatPartiel.size()-1).getValue()), listRestTemp);
			findSommetToAddClicMaximum();
		}
	}


	/**
	 * Permet de trouver le sommet qui forme la premi�re clic maximal
	 */
	public void findSommetToAddClicMaximum(){
		ArrayList<Sommet> firstSommetListeAdjacent = new ArrayList<Sommet>();

		for(Entry<Integer, ArrayList<Sommet>> entry : mapSommetTemp.entrySet()) 
		{
			//on prend la liste d'ajacence du premier sommet des resultats partiel
			Integer cle = entry.getKey();
			firstSommetListeAdjacent = entry.getValue();
			//do something
			boolean sommetTrouve = false;
			for (int i = 0; i < firstSommetListeAdjacent.size(); i++) 
			{

				int cmpt =0;
				ArrayList<Sommet> firstSommetListeAdjacentTemp = new ArrayList<Sommet>();
				for(Entry<Integer, ArrayList<Sommet>> entryTemp : mapSommetTemp.entrySet()) 
				{
					Integer cleTemp = entryTemp.getKey();
					firstSommetListeAdjacentTemp = entryTemp.getValue();
					if(cle!=cleTemp){
						if(!firstSommetListeAdjacentTemp.contains(firstSommetListeAdjacent.get(i))){
							cmpt ++;	
						}

					}
				}
				if(cmpt == 0){
					resultatPartiel.add(new Sommet(firstSommetListeAdjacent.get(i).getValue()));
					sommetTrouve = true;
					break;
				}
			}

			if(!sommetTrouve){
				endOfMainLoop = true;
			}

			break;

		}

	}


	void deleteSommetToRestant(int valeur)
	{
		for (int i = 0; i < sommetRestants.size(); i++) 
		{
			if(sommetRestants.get(i).getValue() == valeur)
			{
				sommetRestants.remove(i);
				break;				
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




	public HashMap<Integer, ArrayList<Sommet>> getMapSommetTemp() {
		return mapSommetTemp;
	}




	public void setMapSommetTemp(HashMap<Integer, ArrayList<Sommet>> mapSommetTemp) {
		this.mapSommetTemp = mapSommetTemp;
	}




	public boolean isEndOfMainLoop() {
		return endOfMainLoop;
	}




	public void setEndOfMainLoop(boolean endOfMainLoop) {
		this.endOfMainLoop = endOfMainLoop;
	}
}
