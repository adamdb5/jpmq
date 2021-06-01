package net.adambruce.jpmq;

/**
 * A MessageLengthException is thrown when the message sent is longer than the
 * message size for the queue.
 *
 * @since 1.0
 * @author Adam Bruce
 */
public class MessageLengthException extends Exception {

        /**
         * Creates a new MessageLengthException.
         *
         * @param msg the message from the thrower
         * @since 1.0
         */
    public MessageLengthException(String msg) {
           super(msg);
    }
}