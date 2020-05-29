package de.uulm.sp.pvs.sokoban;

import java.io.IOException;
import java.util.Objects;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import de.uulm.sp.pvs.util.Sokoban;

public class App {

	private final static char[] WASD = { 'W', 'A', 'S', 'D' };
	private final static char[] NWSE = { 'N', 'W', 'S', 'E' };
	private final static char EXIT_KEY = 'X';
	private final static char TOGGLE_KEY = ' ';
	/**
	 * 4 letters to control the player, counterclockwise, starting with up.
	 */
	private static char[] movementKeys = NWSE;

	public static void main(String[] args) throws IOException, InvalidFileException {
		SokobanLevel level = new SokobanLevel("test_level.xml");
		char[][] board =level.board;
		playLevel(board);
	}

	static void playLevel(final char[][] board) throws IOException {
		Terminal terminal = TerminalBuilder.terminal();
		terminal.enterRawMode();
		var reader = terminal.reader();
		printBoardAndAskForInput(board);
		for (char read = Character.toUpperCase((char) reader.read());; read = Character
				.toUpperCase((char) reader.read())) {
			System.out.println(read);
			if (EXIT_KEY == read) {
				return;
			}
			if (TOGGLE_KEY == read) {
				toggleControls();
				printAskForInput();
				continue;
			}
			try {
				moveByChar(read, board);
			} catch (IllegalArgumentException e) {
				System.out.print("Invalid Input. ");
				printAskForInput();
				continue;
			}
			printBoardAndAskForInput(board);
		}
	}

	static void moveByChar(final char keyboardInputUpperCase, char[][] board) throws IllegalArgumentException {
		if (Objects.equals(keyboardInputUpperCase, movementKeys[0])) {
			Sokoban.moveNorth(board);
		} else if (Objects.equals(keyboardInputUpperCase, movementKeys[1])) {
			Sokoban.moveWest(board);
		} else if (Objects.equals(keyboardInputUpperCase, movementKeys[2])) {
			Sokoban.moveSouth(board);
		} else if (Objects.equals(keyboardInputUpperCase, movementKeys[3])) {
			Sokoban.moveEast(board);
		} else {
			throw new IllegalArgumentException();
		}
	}

	static void toggleControls() {
		if (WASD == movementKeys) {
			movementKeys = NWSE;
			System.out.println("Controls: NWSE");
		} else if (NWSE == movementKeys) {
			movementKeys = WASD;
			System.out.println("Controls: WASD");
		} else {
			System.err.println("Error toggling keyboard layout");
		}
	}

	static void setControlsWASD() {
		movementKeys = WASD;
	}

	static void setControlsNWSE() {
		movementKeys = NWSE;
	}

	static void printBoardAndAskForInput(char[][] board) {
		System.out.print("\033[H\033[2J"); 
		System.out.flush();
		System.out.printf("Current Board:\n\n%s\n", Sokoban.sokobanToString(board));
		printAskForInput();
	}

	static void printAskForInput() {
		System.out.printf("Where do you want to go? (%s/%s/%s/%s or %c to exit): ", movementKeys[0], movementKeys[1],
				movementKeys[2], movementKeys[3], EXIT_KEY);
	}
}