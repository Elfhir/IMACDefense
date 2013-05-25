package gameengine;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

import map.Mapping;
import players.Player;

public class GameEngine {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player (1, Player.PlayerColor.red));
		players.add(new Player (2, Player.PlayerColor.green));
		players.add(new Player (3, Player.PlayerColor.yellow));
		players.add(new Player (4, Player.PlayerColor.blue));
		SwingUtilities.invokeLater(new GameEngineRunnable(new Mapping ("map1.xml"), players));
	}
}
