@echo off
echo [ Compiling library classes... ]
dir /s /B *.java > sources.txt
javac -cp C:\Users\ksavchenko\Documents\Projects\Java\SimpleEncodings\production\* @sources.txt -encoding utf8 -d ./build
echo [ Library classes compiled successfully! ]
echo [ Compiling .jar... ]
jar cvf ./production/SimpleDictionaryService.jar -C ./build/ .
echo [ .jar compiled successfully! ]