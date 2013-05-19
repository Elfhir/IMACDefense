package map.tiles;

import java.io.File;

import map.Zone;

public class Buttress extends Tile {
	
	private boolean destroyable = false;
	private boolean walkable = false;
	private boolean constructible = true;
	private GroupPosition pos = GroupPosition.Center;
	private String imageName = System.getProperty("user.dir") + File.separator + "img" + File.separator + "tileset" + File.separator + "Buttress.png";
	
	public enum GroupPosition
	{
		TopLeftCorner (0, 0),
		Top (20, 0),
		TopRightCorner (40, 0),
		Left (0, 20),
		Center (20, 20),
		Right (40, 20),
		BottomLeftCorner (0, 40),
		Bottom (20, 40),
		BottomRightCorner (40, 40),
		AloneTop (60, 0),
		AloneLeft (80, 0),
		AloneBottom (60, 20),
		AloneRight (80, 20);

		private int subX = 0;
		private int subY = 0;
		
		private GroupPosition (int subX, int subY)
		{			
			this.subX = subX;
			this.subY = subY;
		}

		public int getSubX() {
			return subX;
		}

		public int getSubY() {
			return subY;
		}
	};

	private int subImageX = 80;
	private int subImageY = 41;
	
	private Zone zone = null;

	public Buttress(Zone zone) {
		// TODO Auto-generated constructor stub
		this.zone = zone;
		this.setButtressColor();
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
		
		// Si tout est faux sauf top : alors le tile est de type "AloneBottom"
		else if (top && !left && !bottom && !right)
			this.pos = Buttress.GroupPosition.AloneBottom;
		
		// Si tout est faux sauf left : alors le tile est de type "AloneRight"
		else if (!top && left && !bottom && !right)
			this.pos = Buttress.GroupPosition.AloneRight;
		
		// Si tout est faux sauf bottom : alors le tile est de type "AloneTop"
		else if (!top && !left && bottom && !right)
			this.pos = Buttress.GroupPosition.AloneTop;
		
		// Si tout est faux sauf right : alors le tile est de type "AloneLeft"
		else if (!top && !left && !bottom && right)
			this.pos = Buttress.GroupPosition.AloneLeft;
		
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
		return this.imageName;
	}
	
	public void setButtressColor ()
	{
		this.imageName = System.getProperty("user.dir") + File.separator + "img" + File.separator + "tileset" + File.separator + "Buttress" + this.zone.getPlayer() + ".png";
	}
}
