package net.adambruce.jpmq;

/**
 * A DescriptorLimitException is thrown whenever the process or global
 * message queue descriptor limit is reached.
 *
 * @since 1.0
 * @author Adam Bruce
 */
public class DescriptorLimitException extends Exception {

        /**
         * Creates a new DescriptorLimitException.
         *
         * @param msg the message from the thrower
         * @since 1.0
         */
    public DescriptorLimitException(String msg) {
           super(msg);
    }
}