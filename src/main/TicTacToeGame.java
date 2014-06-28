package main;
import java.util.ArrayList;
import main.Interfaces.Game;

public class TicTacToeGame implements Game {
	private ArrayList<ArrayList<Player>> board = new ArrayList<ArrayList<Player>>();
	private Player currentPlayer = Player.NONE;
	private boolean isGameOver = false;
	private Player winner = Player.NONE;
	private int numberOfStonesOnBoard = 0;
	private final int BOARD_SIZE = 3;
	
	private Location[][] winningCombinations = {
			{new Location(1, 1), new Location(1, 2), new Location(1, 3)},
			{new Location(2, 1), new Location(2, 2), new Location(2, 3)},
			{new Location(3, 1), new Location(3, 2), new Location(3, 3)},
			{new Location(1, 1), new Location(2, 1), new Location(3, 1)},
			{new Location(1, 2), new Location(2, 2), new Location(3, 2)},
			{new Location(1, 3), new Location(2, 3), new Location(3, 3)},
			{new Location(1, 1), new Location(2, 2), new Location(3, 3)},
			{new Location(1, 3), new Location(2, 2), new Location(3, 1)}
	};
		
	public TicTacToeGame() {
		initBoard();
	}

	private void initBoard() {
		createEmptyBoard();
		this.currentPlayer = Player.NONE;
		this.winner = Player.NONE;
		this.isGameOver = false;
		this.numberOfStonesOnBoard = 0;
	}
	
	private void createEmptyBoard() {
		board = new ArrayList<ArrayList<Player>>();
		for (int i=0; i<BOARD_SIZE; i++) {
			ArrayList<Player> line = new ArrayList<Game.Player>();
			for (int j=0; j<BOARD_SIZE; j++) 
				line.add(Player.NONE);
			board.add(line);
		}
	}

	@Override
	public boolean placeStoneAtLocationForPlayer(Location location, Player player) {
		return placeStoneAtLocationForPlayer(location.x, location.y, player);
	}

	@Override
	public boolean placeStoneAtLocationForPlayer(int x, int y, Player player) {
		if(this.isGameOver || !isValidLocation(x, y)) return false;
		this.currentPlayer = player;
		return setCurrentPlayersStoneAtLocation(x, y);
	}
	
	private boolean isValidLocation(int x, int y) {
		return x>=1 && x<=BOARD_SIZE && y>=1 && y<=BOARD_SIZE;
	}
	
	private boolean setCurrentPlayersStoneAtLocation(int x, int y) {
		if (isAbleToPlaceStoneAtLocation(new Location(x, y))) {
			board.get(x-1).set(y-1,this.currentPlayer);
			numberOfStonesOnBoard++;
			verifyIfGameOver();
			return true;
		}
		return false;		
	}
	
	private void verifyIfGameOver() {
		boolean boardFull = isBoardFull();
		boolean currentPlayerWon = currentPlayerWon();
		if (currentPlayerWon || boardFull)
			this.isGameOver = true;
		if (currentPlayerWon)
			this.winner = this.currentPlayer;
	}
	
	private boolean isBoardFull() {
		return this.numberOfStonesOnBoard == BOARD_SIZE*BOARD_SIZE;
	}

	private boolean currentPlayerWon() {
		for (int i=0; i<winningCombinations.length; i++) {
			Location[] currentCombination = winningCombinations[i];
			if(currentPlayerHasStonesAtEachPositionForCombination(currentCombination))
				return true;
		}
		return false;
	}
	
	private boolean currentPlayerHasStonesAtEachPositionForCombination(Location[] combination) {
		return ownerOfStoneAtLocation(combination[0]) == this.currentPlayer &&
				ownerOfStoneAtLocation(combination[1]) == this.currentPlayer &&
				ownerOfStoneAtLocation(combination[2]) == this.currentPlayer;
	}
	
	private boolean isAbleToPlaceStoneAtLocation(Location location) {
		return ownerOfStoneAtLocation(location) == Player.NONE;
	}

	@Override
	public Player ownerOfStoneAtLocation(Location location) {
		return ownerOfStoneAtLocation(location.x, location.y);
	}

	@Override
	public Player ownerOfStoneAtLocation(int x, int y) {
		return board.get(x-1).get(y-1);
	}

	@Override
	public Location playComputer() {
		if (this.isGameOver) return null;
		Location location = selectLocationToPlaceStone();
		placeStoneAtLocationForPlayer(location, Player.COMPUTER);
		return location;
	}
	
	private Location selectLocationToPlaceStone() {
		Location computerWinningLocation = getLocationForAWinningCombinationForPlayer(Player.COMPUTER);
		if (computerWinningLocation != null) 
			return computerWinningLocation;
		Location humanWinningLocation = getLocationForAWinningCombinationForPlayer(Player.HUMAN);
		if (humanWinningLocation != null) 
			return humanWinningLocation;
		Location boardCenter = new Location(2, 2);
		if (isAbleToPlaceStoneAtLocation(boardCenter))
			return boardCenter;
		else if (triangleOfDeathDetected())
			return getSideLocationThatIsNotCorner();
		else
			return getFirstEmptyLocation();
	}
	
	private Location getLocationForAWinningCombinationForPlayer(Player player) {
		for (int i=0;i<winningCombinations.length;i++) {
			Location[] currentWinningCombination = winningCombinations[i];
			Location sugestedLocation = getSuggestedLocationToCompleteCombinationForPlayer(currentWinningCombination, player);
			if (sugestedLocation != null)
				return sugestedLocation;
		}
		return null;
	}

	private Location getSuggestedLocationToCompleteCombinationForPlayer(Location[] combination, Player player) {
		int numberOfPiecesOwnedByPlayer = 0;
		Location suggestedLocation = null;
		for (int i=0; i<combination.length; i++) {
			if (isStoneAtLocationOwnedByOponentOfPlayer(combination[i], player))
				return null;
			if (ownerOfStoneAtLocation(combination[i]) == player)
				numberOfPiecesOwnedByPlayer++;
			else
				suggestedLocation = combination[i];
		}
		if (numberOfPiecesOwnedByPlayer==2)
			return suggestedLocation;
		return null;
	}
	
	private boolean isStoneAtLocationOwnedByOponentOfPlayer(Location location, Player player) {
		return ownerOfStoneAtLocation(location) != Player.NONE && 
			   ownerOfStoneAtLocation(location) != player;
	}
	
	private boolean triangleOfDeathDetected() {
		return numberOfStonesOnBoard==3 &&
			   (ownerOfStoneAtLocation(1,1) == Player.HUMAN &&
				ownerOfStoneAtLocation(3,1) == Player.HUMAN) ||
			   (ownerOfStoneAtLocation(3,1) == Player.HUMAN &&
				ownerOfStoneAtLocation(1,3) == Player.HUMAN);				   
	}
	
	private Location getSideLocationThatIsNotCorner() {
		Location sideLocation = new Location(2, 1);
		if (isAbleToPlaceStoneAtLocation(sideLocation)) 
			return sideLocation;
		sideLocation = new Location(1, 2);
		if (isAbleToPlaceStoneAtLocation(sideLocation)) 
			return sideLocation;
		sideLocation = new Location(3, 2);
		if (isAbleToPlaceStoneAtLocation(sideLocation)) 
			return sideLocation;
		sideLocation = new Location(2, 3);
		return sideLocation;
	}
	
	private Location getFirstEmptyLocation() {
		for (int i=1;i<=BOARD_SIZE;i++)
			for (int j=1; j<=BOARD_SIZE; j++) {
				Location location = new Location(i,j);
				if (ownerOfStoneAtLocation(location)==Player.NONE)
					return location;
			}
		return null;
	}
	
	@Override
	public boolean isGameOver() {
		return this.isGameOver;
	}

	@Override
	public Player getWinner() {
		return this.winner;
	}

	@Override
	public void reset() {
		initBoard();
	}

	@Override
	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}

	@Override
	public int getNumberOfStonesOnBoard() {
		return this.numberOfStonesOnBoard;
	}
}
