#!/bin/sh
mv src srcP
java -jar RetroMCP-Java-all.jar setup b1.7.3
java -jar RetroMCP-Java-all.jar decompile
rm -rf src
mv srcP src
java -jar RetroMCP-Java-all.jar updatemd5