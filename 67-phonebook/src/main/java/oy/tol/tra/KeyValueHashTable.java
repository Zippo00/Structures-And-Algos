package oy.tol.tra;

public class KeyValueHashTable<K extends Comparable<K>, V> implements Dictionary<K, V> {

    private int tableSize;
    private Node<K, V> [] pairs = null;
    private int count = 0;
    private long collisionCount = 0;
    private int reallocationCount = 0;
    private int listMaxLength = 0;

    public KeyValueHashTable(int capacity) throws OutOfMemoryError {
        ensureCapacity(capacity);
    }

    public KeyValueHashTable() throws OutOfMemoryError {
        ensureCapacity(20);
    }

    private class Node<K extends Comparable<K>,V>{
        Pair<K,V> keyValue;
        Node<K,V> parent;
        Node<K,V> next;
    }

    @Override
    public Type getType() {
        return Type.HASHTABLE;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void ensureCapacity(int size) throws OutOfMemoryError {
        if (size < 20) {
            size = 20;
        }
        pairs = (Node<K,V>[])new Node[size];
        reallocationCount = 0;
        collisionCount = 0;
        tableSize = size;
        
    }

    @Override
    public int size() {
        return count;
    }

    /**
     * Prints out the statistics of the hash table.
     * Here you should print out member variable information which tell something
     * about your implementation.
     * <p>
     * For example, if you implement this using a hash table, update member
     * variables of the class (int counters) in add() whenever a collision
     * happen. Then print this counter value here.
     * You will then see if you have too many collisions. It will tell you that your
     * hash function is not good.
     */
    @Override
    public String getStatus() {
        String toReturn = "KeyValueHashTable reallocated " + reallocationCount + " times, each time doubles the size\n";
        toReturn += "KeyValueHashTable collisions happened " + collisionCount + " times\n";
        toReturn += "KeyValueHashTable linked list max length is " + listMaxLength + "\n";
        toReturn += String.format("KeyValueHashTable fill rate is %.2f%%%n", (count / (double)pairs.length) * 100.0);
        return toReturn;
    }

    @Override
    public boolean add(K key, V value) throws IllegalArgumentException, OutOfMemoryError {
        // Supposed to return boolean or hashIndex??
        if (null == key || value == null) throw new IllegalArgumentException("Key nor value can be null");
        if(count >= tableSize){
            reallocate(tableSize*2);
        }
        Node<K,V> node = new Node<>();
        node.keyValue = new Pair<K,V>(key, value);
        int hashIndex = hashIndex(key.hashCode());
        if(pairs[hashIndex] == null){       
            pairs[hashIndex] = node;
            count++;
            return true;
        }
        if(pairs[hashIndex].keyValue.getKey().equals(key)) {
            pairs[hashIndex].keyValue.setvalue(value);
            return true;
        }
        collisionCount++;
        Node<K,V> current = pairs[hashIndex];
        int listLength = 1;
        while(current.next != null){
            current = current.next;
            listLength++;
        }
        current.next = node;
        node.parent = current;
        count++;
        if(listLength > listMaxLength){
            listMaxLength = listLength;
        }
        return true;
    }

    @Override
    public V find(K key) throws IllegalArgumentException {
        if(key == null) throw new IllegalArgumentException("Can not search for null");
        int hashIndex = hashIndex(key.hashCode());
        if(pairs[hashIndex] == null){
            return null;
        }
        Node<K,V> current = pairs[hashIndex];
        if(current.keyValue.getKey().compareTo(key) == 0){
            return current.keyValue.getValue();
        }
        while(current.next != null) {
            current = current.next;
            K pairsKey = current.keyValue.getKey();
            if(pairsKey.compareTo(key) == 0) {
                return current.keyValue.getValue();
            }
        }
        return null;
    }

    @Override
    @java.lang.SuppressWarnings({"unchecked"})
    public Pair<K,V> [] toSortedArray() {
        Pair<K, V> [] sorted = (Pair<K,V>[])new Pair[count];
        int j = 0;
        Node<K,V> current;
        compress();
        for(int i = 0; i < tableSize; i++) {
            if(pairs[i] != null){
                sorted[j] = pairs[i].keyValue;
                j++;
                current = pairs[i];
                while(current.next != null){
                    current = current.next;
                    sorted[j] = current.keyValue;
                    j++;
                }
            }
        }
        Algorithms.fastSort(sorted, 0, sorted.length - 1);
        return sorted;
      }

    @Override
    public void compress() throws OutOfMemoryError {
        // First partition the null's to the end of the array.
        int indexOfFirstNull = Algorithms.partitionByRule(pairs, count, element -> element == null);
        // Then reallocate using the index from partitioning, pointing the first null in the array.
        if(indexOfFirstNull >= tableSize){
            return;
        }
        reallocate(indexOfFirstNull);
        reallocationCount--;
    }
    
    private int hashIndex(int hashCode) {
        /*
         * Takes key.hashCode() as an argument and returns the calculated hashIndex
         */
        int index = (hashCode & 0x7fffffff) % tableSize;
            return index;
    }

    private void reallocate(int newSize){
        reallocationCount++;
        int oldSize = tableSize;
        tableSize = newSize;
        Node<K, V> [] tempPairs = pairs;
        pairs = (Node<K,V>[])new Node[newSize];
        int tempCount = count;
        Node<K,V> current; 
        for(int i = 0; i < oldSize; i++){
            current = tempPairs[i];
            if(tempPairs[i] != null){
                add(tempPairs[i].keyValue.getKey(), tempPairs[i].keyValue.getValue());
                while(current.next != null){
                    current = current.next;
                    add(current.keyValue.getKey(), current.keyValue.getValue());
                }
            }
        }
        count = tempCount;
        return;
    }
    /*   ***MOVED TO ALGORITHMS***
    private void tableSort(Node<K,V>[] array, int start, int end) {
        /*
         * Quicksort algorithm fo Pair<K,V>
         * Sorts array into ascending order based on keys
         * INPUTS:
         * array: Array to be sorted
         * start: Index of the first element to sort (generally 0)
         * end: Index of the final element to be sorted (generally array.length-1)
         
        if (start < end){
            //Partition
            K pivot = array[end].getKey();
            int i = (start - 1);
            for(int j = start; j <= end - 1;j++){
                if(array[j].getKey().compareTo(pivot) < 0){
                    i++;
                    swap(array, i, j);
                }
            }
            swap(array, (i + 1), end);
            tableSort(array, start, i);
            tableSort(array, (i + 2), end);
        }
    }
    private void swap(Node<K,V>[] array, int i, int j){
        //Local swap function for Quicksort algorithm
        Node<K, V> temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    */
}
