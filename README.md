# SoftwareGurusBar
A terminal-based Java program to sort and manage the bar's customer arrivals, orders and departure utilizing Priority Queue and Heap data structures and writing modularized code with Java's public and private classes, interfaces and concepts like inheritance and polymorphism.

Files:
1. Software Gurus Bar
  The main driver class that handles all of the different types of events that goes on in the bar
2. Simulation Framework
  Keeps track of the bar time in the program and runs the simulation for this duration by queueing the events
3. MinPQ
  API for the mininum priority queue employed to sort the occurring events in the order of precedence (for the bar to serve)
4. Event
  Abstract Event clas,s which is inherited by different event classes in SoftwareGurusBar, that keeps track of the time each event occurs and implements the Comparable class to compare the events according to their time
