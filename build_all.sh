VERSION="1.0"
OS=$(uname -o)

mkdir build
cd build

echo "Building for $OS"
if [ $OS == "GNU/Linux" ]; then
	OS="linux"
fi

ARCH="x86_64"
NAME="jpmq-$VERSION-$ARCH-$OS"
mkdir $NAME
cd $NAME
cmake ../.. -D CMAKE_C_FLAGS=-m64
make

cd ..
ARCH="i386"
NAME="jpmq-$VERSION-$ARCH-$OS"
mkdir $NAME
cd $NAME
cmake ../.. -D CMAKE_C_FLAGS=-m32
make

cd ..
ARCH="aarch64"
NAME="jpmq-$VERSION-$ARCH-$OS"
mkdir $NAME
cd $NAME
cmake ../.. -D CMAKE_C_COMPILER=aarch64-linux-gnu-gcc
make
