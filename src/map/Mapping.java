package map;

import java.util.ArrayList;
import java.util.Iterator;

import map.tiles.Buttress;
import map.tiles.Field;
import map.tiles.Mountain;
import map.tiles.Tile;

public class Mapping {
	
	private String name = "";
	private int width = 20; // Largeur de la map en tiles
	private int height = 20; // Hauteur de la map en tiles
	private ArrayList<ArrayList<Tile>> tiles = new ArrayList<ArrayList<Tile>>();
	
	/* Constructeurs */
	
	public Mapping (String XMLFileName)
	{
		super ();
		this.readXMLFile (XMLFileName);
	}

	public Mapping(String name, int widthTiles, int heightTiles) {
		// TODO Auto-generated constructor stub
		super();
		this.name = name;
		this.width = widthTiles;
		this.height = heightTiles;
		this.initializeTiles();
	}
	
	/* Initialisations */
	
	public void initializeTiles ()
	{
		int i = 0, j = 0;
		for (i = 0; i < this.height; ++i)
		{
			ArrayList<Tile> newLine = new ArrayList<Tile>();
			for (j = 0; j < this.width; ++j)
			{
				newLine.add(new Field ());
			}
			this.tiles.add(newLine);
		}
		return;
	}
	
	/* Getters */
	
	public String getName() {
		return name;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	/* Traitement de fichier */

	public void readXMLFile (String filename)
	{
		XMLParser parser = new XMLParser (filename);
		this.name = parser.getMapName();
		this.width = parser.getMapWidth();
		this.height = parser.getMapHeight();
		this.initializeTiles();
		this.tiles = parser.getMapTiles();
		this.setCorrectImages();
	}
	
	public void setCorrectImages ()
	{
		Iterator<ArrayList<Tile>> line = this.tiles.iterator();
		int i = 0; int j = 0;
		while(line.hasNext())
        {
			ArrayList<Tile> currentLine = (ArrayList<Tile>) line.next();
        	Iterator<Tile> column = currentLine.iterator();
        	while(column.hasNext())
        	{
        		Tile currentTile = (Tile) column.next();
        		if (currentTile instanceof Mountain || currentTile instanceof Buttress)
        		{
        			boolean left = false;
        			boolean right = false;
        			boolean top = false;
        			boolean bottom = false;
        			
        			currentTile.getClass().equals(currentTile.getClass());
        			
        			// Si le tile en haut est Mountain
    				if (i > 0 && ((Tile) this.tiles.get(i-1).get(j)).getClass().equals(currentTile.getClass()))
                    	top = true;
    				
        			// Si le tile à gauche est Mountain
        			if (j > 0 && ((Tile) this.tiles.get(i).get(j-1)).getClass().equals(currentTile.getClass()))
        				left = true;
        			
        			// Si le tile en bas est Mountain
            		if (i < this.width - 1 && ((Tile)this.tiles.get(i+1).get(j)).getClass().equals(currentTile.getClass()))
            			bottom = true;
    				
    				// Si le tile à droite est Mountain
					if (j < this.width - 1 && ((Tile)this.tiles.get(i).get(j+1)).getClass().equals(currentTile.getClass()))
						right = true;
					
					if (currentTile instanceof Mountain)
						((Mountain) currentTile).findPos (top, left, bottom, right);
					else
						((Buttress) currentTile).findPos (top, left, bottom, right);
					
        		}
        		++j;
        	}
        	++i;
        	j = 0;
        }
	}
	
	/* Affichage */
	
	/*public void print ()
	{
		Iterator<ArrayList<Tile>> line = this.tiles.iterator();
		while(line.hasNext())
        {
			ArrayList<Tile> currentLine = (ArrayList<Tile>) line.next();
        	Iterator<Tile> column = currentLine.iterator();
        	while(column.hasNext())
        	{
        		Tile currentTile = (Tile) column.next();
        		currentTile.getType().print();
        	}
        }
		return;
	}*/
	
	public ArrayList<ArrayList<Tile>> getTiles() {
		return tiles;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Mapping map = new Mapping ("map1.xml");
		System.out.println(map.getName());
		System.out.println(map.getWidth());
		System.out.println(map.getHeight());
		//map.print();
	}
}
