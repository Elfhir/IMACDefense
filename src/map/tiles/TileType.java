package map.tiles;

public enum TileType {
	
	Field ("", false, true, false),
	Mountain ("", true, false, false),
	Buttress ("", false, false, true);
	
	public static int width = 20;
	
	private String image = "";
	private boolean destroyable = false;
	private boolean walkable = true;
	private boolean constructible = false;

	private TileType(String image, boolean destroyable, boolean walkable,
			boolean constructible) {
		this.image = image;
		this.destroyable = destroyable;
		this.walkable = walkable;
		this.constructible = constructible;
	}
	
	static public TileType convertStringToTileType (String type)
	{
		switch (type)
		{
			case "buttress" :
			{
				return TileType.Buttress;
			}
			case "mountain" :
			{
				return TileType.Mountain;
			}
			default :
			{
				return TileType.Buttress;
			}
		}
	}
	
	public void print ()
	{
		System.out.println(this);
		return;
	}

}