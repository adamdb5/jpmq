package net.adambruce.jpmq;

public class QueueDoesNotExistException extends Exception {
    public QueueDoesNotExistException(String msg) {
           super(msg);
    }
}