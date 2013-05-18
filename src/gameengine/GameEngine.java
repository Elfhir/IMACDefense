package gameengine;

import javax.swing.SwingUtilities;
import window.GraphicalInterface;

public class GameEngine {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				// On crée une nouvelle instance de notre JDialog
				GraphicalInterface window = new GraphicalInterface();
				window.setVisible(true); //On la rend visible
			}
		});
	}
}
