package window;

import gameengine.GameEngine;

import java.awt.Dimension;
import java.awt.event.MouseEvent;

public class IHMChooseLevel implements IHM {

	private PanInitialMenu pan;

	public IHMChooseLevel(GameEngine window) {
		
		Dimension panDimension = new Dimension(550, 400);
		this.pan = new PanInitialMenu(window);
		
		this.pan.setPreferredSize(panDimension);
		
		this.pan.setLayout(null);
		this.pan.setDoubleBuffered(true);
	    
	    window.setPan(this.pan);
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

}
