package basis;

import java.awt.Point;
import java.util.ArrayList;

import players.Player;
import players.SelectableObject;

import agents.Agent;
import map.Mapping;
import map.Zone;

public class Base implements SelectableObject {

	private static int time = 1000; // temps d'attente entre chaque création d'agent en millièmes de secondes
	private int nbCreatableAgents = 0; // nb d’agents que la base peut créer (par intervalle régulier)
	private int nbHostedAgents = 0; // nb d’agents que la base héberge actuellement
	private int capacity = 0; // nb d'agents que la base peut héberger
	private Base target = null;
	private Zone zone = null;
	private Player owner = null; // Si la base n'est associée à aucune zone, le propriétaire est sauvegardé directement dans la base.
	private int diam = 0; // diamètre du cercle représentant la base en nb de tiles
	
	private Point coordInTiles = null;
	
	private boolean selected = false;
	
	/*
	 * Getter - setter
	 */

	public int getTime() {
		return time;
	}
	
	public Player getOwner() {
		if (zone != null)
			return zone.getOwner();
		return owner;
	}
	
	public void setOwner (Player owner)
	{
		if (zone != null)
		{
			zone.setOwner(owner);
			return;
		}
		this.owner = owner;
	}

	public int getNbHostedAgents() {
		return nbHostedAgents;
	}
	
	public void setNbHostedAgents(int nbHostedAgents) {
		this.nbHostedAgents = nbHostedAgents;
		this.nbCreatableAgents = nbHostedAgents/2;
	}
	
	public void decreaseNbHostedAgents(int nbToDecrease) {
		this.setNbHostedAgents(nbHostedAgents - nbToDecrease);
	}
	
	public int getCapacity() {
		return capacity;
	}

	public Point getCoordInTiles() {
		return coordInTiles;
	}

	/* Zone : zone à laquelle est associée la base */
	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}
	
	/* Diam : diamètre du cercle (en tiles) représentant la base */
	public int getDiam() {
		return diam;
	}
	
	/* Target : dernière cible de la base */
	public void setTarget(Base target) {
		this.target = target;
	}
	
	/* Selected : booléen indiquant si l'objet est sélectionné dans l'IHM ou non. */
	
	@Override
	public boolean isSelected() {
		return selected;
	}
	
	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	@Override
	public void inverseSelected() {
		// TODO Auto-generated method stub
		this.selected = !this.selected;
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
		this.coordInTiles = point;
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
	
	public Agent sendAgent (Mapping map)
	{
		/* La base ne peut envoyer des agents attaquer si elle n'appartient à personne. */
		if (this.getOwner() == null)
		{
			return null;
		}
		
		/* On n'envoie pas d'agents attaquer si la base n'héberge plus qu'un agent. */
		if (this.nbHostedAgents <= 1)
		{
			return null;
		}
		
		/* On n'envoie pas d'agents attaquer tant qu'il n'y a pas de cible choisie. */
		if (this.target == null)
		{
			return null;
		}
		
		Agent agent = new Agent (this.coordInTiles, this.target, this.getOwner(), this.nbCreatableAgents);
		this.nbHostedAgents -= this.nbCreatableAgents;
		this.nbCreatableAgents = (int)this.nbHostedAgents/2;
		if (agent != null)
			map.getAgents().add(agent);
		return agent;
	}
	
	public void beAttackedByAgent (Agent agent)
	{
		if (this.nbHostedAgents == agent.getForce())
		{
			this.setNbHostedAgents(0);
			this.setOwner(null);
		}
		else if (this.nbHostedAgents > agent.getForce())
		{
			this.decreaseNbHostedAgents(agent.getForce());
		}
		else if (this.nbHostedAgents < agent.getForce())
		{
			int newNbHostedAgents = agent.getForce () - this.nbHostedAgents;
			this.setOwner(agent.getOwnerPlayer());
			this.setNbHostedAgents(newNbHostedAgents);
		}
	}
	
	public void hostFriendAgent (Agent agent)
	{
		this.setNbHostedAgents(this.nbHostedAgents + agent.getForce());
	}
	
	public void receiveAgent (Agent agent)
	{
		if (this.getOwner() != null && this.getOwner().equals(agent.getOwnerPlayer()))
		{
			this.hostFriendAgent(agent);
		}
		else
		{
			this.beAttackedByAgent(agent);
		}
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return diam;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return diam;
	}
}