package map;

import java.util.ArrayList;
import java.util.Iterator;

import map.tiles.Tile;
import map.tiles.TileType;

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
		for (i = 0; i < this.width; ++i)
		{
			ArrayList<Tile> newLine = new ArrayList<Tile>();
			for (j = 0; j < this.height; ++j)
			{
				newLine.add(new Tile (TileType.Field));
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
	}
	
	/* Affichage */
	
	public void print ()
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
		map.print();
	}
}