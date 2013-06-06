package agents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import map.Mapping;
import map.tiles.Tile;

// Algorithme de pathfinding
public class AStarAlgorithm {
	
	public class Node {
		Tile objectNode = null;
		Node parent = null;
		int nbNodesFromStart = 0;
		double distanceToArrival = 0;
		double weight = 0; // nbNodesFromStart + distanceToArrival
		
		public Node(Tile objectNode, Node parent) {
			super();
			this.objectNode = objectNode;
			this.parent = parent;
		}

		public double getWeight() {
			return weight;
		}

		public Tile getObjectNode() {
			return objectNode;
		}
		
		public void setParent(Node parent) {
			this.parent = parent;
		}

		public Node getParent() {
			return parent;
		}

		public int getNbNodesFromStart() {
			return nbNodesFromStart;
		}
		
		public void calculateNbNodesFromStart ()
		{
			if (this.parent == null)
				this.nbNodesFromStart = 0;
			else
				this.nbNodesFromStart = this.parent.getNbNodesFromStart()+1;
		}
		
		public void calculateDistanceToArrival (Node arrival)
		{
			this.distanceToArrival = this.getObjectNode().getCoordsInMap().distance(arrival.getObjectNode().getCoordsInMap());
		}
		
		public void calculateWeight ()
		{
			this.weight = this.nbNodesFromStart + this.distanceToArrival;
		}

		@Override
		public boolean equals(Object obj) {
			// TODO Auto-generated method stub
			if (obj instanceof Node)
			{
				if (((Node) obj).getObjectNode() == this.objectNode)
				{
					return true;
				}
			}
			return false;
		}
	};
	
	ArrayList<Node> openList = new ArrayList<Node>();
	ArrayList<Node> closedList = new ArrayList<Node>();
	Node start = null;
	Node arrival = null;
	
	ArrayList<ArrayList<Tile>> allObjectNodes = null;
	
	Mapping map = null;

	public AStarAlgorithm(Tile start, Tile arrival, Mapping map) {
		// TODO Auto-generated constructor stub
		this.start = new Node (start, null);
		this.start.calculateNbNodesFromStart();
		
		this.arrival = new Node (arrival, null);
		this.map = map;
		this.allObjectNodes = map.getTiles();
	}
	
	private void findPath ()
	{
		/* On ajoute le node de départ à la liste ouverte. */
		openList.add(this.start);
		Node current = this.start;
		
		/* Lorsqu'on arrive au node destination ou que la liste ouverte est vide, on sort de l'itération. */
		while (current != null && !current.equals(arrival) && !openList.isEmpty())
		{			
			/* On bascule le node courant dans la liste fermée. */
			openList.remove(current);
			closedList.add(current);
			
			/* Pour chaque node voisin au node courant, */
			ArrayList<Node> neighbours = getNeighbours(current);
			if (!neighbours.isEmpty())
			{
				Iterator<Node> it = neighbours.iterator();
				while (it.hasNext())
				{
					Node currentNeighbour = it.next();
					
					/* Le node voisin ne doit pas être un obstacle, ni être contenu dans la liste fermée. */
					if (currentNeighbour != null && !currentNeighbour.getObjectNode().isObstacle() && !closedList.contains(currentNeighbour))
					{
						/* Si le node voisin n'est pas dans la liste ouverte, */
						if (!openList.contains(currentNeighbour))
						{
							if (currentNeighbour.equals(arrival))
								currentNeighbour = arrival;
							
							/* On l'ajoute à la liste ouverte */
							openList.add(currentNeighbour);
							
							/* Le node courant devient le parent du node voisin. */
							currentNeighbour.setParent(current);
							
							/* On calcule les propriétés du node voisin. */
							currentNeighbour.calculateNbNodesFromStart();
							currentNeighbour.calculateDistanceToArrival(arrival);
							currentNeighbour.calculateWeight();
						}
						
						/* Si le node voisin est déjà dans la liste ouverte, */
						else
						{
							currentNeighbour = openList.get(openList.indexOf(currentNeighbour));
							
							/* 
							 * On recalcule la longueur du chemin pour parvenir à ce tile, cette fois-ci depuis le node courant ;
							 * en effet, si ce node voisin est déjà dans la liste ouverte, c'est qu'on a déjà pu y accéder depuis un autre node,
							 * la longueur du chemin peut donc être différente selon depuis quel node on accède à ce node voisin.
							 */
							int newNbNodesFromStart = current.getNbNodesFromStart()+1;
							
							/* Si le chemin depuis le node courant est le plus court, alors le node voisin aura préférentiellement le node courant en tant que parent. */
							if (newNbNodesFromStart < currentNeighbour.getNbNodesFromStart())
							{
								currentNeighbour.setParent(current);
								currentNeighbour.calculateNbNodesFromStart();
								currentNeighbour.calculateDistanceToArrival(arrival);
								currentNeighbour.calculateWeight();
							}
						}
					}
				}
			}
			
			/* On récupère le node le plus léger de la liste ouverte. */
			current = lightestNodeInOpenList ();
		}
	}
	
	// Node le plus léger -> avec le poids le moins important.
	private Node lightestNodeInOpenList ()
	{
		if (openList.isEmpty())
		{
			return null;
		}
		Node lightestWeightNode = openList.get(0);
		Iterator<Node> it = openList.iterator();
		while (it.hasNext())
		{
			Node current = it.next();
			if (current.getWeight() < lightestWeightNode.getWeight())
			{
				lightestWeightNode = current;
			}
		}
		
		return lightestWeightNode;
	}
	
	// Retourne la liste des nodes voisins au node
	private ArrayList<Node> getNeighbours (Node node)
	{
		ArrayList<Node> neighbours = new ArrayList<Node>();
		
		int line = 0, column = 0;
		if (node.getObjectNode() == null || node.getObjectNode().getCoordsInMap() == null)
			return null;
		
		line = (int)node.getObjectNode().getCoordsInMap().getY();
		column = (int)node.getObjectNode().getCoordsInMap().getX();
		
		for (int i = line-1; i <= line+1; ++i)
		{
			for (int j = column-1; j <= column+1; ++j)
			{
				/* 
				 * On ne considère pas dans les voisins le node actuel, 
				 * donc au moins une des deux coordonnées est différente.
				 */
				if ((i != line || j != column) && i >= 0 && i < map.getWidth() && j >= 0 && j < map.getHeight())
				{
					neighbours.add(new Node(allObjectNodes.get(i).get(j), null));
				}
			}
		}
		
		return neighbours;
	}

	private ArrayList<Tile> constructFinalPath ()
	{
		if (this.arrival.getParent() == null)
		{
			return null;
		}
		
		ArrayList<Tile> finalPath = new ArrayList<Tile>();
		
		Node current = this.arrival;
		
		while (current != null)
		{
			finalPath.add(current.getObjectNode());
			current = current.getParent();
		}
		
		Collections.reverse(finalPath);
		
		return finalPath;
	}
	
	public ArrayList<Tile> getFinalPath ()
	{
		findPath();
		return constructFinalPath();
	}
}
