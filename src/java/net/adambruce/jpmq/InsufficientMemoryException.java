package net.adambruce.jpmq;

public class InsufficientMemoryException extends Exception {
    public InsufficientMemoryException(String msg) {
           super(msg);
    }
}