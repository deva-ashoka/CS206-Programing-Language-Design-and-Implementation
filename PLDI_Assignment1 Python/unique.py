inputFileName = "int-list-in.txt"

with open(inputFileName) as f:
    # number of numbers
    n = int(f.readline())

    content = f.readlines()
# you may also want to remove whitespace characters like `\n` at the end of each line
content = [int(x.strip()) for x in content]

uniqueElements = [content[0]]

for num in content:
    if (num not in uniqueElements):
        uniqueElements.append(num)

outputFileName = "unique-out-python.txt"

outputFile = open(outputFileName, 'w')
for num in uniqueElements:
    outputFile.write(str(num) + "\n")
