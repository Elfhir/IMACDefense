package window;

import java.awt.Image;
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
		build();//On initialise notre fen�tre
	}
 
	private void build(){
		Mapping map = new Mapping ("map1.xml");
		Image icone = Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + File.separator + "img" + File.separator + "icon.gif");
		setIconImage(icone);
		setTitle("IMACDefense - " + map.getName()); // On donne un titre � l'application
		setSize(map.getWidth()*Tile.getWidth() + 6,map.getHeight()*Tile.getHeight() + 28); // On donne une taille � notre fen�tre
		setLocationRelativeTo(null); //On centre la fen�tre sur l'�cran
		setResizable(false); // On interdit le redimensionnement de la fen�tre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // On dit � l'application de se fermer lors du clic sur la croix

		//Instanciation d'un objet JPanel
	    JPanel pan = new Panel(map);
	    //On pr�vient notre JFrame que notre JPanel sera son content pane
	    this.setContentPane(pan);
	    
	    this.setVisible(true);
	}
	
	public static void main(String[] args) {
	}
}