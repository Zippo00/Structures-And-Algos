package oy.tol.tra;


public class KeyValueBSearchTree<K extends Comparable<K>,V> implements Dictionary<K, V> {

    // This is the BST implementation, KeyValueHashTable has the hash table implementation
    int collisionCount = 0;
    int collisionChainMax = 0;
    int count;
    int indexCounter = 0;

    class Node<K extends Comparable<K>,V> {
        int hash = -1;
        Pair<K,V> keyValue;
        Node<K, V> left = null;
        Node<K, V> right = null;
        Node<K, V> parent = null;
        Node<K, V> next = null;

        private void add(Node<K,V> node) {
            int collisionChain = 0;
            if (node.hash == this.hash){
                collisionCount++;
                if(this.next == null){
                    this.next = node;
                    count++;
                    node.parent = this;
                    collisionChain++;
                } else {
                    collisionChain++;
                    this.next.add(node);
                } 
            } else if(node.hash < this.hash){
                if(this.left == null) {
                    this.left = node;
                    count++;
                    node.parent = this;
                } else {
                    this.left.add(node);
                }
            } else if (node.hash > this.hash){
                if(this.right == null){
                    this.right = node;
                    count++;
                    node.parent = this;
                } else {
                    this.right.add(node);
                }
            }
            if (collisionChain > collisionChainMax) {
                collisionChainMax = collisionChain;
            }
        }
    }
    //BST root
    Node<K,V> root;
    
    private int getDepth(Node<K,V> node){
        int treeDepthLeft = 0;
        int treeDepthRight = 0;
        if(node == null || root == null) {
            return 0;
        }
        Node<K,V> leftNode = node;
        treeDepthLeft++;
        while(leftNode.left != null){
            leftNode = leftNode.left;
            treeDepthLeft++;
        }
        Node<K,V> rightNode = node;
        treeDepthRight++;
        while(rightNode.right != null) {
            rightNode = rightNode.right;
            treeDepthRight++;
        }
        if(treeDepthLeft > treeDepthRight){
            return treeDepthLeft;
        }
        return treeDepthRight;
    }

    @Override
    public Type getType() {
       return Type.BST;
    }

    @Override
    public int size() {
        return count;
    }

    /**
     * Prints out the statistics of the tree structure usage.
     * Here you should print out member variable information which tell something about
     * your implementation.
     * <p>
     * For example, if you implement this using a hash table, update member variables of the class
     * (int counters) in add(K) whenever a collision happen. Then print this counter value here. 
     * You will then see if you have too many collisions. It will tell you that your hash function
     * is good or bad (too much collisions against data size).
     */
    @Override
    public String getStatus() {
        String toReturn = "BST longest collision chain in a tree node is " + collisionChainMax + "\n";
        toReturn += "BST collisions happened " + collisionCount + " times\n";
        toReturn += String.format("BST has max depth of  %d\n", getDepth(root));
        return toReturn;
    }

    @Override
    public boolean add(K key, V value) throws IllegalArgumentException, OutOfMemoryError {
        //Adds the given key-value pair into BST as a node
        if(key == null || value == null) throw new IllegalArgumentException("Neither key or value can be null in add()");
        Node<K,V> node = new Node<>();
        node.keyValue = new Pair<>(key, value);
        node.hash = key.hashCode();
        if(root == null) {
            root = node;
            count++;
        } else {
            root.add(node);
        }
        return true;
    }

    @Override
    public V find(K key) throws IllegalArgumentException {
        //Looks for the given key from BST and returns the correspondiong value if found.
        //Else returns null.
        if(key == null) throw new IllegalArgumentException("Can not try to find a null key");
        int hashToFind = key.hashCode();
        if(root != null) {
            Node<K,V> node = root;
            while(node != null && node.hash != hashToFind) {
                if(hashToFind < node.hash){
                    node = node.left;
                } else if(hashToFind > node.hash) {
                    node = node.right;
                }
            }
            if(node != null){
                if(node.keyValue.getKey().equals(key)) {
                    return node.keyValue.getValue();
                }
            } 
            if(node != null){
                while(node.keyValue.getKey() != key && node.next != null){
                    node = node.next;
                    if(node.keyValue.getKey().equals(key)){
                        return node.keyValue.getValue();
                    }
                }
                
            }   
        }
        return null;
    }

    @Override
    public Pair<K,V> [] toSortedArray() {
        // Returns contents of BST as an array
        // sorted by ascending key order
        if(root == null) return null;
        Pair<K, V> [] sorted = (Pair<K,V>[])new Pair[count];
        bstToArrayInOrder(root, sorted);
        indexCounter = 0;
        Algorithms.fastSort(sorted, 0, sorted.length-1);
        return sorted;
      }
    private void bstToArrayInOrder(Node<K,V> node, Pair<K,V> [] array){
        //Adds key-value pairs of nodes into an array
        //Must reset indexCounter to 0 after calling

        if(node != null) {
            bstToArrayInOrder(node.left, array);
            array[indexCounter] = node.keyValue;
            indexCounter++;
            bstToArrayInOrder(node.right, array);

        }
    }

    @Override
    public void compress() throws OutOfMemoryError {
        return;    
    }
    @Override
    public void ensureCapacity(int size) throws OutOfMemoryError {
        return;
    }
    

   
}
