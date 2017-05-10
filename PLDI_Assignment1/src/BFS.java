/*
Name: Deva Surya Vivek
CS301: Algorithms and Data Structures
Programming Assignment 3
*/

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BFS {

    public static int[] breadthFirstSearch(int numberOfVertices, int adjacencyMatrix[][], int startVertex, int endVertex) {

        List<Integer> reachableVertices = new ArrayList<>(); //all the reachable vertices after BFS will be added to this list

        boolean visitedVertex[] = new boolean[numberOfVertices]; //array containing visited/not visited status of vertices
        int[] parentVertex = new int[numberOfVertices];
        for (int i = 0; i < numberOfVertices; i++) {
            parentVertex[i] = -1;
        }

        //intializing all the vertices to not visited
        for (int i = 0; i < numberOfVertices; i++) {
            visitedVertex[i] = false;
        }

        //list to store the vertices to be explored
        List<Integer> list = new ArrayList<>();

        list.add(startVertex);
        reachableVertices.add(startVertex);
        visitedVertex[startVertex] = true;


        while (list.size() != 0) {

            boolean stop = false;

            int vertex = list.get(0);
            list.remove(0);

            for (int i = 0; i < numberOfVertices; i++) {
                if (adjacencyMatrix[vertex][i] == 1) {
                    if (!visitedVertex[i]) {
                        list.add(i);
                        visitedVertex[i] = true;
                        reachableVertices.add(i);
                        parentVertex[i] = vertex;
                        if (i == endVertex) {
                            stop = true;
                            break;
                        }
                    }
                }
            }
            if (stop) {
                break;
            }
        }
        return parentVertex;
    }

    public static void main(String args[]) {
        try {
            BufferedReader BR = new BufferedReader(new FileReader("breadth-first-search-in.txt"));

            //reading the first line: number of vertices
            String line = BR.readLine();
            int numberOfVertices = Integer.parseInt(line);

            //creating the required matrix
            int adjacencyMatrix[][] = new int[numberOfVertices][numberOfVertices];

            for (int i = 0; i < numberOfVertices; i++) {
                for (int j = 0; j < numberOfVertices; j++) {
                    adjacencyMatrix[i][j] = 0;
                }
            }

            int vertex1;
            int vertex2;

            while (true) {
                vertex1 = Integer.parseInt(BR.readLine());
                if (vertex1 == -1) {
                    break;
                }
                vertex2 = Integer.parseInt(BR.readLine());
                adjacencyMatrix[vertex1][vertex2] = 1;
                adjacencyMatrix[vertex2][vertex1] = 1;

            }

            //reading the source vertex and goal vertex from file
            line = BR.readLine();
            int startVertex = Integer.parseInt(line);
            line = BR.readLine();
            int endVertex = Integer.parseInt(line);

            BR.close();


            int[] parentVertex = breadthFirstSearch(numberOfVertices, adjacencyMatrix, startVertex, endVertex);

            if(parentVertex[endVertex] != -1) {


                List<Integer> path = new ArrayList<>();

                path.add(endVertex);
                int vertexPointer = parentVertex[endVertex];
                while (vertexPointer != startVertex) {
                    path.add(vertexPointer);
                    vertexPointer = parentVertex[vertexPointer];
                }
                path.add(startVertex);
                Collections.reverse(path);

                int size = path.size();

                PrintWriter writer = new PrintWriter("breadth-first-search-out-java.txt", "UTF-8");
                for (int i = 0; i < size; i++) {
                    writer.println(path.get(i));
                }
                writer.close();
            } else {
                PrintWriter writer = new PrintWriter("breadth-first-search-out-java.txt", "UTF-8");
                writer.println("-1");
                writer.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
