def loadGraph():
    inputFileName = "breadth-first-search-in.txt"
    with open(inputFileName) as f:
        content = f.read().splitlines()  # without \n

    # print(content.index(content[2]))
    totalNodes = int(content[0])
    graphMatrix = [[0 for x in range(totalNodes)] for y in range(totalNodes)]  # initializeng with 0

    for i in range(1, len(content) + 2, 2):
        if (int(content[i]) == -1):
            startNode = content[i + 1]
            endNode = content[i + 2]
            # printGraph(graphMatrix)
            if (startNode == endNode):
                savePathToFile([startNode])
            else:
                bfs(graphMatrix, startNode, endNode, totalNodes)
            break
        else:
            graphMatrix[int(content[i])][int(content[i + 1])] = 1
            graphMatrix[int(content[i + 1])][int(content[i])] = 1


def printGraph(graph):
    for row in range(0, len(graph)):
        print("\n")
        for col in range(0, len(graph)):
            print(graph[row][col])


def savePathToFile(path):
    file = open('breadth-first-search-out-python.txt', 'w')
    path.reverse()
    for node in range(0, len(path)):
        file.write(str(path[node]))
        file.write("\n")
        # to print on console
        print(path[node])
    file.close()


def bfs(graph, startNode, endNode, totalNodes):
    currentLayer = []
    outputVertices = []
    parentChildVertices = {}  # dictionary
    path = []

    currentLayer.append(int(startNode))
    outputVertices.append(int(startNode))

    while (True):
        nextLayer = []
        for nodes in currentLayer:
            node = int(nodes)

            for j in range(0, int(totalNodes)):
                if (graph[node][j] == 1 and (j) not in outputVertices):
                    nextLayer.append(j)
                    outputVertices.append(j)
                    parentChildVertices[j] = node  # mapping every element to its parent

        if (len(nextLayer) == 0):
            # print(currentLayer)
            break
        else:
            currentLayer = nextLayer

    # print(outputVertices)
    if (int(endNode) not in outputVertices):  # if there is no path
        # print("-1")
        path.append(-1)
    else:
        # print(endNode)
        # print(parentChildVertices[int(endNode)])
        path.append(endNode)
        path.append(parentChildVertices[int(endNode)])
        key = int(parentChildVertices[int(endNode)])

        # print("key : ",key)
        # print("key : ",key)
        while (True):

            if (parentChildVertices[key] != int(startNode)):
                # print(parentChildVertices[key])
                path.append(parentChildVertices[key])
                key = int(parentChildVertices[key])
            else:
                break
        # print(startNode)
        path.append(startNode)
        savePathToFile(path)


loadGraph()
