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
	private ArrayList<Integer> resultatPartiel;
	private ArrayList<Integer> sommetRestants;
	private ArrayList<Integer> resultatFinal;
	private HashMap<Integer, ArrayList<Integer>> mapSommetTemp;
	private ArrayList<Integer> sommetPotentiel;


	private Graphe graphe;
	private boolean endOfMainLoop;

	/**
	 * Constructeur
	 * @param readFile
	 */
	public AlgoGlouton(ReadFile readFile)
	{
		graphe= readFile.getGraphe();
		resultatFinal = new ArrayList<Integer>();
		endOfMainLoop=false;
		sommetPotentiel = new ArrayList<Integer>();
	}

	public void cliqueMaximumGlouton()
	{
		long startTime = System.currentTimeMillis();
		//Pour tous les sommets du graphes
		for (int i = 0; i < graphe.getAllSommet().size(); i++) 
		{
			System.out.println("tour : "+ i);
			//on prend tous les sommet dans la liste des r�sultats restants
			sommetRestants=new ArrayList<Integer>(graphe.getAllSommet());

			//on r�initialise la liste des r�sultat partiel a chaque fois 
			//que l'on veut trouver une clic maximal
			resultatPartiel = new ArrayList<Integer>();

			//on initialise la liste des r�sultats partiel avec le sommet pris au hasard*
			//*hasard : nous les prenons du premier au dernier. 
			resultatPartiel.add(sommetRestants.get(i));


			//initialisation de la map temporaire
			mapSommetTemp = new HashMap<Integer, ArrayList<Integer>>();

			//lancement du traitement de la clic maximum pour chacun des sommets
			findCliqueGlouton();

			endOfMainLoop = false;
			System.out.println(resultatPartiel.toString());
			//si la clic est plus grande que la pr�c�dente, on la prend comme resultat final
			if(resultatPartiel.size() > resultatFinal.size())
				resultatFinal= new ArrayList<Integer>(resultatPartiel);

		}
		long endTime = System.currentTimeMillis();
		System.out.println(resultatFinal.toString());
		System.err.println("\nLa clique est de : "+resultatFinal.size() + " sommets");
		System.err.println("Algo execute en "+(endTime-startTime)+" ms");
	}


	/**
	 *  pour trouver une clic, nous choisissons de prendre le premier
	 */
	public void findCliqueGlouton()
	{
		while(!endOfMainLoop)
		{
			//on recupere le dernier sommet de la liste partiel (bleu)
			Sommet lastElementPartialList= new Sommet(resultatPartiel.get(resultatPartiel.size()-1));

			sommetRestants=new ArrayList<Integer>(graphe.getAllSommet());
			//on enleve le sommet en cours parmis tous les sommets 
			deleteSommetToRestant(lastElementPartialList.getValue());

			Integer tmp = new Integer(resultatPartiel.get(resultatPartiel.size()-1));
			mapSommetTemp.put(tmp, graphe.getMapGraphe().get(tmp));
//			findSommetToAddClicMaximum();
			findSommetToAddClicMaximumAlgo2();
		}
	}


	/**
	 * Permet de trouver le sommet qui forme la premi�re clic maximal
	 */
	public void findSommetToAddClicMaximum()
	{
		ArrayList<Integer> firstSommetListeAdjacent = new ArrayList<Integer>();

		for(Entry<Integer, ArrayList<Integer>> entry : mapSommetTemp.entrySet()) 
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
				ArrayList<Integer> firstSommetListeAdjacentTemp = new ArrayList<Integer>();

				//pour chaque sommet on va chercher dans la map un sommet en commum avec notre liste du premier sommet
				for(Entry<Integer, ArrayList<Integer>> entryTemp : mapSommetTemp.entrySet()) 
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
					resultatPartiel.add(firstSommetListeAdjacent.get(i));
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
	public void findSommetToAddClicMaximumAlgo2()
	{
		ArrayList<Integer> firstSommetListeAdjacent = new ArrayList<Integer>();

		for(Entry<Integer, ArrayList<Integer>> entry : mapSommetTemp.entrySet()) 
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
				ArrayList<Integer> firstSommetListeAdjacentTemp = new ArrayList<Integer>();

				//pour chaque sommet on va chercher dans la map un sommet en commum avec notre liste du premier sommet
				for(Entry<Integer, ArrayList<Integer>> entryTemp : mapSommetTemp.entrySet()) 
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
				//sinon le compteur serait >0 et le sommet ne doit pas être dans la liste de resultat partiel
				if(cmpt == 0 && !isSommetInResultPartial(resultatPartiel,firstSommetListeAdjacent.get(i))){
					sommetPotentiel.add(firstSommetListeAdjacent.get(i));
				}
			}
			
			//on doit prendre ici le sommet de sommetPotentiel qui à le plus de sommet adjacents et qui ne soit pas dans les sommets de resultat partiels
			if(!sommetPotentiel.isEmpty()){
				Integer sommet = sommetPotentiel.get(0);
				for (int i = 1; i < sommetPotentiel.size(); i++) {
					if(graphe.getMapGraphe().get(sommetPotentiel.get(i)).size() > graphe.getMapGraphe().get(sommet).size()){
//					if(sommetPotentiel.get(i)>sommet){
						sommet = sommetPotentiel.get(i);
					}
					
				}
				resultatPartiel.add(sommet);
				sommetTrouve = true;
				sommetPotentiel.clear();
				break;
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
			if(sommetRestants.get(i) == valeur)
			{
				sommetRestants.remove(i);
				break;				
			}
		}
	}

	//methode pour savoir si un sommet est contenu dans le resultat partiel
	//sert dans l'algo2
	public boolean isSommetInResultPartial(ArrayList<Integer> resultat, Integer sommet){
		boolean estContenu = false;
		for (int i = 0; i < resultat.size(); i++) {
			if(resultat.get(i) == sommet){
				estContenu = true;
			}
		}
		return estContenu;
	}


}
