import net.adambruce.jpmq.JPMQ;
import net.adambruce.jpmq.JPMQAttributes;

public class Example {
	public static void main(String[] args) {
	
    	/* Specify the attributes (equivalent to struct mq_attr) */
    	JPMQAttributes attrs = new JPMQAttributes(0, 2, 30, 0);
	
    	/* Create the queue (equivalent to calling mq_open) */
		JPMQ myQueue = new JPMQ("/myQueue", JPMQ.O_CREAT | JPMQ.O_RDWR, 
							   0644, attrs);
	
		/* Insert message into the queue with priority 0
		 * (equivalent to calling mq_send)
		 */
		String myMessage = "Hello POSIX Message Queues!";
		myQueue.send(myMessage, 0);
		System.out.println("Sent: " + myMessage);
	
		/* Poll for our message (equivalent to calling mq_receive) */
		String receivedMessage = myQueue.receive();
		System.out.println("Received: " + receivedMessage);
					      
		/* Unlink (destroy) the message queue (equivalent to mq_unlink) */
		myQueue.unlink();
	}
}
