package window;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JFrame;
import players.Player;
import players.SelectableObject;

import basis.Base;

import agents.Agent;

import map.Mapping;
import map.tiles.Tile;

public class GraphicalInterface extends JFrame implements MouseListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Mapping map = null;
	private PanMap pan = null;
	
	public GraphicalInterface(){
		super();
		build();//On initialise notre fen�tre
	}
 
	public GraphicalInterface(Mapping map) throws HeadlessException {
		super();
		this.map = map;
		build ();
	}

	private void build(){
		if (this.map == null)
			return;
		
		// Ic�ne de l'application
		Image icone = Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + File.separator + "img" + File.separator + "icon.gif");
		this.setIconImage(icone);
		
		// Curseur Cible
		Image cursorImage = Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + File.separator + "img" + File.separator + "cursor.png");
		// On r�cup�re la dimension qu'aura la curseur � l'�cran
		Dimension cursorDimension = Toolkit.getDefaultToolkit().getBestCursorSize(15, 15);
		// Le curseur est en forme de croix (cible), on veut donc que le "hotspot" se situe au centre du curseur, donc on divise les dimensions r�cup�r�es par 2.
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point((int)cursorDimension.getWidth()/2, (int)cursorDimension.getHeight()/2), "Cursor");
		this.setCursor(cursor);
	
		this.setTitle("IMACDefense - " + map.getName()); // On donne un titre � l'application
		//this.setSize(map.getWidth()*Tile.getWidth() + 6, map.getHeight()*Tile.getHeight() + 28); // On donne une taille � notre fen�tre
		
		this.setLocationRelativeTo(null); //On centre la fen�tre sur l'�cran
		this.setResizable(false); // On interdit le redimensionnement de la fen�tre
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // On dit � l'application de se fermer lors du clic sur la croix

		// On impose un layout au contentPane afin que ses dimensions ne prennent pas en compte les bords de la fen�tre
		Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());
        
        //On pr�vient notre JFrame que notre JPanel sera son content pane
		this.pan = new PanMap (map);
		this.pan.setDoubleBuffered(true);
		this.pan.setPreferredSize(new Dimension(map.getWidth()*Tile.getWidth(), map.getHeight()*Tile.getHeight()));
	    contentPane.add(this.pan);
	    pack ();
	    this.setVisible(true);
	    
	    // On ajoute le mouseListener au contentPane
	    contentPane.addMouseListener(this);
	    
		Hashtable<Point, Base> mapBasis = map.getBasis();
		Set<Point> set = mapBasis.keySet();
		Iterator<Point> it = set.iterator();

		while (it.hasNext())
		{
			Point currentPoint = it.next();
			Base currentBase = mapBasis.get(currentPoint);
			if (currentBase != null)
			{
				int nbToCreate = currentBase.getNbCreatableAgents();
				for (int i = 0; i < nbToCreate; ++i)
				{
					Agent newagent = currentBase.createAgent();
					if (newagent != null)
					{
						map.getAgents().add(newagent);
					}
				}
				currentBase.setTarget(null);
			}
		}
	}

	public Mapping getMap() {
		return map;
	}

	public PanMap getPan() {
		return pan;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		// On v�rifie si le joueur a cliqu� sur un objet selectionnable.
		SelectableObject object = Player.whereDidIClick(new Point(e.getX(), e.getY()), map);
		
		// Si oui,
		if (object != null)
		{
			/*
			 *  Si l'objet est d�j� s�lectionn�, le joueur a cliqu� pour d�selectionner l'objet.
			 *  Donc on d�selectionne.
			 */
			
			if (object.isSelected())
			{
				Player.setLastObjectSelected(null);
			}
			
			/*
			 * Sinon, le joueur a voulu s�lectionner l'objet.
			 * On d�s�lectionne l'objet qui �tait s�lectionn� auparavant, et on s�lectionne l'objet click�.
			 */
			else
			{
				if (Player.getLastObjectSelected() != null)
					Player.getLastObjectSelected().setSelected(false);
				Player.setLastObjectSelected(object);
			}
			
			/*
			 * On inverse donc le bool�en "selected" :
			 * s'il �tait � false (objet d�selectionn�) il passe � true (objet s�lectionn�)
			 * et vice-versa.
			 */
			object.inverseSelected();
			
			/*
			 * Puis on redessine les �l�ments.
			 */
			pan.repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	} 
}