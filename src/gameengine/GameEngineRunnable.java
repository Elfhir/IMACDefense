package gameengine;

import map.Mapping;

public class GameEngineRunnable implements Runnable {
	
	private static GameEngine window = null;
	Mapping map = null;

	public GameEngineRunnable(Mapping map) {
		// TODO Auto-generated constructor stub
		this.map = map;
	}
	
	public void run(){
		if (map == null)
			return;

		/* On crée une nouvelle instance de notre JDialog */
		GameEngineRunnable.setWindow (new GameEngine(map));
		window.getContentPane().addMouseListener(window);
	}

	public static GameEngine getWindow() {
		return window;
	}

	public static void setWindow(GameEngine window) {
		GameEngineRunnable.window = window;
	}
}
