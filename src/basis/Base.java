package basis;

import map.Zone;

public class Base {

	private static int time = 1000; // temps d'attente entre chaque cr�ation d'agent en milli�mes de secondes
	private int nbCreatableAgents = 0; // nb d�agents que la base peut cr�er (par intervalle r�gulier)
	private int nbHostedAgents = 0; // nb d�agents que la base h�berge actuellement
	private int capacity = 0; // nb d'agents que la base peut h�berger
	private Base target = null;
	private Zone zone = null;
	
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
	
	/*
	 * Constructor
	 */

	public Base(int capacity, Zone zone) {
		this.nbCreatableAgents = capacity/5;
		this.capacity = capacity;
		this.zone = zone;
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
}