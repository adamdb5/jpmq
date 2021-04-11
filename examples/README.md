# Running the example

The example can be run simply be running the test.sh script as:

```
sh test.sh
```

## What the example does:
The example carries out the following steps:
1. Open a new message queue
2. Send a message of "hello" to the queue
3. Retrieve the same message back from the queue
4. Unlink (destroy) the queue

## Expected output
If the c and java are successfully compiled, you will see the following output:
```
Compiling jar and shared object from parent directory
mkdir -p tmp
javac -h tmp/ src/java/net/adambruce/jpmq/*.java
javac -d tmp/ src/java/net/adambruce/jpmq/*.java
jar cf jpmq.jar -C tmp/ net/
cc -fPIC -shared -lrt -I"/usr/lib/jvm/java-8-openjdk/include" -I/usr/lib/jvm/java-8-openjdk/include/linux -o libjpmq.so src/c/jpmq.c
rm -r tmp/
rm src/java/net/adambruce/jpmq/*.class
```

Then, the program should automatically execute using you're default java runtime:
```
=====================
Executing the program
=====================
Sent: Hello POSIX Message Queues!
Received: Hello POSIX Message Queues!
```

If you see this output then everything is working correctly.
