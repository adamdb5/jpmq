# JPMQ

[![CMake](https://github.com/adamdb5/jpmq/actions/workflows/cmake.yml/badge.svg)](https://github.com/adamdb5/jpmq/actions/workflows/cmake.yml)
![GitHub](https://img.shields.io/github/license/adamdb5/jpmq)

## About JPMQ
JPMQ is a Java library providing an API for native POSIX message queues.

This library uses the Java Native Interface (JNI) and all native code is written in POSIX.1-2001 compliant ANSI C.

## Downloading Prebuilt Binaries
Prebuild binaries and shared objects are provided for common Linux and FreeBSD architectures, however instructions are also provided below for compiling.

These are available under releases.

## Compiling
Prerequisites:
- Any JDK and JRE >= 1.8
- Any C compiler
- CMake >= 3.0.0
- Make

Both the JAR and shared object can be created from the CMakeLists.txt file included within the project. The following commands will build both the JAR and shared object.

```
cd jpmq
mkdir build
cd build
cmake ..
make
```

This will generate `jpmq-1.0.jar` and `libjmpq.so`.

## Usage

### Using the library
Documentation is available [here](https://jpmq.adambruce.net/)

Online javadocs are available [here](https://jpmq.adambruce.net/javadoc).

JPMQ is stored within the package `net.adambruce.jpmq` and can be imported using `import net.adambruce.jpmq.*;`

Below is an example which does the following:

1. Creates a new message queue (equivalent to `mq_open`)
2. Sends a new message to the queue with priority 0 (equivalent to `mq_send`)
3. Receives a message from the queue (equivalent to `mq_receive`)
4. Unlinks (deletes) the queue (equivalent to `mq_unlink`)

Example.java:

```
import net.adambruce.jpmq.*;

public class Example {
        public static void main(String[] args) {
                try {
                        JPMQAttributes attrs = new JPMQAttributes(0, 2, 30, 0);
                        JPMQ myQueue = new JPMQ("/myQueue", JPMQ.O_CREAT | JPMQ.O_RDWR, 0644, attrs);
                        myQueue.send("Hello POSIX Message Queues!", 0);
                        System.out.println("Received: " + myQueue.receive());                         
                        myQueue.unlink();
                } catch (Exception e) {
                        e.printStackTrace(System.out);
                }
        }
}

```

### Compiling a Java program
You can compile your Java program just like you would with any other JAR by specifiying the classpath argument. If we created the previous Example.java program in the new `build` directory we will have the following directory contents:

```
jpmq/
|- build/
   |- Example.java
   |- jpmq-1.0.jar
   |- libjpmq.so
```

We can now compile our example program using:

`javac Example.java -classpath ".:jpmq-1.0.jar"`

If you wish to compile from a different directory to the JAR, you will need to change `.:jmpq-1.0.jar` to `.:/path/to/jpmq-1.0.jar`.

### Executing a Java program

When executing a Java program you will need to specify the classpath and the library path to the `libjpmq.so` library. So if we consider our new directory contents:

```
jpmq/
|- build/
   |- Example.java
   |- Example.class
   |- jpmq-1.0.jar
   |- libjpmq.so
```

We can now execute our example, with the native library using:

`java -Djava.library.path=. -classpath ".:jpmq-1.0.jar" Example`

Similarly to compiling, we can specify a different library path and classpath if we are executing from a different directory. For example, you will need to change `.` to `/directory/of/libjpmq.so` and change `.:jpmq.jar` to `.:/path/to/jpmq.jar`.

If the application executes correctly, the following output will be displayed:


`Received: Hello POSIX Message Queues!`

## License
JPMQ is licensed under the MIT license.
