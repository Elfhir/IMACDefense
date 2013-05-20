package gameengine;

import java.awt.Point;

import javax.swing.SwingUtilities;

import agents.Agent;

import map.Mapping;

import players.Player;
import towers.Tower;
import towers.towertypes.FreezeTower;
import towers.towertypes.LaserTower;
import window.GraphicalInterface;

public class GameEngine {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				Mapping map = new Mapping("map1.xml");
				Player player = new Player (1, Player.Color.red);
				Tower tower = new FreezeTower ();
				Tower tower2 = new LaserTower ();
				Tower tower3 = new LaserTower ();
				player.construct (tower, map, 2, 0);
				player.construct (tower2, map, 4, 0);
				player.construct (tower3, map, 6, 2);
				Agent agent = new Agent (new Point (0, 0), player);
				map.agent = agent;
				
				// On cr�e une nouvelle instance de notre JDialog
				GraphicalInterface window = new GraphicalInterface(map);
				window.setVisible(true); //On la rend visible
			}
		});
	}
}
