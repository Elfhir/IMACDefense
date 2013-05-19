package window;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;

import map.Mapping;
import map.tiles.Tile;

public class GraphicalInterface extends JFrame
{	
	public GraphicalInterface(){
		super();
		build();//On initialise notre fenêtre
	}
 
	private void build(){
		Mapping map = new Mapping ("map1.xml");
		
		/* Icône de l'application */
		Image icone = Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + File.separator + "img" + File.separator + "icon.gif");
		setIconImage(icone);
		
		/* Curseur Cible */
		Image cursorImage = Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + File.separator + "img" + File.separator + "cursor.png");
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(1, 1), "Cursor");
		setCursor(cursor);		
		
		setTitle("IMACDefense - " + map.getName()); // On donne un titre à l'application
		setSize(map.getWidth()*Tile.getWidth() + 6,map.getHeight()*Tile.getHeight() + 28); // On donne une taille à notre fenêtre
		setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
		setResizable(false); // On interdit le redimensionnement de la fenêtre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // On dit à l'application de se fermer lors du clic sur la croix

		//Instanciation d'un objet JPanel
	    JPanel pan = new Panel(map);
	    //On prévient notre JFrame que notre JPanel sera son content pane
	    this.setContentPane(pan);
	    
	    this.setVisible(true);
	}
	
	public static void main(String[] args) {
	}
}