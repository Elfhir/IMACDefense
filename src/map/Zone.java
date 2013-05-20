package map;

import players.Player;

public class Zone {
	private static int lastid = 0;
	private Player owner = null;
	private int ownerid = 1;
	private int id = 0;

	public int getPlayerId() {
		return ownerid;
	}

	public void setPlayerId(int id) {
		this.ownerid = id;
	}
	
	public static int getLastId ()
	{
		return lastid;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public Zone(Player owner) {
		// TODO Auto-generated constructor stub
		super ();
		this.owner = owner;
		this.id = lastid;
		++lastid;
	}
	
	public Zone (int playerid)
	{
		super();
		this.ownerid = playerid;
		this.id = lastid;
		++lastid;
	}
}
