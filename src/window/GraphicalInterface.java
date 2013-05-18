package window;

import javax.swing.JFrame;
import javax.swing.JPanel;

import map.Mapping;

public class GraphicalInterface extends JFrame
{	
	public GraphicalInterface(){
		super();
		build();//On initialise notre fen�tre
	}
 
	private void build(){
		setTitle("IMACDefense"); // On donne un titre � l'application
		setSize(600,440); // On donne une taille � notre fen�tre
		setLocationRelativeTo(null); //On centre la fen�tre sur l'�cran
		setResizable(false); // On interdit le redimensionnement de la fen�tre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // On dit � l'application de se fermer lors du clic sur la croix

		//Instanciation d'un objet JPanel
	    JPanel pan = new Panel(new Mapping ("map1.xml"));
	    //On pr�vient notre JFrame que notre JPanel sera son content pane
	    this.setContentPane(pan);
	    
	    this.setVisible(true);
	}
	
	public static void main(String[] args) {
	}
}