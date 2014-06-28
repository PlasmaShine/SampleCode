package tests;
import static org.junit.Assert.*;
import main.Interfaces.Game;
import main.Interfaces.Game.Player;
import main.TicTacToeGame;

import org.junit.Before;
import org.junit.Test;

import main.Location;

public class TestGame {
	private Game game;
		
	private void placeFollowingNumberOfItemsInColumnForPlayer(int numberOfItems, int column, Player player) {
		for (int i=1;i<=numberOfItems;i++) {
			game.placeStoneAtLocationForPlayer(column, i, player);
		}
	}

	private void placeFollowingNumberOfItemsInRowForPlayer(int numberOfItems, int row, Player player) {
		for (int i=1;i<=numberOfItems;i++) {
			game.placeStoneAtLocationForPlayer(i, row, player);
		}
	}
	
	private void placeFollowingNumberOfItemsInMainDiagonalForPlayer(int numberOfItems, Player player) {
		for (int i=1;i<=numberOfItems;i++) {
			game.placeStoneAtLocationForPlayer(i, i, player);
		}		
	}
	
	private void placeFolowingNumberOfItemsInSecondaryDiagonalForPalyer(int numberOfItems, Player player) {
		for (int i=1;i<=numberOfItems;i++) {
			game.placeStoneAtLocationForPlayer(4-i, i, player);
		}				
	}
	
	private void createDrawGameLayout() {
		placeFollowingNumberOfItemsInRowForPlayer(2, 2, Player.HUMAN);
		placeFollowingNumberOfItemsInRowForPlayer(2, 3, Player.COMPUTER);
		game.placeStoneAtLocationForPlayer(1, 1, Player.COMPUTER);
		game.placeStoneAtLocationForPlayer(3, 1, Player.HUMAN);
		game.placeStoneAtLocationForPlayer(3, 2, Player.COMPUTER);
		game.placeStoneAtLocationForPlayer(3, 3, Player.HUMAN);
	}
	
	private void assertLocationIsOnBoard(Location location) {
		assertTrue("X Location should be on board", location.x<=3 && location.x>=1);
		assertTrue("Y location should be on board", location.y<=3 && location.y>=1);
	}
	
	private void assertHumanWon() {
		assertTrue("The game should be over!", game.isGameOver());
		assertTrue("Human Player should have won!", game.getWinner()==Player.HUMAN);
	}
	
	private void assertComputerWon() {
		assertTrue("The game should be over!", game.isGameOver());
		assertTrue("Human Player should have won!", game.getWinner()==Player.COMPUTER);
	}
	
	private void assertDraw() {
		assertTrue("The game should be over!", game.isGameOver());
		assertTrue("No Player should have won!", game.getWinner()==Player.NONE);
	}
	
	@Before
	public void setUp() throws Exception {
		this.game = new TicTacToeGame(); 
	}

	@Test
	public void testGameStartsWithAnEmptyBoard() {
		for(int i=1; i<=3; i++)
			for (int j=1;j<=3; j++)
				assertEquals("Board should be empty", 
						game.ownerOfStoneAtLocation(i, j), Player.NONE);
	}

	@Test
	public void testHumanCanPlaceStoneOnBoard() {
		assertTrue("Should have been able to place stone at location 3, 3", 
				game.placeStoneAtLocationForPlayer(3,3, Player.HUMAN));
		assertTrue("The stone owner should have been HUMAN", 
				game.ownerOfStoneAtLocation(3,3)==Game.Player.HUMAN);
	}
	
	@Test
	public void testHumanCannotPlaceTwoStonesOnTheSameLocation() {
		game.placeStoneAtLocationForPlayer(1,1, Player.HUMAN);
		assertFalse("Should not be able to place two stones on the same location", 
				game.placeStoneAtLocationForPlayer(1, 1, Player.HUMAN));
	}	

	@Test
	public void testHumanCannotPlaceStonesOffBoardI() {
		assertFalse("Should not be able to place a stones off-board", 
				game.placeStoneAtLocationForPlayer(0, 0, Player.HUMAN));
	}	

	@Test
	public void testHumanCannotPlaceStonesOffBoardII() {
		assertFalse("Should not be able to place a stones off-board", 
				game.placeStoneAtLocationForPlayer(4, 4, Player.HUMAN));
	}	

	@Test
	public void testComputerCanPlaceStoneOnBoard() {
		Location location = game.playComputer();
		assertLocationIsOnBoard(location);
		assertTrue("The stone owner should have been COMPUTER", 
				game.ownerOfStoneAtLocation(location)==Game.Player.COMPUTER);
	}
	
	@Test
	public void testComputerStartsAtTheMiddleOfTheBoard() {
		Location location = game.playComputer();
		assertTrue("Computer should have started in the middle of the board", 
				location.equals(new Location(2,2)));
	}
	
	@Test
	public void testIfMiddleOfBoardNotAvailableStartSomewhereOnTheSide() {
		game.placeStoneAtLocationForPlayer(2, 2, Player.HUMAN);
		Location location = game.playComputer();
		assertTrue("Computer should have started on the side of the board", 
				!location.equals(new Location(2,2)));
	}
	
	@Test
	public void testPlacingThreeIdenticalStonesHorizontallyInTheFirstRowWinsTheGameForTheHuman() {
		placeFollowingNumberOfItemsInRowForPlayer(3, 1, Player.HUMAN);
		assertHumanWon();
	}

	@Test
	public void testPlacingThreeIdenticalStonesNotOnAStraightLineDoesNotWinTheGame() {
		placeFollowingNumberOfItemsInRowForPlayer(2, 1, Player.HUMAN);
		game.placeStoneAtLocationForPlayer(1, 2, Player.HUMAN);
		assertFalse("The game should not be over!", game.isGameOver());
		assertTrue("Nobody should have won!", game.getWinner()==Player.NONE);
	}

	@Test
	public void testPlacingThreeIdenticalStonesHorizontallyInTheSecondRowWinsTheGameForTheHuman() {
		placeFollowingNumberOfItemsInRowForPlayer(3, 2, Player.HUMAN);
		assertHumanWon();
	}

	@Test
	public void testPlacingThreeIdenticalStonesHorizontallyInTheThirdRowWinsTheGameForTheHuman() {
		placeFollowingNumberOfItemsInRowForPlayer(3, 3, Player.HUMAN);
		assertHumanWon();
	}

	@Test
	public void testPlacingThreeIdenticalStonesVerticallyInTheFirstColumnWinsTheGameForTheHuman() {
		placeFollowingNumberOfItemsInColumnForPlayer(3, 1, Player.HUMAN);		
		assertHumanWon();
	}

	@Test
	public void testPlacingThreeIdenticalStonesVerticallyInTheSecondColumnWinsTheGameForTheHuman() {
		placeFollowingNumberOfItemsInColumnForPlayer(3, 2, Player.HUMAN);		
		assertHumanWon();
	}

	@Test
	public void testPlacingThreeIdenticalStonesVerticallyInTheThirdColumnWinsTheGameForTheHuman() {
		placeFollowingNumberOfItemsInColumnForPlayer(3, 3, Player.HUMAN);
		assertHumanWon();
	}

	@Test
	public void testPlacingThreeIdenticalStonesOnTheMainDiagonalWinsTheGameForTheHuman() {
		placeFollowingNumberOfItemsInMainDiagonalForPlayer(3, Player.HUMAN);
		assertHumanWon();
	}

	@Test
	public void testPlacingThreeIdenticalStonesOnTheSecondaryDiagonalWinsTheGameForTheHuman() {
		placeFolowingNumberOfItemsInSecondaryDiagonalForPalyer(3, Player.HUMAN);
		assertHumanWon();
	}

	@Test
	public void testCannotPlaceStonesAfterGameIsOver() {
		placeFollowingNumberOfItemsInRowForPlayer(3, 1, Player.HUMAN);
		assertFalse("Should not be able to place stones after game is over", 
				game.placeStoneAtLocationForPlayer(1, 2, Player.HUMAN));
		assertNull("Should not be able to place stones after game is over", 
				game.playComputer());
	}

	@Test
	public void testComputerWillWinByCompletingRow() {
		placeFollowingNumberOfItemsInRowForPlayer(2, 1, Player.COMPUTER);
		placeFollowingNumberOfItemsInRowForPlayer(2, 2, Player.HUMAN);
		game.playComputer();
		assertComputerWon();
	}

	@Test
	public void testComputerWillWinByCompletingColumn() {
		placeFollowingNumberOfItemsInColumnForPlayer(2, 1, Player.COMPUTER);
		placeFollowingNumberOfItemsInColumnForPlayer(2, 2, Player.HUMAN);
		game.playComputer();
		assertComputerWon();
	}
	
	@Test
	public void testComputerWillBlockIfOponentCloseToWinning() {
		placeFollowingNumberOfItemsInRowForPlayer(2, 2, Player.COMPUTER);
		placeFollowingNumberOfItemsInColumnForPlayer(2, 3, Player.HUMAN);
		game.playComputer();
		assertTrue("Computer should have blocked", 
				game.ownerOfStoneAtLocation(3,3) == Player.COMPUTER);
		assertFalse("Game should not be over", game.isGameOver());
	}
	
	@Test
	public void testComputerWillAvoidTriangleOfDeathOnMainDiagonal() {
		game.placeStoneAtLocationForPlayer(2, 2, Player.COMPUTER);
		game.placeStoneAtLocationForPlayer(1, 1, Player.HUMAN);
		game.placeStoneAtLocationForPlayer(3, 3, Player.HUMAN);
		Location placedLocation = game.playComputer();
		assertTrue("Computer should have placed stone on one of the sides but not the corner", 
				!placedLocation.equals(new Location(1, 1)) && 
				!placedLocation.equals(new Location(3, 1)) &&
				!placedLocation.equals(new Location(1, 3)) && 
				!placedLocation.equals(new Location(3, 3)));
	}

	@Test
	public void testComputerWillAvoidTriangleOfDeathOnSecondaryDiagonal() {
		game.placeStoneAtLocationForPlayer(2, 2, Player.COMPUTER);
		game.placeStoneAtLocationForPlayer(3, 1, Player.HUMAN);
		game.placeStoneAtLocationForPlayer(1, 3, Player.HUMAN);
		Location placedLocation = game.playComputer();
		assertTrue("Computer should have placed stone on one of the sides but not the corner", 
				!placedLocation.equals(new Location(1, 1)) && 
				!placedLocation.equals(new Location(3, 1)) &&
				!placedLocation.equals(new Location(1, 3)) && 
				!placedLocation.equals(new Location(3, 3)));
	}

	@Test
	public void testDrawGame() {
		createDrawGameLayout();
		game.playComputer();
		assertTrue("Computer should filled the last empty place", 
				game.ownerOfStoneAtLocation(2, 1)==Player.COMPUTER);
		assertDraw();
	}

	@Test
	public void testTheGameCanBeResetAfterWinning() {
		placeFollowingNumberOfItemsInRowForPlayer(3, 1, Player.COMPUTER);
		game.reset();
		assertTrue("There should be no current player", game.getCurrentPlayer()==Player.NONE);
		assertTrue("There should be no winner", game.getWinner() == Player.NONE);
		assertFalse("Game should not be over", game.isGameOver());
		assertTrue("Board should be empty", game.getNumberOfStonesOnBoard() == 0);
	}

}
