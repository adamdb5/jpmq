package net.adambruce.jpmq;

/**
 * A TimeoutException is thrown when a call times out before sending or
 * receiving a message.
 *
 * @since 1.0
 * @author Adam Bruce
 */
public class TimeoutException extends Exception {

        /**
         * Creates a new TimeoutException.
         *
         * @param msg the message from the thrower
         * @since 1.0
         */
    public TimeoutException(String msg) {
           super(msg);
    }
}