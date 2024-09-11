package org.example;

public class OutOfPosition extends RuntimeException{
    public OutOfPosition() {
    }

    public OutOfPosition(String message) {
        super(message);
    }
}
