package net.adambruce.jpmq;

/**
 * An InsufficientMemoryException is thrown when the system has insufficient
 * memory to open the queue.
 *
 * @since 1.0
 * @author Adam Bruce
 */
public class InsufficientMemoryException extends Exception {

        /**
         * Creates a new InsufficientMemoryException.
         *
         * @param msg the message from the thrower
         * @since 1.0
         */
    public InsufficientMemoryException(String msg) {
           super(msg);
    }
}