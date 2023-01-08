#!/bin/sh
mv src srcP
java -jar RetroMCP-Java-all.jar setup
java -jar RetroMCP-Java-all.jar decompile
rm src
mv srcP src
