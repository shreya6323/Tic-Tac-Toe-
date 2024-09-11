package org.example;

import java.util.Scanner;

abstract class Player {
    protected char symbol;
    private String name;



    public Player() {
    }



    public String getName() {
        return name;
    }

    public  void setName(String name){this.name = name;}

    public char getSymbol() {
        return symbol;
    }




    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }
}
