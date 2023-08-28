package oy.tol.tra;

/**
 * An implementation of the StackInterface.
 * <p>
 * 
 * Note that you need to implement construtor(s) for your concrete StackImplementation, which
 * allocates the internal Object array for the Stack:
 * - a default constructor, calling the StackImplementation(int size) with value of 10.
 * - StackImplementation(int size), which allocates an array of Object's with size.
 *  -- remember to maintain the capacity and/or currentIndex when the stack is manipulated.
 */
public class StackImplementation<E> implements StackInterface<E> {
   int STACK_TOP = -1;
   int STACK_SIZE = 10;
   private Object [] itemArray;
   // Do not use constant values in code, e.g. 10. Instead, define a constant for that. For example:
   // private static final int MY_CONSTANT_VARIABLE = 10;


   /**
    * Allocates a stack with a default capacity.
    * @throws StackAllocationException
    */
   public StackImplementation() throws StackAllocationException {
      //Call the constructor with size parameter with default size (see member variable!).
      try{
         itemArray = new Object[STACK_SIZE];
      } catch(Exception e){
         throw new StackAllocationException("Stack implementation failed!");
      }
   }

   /** Implement so that
    * - if the size is less than 2, throw StackAllocationException
    * - if the allocation of the array throws with Java exception,
    *   throw StackAllocationException.
    * @param capacity The capacity of the stack.
    * @throws StackAllocationException If cannot allocate room for the internal array.
    */
   public StackImplementation(int capacity) throws StackAllocationException {
      if (capacity < 2){
         throw new StackAllocationException("Capacity must be larger than 2");
      }
      try{
         itemArray = new Object[capacity];
      }catch(Exception E){
         throw new StackAllocationException("Java language exception");
      }
      STACK_SIZE = capacity;
      itemArray = new Object[STACK_SIZE];
   }

   @Override
   public int capacity() {
      return STACK_SIZE;
   }

   @Override
   public void push(E element) throws StackAllocationException, NullPointerException {
      if (element == null){
         throw new NullPointerException();
      }
      if (STACK_TOP == STACK_SIZE-1){
         try{
            Object [] newArray;
            newArray = new Object[STACK_SIZE*2];
            STACK_SIZE = STACK_SIZE*2;
            for(int i=0;i<itemArray.length;i++){
               newArray[i] = itemArray[i];
            }
            itemArray = newArray;
            newArray = null;
         }catch(Exception E){
            throw new StackAllocationException("Java language exception");
         }
      }
      STACK_TOP += 1;
      itemArray[STACK_TOP] = element;
   }

   @SuppressWarnings("unchecked")
   @Override
   public E pop() throws StackIsEmptyException {
      if (STACK_TOP == -1){
         throw new StackIsEmptyException("Stack is empty");
      }
      E element = (E) itemArray[STACK_TOP];
      itemArray[STACK_TOP] = null;
      STACK_TOP -= 1;
      return element;
   }

   @SuppressWarnings("unchecked")
   @Override
   public E peek() throws StackIsEmptyException {
      if (STACK_TOP == -1){
         throw new StackIsEmptyException("Stack is empty");
      }
      return (E) itemArray[STACK_TOP];
   }

   @Override
   public int size() {
      return STACK_TOP+1;
   }

   @Override
   public void clear() {
      for (int i = 0;i<STACK_SIZE;i++){
         itemArray[i] = null;
      }
      STACK_TOP = -1;
   }

   @Override
   public boolean isEmpty() {
      return STACK_TOP == -1;
   }

   @Override
   public String toString() {
      StringBuilder string = new StringBuilder("[");
      for(int i = 0;i<STACK_SIZE;i++){         
         if(itemArray[i] == null){
         break;
         }
         string.append(itemArray[i]);
         string.append(", ");
      }
      if(string.length() > 1){
         string.deleteCharAt(string.length()-1);
         string.deleteCharAt(string.length()-1);
      }
      string.append("]");
      String string2 = string.toString();
      return string2;
   }
}
