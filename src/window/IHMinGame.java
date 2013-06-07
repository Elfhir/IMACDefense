package window;

import gameengine.AnimationAction;
import gameengine.GameEngine;
import gameengine.MoveBulletAction;
import gameengine.TowerShootAction;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JButton;

import map.Mapping;
import map.tiles.Buttress;
import map.tiles.Tile;
import players.Player;
import players.SelectableObject;
import players.types.HumanPlayer;
import towers.Tower;
import towers.towertypes.BombTower;
import towers.towertypes.FreezeTower;
import towers.towertypes.LaserTower;
import towers.towertypes.MedicalTower;
import towers.towertypes.SubmachineGunTower;
import agents.Agent;
import basis.Base;

public class IHMinGame implements IHM {
	
	GameEngine window = null;
	PanMap pan;
	Mapping map;
	Hashtable<Point, Tower> towers = null;
	private Class<? extends Tower> selectedTowerClass = null;

	public IHMinGame(GameEngine window, Mapping map) {
		this.window = window;
		this.map = map;
		
		ArrayList<Player> players = window.getPlayers();
		HumanPlayer humanplayer = new HumanPlayer();
		
		if (players == null)
			return;
		
		Iterator<Player> it = players.iterator();
		
		while (it.hasNext())
		{
			Player currentPlayer = it.next();
			if (currentPlayer instanceof HumanPlayer)
			{
				humanplayer = (HumanPlayer) currentPlayer;
				break;
			}
		}
		
		//On prévient notre JFrame que notre JPanel sera son content pane
		try {
			window.setPan(new PanMap (map, this, humanplayer));
			this.pan = (PanMap)window.getPan();
			this.pan.setDoubleBuffered(true);
			
			int ihmsize = 150;
			
			this.pan.setPreferredSize(new Dimension(map.getWidth()*Tile.getWidth() + ihmsize, map.getHeight()*Tile.getHeight()));
			
			this.pan.setLayout(null);
			
			/*JButton buttonConstruct = new JButton("Construire");
		    buttonConstruct.setBounds(map.getWidth()*Tile.getWidth(), 340, ihmsize, 30);
		    buttonConstruct.setBackground(Color.darkGray);
		    buttonConstruct.setForeground(Color.white);
		    pan.add(buttonConstruct);*/
		    
		    JButton buttonExit = new JButton("Menu Initial");
		    buttonExit.setBounds(map.getWidth()*Tile.getWidth(), 370, ihmsize, 30);
		    buttonExit.addActionListener(new ChangeIHMModeAction(window, GameEngine.IHMMode.InitialMenu, null));
		    buttonExit.setBackground(Color.darkGray);
		    buttonExit.setForeground(Color.white);
		    pan.add(buttonExit);
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		towers = new Hashtable<Point, Tower>();
		if (towers != null)
		{
			towers.put(new Point(map.getWidth() + 1, 10), new BombTower());
			towers.put(new Point (map.getWidth() + 3, 10), new FreezeTower());
			towers.put(new Point (map.getWidth() + 5, 10), new LaserTower());
			towers.put(new Point (map.getWidth() + 2, 13), new MedicalTower());
			towers.put(new Point (map.getWidth() + 4, 12), new SubmachineGunTower());
		}
	}
	
	public Hashtable<Point, Tower> getTowers() {
		return towers;
	}
	
	public void setTowerCursor (Tower tower)
	{
		if (tower == null || !this.towers.contains(tower))
		{
			return;
		}
		Image cursorImage = Toolkit.getDefaultToolkit().getImage(tower.getImageName());
		// On récupère la dimension qu'aura la curseur à l'écran
		Dimension cursorDimension = Toolkit.getDefaultToolkit().getBestCursorSize(tower.getObjectWidth()*Tile.getWidth(), tower.getObjectHeight()*Tile.getHeight());
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point((int)cursorDimension.getWidth()/2, (int)cursorDimension.getHeight()/2), "Cursor");
		
		if (this.window != null && cursor != null)
			this.window.setCursor(cursor);
		this.selectedTowerClass = tower.getClass();
	}

	public void printTowers ()
	{
		Set<Point> set = towers.keySet();
		Iterator<Point> it = set.iterator();
		while (it.hasNext())
		{
			Point point = it.next();
			Tower current = towers.get(point);
			pan.repaint ((int)point.getX()*Tile.getWidth(), (int)point.getY()*Tile.getHeight(), current.getObjectWidth()*Tile.getWidth(), current.getObjectHeight()*Tile.getHeight());
		}
	}

	public Class<? extends Tower> getSelectedTowerClass() {
		return selectedTowerClass;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		Point mousepoint = new Point(e.getX(), e.getY());
		// On vérifie si le joueur a cliqué sur un objet selectionnable.
		SelectableObject object = HumanPlayer.whereDidIClick(mousepoint, map, this);
		
		// Si oui,
		if (object != null)
		{
			/*
			 *  Si l'objet est déjà sélectionné, le joueur a cliqué pour déselectionner l'objet.
			 *  Donc on déselectionne.
			 */
			
			if (object.isSelected())
			{
				Player.setLastObjectSelected(null);
			}
			
			/*
			 * Si le dernier objet sélectionné est une base appartenant à un joueur humain,
			 * le joueur a voulu faire de la base clickée la cible de la base sélectionnée.
			 * On vérifie donc si :
			 * 1 - l'objet clické est une base
			 * 2 - l'objet sélectionné est une base
			 */
			else if (object instanceof Base && Player.getLastObjectSelected() instanceof Base && ((Base)Player.getLastObjectSelected()).getOwner() != null && ((Base)Player.getLastObjectSelected()).getOwner() instanceof HumanPlayer)
			{
				((Base) Player.getLastObjectSelected()).setTarget ((Base) object);
				Agent agent = ((Base) Player.getLastObjectSelected()).sendAgent(map);
				AnimationAction action = new MoveBulletAction(agent, window, 30);
				action.run();
				return;
			}
			
			/*
			 * Sinon, le joueur a voulu sélectionner l'objet.
			 * On désélectionne l'objet précédemment sélectionné et on sélectionne l'objet clické.
			 */
			else
			{
				if (Player.getLastObjectSelected() != null)
					Player.getLastObjectSelected().setSelected(false);
				Player.setLastObjectSelected(object);
			}
			
			/*
			 * On inverse donc le booléen "selected" :
			 * s'il était à false (objet déselectionné) il passe à true (objet sélectionné)
			 * et vice-versa.
			 */
			object.inverseSelected();
			
			if (object instanceof Tower)
			{
				this.setTowerCursor((Tower)object);
				HumanPlayer.setConstructingNow(true);
			}
			
			/*
			 * Puis on redessine les éléments.
			 */
			if (pan != null)
				pan.repaint();
		}
		else
		{
			Buttress tileObject = HumanPlayer.whatZoneDidIClickIn(mousepoint, map);
			
			if (tileObject != null && tileObject.getZone() != null && tileObject.getZone().getOwner() != null && tileObject.getZone().getOwner() instanceof HumanPlayer)
			{
				HumanPlayer player = (HumanPlayer) tileObject.getZone().getOwner();
				
				if (HumanPlayer.isConstructingNow())
				{
					Tower tower = player.construct(this.getSelectedTowerClass(), map, tileObject.getZone(), (int)mousepoint.getX()/20, (int)mousepoint.getY()/20);
					if (tower != null)
					{
						pan.repaint((int)tower.getCoordInTiles().getX()*Tile.getWidth(), (int)tower.getCoordInTiles().getY()*Tile.getHeight(), tower.getObjectWidth()*Tile.getWidth(), tower.getObjectHeight()*Tile.getHeight());
						TowerShootAction action = new TowerShootAction(map, window, 10000/tower.getShootSpeed(), tower);
						action.run();
					}
					window.setTargetCursor();
				}
			}
		}
	}
}
