# Robot Programming Assignment Skeleton

An example to demonstrate the correct structure for a Robot Programming individual assignment. If you want to use this to help you get started, you can fork this into your account and develop from there. *Note*: do not clone this to your workspace!

## Eclipse 

To use this project in Eclipse create a new standard Java project from it (e.g. via File > Import > Git > Projects From Git > URI). To this project, add the following dependencies: 

 * the rp-shared project  (add via Java Build Path > Projects)
 * the rp-pc project (add via Java Build Path > Projects)
 * the leJOS PC library (add via Java Build Path > Libraries > Add Library...)
 * the JUnit 4 library (add via Java Build Path > Libraries > Add Library...)

## Command Line

To use this project in other build systems, e.g. compiling from the command line, you should download the jars from the dependencies:

 * [rp-shared](https://raw.githubusercontent.com/hawesie/rp-shared/master/export/rp-shared.jar)
 * [rp-pc](https://raw.githubusercontent.com/hawesie/rp-pc/rp16/export/rp-pc.jar)
 * JUnit 4: [junit.jar](http://bit.ly/My9IXz) and [hamcrest-core.jar](http://bit.ly/1gbl25b)

And make sure you have leJOS installed. You can then compile as follows:

```
javac -cp $NXJ_HOME/lib/pc/pccomm.jar:$NXJ_HOME/lib/pc/pctools.jar:rp-shared.jar:rp-pc.jar:junit-4.12.jar:hamcrest-core-1.3.jar  src/rp/assignments/individual/ex1/*.java
```

And run:

```
java -cp $NXJ_HOME/lib/pc/pccomm.jar:$NXJ_HOME/lib/pc/pctools.jar:rp-shared.jar:rp-pc.jar:junit-4.12.jar:hamcrest-core-1.3.jar:src rp.assignments.individual.ex1.IndividualAssignment1Simulation
```
