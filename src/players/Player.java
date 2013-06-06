package players;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import basis.Base;
import towers.Tower;
import window.IHM;
import map.Mapping;
import map.Zone;
import map.tiles.Buttress;
import map.tiles.Tile;

public class Player {
	
	private static SelectableObject lastObjectSelected = null;
	
	private int id = 1;
	private int money = 5000;
	private PlayerColor color = PlayerColor.red;
	private static boolean constructingNow = false;
	
	public enum PlayerColor
	{
		blue (Color.blue),
		green (Color.green.darker().darker()),
		red (Color.red.darker().darker()),
		yellow (Color.orange.darker());
		
		private Color color = null;
		
		private PlayerColor (Color color)
		{
			this.color = color;
		}
		
		public Color getColor ()
		{
			return this.color;
		}
	}

	public Player() {
		// TODO Auto-generated constructor stub
	}
	
	public Player (int id, PlayerColor color)
	{
		super();
		this.id = id;
		this.setColor(color);
	}
	
	public void setColor (PlayerColor color)
	{
		if (color != PlayerColor.red && color != PlayerColor.blue && color != PlayerColor.yellow && color != PlayerColor.green)
		{
			this.color = PlayerColor.red;
			return;
		}
		this.color = color;
	}

	/* Lorsque les joueurs seront implémentés : enlever static */
	public static boolean isConstructingNow() {
		return constructingNow;
	}

	public PlayerColor getColorName() {
		return color;
	}
	
	public Color getColor ()
	{
		return color.getColor();
	}

	public static SelectableObject getLastObjectSelected() {
		return lastObjectSelected;
	}

	public static void setLastObjectSelected(SelectableObject lastObjectSelected) {
		Player.lastObjectSelected = lastObjectSelected;
	}

	public int getId() {
		return id;
	}
	
	public static void setConstructingNow(boolean constructingNow) {
		Player.constructingNow = constructingNow;
	}

	public boolean isThisTileInMyZone (Tile tile)
	{
		if (!(tile instanceof Buttress))
			return false;
		
		Zone tileZone = ((Buttress) tile).getZone();
		if (tileZone.getPlayerId() == this.id)
		{
			return true;
		}
		return false;
	}
	
	/* Lorsque les joueurs seront implémentés : la méthode ne doit plus être statique et la condition doit être décommentée */
	public static Buttress didIClickInMyZone (Point mousepoint, Mapping map)
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

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
	
	// Vérifie si le joueur a le droit de construire la tour tower dans la map map aux coordonnées x et y.
	public boolean canIConstruct (Tower tower, Mapping map, int x, int y)
	{
		if (map == null || tower == null)
			return false;
		
		ArrayList<ArrayList<Tile>> mapTiles = map.getTiles();
		
		int i = 0, j = 0;
		
		/* On vérifie d'abord que le joueur a assez d'argent. */
		if (this.money < tower.getPrice())
			return false;
		
		if (y+tower.getObjectHeight() >= map.getHeight() || y < 0 || x+tower.getObjectWidth() >= map.getWidth() || x < 0)
		{
			return false;
		}
		
		/* Puis on vérifie qu'il veut construire la tour au bon endroit.
		 * Pour chaque ligne de tiles occupée par la tour,
		 */
		for (i = 0; i < tower.getObjectHeight(); ++i)
		{
			/* Pour chaque tile occupé par la tour (ligne*colonne) */
			for (j = 0; j < tower.getObjectWidth(); ++j)
			{
				/* On vérifie si le tile en question est bien dans la zone du joueur */
				if (!this.isThisTileInMyZone(mapTiles.get(y+i).get(x+j)))
				{
					return false;
				}
				
				/* On vérifie également qu'il n'y a aucune tour déjà construite à cet endroit. */
				if (map.isThereATowerHere(x+j, y+i))
				{
					return false;
				}
			}
		}
		return true;
	}
	
	public Tower construct (Tower tower, Mapping map, Zone zone, int x, int y)
	{
		if (this.canIConstruct(tower, map, x, y))
		{
			map.setTower(tower, x, y);
			tower.setCoordInTiles(new Point(x,y));
			tower.setZone(zone);
			this.money -= tower.getPrice();
			constructingNow = false;
			return tower;
		}
		return null;
	}
}
