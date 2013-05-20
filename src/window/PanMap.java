package window;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import towers.Tower;
import map.Mapping;
import map.tiles.Tile;
  
public class PanMap extends JPanel {
	
	private Mapping map = null;
	
	public PanMap (Mapping map)
	{
		this.map = map;
	}
	
	@Override
	public void paintComponent (Graphics g)
	{
		paintMap (g);
	}
	
	public void paintMap (Graphics g)
	{
		paintAllTiles (g);
		paintAllTowers(g);
	}
	
	public void paintAllTiles (Graphics g)
	{
		ArrayList<ArrayList<Tile>> mapTiles = this.map.getTiles();
		Iterator<ArrayList<Tile>> line = mapTiles.iterator();
		int i = 0; int j = 0;
		while(line.hasNext())
        {
			ArrayList<Tile> currentLine = (ArrayList<Tile>) line.next();
        	Iterator<Tile> column = currentLine.iterator();
        	while(column.hasNext())
        	{
        		Tile currentTile = (Tile) column.next();
        		paintTile(currentTile, j, i, g);
        		++j;
        	}
        	++i;
        	j = 0;
        }
	}
	
	public void paintTile(Tile tile, int coordX, int coordY, Graphics g){
		try {
			BufferedImage img = ImageIO.read(new File(tile.getImageName()));
			g.drawImage(img.getSubimage(tile.getSubImageX (), tile.getSubImageY (), Tile.getWidth(), Tile.getHeight()), coordX*Tile.getWidth(), coordY*Tile.getHeight(), this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void paintAllTowers (Graphics g)
	{
		Hashtable<Point, Tower> mapTowers = this.map.getTowers();
		Set<Point> set = mapTowers.keySet();
		Iterator<Point> it = set.iterator();

		while (it.hasNext())
		{
			Point currentPoint = it.next();
			Tower currentTower = mapTowers.get(currentPoint);
			
			/* Une tour peut prendre plus d'un tile, il faut donc vérifier qu'on affiche pas la tour plusieurs fois.
			 * Pour cela, on vérifie si elle a déjà été affichée en vérifiant si la même tour est présente dans le tile en haut et/ou dans le tile à gauche.
			 */
			int x = (int) currentPoint.getX();
			int y = (int) currentPoint.getY();
			
			Tower left = mapTowers.get(new Point(x-1, y));
			Tower top = mapTowers.get(new Point(x, y-1));
			
			if (!(left != null && left.equals(currentTower)) && !(top != null && top.equals(currentTower)))
			{
				paintTower (currentTower, x, y, g);
			}
		}
	}
	
	public void paintTower (Tower tower, int coordX, int coordY, Graphics g)
	{
		try {
			BufferedImage img = ImageIO.read(new File(tower.getImageName()));
			g.drawImage(img, coordX*Tile.getWidth(), coordY*Tile.getHeight(), this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}