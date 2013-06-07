package players.types.aiStrategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

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

public class ExplorerStrategy implements AIStrategy {
	
	/*
	 * L'Explorer Strategy consiste � tenter de conqu�rir les bases neutres d'abord
	 * et n'attaquer un ennemi que lorsqu'il s'attaque au joueur.
	 * Les tours sont construites pour prot�ger les bases.
	 */
	
	private ArtificialIntelligencePlayer player;
	
	private enum TowerProbability
	{
		/* 
		 * Tours pr�f�r�es : FreezeTower et BombTower
		 * Probabilit� de construction des tours :
		 * BombTower - 25%
		 * FreezeTower - 25%
		 * LaserTower - 15%
		 * MedicalTower - 20%
		 * SubmachineGunTower - 15%
		 */
		
		pBombTower(BombTower.class, 25),
		pFreezeTower(FreezeTower.class, 25),
		pLaserTower(LaserTower.class, 15),
		pMedicalTower(MedicalTower.class, 20),
		pSubMachineGunTower(SubmachineGunTower.class, 15);
		
		private Class<? extends Tower> towerclass;
		private static int totalCummulatedProbability;
		private int cummulatedProbability;
		
		private TowerProbability (Class<? extends Tower> towerclass, int probability)
		{
			this.towerclass = towerclass;
			TowerProbability.increaseTotalCummulatedProbability(probability);
			this.cummulatedProbability = TowerProbability.getTotalCummulatedProbability();
		}

		public Class<? extends Tower> getTowerclass() {
			return towerclass;
		}

		private static void increaseTotalCummulatedProbability(int totalCummulatedProbability) {
			TowerProbability.totalCummulatedProbability += totalCummulatedProbability;
		}
		
		private static int getTotalCummulatedProbability() {
			return totalCummulatedProbability;
		}

		public static TowerProbability getRandomTowerProbability ()
		{
			/*
			 * G�n�ration d'un nombre al�atoire entre 0 et TotalCummulatedProbability
			 */
			
			Random r = new Random();
			int randomvalue = r.nextInt(TowerProbability.getTotalCummulatedProbability());
			
			/*
			 * On choisit la towerprobability correspondant � ce nombre al�atoire.
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
	}

	public ExplorerStrategy(ArtificialIntelligencePlayer player) {
		this.player = player;
	}

	@Override
	public void constructTower(Mapping map) {
		/*
		 * Les tours sont construites pr�s des bases amies et loin si possible des tours ennemies.
		 */
		
		/*
		 * Choix d'une tour au hasard
		 */
		
		TowerProbability towerp = TowerProbability.getRandomTowerProbability();
		
		ArrayList<Zone> zones = map.getZones();
		Iterator<Zone> it = zones.iterator();
		
		while (it.hasNext())
		{
			Zone currentZone = it.next();
			if (currentZone != null && currentZone.getOwner() != null && currentZone.getOwner().equals(player))
			{
				/*
				 *  On cherche jusqu'� dix fois o� on pourrait construire, si au bout des 10 fois on n'a pas r�ussi � trouver, on laisse tomber.
				 */
				int counter = 0;
				while (counter < 10)
				{
					Buttress currentbuttress = currentZone.selectRandomButtressTile();
					try {
						if (currentbuttress != null && currentbuttress.getCoordsInMap() != null && player.canIConstruct(towerp.getTowerclass().newInstance(), map, (int)currentbuttress.getCoordsInMap().getX(), (int)currentbuttress.getCoordsInMap().getY()))
						{
							player.construct(towerp.getTowerclass(), map, currentZone, (int)currentbuttress.getCoordsInMap().getX(), (int)currentbuttress.getCoordsInMap().getY());
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
	}

	@Override
	public void moveAgents() {
	}

}
