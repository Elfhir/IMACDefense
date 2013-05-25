package window;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import map.Mapping;
import map.tiles.Tile;

import towers.Tower;
import towers.towertypes.BombTower;
import towers.towertypes.FreezeTower;
import towers.towertypes.LaserTower;
import towers.towertypes.MedicalTower;
import towers.towertypes.SubmachineGunTower;

public class IHM {
	
	GraphicalInterface window = null;
	PanMap pan = null;
	Hashtable<Point, Tower> towers = null;
	private Class<? extends Tower> selectedTowerClass = null;

	public IHM(GraphicalInterface window, Mapping map) {
		// TODO Auto-generated constructor stub
		this.pan = window.getPan();
		this.window = window;
		towers = new Hashtable<Point, Tower>();
		if (towers != null)
		{
			towers.put(new Point(map.getWidth() + 1, 10), new BombTower());
			towers.put(new Point (map.getWidth() + 3, 10), new FreezeTower());
			towers.put(new Point (map.getWidth() + 5, 10), new LaserTower());
			towers.put(new Point (map.getWidth() + 2, 13), new MedicalTower());
			towers.put(new Point (map.getWidth() + 4, 12), new SubmachineGunTower());
		}
	}
	
	public Hashtable<Point, Tower> getTowers() {
		return towers;
	}
	
	public void setTowerCursor (Tower tower)
	{
		if (tower == null || !this.towers.contains(tower))
		{
			return;
		}
		Image cursorImage = Toolkit.getDefaultToolkit().getImage(tower.getImageName());
		// On récupère la dimension qu'aura la curseur à l'écran
		Dimension cursorDimension = Toolkit.getDefaultToolkit().getBestCursorSize(tower.getObjectWidth()*Tile.getWidth(), tower.getObjectHeight()*Tile.getHeight());
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point((int)cursorDimension.getWidth()/2, (int)cursorDimension.getHeight()/2), "Cursor");
		this.window.setCursor(cursor);
		this.selectedTowerClass = tower.getClass();
	}

	public void printTowers ()
	{
		Set<Point> set = towers.keySet();
		Iterator<Point> it = set.iterator();
		while (it.hasNext())
		{
			Point point = it.next();
			Tower current = towers.get(point);
			pan.repaint ((int)point.getX()*Tile.getWidth(), (int)point.getY()*Tile.getHeight(), current.getObjectWidth()*Tile.getWidth(), current.getObjectHeight()*Tile.getHeight());
		}
	}

	public Class<? extends Tower> getSelectedTowerClass() {
		return selectedTowerClass;
	}
}
