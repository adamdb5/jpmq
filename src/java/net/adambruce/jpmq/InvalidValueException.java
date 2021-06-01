package net.adambruce.jpmq;

/**
 * An InvalidValueException is thrown when an invalid value is passed to
 * one of the native POSIX functions.
 *
 * @since 1.0
 * @author Adam Bruce
 */
public class InvalidValueException extends Exception {

        /**
         * Creates a new InvalidValueException.
         *
         * @param msg the message from the thrower
         * @since 1.0
         */
    public InvalidValueException(String msg) {
           super(msg);
    }
}