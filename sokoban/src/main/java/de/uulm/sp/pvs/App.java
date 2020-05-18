package de.uulm.sp.pvs;

import java.util.Scanner;

import de.uulm.sp.pvs.util.Sokoban;

public class App {
	public static void main(String[] args) {
		char[][] board = SokobanBoards.getDefaultBoard();

		System.out.print("\nStarting game! ");
		{ Scanner scanner = new Scanner(System.in);
			printBoardAndAskForInput(board);
			for (String stdInput = scanner.next(); null != stdInput; stdInput = scanner.next()) {
				System.out.println(stdInput);
				printBoardAndAskForInput(board);
			}
		scanner.close();}
	}

	static void printBoardAndAskForInput(char[][] board){
		System.out.printf("Current Board:\n\n%s\nInput: ", Sokoban.sokobanToString(board));
	}
}
