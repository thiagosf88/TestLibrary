#!/bin/bash
libraryPath=/home/thiago/Dropbox/mestrado/Dissertação/Desenvolvimento/ubu_casa/Library
jniPath="$libraryPath/jni/"
srcPath="$libraryPath/src/"
testPath=$srcPath"edu/performance/test"
cd $testPath
for folder in `ls -d -- */`; 
do
	
     for javaFileName in `find /home/thiago/Dropbox/mestrado/Dissertação/Desenvolvimento/ubu_casa/Library/src/edu/performance/test/$folder -iname "*java" ! -wholename "*/util/*" ! -wholename "*/dominio/*"`

	do
	cd $srcPath
	className=${javaFileName/#$srcPath/""}
	className=${className//"/"/"."}
	className=${className/".java"/""}
	file=${javaFileName/#$testPath/""}
	hFile=${file/"/"/"_"}
	
	#echo -e "$srcPath'\n'javah -jni $className'\n''\n'" ##$className # $jniPath${folder/"/"/".h"}
	 # -d $jniPath # $jniPath${hFile/".java"/".h"}
	javah -d $jniPath -jni $className 
	done
done

##for i in `find /home/thiago/Dropbox/mestrado/Dissertação/Desenvolvimento/ubu_casa/Library/src/edu/performance/test -name "*java"`
##do
#/home/thiago/Dropbox/mestrado/Dissertação/Desenvolvimento/ubu_casa/Library/src/edu/performance/test/readandwrite/ReadAndWrite.java
##javah -jni /home/thiago/Dropbox/mestrado/Dissertação/Desenvolvimento/ubu_casa/Library/src/edu/performance/test/ -d edu_performance_test_library_Library.h /home/thiago/Dropbox/mestrado/Dissertação/Desenvolvimento/ubu_casa/Library/jni/

