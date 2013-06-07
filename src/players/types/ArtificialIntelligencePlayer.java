package players.types;

import gameengine.GameEngine;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import basis.Base;

import map.Mapping;
import map.Zone;
import players.Player;
import players.types.aiStrategy.AIStrategy;
import players.types.aiStrategy.ExplorerStrategy;
import players.types.aiStrategy.MixedStrategy;
import players.types.aiStrategy.OffensiveStrategy;

public class ArtificialIntelligencePlayer extends Player {
	
	AIStrategy strategy;

	public ArtificialIntelligencePlayer() {
	}

	public ArtificialIntelligencePlayer(int id, String name, PlayerColor color) {
		super(id, name, color);
		this.setRandomStrategy();
	}
	
	public ArrayList<Zone> getMyZones (Mapping map)
	{
		ArrayList<Zone> zones = new ArrayList<Zone>();
		for (Zone zone : map.getZones())
		{
			if (zone != null && zone.getOwner() != null && zone.getOwner().equals(this))
			{
				zones.add (zone);
			}
		}
		return zones;
	}

	public void setRandomStrategy ()
	{
		Random r = new Random();
		int randomvalue = r.nextInt(2);
		switch (randomvalue)
		{
			case 1 :
			{
				this.strategy = new ExplorerStrategy(this);
				break;
			}
			case 2 :
			{
				this.strategy = new OffensiveStrategy(this);
				break;
			}
			default :
			{
				this.strategy = new MixedStrategy(this);
				break;
			}
		}
	}
	
	public synchronized void play (Mapping map, GameEngine frame)
	{
		/*  On choisit au hasard une action.
		 *  
		 *  1/4 de chances de ne rien faire.
		 *  1/4 de chances de construire une tour.
		 *  1/2 de chances de faire bouger des agents.
		 */
		
		Random r = new Random();
		int randomvalue = r.nextInt(4);
		
		switch (randomvalue)
		{
			case 1:
			{
				this.strategy.constructTower(map, frame);
				break;
			}
			case 2 :
			{
				break;
			}
			default :
			{
				this.strategy.moveAgents(map, frame);
				break;
			}
		}
	}
}
