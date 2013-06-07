package players;

import map.Mapping;

import players.types.ArtificialIntelligencePlayer;

public class PlayerRunnable implements Runnable {
	ArtificialIntelligencePlayer player = null;
	Mapping map = null;

	public PlayerRunnable() {
		// TODO Auto-generated constructor stub
		this.player = new ArtificialIntelligencePlayer();
	}

	public PlayerRunnable(ArtificialIntelligencePlayer player, Mapping map) {
		super();
		this.player = player;
		this.map = map;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		/*
		 *  Actions du joueur
		 */
		if (player != null)
			player.play(map);
	}

}
