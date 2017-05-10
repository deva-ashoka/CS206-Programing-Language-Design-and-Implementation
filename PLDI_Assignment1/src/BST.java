import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

class Node {
    public int key;//This denotes the key stored at the node
    public Node leftChild;//This is a reference to the left child in the binary tree
    public Node rightChild;//This is a reference to the right child in the binary tree
    public Node parent;//This is a reference to the parent of the node in the binary tree.

    public Node() {
        parent = leftChild = rightChild = null;
    }
}


public class BST {
    public Node root;
    public int size;

    public BST() {

        size = 0;
        //root = null;
    }

    public boolean isLeaf(Node N) {
        if (N.leftChild == null && N.rightChild == null) return (true);
        else return (false);
    }


    public int contains(int k) {
        Node P = root;
        while (!isLeaf(P)) {
            if (P.key == k) {
                return 1;
            } else {
                if (k > P.key) {
                    P = P.rightChild;
                } else {
                    P = P.leftChild;
                }
            }
        }
        return 0;
    }

    public String insert(int k) {
        if (size == 0) {
            Node P = new Node();
            P.key = k;
            root = P;

            Node pLeft = new Node();
            P.leftChild = pLeft;
            pLeft.parent = P;

            Node pRight = new Node();
            P.rightChild = pRight;
            pRight.parent = P;

            size++;
            return "Inserted root";
        }
        Node P = root;
        while (!isLeaf(P)) {
            if (k == P.key) {
                return "Integer key already exists";
            }
            if (k > P.key) {
                P = P.rightChild;
            } else {
                P = P.leftChild;
            }
        }
        P.key = k;

        Node pLeft = new Node();
        P.leftChild = pLeft;
        pLeft.parent = P;

        Node pRight = new Node();
        P.rightChild = pRight;
        pRight.parent = P;
        size++;
        return "Inserted";
    }

    public int delete(int k) {
        Node P = root;
        while (!isLeaf(P)) {
            if (P.key == k) {
                deleteNode(P);
                return 1;
            } else {
                if (k > P.key) {
                    P = P.rightChild;
                } else {
                    P = P.leftChild;
                }
            }
        }
        return 0;
    }

    public void deleteNode(Node P) {
        if (isLeaf(P.leftChild) && isLeaf(P.rightChild)) {
            Node newP = new Node();
            Node pParent = P.parent;
            if (pParent.leftChild == P) {
                pParent.leftChild = newP;
                newP.parent = pParent;
            }
            if (pParent.rightChild == P) {
                pParent.rightChild = newP;
                newP.parent = pParent;
            }
        }
        if (isLeaf(P.leftChild) && !isLeaf(P.rightChild)) {
            P = P.rightChild;
        }
        if (!isLeaf(P.leftChild) && !isLeaf(P.rightChild)) {
            Node temp = P.leftChild;
            while (!isLeaf(temp.rightChild)) {
                temp = temp.rightChild;
            }
            P.key = temp.key;
            deleteNode(temp);
        }
    }

    public static void main(String[] args) {


        String inputFilePath = "binary-search-tree-in.txt";

        BST bst = new BST();

        List<Integer> outputList = new ArrayList<>();

        try {

            FileReader inputFile = new FileReader(inputFilePath);
            BufferedReader inputBr = new BufferedReader(inputFile);

            int key;
            int operation;

            while (true) {
                operation = Integer.parseInt(inputBr.readLine());
                if (operation == -1) {
                    break;
                }
                key = Integer.parseInt(inputBr.readLine());
                if(operation == 0){
                    int retVal = bst.delete(key);
                    outputList.add(retVal);
                }
                if(operation == 1){
                    String retVal = bst.insert(key);
                    System.out.println(retVal);
                }
                if(operation == 2){
                    int retVal = bst.contains(key);
                    outputList.add(retVal);
                }
            }

            PrintWriter writer = new PrintWriter("binary-search-tree-out-java.txt", "UTF-8");
            int size = outputList.size();
            for (int i = 0; i < size; i++) {
                writer.println(outputList.get(i));
            }
            writer.close();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

