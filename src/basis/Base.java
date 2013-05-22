package basis;

import java.awt.Point;
import java.util.LinkedList;

import agents.Agent;
import map.Zone;

public class Base {

	private static int time = 1000; // temps d'attente entre chaque cr�ation d'agent en milli�mes de secondes
	private int nbCreatableAgents = 0; // nb d�agents que la base peut cr�er (par intervalle r�gulier)
	private int nbHostedAgents = 0; // nb d�agents que la base h�berge actuellement
	private int capacity = 0; // nb d'agents que la base peut h�berger
	private Base target = null;
	private Zone zone = null;
	private int diam = 0;
	
	private Point position2d = null;
	
	private LinkedList<Agent> hostedAgents = new LinkedList<Agent>();
	
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
	
	/*
	 * Constructor
	 */

	public Base(int capacity, Zone zone, Point point) {
		this.nbCreatableAgents = capacity;
		this.capacity = capacity*4;
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
		/* On ne cr�e pas d'agents si la base n'appartient � personne. */
		if (this.zone == null || this.zone.getOwner() == null)
			return null;
		
		/* On ne cr�e pas d'agents tant qu'il n'y a pas de cible choisie. */
		/*if (this.target == null)
			return null;*/
		
		Agent agent = new Agent (this.position2d, this.target, this.zone.getOwner());
		return agent;
	}
}