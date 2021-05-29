package net.adambruce.jpmq;

public class QueueEmptyException extends Exception {
    public QueueEmptyException(String msg) {
           super(msg);
    }
}