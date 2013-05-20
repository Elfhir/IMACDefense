package players;

import map.Zone;
import map.tiles.Buttress;
import map.tiles.Mountain;
import map.tiles.Tile;

public class Player {
	
	private int id = 1;
	private int money = 5000;

	public Player() {
		// TODO Auto-generated constructor stub
	}
	
	public Player (int id)
	{
		super();
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public boolean isThisTileInMyZone (Tile tile)
	{
		if (!(tile instanceof Buttress))
			return false;
		
		Zone tileZone = ((Buttress) tile).getZone();
		if (tileZone.getPlayer() == this.id)
		{
			return true;
		}
		return false;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Player player = new Player (1);
		Player player2 = new Player (2);
		Buttress tile = new Buttress (new Zone (1));
		Mountain tile2 = new Mountain ();
		System.out.println("tile and player 1 : " + player.isThisTileInMyZone(tile));
		System.out.println("tile2 and player 1 : " + player.isThisTileInMyZone(tile2));
		System.out.println("tile and player 2 : " + player2.isThisTileInMyZone(tile));
		System.out.println("tile2 and player 2 : " + player2.isThisTileInMyZone(tile2));
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

}
