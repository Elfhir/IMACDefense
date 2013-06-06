package gameengine;

import map.Mapping;
import players.Player;
import players.types.ArtificialIntelligencePlayer;
import players.types.HumanPlayer;

import javax.swing.*;
import java.util.ArrayList;

public class GameEngine {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new HumanPlayer (1, "Fifi", Player.PlayerColor.red));
		players.add(new ArtificialIntelligencePlayer (2, "Loulou", Player.PlayerColor.green));
		players.add(new ArtificialIntelligencePlayer (3, "Riri", Player.PlayerColor.yellow));
		players.add(new ArtificialIntelligencePlayer (4, "Donald", Player.PlayerColor.blue));
		SwingUtilities.invokeLater(new GameEngineRunnable(new Mapping ("map1.xml"), players));
	}
}
