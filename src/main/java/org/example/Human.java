package org.example;

import java.util.Scanner;

public class Human extends Player{


public Human()
{

}
    public Human(String name, char symbol) {
        super(name, symbol);
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    public void setSymbol() {
        Scanner sc = new Scanner(System.in);
        do {


            String result = String.format("%s choose your playerSymbol from { X, O }: ", this.getName());
            System.out.print(result);
             this.symbol = sc.nextLine().charAt(0);

        } while (!("Character".equals(((Object) this.symbol).getClass().getSimpleName())) || !(this.symbol == 'X' || this.symbol == 'O'));

    }
}
