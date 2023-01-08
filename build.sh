#!/bin/sh

[ -f build/HaxLoader.zip ] && rm build/HaxLoader.zip

java -jar RetroMCP-Java-all.jar build
7z d build/minecraft.zip yz.class

(
cd loader
7z a ../build/minecraft.zip mod_HaxLoader.class
)

mv build/minecraft.zip build/HaxLoader.zip
