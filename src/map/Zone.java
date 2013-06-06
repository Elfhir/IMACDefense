package map;

import players.Player;

public class Zone {
	private Player owner = null;
	private int ownerid = 1;

	public int getPlayerId() {
		return ownerid;
	}

	public void setPlayerId(int id) {
		this.ownerid = id;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public Zone(Player owner) {
		super ();
		this.owner = owner;
	}
	
	public Zone (int playerid)
	{
		super();
		this.ownerid = playerid;
	}
}
