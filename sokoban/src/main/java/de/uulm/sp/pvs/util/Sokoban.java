package de.uulm.sp.pvs.util;

public class Sokoban {

  /**
   * Finds @ inside a 2D char array
   * 
   * @param gameBoard char array to check
   * @return Pair containing X and Y coordinates of @ (-1,-1) if non existent
   */
  public static Pair<Integer, Integer> findPlayer(char[][] gameBoard) {

    for (int row = 0; row < gameBoard.length; row++) {
      for (int column = 0; column < gameBoard.length; column++) {
        if (gameBoard[row][column] == '@') {
          return new Pair<>(row, column);
        }
      }
    }
    return new Pair<>(-1, -1);
  }

  /**
   * Enum for representing cardinal points.
   * 
   * @author stefan
   *
   */
  private static enum CardinalPoint {
    NORTH, EAST, SOUTH, WEST
  }

  /**
   * Move @ within charArray one up
   * 
   * @param gameBoard
   * @return true if it was possible false if it wasn't
   */
  public static boolean moveNorth(char[][] gameBoard) {
    return moveDirection(CardinalPoint.NORTH, gameBoard);
  }

  /**
   * Move @ within charArray one to the right
   * 
   * @param gameBoard
   * @return true if it was possible false if it wasn't
   */
  public static boolean moveEast(char[][] gameBoard) {
    return moveDirection(CardinalPoint.EAST, gameBoard);
  }

  /**
   * Move @ within charArray one down
   * 
   * @param gameBoard
   * @return true if it was possible false if it wasn't
   */
  public static boolean moveSouth(char[][] gameBoard) {
    return moveDirection(CardinalPoint.SOUTH, gameBoard);
  }

  /**
   * Move @ within charArray one to the left
   * 
   * @param gameBoard
   * @return true if it was possible false if it wasn't
   */
  public static boolean moveWest(char[][] gameBoard) {
    return moveDirection(CardinalPoint.WEST, gameBoard);
  }


  /**
   * Move @ within char array in specific direction
   * 
   * @param direction which direction to move
   * @param gameBoard
   * @return true if it was possible, false if it was not.
   */
  private static boolean moveDirection(CardinalPoint direction, char[][] gameBoard) {
    // Fetch current player position if illegal exit
    var playerPosition = findPlayer(gameBoard);
    if (playerPosition.getFirst() <= -1 || playerPosition.getSecond() <= -1)
      return false;

    // Initialise variables for representing X and Y coordinates of player.
    int playerX = playerPosition.getFirst();
    int playerY = playerPosition.getSecond();

    // Define movement of player based on @direction.
    int xMovement = 0;
    int yMovement = 0;
    switch (direction) {
      case NORTH:
        xMovement = -1;
        break;
      case EAST:
        yMovement = 1;
        break;
      case SOUTH:
        xMovement = 1;
        break;
      case WEST:
        yMovement = -1;
        break;
    }
    // Future player position.
    int playerXNext = playerX + xMovement;
    int playerYNext = playerY + yMovement;
    // Future position of object in front of player.
    int playerXNextNext = playerX + 2 * xMovement;
    int playerYNextNext = playerY + 2 * yMovement;

    // Move player and object in front of player in accordance with game rules.
    if (playerXNext > -1 && playerYNext > -1 && gameBoard[playerXNext][playerYNext] == '.') {
      gameBoard[playerXNext][playerYNext] = '@';
      gameBoard[playerX][playerY] = '.';
      return true;
    } else if (playerXNextNext > -1 && playerYNextNext > -1
        && gameBoard[playerXNext][playerYNext] == '$'
        && gameBoard[playerXNextNext][playerYNextNext] == '.') {
      gameBoard[playerXNext][playerYNext] = '@';
      gameBoard[playerXNextNext][playerYNextNext] = '$';
      gameBoard[playerX][playerY] = '.';
      return true;
    } else {
      return false;
    }
  }

  /**
   * transforms 2D char array into single string with \n between each line.
   * 
   * @param gameBoard
   * @return string
   */
  public static String sokobanToString(char[][] gameBoard) {
    var stringRepresentation = "";
    for (int i = 0; i < gameBoard.length; i++) {
      stringRepresentation += new String(gameBoard[i]) + "\n";
    }

    return stringRepresentation;
  }

}
