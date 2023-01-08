#!/bin/sh

[ -f build/minecraft.jar ] && rm build/minecraft.jar

java -jar RetroMCP-Java-all.jar build -fullbuild