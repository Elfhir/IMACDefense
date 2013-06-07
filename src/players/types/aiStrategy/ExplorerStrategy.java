package players.types.aiStrategy;

import java.util.Random;

import players.types.ArtificialIntelligencePlayer;
import towers.Tower;
import towers.towertypes.BombTower;
import towers.towertypes.FreezeTower;
import towers.towertypes.LaserTower;
import towers.towertypes.MedicalTower;
import towers.towertypes.SubmachineGunTower;

public class ExplorerStrategy extends AIStrategy {

	/*
	 * L'Explorer Strategy consiste à tenter de conquérir les bases neutres d'abord
	 * et n'attaquer un ennemi que lorsqu'il s'attaque au joueur.
	 * Les tours sont construites pour protéger les bases.
	 */
	
	private enum TowerProbability implements TowerProbabilityInterface
	{
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
	
	public ExplorerStrategy(ArtificialIntelligencePlayer player) {
		super(player);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public TowerProbabilityInterface getRandomTowerProbability()
	{
		return TowerProbability.getRandomTowerProbability();
	}
	
}
