package org.example;

public class EasyStrategy implements  Strategy{

    Board board;
    public EasyStrategy(Board board) {
        this.board = board;
    }

    @Override
    public int move() {
        return board.getEmptyPositions().getFirst();
    }
}
