package players.types;

import java.util.Random;

import map.Mapping;

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

	public void setRandomStrategy ()
	{
		Random r = new Random();
		int randomvalue = r.nextInt(2);
		/*switch (randomvalue)
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
		}*/
		this.strategy = new ExplorerStrategy(this);
	}
	
	public void play (Mapping map)
	{
		/*  On choisit au hasard une action.
		 *  
		 *  1/3 de chances de ne rien faire.
		 *  1/3 de chances de construire une tour.
		 *  1/3 de chances de faire bouger des agents.
		 */
		
		Random r = new Random();
		int randomvalue = 1 + r.nextInt(2);
		
		/*switch (randomvalue)
		{
			case 1:
			{
				this.strategy.constructTower(map);
				break;
			}
			case 2 :
			{
				this.strategy.moveAgents();
				break;
			}
			default :
				break;
		}*/
		
		this.strategy.constructTower(map);
	}
}
