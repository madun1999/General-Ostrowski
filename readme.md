# General Ostrowski Addition Automaton
This program produces Algorithms 0,1,2, and 3 of addition automaton, and recognition automaton for any Ostrowski numeration system, which can then be used to produce the full addition automaton in Walnut.

## Getting Started
These instructions will help you use the program.

### Pre-requisite
This program is to be used with Walnut, which can be found here: https://github.com/hamousavi/Walnut.  
You will also need Java 8 to run this program. The most recent version of Java can be found here: https://www.java.com/en/download/.  

### Usage
Firstly, download the project file and unzip. 

Secondly, put the continued fraction of the desired quadratic number in the "input.txt" file, or other text file,  with the following format: 
  ``` 
   Line 1: the name of the numeration system 
   Line 2: the nonRepeated part of the continued fraction, separated by space
   Line 3: the repeated part of the continued fraction, separated by space, must not be empty
   Other lines are ignored.
  ``` 
  For example, 
  ```
  rt3

  1 2
  ```

Thirdly, run the program using the following command:

```java -jar Automaton_Producer.jar <input_file_name>```

Then, 6 files representing the the five automata and a Walnut command file can be found in the "output/\<name\>/" folder.

Note: The output files are in UTF-16 encoding, which is required by Walnut. Depending on the text editor, the file might look a little bit weird, but it is normal.

Finally, a Walnut command can be found in "\<name\>AdditionCommand.txt". Put all the automaton files into the "/Automata Library" folder of Walnut and run the command for the addition automaton.
### Note
In `a`-Ostrowski numeration system, if the continued fraction of `a` starts with a `1`, then the least significant digit of any number will be `0`. Since Walnut requires integer 1 to be exactly `10*` in lsd or `0*1` in msd, the output of this program will ommit the `0` in the least significant digit. 

## Issues

### Verifying With Constants
This program is not fully tested. To test if the output automata is correct, we recommend checking a few constants before using it.

Use the following command to generate Walnut commands for verifying the automata generated.

```java -jar Automaton_Producer.jar -verifyConstants <input_file_name>```

The verification command will appear in "\<name\>AdditionCommand.txt" below the command for building addition automaton.

### Issue reporting
If the resulting addition automaton is not correct, please create an issue. Please include the continued fraction of the quadratic number, the description of the issue, and related files. 
