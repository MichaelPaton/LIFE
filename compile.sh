#!/bin/sh
CP=conf/:classes/:lib/*
SP=src/java/

/bin/rm -f life.jar
/bin/rm -rf classes
/bin/mkdir -p classes/

javac -sourcepath $SP -classpath $CP -d classes/ src/java/life/*.java src/java/life/*/*.java || exit 1

echo "life class files compiled successfully"
