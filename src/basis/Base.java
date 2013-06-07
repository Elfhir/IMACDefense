package basis;

import java.awt.Point;
import java.awt.geom.Rectangle2D;

import players.Player;
import players.SelectableObject;
import towers.strategy.shooter.AttackableObject;
import towers.strategy.shooter.shootType.AttackingObject;
import agents.Agent;
import map.Mapping;
import map.Zone;
import map.tiles.Tile;

public class Base implements SelectableObject, AttackableObject {

	private static int time = 1000; // temps d'attente entre chaque création d'agent en millièmes de secondes
	private int nbCreatableAgents = 0; // nb d’agents que la base peut créer (par intervalle régulier)
	private int nbHostedAgents = 0; // nb d’agents que la base héberge actuellement
	private int maxCapacity = 0; // nb d'agents que la base peut héberger
	private Base target = null;
	private Zone zone = null;
	private Player owner = null; // Si la base n'est associée à aucune zone, le propriétaire est sauvegardé directement dans la base.
	private int diam = 0; // diamètre du cercle représentant la base en nb de tiles
	
	private Point coordInTiles = null;
	
	private boolean selected = false;
	private Rectangle2D.Double hitbox;
	
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
	
	public int getMaxCapacity() {
		return maxCapacity;
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
		this.selected = !this.selected;
	}
	
	/*
	 * Constructor
	 */

	public Base(int initialCapacity, Zone zone, Point point) {
		this.nbCreatableAgents = initialCapacity/2;
		this.nbHostedAgents = initialCapacity;
		this.maxCapacity = initialCapacity*4;
		this.diam = initialCapacity/5;
		this.zone = zone;
		this.coordInTiles = point;
		
		this.setHitBox();
	}
	
	public Base() {
		super();
	}
	
	/*
	 * Methodes
	 */
	
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
			System.out.println("no target");
			return null;
		}
		
		Agent agent = new Agent (this.coordInTiles, this.target, this.getOwner(), this.nbCreatableAgents, map);
		this.nbHostedAgents -= this.nbCreatableAgents;
		this.nbCreatableAgents = (int)this.nbHostedAgents/2;
		if (agent != null)
			map.getAgents().add(agent);
		return agent;
	}
	
	@Override
	public void beAttacked (AttackingObject agent)
	{
		if (agent instanceof Agent)
		{
			if (this.nbHostedAgents == ((Agent) agent).getForce())
			{
				this.setNbHostedAgents(0);
				this.setOwner(null);
			}
			else if (this.nbHostedAgents > ((Agent) agent).getForce())
			{
				this.decreaseNbHostedAgents(((Agent) agent).getForce());
			}
			else if (this.nbHostedAgents < ((Agent) agent).getForce())
			{
				int newNbHostedAgents = ((Agent) agent).getForce () - this.nbHostedAgents;
				this.setOwner(((Agent) agent).getOwnerPlayer());
				this.setNbHostedAgents(newNbHostedAgents);
			}
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
			this.beAttacked(agent);
		}
	}

	@Override
	public int getObjectWidth() {
		return diam;
	}

	@Override
	public int getObjectHeight() {
		return diam;
	}

	@Override
	public void setCoordInTiles(Point coordInTiles) {
		this.coordInTiles = coordInTiles;
	}

	@Override
	public boolean isInHitBox(Point point) {
		if (this.hitbox.contains(point))
			return true;
		return false;
	}
	
	@Override
	public void setHitBox() {
		if (this.hitbox == null)
		{
			this.hitbox = new Rectangle2D.Double();
		}
		this.hitbox.setRect(coordInTiles.getX()*Tile.getWidth(), coordInTiles.getY()*Tile.getHeight(), diam, diam);
	}
	
	@Override
	public Rectangle2D.Double getHitBox() {
		return hitbox;
	}
}