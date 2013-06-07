package map;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;

import map.tiles.Buttress;
import map.tiles.Field;
import map.tiles.Mountain;
import map.tiles.Tile;
import towers.Tower;
import towers.strategy.shooter.shootType.Projectile;
import agents.Agent;
import basis.Base;
/* ----- IMPORTS TILES ----- */

public class Mapping {

	private String name = ""; // Titre de la map
	private int width = 20; // Largeur de la map en tiles
	private int height = 20; // Hauteur de la map en tiles
	private ArrayList<ArrayList<Tile>> tiles = new ArrayList<ArrayList<Tile>>(); // Tableau de tiles � 2 dimensions
	private ArrayList<Zone> zones = new ArrayList<Zone> ();
	private Hashtable<Point, Tower> towers = new Hashtable<Point, Tower>();
	private Hashtable<Point, Base> basis = new Hashtable<Point, Base>();
	private ArrayList<Agent> agents = new ArrayList<Agent>();
	private ArrayList<Projectile> bullets = new ArrayList<Projectile>();
	
	/* ----- CONSTRUCTEURS ----- */
	
	// Construit une map � partir de la lecture d'un fichier XML
	public Mapping (String XMLFileName)
	{
		super ();
		/* Lecture du fichier XML */
		this.readXMLFile (XMLFileName);
	}

	// Construit une map � partir des donn�es pass�es en param�tres, et initialise les tableaux.
	public Mapping(String name, int widthTiles, int heightTiles) {
		// TODO Auto-generated constructor stub
		super();
		
		/* Les param�tres sont transform�s en attributs. */
		this.name = name;
		this.width = widthTiles;
		this.height = heightTiles;
		
		/* Le tableau de tiles � 2 dimensions est initialis�. */
		this.initializeTiles();
	}
	
	/* ----- INITIALISATIONS ----- */
	
	// Initialise le tableau de tiles � deux dimensions avec uniquement des tiles Field.
	public void initializeTiles ()
	{
		/* Pour des compteurs i (ligne) allant de 0 � height
		 * et j (colonne) allant de 0 � width,
		 */
		int i = 0, j = 0;
		
		/* Pour chaque ligne, */
		for (i = 0; i < this.height; ++i)
		{
			/* On initialise une ligne */
			ArrayList<Tile> newLine = new ArrayList<Tile>();
			
			/* Pour chaque ligne x colonne, */
			for (j = 0; j < this.width; ++j)
			{
				/* On ajoute un tile Field � la ligne */
				newLine.add(new Field (new Point(j, i)));
			}
			/* On ajoute la ligne au tableau */
			this.tiles.add(newLine);
		}
		return;
	}
	
	/* ----- GETTERS ----- */
	
	public String getName() {
		return name;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public ArrayList<ArrayList<Tile>> getTiles() {
		return tiles;
	}
	
	public Hashtable<Point, Tower> getTowers() {
		return towers;
	}
	
	public Hashtable<Point, Base> getBasis() {
		return basis;
	}
	
	public ArrayList<Zone> getZones() {
		return zones;
	}
	
	public ArrayList<Agent> getAgents() {
		return agents;
	}
	
	/* ----- SETTERS ----- */

	public void setTower (Tower tower, int x, int y)
	{
		int i = 0, j = 0;
		for (i = 0; i < tower.getObjectHeight(); ++i)
		{
			for (j = 0; j < tower.getObjectWidth(); ++j)
			{
				this.towers.put (new Point(x + j, y + i), tower);
			}
		}
	}
	
	public void setBase (Base base, int x, int y)
	{
		int i = 0, j = 0;
		for (i = 0; i < base.getDiam(); ++i)
		{
			for (j = 0; j < base.getDiam(); ++j)
			{
				this.basis.put (new Point(x + j, y + i), base);
			}
		}
	}
	
	public void setZones (ArrayList<Zone> zones)
	{
		this.zones = zones;
	}
	
	/* ----- TRAITEMENT DE FICHIER ----- */

	// Lecture d'un fichier XML
	public void readXMLFile (String filename)
	{		
		/* --- OUVERTURE DU FICHIER XML --- */
		/* On instancie un parser XML qui ouvre le fichier XML demand� */
		XMLParser parser = new XMLParser (filename);
		
		/* --- INFORMATIONS DE LA MAP --- */		
		/* On r�cup�re les informations de la map : nom, largeur, hauteur */
		this.name = parser.getMapName();
		this.width = parser.getMapWidth();
		this.height = parser.getMapHeight();
		
		
		/* --- LECTURE DES TILES --- */
		/* Initialisation du tableau � double dimension de tiles */
		this.initializeTiles();
		
		/* On r�cup�re dans le tableau de tiles les informations lues par le parser */
		parser.getMap(this);
		
		/* On "corrige" les images de tiles buttress et mountain (images de coins et de rebords) */
		this.setCorrectImages();
	}

	// Choix des images de coins/rebords de montagne et contrefort
	public void setCorrectImages ()
	{
		/* --- PARCOURS DU TABLEAU DE TILES --- */
		/* On instancie un it�rateur du tableau de tiles � double dimension : ligne par ligne */
		Iterator<ArrayList<Tile>> line = this.tiles.iterator();
		
		/* Compteurs i et j */
		int i = 0; int j = 0;
		
		/* 
		 * On parcourt le tableau ligne par ligne.
		 * Tant que l'it�rateur n'est pas arriv� � la fin du tableau de lignes de tiles,
		 */
		while(line.hasNext())
        {
			/* L'�l�ment courant est la ligne sur laquelle se situe l'it�rateur. */
			ArrayList<Tile> currentLine = (ArrayList<Tile>) line.next();
			
			/* Le tableau est � double dimension : on parcourt la ligne tile par tile. */
        	Iterator<Tile> column = currentLine.iterator();
        	
        	/* Tant que l'it�rateur column n'est pas arriv� � la fin de la ligne, */
        	while(column.hasNext())
        	{
        		/* L'�l�ment courant est le tile sur lequel se situe l'it�rateur. */
        		Tile currentTile = (Tile) column.next();
        		
        		/* Si l'�l�ment courant est un tile Mountain ou Buttress, on peut "corriger" l'image. */
        		if (currentTile instanceof Mountain || currentTile instanceof Buttress)
        		{
        			/* --- DETERMINATION DES IMAGES --- */
        			
        			/* Les bool�ens initialis�s � false d�terminent si les tiles autour du tile courant sont du m�me type que le tile courant.
        			 * Par exemple, le bool�en left sera mis � true si le tile � gauche du tile courant poss�de le m�me type que le tile courant.
        			 * Remarque : si le tile se trouve sur le bord de la map, on consid�re que les tiles se situant au-del� de la map sont du m�me type.
        			 * Exemple : si le tile se trouve sur le bord gauche de la map, alors on consid�re que le tile � gauche est du m�me type (que le bloc continue au-del� de la map).
        			 */
        			boolean left = false;
        			boolean right = false;
        			boolean top = false;
        			boolean bottom = false;
        			boolean topLeftCorner = false;
        			boolean topRightCorner = false;
        			boolean bottomLeftCorner = false;
        			boolean bottomRightCorner = false;
        			
        			// Si le tile en haut est du m�me type que le tile courant ou si on se trouve en haut de la map
    				if (i == 0 || (i > 0 && ((Tile) this.tiles.get(i-1).get(j)).getClass().equals(currentTile.getClass())))
                    	top = true;
    				
        			// Si le tile � gauche est du m�me type que le tile courant ou si on se trouve � gauche de la map
        			if (j == 0 || (j > 0 && ((Tile) this.tiles.get(i).get(j-1)).getClass().equals(currentTile.getClass())))
        				left = true;
        			
        			// Si le tile en bas est du m�me type que le tile courant ou si on se trouve en bas de la map
            		if (i == this.height - 1 || (i < this.height - 1 && ((Tile)this.tiles.get(i+1).get(j)).getClass().equals(currentTile.getClass())))
            			bottom = true;
    				
    				// Si le tile � droite est du m�me type que le tile courant ou si on se trouve � droite de la map
					if (j == this.width - 1 || (j < this.width - 1 && ((Tile)this.tiles.get(i).get(j+1)).getClass().equals(currentTile.getClass())))
						right = true;
					
					// Si le tile en diagonale haut et gauche est du m�me type que le tile courant ou si le tile est au bord gauche et/ou haut
        			if ((i == 0) || (j == 0) || (i > 0 && j > 0 && ((Tile) this.tiles.get(i-1).get(j-1)).getClass().equals(currentTile.getClass())))
        				topLeftCorner = true;
        			
        			// Si le tile en diagonale haut et droite est du m�me type que le tile courant ou si le tile est au bord droit et/ou haut
					if ((i == 0) || (j == this.width - 1) || (i > 0 && j < this.width - 1 && ((Tile)this.tiles.get(i-1).get(j+1)).getClass().equals(currentTile.getClass())))
						topRightCorner = true;
					
					// Si le tile en diagonale bas et gauche est du m�me type que le tile courant ou si le tile est au bord gauche et/ou bas
        			if ((i == this.height - 1) || (j == 0) || (i < this.height - 1 && j > 0 && ((Tile) this.tiles.get(i+1).get(j-1)).getClass().equals(currentTile.getClass())))
        				bottomLeftCorner = true;
        			
        			// Si le tile en diagonale bas et droite est du m�me type que le tile courant ou si le tile est au bord droit et/ou bas
        			if ((i == this.height - 1) || (j == this.width - 1) || (i < this.height - 1 && j < this.width - 1 && ((Tile) this.tiles.get(i+1).get(j+1)).getClass().equals(currentTile.getClass())))
        				bottomRightCorner = true;
					
        			/* Selon le type du tile - Mountain ou Buttress - la fonction findPos permettant de fixer la position du tile dans le bloc ne prend pas les m�mes param�tres.
        			 * En effet, il est important pour conna�tre la position d'un tile Mountain dans le bloc d'en savoir plus sur les tiles autour de lui se situant dans sa diagonale,
        			 * alors que seuls les tiles de droite, gauche, haut et bas sont n�cessaires pour le type Buttress.
        			 * Cela d�pend directement de la mani�re dont les tiles ont �t� dessin�s.
        			 */
					if (currentTile instanceof Mountain)
						((Mountain) currentTile).findPos (top, left, bottom, right, topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner);
					else
						((Buttress) currentTile).findPos (top, left, bottom, right);
					
        		}
        		/* On incr�mente le compteur colonne. */
        		++j;
        	}
        	/* On incr�mente le compteur ligne. Et on remet le compteur colonne � 0. */
        	++i;
        	j = 0;
        }
	}
	
	/* ----- OPERATIONS SUR LES TOURS ----- */
	
	public boolean isThereATowerHere (int x, int y)
	{
		Point pointToTest = new Point (x, y);
		
		/* S'il y a une tour sauvegard�e � ces coordonn�es pr�cis�ment :
		 * il y a bien une tour, on retourne true.
		 */
		if (this.towers.get(pointToTest) != null)
			return true;
		
		return false;
	}
	
	public void removeTower (Tower tower)
	{
		for (int i = 0; i < 2; ++i)
		{
			int y = (int) (tower.getCoordInTiles().getY() + i);
			for (int j = 0; j < 2; ++j)
			{
				int x = (int) (tower.getCoordInTiles().getX() + j);
				this.towers.remove(new Point (x, y));
			}
		}
	}
	
	/* ----- OPERATIONS SUR LES AGENTS ----- */
	
	public void removeAgent (Agent agent)
	{
		this.agents.remove(agent);
	}

	public ArrayList<Projectile> getBullets() {
		return bullets;
	}
	
	public void addBullet (Projectile bullet)
	{
		this.bullets.add(bullet);
	}
	
	public void removeBullet (Projectile bullet)
	{
		this.bullets.remove(bullet);
	}
	
	public Base getRandomBase ()
	{
		Random r = new Random();
		int randomindex = r.nextInt(basis.size()-1);
		Collection<Base> values = basis.values();
		Object[] tempbasis = values.toArray();
		return (Base)tempbasis[randomindex];
	}
}
