//Name: Deva Surya Vivek
//PLDI - Handout3 problem 2
#include <stdio.h>
#include <stdlib.h>
#include <memory.h>

#define BUFFERSIZE 1024

char *finalString = "";

typedef enum {
    Integer, String, List, Nil
} nodeType;

typedef struct node {
    void *data;
    nodeType node_type;
    struct node *next;
} Node;

//node for integer
Node *intNode(int data) {
    Node *integerNode = (Node *) malloc(sizeof(Node));
    integerNode->node_type = Integer;
    integerNode->data = malloc(sizeof(int));
    *(int *) integerNode->data = data;
    integerNode->next = NULL;

    Node *lstNode = (Node *) malloc(sizeof(Node));
    lstNode->node_type = List;
    lstNode->data = malloc(sizeof(Node));
    *(Node *) lstNode->data = *integerNode;
    lstNode->next = NULL;
    return lstNode;
}

//node for string
Node *strNode(char *data) {
    Node *stringNode = (Node *) malloc(sizeof(Node));
    stringNode->node_type = String;
    stringNode->data = malloc(sizeof(char *));
    *(char **) stringNode->data = data;
    stringNode->next = NULL;

    Node *lstNode = (Node *) malloc(sizeof(Node));
    lstNode->node_type = List;
    lstNode->data = malloc(sizeof(Node));
    *(Node *) lstNode->data = *stringNode;
    lstNode->next = NULL;

    return lstNode;
}

Node *addNode(Node *head, Node *node) {
    if (head == NULL) {
        head = node;
    } else {
        Node *tempNode = head;
        while (tempNode->next != NULL) {
            tempNode = tempNode->next;
        }
        tempNode->next = node;
    }
    return head;
}

char *convert(int type) {
    switch (type) {
        case 0:
            return "Integer";
        case 1:
            return "String";
        case 2:
            return "List";
        case 3:
            return "Nil";
        default:
            printf("%s \n", "TYPE NOT AVAILABLE");
    }
    return "NULL";
}

void printLinkedList(Node *N) {
    Node *temp = NULL;
    for (temp = N; temp != NULL; temp = temp->next) {
        int type = temp->node_type;
        Node *dataNode = temp->data;
        int datatype = dataNode->node_type;

        switch (dataNode->node_type) {
            case Integer:
                printf("%s | %s , %d | -> ", convert(type), convert(datatype), *(int *) dataNode->data);
                break;
            case String:
                printf("%s | %s , %s | -> ", convert(type), convert(datatype), *(char **) dataNode->data);
                break;
            case List:
                printLinkedList(dataNode);
            case Nil:
                putchar('\n');
                break;
        }
    }
    printf("%s \n", "NULL");
}

static char *concatenateTwoStrings(const char *string1, const char *string2) {
    char *retVal = malloc(strlen(string1) + strlen(string2) + 1);
    strcpy(retVal, string1);
    strcat(retVal, string2);
    return retVal;
}

void concatenate(Node *N) {
    Node *temp = NULL;
    for (temp = N; temp != NULL; temp = temp->next) {
        Node *dataNode = temp->data;
        switch (dataNode->node_type) {
            case String:
                finalString = concatenateTwoStrings(finalString, *(char **) dataNode->data);
                break;
            case List:
                concatenate(dataNode);
                break;
            default:
                break;
        }
    }
}

char *ConcatAll(Node *N) {
    concatenate(N);
    return finalString;
}

int main(int argc, char **argv) {

    //reading the input and storing it in buffer
    char buffer[BUFFERSIZE];
    FILE *f = fopen(argv[1], "r");
    fgets(buffer, BUFFERSIZE, f);
    printf("Input: %s\n", buffer);

    Node *N;

    memmove(buffer, buffer + 1, strlen(buffer));
    buffer[strlen(buffer) - 1] = NULL;
    char *element = strtok(buffer, " ");
    int i = 0;
    while (element != NULL) {

        if (strcmp(element, "0") == 0) {
            N = addNode(N, intNode(atoi(element)));
        } else {
            int num = atoi(element);
            if (num == 0) {
                N = addNode(N, strNode(element));
            } else {
                N = addNode(N, intNode(num));
            }
        }
        element = strtok(NULL, " ");
    }

    printLinkedList(N);
    printf("Concatenated Strings: %s \n", ConcatAll(N));
}
