#!/bin/bash
libraryPath=/home/thiago/Dropbox/mestrado/Dissertação/Desenvolvimento/library/Library
jniPath="$libraryPath/jni/"
srcPath="$libraryPath/src/"
testPath=$srcPath"edu/performance/test/nativo"
number=0
# Entra na pasta test do projeto
cd $testPath
# Busca cada uma das pastas de teste da biblioteca
echo "[creates_headers.sh] Creating header files in jni folder..."
for folder in `ls -d -- */`; 
do


# Busca todos os arquivos .java de cada um dos testes	
     for javaFileName in `find "$testPath/$folder" -iname "*java" -wholename "*/nativo/*" `

	do
	# Entra na pasta de códigos do projeto, pois é necessário.
		cd $srcPath
	#Geração do segundo parametro para o comando jni
	# Retira todo caminho daquela classe até a pasta src	
		className=${javaFileName/#$srcPath/""}
	# Troca todas as barras do caminho por um .
		className=${className//"/"/"."}
	# Retira a extensão do arquivo restando apenas o nome da classe e toda estrutura de pacotes desde edu.
		className=${className/".java"/""}
		cmd="javah -d $jniPath -jni $className"	
	#echo $cmd
		eval $cmd
		echo ${className//"."/"_"}".h created!"
		number=$(($number+1))  
	done     	
done
echo "[creates_headers.sh] $number file(s) created!"


