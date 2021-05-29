package net.adambruce.jpmq;

public class NameTooLongException extends Exception {
    public NameTooLongException(String msg) {
           super(msg);
    }
}