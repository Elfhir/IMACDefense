package window;

import gameengine.GameEngine;
import gameengine.GameEngine.IHMMode;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import map.Mapping;

public class ChangeIHMModeAction implements ActionListener {
	
	private GameEngine window;
	private IHMMode mode;
	private Mapping map;

	public ChangeIHMModeAction(GameEngine window, GameEngine.IHMMode mode, Mapping map) {
		// TODO Auto-generated constructor stub
		this.window = window;
		this.mode = mode;
		this.map = map;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		window.setMap(map);
		if (window != null && mode != null)
		{
			window.setIHMMode(mode);
		}
	}

}
