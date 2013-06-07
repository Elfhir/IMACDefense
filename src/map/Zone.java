package map;

import java.util.ArrayList;
import java.util.Random;

import map.tiles.Buttress;

import players.Player;

public class Zone {
	private Player owner = null;
	private int ownerid = 1;
	private ArrayList<Buttress> buttresstiles = new ArrayList<Buttress>();

	public int getPlayerId() {
		return ownerid;
	}

	public void setPlayerId(int id) {
		this.ownerid = id;
	}

	public ArrayList<Buttress> getButtresstiles() {
		return buttresstiles;
	}
	
	public void addButtressTile (Buttress tile)
	{
		if (tile != null)
			this.buttresstiles.add(tile);
	}
	
	public Buttress selectRandomButtressTile ()
	{
		Random r = new Random();
		int randomindex = r.nextInt(this.buttresstiles.size() - 1);
		
		return buttresstiles.get(randomindex);
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
