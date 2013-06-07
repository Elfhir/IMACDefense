package players.types.aiStrategy;

import gameengine.Action;
import gameengine.MoveBulletAction;
import gameengine.TowerShootAction;

import java.util.ArrayList;
import java.util.Random;

import agents.Agent;
import basis.Base;

import map.Mapping;
import map.Zone;
import map.tiles.Buttress;
import players.types.ArtificialIntelligencePlayer;
import towers.Tower;
import towers.towertypes.BombTower;
import towers.towertypes.FreezeTower;
import towers.towertypes.LaserTower;
import towers.towertypes.MedicalTower;
import towers.towertypes.SubmachineGunTower;
import window.GraphicalInterface;

public class AIStrategy {
	
	private enum TowerProbability implements TowerProbabilityInterface
	{
		pBombTower(BombTower.class, 20),
		pFreezeTower(FreezeTower.class, 20),
		pLaserTower(LaserTower.class, 20),
		pMedicalTower(MedicalTower.class, 20),
		pSubMachineGunTower(SubmachineGunTower.class, 20);
		
		private Class<? extends Tower> towerclass;
		private static int totalCummulatedProbability;
		private int cummulatedProbability;
		
		private TowerProbability (Class<? extends Tower> towerclass, int probability)
		{
			this.towerclass = towerclass;
			this.increaseTotalCummulatedProbability(probability);
			this.cummulatedProbability = this.getTotalCummulatedProbability();
		}

		public Class<? extends Tower> getTowerclass() {
			return towerclass;
		}

		public void increaseTotalCummulatedProbability(int totalCummulatedProbability) {
			TowerProbability.totalCummulatedProbability += totalCummulatedProbability;
		}
		
		public int getTotalCummulatedProbability() {
			return totalCummulatedProbability;
		}

		public static TowerProbability getRandomTowerProbability ()
		{
			/*
			 * Génération d'un nombre aléatoire entre 0 et TotalCummulatedProbability
			 */
			
			Random r = new Random();
			int randomvalue = r.nextInt(TowerProbability.totalCummulatedProbability);
			
			/*
			 * On choisit la towerprobability correspondant à ce nombre aléatoire.
			 */
			
			int lastCummulatedProbability = 0;
			for (TowerProbability towerprobability : TowerProbability.values())
			{
				if (lastCummulatedProbability <= randomvalue && towerprobability.cummulatedProbability >= randomvalue)
					return towerprobability;
				lastCummulatedProbability = towerprobability.cummulatedProbability;
			}
			return null;
		}
	};

	private ArtificialIntelligencePlayer player;
	
	public AIStrategy(ArtificialIntelligencePlayer player) {
		this.player = player;
	}
	
	public synchronized void constructTower(Mapping map, GraphicalInterface frame) {
		/*
		 * Les tours sont construites près des bases amies et loin si possible des tours ennemies.
		 */
		
		/*
		 * Choix d'une tour au hasard
		 */
		
		TowerProbabilityInterface towerp = getRandomTowerProbability();
		
		if (towerp == null || map == null)
			return;
		
		ArrayList<Zone> zones = player.getMyZones(map);
		
		if (zones.isEmpty() || zones.size() == 0)
			return;
		
		Random r = new Random();
		int randomindex = r.nextInt(zones.size())-1;
		
		if (randomindex < 0 || randomindex > zones.size()-1)
			return;
		
		Zone currentZone = zones.get(randomindex);
		if (currentZone != null && currentZone.getOwner() != null && currentZone.getOwner().equals(player))
		{
			/*
			 *  On cherche jusqu'à dix fois où on pourrait construire, si au bout des 10 fois on n'a pas réussi à trouver, on laisse tomber.
			 */
			int counter = 0;
			while (counter < 10)
			{
				Buttress currentbuttress = currentZone.selectRandomButtressTile();
				try {
					if (currentbuttress != null && currentbuttress.getCoordsInMap() != null && player.canIConstruct(towerp.getTowerclass().newInstance(), map, (int)currentbuttress.getCoordsInMap().getX(), (int)currentbuttress.getCoordsInMap().getY()))
					{
						Tower tower = player.construct(towerp.getTowerclass(), map, currentZone, (int)currentbuttress.getCoordsInMap().getX(), (int)currentbuttress.getCoordsInMap().getY());
						TowerShootAction action = new TowerShootAction(map, frame, 10000/tower.getShootSpeed(), tower);
						action.run();
						return;
					}
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				counter++;
			}
		}
	}
	
	public synchronized TowerProbabilityInterface getRandomTowerProbability()
	{
		return TowerProbability.getRandomTowerProbability();
	}

	public synchronized void moveAgents(Mapping map, GraphicalInterface frame) {
		ArrayList<Base> basis = player.getMyBasis(map);
		if (basis.isEmpty() || basis.size() == 0)
			return;
		
		Random r = new Random();
		int randomindex = r.nextInt(basis.size())-1;
		
		if (randomindex < 0 || randomindex > basis.size()-1)
			return;
		
		Base currentBase = basis.get(randomindex);
		
		if (currentBase == null)
			return;
		
		Base targetBase;
		
		do 
		{
			targetBase = map.getRandomBase();
		} while (targetBase == null || targetBase.equals(currentBase));
		
		currentBase.setTarget(targetBase);
		Agent agent = currentBase.sendAgent(map);
		Action action = new MoveBulletAction(agent, frame, 30);
		action.run();
	}
	
}
