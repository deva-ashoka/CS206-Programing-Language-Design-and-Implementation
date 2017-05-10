//PLDI Assignment: Implementing Hash Table
//Name: Deva

#include <stdio.h>
#include <memory.h>
#include <stdlib.h>

unsigned int status = 1;
unsigned int MAX_ITER_CNT = 10;
unsigned int MAX_REC_LEVEL = 4;
unsigned int CHANGE_PARAMS = 0;
unsigned int DOUBLE_SIZE = 1;

typedef struct {
    unsigned int s, t, n, a, b;
    char **array;
} HashTable;


HashTable init();

unsigned int hash(char *, unsigned int, unsigned int);

unsigned int search(HashTable, char *);

HashTable insertAtIndex(HashTable, char *, unsigned int);

HashTable insert(HashTable, char *, unsigned int);

HashTable reHash(HashTable, unsigned int);

HashTable delete(HashTable, char *);

//initialise the hash table with the given values
HashTable init() {
    HashTable hashTable;
    hashTable.s = 8;
    hashTable.t = 3;
    hashTable.n = 0;
    hashTable.a = 1;
    hashTable.b = 3;
    hashTable.array = (char **) malloc((hashTable.s) * sizeof(char *));
    for (unsigned int i = 0; i < hashTable.s; i++) {
        hashTable.array[i] = NULL;
    }
    return hashTable;
}

//hash function
unsigned int hash(char *string, unsigned int c, unsigned int t) {
    unsigned int m = 0;
    for (unsigned int i = 0; string[i] != '\0'; i++) {
        m = 65791 * m + (unsigned int) string[i];
    }
    m = m * c;
    return m >> (32 - t);
}

//search function: returns 1 if the string is found in the hash table
unsigned int search(HashTable hashTable, char *string) {
    unsigned int h1, h2, s = 0;
    h1 = hash(string, hashTable.a, hashTable.t);
    if (hashTable.array[h1]) {
        if (strcmp(hashTable.array[h1], string) == 0) {
            s = 1;
        }
    }
    h2 = hash(string, hashTable.b, hashTable.t);
    if (hashTable.array[h2]) {
        if (strcmp(hashTable.array[h2], string) == 0) {
            s = 1;
        }
    }
    return s;

}

//rehash: necessary when load factor is grater than half
HashTable reHash(HashTable hashTable, unsigned int type) {
    HashTable newHashTable;
    if (type == DOUBLE_SIZE) {
        newHashTable.a = hashTable.a;
        newHashTable.b = hashTable.b;
        newHashTable.s = 2 * hashTable.s;
        newHashTable.t = hashTable.t + 1;
        printf("Rehashing : type = doubling size\n");
        printf("------Copying inserted strings to new Hash Table-----\n");
    } else if (type == CHANGE_PARAMS) {
        newHashTable.a = hashTable.b;
        newHashTable.b = newHashTable.a + 2;
        newHashTable.s = hashTable.s;
        newHashTable.t = hashTable.t;
        printf("Rehashing : type = parameters changed - new parameters are %u , %u\n", newHashTable.a,
               newHashTable.b);
        printf("------Copying inserted strings to new Hash Table-----\n");
    } else {
        printf("Wrong type\n");
        status = 0;
        return hashTable;
    }

    newHashTable.n = 0;
    newHashTable.array = (char **) malloc((newHashTable.s) * sizeof(char *));
    for (unsigned int i = 0; i < newHashTable.s; i++) {
        newHashTable.array[i] = NULL;
    }

    for (unsigned int idx = 0; idx < hashTable.s; idx++) {
        newHashTable = insert(newHashTable, hashTable.array[idx], MAX_REC_LEVEL);
        if (status == 0) {
            printf("------------Rehashing failed!------------\n");
            for (unsigned int i = 0; i < newHashTable.s; i++) {
                free(newHashTable.array[i]);
            }
            free(newHashTable.array);
            return hashTable;
        }
    }
    printf("------------Rehashing done!-------------\n");
    for (unsigned int i = 0; i < hashTable.s; i++) {
        free(hashTable.array[i]);
    }
    free(hashTable.array);
    return newHashTable;
}

//insert a string at the specified index
HashTable insertAtIndex(HashTable hashTable, char *string, unsigned int h) {
    hashTable.array[h] = (char *) malloc((1 + strlen(string)) * sizeof(char));
    strcpy(hashTable.array[h], string);
    status = 1;
    printf("'%s' inserted at index %d\n", string, h);
    hashTable.n += 1;
    if (2 * hashTable.n > hashTable.s) {
        printf("Load factor is grater than half. Rehashing by doubling size.\n");
        hashTable = reHash(hashTable, DOUBLE_SIZE);
    }
    return hashTable;
}

//insert function: to insert a string into the hash table
HashTable insert(HashTable hashTable, char *string, unsigned int recLevel) {

    if (!string) {
        return hashTable;
    }
    if (search(hashTable, string) == 1) {
        printf("String already present\n");
        status = 0;
        return hashTable;
    }

    char *str = (char *) malloc((1 + strlen(string)) * sizeof(char));
    strcpy(str, string);

    unsigned int h1, h2;
    char *temp;

    for (unsigned int i = 0; i < MAX_ITER_CNT; ++i) {
        h1 = hash(str, hashTable.a, hashTable.t);
        if (hashTable.array[h1] == NULL) {
            hashTable = insertAtIndex(hashTable, str, h1);
            free(str);
            return hashTable;
        }
        h2 = hash(str, hashTable.b, hashTable.t);
        if (hashTable.array[h2] == NULL) {
            hashTable = insertAtIndex(hashTable, str, h2);
            free(str);
            return hashTable;
        }

        temp = hashTable.array[h1];
        hashTable.array[h1] = str;
        str = temp;
    }

    if (recLevel < MAX_REC_LEVEL) {

        hashTable = reHash(hashTable, CHANGE_PARAMS);
        if (status == 0) {
            hashTable = reHash(hashTable, DOUBLE_SIZE);
        }
        if (str == 0) {
            printf("Insertion failed\n");
        } else {
            hashTable = insert(hashTable, str, recLevel + 1);
        }
    } else {
        printf("Maximum rec level reached. Insertion failed\n");
    }
    return hashTable;
}

//delete function: deletes the string from the hash table
HashTable delete(HashTable hashTable, char *string) {
    unsigned int h1, h2;
    status = 0;
    h1 = hash(string, hashTable.a, hashTable.t);
    if (hashTable.array[h1]) {
        if (strcmp(hashTable.array[h1], string) == 0) {
            free(hashTable.array[h1]);
            hashTable.array[h1] = NULL;
            hashTable.n -= 1;
            status = 1;
        }
    }
    h2 = hash(string, hashTable.b, hashTable.t);
    if (hashTable.array[h2]) {
        if (strcmp(hashTable.array[h2], string) == 0) {
            free(hashTable.array[h1]);
            hashTable.array[h2] = NULL;
            hashTable.n -= 1;
            status = 1;
        }
    }
    return hashTable;
}

int main(int argc, char *argv[]) {
    char *fileName = argv[1];
    FILE *stream;
    size_t len = 0;
    ssize_t read;
    char *line = NULL;

    HashTable hashTable;
    hashTable = init();


    stream = fopen(fileName, "r");

    read = getline(&line, &len, stream);
    int nint = atoi(line);
    printf("+++++++++++++ Running Insert +++++++++++++\n");
    for (int i = 0; i < nint; i++) {
        read = getline(&line, &len, stream);
        line[strlen(line) - 1] = '\0';
        hashTable = insert(hashTable, line, 0);
    }
    read = getline(&line, &len, stream);
    int nsearch = atoi(line);
    printf("?????????????? Running Search ??????????????\n");
    for (int i = 0; i < nsearch; i++) {
        read = getline(&line, &len, stream);
        line[strlen(line) - 1] = '\0';
        int found = search(hashTable, line);
        if (found == 1) {
            printf("%s found!\n", line);
        } else {
            printf("%s not found!\n", line);
        }
    }
    read = getline(&line, &len, stream);
    int ndel = atoi(line);
    printf("- - - - - - - Running Delete - - - - - - - \n");
    for (int i = 0; i < ndel; i++) {
        read = getline(&line, &len, stream);
        line[strlen(line) - 1] = '\0';
        hashTable = delete(hashTable, line);
        if (status == 1) {
            printf("Successfully deleted %s!\n", line);
        } else {
            printf("Failed to delete %s!\n", line);
        }
    }

    free(line);
    fclose(stream);

}