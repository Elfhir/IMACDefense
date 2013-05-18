package window;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import map.Mapping;
import map.tiles.Tile;
  
public class Panel extends JPanel {
	
	private Mapping map = null;
	
	public Panel (Mapping map)
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
}