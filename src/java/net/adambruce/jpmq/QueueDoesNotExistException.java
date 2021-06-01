package net.adambruce.jpmq;

/**
 * A QueueDoesNotExistException is thrown when attempting to open a
 * non-existent queue.
 *
 * @since 1.0
 * @author Adam Bruce
 */
public class QueueDoesNotExistException extends Exception {

        /**
         * Creates a new QueueDoesNotExistException.
         *
         * @param msg the message from the thrower
         * @since 1.0
         */
    public QueueDoesNotExistException(String msg) {
           super(msg);
    }
}