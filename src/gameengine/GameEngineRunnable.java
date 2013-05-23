package gameengine;

import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import javax.swing.SwingUtilities;

import map.Mapping;
import map.Zone;
import players.Player;
import window.GraphicalInterface;
import agents.Agent;
import basis.Base;

public class GameEngineRunnable implements Runnable {
	
	private static GraphicalInterface window = null;
	Mapping map = null;
	ArrayList<Player> players = new ArrayList<Player>();
	//ArrayList<Thread> basisthreads = new ArrayList<Thread>();

	public GameEngineRunnable(Mapping map, ArrayList<Player> players) {
		// TODO Auto-generated constructor stub
		this.map = map;
		this.players = players;
		
		if (map != null && players != null)
		{
			ArrayList<Zone> zones = map.getZones();
			if (zones != null)
			{
				for (Zone zone:zones)
				{
					for (Player player:players)
					{
						if (zone.getPlayerId() == player.getId())
						{
							zone.setOwner(player);
						}
					}
				}
			}
		}

		/* --- CREATION DES THREADS DE BASES --- */

		/* Initialisation de l'itérateur de la table de Bases */
		/*Hashtable<Point, Base> mapBasis = map.getBasis();
		Set<Point> set = mapBasis.keySet();
		Iterator<Point> it = set.iterator();
		
		while (it.hasNext())
		{
			Point currentPoint = it.next();
			Base currentBase = mapBasis.get(currentPoint);
			if (currentBase != null)
			{
				Thread thread = new Thread(new BaseRunnable (currentBase, map));
				this.basisthreads.add(thread);
				thread.start();
			}
		}*/
	}
	
	public void run(){
		if (map == null || players == null)
			return;

		/* On crée une nouvelle instance de notre JDialog */
		GameEngineRunnable.setWindow (new GraphicalInterface(map));
		window.getContentPane().addMouseMotionListener(window);
		IncreaseNbHostedAgentsAction action = new IncreaseNbHostedAgentsAction(window, 5000);
		action.run();
		//Action moveagentanimation = new MoveAgentAction (window, 0);
		/*if (SwingUtilities.isEventDispatchThread()) {
			moveagentanimation.run();
		} else {*/
			 // SwingUtilities.invokeLater(moveagentanimation);
		/*}*/
	}

	public static GraphicalInterface getWindow() {
		return window;
	}

	public static void setWindow(GraphicalInterface window) {
		GameEngineRunnable.window = window;
	}
}
