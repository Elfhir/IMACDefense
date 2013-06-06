package players.types;

import java.awt.Point;

import basis.Base;

import map.Mapping;
import map.tiles.Buttress;
import map.tiles.Tile;
import players.Player;
import players.SelectableObject;
import towers.Tower;
import window.IHM;

public class HumanPlayer extends Player {
	
	private static boolean constructingNow = false;

	public HumanPlayer() {
		// TODO Auto-generated constructor stub
	}

	public HumanPlayer(int id, PlayerColor color) {
		super(id, color);
		// TODO Auto-generated constructor stub
	}
	
	/* Lorsque les joueurs seront implémentés : enlever static */
	public static boolean isConstructingNow() {
		return constructingNow;
	}
	
	public static void setConstructingNow(boolean constructingNow) {
		HumanPlayer.constructingNow = constructingNow;
	}
	
	@Override
	public Tower construct(Class<? extends Tower> towerclass, Mapping map, map.Zone zone, int x, int y) {
		Tower temptower = super.construct(towerclass, map, zone, x, y);
		
		if (temptower != null)
			constructingNow = false;
		
		return temptower;
	}
	
	public static SelectableObject whereDidIClick (Point mousepoint, Mapping map, IHM ihm)
	{
		/* Les coordonnées clickées par la souris ne sont pas en nombre de tiles,
		 * mais plutôt les coordonnées réelles dans la fenêtre.
		 * Il faut donc diviser ces coordonnées par la largeur d'un tile pour connaître les coordonnées en nombre de tiles.
		 */
		Point tilepoint = new Point((int)mousepoint.getX()/Tile.getWidth(), (int)mousepoint.getY()/Tile.getHeight());
		
		Base base = map.getBasis().get(tilepoint);
		if (base != null)
		{
			return base;
		}
		
		Tower tower = map.getTowers().get(tilepoint);
		if (tower != null)
		{
			return tower;
		}
		
		if (ihm != null && ihm.getTowers() != null)
		{
			tower = ihm.getTowers().get(tilepoint);
			if (tower != null)
			{
				return tower;
			}
		}
		
		return null;
	}

	/* Lorsque les joueurs seront implémentés : la méthode ne doit plus être statique et la condition doit être décommentée */
	public static Buttress whatZoneDidIClickIn (Point mousepoint, Mapping map)
	{
		Point tilepoint = new Point((int)mousepoint.getX()/Tile.getWidth(), (int)mousepoint.getY()/Tile.getHeight());
		if (tilepoint.getX() < map.getWidth() && tilepoint.getY() < map.getHeight())
		{
			Tile tile = map.getTiles().get((int)tilepoint.getX()).get((int)tilepoint.getY());
			if (tile != null && tile instanceof Buttress /*&& this.isThisTileInMyZone(tile)*/)
			{
				return (Buttress)tile;
			}	
		}
		return null;
	}

}
