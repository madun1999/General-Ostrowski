This program produces Algorithms 0,1,2, and 3 for Ostrowski Addition with a=sqrt(2).
(ostrowski.Alg0Automaton(x,y,z) checks if x+y=z digit by digit)

To use the program, first run command:
make
Then, goto folder "/bin" and run command:
java ostrowski.OstrowskiAddition

After running the program, 4 files representing the the four automata for the four algorithms can be found in the "/output".
The files is in UTF-16 encoding, which is required by Walnut. Depending on the text editor, the file might look a little bit weird, but it is normal.

To produce the automaton, put the files into the "/Automata Library" folder of Walnut and run:
eval lsd_rt2_addition "Ey (Ex (Ew $rrt2(a) & $rrt2(b) & $alg0(a,b,w) & `$alg1(w,x)) & $alg2(x,y)) & `$alg3(y,c)":

The final automaton, named "lsd_rt2_addition", can be found in "/Result" folder of Walnut.
