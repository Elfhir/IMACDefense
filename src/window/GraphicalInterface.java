package window;

import javax.swing.JFrame;

public class GraphicalInterface extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -510113458488362987L;
	
	public GraphicalInterface(){
		super();
		build();//On initialise notre fen�tre
	}
 
	private void build(){
		setTitle("IMACDefense"); //On donne un titre � l'application
		setSize(600,400); //On donne une taille � notre fen�tre
		setLocationRelativeTo(null); //On centre la fen�tre sur l'�cran
		setResizable(false); //On interdit la redimensionnement de la fen�tre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit � l'application de se fermer lors du clic sur la croix
	}
	
	public static void main(String[] args) {
		System.out.println("Hello World");
	}
}