package net.adambruce.jpmq;

public class InsufficientSpaceException extends Exception {
    public InsufficientSpaceException(String msg) {
           super(msg);
    }
}