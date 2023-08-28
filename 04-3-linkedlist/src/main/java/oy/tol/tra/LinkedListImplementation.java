package oy.tol.tra;

public class LinkedListImplementation<E> implements LinkedListInterface<E> {

   private class Node<T> {
      Node(T data) {
         element = data;
         next = null;
      }
      T element;
      Node<T> next;
      @Override
      public String toString() {
         return element.toString();
      }
   }

   private Node<E> head = null;
   private int size = 0;

   @Override
   public void add(E element) throws NullPointerException, LinkedListAllocationException {
      if(element == null){
         throw new NullPointerException("Cannot add null!");
      }
      try{
         Node<E> newNode = new Node<>(element);
         if(head == null){
            head = newNode;
            size++;
         } else{
            Node<E> current = head;
            while(current.next != null){
               current = current.next;
            }
            current.next = newNode;
            size++;
         }
      }catch(Exception e){
         throw new LinkedListAllocationException("Node allocation failed!");
      }
   }

   @Override
   public void add(int index, E element) throws NullPointerException, LinkedListAllocationException, IndexOutOfBoundsException {
      if(index > size || index < 0){
         throw new IndexOutOfBoundsException();
      }
      if(element == null){
         throw new NullPointerException("Cannot add null!");
      }
      try{
         Node<E> newNode = new Node<>(element);
         Node<E> current = head;
         int counter = 0;
         if(index==0){
            if(head != null){
               if(head.next != null){
                  newNode.next = head;
               }
            }
            head = newNode;
            size++;
         }else{
            while(counter < index-1){
               current = current.next;
               counter++;
            }
            newNode.next = current.next;
            current.next = newNode;
            size++;
         }
         
      }catch(Exception e){
         throw new LinkedListAllocationException("Node allocation failed!");
      }
   }

   @Override
   public boolean remove(E element) throws NullPointerException {
      if(element == null){
         throw new NullPointerException();
      }
      if(head == null){
         return false;
      }
      Node<E> current = head;
      while(current.next != null && current.next.element != element){
         current = current.next;
      }
      if(current.next != null){
         if(current.next.element == element){
            Node<E>tempCurrent = current.next;
            current.next = tempCurrent.next;
            size--;
            return true;
         } 
      }
      return false;
   }

   @Override
   public E remove(int index) throws IndexOutOfBoundsException {
      if(index >= size || index < 0){
         throw new IndexOutOfBoundsException();
      }
      E element;
      Node<E> current = head;
      int counter = 0;
      while(counter < index-1){
         current = current.next;
         counter++;
      }
      if(index == 0){
         element = current.element;
         head = current.next;
         size--;
      } else{
         Node<E>tempCurrent = current.next;
         element = current.element;
         current.next = tempCurrent.next;
         size--;
      }
      return element;
   }

   @Override
   public E get(int index) throws IndexOutOfBoundsException {
      if(index >= size || index < 0){
         throw new IndexOutOfBoundsException();
      }
      if(head == null){
         return null;
      }
      Node<E> current = head;
      int counter = 0;
      while(current != null && counter != index){
         current = current.next;
         counter++;
      }
      if(current == null){
         return null;
      }
      return current.element;
   }

   @Override
   public int indexOf(E element) throws NullPointerException {
      if(element == null){
         throw new NullPointerException();
      }
      if(head == null){
         return -1;
      }
      Node<E> current = head;
      int counter = 0;
      while(current != null){
         if(current.element == element) break;
         current = current.next;
         counter++;
      }
      if(current == null){
         return -1;
      }
      return counter;
   }

   @Override
   public int size() {
      return size;
   }

   @Override
   public void clear() {
      head = null;
      size = 0;
   }

   @Override
   public void reverse() {
      if(head == null) return;
      if(head.next == null) return;
      Node<E> current = head.next;
      Node<E> tempCurrent;
      if(current.next != null){
         int counter = 0;
         while(current.next != null){
            tempCurrent = current;
            current = current.next;
            tempCurrent.next = head;
            if(counter == 0){
               head.next = null;
            }
            head = tempCurrent; 
            counter++; 
         }
         current.next = head;
         head = current;
         }else{
            head.next = null;
            current.next = head;
            head = current;
         }
   }

   @Override
   public String toString() {
      StringBuilder string = new StringBuilder("[");
      Node<E> current = head;
      while(current != null){
         string.append(current.element);
         string.append(", ");
         current = current.next;
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
