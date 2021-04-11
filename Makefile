JAVA_HOME=/usr/lib/jvm/java-8-openjdk
OS_NAME=linux

CC=cc
CFLAGS=-fPIC -shared -lrt
INCLUDE=-I"$(JAVA_HOME)/include" -I$(JAVA_HOME)/include/$(OS_NAME)
SOURCES=src/c/jpmq.c

JC=javac
JSOURCES=src/java/net/adambruce/jpmq/*.java

all:	jar clib rmtmp

directories:
	mkdir -p tmp

java:	directories 
	$(JC) -h tmp/ $(JSOURCES)
	$(JC) -d tmp/ $(JSOURCES)

clib:	java
	$(CC) $(CFLAGS) $(INCLUDE) -o libjpmq.so $(SOURCES)

jar:	java
	jar cf jpmq.jar -C tmp/ net/

rmtmp:	clib
	rm -r tmp/
	rm src/java/net/adambruce/jpmq/*.class

clean:
	rm libjpmq.so
	rm jpmq.jar
