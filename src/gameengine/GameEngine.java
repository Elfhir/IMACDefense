package gameengine;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

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
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player (1, Player.PlayerColor.red));
		players.add(new Player (2, Player.PlayerColor.green));
		players.add(new Player (3, Player.PlayerColor.yellow));
		players.add(new Player (4, Player.PlayerColor.blue));
		SwingUtilities.invokeLater(new GameEngineRunnable(new Mapping ("map1.xml"), players));
	}
}
