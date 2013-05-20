package map;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/* ----- IMPORTS LECTURE XML ----- */
import org.jdom2.Element;
import org.jdom2.filter.ElementFilter;
import org.jdom2.input.SAXBuilder;

import basis.Base;

/* ----- IMPORTS TILES ----- */
import map.tiles.Tile;
import map.tiles.Buttress;
import map.tiles.Field;
import map.tiles.Mountain;

public class XMLParser {
	
	private org.jdom2.Document document;
	private Element racine;
	
	/* ----- CONSTRUCTEUR ----- */

	public XMLParser(String filename) {
		// TODO Auto-generated constructor stub
		super();
		String workingDir = System.getProperty("user.dir");
		String finalfile = workingDir + File.separator + "data" + File.separator + filename;
		
		//On crée une instance de SAXBuilder
		SAXBuilder sxb = new SAXBuilder();
		try
		{
			//On crée un nouveau document JDOM avec en argument le fichier XML
			document = sxb.build(new File(finalfile));
		}
		catch(Exception e){e.printStackTrace();}
		finally
		{
			//On initialise un nouvel élément racine avec l'élément racine du document.
			racine = document.getRootElement();
		}
	}
	
	/* ----- DONNEES DE LA MAP ----- */
	
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
	
	/* ----- DONNEES DE ZONES ----- */
	
	public void getMapZones (Mapping map)
	{
		ArrayList<Zone> zones = new ArrayList<Zone>();
		Iterator<Element> it = (Iterator<Element>) racine.getDescendants(new ElementFilter ("zone"));
		while (it.hasNext())
		{
			Element current = it.next();
			Zone zone = new Zone(Integer.parseInt(current.getAttributeValue("player")));
			zones.add(zone);
			getDescendentTiles (current, zone, map);
			getDescendentGroupsOfTiles(current, zone, map);
			getDescendentBasis (current, zone, map);
		}
		map.setZones(zones);
	}
	
	/* ----- DONNEES DE TILES ----- */
	
	public Tile readMapTile (Element tileElement, Zone zone, Mapping map)
	{
		if (tileElement.getName() != "tile" && tileElement.getName() != "group") return null;
		
		// Les coordonnées du tile ne peuvent être en dehors de la taille de la map demandée.
		if (Integer.parseInt(tileElement.getAttributeValue("x")) < 0 && Integer.parseInt(tileElement.getAttributeValue("x")) >= this.getMapWidth() && Integer.parseInt(tileElement.getAttributeValue("y")) < 0 && Integer.parseInt(tileElement.getAttributeValue("y")) >= this.getMapHeight())
		{
			return null;
		}
		
		/* Si la zone n'est pas nulle, */
		if (zone != null)
		{			
			/* Le tile appartient à cette zone : on met la zone en attribut. */
			/* On sait aussi que le Tile est de type Buttress. */
			return new Buttress (zone);
		}
		
		/* Sinon : le parent est un type de tile, le tile n'appartient à aucune zone particulière. */
		else
		{
			zone = null;
			
			/* On récupère l'élément parent à la balise tile */
			Element parent = tileElement.getParentElement();
			
			switch (parent.getName())
			{
				case "mountain" :
				{
					return new Mountain ();
				}
				default :
				{
					break;
				}
			}
		}
		return new Field();
	}
	
	public void getDescendentTiles (Element element, Zone zone, Mapping map)
	{
		if (map.getTiles () == null)
			map.initializeTiles();
		
		Iterator<Element> it = (Iterator<Element>) element.getDescendants(new ElementFilter ("tile"));
		while (it.hasNext())
		{
			Element current = it.next();
			Tile tile = new Field();
			
			// Les coordonnées du tiles ne peuvent être en dehors de la taille de la map demandée.
			if (Integer.parseInt(current.getAttributeValue("x")) >= 0 && Integer.parseInt(current.getAttributeValue("x")) < this.getMapWidth() && Integer.parseInt(current.getAttributeValue("y")) >= 0 && Integer.parseInt(current.getAttributeValue("y")) < this.getMapHeight())
			{					
				tile = readMapTile (current, zone, map);
				
				if (tile != null)
				{
					/* On détermine la case du tableau-liste à double dimension (ArrayList de ArrayList) qui doit être actualisée :
					 * ligne et colonne
					 */
					int column = Integer.parseInt(current.getAttributeValue("x"));
					int line = Integer.parseInt(current.getAttributeValue("y"));
					
					/* Le tile est sauvegardé dans le tableau-liste retourné. */
					map.getTiles().get(line).set(column, tile);
				}
			}
		}
	}
	
	public void getDescendentGroupsOfTiles (Element element, Zone zone, Mapping map)
	{
		int i = 0, j = 0;
		/* Parcours des balises group contenues dans le document avec un itérateur */	
		Iterator<Element> it = (Iterator<Element>) element.getDescendants(new ElementFilter ("group"));
		while (it.hasNext())
		{
			Element current = it.next();
			Tile tile = new Field ();
			
			// Les coordonnées du tiles ne peuvent être en dehors de la taille de la map demandée.
			if (Integer.parseInt(current.getAttributeValue("x")) >= 0 && Integer.parseInt(current.getAttributeValue("x")) < this.getMapWidth() && Integer.parseInt(current.getAttributeValue("y")) >= 0 && Integer.parseInt(current.getAttributeValue("y")) < this.getMapHeight())
			{
				// On veille à ce que le groupe de tiles ne dépasse pas la map.
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
						tile = readMapTile(current, zone, map);
						if (tile != null)
						{
							/* On détermine la case du tableau-liste à double dimension (ArrayList de ArrayList) qui doit être actualisée :
							 * ligne et colonne
							 */
							int column = Integer.parseInt(current.getAttributeValue("x")) + i;
							int line = Integer.parseInt(current.getAttributeValue("y")) + j;
							
							/* Le tile est sauvegardé dans le tableau-liste retourné. */
							map.getTiles().get(line).set(column, tile);
						}
					}
				}
			}
		}
	}
	
	public void getMap (Mapping map)
	{
		/* Initialisation de tilesTable :
		 * On initialise un tableau width * height (attributs de la balise map) avec uniquement des tiles de type Field.
		 */
		if (map.getTiles () == null)
			map.initializeTiles();
		
		/* Récupération des zones et donc des tiles de type buttress */
		getMapZones(map);
		
		/* Récupération des tiles de type mountain */
		Element current = racine.getChild ("mountain");
		getDescendentTiles (current, null, map);
		getDescendentGroupsOfTiles(current, null, map);
		
		current = racine.getChild("neutralbasis");
		getDescendentBasis (current, null, map);
	}
	
	/* ----- DONNEES DE BASES ----- */
	
	public void getDescendentBasis (Element element, Zone zone, Mapping map)
	{
		if (element == null) {
			return;
		}
		
		Iterator<Element> it = (Iterator<Element>) element.getDescendants(new ElementFilter ("base"));
		
		while (it.hasNext())
		{
			Element baseElement = it.next();
			int x = Integer.parseInt(baseElement.getAttributeValue("x"));
			int y = Integer.parseInt(baseElement.getAttributeValue("y"));
			int capacity = Integer.parseInt(baseElement.getAttributeValue("capacity"));
			
			if (!(x < 0 || x >= 20 || x+capacity/5-1 < 0 || x+capacity/5-1 >= 20 || y+capacity/5-1 < 0 || y+capacity/5-1 >= 20))
				map.getBasis().put (new Point (x, y), new Base(capacity, zone));
		}
	}
}
