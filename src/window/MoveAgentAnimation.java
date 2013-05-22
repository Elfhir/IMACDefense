package window;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import map.tiles.Tile;
import agents.Agent;

public class MoveAgentAnimation extends Animation {

	public MoveAgentAnimation(GraphicalInterface frame, int timer) {
		super(frame, timer);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		ArrayList<Agent> agents = map.getAgents();
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
        }
	}
}
