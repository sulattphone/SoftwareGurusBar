/***************************************************************************************
* Su Latt Phone
* CIS 256
* Programming Project 2
* Due 10/13/18
*
* MinPQ.java
* API for a minimum prioriy queue to be used
***************************************************************************************/
import java.util.Arrays;

public class MinPQ <Key extends Comparable<Key>>
{
   //declaring instance variables
   private Key[] minPQueue;      //the mininum priority queue with Key elements
   private int queueSize;        //the size of the minPQueueu
   private int pointer;          //the pointer pointing to the last/latest element in the priority queue
   
   
   /*
   * Constructor builds the priority queue of a pre-determined size
   * Asks for no arguments and initializes a Comparable array and casts it to Key array
   */
   public MinPQ()
   {
      queueSize = 51;
      minPQueue = (Key[]) new Comparable[queueSize];
   }
   
   /*
   * The method to add a new element into the priority queue(array) and automatically sorts it
   * with swim() method
   * Asks for a new element with a generic type Key
   * 
   * @param theKey  a new element to be added to the queue
   */
   public void insert(Key theKey)
   {
      if(pointer==minPQueue.length-1)
      {
         changeLength();
      }
      minPQueue[++pointer] = theKey;
      swim(pointer);
   }
   
   
   /* 
   * The method to send an element up in the min heap (min priority queue) if there are bigger elements up there
   * and switches elements if they are in the wrong order in the heap
   * 
   * @param p  index of the element to be lifted up
   */ 
   public void swim(int p)
   {
      while(p>1 && smaller(p, p/2))
      {
         exchange(p,p/2);
         p = p/2;
      }
   }
   
   /*
   * Method to check if one element is smaller than the other
   * Takes in two elements to be compared and makes use of compareTo method from the event class
   * 
   * @param mainNum     the index of the subject element that will be checked if it's smaller
   *        compareNum  the index of the other element that will be compared against the mainNum
   * @return            a boolean indicating if the mainNum is smaller or not
   */
   public boolean smaller(int mainNum, int compareNum)
   {
      Event mainEvent = (Event) minPQueue[mainNum];
      return mainEvent.compareTo(minPQueue[compareNum])==-1;
   }
   
   
   /*
   * Method to exchange two elements in the heap
   *
   * @param i  the index of the first element among the two
            j  the index of the other element to be swapped
   */
   public void exchange(int i, int j)
   {
      Key temp = minPQueue[i];
      minPQueue[i] = minPQueue[j];
      minPQueue[j] = temp;
   }
   
   /*
   * Method to take out the first element in the array
   * which is the smallest element in the priority queue
   *
   * @return  the smallest element from the queue
   */
   public Key deleteMin()
   {
      Key min = minPQueue[1];
      exchange(1, pointer);
      pointer--;
      sink(1, pointer);
      minPQueue[pointer+1] = null;
      return min;
   }
   
   
   /*
   * Method to drag down a large element to its appropriate place in the heap
   * Takes in the index of the element to move down as well as the number of elements
   * in the priority queue and keeps moving the element down until there are no smaller
   * elements under it
   * 
   * @param  p             the index of the element to be dropped down
   *         totalEntries  the number of elements currently in the priority queue
   */
   public void sink(int p,int totalEntries)
   {
      while(2*p<= totalEntries)
      {
         int k = 2*p;
         if(k< totalEntries && smaller(k+1,k))
            k++;
         if(!smaller(k,p))
            break;
         exchange(p,k);
         p = k;
      }
   }
   
   /*
   * Method that would increase the length of the array to twice of its original length
   * Takes in no arguments but copies the origianal array and replaces back in with twice the length
   */
   public void changeLength()
   {
      queueSize *= 2;
      minPQueue = Arrays.copyOf(minPQueue, queueSize);
   }
   
   
   /*
   *  Method to check if the array (priority queue is empty or not)
   *  Checks by using the pointer variable that points to the last element in the array
   *  Empty if the pointer is 0 since there are never any element in the index 0
   *
   * @return  a boolean indicating if the array is empty or not
   */
   public boolean isEmpty()
   {
      return (pointer==0);
   }
   
   
   /*
   * Method that takes a peek at the first(smallest) element in the priority queue
   * But it does not take out that element from the queue
   *
   * @return   the first(smallest) element in the queue
   */
   public Key min()
   {
      return minPQueue[1];
   }
   
   
   /*
   * Method that tells the number of elements currently in the queue
   *
   * @return  the number of elements in the queue currently
   */
   public int size()
   {
      return pointer;
   }

}