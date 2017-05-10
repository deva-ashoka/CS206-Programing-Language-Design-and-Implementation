//Name: Deva Surya Vivek
//PLDI Assignment 2 - Generic Insertion Sort

import java.util.*;


public class GenericInsertionSort {

    public interface CompareFunction {
        boolean compare(Object element1, Object element2);
    }

    //method to to compare two arrays of integers. 4th client in the assignment
    public class IntegerArrayCompareFunction implements CompareFunction {
        public boolean compare(Object element1, Object element2) {
            Integer[] array1 = (Integer[]) element1;
            Integer[] array2 = (Integer[]) element2;
            int sum1 = 0;
            for (Integer integer : array1) {
                sum1 += integer;
            }
            int sum2 = 0;
            for (Integer integer : array2) {
                sum2 += integer;
            }
            if (sum2 > sum1) {
                return true;
            } else {
                return false;
            }
        }
    }

    //method to compare two LinkedLists. 5th client in the assignment
    public class LinkedListCompareFunction implements CompareFunction {
        public boolean compare(Object element1, Object element2) {
            try {
                LinkedList<Integer> list1 = (LinkedList<Integer>) element1;
                LinkedList<Integer> list2 = (LinkedList<Integer>) element2;
                Integer max1 = Collections.max(list1);
                Integer max2 = Collections.max(list2);
                if (max2 > max1) {
                    return true;
                } else {
                    return false;
                }
            } catch (ClassCastException e) {
                LinkedList<Float> list1 = (LinkedList<Float>) element1;
                LinkedList<Float> list2 = (LinkedList<Float>) element2;
                Float max1 = Collections.max(list1);
                Float max2 = Collections.max(list2);
                if (max2 > max1) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    public static void genericInsertionSort(CompareFunction compareFunction, Object[] list, int size) {
        Object element;
        for (int i = 1; i < size; i++) {
            element = list[i];
            int j;
            for (j = i; j > 0 && compareFunction.compare(list[j - 1], element); j--) {
                list[j] = list[j - 1];
            }
            list[j] = element;
        }
    }

    public static <T extends Comparable<T>> void genericInsertionSort(T[] list, int size) {
        T element;
        for (int i = 1; i < size; i++) {
            element = list[i];
            int j;
            for (j = i; j > 0 && list[j - 1].compareTo(element) > 0; j--) {
                list[j] = list[j - 1];
            }
            list[j] = element;
        }
    }


    public static void main(String[] args) {

        /*
        * For sorting integers, strings or floats: genericInsertionSort(array, size)
        *
        * For sorting other elements with custom comparison function: genericInsertionSort(comparisonFunction, array, size)
        */

        //Integer sorting example
        System.out.println("-----Integer sorting-----");
        Random random = new Random();
        Integer[] intArray = new Integer[5];
        for (int i = 0; i < 5; i++) {
            intArray[i] = random.nextInt(100);
        }
        System.out.println("Before Sorting: " + Arrays.toString(intArray));
        genericInsertionSort(intArray, 5);
        System.out.println("After sorting: " + Arrays.toString(intArray));
        System.out.println();

        //String sorting example
        System.out.println("-----String sorting-----");
        String[] strArray = new String[5];
        strArray[0] = "This";
        strArray[1] = "Is";
        strArray[2] = "Generic";
        strArray[3] = "Insertion";
        strArray[4] = "Sort";
        System.out.println("Before Sorting: " + Arrays.toString(strArray));
        genericInsertionSort(strArray, 5);
        System.out.println("After sorting: " + Arrays.toString(strArray));
        System.out.println();

        //Float sorting example
        System.out.println("-----Float sorting-----");
        Random rand = new Random();
        Float[] floatArray = new Float[5];
        for (int i = 0; i < 5; i++) {
            floatArray[i] = rand.nextFloat() * rand.nextInt(100);
        }
        System.out.println("Before Sorting: " + Arrays.toString(floatArray));
        genericInsertionSort(floatArray, 5);
        System.out.println("After sorting: " + Arrays.toString(floatArray));
        System.out.println();

        //Arrays of Integers sorting
        System.out.println("-----Arrays of Integers sorting-----");
        Object[] array = new Object[5];
        Integer[] array1 = new Integer[5];
        Integer[] array2 = new Integer[5];
        Integer[] array3 = new Integer[5];
        Integer[] array4 = new Integer[5];
        Integer[] array5 = new Integer[5];
        for (int i = 0; i < 5; i++) {
            array1[i] = random.nextInt(20);
            array2[i] = random.nextInt(20);
            array3[i] = random.nextInt(20);
            array4[i] = random.nextInt(20);
            array5[i] = random.nextInt(20);
        }
        array[0] = array1;
        array[1] = array2;
        array[2] = array3;
        array[3] = array4;
        array[4] = array5;
        CompareFunction function1 = new GenericInsertionSort().new IntegerArrayCompareFunction();
        System.out.print("Before Sorting: [");
        for (int i = 0; i < 5; i++) {
            System.out.print(Arrays.toString((Integer[]) array[i]));
            if (i != 4) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
        genericInsertionSort(function1, array, 5);
        System.out.print("After sorting: [");
        for (int i = 0; i < 5; i++) {
            System.out.print(Arrays.toString((Integer[]) array[i]));
            if (i != 4) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
        System.out.println();


        //LinkedList sorting example
        System.out.println("-----Linked lists sorting-----");
        Object[] linkedListArray = new Object[5];
        List<Float> list1 = new LinkedList<>();
        List<Float> list2 = new LinkedList<>();
        List<Float> list3 = new LinkedList<>();
        List<Float> list4 = new LinkedList<>();
        List<Float> list5 = new LinkedList<>();

        for (int i = 0; i < 5; i++) {
            list1.add(random.nextFloat() * random.nextInt(500));
            list2.add(random.nextFloat() * random.nextInt(500));
            list3.add(random.nextFloat() * random.nextInt(500));
            list4.add(random.nextFloat() * random.nextInt(500));
            list5.add(random.nextFloat() * random.nextInt(500));
        }
        linkedListArray[0] = list1;
        linkedListArray[1] = list2;
        linkedListArray[2] = list3;
        linkedListArray[3] = list4;
        linkedListArray[4] = list5;
        CompareFunction function2 = new GenericInsertionSort().new LinkedListCompareFunction();
        System.out.println("Before Sorting: " + Arrays.toString(linkedListArray));
        genericInsertionSort(function2, linkedListArray, 5);
        System.out.println("After sorting: " + Arrays.toString(linkedListArray));
        System.out.println();
    }
}
