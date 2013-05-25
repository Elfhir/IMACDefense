package window;

import gameengine.Action;
import gameengine.MoveAgentAction;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import players.Player;
import players.SelectableObject;
import towers.Tower;
import basis.Base;

import agents.Agent;

import map.Mapping;
import map.tiles.Buttress;
import map.tiles.Tile;

public class GraphicalInterface extends JFrame implements MouseListener, KeyListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Mapping map = null;
	private PanMap pan = null;
	private IHM ihm = null;
	
	public GraphicalInterface(){
		super();
		build();//On initialise notre fen�tre
	}
 
	public GraphicalInterface(Mapping map) throws HeadlessException {
		super();
		this.map = map;
		this.ihm = new IHM(this, map);
		build ();
	}

	private void build(){
		if (this.map == null)
			return;
		
		// Ic�ne de l'application
		Image icone = Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + File.separator + "img" + File.separator + "icon.gif");
		this.setIconImage(icone);
		
		this.setTargetCursor();
	
		this.setTitle("IMACDefense - " + map.getName()); // On donne un titre � l'application
		//this.setSize(map.getWidth()*Tile.getWidth() + 6, map.getHeight()*Tile.getHeight() + 28); // On donne une taille � notre fen�tre
		
		this.setLocationRelativeTo(null); //On centre la fen�tre sur l'�cran
		this.setResizable(false); // On interdit le redimensionnement de la fen�tre
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // On dit � l'application de se fermer lors du clic sur la croix

		// On impose un layout au contentPane afin que ses dimensions ne prennent pas en compte les bords de la fen�tre
		Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());
        
        //On pr�vient notre JFrame que notre JPanel sera son content pane
		try {
			this.pan = new PanMap (map, ihm);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.pan.setDoubleBuffered(true);
		
		int ihmsize = 150;
		
		this.pan.setPreferredSize(new Dimension(map.getWidth()*Tile.getWidth() + ihmsize, map.getHeight()*Tile.getHeight()));
		
		this.pan.setLayout(null);
		
		JButton buttonConstruct = new JButton("Construire");
	    buttonConstruct.setBounds(map.getWidth()*Tile.getWidth(), 100, ihmsize, 30);
	    pan.add(buttonConstruct);
	    
	    contentPane.add(this.pan);
	    pack ();
	    this.setVisible(true);
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
		Point mousepoint = new Point(e.getX(), e.getY());
		// On v�rifie si le joueur a cliqu� sur un objet selectionnable.
		SelectableObject object = Player.whereDidIClick(mousepoint, map, ihm);
		
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
			 * Si le dernier objet s�lectionn� est une base du joueur, (! A IMPLEMENTER)
			 * le joueur a voulu faire de la base click�e la cible de la base s�lectionn�e.
			 * On v�rifie donc si :
			 * 1 - l'objet click� est une base
			 * 2 - l'objet s�lectionn� est une base
			 */
			else if (object instanceof Base && Player.getLastObjectSelected() instanceof Base)
			{
				((Base) Player.getLastObjectSelected()).setTarget ((Base) object);
				Agent agent = ((Base) Player.getLastObjectSelected()).sendAgent(map);
				Action action = new MoveAgentAction(agent, this, 1);
				action.run();
				return;
			}
			
			/*
			 * Sinon, le joueur a voulu s�lectionner l'objet.
			 * On d�s�lectionne l'objet pr�c�demment s�lectionn� et on s�lectionne l'objet click�.
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
			
			if (object instanceof Tower)
			{
				ihm.setTowerCursor((Tower)object);
				Player.setConstructingNow(true);
			}
			
			/*
			 * Puis on redessine les �l�ments.
			 */
			pan.repaint();
		}
		else
		{
			Buttress tileObject = Player.didIClickInMyZone(mousepoint, map);
			if (tileObject != null && Player.isConstructingNow())
			{
				// A terminer
				try {
					Tower tower = tileObject.getZone().getOwner().construct(ihm.getSelectedTowerClass().newInstance(), map, tileObject.getZone(), (int)mousepoint.getX()/20, (int)mousepoint.getY()/20);
					if (tower != null)
						pan.repaint((int)tower.getCoordInTiles().getX()*Tile.getWidth(), (int)tower.getCoordInTiles().getY()*Tile.getHeight(), tower.getObjectWidth()*Tile.getWidth(), tower.getObjectHeight()*Tile.getHeight());
					
					this.setTargetCursor();
				} catch (InstantiationException | IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		/*char c = e.getKeyChar();
			System.out.println(c);*/
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		char c = e.getKeyChar();
			System.out.println(c);
	}
	
	public void setTargetCursor ()
	{
		// Curseur Cible
		Image cursorImage = Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + File.separator + "img" + File.separator + "cursor.png");
		// On r�cup�re la dimension qu'aura la curseur � l'�cran
		Dimension cursorDimension = Toolkit.getDefaultToolkit().getBestCursorSize(15, 15);
		// Le curseur est en forme de croix (cible), on veut donc que le "hotspot" se situe au centre du curseur, donc on divise les dimensions r�cup�r�es par 2.
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point((int)cursorDimension.getWidth()/2, (int)cursorDimension.getHeight()/2), "Cursor");
		this.setCursor(cursor);
	}
}