package window;

import gameengine.GameEngine;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;

public class IHMMenu implements IHM {
	
	PanInitialMenu pan;
	GameEngine window;

	public IHMMenu(GameEngine window) {
		this.window = window;
		
		Dimension panDimension = new Dimension(550, 400);
		Dimension buttonDimension = new Dimension(200, 30);
		
	    JLabel labelTitle = new JLabel("IMACDefense");
	    labelTitle.setFont(new Font("Monospaced", Font.BOLD, 30));
	    labelTitle.setBounds((int)(panDimension.getWidth()/2 - buttonDimension.getWidth()/2), 60, (int)buttonDimension.getWidth(), (int)buttonDimension.getHeight());
	    
	    JButton buttonCreateGame = new JButton("Nouvelle partie");
	    buttonCreateGame.setBounds((int)(panDimension.getWidth()/2 - buttonDimension.getWidth()/2), 150, (int)buttonDimension.getWidth(), (int)buttonDimension.getHeight());
	    buttonCreateGame.addActionListener(new ChangeIHMModeAction(window, GameEngine.IHMMode.InGame));
	    
	    JButton buttonOptions = new JButton("Options de jeu");
	    buttonOptions.setBounds((int)(panDimension.getWidth()/2 - buttonDimension.getWidth()/2), 190, (int)buttonDimension.getWidth(), (int)buttonDimension.getHeight());
	    
	    window.setPan(new PanInitialMenu());
		
		this.pan = (PanInitialMenu)window.getPan();
		
		this.pan.setPreferredSize(panDimension);
		
		this.pan.setLayout(null);
		this.pan.setDoubleBuffered(true);
	    
	    pan.add(buttonCreateGame);
	    pan.add(buttonOptions);
	    pan.add(labelTitle);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
