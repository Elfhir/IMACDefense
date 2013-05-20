package map;

public class Zone {
	
	private int id = 0;
	// private Player owner = null;
	private int player = 1;

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public Zone(int id/*, Player owner*/) {
		// TODO Auto-generated constructor stub
		super ();
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
