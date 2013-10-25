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
	
	public Graphe() 
	{
		graphe = new ArrayList<Edge>();
		allSommet =new ArrayList<Sommet>();
	}
	
	

	@Override
	public String toString()
	{
		String grapheToAffiche="";
		grapheToAffiche= "Graphe [Nombre Arêtes = " + graphe.size()/2 + ", Nombre Sommet=" + allSommet.size() + "]";
		 
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


	public boolean contains(Object o) 
	{
		for (int i = 0; i < allSommet.size(); i++) 
		{
			if(allSommet.get(i).getValue().equals(o))
			{
				return true;
			}
		}
		return false;
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
