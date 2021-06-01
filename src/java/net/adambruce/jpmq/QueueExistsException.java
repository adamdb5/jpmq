package net.adambruce.jpmq;

/**
 * A QueueExistsException is thrown when attempting to create a queue with the
 * same name as an existing queue.
 *
 * @since 1.0
 * @author Adam Bruce
 */
public class QueueExistsException extends Exception {

        /**
         * Creates a new QueueExistsException.
         *
         * @param msg the message from the thrower
         * @since 1.0
         */
    public QueueExistsException(String msg) {
           super(msg);
    }
}