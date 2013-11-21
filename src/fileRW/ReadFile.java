package fileRW;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

import context.Edge;
import context.Graphe;
import context.Sommet;


public class ReadFile 
{

	private Path pathSource;
	private Graphe graphe;
	
	
	public ReadFile()
	{

	}

	public void readLines(String pathS) throws IOException
	{
		setPathSource(pathS);
		graphe = new Graphe();
		
		List<String> lignes =  Files.readAllLines(pathSource, StandardCharsets.UTF_8);  
		for (String ligne : lignes)
		{
			if('e' == ligne.charAt(0))
			{
				String[] ligneSplit = ligne.split(" ");
				int valueSplit1 = Integer.valueOf(ligneSplit[1]);
				int valueSplit2 = Integer.valueOf(ligneSplit[2]);
				
				graphe.addEdge(new Edge(new Sommet(valueSplit1), new Sommet(valueSplit2)));
				graphe.addEdge(new Edge(new Sommet(valueSplit2), new Sommet(valueSplit1)));
				
				if(!graphe.contains(valueSplit1))
				{
					graphe.getAllSommet().add(new Sommet(valueSplit1));
				}
				
				if(!graphe.contains(valueSplit2))
				{
					graphe.getAllSommet().add(new Sommet(valueSplit2));
				}
			}
		}
	}

	public void setPathSource(String pathS)
	{
		pathSource = Paths.get(pathS);
	}

	public Graphe getGraphe() {
		return graphe;
	}

	public void setGraphe(Graphe graphe) 
	{
		this.graphe = graphe;
	}
}
