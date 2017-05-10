class Node:
    def __init__(self):
        self.key = 0
        self.leftChild = None
        self.rightChild = None
        self.parent = None


class BST:
    def __init__(self):
        self.root = None
        self.size = 0

    def isLeaf(self, N):
        if N.leftChild is None and N.rightChild is None:
            return True
        else:
            return False

    def contains(self, k):
        P = self.root
        while not self.isLeaf(P):
            if P.key == k:
                return 1
            else:
                if k > P.key:
                    P = P.rightChild
                else:
                    P = P.leftChild
        return 0

    def insert(self, k):
        if self.size == 0:
            P = Node()
            P.key = k
            self.root = P

            pLeft = Node()
            P.leftChild = pLeft
            pLeft.parent = P

            pRight = Node()
            P.rightChild = pRight
            pRight.parent = P

            self.size += 1
            return "Inserted Root"

        P = self.root
        while not self.isLeaf(P):
            if P.key == k:
                return "Key already exists"
            else:
                if k > P.key:
                    P = P.rightChild
                else:
                    P = P.leftChild
        P.key = k

        pLeft = Node()
        P.leftChild = pLeft
        pLeft.parent = P

        pRight = Node()
        P.rightChild = pRight
        pRight.parent = P

        self.size += 1

        return "Inserted"

    def deleteNode(self, P):
        if self.isLeaf(P.leftChild) and self.isLeaf(P.rightChild):
            newP = Node()
            pParent = P.parent
            if pParent.leftChild == P:
                pParent.leftChild = newP
                newP.parent = pParent
            if pParent.rightChild == P:
                pParent.rightChild = newP
                newP.parent = pParent
        if self.isLeaf(P.leftChild) and not self.isLeaf(P.rightChild):
            P = P.rightChild
        if not self.isLeaf(P.leftChild) and not self.isLeaf(P.rightChild):
            temp = P.leftChild
            while (not self.isLeaf(temp.rightChild)):
                temp = temp.rightChild
            P.key = temp.key
            self.deleteNode(temp)

    def delete(self, k):
        P = self.root
        while not self.isLeaf(P):
            if P.key == k:
                self.deleteNode(P)
                return 1
            else:
                if k > P.key:
                    P = P.rightChild
                else:
                    P = P.leftChild
        return 0


filepath = "binary-search-tree-in.txt"
outputList = []
t = BST()

fo = open(filepath, 'r')

key = 0
operation = 0

while True:
    operation = int(fo.readline().rstrip())
    if operation == -1:
        break
    key = int(fo.readline().rstrip())
    if operation == 0:
        retVal = t.delete(key)
        outputList.append(retVal)
    if operation == 1:
        retVal = t.insert(key)
        print(retVal)
    if operation == 2:
        retVal = t.contains(key)
        outputList.append(retVal)
fo.close()

fw = open('binary-search-tree-out-python.txt', 'w')
for element in outputList:
    fw.write(str(element) + '\n')
fw.close()
