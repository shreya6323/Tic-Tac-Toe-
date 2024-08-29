package org.example;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

import static java.lang.System.exit;

public class Game {
    private Board boardobj;
    private Player player;
    private Player cyborg;

    public Game(Player player, Player cyborg) {
        this.boardobj = new Board();
        this.player = player;
        this.cyborg = cyborg;
    }


    private void whoWin(char ch) {
        if (ch == player.getSymbol()) {
            for (int i = 0; i < 25; i++) {
                System.out.print("\uD83E\uDEC5 ");
            }
            System.out.println("");
            String result = String.format("Hurray \uD83E\uDD73, %s you nailed it \uD83E\uDD29 !: ", player.getName());
            System.out.println(result);

        } else {
            for (int i = 0; i < 25; i++) {
                System.out.print("\uD83E\uDD16 ");
            }
            System.out.println("");
            String result = "Cyborg win ! Bring on your defense game \uD83D\uDCAA next time !";
            System.out.println(result);

        }

    }

    void initialStartup() {

        manual();
        boardobj.initializeBoard();
        boardobj.showBoard();
        player.setName();
        player.setSymbol();
        cyborg.setSymbol( (player.getSymbol() == 'X')?'O':'X');
        startPlay();
  }

    private  void startPlay()  {

        if (player.getSymbol() == 'X') {
            while (true) {
                playerPlay();
                computerPlay();
            }

        } else {
            while (true) {
                computerPlay();
                playerPlay();
            }
        }

    }



    private  void computerPlay() {

        Iterator<Integer> i = boardobj.getIntList().iterator();
        int position = boardobj.getIntList().get(0);
        boardobj.getIntList().remove(0);
        int row = (position - 1) / 3;
        int col = position - (row * 3) - 1;
        boardobj.getBoard()[row][col] = cyborg.getSymbol();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~  Cyborg's Move  ~~~~~~~~~~~~~~~~~~~~~~~~~~");
        boardobj.showBoard();
        isWinningMoveOrTie(cyborg.getSymbol());
    }


    private void playerPlay()  {
        int position = -1;
        Scanner sc = new Scanner(System.in);

        do {
            try {

                String result = String.format("%s enter empty location for the next move from 1 to 9 : ", player.getName());
                System.out.print(result);
                position = sc.nextInt();

            } catch (InputMismatchException e) {
                System.out.println("Plzz Enter only numbers  from 1 to 9 !! ");

            } finally {
                sc.nextLine();
            }

        } while (!("Integer".equals(((Object) position).getClass().getSimpleName())) || !(position >= 1 && position <= 9) || !boardobj.getIntList().contains(position));


        int row = (position - 1) / 3;
        int col = position - (row * 3) - 1;

        boardobj.getBoard()[row][col] = player.getSymbol();

        int pos = boardobj.getIntList().indexOf(position);
        boardobj.getIntList().remove(pos);
        String result = String.format("~~~~~~~~~~~~~~~~~~~~~~~~~~~  %s's Move  ~~~~~~~~~~~~~~~~~~~~~~~~~~", player.getName());
        System.out.println(result);
        boardobj.showBoard();
        isWinningMoveOrTie(player.getSymbol());

    }

    private void manual() {
        System.out.println("\uD83C\uDF69 \uD83C\uDF69 Welcome to the classical Tic-Tac-Toe game \uD83C\uDF69 \uD83C\uDF69");
        System.out.println("\uD83D\uDCA1 Start the game by placing Xs and Os alternatively\n" +
                "\uD83D\uDCA1 The goal is to get three of your symbols in a row, column or diagonal \n" +
                "\uD83D\uDCA1 The game ends when one player wins or the grid is full, resulting in a TIE\n" +
                "Happy Gaming \uD83C\uDFAE");
    }

    private void rePlay()
    {
        manual();
        boardobj.initializeBoard();
        boardobj.showBoard();
        player.setSymbol();
        cyborg.setSymbol( (player.getSymbol() == 'X')?'O':'X');
        startPlay();
    }

    private void menu() {
        System.out.println("\uD83D\uDCA1 If u wanna replay ♻\uFE0F then press R else press aything ( except power button \uD83D\uDE05 )and Enter to quit ⛩\uFE0F !! ");
        Scanner sc = new Scanner(System.in);
        String pref = sc.nextLine();
        if (!pref.isEmpty() && 'R' == pref.charAt(0)) {
          rePlay();
        } else {
            exit(0);
        }
    }

    private  boolean isWinningMoveOrTie(char symbol) {
        char[][] board = boardobj.getBoard();
        for (int i = 0; i < 3; i++) {//checking row and column trio
            if ((board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol)
                    ||
                    (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol)
            ) {
                whoWin(symbol);
                menu();
                return true;
            }
        }
            if ((board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol)
                    ||
                    (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol)) {
                whoWin(symbol);
                menu();//checking diagonal trio
                return true;
            }
            if (boardobj.getIntList().isEmpty()) {//checking tie
                for (int i = 0; i < 20; i++) {
                    System.out.print("\uD83D\uDE48 ");
                }
                System.out.println("");
                String result = "It's a TIE \uD83D\uDC94";
                System.out.println(result);
                menu();
                return true;
            }
            return false;
        }

}