package context;

import java.util.ArrayList;

/**
 * @author Mickael
 *
 */
public class Graphe
{
	private ArrayList<Edge> graphe;
	private ArrayList<Sommet> allSommet;
	private int allSommetAdj[] ;

	public Graphe() 
	{
		graphe = new ArrayList<Edge>();
		allSommet =new ArrayList<Sommet>();
	}



	@Override
	public String toString()
	{
		String grapheToAffiche="";
		grapheToAffiche= "Graphe [Nombre Ar�tes = " + graphe.size()/2 + ", Nombre Sommet=" + allSommet.size() + "]";

		return grapheToAffiche;
	}


	public boolean isEdgeInGraphe(Sommet s1, Sommet s2)
	{
		for (int i = 0; i <graphe.size() ; i++) 
		{
			if(graphe.get(i).isInEdges(s1, s2))
			{
				return true;
			}
		}
		return false;
	}


	public boolean contains(int o) 
	{
		for (int i = 0; i < allSommet.size(); i++) 
		{
			if(allSommet.get(i).getValue()==o)
			{
				return true;
			}
		}
		return false;
	}

	/** Permet d'ajouter pour tous les sommets leurs sommets adjacents
	 * 
	 * @param valeur
	 */
	public void findAllAdjacenceSommet(){
		allSommetAdj = new int[allSommet.size()+1];
		for (int i = 0; i < allSommet.size(); i++) 
		{
			for (int j = 0; j < allSommet.size(); j++) 
			{
				if(this.isEdgeInGraphe(allSommet.get(i), allSommet.get(j) ))
				{
					allSommetAdj[allSommet.get(i).getValue()]++;
				}	
			}

		}	
	}

	//permet d'avoir la valeur de sommet adjacent d'un sommet donné
	public int getNumberSommetAdj(Sommet s){
		return allSommetAdj[s.getValue()];
	}

	public int[] getAllSommetAdj() {
		return allSommetAdj;
	}

	public void showTabSommetAdj(){
		for (int i = 0; i < allSommetAdj.length; i++) 
		{
			System.out.println(i +" " +allSommetAdj[i]);
		}
	}

	public void addEdge(Edge edge)
	{
		graphe.add(edge);
	}

	public void addSommet(Sommet sommet)
	{
		allSommet.add(sommet);
	}


	public ArrayList<Edge> getGraphe() 
	{
		return graphe;
	}

	public void setGraphe(ArrayList<Edge> graphe)
	{
		this.graphe = graphe;
	}

	public ArrayList<Sommet> getAllSommet()
	{
		return allSommet;
	}

	public void setAllSommet(ArrayList<Sommet> allSommet) 
	{
		this.allSommet = allSommet;
	}
}
