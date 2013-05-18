package map;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import map.tiles.Buttress;
import map.tiles.Field;
import map.tiles.Mountain;
import map.tiles.Tile;
import org.jdom2.Element;
import org.jdom2.filter.ElementFilter;
import org.jdom2.input.SAXBuilder;

public class XMLParser {
	
	private org.jdom2.Document document;
	private Element racine;
	
	/* Constructeur */

	public XMLParser(String filename) {
		// TODO Auto-generated constructor stub
		super();
		String workingDir = System.getProperty("user.dir");
		String finalfile = workingDir + File.separator + "data" + File.separator + filename;
		
		//On cr�e une instance de SAXBuilder
		SAXBuilder sxb = new SAXBuilder();
		try
		{
			//On cr�e un nouveau document JDOM avec en argument le fichier XML
			document = sxb.build(new File(finalfile));
		}
		catch(Exception e){e.printStackTrace();}
		finally
		{
			//On initialise un nouvel �l�ment racine avec l'�l�ment racine du document.
			racine = document.getRootElement();
		}
	}
	
	/* Donn�es de la map */
	
	public String getMapName ()
	{
	   return racine.getAttributeValue("name");
	}
	
	public int getMapWidth ()
	{
		return Integer.parseInt(racine.getAttributeValue("width"));
	}
	
	public int getMapHeight ()
	{
		return Integer.parseInt(racine.getAttributeValue("height"));
	}
	
	/* Donn�es de tiles */
	
	public Tile readMapTile (Element tileElement, Zone zone)
	{
		if (tileElement.getName() != "tile" && tileElement.getName() != "group") return null;
		
		Tile tile = new Field ();
		
		// Les coordonn�es du tile ne peuvent �tre en dehors de la taille de la map demand�e.
		if (Integer.parseInt(tileElement.getAttributeValue("x")) < 0 && Integer.parseInt(tileElement.getAttributeValue("x")) >= this.getMapWidth() && Integer.parseInt(tileElement.getAttributeValue("y")) < 0 && Integer.parseInt(tileElement.getAttributeValue("y")) >= this.getMapHeight())
		{
			return null;
		}
		/* On r�cup�re l'�l�ment parent � la balise tile */
		Element parent = tileElement.getParentElement();
		
		/* Si l'�l�ment parent repr�sente une zone : */
		if (parent.getName() == "zone")
		{
			/* Si la zone n'existe pas encore, on la cr�e */
			if (zone == null || Integer.parseInt(parent.getAttributeValue("id")) != zone.getId())
			{
				zone = new Zone (Integer.parseInt(parent.getAttributeValue("id")));
			}
			
			/* Le tile appartient � cette zone : on met la zone en attribut. */
			/* On sait aussi que le Tile est de type Buttress. */
			tile = new Buttress (zone);
			
			/* On r�cup�re le parent de la zone, qui est obligatoirement un type de tile (si le document XML est bien construit). */
			parent = parent.getParentElement();
		}
		
		/* Sinon : le parent est un type de tile, le tile n'appartient � aucune zone particuli�re. */
		else
		{
			zone = null;
			switch (parent.getName())
			{
				case "mountain" :
				{
					tile = new Mountain ();
					break;
				}
				default :
				{
					break;
				}
			}
		}
		
		return tile;
	}
	
	public ArrayList<ArrayList<Tile>> getMapTiles ()
	{
		/* Initialisation de tilesTable :
		 * On initialise un tableau width * height (attributs de la balise map) avec uniquement des tiles de type Field.
		 */
		
		ArrayList<ArrayList<Tile>> tilesTable = new ArrayList<ArrayList<Tile>>();
		
		int i = 0, j = 0;
		for (i = 0; i < getMapHeight(); ++i)
		{
			ArrayList<Tile> newLine = new ArrayList<Tile>();
			for (j = 0; j < getMapWidth(); ++j)
			{
				newLine.add(new Field ());
			}
			tilesTable.add(newLine);
		}
		
		/* Parcours des balises tile contenues dans le document avec un it�rateur */
		
		Iterator<Element> it = (Iterator<Element>) racine.getDescendants(new ElementFilter ("tile"));
		Zone zone = null;
		while (it.hasNext())
		{
			Element current = it.next();
			Tile tile = new Field ();
			
			// Les coordonn�es du tiles ne peuvent �tre en dehors de la taille de la map demand�e.
			if (Integer.parseInt(current.getAttributeValue("x")) >= 0 && Integer.parseInt(current.getAttributeValue("x")) < this.getMapWidth() && Integer.parseInt(current.getAttributeValue("y")) >= 0 && Integer.parseInt(current.getAttributeValue("y")) < this.getMapHeight())
			{					
				tile = readMapTile (current, zone);
				if (tile != null)
				{
					/* On d�termine la case du tableau-liste � double dimension (ArrayList de ArrayList) qui doit �tre actualis�e :
					 * ligne et colonne
					 */
					int column = Integer.parseInt(current.getAttributeValue("x"));
					int line = Integer.parseInt(current.getAttributeValue("y"));
					
					/* Le tile est sauvegard� dans le tableau-liste retourn�. */
					tilesTable.get(line).set(column, tile);
				}
			}
		}
		
		/* Parcours des balises group contenues dans le document avec un it�rateur */	
		it = (Iterator<Element>) racine.getDescendants(new ElementFilter ("group"));
		zone = null;
		while (it.hasNext())
		{
			Element current = it.next();
			Tile tile = new Field ();
			
			// Les coordonn�es du tiles ne peuvent �tre en dehors de la taille de la map demand�e.
			if (Integer.parseInt(current.getAttributeValue("x")) >= 0 && Integer.parseInt(current.getAttributeValue("x")) < this.getMapWidth() && Integer.parseInt(current.getAttributeValue("y")) >= 0 && Integer.parseInt(current.getAttributeValue("y")) < this.getMapHeight())
			{
				// On veille � ce que le groupe de tiles ne d�passe pas la map.
				int height = Integer.parseInt(current.getAttributeValue("height"));
				int width = Integer.parseInt(current.getAttributeValue("width"));
				if (height + Integer.parseInt(current.getAttributeValue("y")) >= this.getMapHeight())
				{
					height -= (height + Integer.parseInt(current.getAttributeValue("y"))) - this.getMapHeight();
				}
				if (width + Integer.parseInt(current.getAttributeValue("x")) >= this.getMapWidth())
				{
					width -= (width + Integer.parseInt(current.getAttributeValue("x"))) - this.getMapWidth();
				}
				
				for (i = 0; i < width; ++i)
				{
					for (j = 0; j < height; ++j)
					{
						tile = readMapTile(current, zone);
						
						if (tile != null)
						{
							/* On d�termine la case du tableau-liste � double dimension (ArrayList de ArrayList) qui doit �tre actualis�e :
							 * ligne et colonne
							 */
							
							int column = Integer.parseInt(current.getAttributeValue("x")) + i;
							int line = Integer.parseInt(current.getAttributeValue("y")) + j;
							
							/* Le tile est sauvegard� dans le tableau-liste retourn�. */
							tilesTable.get(line).set(column, tile);
						}
					}
				}
			}
		}
		
		/* On retourne le tableau-liste construit. */
		return tilesTable;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		XMLParser parser = new XMLParser ("map1.xml");
		System.out.println(parser.getMapName());
		parser.getMapTiles();
	}

}
