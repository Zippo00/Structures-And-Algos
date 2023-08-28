package oy.tol.tra;

public class QueueImplementation<E> implements QueueInterface<E>{
    int QUEUE_SIZE = 25;
    int Head = 0;
    int Tail = 0;
    int itemCount = 0;
    private Object [] itemQueue;

    public QueueImplementation() throws QueueAllocationException{
        try{
            itemQueue = new Object[QUEUE_SIZE];
        }
        catch(Exception e){
            throw new QueueAllocationException("Failed to create new queue!");
        }      
    }
    public QueueImplementation(int capacity) throws QueueAllocationException{
        if(capacity < 2){
            throw new QueueAllocationException("Capacity must be more than 1!");
        }
        try{
            itemQueue = new Object[capacity];
        }catch(Exception e){
            throw new QueueAllocationException("Java language exception");
        }
        QUEUE_SIZE = capacity;
    } 
    @Override
    public int capacity(){
        return QUEUE_SIZE;
    }
    @Override
    public void enqueue(E element) throws QueueAllocationException, NullPointerException{
        if(element == null){
            throw new NullPointerException();
        }
        if((Tail >= QUEUE_SIZE) && (Head > 0)){
            Tail = 0;
        }
        if(itemCount == QUEUE_SIZE){
            try{
                Object [] newQueue;
                newQueue = new Object[QUEUE_SIZE*2];
                QUEUE_SIZE = QUEUE_SIZE*2;
                for(int i=0;i<QUEUE_SIZE/2;i++){
                    if(Head > (QUEUE_SIZE/2)-1){
                        Head = 0;
                    }
                    newQueue[i] = itemQueue[Head];
                    Head++;
                    if(i == (QUEUE_SIZE/2)-1){
                        itemQueue = newQueue;
                        newQueue = null;
                        Head = 0;
                        Tail = i+1;
                    }
                }
            }
            catch(Exception e){
                throw new QueueAllocationException("Queue reallocation failed!");
            }
        }
        itemQueue[Tail] = element;
        Tail++;
        itemCount++;
    }
    @Override
    public E dequeue() throws QueueIsEmptyException{
        if(itemCount == 0){
            throw new QueueIsEmptyException("Queue is empty!");
        }
        E x = (E) itemQueue[Head];
        itemQueue[Head] = null;
        Head++;
        itemCount--;
        if(Head >= QUEUE_SIZE){
            Head = 0;
        }
        return x;
    }
    @Override
    public E element() throws QueueIsEmptyException{
        if (itemCount == 0){
            throw new QueueIsEmptyException("Queue is empty!");
        }
        return (E) itemQueue[Head];
    }
    @Override
    public int size(){
        return itemCount;
    }
    @Override
    public boolean isEmpty(){
        return (itemCount == 0);
    }
    @Override
    public void clear(){
        for (int i = 0;i<QUEUE_SIZE;i++){
            itemQueue[i] = null;
        }
        itemCount = 0;
        Head = 0;
        Tail = 0;
    }
    @Override
    public String toString(){
        StringBuilder string = new StringBuilder("[");
        int tempHead = Head;
        for(int i=0;i<QUEUE_SIZE;i++){
            if(tempHead >= QUEUE_SIZE){
                tempHead = 0;
            }
            if(itemQueue[tempHead] == null){
                break;
            }
            string.append(itemQueue[tempHead]);
            string.append(", ");
            tempHead++;
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
