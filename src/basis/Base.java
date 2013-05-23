package basis;

import java.awt.Point;
import java.util.LinkedList;

import players.SelectableObject;

import agents.Agent;
import map.Zone;

public class Base implements SelectableObject {

	private static int time = 1000; // temps d'attente entre chaque création d'agent en millièmes de secondes
	private int nbCreatableAgents = 0; // nb d’agents que la base peut créer (par intervalle régulier)
	private int nbHostedAgents = 0; // nb d’agents que la base héberge actuellement
	private int capacity = 0; // nb d'agents que la base peut héberger
	private Base target = null;
	private Zone zone = null;
	private int diam = 0; // diamètre du cercle représentant la base en nb de tiles
	
	private Point position2d = null;
	
	private boolean selected = false;
	
	/*
	 * Getter - setter
	 */

	public int getTime() {
		return time;
	}
	
	public int getNbCreatableAgents() {
		return nbCreatableAgents;
	}
	
	public void setNbCreatableAgents(int nbCreatableAgents) {
		this.nbCreatableAgents = nbCreatableAgents;
	}
	
	public int getNbHostedAgents() {
		return nbHostedAgents;
	}
	
	public void setNbHostedAgents(int nbHostedAgents) {
		this.nbHostedAgents = nbHostedAgents;
	}
	
	public int getCapacity() {
		return capacity;
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}
	
	public int getDiam() {
		return diam;
	}
	
	public void setTarget(Base target) {
		this.target = target;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	/*
	 * Constructor
	 */

	public Base(int capacity, Zone zone, Point point) {
		this.nbCreatableAgents = capacity/2;
		this.nbHostedAgents = capacity;
		this.capacity = capacity;
		this.diam = capacity/5;
		this.zone = zone;
		this.position2d = point;
	}
	
	public Base() {
		super();
	}
	
	/*
	 * Methodes
	 */
	
	public Base chooseTarget(Base base) { //choisir une cible
		
		return base;
	}
	
	public synchronized Agent createAgent ()
	{
		/* On ne crée pas d'agents si la base n'appartient à personne. */
		if (this.zone == null || this.zone.getOwner() == null)
			return null;
		
		/* On ne crée pas d'agents tant qu'il n'y a pas de cible choisie. */
		if (this.target == null)
			return null;
		
		Agent agent = new Agent (this.position2d, this.target, this.zone.getOwner());
		this.nbHostedAgents--;
		this.nbCreatableAgents = (int)this.nbHostedAgents/2;
		return agent;
	}

	public void inverseSelected() {
		// TODO Auto-generated method stub
		this.selected = !this.selected;
	}
}