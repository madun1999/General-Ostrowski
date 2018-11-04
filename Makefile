main: Alg3.java Alg2.java OstrowskiAutomaton.java
	javac -classpath . ./OstrowskiAutomaton.java -d ./bin
test: test.java
	javac -classpath . ./test.java -d ./bin
