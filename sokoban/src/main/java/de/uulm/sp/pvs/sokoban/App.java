package de.uulm.sp.pvs.sokoban;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

import javax.xml.crypto.Data;

import org.eclipse.persistence.jpa.jpql.parser.ExistsExpression;
import org.jline.reader.impl.LineReaderImpl;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
// import org.jline.utils.NonBlockingReader;

import de.uulm.sp.pvs.util.Database;
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
		try {
			final Terminal terminal = TerminalBuilder.terminal();
			final var name = askForString(terminal, "Enter your Name: ");
			System.out.println("Hallo, " + name);
			for (;;) {
				while (null == currentLevel) {
					currentLevel = askForLevelFromDir(terminal, "./");
				}
				try {
					playLevel(terminal);
				} catch (LevelDoneException e) {
					if (!Database.doesPlayerExist(name)) {
						Database.createPlayer(name);
					}
					try {
						Database.addGame(currentLevel.name, true, Database.getPlayerId(name));
					} catch (Exception e1) {
						System.err.println("Game already exists");
					}
					currentLevel = null;
				}
			}
		} catch (ValidationFileNotFoundException e) {
			System.err.printf("Couldn't load validation file (%s)\n", e.getMessage());
		} catch (ExitGameException e) {
			System.out.println("Leaving game");
		}
	}

	private static SokobanLevel askForLevelFromDir(final Terminal terminal, final String levelsPath)
			throws IOException, InvalidFileException, ValidationFileNotFoundException {
		final var levelList = SokobanUtils.loadLevels(levelsPath);
		final var lineReader = new LineReaderImpl(terminal); // doenst need to close??
		System.out.printf("There are %d levels:\n", levelList.size());
		for (int i = 0; i < levelList.size(); i++) {
			System.out.printf("(%d) (%s) %s\n", i + 1, levelList.get(i).difficulty, levelList.get(i).name);
		}
		System.out.printf("Choose one: ");
		try {
			return levelList.get(Integer.parseInt(lineReader.readLine()) - 1);
		} catch (NumberFormatException | IndexOutOfBoundsException e) {
			return null;
		}
	}

	private static String askForString(final Terminal terminal, final String question) throws IOException {
		final var lineReader = new LineReaderImpl(terminal); // doenst need to close??
		System.out.print(question);
		return lineReader.readLine();
	}

	private static void loadLevel(final String levelFile) throws FileNotFoundException, InvalidFileException {
		currentLevel = new SokobanLevel(levelFile);
	}

	static void playLevel(final Terminal terminal) throws IOException, LevelDoneException {
		terminal.enterRawMode();
		final var reader = terminal.reader();
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
		if (Sokoban.noBoxesLeft(currentLevel.board)) {
			throw new LevelDoneException();
		}
		printAskForInput();
	}

	static void printAskForInput() {
		System.out.printf("Where do you want to go? (%s/%s/%s/%s to move, %c to load map, %c to exit): ",
				movementKeys[0], movementKeys[1], movementKeys[2], movementKeys[3], LOAD_MAP_KEY, EXIT_KEY);
	}
}