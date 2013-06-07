package window;

import gameengine.GameEngine;
import gameengine.GameEngine.IHMMode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import map.Mapping;

public class PanInitialMenu extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GameEngine window;

	public PanInitialMenu(GameEngine window) {
		this.window = window;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		paintInterfaceBackground (g);
		if (window.getIhmmode() == IHMMode.InitialMenu)
		{
			paintInitialInterfaceButtons();
		}
		else
		{
			paintChooseLevelInterfaceButtons();
		}
	}
	
	public void paintInterfaceBackground (Graphics g)
	{
		int width = (int) this.getWidth();
		int height = (int) this.getHeight();
		g.setColor(Color.darkGray);
		g.fillRect(0, 0, width, height);
	}
	
	public void paintInitialInterfaceButtons ()
	{
		int height = this.getHeight();
		int width = this.getWidth();
		
		int buttonWidth = 200;
		int buttonHeight = 30;
		
		String title = "IMACDefense";
	    JLabel labelTitle = new JLabel(title);
	    labelTitle.setForeground(Color.white);
	    Font font = new Font("Monospaced", Font.BOLD, 30);
	    labelTitle.setFont(font);
	    labelTitle.setBounds((int)(width/2 - labelTitle.getFontMetrics(font).stringWidth(title)/2), 60, width, height);
	    
	    JButton buttonCreateGame = new JButton("Nouvelle partie");
	    buttonCreateGame.setBounds((int)(width/2 - buttonWidth/2), 150, buttonWidth, buttonHeight);
	    buttonCreateGame.addActionListener(new ChangeIHMModeAction(window, GameEngine.IHMMode.ChooseLevel, null));
	    buttonCreateGame.setBackground(Color.darkGray);
	    buttonCreateGame.setForeground(Color.white);
	    
	    JButton buttonOptions = new JButton("Options de jeu");
	    buttonOptions.setBounds((int)(width/2 - buttonWidth/2), 190, buttonWidth, buttonHeight);
	    buttonOptions.setBackground(Color.darkGray);
	    buttonOptions.setForeground(Color.white);
	    
	    window.getPan().add(buttonCreateGame);
	    window.getPan().add(buttonOptions);
	    window.getPan().add(labelTitle);
	}
	
	public void paintChooseLevelInterfaceButtons ()
	{
		int width = this.getWidth();
		int buttonWidth = width;
		int buttonHeight = 30;
		
		Mapping level1 = new Mapping ("map1.xml");
		Mapping level2 = new Mapping ("map2.xml");
		
		String title = "Nouvelle partie";
	    JLabel labelTitle = new JLabel(title);
	    labelTitle.setForeground(Color.white);
	    Font font = new Font("Monospaced", Font.BOLD, 30);
	    labelTitle.setFont(font);
	    labelTitle.setBounds((int)(width/2 - labelTitle.getFontMetrics(font).stringWidth(title)/2), 60, buttonWidth, buttonHeight);
	    
	    JButton buttonLevel1 = new JButton("Level 1 - " + level1.getName());
	    buttonLevel1.setBounds((int)(width/2 - buttonWidth/2), 150, buttonWidth, buttonHeight);
	    buttonLevel1.addActionListener(new ChangeIHMModeAction(window, GameEngine.IHMMode.InGame, level1));
	    buttonLevel1.setBackground(Color.darkGray);
	    buttonLevel1.setForeground(Color.white);
	    
	    JButton buttonLevel2 = new JButton("Level 2 - " + level2.getName());
	    buttonLevel2.setBounds((int)(width/2 - buttonWidth/2), 180, buttonWidth, buttonHeight);
	    buttonLevel2.addActionListener(new ChangeIHMModeAction(window, GameEngine.IHMMode.InGame, level2));
	    buttonLevel2.setBackground(Color.darkGray);
	    buttonLevel2.setForeground(Color.white);
	    
	    window.getPan().add(labelTitle);
	    window.getPan().add(buttonLevel1);
	    window.getPan().add(buttonLevel2);
	}
}
