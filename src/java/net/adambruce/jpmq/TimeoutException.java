package net.adambruce.jpmq;

public class TimeoutException extends Exception {
    public TimeoutException(String msg) {
           super(msg);
    }
}