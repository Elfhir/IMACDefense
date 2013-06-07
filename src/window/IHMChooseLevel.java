package window;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import map.Mapping;

import gameengine.GameEngine;

public class IHMChooseLevel implements IHM {

	private JPanel pan;
	Mapping level1 = new Mapping ("map1.xml");
	Mapping level2 = new Mapping ("map2.xml");

	public IHMChooseLevel(GameEngine window) {
		
		Dimension panDimension = new Dimension(550, 400);
		Dimension buttonDimension = new Dimension(550, 30);
		
		String title = "Nouvelle partie";
	    JLabel labelTitle = new JLabel(title);
	    Font font = new Font("Monospaced", Font.BOLD, 30);
	    labelTitle.setFont(font);
	    labelTitle.setBounds((int)(panDimension.getWidth()/2 - labelTitle.getFontMetrics(font).stringWidth(title)/2), 60, (int)buttonDimension.getWidth(), (int)buttonDimension.getHeight());
	    
	    JButton buttonLevel1 = new JButton("Level 1 - " + level1.getName());
	    buttonLevel1.setBounds((int)(panDimension.getWidth()/2 - buttonDimension.getWidth()/2), 150, (int)buttonDimension.getWidth(), (int)buttonDimension.getHeight());
	    buttonLevel1.addActionListener(new ChangeIHMModeAction(window, GameEngine.IHMMode.InGame, level1));
	    
	    JButton buttonLevel2 = new JButton("Level 2 - " + level2.getName());
	    buttonLevel2.setBounds((int)(panDimension.getWidth()/2 - buttonDimension.getWidth()/2), 180, (int)buttonDimension.getWidth(), (int)buttonDimension.getHeight());
	    buttonLevel2.addActionListener(new ChangeIHMModeAction(window, GameEngine.IHMMode.InGame, level2));
	    
	    window.setPan(new PanInitialMenu());
		
		this.pan = window.getPan();
		
		this.pan.setPreferredSize(panDimension);
		
		this.pan.setLayout(null);
		this.pan.setDoubleBuffered(true);
	    
	    pan.add(buttonLevel1);
	    pan.add(buttonLevel2);
	    pan.add(labelTitle);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
