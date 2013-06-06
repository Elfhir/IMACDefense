package towers.strategy.shooter.shootType;

import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import map.Mapping;
import map.tiles.Tile;
import players.Player;
import towers.strategy.shooter.AttackableObject;

public class MovableBullet implements AttackingObject {
	
	protected Point coordInTiles = null; // Position en tiles sur la map
	protected Point finalCoordInTiles; // Position destination finale
	
	protected static int width = 21;
	protected static int height = 21;

	protected Point nextcoordInTiles = null;
	protected Point position2d = null;
	
	protected AttackableObject target = null;
	protected Player ownerPlayer = null; // Joueur propriétaire
	
	protected Direction direction = Direction.right;
	
	protected int speed = 1;
	
	protected int force = 1;
	
	protected Rectangle2D.Double hitbox;
	
	protected enum Direction
	{
		top (21, 0),
		topleft (0, 0),
		left (0, 21),
		bottomleft (0, 42),
		bottom (21, 42),
		bottomright (42, 42),
		right (42, 21),
		topright (42, 0);
		
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
	
	public Player getOwnerPlayer() {
		return ownerPlayer;
	}
	
	public AttackableObject getTarget() {
		return target;
	}
	
	public void setTarget(AttackableObject target) {
		if (target != null && target.getOwner() != null && target.getOwner() != this.getOwnerPlayer())
		{
			this.target = target;
			this.finalCoordInTiles = target.getCoordInTiles();
		}
	}

	public Point getcoordInTiles() {
		return coordInTiles;
	}

	@Override
	public void destruct(Mapping map) {
	}

	@Override
	public int getPower() {
		return this.force;
	}
	
	/*
	 * Constructors
	 */
	
	public MovableBullet(Point coordInTiles, AttackableObject target, Player owner, int force, Mapping map) {
		this.coordInTiles = coordInTiles;
		this.position2d = new Point ((int)coordInTiles.getX()*Tile.getWidth(), (int)coordInTiles.getY()*Tile.getHeight());
		
		this.target = target;
		
		if (target != null)
		{
			this.nextcoordInTiles = target.getCoordInTiles();
			this.finalCoordInTiles = target.getCoordInTiles();
			this.findDirection();
		}
		
		this.ownerPlayer = owner;
		this.force = force;
	}

	public MovableBullet(Point coordInTiles, Player owner, int force) {
		this.coordInTiles = coordInTiles;
		this.position2d = new Point ((int)coordInTiles.getX()*Tile.getWidth(), (int)coordInTiles.getY()*Tile.getHeight());
		this.ownerPlayer = owner;
		this.force = force;
	}
	
	/*
	 * Getters - Setters
	 */
	
	public Point getPosition2d() {
		return position2d;
	}

	public int getForce() {
		return force;
	}
	
	public Point getFinalCoordInTiles() {
		return finalCoordInTiles;
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
	
	// Par défaut, un MovableBullet va tout droit.
	
	public void findDirection ()
	{
		int xDestination = (int)this.nextcoordInTiles.getX()*Tile.getWidth();
		int yDestination = (int)this.nextcoordInTiles.getY()*Tile.getHeight();
		int x = (int)this.position2d.getX();
		int y = (int)this.position2d.getY();
		
		if (xDestination > x && Math.abs(xDestination - x) > speed && yDestination > y && Math.abs(yDestination - y) > speed)
		{
			this.direction = Direction.bottomright;
		}
		else if (xDestination > x && Math.abs(xDestination - x) > speed && yDestination < y && Math.abs(yDestination - y) > speed)
		{
			this.direction = Direction.topright;
		}
		else if (xDestination < x && Math.abs(xDestination - x) > speed && yDestination > y && Math.abs(yDestination - y) > speed)
		{
			this.direction = Direction.bottomleft;
		}
		else if (xDestination < x && Math.abs(xDestination - x) > speed && yDestination < y && Math.abs(yDestination - y) > speed)
		{
			this.direction = Direction.topleft;
		}
		else if (xDestination < x && Math.abs(yDestination - y) <= speed)
		{
			this.direction = Direction.left;
		}
		else if (xDestination > x && Math.abs(yDestination - y) <= speed)
		{
			this.direction = Direction.right;
		}
		else if (yDestination > y && Math.abs(xDestination - x) <= speed)
		{
			this.direction = Direction.bottom;
		}
		else if (yDestination < y && Math.abs(xDestination - x) <= speed)
		{
			this.direction = Direction.top;
		}
	}
	
	public boolean arrivedAtDestination ()
	{
		if (this.finalCoordInTiles == null)
			return true;
		if (this.target == null)
			return false;
		
		if (this.target.getHitBox().intersects(this.hitbox))
			return true;
		
		boolean xTrue = false;
		boolean yTrue = false;
		int currentX = (int)this.position2d.getX();
		int currentY = (int)this.position2d.getY();
		int nextX = (int)this.finalCoordInTiles.getX()*Tile.getWidth();
		int nextY = (int)this.finalCoordInTiles.getY()*Tile.getHeight();
		
		if (currentX == nextX || Math.abs(nextX - currentX) <= speed)
		{
			xTrue = true;
		}
		if (currentY == nextY || Math.abs(nextY - currentY) <= speed)
		{
			yTrue = true;
		}
		
		return xTrue && yTrue;
	}
	
	public void attack ()
	{
		target.beAttacked(this);
	}
	
	public void move(Mapping map)
	{
		if (this.coordInTiles == null || this.position2d == null || this.nextcoordInTiles == null)
		{
			return;
		}
		
		int mapWidth = map.getWidth();
		int mapHeight = map.getHeight();
		
		int x = (int)this.position2d.getX();
		int y = (int)this.position2d.getY();
		
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
		
		this.coordInTiles = new Point ((int)this.position2d.getX()/20, (int)this.position2d.getY()/20);
		this.setHitBox ();
	}

	@Override
	public void setHitBox() {
		if (this.hitbox == null)
		{
			this.hitbox = new Rectangle2D.Double();
		}
		this.hitbox.setRect(position2d.getX(), position2d.getY(), width, height);
	}

	@Override
	public boolean isInHitBox(Point point) {
		if (hitbox.contains(point))
			return true;
		return false;
	}

	@Override
	public Rectangle2D.Double getHitBox() {
		return this.hitbox;
	}
}
