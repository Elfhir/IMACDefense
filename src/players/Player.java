package players;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import basis.Base;
import towers.Tower;
import map.Mapping;
import map.Zone;
import map.tiles.Buttress;
import map.tiles.Tile;

public class Player {
	
	private static SelectableObject lastObjectSelected = null;
	
	private int id = 1;
	private int money = 5000;
	private PlayerColor color = PlayerColor.red;
	
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
	
	public static SelectableObject whereDidIClick (Point mousepoint, Mapping map)
	{
		/* Les coordonn�es click�es par la souris ne sont pas en nombre de tiles,
		 * mais plut�t les coordonn�es r�elles dans la fen�tre.
		 * Il faut donc diviser ces coordonn�es par la largeur d'un tile pour conna�tre les coordonn�es en nombre de tiles.
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
		
		return null;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
	
	// V�rifie si le joueur a le droit de construire la tour tower dans la map map aux coordonn�es x et y.
	public boolean canIConstruct (Tower tower, Mapping map, int x, int y)
	{
		if (map == null || tower == null)
			return false;
		
		ArrayList<ArrayList<Tile>> mapTiles = map.getTiles();
		
		int i = 0, j = 0;
		
		/* On v�rifie d'abord que le joueur a assez d'argent. */
		if (this.money < tower.getPrice())
			return false;
		
		/* Puis on v�rifie qu'il veut construire la tour au bon endroit.
		 * Pour chaque ligne de tiles occup�e par la tour,
		 */
		for (i = 0; i < tower.getHeight(); ++i)
		{
			/* Pour chaque tile occup� par la tour (ligne*colonne) */
			for (j = 0; j < tower.getWidth(); ++j)
			{
				/* On v�rifie si le tile en question est bien dans la zone du joueur */
				if (!this.isThisTileInMyZone(mapTiles.get(y+i).get(x+j)))
				{
					return false;
				}
				
				/* On v�rifie �galement qu'il n'y a aucune tour d�j� construite � cet endroit. */
				if (map.isThereATowerHere(x+j, y+i))
				{
					return false;
				}
			}
		}
		return true;
	}
	
	public void construct (Tower tower, Mapping map, int x, int y)
	{
		if (this.canIConstruct(tower, map, x, y))
		{
			map.setTower(tower, x, y);
			this.money -= tower.getPrice();
		}
	}
}
