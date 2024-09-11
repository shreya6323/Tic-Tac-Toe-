package org.example;

public class OutOfRange extends RuntimeException{
    public OutOfRange() {
        super();
    }

    public OutOfRange(String message) {
        super(message);
    }
}
