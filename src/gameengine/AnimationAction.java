package gameengine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import window.PanMap;

import map.Mapping;

public class AnimationAction implements ActionListener {

	protected Timer timer = null;
	protected Mapping map = null;
	protected PanMap pan = null;
	protected GameEngine frame = null;

	public AnimationAction(GameEngine frame, int timer) {
		// TODO Auto-generated constructor stub
		this.timer = new Timer (timer, this);
		this.frame = frame;
		this.map = frame.getMap();
		this.pan = (PanMap)frame.getPan();
	}

	public void run ()
    {
       if (timer != null)
        	timer.start (); //This starts the animation.
    }

	@Override
	public void actionPerformed (ActionEvent e)
    {
		timer.stop();
    }

}
