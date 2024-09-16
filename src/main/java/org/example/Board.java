package org.example;
import lombok.Getter;

import lombok.Setter;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.pow;

@Setter
@Getter
public class Board {
  private List<Integer> emptyPositions;
  public static final char X_SYMBOL = 'X';
  public static final char O_SYMBOL = 'O';
  private static final int X_SYMBOL_VALUE = -1;
  private static final char SPACE = ' ';
  private static final int O_SYMBOL_VALUE = -2;
  private static final String SEPARATOR = "|";
  private static final String RED = "\u001B[31m";
  private static final String GREEN = "\u001B[32m";
  private static final String WHITE = "\u001B[0m";
  private static final String LINE_ELEMENT = "-";
  private List<List<Integer>> board;
  private int boardSize;

  private void print(String text) {
    System.out.print(text);
  }

 Board()
 {
    this.board = new ArrayList<>();
    this.emptyPositions = new ArrayList<>();
 }

  public void initializeBoard() {
    int positionCount = 1;
    this.board.clear();
    for (int i = 0; i < this.boardSize; i++) {
      this.getBoard().add(new ArrayList<>(this.getBoardSize()));
      for (int j = 0; j < this.boardSize; j++) {
        board.get(i).add(positionCount);
        positionCount++;
      }
    }
    prepareComputerMoves();
  }

  public void prepareComputerMoves() {
    emptyPositions.clear();
    emptyPositions =  IntStream.rangeClosed(1, (int) pow(boardSize,2)).boxed().collect(Collectors.toList());
    Collections.shuffle(emptyPositions);
  }

  public void showBoard() {
    for (int i = 0; i < this.boardSize; i++) {
      for (int j = 0; j < this.boardSize; j++) {
        int symbol = board.get(i).get(j);


          switch (symbol) {
            case X_SYMBOL_VALUE:
              {
                print(SPACE+RED+X_SYMBOL+WHITE+SPACE);
                if (j < board.get(i).size() - 1) {
                  print(SEPARATOR);
                }
                break;
              }

            case O_SYMBOL_VALUE:
              {
                print(SPACE+ GREEN + O_SYMBOL + WHITE+SPACE);
                if (j < board.get(i).size() - 1) {
                  print(SEPARATOR);
                }
                break;
              }
            default:
              {
                print(" "+board.get(i).get(j)+" ");
                if (j < board.get(i).size() - 1) {
                  print(SEPARATOR);
                }
                break;
              }
          }
  }

      System.out.println();
      if (i < this.boardSize - 1) System.out.println(LINE_ELEMENT.repeat(boardSize*3+boardSize));
    }
  }
}
