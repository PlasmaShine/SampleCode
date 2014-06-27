package main;

import main.Interfaces.Game;
import main.Interfaces.UI;

public class Main {
	public static void main(String[] args) {
		Game game = new TicTacToeGame();
		UI ui = new CommandLineUI();
		ui.setGame(game);
		ui.playGame();
	}	
}
