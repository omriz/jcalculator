# JCalculator

## Preface
String line calculator written in Java and gradle.
Tested with OpenJDK11

## Usage
### Requirments
1) Gradle
2) Java 11
### Building
You can run interactively with `./gradlew run`.

A better way is to build the jar using `./gradlew build` and then run it with `java -jar build/libs/jcalculator-1.0-SNAPSHOT.jar` -
That way you won't get annoying gradle prompts along the way.

You can either pass a file with commands in it. Assumption is that every line
should have an "=" sign in it and evalute the expression

Another way is not to pass a file and just run it in an "interperter" mode
that processes stdin. Once you're done just press `Ctrl+D` and it'll handle it.