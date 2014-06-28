package main.Interfaces;

import main.Location;

public interface Game {
	public enum Player {
		HUMAN,
		COMPUTER,
		NONE
	}
	
	public void reset();
	public boolean placeStoneAtLocationForPlayer(Location location, Player player);
	public boolean placeStoneAtLocationForPlayer(int x, int y, Player player);
	public Player ownerOfStoneAtLocation(Location location);
	public Player ownerOfStoneAtLocation(int x, int y);
	public int getNumberOfStonesOnBoard();
	public Location playComputer();
	public Player getCurrentPlayer();
	public boolean isGameOver();
	public Player getWinner();
}
