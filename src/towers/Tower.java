package towers;

import java.awt.Point;
import java.io.File;

import players.SelectableObject;

import map.Zone;
import towers.strategy.improvement.*;
import towers.strategy.shooter.*;

public class Tower implements SelectableObject {
	
	protected int width = 5;
	protected int height = 5;
	protected String imageName = System.getProperty("user.dir") + File.separator + "img" + File.separator + "icon.gif";
	
	protected ImproverInterface improver = new PowerImprovement(); // Ameliorateur
	protected ShooterInterface shooter = new Freeze(); // Tireur
	
	protected static int price = 0; // Coût de placement de la tour
	protected static int shootSpeed = 0; // Vitesse de tir
	protected static int shootPower = 0; // Puissance de tir
	protected static int shootRange = 0; // Portée de tir - rayon
	
	private int life = 10; // Vie de la tour - à 0, elle est détruite
	private Zone zone = null; // Zone à laquelle appartient la tour
	
	private boolean selected = false;
	private Point coordInTiles = null;
	
	/* ----- CONSTRUCTEURS ----- */
	
	public Tower ()
	{
		super ();
	}
	
	public Tower(Point coordInTiles) {
		super();
		this.coordInTiles = coordInTiles;
	}
	
	/* ----- GETTERS & SETTERS ----- */
	
	/* Life */
	
	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	/* ImproverInterface */
	
	public ImproverInterface getImprover() {
		return improver;
	}

	public void setImprover(ImproverInterface improver) {
		this.improver = improver;
	}

	/* ShooterInterface */
	
	public ShooterInterface getShooter() {
		return shooter;
	}

	public void setShooter(ShooterInterface shooter) {
		this.shooter = shooter;
	}

	/* Price */
	
	public int getPrice() {
		return price;
	}
	
	/* ShootSpeed */
	
	public int getShootSpeed() {
		return shootSpeed;
	}

	/* ShootPower */
	
	public int getShootPower() {
		return shootPower;
	}

	/* ShootRange */
	
	public int getShootRange() {
		return shootRange;
	}
	
	/* ImageName */
	
	public String getImageName() {
		return imageName;
	}
	
	/* Zone */
	
	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}
	
	/* ----- FROM SELECTABLEOBJECT INTERFACE ----- */

	@Override
	public int getObjectWidth() {
		return width;
	}

	@Override
	public int getObjectHeight() {
		return height;
	}

	@Override
	public void setCoordInTiles(Point coordInTiles) {
		this.coordInTiles = coordInTiles;
	}

	@Override
	public boolean isSelected() {
		// TODO Auto-generated method stub
		return selected;
	}

	@Override
	public void inverseSelected() {
		// TODO Auto-generated method stub
		selected = !selected;
	}

	@Override
	public void setSelected(boolean selected) {
		// TODO Auto-generated method stub
		this.selected = selected;
	}

	@Override
	public Point getCoordInTiles() {
		// TODO Auto-generated method stub
		return coordInTiles;
	}
}
