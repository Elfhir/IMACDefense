package window;

import agents.Agent;
import basis.Base;
import map.Mapping;
import map.tiles.Buttress;
import map.tiles.Tile;
import players.Player;
import players.types.HumanPlayer;
import towers.Tower;
import towers.strategy.shooter.shootType.*;
import towers.towertypes.BombTower;
import towers.towertypes.FreezeTower;
import towers.towertypes.LaserTower;
import towers.towertypes.MedicalTower;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
  
public class PanMap extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Mapping map = null;
	private IHMinGame ihm = null;
	private HumanPlayer player;
	
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
		submachineGunTowerImage = null,
		gunBulletImage = null,
		medicalBulletImage = null,
		freezeBulletImage = null;
	
	public PanMap (Mapping map, IHMinGame ihm, HumanPlayer player) throws IOException
	{
		this.map = map;
		this.ihm = ihm;
		
		String filepath = System.getProperty("user.dir") + File.separator + "img" + File.separator;
		
		this.fieldAndMountainTileImage = ImageIO.read(new File(filepath + "tileset" + File.separator + map.getMapType() + File.separator + "terrain.png"));
		this.buttressTileImage = ImageIO.read(new File(filepath + "tileset" + File.separator + map.getMapType() + File.separator + "buttress.png"));
		this.redButtressTileImage = ImageIO.read(new File(filepath + "tileset" + File.separator + map.getMapType() + File.separator + "buttressred.png"));
		this.greenButtressTileImage = ImageIO.read(new File(filepath + "tileset" + File.separator + map.getMapType() + File.separator + "buttressgreen.png"));
		this.yellowButtressTileImage = ImageIO.read(new File(filepath + "tileset" + File.separator + map.getMapType() + File.separator + "buttressyellow.png"));
		this.blueButtressTileImage = ImageIO.read(new File(filepath + "tileset" + File.separator + map.getMapType() + File.separator + "buttressblue.png"));

		this.redAgentImage = ImageIO.read(new File(filepath + "agents" + File.separator + "agentred.png"));
		this.greenAgentImage = ImageIO.read(new File(filepath + "agents" + File.separator + "agentgreen.png"));
		this.yellowAgentImage = ImageIO.read(new File(filepath + "agents" + File.separator + "agentyellow.png"));
		this.blueAgentImage = ImageIO.read(new File(filepath + "agents" + File.separator + "agentblue.png"));
		
		this.bombTowerImage = ImageIO.read(new File(filepath + "buildings" + File.separator + "bombtower.png"));
		this.freezeTowerImage = ImageIO.read(new File(filepath + "buildings" + File.separator + "freezetower.png"));
		this.laserTowerImage = ImageIO.read(new File(filepath + "buildings" + File.separator + "lasertower.png"));
		this.medicalTowerImage = ImageIO.read(new File(filepath + "buildings" + File.separator + "medicaltower.png"));
		this.submachineGunTowerImage = ImageIO.read(new File(filepath + "buildings" + File.separator + "submachineguntower.png"));
		
		this.gunBulletImage = ImageIO.read(new File(filepath + "shooting" + File.separator + "gunbullet.png"));
		this.medicalBulletImage = ImageIO.read(new File(filepath + "shooting" + File.separator + "medicalbullet.png"));
		this.freezeBulletImage = ImageIO.read(new File(filepath + "shooting" + File.separator + "freezebullet.png"));
	
		this.player = player;
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
		paintAllBullets (g);
	}
	
	private void paintIHM (Graphics g)
	{
		paintPlayerInformationsIHM (g);
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
			 * Une tour peut prendre plus d'un tile, il faut donc vï¿½rifier qu'on affiche pas la tour plusieurs fois.
			 * Pour cela, on vï¿½rifie si elle a dï¿½jï¿½ ï¿½tï¿½ affichï¿½e en vï¿½rifiant si la mï¿½me tour est prï¿½sente dans le tile en haut et/ou dans le tile ï¿½ gauche.
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
		
		// Hitbox pour le test
		/*if (tower.getHitBox() != null)
		{
			g.setColor(Color.white);
			g.drawRect((int)tower.getHitBox().getX(), (int)tower.getHitBox().getY(), tower.getObjectWidth()*Tile.getWidth(), tower.getObjectHeight()*Tile.getHeight());
		}*/
	}
	
	private void paintAllAgents (Graphics g)
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
	
	private void paintAgent (Agent agent, Graphics g)
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
		
		img = img.getSubimage(agent.getSubImageX(), agent.getSubImageY(), MovableBullet.getWidth(), MovableBullet.getHeight());
		if (img != null)
			g.drawImage(img, coordX, coordY, this);
		g.setColor(agent.getOwnerPlayer().getColor());
		g.fillRect(coordX, coordY - MovableBullet.getHeight()/2, MovableBullet.getWidth(), MovableBullet.getHeight()/2);
		
		g.setColor(Color.white);
		String textToDraw = Integer.toString(agent.getForce());
		int textWidth = g.getFontMetrics().stringWidth(textToDraw);
		
		g.drawString(textToDraw, coordX + MovableBullet.getWidth()/2 - textWidth/2, coordY);
		
		// HitBox pour le test
		//g.drawRect((int)agent.getHitBox().getX(), (int)agent.getHitBox().getY(), 21, 21);
	}
	
	private void paintAllBullets (Graphics g)
	{
		ArrayList<Projectile> bullets = this.map.getBullets();
		Iterator<Projectile> it = bullets.iterator();
		
		while (it.hasNext())
		{
			Projectile currentBullet = it.next();
			if (currentBullet != null)
			{
				if (currentBullet instanceof LaserRay)
				{
					paintLaserRay ((LaserRay)currentBullet, g);
				}
				else
				{
					paintBullet (currentBullet, g);
				}
			}
		}
	}
	
	private void paintBullet (Projectile bullet, Graphics g)
	{
		if (bullet == null)
			return;
		
		if (bullet.getPosition2d() == null)
			return;
		
		int coordX = (int)bullet.getPosition2d().getX();
		int coordY = (int)bullet.getPosition2d().getY();
		BufferedImage img = null;
		
		if (bullet instanceof GunBullet)
			img = gunBulletImage;
		else if (bullet instanceof MedicalBullet)
			img = medicalBulletImage;
		else if (bullet instanceof FreezeBullet)
			img = freezeBulletImage;
		
		if (bullet instanceof GunBullet || bullet instanceof MedicalBullet)
			img = img.getSubimage(bullet.getSubImageX(), bullet.getSubImageY(), MovableBullet.getWidth(), MovableBullet.getHeight());
		
		if (img != null)
			g.drawImage(img, coordX, coordY, this);
		
		// HitBox pour le test
		/*if (bullet.getHitBox() != null)
			g.drawRect((int)bullet.getHitBox().getX(), (int)bullet.getHitBox().getY(), 21, 21);*/
	}
	
	private void paintLaserRay (LaserRay laserray, Graphics g)
	{
		if (laserray == null)
			return;
		
		if (laserray.getPosition2d() == null)
			return;
		
		int firstCoordX = (int)laserray.getInitialPoint().getX();
		int firstCoordY = (int)laserray.getInitialPoint().getY();
		
		int coordX = (int)laserray.getPosition2d().getX();
		int coordY = (int)laserray.getPosition2d().getY();

		g.setColor(Color.red);
		g.drawLine(firstCoordX*Tile.getWidth(), firstCoordY*Tile.getHeight(), coordX, coordY);
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
			 * Voir explication de l'opï¿½ration suivante dans la fonction paintAllTowers.
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

    public void paintPlayerInformationsIHM (Graphics g)
    {
        if (player ==null)
            return;
        //les coordonnées en lesquelles le rectangle de couleur s'affiche resteront fixes
        int x=410;
        int y=5;

        //choix du type de police et son format
        Font fonte = new Font("Monospaced", Font.BOLD, 15);
        g.setFont(fonte);

        //on récupère la couleur du joueur
        g.setColor(player.getColor());
        // rempli un rectangle avec la couleur courante, en l'occurence celle du joueur
        g.fillRect(x, y, 130, 30);

        // permet d'afficher le nom du joueur en dessous du rectangle de couleur
        g.drawString(player.getName(), 460, 50);
        
        String moneyText = "Argent:" + player.getMoney();

        g.setColor(Color.lightGray);
        g.fillRect(430, 70 - g.getFontMetrics().getHeight()/2, g.getFontMetrics().stringWidth(moneyText), g.getFontMetrics().getHeight());
        g.setColor(player.getColor());
        g.drawString(moneyText, 430, 70);
    }
    
	private void paintBase (Base base, int coordX, int coordY, Graphics g)
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
	    
	    /*
	     *  On calcule les coordonnï¿½es du texte afin qu'il soit centrï¿½ sur le cercle.
	     */
	    int coordXString = coordX*Tile.getWidth() + diamCircle/2 - textWidth/2;
	    int coordYString = coordY*Tile.getHeight() + diamCircle/2 + textHeight/4;
	    
	    /* On écrit un texte en blanc sur le disque */
	    g.drawString(textToDraw, coordXString, coordYString);
	}
	
	private void paintConstructIHM (Graphics g)
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