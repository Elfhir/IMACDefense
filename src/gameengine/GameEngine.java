package gameengine;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import basis.Base;

import agents.Agent;

import map.Mapping;
import map.Zone;

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
				Player player2 = new Player (2, Player.PlayerColor.green);
				Player player3 = new Player (3, Player.PlayerColor.yellow);
				Player player4 = new Player (4, Player.PlayerColor.blue);
				Tower tower = new FreezeTower ();
				Tower tower2 = new LaserTower ();
				Tower tower3 = new LaserTower ();
				player.construct (tower, map, 2, 0);
				player.construct (tower2, map, 4, 0);
				player.construct (tower3, map, 6, 2);
				
				ArrayList<Zone> zones = map.getZones();
				for (Zone zone:zones)
				{
					if (zone.getPlayerId() == player.getId())
					{
						zone.setOwner(player);
					}
					if (zone.getPlayerId() == player2.getId())
					{
						zone.setOwner(player2);
					}
					if (zone.getPlayerId() == player3.getId())
					{
						zone.setOwner(player3);
					}
					if (zone.getPlayerId() == player4.getId())
					{
						zone.setOwner(player4);
					}
				}
				
				Agent agent = new Agent (new Point (0, 0), player);
				map.agent = agent;
				
				// On crée une nouvelle instance de notre JDialog
				GraphicalInterface window = new GraphicalInterface(map);
				window.setVisible(true); //On la rend visible
			}
		});
	}
}
