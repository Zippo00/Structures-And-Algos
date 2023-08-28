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
        for(i = 0;i <= count;i++){
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