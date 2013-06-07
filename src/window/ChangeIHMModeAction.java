package window;

import gameengine.GameEngine;
import gameengine.GameEngine.IHMMode;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeIHMModeAction implements ActionListener {
	
	private GameEngine window;
	private IHMMode mode;

	public ChangeIHMModeAction(GameEngine window, GameEngine.IHMMode mode) {
		// TODO Auto-generated constructor stub
		this.window = window;
		this.mode = mode;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (window != null && mode != null)
			window.setIHMMode(mode);
	}

}
