package net.adambruce.jpmq;

public class QueueExistsException extends Exception {
    public QueueExistsException(String msg) {
           super(msg);
    }
}