inputFileName = "int-list-in.txt"

with open(inputFileName) as f:
    # number of numbers
    n = int(f.readline())

    content = f.readlines()
# you may also want to remove whitespace characters like `\n` at the end of each line
content = [int(x.strip()) for x in content]

maximum = content[0]

for i in range(1, n):
    if (maximum < content[i]):
        maximum = content[i]

outputFileName = "maximum-out-python.txt"

outputFile = open(outputFileName, 'w')
outputFile.write(str(maximum))
