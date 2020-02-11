/********************************************************************************
* Su Latt Phone
* CIS 256
* Programming Project 2
* Due 10/13/18
*
* SoftwareGurusBar.java
* Main driver class that handles all the different types of events that goes on
* in the bar
***********************************************************************************/
public class SoftwareGurusBar
{
   private int freeChairs = 50;
   private double profit = 0.0;
   private double reOrderProb = 0.15;        //probability of reordering
   private boolean reOrder;                  //boolean indicating if they are reordering or not
   private SimulationFramework simulation = new SimulationFramework();
   
   private final int barOperatingMinutes = 240;
   private double[] beerPrices = {0, 2.0, 3.0, 4.0};    //prices local, imported, special
   private double[] beerProb = {0,0.15,0.60,0.25};       //probability of local, import, special
   private double[] gSizeProb = {0,0.20,0.35,0.10,0.25,0.10};  //probability of group sizes (1-5)
   private double[] reOrderPeopleProb = {0, 0.3, 0.4, 0.15, 0.1, 0.05};
   
   
   public static void main(String[] args)
   {
      SoftwareGurusBar world = new SoftwareGurusBar();
   }
   
   /*
   * Constructor that creates ArriveEvent object and runs the whole bar simulation after everything
   * And finally displays the total profit accumulated
   */
   SoftwareGurusBar()        
   {
      int t = 0;
      while(t < barOperatingMinutes)         //simulate 4 hours of bar operation time
      {
         t+= randomBetween(2,5);             //new group every 2-5 minutes
         simulation.scheduleEvent(new ArriveEvent(t, simulation.weightedProbability(gSizeProb)));
      }
      simulation.run();
      System.out.printf("Total profit %.2f", profit);
   }
   
   
   /*
   * Method to generate a integer between two particular integers
   * Asks for two integers and returns an integer between (the two bounds inclusive)
   * 
   * @param low    the smaller integer of the two (the lower bound)
   *        high   the bigger integer of the two (the higher bound)
   * @return       an integer between low and high (both inclusive)
   */
   private int randomBetween(int low, int high)
   {
      return low+ (int)((high-low+1)*Math.random());
   }
   
   
   /*
   * Method determining whether there is enough seat for a certain group
   * Takes in the number of people  and determines if there is enough seat
   *
   * @param numOfPeople the number of people in a certain group that comes in
   * @return            boolean indicating whether there is enough seat or not
   */
   public boolean canSeat(int numOfPeople)
   {
      System.out.println("Group of "+numOfPeople+" arrives at time "+simulation.time());
      
      if(numOfPeople<freeChairs)
      {
         freeChairs-= numOfPeople;
         System.out.println(numOfPeople+" people seated.");
         return true;
      }
      else
      {
         System.out.println("No free spots. Group leaves.");
         return false;
      }
   }
   
   /*
   * Method that orders the right type of beer and updates the profit
   * Also determines the type of order is a first-time-order or a reorder
   * and displays different outputs according to it
   * 
   * @param beerType index assigned to each type of beer
   */
   public void order(int beerType)
   {
      if(reOrder)
         System.out.println("Service reordered for beer type "+ beerType+" at time "+ simulation.time());
      else
         System.out.println("Service ordered for beer type "+ beerType+" at time "+simulation.time());
         
      profit += beerPrices[beerType];
   }
   
   /*
   * Method that takes care of the reordering from a group
   * Also determines randomly how many people from the group will reorder (not everyone all the time)
   *
   * @param numOfPeople  the total number of people in the group
   */
   public void reOrder(int numOfPeople)
   {
      reOrder = true;
      
      //determining how many will reorder
      int randomOrderSize = simulation.weightedProbability(reOrderPeopleProb);
         
      for(int i=0; i<randomOrderSize; i++)
      {
         order(simulation.weightedProbability(beerProb));
      }
   }
   
   /*
   * Method that takes care of a group leaving the bar
   * Takes in how many people leaves and increasing the number of freechairs by that amount
   */
   public void leave(int numOfPeople)
   {
      System.out.println("Group of size "+ numOfPeople+" leaves at time "+ simulation.time());
      freeChairs += numOfPeople;
   }
   
   /*
   *  A private class that handles groups' arrival in the bar
   */
   private class ArriveEvent extends Event
   {
      private int groupSize;
      
      /*
      * Constructor takes in time and group size as parameters
      * Passes the time to super class (Event) constructor
      * Initializes instance variable groupSize to the group size parameter
      *
      * @param time  the time of the event's occurence in minutes
      *        gs    the size of the group associated with the event 
      */
      ArriveEvent(int time, int gs)
      {
         super(time);
         groupSize = gs;
      }
      
      /*
      * Method that goes and creates the orderEvent object and puts it into the priority queue
      * if there is enough room for the group
      */
      public void processEvent()
      {
         if(canSeat(groupSize))
         {
            simulation.scheduleEvent(new OrderEvent(time+randomBetween(2,10), groupSize));
         }
      }
   }
   
   
   /*
   *  A private class that handles groups' ordering in the bar
   */
   private class OrderEvent extends Event
   {
      private int groupSize;
      
      /*
      * Constructor takes in time and group size as parameters
      * Passes the time to super class (Event) constructor
      * Initializes instance variable groupSize to the group size parameter
      *
      * @param time  the time of the event's occurence in minutes
      *        gs    the size of the group associated with the event 
      */
      OrderEvent(int time, int gs)    
      {
         super(time);
         groupSize = gs;
      }
      
      /*
      * Makes the ordering by calling the order() method and also creates a reOrderEvent or a leaveEvent
      * Generates a random double between 0 and 1
      * If the random double is below the probability of reOrder, creates reOrderEvent object
      * Else, creates leaveEvent
      */
      public void processEvent()
      {
      
         //all members of the group first at least order once
         for(int i=0; i<groupSize; i++)
         {
            order(simulation.weightedProbability(beerProb));
         }
         
         
         double randomReOrder = Math.random(); //random value to check against the reorder probability
         
         if(randomReOrder<= 0.2)    //if someone is reordering, create reOrderEvent
         {
            simulation.scheduleEvent(new ReOrderEvent(time+randomBetween(15,30), groupSize));   //reorder in 15-30 mins
         }
         else                       //if no one is reordering, create leaveEvent
         {
            reOrder = false;
            simulation.scheduleEvent(new LeaveEvent(time+randomBetween(30,60),groupSize));      //leave in 30 or 60 mins
         }
      }
   }
   
   /*
   *  A private class that handles groups' departure from the bar
   */
   private class LeaveEvent extends Event
   {
      private int groupSize;
      
      /*
      * Constructor takes in time and group size as parameters
      * Passes the time to super class (Event) constructor
      * Initializes instance variable groupSize to the group size parameter
      *
      * @param time  the time of the event's occurence in minutes
      *        gs    the size of the group associated with the event 
      */
      LeaveEvent(int time, int gs)
      {
         super(time);
         groupSize = gs;
      }
      
      
      /*
      * Method that calls the leave() method
      */
      public void processEvent()
      {
         leave(groupSize);
      }
   }
   
   /*
   *  A private class that handles groups' reordering in the bar
   */
   private class ReOrderEvent extends Event
   {
      private int groupSize;
      
      /*
      * Constructor takes in time and group size as parameters
      * Passes the time to super class (Event) constructor
      * Initializes instance variable groupSize to the group size parameter
      *
      * @param time  the time of the event's occurence in minutes
      *        gs    the size of the group associated with the event 
      */
      ReOrderEvent(int time, int gs)
      {
         super(time);
         groupSize  = gs;
      }
      
      /*
      * Method that does the reordering for the customers
      * And then creates the leaveEvent object for them
      */
      public void processEvent()
      {
         reOrder(groupSize);
         simulation.scheduleEvent(new LeaveEvent(time + randomBetween(30,60), groupSize));
      }
   }
}