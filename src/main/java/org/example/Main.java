package org.example;

import java.security.NoSuchAlgorithmException;
import java.util.*;

import static java.lang.System.exit;

//make user defined  exceptions
//create redme file
//time compleixt
//improve code quality
//conventions like name variable method
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static String RED = "\u001B[31m";
    static String GREEN = "\u001B[32m";
    static String WHITE = "\u001B[0m";
    static String player = "";
    static char symbol;
    static char computerSymbol;
    static List<Integer> intList = new ArrayList<>();

    static char[][] board = new char[3][3];

    public static void showBoard() {
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
                System.out.println("----------");
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {


        initialStartup();
    }

    private static void initialStartup() throws NoSuchAlgorithmException {
        manual();
        initializeBoard();
        showBoard();
        Scanner sc = new Scanner(System.in);
        while (player.isEmpty()) {
            System.out.println("Enter your name ✏\uFE0F");
            player = sc.nextLine();
        }


        do {
            try {

                String result = String.format("%s choose your symbol from { X, O }: ", player);
                System.out.print(result);
                symbol = sc.nextLine().charAt(0);
            } catch (InputMismatchException e) {
                System.out.println("Plzz enter only mentioned characters !! ");
            }
        } while (!("Character".equals(((Object) symbol).getClass().getSimpleName())) || !(symbol == 'X' || symbol == 'O'));

        if (symbol == 'X') {
            computerSymbol = 'O';
        } else {
            computerSymbol = 'X';
        }
        startPlay();
    }


    private static void startPlay() throws NoSuchAlgorithmException {

        if (symbol == 'X') {
            while (!gameEnd())
            {
                playerPlay();
                computerPlay();
            }

        } else {
            while (!gameEnd()) {
                computerPlay();
                playerPlay();
            }
        }

    }

    private static void menu() throws NoSuchAlgorithmException {

        System.out.println("\uD83D\uDCA1 If u wanna replay ♻\uFE0F then press R else press aything ( except power button \uD83D\uDE05 )and Enter to quit ⛩\uFE0F !! ");
        Scanner sc = new Scanner(System.in);
        String pref = sc.nextLine();
        if (!pref.isEmpty() && 'R' == pref.charAt(0)) {
            initialStartup();
        } else {
            exit(0);
        }
    }

    private static boolean gameEnd() throws NoSuchAlgorithmException {
        for (int i = 0; i < board.length; i++) {
            char ch = board[i][0];
            if (ch == board[i][1] && ch == board[i][2]) {
                whoWin(ch);
                menu();
                return true;
            }
        }

        for (int j = 0; j < board.length; j++) {
            char ch = board[0][j];
            if (ch == board[1][j] && ch == board[2][j]) {
                whoWin(ch);
                menu();
                return true;
            }
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            whoWin(board[0][0]);
            menu();
            return true;
        }

        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            whoWin(board[0][2]);
            menu();
            return true;
        }
        if (intList.isEmpty()) {
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


    private static void whoWin(char ch) {
        if (ch == symbol) {
            for (int i = 0; i < 25; i++) {
                System.out.print("\uD83E\uDEC5 ");
            }
            System.out.println("");
            String result = String.format("Hurray \uD83E\uDD73, %s you nailed it \uD83E\uDD29 !: ", player);
            System.out.println(result);
            //exit(0);
        } else {
            for (int i = 0; i < 25; i++) {
                System.out.print("\uD83E\uDD16 ");
            }
            System.out.println("");
            String result = "Cyborg win ! Bring on your defense game \uD83D\uDCAA next time !";
            System.out.println(result);

        }

    }

    private static void computerPlay() throws NoSuchAlgorithmException {
        if (!gameEnd()) {
            Iterator<Integer> i = intList.iterator();
            int position = intList.get(0);
            intList.remove(0);
            int row = (position - 1) / 3;
            int col = position - (row * 3) - 1;
            board[row][col] = computerSymbol;
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~  Cyborg's Move  ~~~~~~~~~~~~~~~~~~~~~~~~~~");
            showBoard();
        }
    }

    //code conventions !!!
    private static void playerPlay() throws NoSuchAlgorithmException {
        if (!gameEnd()) {

            int position = -1;
            Scanner sc = new Scanner(System.in);


            do {
                try {

                    String result = String.format("%s enter empty location for the next move from 1 to 9 : ", player);
                    System.out.print(result);
                    position = sc.nextInt();

                } catch (InputMismatchException e) {
                    System.out.println("Plzz Enter only numbers  from 1 to 9 !! ");

                } finally {
                    sc.nextLine();
                }

            } while (!("Integer".equals(((Object) position).getClass().getSimpleName())) || !(position >= 1 && position <= 9) || !intList.contains(position));


            int row = (position - 1) / 3;
            int col = position - (row * 3) - 1;
            board[row][col] = symbol;
            int pos = intList.indexOf(position);
            intList.remove(pos);
            String result = String.format("~~~~~~~~~~~~~~~~~~~~~~~~~~~  %s's Move  ~~~~~~~~~~~~~~~~~~~~~~~~~~", player);
            System.out.println(result);

            showBoard();
        }

    }

    private static void manual() {
        System.out.println("\uD83C\uDF69 \uD83C\uDF69 Welcome to the classical Tic-Tac-Toe game \uD83C\uDF69 \uD83C\uDF69");
        System.out.println("\uD83D\uDCA1 Start the game by placing Xs and Os alternatively\n" +
                "\uD83D\uDCA1 The goal is to get three of your symbols in a row, column or diagonal \n" +
                "\uD83D\uDCA1 The game ends when one player wins or the grid is full, resulting in a TIE\n" +
                "Happy Gaming \uD83C\uDFAE");

    }

    private static void prepareComputerMoves() throws NoSuchAlgorithmException {

        intList.clear();

        intList.addAll(Arrays.asList(1, 2, 4, 3, 5, 6, 7, 8, 9));


        Collections.shuffle(intList);
    }

    private static void initializeBoard() throws NoSuchAlgorithmException {
        int a = 1;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = (char) (a + 48);
                a++;
            }
        }
        prepareComputerMoves();
    }


}