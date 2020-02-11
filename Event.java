/***************************************************************************************
* Su Latt Phone
* CIS 256
* Programming Project 2
* Due 10/13/18
*
* Event.java
* An abstract class that keeps track of the time and implements Comparable
* enabling comparing events in the priority queue for the program
***************************************************************************************/

public abstract class Event implements Comparable
{
   public final int time;
   
   /*
   * Constructor initializes the time instance variable
   * Takes in an integer for the time
   * @param t an int for the time
   */
   public Event(int t)
   {
      time = t;
   }
   
   abstract void processEvent();
   
   /*
   * Method to compare an instantiated Event object with another object
   * Mehthod requires an Object and the result will be an int -1,0 or 1 for
   * smaller, equal or larger respectively
   * @param o    Object to be compared against
   * @return     an integer -1,0 or 1
   */
   public int compareTo(Object o)
   {
      Event right = (Event) o;
      if(time<right.time)
         return -1;
      else if(time==right.time)
         return 0;
      else
         return 1;
   }
}