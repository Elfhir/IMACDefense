package map.tiles;

import java.io.File;

public class Mountain extends Tile {
	
	private boolean destroyable = true;
	private boolean walkable = false;
	private boolean constructible = false;
	private GroupPosition pos = GroupPosition.Alone;
	
	public enum GroupPosition
	{
		TopLeftCorner ("Sand.bmp", 0, 1),
		BottomLeftCorner ("Sand.bmp", 0, 81),
		TopRightCorner ("Sand.bmp", 80, 1),
		BottomRightCorner ("Sand.bmp", 80, 81),
		Left ("Sand.bmp", 0, 41),
		Right ("Sand.bmp", 80, 41),
		Top ("Sand.bmp", 40, 1),
		Bottom ("Sand.bmp", 40, 81),
		Center ("Sand.bmp", 40, 41),
		Alone ("SandMisc.bmp", 160, 81);
		
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

	public Mountain() {
		super ();
	}
	
	public Mountain(Mountain.GroupPosition pos) {
		super ();
		this.pos = pos;
	}
	
	@Override
	public int getSubImageX ()
	{
		return this.pos.getSubX();
	}
	
	public void findPos(boolean top, boolean left, boolean bottom, boolean right) {
		// Si tout est vrai : alors le tile est de type "Center" : il est au centre d'une colline.
		if (top && left && bottom && right)
			this.pos = Mountain.GroupPosition.Center;
		
		// Si tout est vrai sauf top : alors le tile est de type "Top" : il est en haut au centre de la colline.
		else if (!top && left && bottom && right)
			this.pos = Mountain.GroupPosition.Top;
		
		// Si tout est vrai sauf left : alors le tile est de type "Left"
		else if (top && !left && bottom && right)
			this.pos = Mountain.GroupPosition.Left;
		
		// Si tout est vrai sauf bottom : alors le tile est de type "Bottom"
		else if (top && left && !bottom && right)
			this.pos = Mountain.GroupPosition.Bottom;
		
		// Si tout est vrai sauf right : alors le tile est de type "Right"
		else if (top && left && bottom && !right)
			this.pos = Mountain.GroupPosition.Right;
			
		// Si tout est vrai sauf top et left : alors le tile est de type "TopLeftCorner"
		else if (!top && !left && bottom && right)
			this.pos = Mountain.GroupPosition.TopLeftCorner;
			
		// Si tout est vrai sauf top et right : alors le tile est de type "TopRightCorner"
		else if (!top && left && bottom && !right)
			this.pos = Mountain.GroupPosition.TopRightCorner;
			
		// Si tout est vrai sauf bottom et left : alors le tile est de type "BottomLeftCorner"
		else if (top && !left && !bottom && right)
			this.pos = Mountain.GroupPosition.BottomLeftCorner;
			
		// Si tout est vrai sauf bottom et right : alors le tile est de type "BottomLeftCorner"
		else if (top && left && !bottom && !right)
			this.pos = Mountain.GroupPosition.BottomRightCorner;
		
		// Si aucune des conditions précédentes n'est vraie, alors on a un tile Mountain "solitaire" : "Alone"
		else
			this.pos = Mountain.GroupPosition.Alone;
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
