package org.example;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Board {
    private List<Integer> intList ;
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String WHITE = "\u001B[0m";
    private static final String LINE_ELEMENT = "-";
    private char[][] board ;

    public char[][] getBoard() {
        return board;
    }

    public List<Integer> getIntList() {
        return intList;
    }


    public Board()
    {
         board = new char[3][3];
          intList = new ArrayList<>();
    }
    public  void initializeBoard() {
        int a = 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = (char) (a + 48);
                a++;
            }
        }
        prepareComputerMoves();
    }
    public void prepareComputerMoves()  {

        intList.clear();
        intList.addAll(Arrays.asList(1, 2, 4, 3, 5, 6, 7, 8, 9));
        Collections.shuffle(intList);
    }

    public void showBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (j < board[i].length - 1) {
                    if (board[i][j] == 'X') {
                        System.out.print(RED + board[i][j] + WHITE);

                        System.out.print(" | ");
                    } else if (board[i][j] == 'O') {
                        System.out.print(GREEN + board[i][j] + WHITE);
                        System.out.print(" | ");
                    } else {
                        System.out.print(board[i][j] + " | ");
                    }
                } else {
                    if (board[i][j] == 'X') {
                        System.out.print(RED + board[i][j] + WHITE);
                    } else if (board[i][j] == 'O') {
                        System.out.print(GREEN + board[i][j] + WHITE);
                    } else {
                        System.out.print(board[i][j]);
                    }
                }
            }
            System.out.println();
            if (i < board.length - 1)
                System.out.println(LINE_ELEMENT.repeat(10));
        }
    }


}
