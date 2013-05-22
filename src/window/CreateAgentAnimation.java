package window;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import agents.Agent;
import basis.Base;

public class CreateAgentAnimation extends Animation {

	public CreateAgentAnimation(GraphicalInterface frame, int timer) {
		super(frame, timer);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {        
        Hashtable<Point, Base> mapBasis = map.getBasis();
		Set<Point> set = mapBasis.keySet();
		Iterator<Point> it = set.iterator();

		while (it.hasNext())
		{
			Point currentPoint = it.next();
			Base currentBase = mapBasis.get(currentPoint);
			if (currentBase != null)
			{
				for (int i = 0; i < currentBase.getNbCreatableAgents(); ++i)
				{
					Agent newagent = currentBase.createAgent();
					if (newagent != null)
					{
						map.getAgents().add(newagent);
					}
				}
			}
		}
	}

}
