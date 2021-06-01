package net.adambruce.jpmq;

/**
 * A QueueEmptyException is thrown when attempting to receive a message from an
 * empty queue, when O_NONBLOCK has been specified.
 *
 * @since 1.0
 * @author Adam Bruce
 */
public class QueueEmptyException extends Exception {

        /**
         * Creates a new QueueEmptyException.
         *
         * @param msg the message from the thrower
         * @since 1.0
         */
    public QueueEmptyException(String msg) {
           super(msg);
    }
}