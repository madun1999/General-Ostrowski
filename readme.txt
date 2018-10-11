This program produces Algorithms 0,1,2, and 3 for Ostrowski Addition with a=sqrt(2). 
(Alg0(x,y,z) checks if x+y=z digit by digit)

To use the program, goto folder "/bin" in this folder and run command:
java OstrowskiAddition

After running the program, 4 files representing the the four automata for the four algorithms can be found in the "/output".
The files is in UTF-16 encoding, which is required by Walnut. Depending on the text reader, the file might look a little bit weird, but it is normal.

To produce the automaton, put the files into the "/Automata Library" folder of Walnut and run:
eval lsd_rt2_addition "(Ey (Ex (Ew $alg0(a,b,w) & `$alg1(w,x)) & $alg2(x,y)) & `$alg3(y,c)) & $rrt2(c)":

The final automaton, named "lsd_rt2_addition", can be found in "/Result" folder of Walnut.
