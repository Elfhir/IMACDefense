package agents;

import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import map.Mapping;
import map.tiles.Tile;
import players.Player;
import towers.strategy.shooter.ShootableObject;
import towers.strategy.shooter.shootType.AttackingObject;
import towers.strategy.shooter.shootType.MovableBullet;
import basis.Base;

public class Agent extends MovableBullet implements ShootableObject {
	
	private ArrayList<Tile> path = null;
	private boolean frozen = false;
	private int frozentime = 0;

	/*
	 * Getters - Setters
	 */

	@Override
	public Point getCoordInTiles() {
		return coordInTiles;
	}

	@Override
	public Base getTarget() {
		return (Base) target;
	}
	
	public void setTarget(Base base) {
		this.target = base;
	}
	
	/*
	 * Methodes
	 */
	
	public void pathfinding (Mapping map)
	{
		if (path != null)
		{
			int indexOfCurrentTile = path.indexOf(map.getTiles().get((int)this.coordInTiles.getY()).get((int)this.coordInTiles.getX()));
			if (indexOfCurrentTile + 1 < path.size())
			{
				Tile nextTile = path.get(indexOfCurrentTile + 1);
				this.nextcoordInTiles = new Point ((int)nextTile.getCoordsInMap().getX(), (int)nextTile.getCoordsInMap().getY());
			}
		}
	}
	
	public void calculatePath (Mapping map)
	{
		ArrayList<ArrayList<Tile>> mapTiles = map.getTiles();
		
		Tile start = mapTiles.get((int)this.coordInTiles.getY()).get((int)this.coordInTiles.getX());
		Tile arrival = mapTiles.get((int)this.target.getCoordInTiles().getY()).get((int)this.target.getCoordInTiles().getX());
		
		AStarAlgorithm aStar = new AStarAlgorithm(start, arrival, map);
		
		path = aStar.getFinalPath();
	}
	
	@Override
	public void move(Mapping map)
	{
		
		if (this.coordInTiles == null || this.position2d == null || this.nextcoordInTiles == null)
		{
			return;
		}
		
		if (coordInTiles.equals(nextcoordInTiles))
		{
			this.pathfinding(map);
		}
		
		findDirection();
		
		super.move(map);
	}
	
	@Override
	public void attack ()
	{
		((Base) this.target).receiveAgent(this);
	}
	
	/*
	 * Constructors
	 */

	public Agent (Point coordInTiles, Player owner, int force)
	{
		super (coordInTiles, owner, force);
	}
	
	public Agent(Point coordInTiles, Base target, Player owner, int force, Mapping map) {		
		super (coordInTiles, target, owner, force, map);
		
		this.calculatePath(map);
		this.pathfinding(map);
		
		this.setHitBox();
	}
	
	/* ----- FROM SHOOTABLEOBJECT INTERFACE ----- */

	@Override
	public void destruct(Mapping map) {
		// TODO Auto-generated method stub
		map.removeAgent(this);
		this.force = 0;
	}

	@Override
	public boolean isDestructed() {
		// TODO Auto-generated method stub
		return (this.force <= 0);
	}

	@Override
	public void beAttacked(AttackingObject object) {
		this.force -= object.getPower ();
	}
	
	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}

	@Override
	public Player getOwner() {
		return this.ownerPlayer;
	}

	@Override
	public int getLife() {
		// TODO Auto-generated method stub
		return force;
	}

	@Override
	public void setLife(int life) {
		// TODO Auto-generated method stub
		this.force = life;
	}
	
	@Override
	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}

	@Override
	public boolean isFrozen() {
		return frozen;
	}
	
	@Override
	public int getTotalFrozenTime() {
		return frozentime;
	}
	
	@Override
	public void setTotalFrozenTime(int frozentime) {
		this.frozentime = frozentime;
	}

	@Override
	public Rectangle2D.Double getHitBox() {
		return hitbox;
	}
}
