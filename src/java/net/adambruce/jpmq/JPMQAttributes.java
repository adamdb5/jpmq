package net.adambruce.jpmq;

/**
 * JPMQAttributes represents the POSIX mq_attr struct.
 *
 * @since 1.0
 * @author Adam Bruce
 */
public class JPMQAttributes {

    /** Holds the O_FLAGS provided in the JPMQ class */
    private int flags;
    /** The maximum number of messages in the queue */
    private int maxMessages;
    /** The size of messages in the queue */
    private int messageSize;
    /** The current number of messages in the queue */
    private int currentMessages;
    
    /**
     * Creates a new JPMQAttributes object with the given arguments.
     * 
     * @param flags the message queue flags
     * @param maxMessages the maximum number of messages in the queue
     * @param messageSize the size of each message in the queue
     * @param currentMessages the current number of messages in the queue
     */
    public JPMQAttributes(int flags, int maxMessages, int messageSize, 
    					  int currentMessages) {
		this.flags = flags;
		this.maxMessages = maxMessages;
		this.messageSize = messageSize;
		this.currentMessages = currentMessages;
    }

    /**
     * Sets the flags.
     * 
     * @param flags the flags
     */
    public void setFlags(int flags) {
    	this.flags = flags;
    }

    /**
     * Sets the maximum number of messages.
     * 
     * @param maxMessages the maximum number of messages 
     */
    public void setmaxMessages(int maxMessages) {
    	this.maxMessages = maxMessages;
    }

    /**
     * Sets the message size.
     * 
     * @param messageSize the size of messages
     */
    public void setmessageSize(int messageSize) {
    	this.messageSize = messageSize;
    }

    /**
     * Returns the flags.
     * 
     * @return the flags
     */
    public int getFlags() {
    	return flags;
    }

    /**
     * Returns the maximum number of messages.
     * 
     * @return the maximum number of messages
     */
    public int getmaxMessages() {
    	return maxMessages;
    }

    /**
     * Returns the message size.
     * 
     * @return the message size
     */
    public int getmessageSize() {
    	return messageSize;
    }

    /**
     * Returns the current number of messages
     * 
     * @return the current number of messages
     */
    public int getcurrentMessages() {
    	return currentMessages;
    }
}
