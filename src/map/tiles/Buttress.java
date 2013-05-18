package map.tiles;

import java.io.File;

import map.Zone;

public class Buttress extends Tile {
	
	private boolean destroyable = false;
	private boolean walkable = false;
	private boolean constructible = true;
	private GroupPosition pos = GroupPosition.Center;
	
	public enum GroupPosition
	{
		TopLeftCorner ("SandRoad.bmp", 0, 1),
		BottomLeftCorner ("SandRoad.bmp", 0, 81),
		TopRightCorner ("SandRoad.bmp", 80, 1),
		BottomRightCorner ("SandRoad.bmp", 80, 81),
		Left ("SandRoad.bmp", 0, 41),
		Right ("SandRoad.bmp", 80, 41),
		Top ("SandRoad.bmp", 40, 1),
		Bottom ("SandRoad.bmp", 40, 81),
		Center ("SandRoad.bmp", 40, 41);
		
		private String imageName = "";
		private int subX = 0;
		private int subY = 0;
		
		private GroupPosition (String imageName, int subX, int subY)
		{
			String workingDir = System.getProperty("user.dir");
			String filepath = workingDir + File.separator + "img" + File.separator + "HardVaccum_Tileset" + File.separator + "Terrain Tiles" + File.separator;
			this.imageName = filepath + imageName;
			
			this.subX = subX;
			this.subY = subY;
		}

		public String getImageName() {
			return imageName;
		}

		public int getSubX() {
			return subX;
		}

		public int getSubY() {
			return subY;
		}
	};
	
	private String imageName = System.getProperty("user.dir") + File.separator + "img" + File.separator + "HardVaccum_Tileset" + File.separator + "Terrain Tiles" + File.separator + "SandRoad.bmp";
	private int subImageX = 80;
	private int subImageY = 41;
	
	private Zone zone = null;

	public Buttress(Zone zone) {
		// TODO Auto-generated constructor stub
		this.zone = zone;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public int getSubImageX ()
	{
		return this.pos.getSubX();
	}
	
	public void findPos(boolean top, boolean left, boolean bottom, boolean right) {
		
		// Si tout est vrai sauf top : alors le tile est de type "Top" : il est en haut au centre.
		if (!top && left && bottom && right)
			this.pos = Buttress.GroupPosition.Top;
		
		// Si tout est vrai sauf left : alors le tile est de type "Left"
		else if (top && !left && bottom && right)
			this.pos = Buttress.GroupPosition.Left;
		
		// Si tout est vrai sauf bottom : alors le tile est de type "Bottom"
		else if (top && left && !bottom && right)
			this.pos = Buttress.GroupPosition.Bottom;
		
		// Si tout est vrai sauf right : alors le tile est de type "Right"
		else if (top && left && bottom && !right)
			this.pos = Buttress.GroupPosition.Right;
			
		// Si tout est vrai sauf top et left : alors le tile est de type "TopLeftCorner"
		else if (!top && !left && bottom && right)
			this.pos = Buttress.GroupPosition.TopLeftCorner;
			
		// Si tout est vrai sauf top et right : alors le tile est de type "TopRightCorner"
		else if (!top && left && bottom && !right)
			this.pos = Buttress.GroupPosition.TopRightCorner;
			
		// Si tout est vrai sauf bottom et left : alors le tile est de type "BottomLeftCorner"
		else if (top && !left && !bottom && right)
			this.pos = Buttress.GroupPosition.BottomLeftCorner;
			
		// Si tout est vrai sauf bottom et right : alors le tile est de type "BottomLeftCorner"
		else if (top && left && !bottom && !right)
			this.pos = Buttress.GroupPosition.BottomRightCorner;
		
		// Si aucune des conditions précédentes n'est vraie, alors on a un tile "solitaire" ou au centre : "Center"
		else
			this.pos = Buttress.GroupPosition.Center;
	}

	@Override
	public int getSubImageY ()
	{
		return this.pos.getSubY();
	}

	@Override
	public String getImageName() {
		return this.pos.getImageName();
	}
}
