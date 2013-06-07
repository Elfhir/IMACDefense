package gameengine;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import map.Mapping;
import map.Zone;
import players.Dispatcher;
import players.Player;
import players.types.ArtificialIntelligencePlayer;
import players.types.HumanPlayer;
import window.IHM;
import window.IHMChooseLevel;
import window.IHMMenu;
import window.IHMinGame;

public class GameEngine extends JFrame implements MouseListener
{
	private ArrayList<Player> players;
	private static final long serialVersionUID = 1L;
	private Mapping map = null;
	private JPanel pan = null;
	private IHM ihm = null;
	private IHMMode ihmmode = IHMMode.InitialMenu;
	private Dispatcher dispatcher;
	private IncreaseNbHostedAgentsAction increaseagentsaction;
	
	public enum IHMMode
	{
		InitialMenu,
		ChooseLevel,
		InGame;
	};
	
	public GameEngine(){
		super();
		build();//On initialise notre fenêtre
	}
 
	public GameEngine(Mapping map) throws HeadlessException {
		super();
		this.map = map;
		build ();
	}

	private void build(){
		
		// Icône de l'application
		Image icone = Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + File.separator + "img" + File.separator + "icon.gif");
		this.setIconImage(icone);
		
		this.setTargetCursor();
	
		this.setTitle("IMACDefense"); // On donne un titre à l'application
		//this.setSize(map.getWidth()*Tile.getWidth() + 6, map.getHeight()*Tile.getHeight() + 28); // On donne une taille à notre fenêtre
		
		this.setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
		this.setResizable(false); // On interdit le redimensionnement de la fenêtre
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // On dit à l'application de se fermer lors du clic sur la croix

		// On impose un layout au contentPane afin que ses dimensions ne prennent pas en compte les bords de la fenêtre
		Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());
        
        if (ihmmode == IHMMode.InitialMenu)
        {
        	this.ihm = new IHMMenu(this);
        }
        
        else if (ihmmode == IHMMode.ChooseLevel)
        {
        	this.ihm = new IHMChooseLevel(this);
        }
        
        else if (ihmmode == IHMMode.InGame)
        {
        	if (this.map == null)
        		return;
        	
        	this.setTitle("IMACDefense - " + map.getName());
        	this.ihm = new IHMinGame(this, map);
	        players = new ArrayList<Player>();
	        HumanPlayer humanplayer = new HumanPlayer (1, "Fifi", Player.PlayerColor.red);
			players.add(humanplayer);
			players.add(new ArtificialIntelligencePlayer (2, "Loulou", Player.PlayerColor.green));
			players.add(new ArtificialIntelligencePlayer (3, "Riri", Player.PlayerColor.yellow));
			players.add(new ArtificialIntelligencePlayer (4, "Donald", Player.PlayerColor.blue));
	        
			this.ihm = new IHMinGame(this, map);
			
			/* Association des zones et des joueurs */
			if (map != null && players != null)
			{
				ArrayList<Zone> zones = map.getZones();
				if (zones != null)
				{
					for (Zone zone:zones)
					{
						for (Player player:players)
						{
							if (zone.getPlayerId() == player.getId())
							{
								zone.setOwner(player);
							}
						}
					}
				}
			}
			
			dispatcher = new Dispatcher(players, map, this, 5000);
			dispatcher.run();
			increaseagentsaction = new IncreaseNbHostedAgentsAction(this, 5000);
			increaseagentsaction.run();
        }
	    
		if (this.pan != null)
		{
			contentPane.removeAll();
			contentPane.add(this.pan);
		}
	    pack ();
	    this.setVisible(true);
	}

	public Mapping getMap() {
		return map;
	}

	public JPanel getPan() {
		return pan;
	}
	
	public void setTargetCursor ()
	{
		// Curseur Cible
		Image cursorImage = Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + File.separator + "img" + File.separator + "cursor.png");
		// On récupère la dimension qu'aura le curseur à l'écran
		Dimension cursorDimension = Toolkit.getDefaultToolkit().getBestCursorSize(15, 15);
		// Le curseur est en forme de croix (cible), on veut donc que le "hotspot" se situe au centre du curseur, donc on divise les dimensions récupérées par 2.
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point((int)cursorDimension.getWidth()/2, (int)cursorDimension.getHeight()/2), "Cursor");
		this.setCursor(cursor);
	}

	public void setPan(JPanel pan) {
		this.pan = pan;
		this.pan.repaint();
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setIHMMode(IHMMode mode) {
		// TODO Auto-generated method stub
		if (this.ihmmode == mode)
			return;
		
		this.ihmmode = mode;
		if (!this.ihmmode.equals(IHMMode.InGame) && dispatcher != null)
		{
			dispatcher.timer.stop();
			increaseagentsaction.timer.stop();
		}
		this.pan.removeAll();
		this.build();
		this.pan.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		ihm.mousePressed(arg0);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setMap(Mapping map) {
		this.map = map;
	}

	public IHMMode getIhmmode() {
		return ihmmode;
	}
}