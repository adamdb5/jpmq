cmake_minimum_required(VERSION 3.0)

project(jpmq VERSION 1.0)
set(CMAKE_BUILD_TYPE Release)

find_package(JNI REQUIRED)
find_package(Java REQUIRED)
include(UseJava)

add_jar(jpmq-jar
    src/java/net/adambruce/jpmq/JPMQ.java
    src/java/net/adambruce/jpmq/JPMQAttributes.java
    src/java/net/adambruce/jpmq/JPMQTimespec.java
    GENERATE_NATIVE_HEADERS jpmq-native
    OUTPUT_NAME jpmq
    )

#include_directories(${JNI_INCLUDE_DIRS})
add_library(jpmq
    SHARED
    src/c/jpmq.c
    )
target_link_libraries(jpmq rt jpmq-native)

