package org.example;

public class HardStrategy implements Strategy{

    Board board;

    public HardStrategy(Board board) {
        this.board = board;
    }

    @Override
    public int move() {
        return board.getEmptyPositions().getFirst();//need to add hard logic
    }
}
