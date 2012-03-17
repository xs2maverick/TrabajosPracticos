package tictactoe;

import tictactoe.ai.Player;


public class Engine {
	
	private int playedTurns;
	private Game game;
	private Player current, p1, p2;
	
	public Engine() {
		game = new Game();
		playedTurns = 0;
	}
	
	public void startGame(Player p1, Player p2) {
		this.p1 = p1;
		this.p2 = p2;
		current = p1;
		play();
	}
	
	private void play() {
		while(!game.isGameFinished()) {
			printBoardStatus();
			current.makeMove(game);
			playedTurns++;
			nextPlayer();
		}
		printBoardStatus();
	}
	
	private Player nextPlayer() {
		if (current == p2 || current == null) {
			current = p1;
		} else {
			current = p2;
		}
		return current;
	}
	
	private void printBoardStatus() {
		if (!game.isGameFinished()) {
			System.out.println("------- Turn Count: " + playedTurns + "-------");
			System.out.println("Plays " + current);
		} else {
			System.out.println("*******************************************");
			System.out.println("Game Finished! - Final board:");
			System.out.println("Winner: " + game.getWinner() + "\n");
		}
		System.out.print("\n    ");
		for(int i = 0; i < Board.SIZE; i++) {
			System.out.print(i + " ");
		}
		System.out.println("\n   ------");
		int rowNumber = 0;
		for (Mark[] row: game.getBoard().getMarks()) {
			System.out.print(rowNumber + " | ");
			for(Mark value: row) {
				String mark = (value == Mark.O) ? "0" : (value == Mark.X ? "X" : "-"); 
				System.out.print(mark + " ");
			}
			System.out.println();
		}
	}
}
