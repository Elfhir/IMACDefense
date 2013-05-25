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
import towers.towertypes.SubmachineGunTower;
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
	//private Point mousePosition = null;
	
	public PanMap (Mapping map, IHM ihm)
	{
		this.map = map;
		this.ihm = ihm;
	}
	
	@Override
	public void paintComponent (Graphics g)
	{
		if (this.map != null)
		{
			paintMap (g);
			paintIHM(g);
			//paintSelectedLine(g);
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
	
	// Dessine 1 tile
	private void paintTile(Tile tile, int coordX, int coordY, Graphics g){
		try {
			if (tile instanceof Buttress)
			{
				((Buttress)tile).setButtressColor();
			}
			BufferedImage img = ImageIO.read(new File(tile.getImageName()));
			g.drawImage(img.getSubimage(tile.getSubImageX (), tile.getSubImageY (), Tile.getWidth(), Tile.getHeight()), coordX*Tile.getWidth(), coordY*Tile.getHeight(), this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			 * Une tour peut prendre plus d'un tile, il faut donc vérifier qu'on affiche pas la tour plusieurs fois.
			 * Pour cela, on vérifie si elle a déjà été affichée en vérifiant si la même tour est présente dans le tile en haut et/ou dans le tile à gauche.
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
	
	// Dessine 1 tour
	private void paintTower (Tower tower, int coordX, int coordY, Graphics g)
	{
		try {
			BufferedImage img = ImageIO.read(new File(tower.getImageName()));
			g.drawImage(img, coordX*Tile.getWidth(), coordY*Tile.getHeight(), this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		try {
			BufferedImage img = ImageIO.read(new File(agent.getImageName()));
			g.drawImage(img.getSubimage(agent.getSubImageX(), agent.getSubImageY(), Agent.getWidth(), Agent.getHeight()), coordX/*Tile.getWidth()*/, coordY/*Tile.getHeight()*/, this);
			g.setColor(agent.getOwnerPlayer().getColor());
			g.fillRect(coordX, coordY - Tile.getHeight()/2, Tile.getWidth(), Tile.getHeight()/2);
			
			g.setColor(Color.white);
			String textToDraw = Integer.toString(agent.getForce());
			int textWidth = g.getFontMetrics().stringWidth(textToDraw);
			
			g.drawString(textToDraw, coordX + Tile.getWidth()/2 - textWidth/2, coordY);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			 * Voir explication de l'opération suivante dans la fonction paintAllTowers.
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

		/* On détermine la couleur de la base */
		Color color = Color.gray;
		Player owner = base.getOwner();
		if (owner != null && owner.getColor() != null)
		{
			color = owner.getColor();
		}
		
		g.setColor(color);
		
		/* On dessine un disque de cette couleur */
	    g.fillOval(coordX*Tile.getWidth(), coordY*Tile.getWidth(), base.getDiam()*Tile.getWidth(), base.getDiam()*Tile.getHeight());
	    
	    /* On sélectionne la couleur blanche */
	    g.setColor(Color.white);
	    
	    /* Si la base est sélectionnée par le joueur, alors on entoure la base de blanc. */
	    if (base.isSelected())
	    	g.drawOval(coordX*Tile.getWidth(), coordY*Tile.getWidth(), base.getDiam()*Tile.getWidth(), base.getDiam()*Tile.getHeight());
	    
	    String textToDraw = Integer.toString(base.getNbHostedAgents());
	    int textWidth = g.getFontMetrics().stringWidth(textToDraw);
	    int textHeight = g.getFontMetrics().getHeight();
	    int diamCircle = base.getDiam()*Tile.getWidth();
	    
	    // On calcule les coordonnées du texte afin qu'il soit centré sur le cercle.
	    int coordXString = coordX*Tile.getWidth() + diamCircle/2 - textWidth/2;
	    int coordYString = coordY*Tile.getHeight() + diamCircle/2 + textHeight/4;
	    
	    /* On écrit un texte en blanc sur le disque */
	    g.drawString(textToDraw, coordXString, coordYString);
	}
	
	/*public void paintSelectedLine (Graphics g)
	{
		if (mousePosition == null)
			return;
		if (Player.getLastObjectSelected() != null && Player.getLastObjectSelected() instanceof Base)
		{
			/*
			 *  On définit les coordonnées du centre de l'objet.
			 *  En effet, les coordonnées de l'objet sont toujours celles du coin supérieur gauche de l'image !
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

	/*public void setMousePosition(Point mousePosition) {
		this.mousePosition = mousePosition;
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
	
	/*public void paintConstructIHM (Graphics g)
	{
		paintTower (new BombTower (), map.getWidth() + 1, 10, g);
		paintTower (new FreezeTower (), map.getWidth() + 3, 10, g);
		paintTower (new LaserTower (), map.getWidth() + 5, 10, g);
		paintTower (new MedicalTower (), map.getWidth() + 2, 13, g);
		paintTower (new SubmachineGunTower (), map.getWidth() + 4, 12, g);
	}*/
}