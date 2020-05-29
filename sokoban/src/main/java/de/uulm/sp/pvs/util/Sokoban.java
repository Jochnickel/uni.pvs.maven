package de.uulm.sp.pvs.util;

public class Sokoban {
	private static Pair<Integer, Integer> NORTH = new Pair<Integer, Integer>(0, 1);
	private static Pair<Integer, Integer> SOUTH = new Pair<Integer, Integer>(0, -1);
	private static Pair<Integer, Integer> WEST = new Pair<Integer, Integer>(-1, 0);
	private static Pair<Integer, Integer> EAST = new Pair<Integer, Integer>(1, 0);

	final private static char EMPTY_FIELD = ' ';
	final private static char BOX_FIELD = '$';
	final private static char PLAYER_FIELD = '@';
	final private static char TARGET_FIELD = '.';
	final private static char PLAYER_AND_TARGET_FIELD = '+';
	final private static char SOLVED_FIELD = '*';

	public static void main(String[] args) {
		char[][] sokoban = getDefaultField();
		System.out.println(sokobanToString(sokoban));
		moveNorth(sokoban);
		System.out.println(sokobanToString(sokoban));
		moveEast(sokoban);
		System.out.println(sokobanToString(sokoban));
		moveSouth(sokoban);
		System.out.println(sokobanToString(sokoban));
		moveSouth(sokoban);
		System.out.println(sokobanToString(sokoban));
		moveSouth(sokoban);
		System.out.println(sokobanToString(sokoban));
		moveSouth(sokoban);
		System.out.println(sokobanToString(sokoban));
		moveSouth(sokoban);
		System.out.println(sokobanToString(sokoban));
		moveEast(sokoban);
		System.out.println(sokobanToString(sokoban));
		moveSouth(sokoban);
		System.out.println(sokobanToString(sokoban));
		moveEast(sokoban);
		System.out.println(sokobanToString(sokoban));
		moveWest(sokoban);
		System.out.println(sokobanToString(sokoban));
	}

	public static Pair<Integer, Integer> findPlayer(char[][] board) {
		for (int y = board.length - 1; y > 0; y--) {
			for (int x = board[y].length - 1; x > 0; x--) {
				if (PLAYER_FIELD == board[y][x] || PLAYER_AND_TARGET_FIELD == board[y][x]) {
					return new Pair<Integer, Integer>(x, y);
				}
			}
		}
		return null;
	}

	private static void setField(char[][] board, Pair<Integer, Integer> pos, char newChar)
			throws IndexOutOfBoundsException {
		board[pos.getSecond()][pos.getFirst()] = newChar;
	}

	private static char getField(char[][] board, Pair<Integer, Integer> pos) throws IndexOutOfBoundsException {
		return board[pos.getSecond()][pos.getFirst()];
	}

	private static boolean move(char[][] board, Pair<Integer, Integer> direction /* relative Position to current */) {
		final var oldPos = findPlayer(board);
		final var newPos = new Pair<Integer, Integer>(oldPos.getFirst() + direction.getFirst(),
				oldPos.getSecond() + direction.getSecond());
		final var twoAheadPos = new Pair<Integer, Integer>(newPos.getFirst() + direction.getFirst(),
				newPos.getSecond() + direction.getSecond());
		final char charAtNewPos;
		try {
			charAtNewPos = getField(board, newPos);
		} catch (IndexOutOfBoundsException e) {
			return false;
		}

		switch (charAtNewPos) {
			case BOX_FIELD:
				try {
					switch (getField(board, twoAheadPos)) {
						case EMPTY_FIELD:
							setField(board, twoAheadPos, BOX_FIELD);
							break;
						case TARGET_FIELD:
							setField(board, twoAheadPos, SOLVED_FIELD);
							break;
						default:
							return false;
					}
				} catch (IndexOutOfBoundsException e) {
					/*
					 * man kann Kisten auch aus dem Spielfeld rausschieben. Habe dies aus der
					 * "Grafik" entnommen, sonst wären da keine Wände am Rand.
					 */
				}
				setField(board, newPos, PLAYER_FIELD);
				break;
			case EMPTY_FIELD:
				setField(board, newPos, PLAYER_FIELD);
				break;
			case TARGET_FIELD:
				setField(board, newPos, PLAYER_AND_TARGET_FIELD);
				break;
			default:
				return false;
		}
		switch (getField(board, oldPos)) {
			case PLAYER_AND_TARGET_FIELD:
				setField(board, oldPos, TARGET_FIELD);
				break;
			case PLAYER_FIELD:
				setField(board, oldPos, EMPTY_FIELD);
				break;
			default:
				break;
		}
		return true;
	}

	public static boolean moveNorth(char[][] board) {
		return move(board, NORTH);
	}

	public static boolean moveSouth(char[][] board) {
		return move(board, SOUTH);
	}

	public static boolean moveWest(char[][] board) {
		return move(board, WEST);
	}

	public static boolean moveEast(char[][] board) {
		return move(board, EAST);
	}

	public static String sokobanToString(char[][] board) {
		String out = "";
		for (int i = board.length - 1; 0 <= i; i--) {
			out += new String(board[i]);
			out += '\n';
		}
		return out;
	}

	public static char[][] getDefaultField() {
		return new char[][] { { '#', '#', '#', '#', '#', '#', '#' }, { '#', '.', '.', '.', '.', '.', '#' },
				{ '#', '.', '.', '.', '.', '.', '#' }, { '#', '.', '.', '.', '.', '.', '#' },
				{ '#', '.', '.', '$', '.', '.', '#' }, { '#', '.', '$', '@', '$', '.', '#' },
				{ '#', '.', '.', '$', '.', '.', '#' }, { '#', '.', '.', '.', '.', '.', '#' },
				{ '#', '.', '.', '.', '.', '.', '#' }, { '#', '.', '.', '.', '.', '.', '#' },
				{ '#', '#', '#', '#', '#', '#', '#' } };
	}

}