package gameengine;

import java.awt.Color;
import java.awt.Point;

import javax.swing.SwingUtilities;

import basis.Base;

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
				Player player = new Player (1, Player.PlayerColor.red);
				Tower tower = new FreezeTower ();
				Tower tower2 = new LaserTower ();
				Tower tower3 = new LaserTower ();
				player.construct (tower, map, 2, 0);
				player.construct (tower2, map, 4, 0);
				player.construct (tower3, map, 6, 2);
				Agent agent = new Agent (new Point (0, 0), player);
				map.agent = agent;
				Base base = new Base(10, player);
				map.getBasis().put (new Point (2,3), base);
				
				Base base2 = new Base(5, player);
				map.getBasis().put (new Point (6,5), base2);
				
				// On crée une nouvelle instance de notre JDialog
				GraphicalInterface window = new GraphicalInterface(map);
				window.setVisible(true); //On la rend visible
			}
		});
	}
}
