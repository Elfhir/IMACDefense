package gameengine;

import javax.swing.SwingUtilities;

import map.Mapping;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new GameEngineRunnable(new Mapping ("map1.xml")));
	}
}
