package window;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import basis.Base;

import agents.Agent;

import map.Mapping;
import map.tiles.Tile;

public class GraphicalInterface extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Mapping map = null;
	private PanMap pan = null;
	
	public GraphicalInterface(){
		super();
		build();//On initialise notre fenêtre
	}
 
	public GraphicalInterface(Mapping map) throws HeadlessException {
		super();
		this.map = map;
		build ();
	}

	private void build(){
		if (this.map == null)
			return;
		
		// Icône de l'application
		Image icone = Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + File.separator + "img" + File.separator + "icon.gif");
		this.setIconImage(icone);
		
		// Curseur Cible
		Image cursorImage = Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + File.separator + "img" + File.separator + "cursor.png");
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(7, 7), "Cursor");
		this.setCursor(cursor);		
	
		this.setTitle("IMACDefense - " + map.getName()); // On donne un titre à l'application
		this.setSize(map.getWidth()*Tile.getWidth() + 6,map.getHeight()*Tile.getHeight() + 28); // On donne une taille à notre fenêtre
		
		this.setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
		this.setResizable(false); // On interdit le redimensionnement de la fenêtre
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // On dit à l'application de se fermer lors du clic sur la croix

	    //On prévient notre JFrame que notre JPanel sera son content pane
		this.pan = new PanMap (map);
	    this.setContentPane(pan);
	    this.setVisible(true);
		Hashtable<Point, Base> mapBasis = map.getBasis();
		Set<Point> set = mapBasis.keySet();
		Iterator<Point> it = set.iterator();

		while (it.hasNext())
		{
			Point currentPoint = it.next();
			Base currentBase = mapBasis.get(currentPoint);
			if (currentBase != null)
			{
				for (int i = 0; i < currentBase.getNbCreatableAgents(); ++i)
				{
					Agent newagent = currentBase.createAgent();
					if (newagent != null)
					{
						map.getAgents().add(newagent);
					}
				}
			}
		}
	}

	public Mapping getMap() {
		return map;
	}

	public PanMap getPan() {
		return pan;
	}
}