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
		build();//On initialise notre fenêtre
	}
 
	private void build(){
		setTitle("IMACDefense"); //On donne un titre à l'application
		setSize(600,400); //On donne une taille à notre fenêtre
		setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
		setResizable(false); //On interdit la redimensionnement de la fenêtre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix
	}
	
	public static void main(String[] args) {
		System.out.println("Hello World");
	}
}