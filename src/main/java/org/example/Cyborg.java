package org.example;

public class Cyborg extends Player{

    public Cyborg(String name, char symbol) {
        super(name, symbol);
    }
    private char symbol;


    public Cyborg()
    {

    }
    @Override
    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }
}
