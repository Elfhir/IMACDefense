package players;

import java.awt.Point;

public interface SelectableObject {
	public boolean isSelected();
	public void setSelected (boolean selected);
	public void inverseSelected();
	
	public Point getCoordInTiles ();
	public int getWidth();
	public int getHeight();
}
