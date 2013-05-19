package map.tiles;

import java.io.File;

public class Mountain extends Tile {
	
	private boolean destroyable = true;
	private boolean walkable = false;
	private boolean constructible = false;
	private GroupPosition pos = GroupPosition.Alone;
	private String imageName = System.getProperty("user.dir") + File.separator + "img" + File.separator + "tileset" + File.separator + "terrain.png";
	
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
		InternBottomRightCorner (60, 0),
		InternBottomLeftCorner (80, 0),
		InternTopRightCorner (60, 20),
		InternTopLeftCorner (80, 20),
		Alone (60, 40);

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

	public Mountain() {
		super ();
	}
	
	@Override
	public int getSubImageX ()
	{
		return this.pos.getSubX();
	}
	
	public void findPos(boolean top, boolean left, boolean bottom, boolean right, boolean topLeftCorner, boolean topRightCorner, boolean bottomLeftCorner, boolean bottomRightCorner) {
		// Si tout est vrai : alors le tile est de type "Center" : il est au centre d'une colline.
		if (top && left && bottom && right)
		{
			if (!topLeftCorner && topRightCorner)
				this.pos = Mountain.GroupPosition.InternTopLeftCorner;
			else if (!topRightCorner && topLeftCorner)
				this.pos = Mountain.GroupPosition.InternTopRightCorner;
			else if (!bottomLeftCorner && bottomRightCorner)
				this.pos = Mountain.GroupPosition.InternBottomLeftCorner;
			else if (!bottomRightCorner && bottomLeftCorner)
				this.pos = Mountain.GroupPosition.InternBottomRightCorner;
			else
				this.pos = Mountain.GroupPosition.Center;
		}
		
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
		return this.imageName;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
