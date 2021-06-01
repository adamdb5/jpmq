package net.adambruce.jpmq;

/**
 * A QueueFullException is thrown when attempting to send a message to a
 * queue that has reached its capacity.
 *
 * @since 1.0
 * @author Adam Bruce
 */
public class QueueFullException extends Exception {

        /**
         * Creates a new QueueFullException.
         *
         * @param msg the message from the thrower
         * @since 1.0
         */
    public QueueFullException(String msg) {
           super(msg);
    }
}