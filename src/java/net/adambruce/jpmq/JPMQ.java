package net.adambruce.jpmq;


/**
 * @file JPMQ.java
 * @brief The JPMQ class represents a POSIX message queue.
 * @author Adam Bruce
 * @date 11 Apr 2021
 */

public class JPMQ {

    /* Load the shared object */
	static {
		System.loadLibrary("jpmq");
	}
	
    /* O_ flags */
    public static final int O_RDONLY    = 0b0000001;
    public static final int O_WRONLY    = 0b0000010;
    public static final int O_RDWR      = 0b0000100;
    public static final int O_CLOEXEC   = 0b0001000;
    public static final int O_CREAT     = 0b0010000;
    public static final int O_EXCL      = 0b0100000;
    public static final int O_NONBLOCK  = 0b1000000;

    /* Message queue name and descriptor */
    private byte[] descriptor;
    private String name;
    
    /**
     * Opens an existing message queue.
     * 
     * @param name the name of the message queue
     * @param oflag the open flags (O_)
     */
    public JPMQ(String name, int oflags) throws
        AccessException, InvalidValueException, DescriptorLimitException,
        NameTooLongException, QueueDoesNotExistException, InsufficientMemoryException {

        try {
            descriptor = nativeOpen(name, oflags);
            this.name = name;
		} catch (Exception e) {
		    throw e;
		}
    }
    
    /**
     * Opens a message queue, or creates a new message queue with attributes.
     * 
     * @param name the name of the message queue
     * @param oflag the open flags (O_)
     * @param mode the file mode
     * @param attr the message queue attributes
     */
    public JPMQ(String name, int oflags, int mode, JPMQAttributes attributes) throws
        AccessException, QueueExistsException, InvalidValueException, DescriptorLimitException,
        NameTooLongException, InsufficientMemoryException, InsufficientSpaceException {

		try {
            descriptor = nativeOpenWithAttributes(name, oflags, mode, attributes);
            this.name = name;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Closes the connection to the message queue but does not unlink it.
     * 
     * @return the output of mq_close
     */
    public void close() throws BadDescriptorException {
    	try {
    	    nativeClose(descriptor);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Unlinks (destroys) the message queue.
     * 
     * @return the output of mq_unlink
     */
    public void unlink() throws AccessException, NameTooLongException,
        QueueDoesNotExistException {

        try {
    	    nativeUnlink(name);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Returns the message queue descriptor.
     * 
     * @return the message queue descriptor
     */
    public byte[] getDescriptor() {
    	return descriptor;
    }

    /**
     * Returns the name of the message queue.
     * 
     * @return the name of the message queue
     */
    public String getName() {
    	return name;
    }

    /**
     * Returns the attributes of the message queue.
     * 
     * @return the attributes of the message queue
     */
    public JPMQAttributes getAttributes() throws BadDescriptorException {
    	try {
    	    return nativeGetAttributes(descriptor);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Sets the attributes of the message queue.
     * NOTE: The POSIX standard will ignore all attributes other than the
     * O_NONBLOCK option.
     * 
     * @param attr the new attributes
     * @return the output of mq_setattr
     */
    public void setAttributes(JPMQAttributes attributes) throws BadDescriptorException,
        InvalidValueException {

        try {
    	    nativeSetAttributes(descriptor, attributes);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Receives a message from the message queue.
     * 
     * @return the message received
     */
    public String receive() throws
        QueueEmptyException, BadDescriptorException, InterruptException,
        MessageLengthException {

        try {
    	    return nativeReceive(descriptor);
    	} catch (Exception e) {
    	    throw e;
    	}
    }

    /**
     * Sends a message to the message queue.
     * 
     * @param message the message to send
     * @param priority the priority of the message
     * @return the output of mq_send
     */
    public void send(String message, int priority) throws
        QueueFullException, BadDescriptorException, InterruptException, InvalidValueException,
        MessageLengthException {

        try {
    	    nativeSend(descriptor, message, message.length(), priority);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Attempts to receive a message from the queue. The call will continue
     * trying to receive a message until the timeout is reached, at which point
     * the call will return.
     * 
     * @param timespec the timeout for receiving a message
     * @return the message received
     */
    public String timedReceive(JPMQTimespec timespec) throws
        QueueFullException, BadDescriptorException, InterruptException, InvalidValueException,
        MessageLengthException, TimeoutException {

    	try {
    	    return nativeTimedReceive(descriptor, timespec);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Attempts to send a message to the queue. If the queue is full, the call
     * will continue trying to send the message until the timeout is reached,
     * at which point the call will return.
     * 
     * @param priority the priority of the message
     * @param timespec the timeout for sending the message
     * @return the output of mq_timedsend
     */
    public void timedSend(String str, int priority, JPMQTimespec timespec) throws
        QueueEmptyException, BadDescriptorException, InterruptException, InvalidValueException,
        MessageLengthException, TimeoutException {

        try {
    	    nativeTimedSend(descriptor, str, str.length(), priority, timespec);
        } catch (Exception e) {
        throw e;
        }
    }
     
    /* Native */
    private native byte[] nativeOpen(String name, int oflag) throws
        AccessException, InvalidValueException, DescriptorLimitException,
        NameTooLongException, QueueDoesNotExistException, InsufficientMemoryException;

    private native byte[] nativeOpenWithAttributes(String name, int oflag, int mode, JPMQAttributes attr) throws
        AccessException, QueueExistsException, InvalidValueException, DescriptorLimitException,
        NameTooLongException, InsufficientMemoryException, InsufficientSpaceException;

    private native void nativeClose(byte[] descriptor) throws BadDescriptorException;

    private native void nativeUnlink(String name) throws AccessException, NameTooLongException,
        QueueDoesNotExistException;

    private native JPMQAttributes nativeGetAttributes(byte[] descriptor) throws BadDescriptorException;

    private native void nativeSetAttributes(byte[] descriptor, JPMQAttributes attr) throws
        BadDescriptorException, InvalidValueException;

    private native String nativeReceive(byte[] descriptor) throws
        QueueEmptyException, BadDescriptorException, InterruptException,
        MessageLengthException;

    private native void nativeSend(byte[] descriptor, String str, int length, int priority) throws
        QueueFullException, BadDescriptorException, InterruptException,
        MessageLengthException;


    private native String nativeTimedReceive(byte[] descriptor, JPMQTimespec timespec) throws
        QueueFullException, BadDescriptorException, InterruptException, InvalidValueException,
        MessageLengthException, TimeoutException;


    private native void nativeTimedSend(byte[] descriptor, String str, int length, int priority, JPMQTimespec timespec) throws
        QueueEmptyException, BadDescriptorException, InterruptException, InvalidValueException,
        MessageLengthException, TimeoutException;
}
