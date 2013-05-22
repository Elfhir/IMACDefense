package gameengine;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import map.Mapping;
import map.Zone;
import players.Player;
import window.Animation;
import window.GraphicalInterface;
import window.MoveAgentAnimation;
import agents.Agent;
import basis.Base;

public class GameEngineRunnable implements Runnable {
	
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
		GraphicalInterface window = new GraphicalInterface(map);
		Animation moveagentanimation = new MoveAgentAnimation (window, 0);
		moveagentanimation.run();
	}
}
