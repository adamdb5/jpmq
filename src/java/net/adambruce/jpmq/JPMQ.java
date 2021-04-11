package net.adambruce.jpmq;

/**
 * @file Jmq.java
 * @brief The Jmq class represents a POSIX message queue.
 * @author Adam Bruce
 * @date 08 Apr 2021
 */

public class JPMQ {

	static {
		System.loadLibrary("jpmq");
	}
	
    /* O_ flags - Need to find somewhere better to put these */
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
    public JPMQ(String name, int oflags) {
		descriptor = nativeOpen(name, oflags);
		this.name = name;
    }
    
    /**
     * Opens a message queue, or creates a new message queue with attributes.
     * 
     * @param name the name of the message queue
     * @param oflag the open flags (O_)
     * @param mode the file mode
     * @param attr the message queue attributes
     */
    public JPMQ(String name, int oflags, int mode, JPMQAttributes attributes) {
		descriptor = nativeOpenWithAttributes(name, oflags, mode, attributes);
		this.name = name;
    }

    /**
     * Closes the connection to the message queue but does not unlink it.
     * 
     * @return the output of mq_close
     */
    public int close() {
    	return nativeClose(descriptor);
    }

    /**
     * Unlinks (destroys) the message queue.
     * 
     * @return the output of mq_unlink
     */
    public int unlink() {
    	return nativeUnlink(name);
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
    public JPMQAttributes getAttributes() {
    	return nativeGetAttributes(descriptor);
    }

    /**
     * Sets the attributes of the message queue.
     * NOTE: The POSIX standard will ignore all attributes other than the
     * O_NONBLOCK option.
     * 
     * @param attr the new attributes
     * @return the output of mq_setattr
     */
    public int setAttributes(JPMQAttributes attributes) {
    	return nativeSetAttributes(descriptor, attributes);
    }

    /**
     * Receives a message from the message queue.
     * 
     * @return the message received
     */
    public String receive() {
    	return nativeReceive(descriptor);
    }

    /**
     * Sends a message to the message queue.
     * 
     * @param message the message to send
     * @param priority the priority of the message
     * @return the output of mq_send
     */
    public int send(String message, int priority) {
    	return nativeSend(descriptor, message, message.length(), priority);
    }

    /**
     * Attempts to receive a message from the queue. The call will continue
     * trying to receive a message until the timeout is reached, at which point
     * the call will return.
     * 
     * @param timespec the timeout for receiving a message
     * @return the message received
     */
    public String timedReceive(JPMQTimespec timespec) {
    	return nativeTimedReceive(descriptor, timespec);
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
    public int timedSend(String str, int priority, JPMQTimespec timespec) {
    	return nativeTimedSend(descriptor, str, str.length(), priority, timespec);
    }
     
    /* Native */
    private native byte[] nativeOpen(String name, int oflag);
    private native byte[] nativeOpenWithAttributes(String name, int oflag, int mode, JPMQAttributes attr);
    private native int nativeClose(byte[] descriptor);
    private native int nativeUnlink(String name);
    private native JPMQAttributes nativeGetAttributes(byte[] descriptor);
    private native int nativeSetAttributes(byte[] descriptor, JPMQAttributes attr);
    private native String nativeReceive(byte[] descriptor);
    private native int nativeSend(byte[] descriptor, String str, int length, int priority);
    private native String nativeTimedReceive(byte[] descriptor, JPMQTimespec timespec);
    private native int nativeTimedSend(byte[] descriptor, String str, int length, int priority, JPMQTimespec timespec);
}
