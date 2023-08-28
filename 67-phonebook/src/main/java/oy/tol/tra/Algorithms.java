package oy.tol.tra;

import java.util.function.Predicate;

public class Algorithms {
    //Generic Bubble sort algorithm
    public static <T extends Comparable<T>> void sort(T [] array) {
        int n = array.length;
        while (n > 0){
            int i = 0;
            for (int j = 1; j < n; j++){
                if (array[j - 1].compareTo(array[j]) > 0){
                    T temp = array[j - 1];
                    array[j -1 ] = array[j];
                    array[j] = temp;
                    i = j;
                }
            }
            n = i;
        }
    }
    /* 
    public static <E extends Comparable<E>> void fastSort(E [] array, int start, int end){
        /*
         * Quicksort algorithm
         * INPUTS:
         * array: Array to be sorted
         * start: Index of the first element to sort (generally 0)
         * end: Index of the final element to be sorted (generally n-1)
         
        if (start < end){
            //Partition
            E pivot = array[end];
            int i = (start - 1);
            for(int j = start; j <= end - 1;j++){
                if(array[j].compareTo(pivot) < 0){
                    i++;
                    swap(array, i, j);
                }
            }
            swap(array, (i + 1), end);
            fastSort(array, start, i);
            fastSort(array, (i + 2), end);
        }
    }
    private static <E extends Comparable<E>> void swap(Comparable<E>[] array, int i, int j){
        //Local swap function for Quicksort algorithm
        Comparable<E> temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    */

    public static <K extends Comparable<K>,V> void fastSort(Pair<K,V>[] array, int start, int end) {
        /*
         * Quicksort algorithm fo Pair<K,V>
         * Sorts array into ascending order based on keys
         * INPUTS:
         * array: Array to be sorted
         * start: Index of the first element to sort (generally 0)
         * end: Index of the final element to be sorted (generally array.length-1)
         */
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
            fastSort(array, start, i);
            fastSort(array, (i + 2), end);
        }
    }
    private static <K extends Comparable<K>,V> void swap(Pair<K,V>[] array, int i, int j){
        //Local swap function for Quicksort algorithm
        Pair<K, V> temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    
    public static <T extends Comparable<T>> int binarySearch(T aValue, T [] fromArray, int fromIndex, int toIndex) {
	// Generic binary search algorithm
    while(fromIndex <= toIndex){
        int index = (fromIndex + toIndex) / 2;
        if(fromArray[index].compareTo(aValue) < 0){
            fromIndex = index + 1;
        } else if(fromArray[index].compareTo(aValue) > 0){
            toIndex = index - 1;
        } else{
            return index;
        }
    }
	return -1;
    }

    public static <T> void reverse(T [] array) {
        //Generic reverse array algorithm
        int i = 0;
        int end = array.length-1;
        while (i < end) {
           T temp = array[i];
           array[i] = array[array.length-i-1];
           array[array.length-i-1] = temp;
           i++;
           end--;
        }
    }
    public static class ModeSearchResult<T> {
        public T theMode;
        public int count = 0;
    }
    public static <T> void swap(T element1, T element2) {
        T temp = element1;
        element1 = element2;
        element2 = temp;
    }
    public static <T extends Comparable<T>> ModeSearchResult<T> findMode(T [] array) {
        //Generic algorithm to find mode of array
        ModeSearchResult<T> result = new ModeSearchResult<>();
        if ((array == null) || (array.length == 1)){
            result.theMode = null;
            result.count = -1;
            return result;
        }
        result.count = 0;
        for(int i = 0; i < array.length;i++){
            int count = 0;
            for(int j = 0; j < array.length;j++){
                if (array[i].equals(array[j])){
                    count++;
                }
            }
            if (count > result.count){
                result.count = count;
                result.theMode = array[i];
            }
        }
        return result;
    }

    public static <T> int partitionByRule(T [] array, int count, Predicate<T> rule){
        int i = 0;
        int j = 0;
        T temp;
        for(i = 0;i < count;i++){
            if(rule.test(array[i])){
                break;
            }
            if(i == count){
                return count;
            }
        }
        j = i + 1;
        while(j < count){
            if(!rule.test(array[j])){
                //swap(array[i], array[j]);
                temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                i++;
            }
            j++;
        }
        return i;
    }
  
}