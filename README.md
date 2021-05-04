# <p align="center">JPMQ</p>
<p align="center">
[![CMake](https://github.com/adamdb5/jpmq/actions/workflows/cmake.yml/badge.svg)](https://github.com/adamdb5/jpmq/actions/workflows/cmake.yml)
![GitHub](https://img.shields.io/github/license/adamdb5/jpmq)
<p/>

## About JPMQ
JPMQ is a Java library providing an API for native POSIX message queues.

This library uses the Java Native Interface (JNI) and all native code is written in ANSI C.

## Getting Started
Due to the vast number of OS / architecture combinations, pre-built JARs and shared object libraries are not distributed. This section will explain how to compile the libraries using CMake.

### Prerequisites
- Any Java JDK (e.g. Oracle, OpenJDK)
- Any C compiler (e.g. GCC, Clang)
- CMake >= 3.0.0
- Make

### Compiling
Both the JAR and shared object can be created from the CMakeLists.txt file included within the project. The following commands assume you are already in the jpmq directory.

```
mkdir build
cd build
cmake ..
make
```

This will generate `jpmq.jar` and `libjmpq.so`.

## Usage

### Using the library
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
		JPMQAttributes attrs = new JPMQAttributes(0, 2, 30, 0);
		JPMQ myQueue = new JPMQ("/myQueue", JPMQ.O_CREAT | JPMQ.O_RDWR, 0644, attrs);
		myQueue.send("Hello POSIX Message Queues!", 0);
		System.out.println("Received: " + myQueue.receive());			      
		myQueue.unlink();
	}
}
```

### Compiling the Java program
You can compile your Java program just like you would with any other JAR by specifiying the classpath argument. If we created the previous Example.java program in the new `build` directory we will have the following directory contents:

```
jpmq/
|- build
   |- Example.java
   |- jpmq.jar
   |- libjmpq.so
```

We can now compile our example program using:

`javac Test.java -classpath ".:jpmq.jar"`

### Executing the Java program

When executing the program you will need to specify the classpath and the library path to the `libjpmq.so` library. So if we consider our new directory contents:

```
jpmq/
|- build
   |- Example.java
   |- Example.class
   |- jpmq.jar
   |- libjmpq.so
```

We can now execute our example, with the native library using:

`java -Djava.library.path=. -classpath ".:jpmq.jar" Example`

If the application executes correctly, the following output will be displayed:


`Received: Hello POSIX Message Queues!`


## Roadmap
The following list identifies future feeatures of the JPMQ library:

- Add support for `mq_notify`
- Add ``mode_t`flags
- Add exceptions instead of relying on return status


## License
JPMQ is licensed under the MIT license, so you can do whatever you want with it.