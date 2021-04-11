echo "Compiling jar and shared object from parent directory"
cd ..
make
cd examples

echo "Copying jar and shared object"
cp ../*.jar .
cp ../*.so .

echo "Compiling example"
javac -classpath ".:jpmq.jar" Example.java

echo "Compilation successful"

echo "====================="
echo "Executing the program"
echo "====================="
java -Djava.library.path=. -classpath ".:jpmq.jar" Example
