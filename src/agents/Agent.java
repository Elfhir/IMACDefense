package agents;

import java.util.Vector;
import basis.Base;


public class Agent {

	public static final int SQUARE_LENGTH = 20; //longueur d'un côté de carré sur la map, en pixels. A DEFINIR DANS LA MAP ET PAS ICI !

	protected int id = 0;
	protected Vector<Object> position2d = new Vector<Object> (); // Position 2D sur la map
	protected Base target = new Base();
	// private Player ownerPlayer; // Joueur propriétaire
	
	/*
	 * Getters - Setters
	 */
	
	public int getId() {
		return id;
	}
	
	public Vector<Object> getPosition2D() {
		return position2d;
	}
	
	public void setPosition2d(Vector<Object> position2d) {
		this.position2d = position2d;
	}
	
	public Base getTarget() {
		return target;
	}
	
	public void setTarget(Base base) {
		this.target = base;
	}
	
	/*
	 * Methodes
	 */
	
	public Vector<Object> moveAgent(int direction) {
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
	
	public Agent(Vector<Object> position2d, Base target) {
		this.position2d = position2d;
		this.target = target;
		this.id = this.id + 1;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
