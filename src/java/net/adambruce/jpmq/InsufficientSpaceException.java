package net.adambruce.jpmq;

/**
 * An InsufficientSpaceException is thrown when there is insufficient space
 * to create a new message queue.
 *
 * @since 1.0
 * @author Adam Bruce
 */
public class InsufficientSpaceException extends Exception {

        /**
         * Creates a new InsufficientSpaceException.
         *
         * @param msg the message from the thrower
         * @since 1.0
         */
    public InsufficientSpaceException(String msg) {
           super(msg);
    }
}