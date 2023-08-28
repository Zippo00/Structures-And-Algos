package oy.tol.tra;

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
  
}