hangman
====

This project implements a Hangman game.

Name: Evan Kenyon

### Timeline

Start Date: 9/2/2021

Finish Date: 9/7/2021

Hours Spent: 12


### Tutorial and other Resources Used
All resources used were attributed in inline comments except for the lab_hangman
course gitlab repo and JavaFX documentation. JavaFX documentation was used as a reference
for generating lines for the gallows and the hung person. The lab_hangman course gitlab repo
provided most of the code for the Guesser, InteractiveGuesser, SimpleSecretKeeper, and
InteractiveSecretKeeper objects. Specifically, all the borrowed code was split between
two different hangman game classes, so I refactored it (as instructed) into the separate
classes.


### Running the Program

Main class: runner/Main.java

Data files needed: None

Key/Mouse inputs: If using interactive guesser, use keyboard to guess letter. If using 
a different kind of guesser, press any letter on the keyboard to start the game.

Known Bugs: None


### Notes/Assumptions
If playing as an interactive secret keeper and using a clever guesser, pick a word in the
dictionary used to instantiate the clever guesser object.

### Impressions
I thought that this assignment was particularly helpful in terms of teaching refactoring,
and also in my case in terms of trying to implement inheritance. Overall, a good assignment,
except I thought that implmenting the algorithms for the clever players was rather 
time-consuming given that that was not the main focus of this assignment.
