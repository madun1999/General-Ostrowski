This program produces Algorithms 0,1,2, and 3 of addition automaton, and recognition automaton for any Ostrowski numeration system.
(ostrowski.Alg0Automaton(x,y,z) checks if x+y=z digit by digit)

First, put the input in "input.txt". The first three lines will be used as input.
First line is the name of the numeration system.
Second line is the nonRepeated part of the continued fraction, separated by space.
Third line is the Repeated part of the continued fraction, separated by space, must not be empty.

(Currently doesn't support continued fraction starting with number 1).

Then, goto folder "/bin" and run command:
java ostrowski.OstrowskiAddition

After running the program, 6 files representing the the five automata and a Walnut command file can be found in the "output/[name]/" folder.
The output files are in UTF-16 encoding, which is required by Walnut. Depending on the text editor, the file might look a little bit weird, but it is normal.

To produce the addition automaton, put the automaton files into the "/Automata Library" folder of Walnut and run the command in "[name]AdditionCommand" in Walnut.
The result addition automaton, named "lsd_[name]_addition", can be found in "/Result" folder of Walnut.
