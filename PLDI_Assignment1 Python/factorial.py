def getFactorial(n):
    if (n == 0 or n == 1):
        return 1
    else:
        return (n * getFactorial(n - 1))


fname = "factorial-in.txt"

with open(fname) as f:
    # number of numbers
    n = int(f.readline())

fact = 1
# for i in range(1,n+1):
#    fact = fact * i
fact = getFactorial(n)
outputFileName = "factorial-out-python.txt"

outputFile = open(outputFileName, 'w')
outputFile.write(str(fact))
