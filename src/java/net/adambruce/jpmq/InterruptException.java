package net.adambruce.jpmq;

/**
 * An InterruptException is thrown when a call is interrupted by a signal
 * handler.
 *
 * @author Adam Bruce
 */
public class InterruptException extends Exception {

        /**
         * Creates a new InterruptException.
         *
         * @param msg the message from the thrower
         * @since 1.0
         */
    public InterruptException(String msg) {
           super(msg);
    }
}