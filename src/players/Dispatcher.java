package players;

import gameengine.AnimationAction;
import gameengine.GameEngine;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.SwingUtilities;

import map.Mapping;

import players.types.ArtificialIntelligencePlayer;

public class Dispatcher extends AnimationAction {
	
	ArrayList<Player> players;
	Mapping map;
	GameEngine frame;

	public Dispatcher(ArrayList<Player> players, Mapping map, GameEngine frame, int timer) {
		super(frame, timer);
		this.players = players;
		this.map = map;
		this.frame = frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (players == null)
			return;

		Iterator<Player> it = players.iterator();
		while (it.hasNext())
		{
			Player currentPlayer = it.next();
			if (!currentPlayer.hasLost() && currentPlayer.getMyBasis(map).isEmpty())
			{
				currentPlayer.setLost(true);
			}
			
			if (currentPlayer instanceof ArtificialIntelligencePlayer && !currentPlayer.hasLost())
			{
				SwingUtilities.invokeLater(new PlayerRunnable((ArtificialIntelligencePlayer)currentPlayer, map, frame));
			}
		}
	}

}
