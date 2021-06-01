package net.adambruce.jpmq;

/**
 * A BadDescriptorException is thrown whenever an invalid message queue
 * descriptor is used.
 *
 * @since 1.0
 * @author Adam Bruce
 */
public class BadDescriptorException extends Exception {

        /**
         * Creates a new BadDescriptorException.
         *
         * @param msg the message from the thrower
         * @since 1.0
         */
    public BadDescriptorException(String msg) {
           super(msg);
    }
}