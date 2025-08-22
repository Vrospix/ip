@ECHO OFF

REM create bin directory if it doesn't exist
if not exist C:\Users\vigok\Documents\NUS Importance\CS Y2S1\CS2103T\Chatbot\bin mkdir C:\Users\vigok\Documents\NUS Importance\CS Y2S1\CS2103T\Chatbot\bin

REM delete output from previous run
del ACTUAL.TXT

REM compile the code into the bin folder
javac  -cp C:\Users\vigok\Documents\NUS Importance\CS Y2S1\CS2103T\Chatbot\src\main\java -Xlint:none -d C:\Users\vigok\Documents\NUS Importance\CS Y2S1\CS2103T\Chatbot\bin C:\Users\vigok\Documents\NUS Importance\CS Y2S1\CS2103T\Chatbot\src\main\java\*.java
IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)
REM no error here, errorlevel == 0

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath C:\Users\vigok\Documents\NUS Importance\CS Y2S1\CS2103T\Chatbot\bin Falco < input.txt > ACTUAL.TXT

REM compare the output to the expected output
FC ACTUAL.TXT EXPECTED.TXT
