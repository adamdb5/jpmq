package net.adambruce.jpmq;

/**
 * JPMQ represents a descriptor to a POSIX message queue.
 *
 * A JPMQ object holds a message queue descriptor and supports almost the
 * entire POSIX message queue API.
 *
 * @since 1.0
 * @author Adam Bruce
 */
public class JPMQ {

    /* Load the shared object */
	static {
		System.loadLibrary("jpmq");
	}
	
    /** Opens the queue to receive messages only */
    public static final int O_RDONLY    = 0b0000001;
    /** Opens the queue to send messages only */
    public static final int O_WRONLY    = 0b0000010;
    /** Opens the queue to both send and receive messages */
    public static final int O_RDWR      = 0b0000100;
    /** Set the close-on-exec flag for the queue descriptor */
    public static final int O_CLOEXEC   = 0b0001000;
    /** Creates a new message queue */
    public static final int O_CREAT     = 0b0010000;
    /**
     * If O_CREAT was specified and a queue with the given name already
     * exists, the call will fail with error EEXIST. This will throw
     * the QueueExistsException.
     */
    public static final int O_EXCL      = 0b0100000;
    /** Opens the queue in non-blocking mode */
    public static final int O_NONBLOCK  = 0b1000000;

    /* Message queue name and descriptor */
    private byte[] descriptor;
    private String name;
    
    /**
     * Opens an existing message queue.
     * 
     * @param name the name of the message queue
     * @param oflags the open flags (O_)
     *
     * @throws AccessException if the process does not have permission to access the queue, or an invalid queue name
     * has been provided.
     * @throws InvalidValueException if the queue name is invalid.
     * @throws DescriptorLimitException if the maximum number of process or global file / queue descriptors has been
     * reached.
     * @throws NameTooLongException if the queue name is too long.
     * @throws QueueDoesNotExistException if no queue exists with the given name.
     * @throws InsufficientMemoryException if there is insufficient memory to open the queue.
     * @since 1.0
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
     * @param oflags the open flags (O_)
     * @param mode the file mode
     * @param attributes the message queue attributes
     *
     * @throws AccessException if the process does not have permission to access the queue, or an invalid queue name
     * has been provided.
     * @throws InvalidValueException if the queue name is invalid.
     * @throws DescriptorLimitException if the maximum number of process or global file / queue descriptors has been
     * reached.
     * @throws NameTooLongException if the queue name is too long.
     * @throws QueueExistsException if a queue with the given name already exists.
     * @throws InsufficientMemoryException if there is insufficient memory to open the queue.
     * @throws InsufficientSpaceException if there is insufficient space to create the queue.
     * @since 1.0
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
     * @throws BadDescriptorException if the file descriptor is invalid (may be caused by another process unlinking).
     * @since 1.0
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
     * @throws AccessException if the process does not have permission to access the queue.
     * @throws NameTooLongException if the queue name is too long (Not possible under normal circumstances).
     * @throws QueueDoesNotExistException if the queue does not exist (may be caused by another process unlinking).
     * @since 1.0
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
     * @since 1.0
     */
    public byte[] getDescriptor() {
    	return descriptor;
    }

    /**
     * Returns the name of the message queue.
     * 
     * @return the name of the message queue
     * @since 1.0
     */
    public String getName() {
    	return name;
    }

    /**
     * Returns the attributes of the message queue.
     * 
     * @return the attributes of the message queue
     *
     * @throws BadDescriptorException if the file descriptor is invalid (may be caused by another process unlinking).
     * @since 1.0
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
     * @param attributes the new attributes
     *
     * @throws BadDescriptorException if the file descriptor is invalid (may be caused by another process unlinking).
     * @throws InvalidValueException if the provided attributes contain values other than O_NONBLOCK.
     * @since 1.0
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
     *
     * @throws QueueEmptyException if the queue is empty.
     * @throws BadDescriptorException if the file descriptor is invalid (may be caused by another process unlinking).
     * @throws InterruptException if the call is interrupted.
     * @throws MessageLengthException if the buffer size is less than the queue message size.
     * @since 1.0
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
     *
     * @throws QueueFullException if the queue is full.
     * @throws BadDescriptorException if the file descriptor is invalid (may be caused by another process unlinking).
     * @throws InterruptException if the call is interrupted.
     * @throws MessageLengthException if the message length is greater than the queue message length.
     * @since 1.0
     */
    public void send(String message, int priority) throws
        QueueFullException, BadDescriptorException, InterruptException,
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
     * @return the received message
     *
     * @throws QueueEmptyException if the queue is empty.
     * @throws BadDescriptorException if the file descriptor is invalid (may be caused by another process unlinking).
     * @throws InterruptException if the call is interrupted.
     * @throws MessageLengthException if the buffer size is less than the queue message size.
     * @throws InvalidValueException if the given timeout is invalid.
     * @throws TimeoutException if the call timed out before a message was received.
     * @since 1.0
     */
    public String timedReceive(JPMQTimespec timespec) throws
        QueueEmptyException, BadDescriptorException, InterruptException, InvalidValueException,
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
     * @param message the message to send
     * @param priority the priority of the message
     * @param timespec the timeout for sending the message
     *
     * @throws QueueFullException if the queue is full.
     * @throws BadDescriptorException if the file descriptor is invalid (may be caused by another process unlinking).
     * @throws InterruptException if the call is interrupted.
     * @throws MessageLengthException if the message length is greater than the queue message length.
     * @throws InvalidValueException if the given timeout is invalid.
     * @throws TimeoutException if the call timed out before a message was sent.
     * @since 1.0
     */
    public void timedSend(String message, int priority, JPMQTimespec timespec) throws
        QueueFullException, BadDescriptorException, InterruptException, InvalidValueException,
        MessageLengthException, TimeoutException {

        try {
    	    nativeTimedSend(descriptor, message, message.length(), priority, timespec);
        } catch (Exception e) {
            throw e;
        }
    }
     
    /***************************************************/
    /*                     Native                      */
    /***************************************************/

    /**
     * Native wrapper for mq_open(const char *name, int oflag);
     * Opens an existing message queue.
     *
     * @param name the name of the message queue
     * @param oflag the open flags (O_)
     * @return the message queue descriptor
     *
     * @throws AccessException if the process does not have permission to access the queue, or an invalid queue name
     * has been provided.
     * @throws InvalidValueException if the queue name is invalid.
     * @throws DescriptorLimitException if the maximum number of process or global file / queue descriptors has been
     * reached.
     * @throws NameTooLongException if the queue name is too long.
     * @throws QueueDoesNotExistException if no queue exists with the given name.
     * @throws InsufficientMemoryException if there is insufficient memory to open the queue.
     * @since 1.0
     */
    private native byte[] nativeOpen(String name, int oflag) throws
        AccessException, InvalidValueException, DescriptorLimitException,
        NameTooLongException, QueueDoesNotExistException, InsufficientMemoryException;

    /**
     * Native wrapper for mq_open(const char *name, int oflag, mode_t mode, struct mq_attr *attr);
     * Opens a message queue, or creates a new message queue with attributes.
     *
     * @param name the name of the message queue
     * @param oflag the open flags (O_)
     * @param mode the file mode
     * @param attr the message queue attributes
     * @return the message queue descriptor
     *
     * @throws AccessException if the process does not have permission to access the queue, or an invalid queue name
     * has been provided.
     * @throws InvalidValueException if the queue name is invalid.
     * @throws DescriptorLimitException if the maximum number of process or global file / queue descriptors has been
     * reached.
     * @throws NameTooLongException if the queue name is too long.
     * @throws QueueExistsException if a queue with the given name already exists.
     * @throws InsufficientMemoryException if there is insufficient memory to open the queue.
     * @throws InsufficientSpaceException if there is insufficient space to create the queue.
     * @since 1.0
     */
    private native byte[] nativeOpenWithAttributes(String name, int oflag, int mode, JPMQAttributes attr) throws
        AccessException, QueueExistsException, InvalidValueException, DescriptorLimitException,
        NameTooLongException, InsufficientMemoryException, InsufficientSpaceException;

    /**
     * Native wrapper for int mq_close(mqd_t mqdes);
     * Closes the connection to the message queue but does not unlink it.
     *
     * @param descriptor the queue descriptor
     *
     * @throws BadDescriptorException if the file descriptor is invalid (may be caused by another process unlinking).
     * @since 1.0
     */
    private native void nativeClose(byte[] descriptor) throws BadDescriptorException;

    /**
     * Native wrapper for mq_unlink(const char *name);
     * Unlinks (destroys) the message queue.
     *
     * @param name the queue name
     *
     * @throws AccessException if the process does not have permission to access the queue.
     * @throws NameTooLongException if the queue name is too long (Not possible under normal circumstances).
     * @throws QueueDoesNotExistException if the queue does not exist (may be caused by another process unlinking).
     * @since 1.0
     */
    private native void nativeUnlink(String name) throws AccessException, NameTooLongException,
        QueueDoesNotExistException;

    /**
     * Native wrapper for mq_getattr(mqd_t mqdes, struct mq_attr *attr);
     * Returns the attributes of the message queue.
     *
     * @param descriptor the message queue descriptor
     * @return the attributes of the message queue
     *
     * @throws BadDescriptorException if the file descriptor is invalid (may be caused by another process unlinking).
     * @since 1.0
     */
    private native JPMQAttributes nativeGetAttributes(byte[] descriptor) throws BadDescriptorException;

    /**
     * Native wrapper for mq_setattr(mqd_t mqdes, const struct mq_attr *restrict newattr, struct mq_attr *restrict oldattr);
     * Sets the attributes of the message queue.
     *
     * @param descriptor the message queue descriptor
     * @param attr the new attributes
     *
     * @throws BadDescriptorException if the file descriptor is invalid (may be caused by another process unlinking).
     * @throws InvalidValueException if the provided attributes contain values other than O_NONBLOCK.
     * @since 1.0
     */
    private native void nativeSetAttributes(byte[] descriptor, JPMQAttributes attr) throws
        BadDescriptorException, InvalidValueException;

    /**
     * Native wrapper for mq_receive(mqd_t mqdes, char *msg_ptr, size_t msg_len, unsigned int *msg_prio);
     * Receives a message from the message queue.
     *
     * @param descriptor the message queue descriptor
     * @return the message received
     *
     * @throws QueueEmptyException if the queue is empty.
     * @throws BadDescriptorException if the file descriptor is invalid (may be caused by another process unlinking).
     * @throws InterruptException if the call is interrupted.
     * @throws MessageLengthException if the buffer size is less than the queue message size.
     * @since 1.0
     */
    private native String nativeReceive(byte[] descriptor) throws
        QueueEmptyException, BadDescriptorException, InterruptException,
        MessageLengthException;

    /**
     * Native wrapper for mq_send(mqd_t mqdes, const char *msg_ptr, size_t msg_len, unsigned int msg_prio);
     * Sends a message to the message queue.
     *
     * @param descriptor the message queue descriptor
     * @param message the message to send
     * @param priority the priority of the message
     *
     * @throws QueueFullException if the queue is full.
     * @throws BadDescriptorException if the file descriptor is invalid (may be caused by another process unlinking).
     * @throws InterruptException if the call is interrupted.
     * @throws MessageLengthException if the message length is greater than the queue message length.
     * @since 1.0
     */
    private native void nativeSend(byte[] descriptor, String message, int length, int priority) throws
        QueueFullException, BadDescriptorException, InterruptException,
        MessageLengthException;

    /**
     * Native wrapper for mq_timedreceive(mqd_t mqdes, char *restrict msg_ptr, size_t msg_len, unsigned int *restrict msg_prio,
                                          const struct timespec *restrict abs_timeout);
     * Attempts to receive a message from the queue. The call will continue
     * trying to receive a message until the timeout is reached, at which point
     * the call will return.
     *
     * @param descriptor the message queue descriptor
     * @param timespec the timeout for receiving a message
     * @return the received message
     *
     * @throws QueueEmptyException if the queue is empty.
     * @throws BadDescriptorException if the file descriptor is invalid (may be caused by another process unlinking).
     * @throws InterruptException if the call is interrupted.
     * @throws MessageLengthException if the buffer size is less than the queue message size.
     * @throws InvalidValueException if the given timeout is invalid.
     * @throws TimeoutException if the call timed out before a message was received.
     * @since 1.0
     */
    private native String nativeTimedReceive(byte[] descriptor, JPMQTimespec timespec) throws
        QueueEmptyException, BadDescriptorException, InterruptException, InvalidValueException,
        MessageLengthException, TimeoutException;

    /**
     * Native wrapper for mq_timedsend(mqd_t mqdes, const char *msg_ptr, size_t msg_len, unsigned int msg_prio,
                                       const struct timespec *abs_timeout);

     * Attempts to send a message to the queue. If the queue is full, the call
     * will continue trying to send the message until the timeout is reached,
     * at which point the call will return.
     *
     * @param descriptor the message queue descriptor
     * @param message the message to send
     * @param priority the priority of the message
     * @param timespec the timeout for sending the message
     *
     * @throws QueueFullException if the queue is full.
     * @throws BadDescriptorException if the file descriptor is invalid (may be caused by another process unlinking).
     * @throws InterruptException if the call is interrupted.
     * @throws MessageLengthException if the message length is greater than the queue message length.
     * @throws InvalidValueException if the given timeout is invalid.
     * @throws TimeoutException if the call timed out before a message was sent.
     * @since 1.0
     */
    private native void nativeTimedSend(byte[] descriptor, String message, int length, int priority, JPMQTimespec timespec) throws
        QueueFullException, BadDescriptorException, InterruptException, InvalidValueException,
        MessageLengthException, TimeoutException;
}
