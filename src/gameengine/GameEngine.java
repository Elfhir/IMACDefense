package gameengine;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

import map.Mapping;
import players.Player;
import players.types.ArtificialIntelligencePlayer;
import players.types.HumanPlayer;

public class GameEngine {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new HumanPlayer (1, Player.PlayerColor.red));
		players.add(new ArtificialIntelligencePlayer (2, Player.PlayerColor.green));
		players.add(new ArtificialIntelligencePlayer (3, Player.PlayerColor.yellow));
		players.add(new ArtificialIntelligencePlayer (4, Player.PlayerColor.blue));
		SwingUtilities.invokeLater(new GameEngineRunnable(new Mapping ("map1.xml"), players));
	}
}
