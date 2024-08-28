package org.example;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.IntStream;
//ad and remove comments
import static java.lang.System.exit;
//add tie and randomness
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static String RED = "\u001B[31m";
    static String GREEN = "\u001B[32m";
    static String player ;
    static char symbol ;
    static char computerSymbol;
    static List<Integer> intList = new ArrayList<>();

   static char[][] board = new char[3][3];

    public static void showBoard()
    {
             for(int i = 0;i<board.length;i++)
        {
            for(int j=0;j<board[i].length;j++)
            {
                if(j < board[i].length-1) {
                    System.out.print(board[i][j] + " | ");
                }
                else {
                    System.out.print(board[i][j]);
                }
            }
                 System.out.println();
            if(i<board.length-1)
                 System.out.println("----------");
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        manual();
        initializeBoard();
        showBoard();
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("Enter your name ✏\uFE0F");
            player = sc.nextLine();
        }while(player.isEmpty()
        );
        //make user defined  exceptions
       

        do {
            try {
                // Get input
                // System.out.print("Enter the number of students: ");
                String result = String.format("%s choose your symbol from { X, O }: ",player);
                System.out.print(result);
                symbol = sc.nextLine().charAt(0);
            } catch (InputMismatchException e) {
                System.out.println("Plzz enter only mentioned characters !! ");
            }
            //sc.nextLine(); // clears the buffer
        } while ( !("Character".equals(((Object) symbol).getClass().getSimpleName())) || !(symbol == 'X' || symbol == 'O' ) );
         
        if(symbol == 'X')
        {
            computerSymbol = 'O';
        }
        else{
            computerSymbol = 'X';
        }
          startPlay();

    }



    private static void startPlay() {
//conventions like name variable method
       if(symbol == 'X')
       {
           while(!gameEnd() )//win
           {
               playerPlay();
               computerPlay();
           }

       }
       else {
           while(!gameEnd() ) {
               computerPlay();
               playerPlay();
           }
       }

    }


    private static boolean gameEnd() {
        for(int i =0;i<board.length;i++)
        {
            char ch  = board[i][0];
            if(ch == board[i][1] && ch == board[i][2])
            {
                whoWin(ch);
                return true;
            }
        }

        for(int j =0;j<board.length;j++)
        {
            char ch  = board[0][j];
            if(ch == board[1][j] && ch == board[2][j])
            {
                whoWin(ch);
                return true;
            }
        }
        if(board[0][0] == board[1][1] && board[1][1] == board[2][2])
        {
            whoWin(board[0][0]);
            return true;
        }

        if(board[0][2] == board[1][1] && board[1][1] == board[2][0])
        {
            whoWin(board[0][2]);
            return true;
        }
        if(intList.isEmpty())
        {
            String result = "It's a TIE \uD83D\uDE48";
            System.out.print(result);
            exit(0);
        }
        return false;


    }

    private static void whoWin(char ch) {
        if(ch == symbol)
        {
            String result = String.format("Hurray \uD83E\uDD73, %s you nailed it \uD83E\uDD29 !: ",player);
            System.out.print(result);
            exit(0);
        }
        else{
            String result = "Cyborg win \uD83E\uDD16 ! Bring on your defense game \uD83D\uDCAA next time !";
            System.out.print(result);
            exit(0);
        }

    }

    private static void computerPlay() {
        if(!gameEnd()) {
            Iterator<Integer> i = intList.iterator();
            int position = intList.get(0);
           // int pos = intList.indexOf(position);
            intList.remove(0);
            int row = (position - 1) / 3;
            int col = position - (row*3)-1;
            board[row][col] = computerSymbol;
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~  Cyborg's Move  ~~~~~~~~~~~~~~~~~~~~~~~~~~");
            showBoard();
        }
    }
//code conventions !!!
    private static void playerPlay() {
        if(!gameEnd()) {
           // System.out.println("Choose number from unreserved positions on the board !");
            int position=-1;
            Scanner sc = new Scanner(System.in);//check all the exceptions
          //  position = sc.nextInt();

            do {
                try {
                    // Get input
                    // System.out.print("Enter the number of students: ");
                    String result = String.format("%s enter unreserved position for the next move from 1 to 9 : ",player);
                    System.out.print(result);
                    position = sc.nextInt();

                } catch (InputMismatchException e) {
                    System.out.println("Plzz Enter only numbers  from 1 to 9 !! ");

                }
                finally {
                    sc.nextLine();
                }
                 // clears the buffer
            } while (!("Integer".equals(((Object) position).getClass().getSimpleName())) || !(position >=1 && position <=9) || !intList.contains(position));

//            while(  )
//            {
//                System.out.println("Kindly choose a numbers between 1 to 9 !!");
//                position = sc.nextInt();
//            }

//            while()
//            {
//                System.out.println("OOpssy.....Position is already filled.....Kindly choose another one !!");
//                position = sc.nextInt();
//            }

            int row = (position - 1) / 3;
            int col = position - (row*3)-1;
            board[row][col] = symbol;
            int pos = intList.indexOf(position);
            intList.remove(pos);
            String result = String.format("~~~~~~~~~~~~~~~~~~~~~~~~~~~  %s's Move  ~~~~~~~~~~~~~~~~~~~~~~~~~~",player);
            System.out.println(result);

            showBoard();
        }

    }
    private static void manual() {
        System.out.println("Welcome to Tic-Tac-Toe game");
        System.out.println("Provide the number to fill out the position for you !!\n Happy Gaming \uD83C\uDFAE");

    }

    private static void prepareComputerMoves() throws NoSuchAlgorithmException {
       //creating seed
//        SecureRandom secureRandom = SecureRandom.getInstance("NativePRNG");
//        IntStream intStream = secureRandom.ints(15,1,10);
//        PrimitiveIterator.OfInt random = intStream.iterator();
//         set.clear();
//        // Displaying the stream elements
//        while (random.hasNext() && set.size()<9) {
//            set.add(random.nextInt());
//        }
        intList.clear();
       // Integer[] intArray = { };
        intList.addAll(Arrays.asList(1, 2, 4, 3, 5, 6, 7 ,8,9));
        // intList = Arrays.asList(intArray);

        Collections.shuffle(intList);
        System.out.println(intList);
       // Collections.shuffle(list, new Random(2));
     //   intList.toArray(intArray);
//      set.addAll(intList);
//
//        Iterator iter = set.iterator();
//        while (iter.hasNext()) {
//            System.out.println(iter.next());
//        }
    }
    //make gui
    private static void initializeBoard() throws NoSuchAlgorithmException {
        int a  = 1;
        for(int i =0;i<board.length;i++)
        {
             for(int j =0;j < board[i].length;j++)
             {
                 board[i][j] = (char)(a+48);
                 a++;
             }
        }
        prepareComputerMoves();
    }


}