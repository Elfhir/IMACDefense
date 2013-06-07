package players;

import map.Mapping;

import players.types.ArtificialIntelligencePlayer;
import window.GraphicalInterface;

public class PlayerRunnable implements Runnable {
	ArtificialIntelligencePlayer player = null;
	Mapping map;
	GraphicalInterface frame;

	public PlayerRunnable() {
		// TODO Auto-generated constructor stub
		this.player = new ArtificialIntelligencePlayer();
	}

	public PlayerRunnable(ArtificialIntelligencePlayer player, Mapping map, GraphicalInterface frame) {
		super();
		this.player = player;
		this.map = map;
		this.frame = frame;
	}

	@Override
	public void run() {
		/*
		 *  Actions du joueur
		 */

		if (player != null && map != null)
			player.play(map, frame);
	}

}
