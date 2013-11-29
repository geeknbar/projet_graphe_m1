package fileRW;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

import context.Graphe;


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
				
				//Construction du graphe
				graphe.buildGraphe(valueSplit1, valueSplit2);
				graphe.buildAllSommet();
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
