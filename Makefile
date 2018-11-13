main: ostrowski.Alg3.java ostrowski.Alg2.java OstrowskiAutomaton.java
	javac -classpath . ./OstrowskiAutomaton.java -d ./bin
test: test.java
	javac -classpath . ./test.java -d ./bin
