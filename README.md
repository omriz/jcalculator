# JCalculator

## Preface
This is a little exercise I was given a while back to evaluate my programming.
It's been a while since I practiced something like this so I decided it'll be
nice to try it out and write a complete project in java and gradle.

## Usage:
You can either pass a file with commands in it. Assumption is that every line
should have an "=" sign in it and evalute the expression

Another way is not to pass a file and just run it in an "interperter" mode
that processes stdin. Once you're done just press `Ctrl+D` and it'll handle it.

## Improvments:
Separate the variable store and evaluator to a different class and the calculator itself
to a different class.
i.e. make it more Object Oriented :-)