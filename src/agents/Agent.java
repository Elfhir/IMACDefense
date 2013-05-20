package agents;

import java.awt.Point;
import java.io.File;
import java.util.Vector;

import players.Player;
import players.Player.Color;
import basis.Base;


public class Agent {

	//public static final int SQUARE_LENGTH = 20; //longueur d'un côté de carré sur la map, en pixels. A DEFINIR DANS LA MAP ET PAS ICI !

	private static int width = 20;
	private static int height = 20;
	
	private String imageName = System.getProperty("user.dir") + File.separator + "img" + File.separator + "agents" + File.separator + "agentred.png";
	
	private Point position2d = null; // Position 2D sur la map
	private Point nextPosition2d = null;
	private Base target = null;
	private Player ownerPlayer = null; // Joueur propriétaire
	
	private Direction direction = Direction.right;
	
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
		String filename = "agent" + ownerPlayer.getColor() + ".png";
		this.imageName =  workingdir + path + filename;
	}
	
	public Point moveAgent(int direction) {
		if(direction == 0) { // up
			//this.position2d.lastElement() += SQUARE_LENGTH; //l'ordonnée augmente d'un carré
		} //ne fonctionne pas car lastelement() ne peut pas être modifié. Il faudrait en fait utiliser la structure du vecteur mais je sais pas comment (où ?) on la définit
		if(direction == 1) { // right
			//this.position2d.firstElement() += SQUARE_LENGTH; //l'abscisse augment d'un carré
		}
		if(direction == 2) { // down
			//this.position2d.lastElement() -= SQUARE_LENGTH; //l'ordonnée diminue d'un carré
		}
		if(direction == 3) { // left
			//this.position2d.firstElement() -= SQUARE_LENGTH; //l'abscisse diminue d'un carré
		}
		return position2d;
	}
	
	/*
	 * Constructors
	 */
	public Agent() {
		super();
	}
	
	public Agent (Point point, Player owner)
	{
		this.position2d = point;
		this.ownerPlayer = owner;
		this.findImageName ();
	}
	
	public Agent(Point position2d, Base target, Player owner) {
		this.position2d = position2d;
		this.target = target;
		this.ownerPlayer = owner;
		this.findImageName ();
	}
}
