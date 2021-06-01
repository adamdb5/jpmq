package net.adambruce.jpmq;

/**
 * An AccessException is caused by attempting to open a queue that this
 * process does not have permission to access.
 *
 * @since 1.0
 * @author Adam Bruce
 */
public class AccessException extends Exception {

    /**
     * Creates a new AccessException.
     *
     * @param msg the message from the thrower
     * @since 1.0
     */
    public AccessException(String msg) {
           super(msg);
    }
}