package net.adambruce.jpmq;

public class QueueFullException extends Exception {
    public QueueFullException(String msg) {
           super(msg);
    }
}