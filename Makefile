JAVA_HOME=/usr/lib/jvm/java-15-openjdk

CC=clang
CFLAGS=-fPIC -shared -lrt
INCLUDE=-I"$(JAVA_HOME)/include" -I$(JAVA_HOME)/include/linux
SOURCES=src/c/jpmq.c

JC=javac
JNATIVEFLAGS=-h
JSOURCES=src/java/net/adambruce/jpmq/*.java

all:	jar clib cleantmp

directories:
	mkdir -p tmp

java:	directories
	$(JC) $(JNATIVEFLAGS) tmp/ $(JSOURCES)
	$(JC)  -d tmp/ $(JSOURCES)

clib:	java
	$(CC) $(CFLAGS) $(INCLUDE) -o libjpmq.so $(SOURCES)

jar:	java
	jar cf jpmq.jar -C tmp/ net/

cleantmp:	clib
	rm -r tmp/
	rm src/java/net/adambruce/jpmq/*.class

clean:
	rm libjpmq.so
	rm jpmq.jar
