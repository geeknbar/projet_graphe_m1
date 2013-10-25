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
		grapheToAffiche= "Graphe [Nombre Ar�tes = " + graphe.size() + ", Nombre Sommet=" + allSommet.size() + "]";
		 
		 return grapheToAffiche;
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
