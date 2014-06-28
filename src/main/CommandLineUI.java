package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import main.Interfaces.Game;
import main.Interfaces.UI;
import main.Interfaces.Game.Player;

public class CommandLineUI implements UI {
	private Game game = null;
	private BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
	private String inputString = "";
	private final String EXIT_STRING = "x"; 
	
	@Override
	public void setGame(Game game) {
		this.game = game;
	}

	@Override
	public void playGame() {
		while (!inputString.toLowerCase().equals(EXIT_STRING)) {
			game.reset();
			playARound(game);
			drawBoardForGame(game);
			printEndMessageForWinner(game.getWinner());
			inputString = retrieveInput();
			System.out.println("\n\n");
		}
	}
	
	private void playARound(Game game) {
		while (!game.isGameOver()) {
			drawBoardForGame(game);
			System.out.print("Make your move (x,y): ");
			inputString = retrieveInput();
			Location playerLocation = parseInput(inputString);
			if (playerLocation!=null) {
				if (game.placeStoneAtLocationForPlayer(playerLocation, Player.HUMAN))
					game.playComputer();
				else
					System.out.println("Cannot place stone there");						
			} else
				System.out.println("Invalid input value!");
		}
	}

	private String retrieveInput() {
		try {
			return keyboard.readLine().trim();
		} catch (Exception ex) {
			return "";
		}
	}
	
	private static Location parseInput(String input) {
		String[] elements = input.split(",");
		if(elements.length==2)
			try {
				int x = Integer.parseInt(elements[0].trim());
				int y = Integer.parseInt(elements[1].trim());
				return new Location (x, y);
			} catch (Exception ex){
				return null;
			}
		return null;
	}
	
	private static void drawBoardForGame(Game game) {
		System.out.println("    1   2   3");
		System.out.println("  -------------");
		for (int i=1; i<=3; i++) {
			System.out.print(i + " |");
			for (int j=1; j<=3; j++) {
				if (game.ownerOfStoneAtLocation(j, i)==Player.HUMAN)
					System.out.print(" O |");
				else if (game.ownerOfStoneAtLocation(j, i)==Player.COMPUTER)
					System.out.print(" X |");
				else 
					System.out.print("   |");
			}
			System.out.println();
			System.out.println("  -------------");
		}
	}
	
	private static void printEndMessageForWinner(Player player) {
		if (player==Player.HUMAN)
			System.out.println("YOU WIN!");
		if (player==Player.COMPUTER)
			System.out.println("I WIN!");
		else
			System.out.println("IT'S A DRAW!");
		System.out.println();
		System.out.print("Press any key to play another game or x to exit: ");
	}
}
