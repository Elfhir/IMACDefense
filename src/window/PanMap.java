package window;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import players.Player;
import basis.Base;

import agents.Agent;

import towers.Tower;
import towers.towertypes.BombTower;
import towers.towertypes.FreezeTower;
import towers.towertypes.LaserTower;
import towers.towertypes.MedicalTower;
import map.Mapping;
import map.tiles.Buttress;
import map.tiles.Tile;
  
public class PanMap extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Mapping map = null;
	private IHM ihm = null;
	
	/*
	 * Chargement des images
	 */
	private BufferedImage
	
		/*
		 * Tiles
		 */
		fieldAndMountainTileImage = null,
		buttressTileImage = null,
		redButtressTileImage = null,
		greenButtressTileImage = null,
		yellowButtressTileImage = null,
		blueButtressTileImage = null,
		
		/*
		 * Agents
		 */
		redAgentImage = null,
		greenAgentImage = null,
		yellowAgentImage = null,
		blueAgentImage = null,
		
		/*
		 * Towers
		 */
		bombTowerImage = null,
		freezeTowerImage = null,
		laserTowerImage = null,
		medicalTowerImage = null,
		submachineGunTowerImage = null;
	
	public PanMap (Mapping map, IHM ihm) throws IOException
	{
		this.map = map;
		this.ihm = ihm;
		
		String filepath = System.getProperty("user.dir") + File.separator + "img" + File.separator;
		
		this.fieldAndMountainTileImage = ImageIO.read(new File(filepath + "tileset" + File.separator + "terrain.png"));
		this.buttressTileImage = ImageIO.read(new File(filepath + "tileset" + File.separator + "buttress.png"));
		this.redButtressTileImage = ImageIO.read(new File(filepath + "tileset" + File.separator + "buttressred.png"));
		this.greenButtressTileImage = ImageIO.read(new File(filepath + "tileset" + File.separator + "buttressgreen.png"));
		this.yellowButtressTileImage = ImageIO.read(new File(filepath + "tileset" + File.separator + "buttressyellow.png"));
		this.blueButtressTileImage = ImageIO.read(new File(filepath + "tileset" + File.separator + "buttressblue.png"));

		this.redAgentImage = ImageIO.read(new File(filepath + "agents" + File.separator + "agentred.png"));
		this.greenAgentImage = ImageIO.read(new File(filepath + "agents" + File.separator + "agentgreen.png"));
		this.yellowAgentImage = ImageIO.read(new File(filepath + "agents" + File.separator + "agentyellow.png"));
		this.blueAgentImage = ImageIO.read(new File(filepath + "agents" + File.separator + "agentblue.png"));
		
		this.bombTowerImage = ImageIO.read(new File(filepath + "buildings" + File.separator + "bombtower.png"));
		this.freezeTowerImage = ImageIO.read(new File(filepath + "buildings" + File.separator + "freezetower.png"));
		this.laserTowerImage = ImageIO.read(new File(filepath + "buildings" + File.separator + "lasertower.png"));
		this.medicalTowerImage = ImageIO.read(new File(filepath + "buildings" + File.separator + "medicaltower.png"));
		this.submachineGunTowerImage = ImageIO.read(new File(filepath + "buildings" + File.separator + "submachineguntower.png"));
	}
	
	@Override
	public void paintComponent (Graphics g)
	{
		if (this.map != null)
		{
			paintMap (g);
			paintIHM(g);
		}
	}
	
	// Dessine toute la map
	private void paintMap (Graphics g)
	{
		paintAllTiles (g);
		paintAllTowers(g);
		paintAllBasis (g);
		paintAllAgents (g);
	}
	
	private void paintIHM (Graphics g)
	{
		paintConstructIHM(g);
	}
	
	// Dessine tous les tiles
	private void paintAllTiles (Graphics g)
	{
		ArrayList<ArrayList<Tile>> mapTiles = this.map.getTiles();
		Iterator<ArrayList<Tile>> line = mapTiles.iterator();
		int i = 0; int j = 0;
		while(line.hasNext())
        {
			ArrayList<Tile> currentLine = (ArrayList<Tile>) line.next();
        	Iterator<Tile> column = currentLine.iterator();
        	while(column.hasNext())
        	{
        		Tile currentTile = (Tile) column.next();
        		paintTile(currentTile, j, i, g);
        		++j;
        	}
        	++i;
        	j = 0;
        }
	}
	
	// Dessine un tile
	private void paintTile(Tile tile, int coordX, int coordY, Graphics g){
		BufferedImage img = null;
		if (tile instanceof Buttress)
		{
			if (((Buttress)tile).getZone().getOwner() == null)
			{
				img = this.buttressTileImage;
			}
			else
			{
				switch (((Buttress)tile).getZone().getOwner().getColorName())
				{
					case blue:
					{
						img = this.blueButtressTileImage;
						break;
					}
					case green:
					{
						img = this.greenButtressTileImage;
						break;
					}
					case red:
					{
						img = this.redButtressTileImage;
						break;
					}
					case yellow:
					{
						img = this.yellowButtressTileImage;
						break;
					}
					default:
					{
						img = this.buttressTileImage;
						break;
					}
				}
			}
		}
		else
			img = this.fieldAndMountainTileImage;
		
		img = img.getSubimage(tile.getSubImageX(), tile.getSubImageY(), Tile.getWidth(), Tile.getHeight());

		if (img != null)
		{
			g.drawImage(img, coordX*Tile.getWidth(), coordY*Tile.getHeight(), this);
		}
	}
	
	// Dessine toutes les tours
	private void paintAllTowers (Graphics g)
	{
		Hashtable<Point, Tower> mapTowers = this.map.getTowers();
		Set<Point> set = mapTowers.keySet();
		Iterator<Point> it = set.iterator();

		while (it.hasNext())
		{
			Point currentPoint = it.next();
			Tower currentTower = mapTowers.get(currentPoint);
			
			/* 
			 * Une tour peut prendre plus d'un tile, il faut donc v�rifier qu'on affiche pas la tour plusieurs fois.
			 * Pour cela, on v�rifie si elle a d�j� �t� affich�e en v�rifiant si la m�me tour est pr�sente dans le tile en haut et/ou dans le tile � gauche.
			 */
			int x = (int) currentPoint.getX();
			int y = (int) currentPoint.getY();
			
			Tower left = mapTowers.get(new Point(x-1, y));
			Tower top = mapTowers.get(new Point(x, y-1));
			
			if (!(left != null && left.equals(currentTower)) && !(top != null && top.equals(currentTower)))
			{
				paintTower (currentTower, x, y, g);
			}
		}
	}
	
	// Dessine une tour
	private void paintTower (Tower tower, int coordX, int coordY, Graphics g)
	{
		BufferedImage img = null;
		if (tower instanceof BombTower)
			img = this.bombTowerImage;
		else if (tower instanceof FreezeTower)
			img = this.freezeTowerImage;
		else if (tower instanceof LaserTower)
			img = this.laserTowerImage;
		else if (tower instanceof MedicalTower)
			img = this.medicalTowerImage;
		else
			img = this.submachineGunTowerImage;
		
		if (img != null)
			g.drawImage(img, coordX*Tile.getWidth(), coordY*Tile.getHeight(), this);
	}
	
	public void paintAllAgents (Graphics g)
	{
		ArrayList<Agent> agents = this.map.getAgents();
		Iterator<Agent> it = agents.iterator();
		
		while (it.hasNext())
		{
			Agent currentAgent = it.next();
			if (currentAgent != null)
				paintAgent (currentAgent, g);
		}
	}
	
	public void paintAgent (Agent agent, Graphics g)
	{
		if (agent == null)
			return;
		
		if (agent.getPosition2d() == null)
			return;
		
		int coordX = (int)agent.getPosition2d().getX();
		int coordY = (int)agent.getPosition2d().getY();
		BufferedImage img = null;
		switch (agent.getOwnerPlayer().getColorName())
		{
			case blue:
			{
				img = blueAgentImage;
				break;
			}
			case green:
			{
				img = greenAgentImage;
				break;
			}
			case yellow:
			{
				img = yellowAgentImage;
				break;
			}
			default:
			{
				img = redAgentImage;
				break;
			}
		}
		
		img = img.getSubimage(agent.getSubImageX(), agent.getSubImageY(), Tile.getWidth(), Tile.getHeight());
		if (img != null)
			g.drawImage(img, coordX, coordY, this);
		g.setColor(agent.getOwnerPlayer().getColor());
		g.fillRect(coordX, coordY - Tile.getHeight()/2, Tile.getWidth(), Tile.getHeight()/2);
		
		g.setColor(Color.white);
		String textToDraw = Integer.toString(agent.getForce());
		int textWidth = g.getFontMetrics().stringWidth(textToDraw);
		
		g.drawString(textToDraw, coordX + Tile.getWidth()/2 - textWidth/2, coordY);
	}
	
	// Dessine toutes les tours
	private void paintAllBasis (Graphics g)
	{
		Hashtable<Point, Base> mapBasis = this.map.getBasis();
		Set<Point> set = mapBasis.keySet();
		Iterator<Point> it = set.iterator();

		while (it.hasNext())
		{
			Point currentPoint = it.next();
			Base currentBase = mapBasis.get(currentPoint);
			
			/*
			 * Tout comme une tour, une base peut prendre plusieurs tiles.
			 * Voir explication de l'op�ration suivante dans la fonction paintAllTowers.
			 */
			
			int x = (int) currentPoint.getX();
			int y = (int) currentPoint.getY();
			
			Base left = mapBasis.get(new Point(x-1, y));
			Base top = mapBasis.get(new Point(x, y-1));
			
			if (!(left != null && left.equals(currentBase)) && !(top != null && top.equals(currentBase)))
			{
				paintBase (currentBase, x, y, g);
			}
		}
	}
	
	public void paintBase (Base base, int coordX, int coordY, Graphics g)
	{
		if (base == null)
			return;

		/* On d�termine la couleur de la base */
		Color color = Color.gray;
		Player owner = base.getOwner();
		if (owner != null && owner.getColor() != null)
		{
			color = owner.getColor();
		}
		
		g.setColor(color);
		
		/* On dessine un disque de cette couleur */
	    g.fillOval(coordX*Tile.getWidth(), coordY*Tile.getWidth(), base.getDiam()*Tile.getWidth(), base.getDiam()*Tile.getHeight());
	    
	    /* On s�lectionne la couleur blanche */
	    g.setColor(Color.white);
	    
	    /* Si la base est s�lectionn�e par le joueur, alors on entoure la base de blanc. */
	    if (base.isSelected())
	    	g.drawOval(coordX*Tile.getWidth(), coordY*Tile.getWidth(), base.getDiam()*Tile.getWidth(), base.getDiam()*Tile.getHeight());
	    
	    String textToDraw = Integer.toString(base.getNbHostedAgents());
	    int textWidth = g.getFontMetrics().stringWidth(textToDraw);
	    int textHeight = g.getFontMetrics().getHeight();
	    int diamCircle = base.getDiam()*Tile.getWidth();
	    
	    /*
	     *  On calcule les coordonn�es du texte afin qu'il soit centr� sur le cercle.
	     */
	    int coordXString = coordX*Tile.getWidth() + diamCircle/2 - textWidth/2;
	    int coordYString = coordY*Tile.getHeight() + diamCircle/2 + textHeight/4;
	    
	    /* On �crit un texte en blanc sur le disque */
	    g.drawString(textToDraw, coordXString, coordYString);
	}
	
	/*public void paintSelectedLine (Graphics g)
	{
		if (mousePosition == null)
			return;
		if (Player.getLastObjectSelected() != null && Player.getLastObjectSelected() instanceof Base)
		{
			/*
			 *  On d�finit les coordonn�es du centre de l'objet.
			 *  En effet, les coordonn�es de l'objet sont toujours celles du coin sup�rieur gauche de l'image !
			 */
			/*SelectableObject objectselected = Player.getLastObjectSelected();
			Point position = objectselected.getCoordInTiles();
			int width = objectselected.getWidth();
			int height = objectselected.getHeight();
			double centerXinTiles = (width/2. + position.getX())*Tile.getWidth();
			double centerYinTiles = (height/2. + position.getY())*Tile.getHeight();
			
			g.setColor(Color.gray);
			g.drawLine((int)centerXinTiles, (int)centerYinTiles, (int)mousePosition.getX(), (int)mousePosition.getY());
		}
	}*/
	
	public void paintConstructIHM (Graphics g)
	{
		Hashtable<Point, Tower> ihmTowers = this.ihm.getTowers();
		Set<Point> set = ihmTowers.keySet();
		Iterator<Point> it = set.iterator();
		
		while (it.hasNext())
		{
			Point point = it.next();
			Tower current = ihmTowers.get(point);
			paintTower (current, (int)point.getX(), (int)point.getY(), g);
		}
	}
}