package gameengine;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import basis.Base;

import window.GraphicalInterface;

public class IncreaseNbHostedAgentsAction extends Action {

	public IncreaseNbHostedAgentsAction(GraphicalInterface frame, int timer) {
		super(frame, timer);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {		
		// TODO Auto-generated method stub
		Hashtable<Point, Base> mapBasis = map.getBasis();
		Set<Point> set = mapBasis.keySet();
		Iterator<Point> it = set.iterator();

		while (it.hasNext())
		{
			Point currentPoint = it.next();
			Base currentBase = mapBasis.get(currentPoint);
			
			// Seules les bases qui appartiennent à un joueur peuvent augmenter en effectif au cours du temps.
			if (currentBase.getOwner() != null && currentBase.getNbHostedAgents() + 1 < currentBase.getMaxCapacity())
			{
				currentBase.setNbHostedAgents(currentBase.getNbHostedAgents() + 1);
				pan.repaint();
			}
		}
	}

}
