package players.types.aiStrategy;

import towers.Tower;

public interface TowerProbabilityInterface {
	public Class<? extends Tower> getTowerclass();
	public int getTotalCummulatedProbability();
}
