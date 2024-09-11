package org.example;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Board {
    private List<Integer> emptyPositions ;
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String WHITE = "\u001B[0m";
    private char[][] board ;

    public char[][] getBoard() {
        return board;
    }

    private void print(String text)
    {
        System.out.print(text);
    }
    public List<Integer> getEmptyPositions() {
        return emptyPositions;
    }


    public Board()
    {
         board = new char[3][3];
          emptyPositions = new ArrayList<>();
    }
    public  void initializeBoard() {
        int positionCount = 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = (char) (positionCount + 48);
                positionCount++;
            }
        }
        prepareComputerMoves();
    }
    public void prepareComputerMoves()  {

        emptyPositions.clear();
        emptyPositions.addAll(Arrays.asList(1, 2, 4, 3, 5, 6, 7, 8, 9));
        Collections.shuffle(emptyPositions);
    }

    public void showBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (j < board[i].length - 1) {
                    if (board[i][j] == 'X') {
                        print(RED + board[i][j] + WHITE);

                        print(" | ");
                    } else if (board[i][j] == 'O') {
                        print(GREEN + board[i][j] + WHITE);
                        print(" | ");
                    } else {
                        print(board[i][j] + " | ");
                    }
                } else {
                    if (board[i][j] == 'X') {
                        print(RED + board[i][j] + WHITE);
                    } else if (board[i][j] == 'O') {
                        print(GREEN + board[i][j] + WHITE);
                    } else {
                        print(String.valueOf(board[i][j]));
                    }
                }
            }
            System.out.println();
            if (i < board.length - 1)
                System.out.println("----------");
        }
    }


}
