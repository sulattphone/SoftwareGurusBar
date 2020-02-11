/***************************************************************************************
* Su Latt Phone
* CIS 256
* Programming Project 2
* Due 10/13/18
*
* SimulationFramework.java
* Framework (skeleton) class for the project
***************************************************************************************/

public class SimulationFramework
{
   private int currentTime =0;      //the value of the current time in minutes
   private MinPQ eventQueue = new MinPQ();       //the minPQ priority queue   
   
   /*
   * Method that puts the events into the schedule
   * Takes in a new event and puts it into the priority queue
   * 
   * @param newEvent the new event to be added to the queue
   */
   public void scheduleEvent(Event newEvent)
   {
      eventQueue.insert(newEvent);        //adds a new event into the priority queue
   }
   
   /*
   * The method that would start processing the events all sorted in the priority queue
   */
   public void run()
   {
      while(!eventQueue.isEmpty())
      {
         //taking the smallest element out of the priority queue
         Event nextEvent = (Event) eventQueue.deleteMin();
         
         //the time of the event taken out of the queue becomes the current time
         currentTime = nextEvent.time;
         
         //process that event
         nextEvent.processEvent();
      }
   }
   
   /*
   * Method to get the time instance variable (accessor)
   *
   * @return  the value in the time instance variable
   */
   public int time()
   {
      return currentTime;
   }
   
   
   /*
   * Method that randomly chooses something from an array according to their weights
   * Takes in an array from which an element will be chosen and randomly chooses one 
   * 
   * @param array   the array of weights from which one element will be chosen
   * @return        the index of the chosen element in the array
   *                -1 if something goes wrong
   */
   public int weightedProbability(double[] array)
   {

      double threshold = 0;

      double random = Math.random();
      
      for(int j=1; j<array.length; j++)
      {
         threshold+= array[j];
         if(random<= threshold)
            return j;
      }
      return -1;
   }
}