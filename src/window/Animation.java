package window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import map.Mapping;

public class Animation implements ActionListener {

	protected Timer timer = null;
	protected Mapping map = null;
	protected PanMap pan = null;

	public Animation(GraphicalInterface frame, int timer) {
		// TODO Auto-generated constructor stub
		this.timer = new Timer (timer, this);
		this.map = frame.getMap();
		this.pan = frame.getPan();
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
