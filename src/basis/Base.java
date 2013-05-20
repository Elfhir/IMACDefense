package basis;

import java.awt.Point;

import players.Player;

public class Base {

	private static int time = 1000; // temps d'attente entre chaque cr�ation d'agent en milli�mes de secondes
	private int nbCreatableAgents = 0; // nb d�agents que la base peut cr�er (par intervalle r�gulier)
	private int nbHostedAgents = 0; // nb d�agents que la base h�berge actuellement
	private int capacity = 0; // nb d'agents que la base peut h�berger
	private Base target = null;
	private Player ownerPlayer = null; // Joueur propri�taire
	
	/*
	 * Getter - setter
	 */
	
	public Player getOwnerPlayer() {
		return ownerPlayer;
	}

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
	
	/*
	 * Constructor
	 */

	public Base(int capacity, Player player) {
		this.nbCreatableAgents = capacity/5;
		this.capacity = capacity;
		this.ownerPlayer = player;
	}
	
	public Base() {
		super();
	}
	
	public Base (Player player)
	{
		this.ownerPlayer = player;
	}
	
	/*
	 * Methodes
	 */
	
	public Base ChooseTarget(Base base) { //choisir une cible
		
		return base;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}