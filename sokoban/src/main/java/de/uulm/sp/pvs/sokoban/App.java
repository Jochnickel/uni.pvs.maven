package de.uulm.sp.pvs.sokoban;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

import org.jline.reader.impl.LineReaderImpl;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.NonBlockingReader;

import de.uulm.sp.pvs.util.Sokoban;
import de.uulm.sp.pvs.util.SokobanUtils;

public class App {

	private final static char[] WASD = { 'W', 'A', 'S', 'D' };
	private final static char[] NWSE = { 'N', 'W', 'S', 'E' };
	private final static char EXIT_KEY = 'X';
	private final static char LOAD_MAP_KEY = 'L';
	private final static char TOGGLE_KEY = ' ';
	/**
	 * 4 letters to control the player, counterclockwise, starting with up.
	 */
	private static char[] movementKeys = NWSE;

	private static SokobanLevel currentLevel;

	public static void main(final String[] args) throws IOException, InvalidFileException {
		final Terminal terminal = TerminalBuilder.terminal();
		terminal.enterRawMode();
		final var reader = terminal.reader();

		System.out.println("Hallo, " + askForString(reader, "Enter your Name: "));
		

		loadLevel("test_level.xml");
		playLevel(reader);
	}

	private static SokobanLevel askForLevelFromDir(final NonBlockingReader reader, String levelsPath ){

	}

	private static String askForString(final NonBlockingReader reader, final String question) throws IOException {
		var out = "";
		System.out.print(question);
		while (true) {
			final var r = reader.read();
			if (13 /* Enter */ == r) {
				System.out.println();
				break;
			} else if (4 /* Ctrl+D */ == r) {
				return null;
			} else {
				out += ((char) r);
				System.out.print((char) r);
			}
		}
		return out;
	}

	private static void loadLevel(final String levelFile) throws FileNotFoundException, InvalidFileException {
		currentLevel = new SokobanLevel(levelFile);
	}

	static void playLevel(final NonBlockingReader reader) throws IOException {
		printBoardAndAskForInput();
		for (char read = Character.toUpperCase((char) reader.read());; read = Character
				.toUpperCase((char) reader.read())) {
			if (10 == read)
				continue;
			System.out.println(read);
			if (EXIT_KEY == read) {
				return;
			}
			if (TOGGLE_KEY == read) {
				toggleControls();
				printAskForInput();
				continue;
			}
			if (LOAD_MAP_KEY == read) {
				final var lineReader = new LineReaderImpl(terminal); // doenst need to close??
				try {
					System.out.print("Enter file name to load: ");
					loadLevel(lineReader.readLine());
					printBoardAndAskForInput();
				} catch (final Exception e) {
					System.err.printf("Error loading File: %s\n", e);
					printAskForInput();
				}
				continue;
			}
			try {
				moveByChar(read);
			} catch (final IllegalArgumentException e) {
				System.out.print("Invalid Input. ");
				printAskForInput();
				continue;
			}
			printBoardAndAskForInput();
		}
	}

	static void moveByChar(final char keyboardInputUpperCase) throws IllegalArgumentException {
		if (Objects.equals(keyboardInputUpperCase, movementKeys[0])) {
			Sokoban.moveNorth(currentLevel.board);
		} else if (Objects.equals(keyboardInputUpperCase, movementKeys[1])) {
			Sokoban.moveWest(currentLevel.board);
		} else if (Objects.equals(keyboardInputUpperCase, movementKeys[2])) {
			Sokoban.moveSouth(currentLevel.board);
		} else if (Objects.equals(keyboardInputUpperCase, movementKeys[3])) {
			Sokoban.moveEast(currentLevel.board);
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

	static void printBoardAndAskForInput() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
		System.out.printf("Current Board:\n\n%s\n", Sokoban.sokobanToString(currentLevel.board));
		printAskForInput();
	}

	static void printAskForInput() {
		System.out.printf("Where do you want to go? (%s/%s/%s/%s to move, %c to load map, %c to exit): ",
				movementKeys[0], movementKeys[1], movementKeys[2], movementKeys[3], LOAD_MAP_KEY, EXIT_KEY);
	}
}