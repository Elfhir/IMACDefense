package gameengine;

import java.util.ArrayList;

import map.Mapping;
import map.Zone;
import players.Player;
import window.GraphicalInterface;

public class GameEngineRunnable implements Runnable {
	
	private static GraphicalInterface window = null;
	Mapping map = null;
	ArrayList<Player> players = new ArrayList<Player>();

	public GameEngineRunnable(Mapping map, ArrayList<Player> players) {
		// TODO Auto-generated constructor stub
		this.map = map;
		this.players = players;
		
		if (map != null && players != null)
		{
			ArrayList<Zone> zones = map.getZones();
			if (zones != null)
			{
				for (Zone zone:zones)
				{
					for (Player player:players)
					{
						if (zone.getPlayerId() == player.getId())
						{
							zone.setOwner(player);
						}
					}
				}
			}
		}
	}
	
	public void run(){
		if (map == null || players == null)
			return;

		/* On crée une nouvelle instance de notre JDialog */
		GameEngineRunnable.setWindow (new GraphicalInterface(map));
		window.getContentPane().addMouseListener(window);
		IncreaseNbHostedAgentsAction action = new IncreaseNbHostedAgentsAction(window, 5000);
		action.run();
	}

	public static GraphicalInterface getWindow() {
		return window;
	}

	public static void setWindow(GraphicalInterface window) {
		GameEngineRunnable.window = window;
	}
}
