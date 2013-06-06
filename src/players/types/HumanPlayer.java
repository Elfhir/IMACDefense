package players.types;

import basis.Base;
import map.Mapping;
import map.tiles.Buttress;
import map.tiles.Tile;
import players.Player;
import players.SelectableObject;
import towers.Tower;
import window.IHM;

import java.awt.*;

public class HumanPlayer extends Player {
	
	private static boolean constructingNow = false;

	/*
	 * Constructeurs
	 */
	
	public HumanPlayer() {
		super();
	}

	public HumanPlayer(int id, String name, PlayerColor color) {
		super(id, name, color);
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * Getters
	 */

	public static boolean isConstructingNow() {
		return constructingNow;
	}
	
	/*
	 * Setters
	 */
	
	public static void setConstructingNow(boolean constructingNow) {
		HumanPlayer.constructingNow = constructingNow;
	}
	
	/*
	 * Méthodes
	 */
	
	// Si l'object cliqué est sélectionnable, retourne cet objet
	public static SelectableObject whereDidIClick (Point mousepoint, Mapping map, IHM ihm)
	{
		/* Les coordonnï¿½es clickï¿½es par la souris ne sont pas en nombre de tiles,
		 * mais plutï¿½t les coordonnï¿½es rï¿½elles dans la fenï¿½tre.
		 * Il faut donc diviser ces coordonnï¿½es par la largeur d'un tile pour connaï¿½tre les coordonnï¿½es en nombre de tiles.
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

	// Retourne le tile de contrefort cliqué
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
	
	/*
	 * (non-Javadoc)
	 * @see players.Player#construct(java.lang.Class, map.Mapping, map.Zone, int, int)
	 */
	
	@Override
	public Tower construct(Class<? extends Tower> towerclass, Mapping map, map.Zone zone, int x, int y) {
		Tower temptower = super.construct(towerclass, map, zone, x, y);
		
		if (temptower != null)
			constructingNow = false;
		
		return temptower;
	}

}
