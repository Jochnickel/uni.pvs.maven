package de.uulm.sp.pvs;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

import de.uulm.sp.pvs.util.Sokoban;

public class App {

	private final static String[] WASD = { "W", "A", "S", "D" };
	private final static String[] NWSE = { "N", "W", "S", "E" };
	private final static String EXIT_KEY = "X";
	private final static String TOGGLE = " ";
	/**
	 * 4 letters to control the player, counterclockwise, starting with up.
	 */
	private static String[] movementKeys = NWSE;

	public static void main(String[] args) throws IOException {
		final var board = SokobanBoards.getDefaultBoard();
		System.out.print("\nStarting game! ");
		printBoardAndAskForInput(board);
		{
			Scanner scanner = new Scanner(System.in);
			scanner.useDelimiter("");
			for (String stdInput = String.valueOf(scanner.next().charAt(0));; stdInput = scanner.next()) {
				try {
					moveByKeyboardInt(stdInput, board);
					printBoardAndAskForInput(board);
				} catch (Exception e) {
					switch (stdInput) {
						case "\n":
							break;
						case EXIT_KEY:
							scanner.close();
							System.out.println("Bye");
							System.exit(0);
						case TOGGLE:
							toggleControls();
							break;
						default:
							System.err.print("Invalid input. ");
					}
					printAskForInput();
				}
			}
		}
	}

	static void moveByKeyboardInt(final String keyboardInputAnyCase, char[][] board) throws IllegalArgumentException {
		final String keyboardInput = keyboardInputAnyCase.toUpperCase();
		if (Objects.equals(keyboardInput, movementKeys[0])) {
			Sokoban.moveNorth(board);
		} else if (Objects.equals(keyboardInput, movementKeys[1])) {
			Sokoban.moveWest(board);
		} else if (Objects.equals(keyboardInput, movementKeys[2])) {
			Sokoban.moveSouth(board);
		} else if (Objects.equals(keyboardInput, movementKeys[3])) {
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
		System.out.printf("Current Board:\n\n%s\n", Sokoban.sokobanToString(board));
		printAskForInput();
	}

	static void printAskForInput() {
		System.out.printf("Where do you want to go? (%s/%s/%s/%s or X to exit): ", movementKeys[0], movementKeys[1],
				movementKeys[2], movementKeys[3]);
	}
}
