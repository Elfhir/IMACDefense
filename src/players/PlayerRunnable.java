package players;

public class PlayerRunnable implements Runnable {
	Player player = null;

	public PlayerRunnable() {
		// TODO Auto-generated constructor stub
		this.player = new Player();
	}

	public PlayerRunnable(Player player) {
		super();
		this.player = player;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// Actions du joueur
		int i = 0;
		for (i = 0; i < 50; ++i)
		{
			System.out.println(this.player.getId());
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Player player = new Player (1, Player.PlayerColor.red);
		Player player2 = new Player (2, Player.PlayerColor.blue);
		Thread thread = new Thread (new PlayerRunnable(player));
		Thread thread2 = new Thread (new PlayerRunnable(player2));
		thread.start();
		thread2.start();
	}

}
