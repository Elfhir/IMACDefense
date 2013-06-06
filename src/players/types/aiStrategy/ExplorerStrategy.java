package players.types.aiStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

import map.Mapping;
import towers.Tower;
import towers.towertypes.*;

public class ExplorerStrategy implements AIStrategy {
	
	/*
	 * L'Explorer Strategy consiste à tenter de conquérir les bases neutres d'abord
	 * et n'attaquer un ennemi que lorsqu'il s'attaque au joueur.
	 * Les tours sont construites pour protéger les bases.
	 */
	
	private enum TowerProbability
	{
		/* 
		 * Tours préférées : FreezeTower et BombTower
		 * Probabilité de construction des tours :
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
		private int probability;
		private static int totalCummulatedProbability;
		private int cummulatedProbability;
		
		private TowerProbability (Class<? extends Tower> towerclass, int probability)
		{
			this.towerclass = towerclass;
			this.probability = probability;
			TowerProbability.increaseTotalCummulatedProbability(probability);
			this.cummulatedProbability = TowerProbability.getTotalCummulatedProbability();
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
			 * Génération d'un nombre aléatoire entre 0 et TotalCummulatedProbability
			 */
			
			Random r = new Random();
			int randomvalue = r.nextInt(TowerProbability.getTotalCummulatedProbability());
			
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
	}

	public ExplorerStrategy() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void constructTower(Mapping map) {
		/*
		 * Les tours sont construites près des bases amies et loin si possible des tours ennemies.
		 * 
		 * 
		 * Si pas assez d'argent pour construire la tour choisie, choisira une tour plus "côtée" et moins chère.
		 * Si pas assez d'argent encore, choisira une tour moins "côtée" et moins chère.
		 */
		
		/*
		 * Génération d'un nombre aléatoire
		 */
	}

	@Override
	public void moveAgents() {
		// TODO Auto-generated method stub

	}

}
