cmake_minimum_required(VERSION 3.0)

project(jpmq VERSION 1.0)
set(CMAKE_BUILD_TYPE Release)

find_package(JNI REQUIRED)
find_package(Java REQUIRED)
include(UseJava)

FILE(GLOB java_sources src/java/net/adambruce/jpmq/*.java)
add_jar(jpmq-jar
    ${java_sources}
    GENERATE_NATIVE_HEADERS jpmq-native
    OUTPUT_NAME jpmq
    )

add_library(jpmq
    SHARED
    src/c/jpmq.c
    )

target_link_libraries(jpmq rt jpmq-native)

