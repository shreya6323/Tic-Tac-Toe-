package org.example;

import java.util.Scanner;

public class Player {
    protected char symbol;
    private String name;


    public Player(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public Player() {
    }

    public String getName() {
        return name;
    }

    public void setName() {
        Scanner sc = new Scanner(System.in);
      do{
            System.out.println("Enter your name ✏\uFE0F");
            name = sc.nextLine();
        }  while (name.isEmpty());

    }

    public char getSymbol() {
        return symbol;
    }


    public void setSymbol() {
    }

    public void setSymbol(char c) {
    }
}
