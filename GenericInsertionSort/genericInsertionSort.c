//PLDI Assignment: Generic Insertion Sort (in C)
//Name: Deva

#include <stdlib.h>
#include <stdio.h>
#include <string.h>


void insertionSort(void *arrayPtr, int arraySize, size_t size, int (*compareFunction)(void *, void *)) {

    for (int i = 1; i < arraySize; i++) {
        void *element = (char *) arrayPtr + i * size;
        void *temp = malloc(size);
        memcpy(temp, element, size);
        int j;
        for (j = i; j > 0 && compareFunction((char *) arrayPtr + (j - 1) * size, temp) > 0; j--) {
            memcpy((char *) arrayPtr + j * size, (char *) arrayPtr + (j - 1) * size, size);
        }
        memcpy((char *) arrayPtr + j * size, temp, size);
    }
}

int numCompare(void *s1, void *s2) {
    int *num1 = (int *) s1;
    int *num2 = (int *) s2;
    if (*num1 < *num2)
        return -1;
    else if (*num1 > *num2)
        return 1;
    else
        return 0;
}

int stringCompare(void *s1, void *s2) {
    char *str1 = *(char **) s1;
    char *str2 = *(char **) s2;
    if (strcmp(str1, str2) > 0) {
        return 1;
    } else {
        return 0;
    }
}

int floatCompare(void *s1, void *s2) {
    float *num1 = (float *) s1;
    float *num2 = (float *) s2;
    if (*num1 < *num2)
        return -1;
    else if (*num1 > *num2)
        return 1;
    else
        return 0;
}


int main() {

    printf("------Integer sort------ \n");
    int array[5] = {100, 75, 34, 99, 83};
    for (int i = 0; i < 5; i++) {
        printf("%d ", array[i]);
    }
    printf("\n");
    insertionSort(array, 5, sizeof(int), numCompare);
    for (int i = 0; i < 5; i++) {
        printf("%d ", array[i]);
    }
    printf("\n");


    printf("------String sort------ \n");
    char *strArray[5] = {"This", "Is", "Generic", "Insertion", "Sort"};
    for (int i = 0; i < 5; i++) {
        printf("%s ", strArray[i]);
    }
    printf("\n");
    insertionSort(strArray, 5, sizeof(char *), stringCompare);
    for (int i = 0; i < 5; i++) {
        printf("%s ", strArray[i]);
    }
    printf("\n");

    printf("------Float sort------ \n");
    float floatArray[5] = {10.5, 99.9, 45.7, 2.3, 1.1};
    for (int i = 0; i < 5; i++) {
        printf("%f ", floatArray[i]);
    }
    printf("\n");
    insertionSort(floatArray, 5, sizeof(float), floatCompare);
    for (int i = 0; i < 5; i++) {
        printf("%f ", floatArray[i]);
    }
    printf("\n");
}