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
	private ArrayList<Sommet> sommetPotentiel;



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
		graphe.findAllAdjacenceSommet();
		sommetPotentiel = new ArrayList<Sommet>();
	}




	/**
	 * 
	 */
	public void cliqueMaximumGlouton()
	{
		//Pour tous les sommets du graphes
		for (int i = 0; i < graphe.getAllSommet().size(); i++) 
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
			System.out.println(resultatPartiel.toString());
			//si la clic est plus grande que la pr�c�dente, on la prend comme resultat final
			if(resultatPartiel.size() > resultatFinal.size())
				resultatFinal= new ArrayList<Sommet>(resultatPartiel);

		}

		System.out.println(resultatFinal.toString());
		System.out.println("La clique est de : "+resultatFinal.size() + " sommets");
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
				Sommet sommetTmp = sommetRestants.get(j);
				if(graphe.isEdgeInGraphe(sommetTmp, resultatPartiel.get(resultatPartiel.size()-1)))
				{
					listRestTemp.add(sommetTmp);
				}
			}	
			mapSommetTemp.put(new Integer(resultatPartiel.get(resultatPartiel.size()-1).getValue()), listRestTemp);
			findSommetToAddClicMaximumAlgo2();
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
			//a améliorer car on break après le premier parcours...
			//cf pasNormal => break a la fin du fort 
			Integer cle = entry.getKey();
			firstSommetListeAdjacent = entry.getValue();

			//sommetTrouve correspond si un sommet à été ajouté ou non à la liste des resultatPartiel
			boolean sommetTrouve = false;

			//on parcours tous les sommets de la liste d'ajacence du premier sommet
			for (int i = 0; i < firstSommetListeAdjacent.size(); i++) 
			{

				//compteur si = 0 alors on a trouvé un sommet en commun dans chaque liste
				// si > 0 alors au moins dans une liste on n'a pas de sommet en commun
				int cmpt =0;
				ArrayList<Sommet> firstSommetListeAdjacentTemp = new ArrayList<Sommet>();

				//pour chaque sommet on va chercher dans la map un sommet en commum avec notre liste du premier sommet
				for(Entry<Integer, ArrayList<Sommet>> entryTemp : mapSommetTemp.entrySet()) 
				{
					Integer cleTemp = entryTemp.getKey();
					firstSommetListeAdjacentTemp = entryTemp.getValue();
					if(cle!=cleTemp){
						//si on ne trouve pas de sommet contenu dans les deux liste en cours on incrémente le compteur
						//voir si on ne peut pas break pour aller plus vite
						if(!firstSommetListeAdjacentTemp.contains(firstSommetListeAdjacent.get(i))){
							cmpt ++;
						}

					}
				}
				//si le compteur est à 0 c'est qu'on a trouvé pour chaque liste de la map
				//sinon le compteur serait >0
				if(cmpt == 0){
					resultatPartiel.add(new Sommet(firstSommetListeAdjacent.get(i).getValue()));
					sommetTrouve = true;
					break;
				}
			}

			//si on ne trouve pas de sommet c'est qu'on est arrivé à une clique maximum donc on passe au tour suivant
			if(!sommetTrouve){
				endOfMainLoop = true;
			}

			//pasNormal, break pour prendre uniquement la liste d'ajascence du premier sommet de resultatPartiel
			break;

		}

	}

	/**
	 * Permet de trouver le sommet qui forme la premi�re clic maximal
	 * avec comme choix de prendre le sommet adjacent qui à le plus de sommet adjacents
	 */
	public void findSommetToAddClicMaximumAlgo2(){
		ArrayList<Sommet> firstSommetListeAdjacent = new ArrayList<Sommet>();

		for(Entry<Integer, ArrayList<Sommet>> entry : mapSommetTemp.entrySet()) 
		{
			//on prend la liste d'ajacence du premier sommet des resultats partiel
			//a améliorer car on break après le premier parcours...
			//cf pasNormal => break a la fin du fort 
			Integer cle = entry.getKey();
			firstSommetListeAdjacent = entry.getValue();

			//sommetTrouve correspond si un sommet à été ajouté ou non à la liste des resultatPartiel
			boolean sommetTrouve = false;

			//on parcours tous les sommets de la liste d'ajacence du premier sommet
			for (int i = 0; i < firstSommetListeAdjacent.size(); i++) 
			{

				//compteur si = 0 alors on a trouvé un sommet en commun dans chaque liste
				// si > 0 alors au moins dans une liste on n'a pas de sommet en commun
				int cmpt =0;
				ArrayList<Sommet> firstSommetListeAdjacentTemp = new ArrayList<Sommet>();

				//pour chaque sommet on va chercher dans la map un sommet en commum avec notre liste du premier sommet
				for(Entry<Integer, ArrayList<Sommet>> entryTemp : mapSommetTemp.entrySet()) 
				{
					Integer cleTemp = entryTemp.getKey();
					firstSommetListeAdjacentTemp = entryTemp.getValue();
					if(cle!=cleTemp){
						//si on ne trouve pas de sommet contenu dans les deux liste en cours on incrémente le compteur
						//voir si on ne peut pas break pour aller plus vite
						if(!firstSommetListeAdjacentTemp.contains(firstSommetListeAdjacent.get(i))){
							cmpt ++;
						}

					}
				}
				//si le compteur est à 0 c'est qu'on a trouvé pour chaque liste de la map
				//sinon le compteur serait >0
				if(cmpt == 0){
					Sommet s = new Sommet(firstSommetListeAdjacent.get(0).getValue());
					Sommet s2 = new Sommet(0);
					//on parcours l'ensemble des sommet potentiel trouvé pour prendre celui qi a le plus de sommet adjacents
					for (int j = 1; j < firstSommetListeAdjacent.size(); j++) {
						s2 = new Sommet(firstSommetListeAdjacent.get(j).getValue());
						if(graphe.getNumberSommetAdj(s2) > graphe.getNumberSommetAdj(s)){
								s = new Sommet(s2.getValue());
								System.out.println(s.getValue());
						}
					}
					if(!isSommetInResultPartial(resultatPartiel,s)){
						resultatPartiel.add(s);
						sommetTrouve =true;
					}

					break;
				}
			}

			//si on ne trouve pas de sommet c'est qu'on est arrivé à une clique maximum donc on passe au tour suivant
			if(!sommetTrouve){
				endOfMainLoop = true;
			}

			//pasNormal, break pour prendre uniquement la liste d'ajascence du premier sommet de resultatPartiel
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

	//methode pour savoir si un sommet est contenu dans le resultat partiel
	//sert dans l'algo2
	public boolean isSommetInResultPartial(ArrayList<Sommet> resultat, Sommet s){
		boolean estContenu = false;
		for (int i = 0; i < resultat.size(); i++) {
			if(resultat.get(i).getValue() == s.getValue()){
				estContenu = true;
			}
		}
		return estContenu;
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
