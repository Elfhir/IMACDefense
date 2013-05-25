package gameengine;

import java.awt.Point;
import java.awt.event.ActionEvent;
import window.GraphicalInterface;

import map.tiles.Tile;
import agents.Agent;

public class MoveAgentAction extends Action {
	
	Agent agent = null;
	
	public MoveAgentAction(Agent agent, GraphicalInterface frame, int timer) {
		super(frame, timer);
		this.agent = agent;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		/*ArrayList<Agent> agents = map.getAgents();
        for (Agent agent:agents)
        {
        	Point point = agent.getPosition2d();
        	int tileheight = Tile.getHeight();
        	int tilewidth = Tile.getWidth();
        	pan.repaint ((int)point.getX(),(int)(point.getY()), tilewidth, tileheight);  //This is what paints the animation again (IMPORTANT: won't work without this).
        	agent.move(map);
        	if (!agent.getPosition2d().equals(point))
        	{
        		pan.repaint ((int)agent.getPosition2d().getX(),(int)agent.getPosition2d().getY(), tilewidth, tileheight);
        	}
        }*/
		if (agent == null)
		{
			return;
		}
		
		Point point = agent.getPosition2d();
    	int tileheight = Tile.getHeight();
    	int tilewidth = Tile.getWidth();
    	
    	pan.repaint ((int)point.getX(),(int)(point.getY()-tileheight/2), tilewidth, tileheight*2);

    	agent.move(map);
    	
    	// Si l'agent est arrivé à destination
		if (agent.arrivedAtDestination())
		{
			agent.getTarget().receiveAgent(agent);
			// On repeint la base-cible, pour que le nombre de cette base se mette à jour.
			pan.repaint();
			// On enlève l'agent de la map : on en n'a plus besoin.
			map.getAgents().remove(agent);
			// L'agent est mis à null.
			agent = null;
			// On stoppe le timer.
			this.timer.stop();
			// On sort de la fonction.
			return;
		}
		
    	if (!agent.getPosition2d().equals(point))
    	{
    		pan.repaint ((int)agent.getPosition2d().getX(),(int)agent.getPosition2d().getY()-tileheight/2, tilewidth, tileheight*2);
    	}
	}
}
