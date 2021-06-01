package net.adambruce.jpmq;

/**
 * A NameTooLongException is thrown when the provided name is longer than
 * the maximum name length specified by the OS.
 *
 * @since 1.0
 * @author Adam Bruce
 */
public class NameTooLongException extends Exception {

        /**
         * Creates a new NameTooLongException.
         *
         * @param msg the message from the thrower
         * @since 1.0
         */
    public NameTooLongException(String msg) {
           super(msg);
    }
}