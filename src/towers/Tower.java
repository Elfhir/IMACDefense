package towers;

import java.io.File;

import map.Zone;
import towers.strategy.improvement.*;
import towers.strategy.shooter.*;

public class Tower {
	
	protected int width = 5;
	protected int height = 5;
	protected String imageName = System.getProperty("user.dir") + File.separator + "img" + File.separator + "icon.gif";
	
	protected ImproverInterface improver = new PowerImprovement(); // Ameliorateur
	protected ShooterInterface shooter = new Freeze(); // Tireur
	
	protected static int price = 0; // Coût de placement de la tour
	protected static int shootSpeed = 0; // Vitesse de tir
	protected static int shootPower = 0; // Puissance de tir
	protected static int shootRange = 0; // Portée de tir - rayon
	
	private int x = 0;
	private int y = 0; // Position 2D sur la map
	private int life = 10; // Vie de la tour - à 0, elle est détruite
	private Zone zone = null; // Zone à laquelle appartient la tour
	
	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public ImproverInterface getImprover() {
		return improver;
	}

	public void setImprover(ImproverInterface improver) {
		this.improver = improver;
	}

	public ShooterInterface getShooter() {
		return shooter;
	}

	public void setShooter(ShooterInterface shooter) {
		this.shooter = shooter;
	}

	public int getPrice() {
		return price;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getShootSpeed() {
		return shootSpeed;
	}

	public int getShootPower() {
		return shootPower;
	}

	public int getShootRange() {
		return shootRange;
	}
	
	public Tower(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public String getImageName() {
		return imageName;
	}
}
