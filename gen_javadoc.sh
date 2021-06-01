#!/bin/sh

# Javadoc generator
# Use -jar or --jar to create javadoc jar

JPMQ_VERSION=1.0
GENERATE_JAR=false

echo "**************************************************************"
if [ "$1" != "--jar" ] && [ "$1" != "-jar" ]; then
	echo "Generating javadoc, the output will be saved in javadoc/"
	echo "To automatically generate a javadoc jar use the --jar option."
else
	echo "Generating javadoc jar."
	echo "The output will be saved as jpmq-$JPMQ_VERSION-javadoc.jar"
		GENERATE_JAR=true
fi
echo "**************************************************************"

if javadoc --source-path src/java -d javadoc net.adambruce.jpmq && [ $GENERATE_JAR = true ]; then
  jar --create --file jpmq-$JPMQ_VERSION-javadoc.jar -C javadoc/ .
fi