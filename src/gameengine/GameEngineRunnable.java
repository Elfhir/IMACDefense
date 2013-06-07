package gameengine;

import map.Mapping;
import window.GraphicalInterface;

public class GameEngineRunnable implements Runnable {
	
	private static GraphicalInterface window = null;
	Mapping map = null;

	public GameEngineRunnable(Mapping map) {
		// TODO Auto-generated constructor stub
		this.map = map;
	}
	
	public void run(){
		if (map == null)
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
