package window;

import gameengine.GameEngine;

import java.awt.Dimension;
import java.awt.event.MouseEvent;

public class IHMMenu implements IHM {
	
	PanInitialMenu pan;
	GameEngine window;

	public IHMMenu(GameEngine window) {
		this.window = window;
		
		Dimension panDimension = new Dimension(550, 400);
	    
	    window.setPan(new PanInitialMenu(window));
		
		this.pan = (PanInitialMenu)window.getPan();
		
		this.pan.setPreferredSize(panDimension);
		
		this.pan.setLayout(null);
		this.pan.setDoubleBuffered(true);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
