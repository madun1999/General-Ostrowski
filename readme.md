This program produces Algorithms 0,1,2, and 3 of addition automaton, and recognition automaton for any Ostrowski numeration system.
(ostrowski.Alg0Automaton(x,y,z) checks if x+y=z digit by digit)

First, put the input in a text file. This file must be in the same directory as the Automaton_Producer.jar file.

The first three lines will be used as input.
The first line is the name of the numeration system.
The second line is the nonRepeated part of the continued fraction, separated by space.
The third line is the repeated part of the continued fraction, separated by space, must not be empty.

A default input text file is provided, named "input.txt".

Then, run command:
java -jar Automaton_Producer.jar <name of input text file, default : input.txt>

After running the program, 6 files representing the the five automata and a Walnut command file can be found in the "output/[name]/" folder.
The output files are in UTF-16 encoding, which is required by Walnut. Depending on the text editor, the file might look a little bit weird, but it is normal.

To produce the addition automaton, put the automaton files into the "/Automata Library" folder of Walnut and run the command in "[name]AdditionCommand" in Walnut.
The result addition automaton, named "lsd_[name]_addition", can be found in "/Result" folder of Walnut.
