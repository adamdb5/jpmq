[![CMake](https://github.com/adamdb5/jpmq/actions/workflows/cmake.yml/badge.svg)](https://github.com/adamdb5/jpmq/actions/workflows/cmake.yml)

# JPMQ - A Java API for POSIX Message Queues

## Usage
```
	JPMQAttributes attrs = new JPMQAttributes(0, 2, 30, 0);
	
	JPMQ myQueue = new JPMQ("/myQueue", JPMQ.O_CREAT | JPMQ.O_RDWR, 0644, attrs);
	
	myQueue.send("Hello POSIX Message Queues!", 0);
	
	String receivedMessage = myQueue.receive();
	
	System.out.println("Received: " + receivedMessage);
					      
	myQueue.unlink();

```
