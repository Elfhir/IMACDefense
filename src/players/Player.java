package players;

import map.Mapping;
import map.Zone;
import map.tiles.Buttress;
import map.tiles.Tile;
import towers.Tower;

import java.awt.*;
import java.util.ArrayList;

public class Player {
	
	private static SelectableObject lastObjectSelected = null;
	
	private int id = 1;
	private int money = 5000;
	private PlayerColor color = PlayerColor.red;
    private String name="Fifi";
	
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

	/*
	 * Constructeurs
	 */
	
	public Player() {
		super();
	}
	
	public Player (int id, String name, PlayerColor color)
	{
		super();
		this.id = id;
		this.setColor(color);
        this.setName(name);
	}
	
	/*
	 * Getters
	 */
	
	/*
	 * Setters
	 */

    public void setName(String name)
    {
        this.name=name;
    }

    public String getName()
    {
        return name;
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
		
		if (y+tower.getObjectHeight() >= map.getHeight() || y < 0 || x+tower.getObjectWidth() >= map.getWidth() || x < 0)
		{
			return false;
		}
		
		/* Puis on v�rifie qu'il veut construire la tour au bon endroit.
		 * Pour chaque ligne de tiles occup�e par la tour,
		 */
		for (i = 0; i < tower.getObjectHeight(); ++i)
		{
			/* Pour chaque tile occup� par la tour (ligne*colonne) */
			for (j = 0; j < tower.getObjectWidth(); ++j)
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
	
	public Tower construct (Class<? extends Tower> towerclass, Mapping map, Zone zone, int x, int y)
	{
		Tower tower;
		try {
			tower = towerclass.newInstance();
			if (this.canIConstruct(tower, map, x, y))
			{
				map.setTower(tower, x, y);
				tower.setCoordInTiles(new Point(x,y));
				tower.setZone(zone);
				this.money -= tower.getPrice();
				return tower;
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
