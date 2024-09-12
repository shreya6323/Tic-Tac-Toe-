package org.example;
import lombok.Getter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
public class Board {
  private List<Integer> emptyPositions;
  private static final char X_SYMBOL = 'X';
  private static final char O_SYMBOL = 'O';
  private static final String SEPARATOR = " | ";
  private static final String RED = "\u001B[31m";
  private static final String GREEN = "\u001B[32m";
  private static final String WHITE = "\u001B[0m";
  private char[][] board;

  private void print(String text) {
    System.out.print(text);
  }

  public Board() {
    board = new char[3][3];
    emptyPositions = new ArrayList<>();
  }

  public void initializeBoard() {
    int positionCount = 1;
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        board[i][j] = (char) (positionCount + 48);
        positionCount++;
      }
    }
    prepareComputerMoves();
  }

  public void prepareComputerMoves() {

    emptyPositions.clear();
    emptyPositions.addAll(Arrays.asList(1, 2, 4, 3, 5, 6, 7, 8, 9));
    Collections.shuffle(emptyPositions);
  }

  public void showBoard() {
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {
        char symbol = board[i][j];
        if (j < board[i].length - 1) {

          switch (symbol) {
            case X_SYMBOL:
              {
                print(RED + board[i][j] + WHITE);
                print(SEPARATOR);
                break;
              }

            case O_SYMBOL:
              {
                print(GREEN + board[i][j] + WHITE);
                print(SEPARATOR);
                break;
              }
            default:
              {
                print(board[i][j] + SEPARATOR);
                break;
              }
          }

        } else {
          switch (symbol) {
            case X_SYMBOL:
              {
                print(RED + board[i][j] + WHITE);
                break;
              }

            case O_SYMBOL:
              {
                print(GREEN + board[i][j] + WHITE);
                break;
              }
            default:
              {
                print(String.valueOf(board[i][j]));
                break;
              }
          }
        }
      }
      System.out.println();
      if (i < board.length - 1) System.out.println("----------");
    }
  }
}
