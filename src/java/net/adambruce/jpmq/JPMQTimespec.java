package net.adambruce.jpmq;

/**
 * @file JPMQTimespec.java
 * @brief The JPMQTimespec class represents the POSIX timespec struct.
 * @author Adam Bruce
 * @date 08 Apr 2021
 */

public class JPMQTimespec {

    private int seconds;
    private int nanoSeconds;

    /**
     * Creates a new JPMQTimespec object with the given arguments.
     * 
     * @param seconds the number of seconds
     * @param nanoseconds the number of nanoseconds
     */
    public JPMQTimespec(int seconds, int nanoseconds) {
		this.seconds = seconds;
		this.nanoSeconds = nanoseconds;
    }
    
    /**
     * Returns the number of seconds in the timespec.
     * 
     * @return the number of seconds
     */
    public int getSeconds() {
    	return seconds;
    }
    
    /**
     * Returns the number of nanoseconds in the timespec.
     * 
     * @return the number of nanoseconds
     */
    public int getNanoSeconds() {
    	return nanoSeconds;
    }
    
    /**
     * Sets the number of seconds in the timespec.
     * 
     * @param seconds the number of seconds
     */
    public void setSeconds(int seconds) {
    	this.seconds = seconds;
    }
    
    /**
     * Sets the number of nanoseconds in the timespec.
     * 
     * @param nanoseconds the number of nanoseconds
     */
    public void setNanoSeconds(int nanoseconds) {
    	this.nanoSeconds = nanoseconds;
    }
}
