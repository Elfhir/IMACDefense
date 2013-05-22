package agents;

import java.awt.Point;
import java.io.File;

import map.Mapping;
import map.tiles.Tile;
import players.Player;
import basis.Base;

public class Agent {

	private static int width = 20;
	private static int height = 20;
	
	private String imageName = System.getProperty("user.dir") + File.separator + "img" + File.separator + "agents" + File.separator + "agentred.png";
	
	private Point coordInTiles = null; // Position 2D sur la map
	private Point nextcoordInTiles = new Point (9, 9);
	private Point position2d = null;
	
	private Base target = null;
	private Player ownerPlayer = null; // Joueur propriétaire
	
	private Direction direction = Direction.right;
	
	boolean hostedInBase = false;
	
	int speed = 5;
	
	private enum Direction
	{
		top (40, 0),
		topleft (0, 0),
		left (0, 40),
		bottomleft (0, 80),
		bottom (40, 80),
		bottomright (80, 80),
		right (80, 40),
		topright (80, 0);
		
		int subX = 0;
		int subY = 0;
		
		private Direction (int subX, int subY)
		{
			this.subX = subX;
			this.subY = subY;
		}

		public int getSubX() {
			return subX;
		}

		public int getSubY() {
			return subY;
		}
	}
	
	/*
	 * Getters - Setters
	 */

	public Point getcoordInTiles() {
		return coordInTiles;
	}
	
	public Point getPosition2d() {
		return position2d;
	}

	public Base getTarget() {
		return target;
	}
	
	public void setTarget(Base base) {
		this.target = base;
	}
	
	public String getImageName() {
		return imageName;
	}
	
	public int getSubImageX()
	{
		return this.direction.getSubX();
	}
	
	public int getSubImageY()
	{
		return this.direction.getSubY();
	}
	
	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}
	
	/*
	 * Methodes
	 */

	public void findImageName ()
	{
		if (this.ownerPlayer == null)
			return;
		
		String workingdir = System.getProperty("user.dir");
		String path = File.separator + "img" + File.separator + "agents" + File.separator;
		String filename = "agent" + ownerPlayer.getColorName() + ".png";
		this.imageName =  workingdir + path + filename;
	}
	
	public void pathfinding ()
	{
		int xDestination = (int)this.nextcoordInTiles.getX()*Tile.getWidth();
		int yDestination = (int)this.nextcoordInTiles.getY()*Tile.getHeight();
		int x = (int)this.position2d.getX();
		int y = (int)this.position2d.getY();
		
		if (xDestination > x && yDestination > y)
		{
			this.direction = Direction.bottomright;
		}
		else if (xDestination > x && yDestination < y)
		{
			this.direction = Direction.topright;
		}
		else if (xDestination < x && yDestination > y)
		{
			this.direction = Direction.bottomleft;
		}
		else if (xDestination < x && yDestination < y)
		{
			this.direction = Direction.topleft;
		}
		else if (xDestination < x)
		{
			this.direction = Direction.left;
		}
		else if (xDestination > x)
		{
			this.direction = Direction.right;
		}
		else if (yDestination > y)
		{
			this.direction = Direction.bottom;
		}
		else if (yDestination < y)
		{
			this.direction = Direction.top;
		}
	}
	
	public void move(Mapping map) {
		if (this.hostedInBase)
			return;
		
		if (this.position2d == null || this.nextcoordInTiles == null)
			return;
		
		if (this.position2d.getX() == this.nextcoordInTiles.getX()*Tile.getWidth() && this.position2d.getY() == this.nextcoordInTiles.getY()*Tile.getHeight())
			return;
		
		pathfinding();
		
		int mapWidth = map.getWidth();
		int mapHeight = map.getHeight();
		
		int x = (int)this.position2d.getX();
		int y = (int)this.position2d.getY();
		
		//System.out.println("x = " + x + ", y = " + y);
		
		switch (this.direction)
		{
			case bottom:
			{
				if (y+speed < mapHeight*Tile.getHeight())
					this.position2d = new Point(x, y+speed);
				else
					this.position2d = new Point(x, mapHeight*Tile.getHeight());
				break;
			}
			case bottomleft:
			{
				if (x-speed >= 0 && y+speed < mapHeight*Tile.getHeight())
					this.position2d = new Point(x-speed, y+speed);
				if (x-speed < 0)
				{
					this.position2d = new Point(0, y+speed);
					x = 0;
				}
				if (y+speed >= mapHeight*Tile.getHeight())
				{
					this.position2d = new Point(x, mapHeight*Tile.getHeight());
				}
				break;
			}
			case bottomright:
			{
				if (x+speed < mapWidth*Tile.getWidth() && y+speed < mapHeight*Tile.getHeight())
					this.position2d = new Point(x+speed, y+speed);
				if (x+speed >= mapWidth*Tile.getWidth())
				{
					this.position2d = new Point(mapWidth*Tile.getWidth(), y);
					x = mapWidth*Tile.getWidth();
				}
				if (y+speed >= mapHeight*Tile.getHeight())
				{
					this.position2d = new Point(x, mapHeight*Tile.getHeight());
				}
				break;
			}
			case left:
			{
				if (x-speed >= 0)
					this.position2d = new Point(x-speed, y);
				else
					this.position2d = new Point(0, y);
				break;
			}
			case right:
			{
				if (x+speed < mapWidth*Tile.getWidth())
					this.position2d = new Point(x+speed, y);
				else
					this.position2d = new Point(mapWidth*Tile.getWidth(), y);
				break;
			}
			case top:
			{
				if (y-speed >= 0)
					this.position2d = new Point(x, y-speed);
				else
					this.position2d = new Point(x, 0);
				break;
			}
			case topleft:
			{
				if (x-speed >= 0 && y-speed >= 0)
					this.position2d = new Point(x-speed, y-speed);
				if (x-speed < 0)
				{
					this.position2d = new Point(0, y);
					x = 0;
				}
				if (y-speed < 0)
				{
					this.position2d = new Point(x, 0);
				}
				break;
			}
			case topright:
			{
				if (x+speed < mapWidth*Tile.getWidth() && y-speed >= 0)
					this.position2d = new Point(x+speed, y-speed);
				if (x+speed >= mapWidth*Tile.getWidth())
				{
					this.position2d = new Point(mapWidth*Tile.getWidth(), y);
					x = mapWidth*Tile.getWidth();
				}
				if (y-speed < 0)
				{
					this.position2d = new Point(x, 0);
				}
				break;
			}
			default:
			{
				break;
			}
		}
	}
	
	/*
	 * Constructors
	 */
	public Agent() {
		super();
	}
	
	public Agent (Point coordInTiles, Player owner)
	{
		this.coordInTiles = coordInTiles;
		this.position2d = new Point ((int)coordInTiles.getX()*Tile.getWidth(), (int)coordInTiles.getY()*Tile.getHeight());
		this.ownerPlayer = owner;
		this.findImageName ();
	}
	
	public Agent(Point coordInTiles, Base target, Player owner) {
		this.coordInTiles = coordInTiles;
		this.position2d = new Point ((int)coordInTiles.getX()*Tile.getWidth(), (int)coordInTiles.getY()*Tile.getHeight());
		this.target = target;
		this.ownerPlayer = owner;
		this.findImageName ();
	}
}
