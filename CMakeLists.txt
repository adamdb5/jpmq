cmake_minimum_required(VERSION 3.0)

set(JPMQ_VERSION 1.0)

project(jpmq VERSION ${JPMQ_VERSION})
set(CMAKE_BUILD_TYPE Release)

find_package(JNI REQUIRED)
find_package(Java REQUIRED)
include(UseJava)

file(GLOB java_sources src/java/net/adambruce/jpmq/*.java)
add_jar(jpmq_jar
        ${java_sources}
        GENERATE_NATIVE_HEADERS jpmq_native
        OUTPUT_NAME jpmq-${JPMQ_VERSION}
        )

add_library(jpmq
        SHARED
        src/c/jpmq.c
        )

target_link_libraries(jpmq rt jpmq_native)
