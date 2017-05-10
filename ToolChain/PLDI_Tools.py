import pyparsing as pp
import sys


def computeExpressionAndAssignValue(var, expr):
    operators = ['+', '-', '*', '/']
    i = 0
    for term in expr:
        if term not in operators:
            if not term.isdigit():
                if varDeclared(term):
                    if getVariableValue(term) is not None:
                        expr[i] = getVariableValue(term)
                    else:
                        print(term + " not initialised!")
                        return False
                else:
                    print(term + " not declared!")
                    return False
        i += 1
    expression = ''.join(expr)
    val = eval(expression)
    ret = assignVariableValue(var, str(val))
    return ret


def varDeclared(var):
    if var in [variableInfo[1] for variableInfo in allVarInfo]:
        return True
    else:
        return False


def getVariableValue(var):
    for variableInfo in allVarInfo:
        if var == variableInfo[1]:
            return variableInfo[2]


def assignVariableValue(var, val):
    if not val.isdigit():
        if varDeclared(val):
            if getVariableValue(val) is not None:
                val = getVariableValue(val)
            else:
                print(val + " not initialised!")
                return False
        else:
            print(val + " not declared!")
            return False
    for variableInfo in allVarInfo:
        if var == variableInfo[1]:
            variableInfo[2] = val
            return True
    print(var + " not declared!")
    return False


def declareVariables(typeOfVar, listOfVar):
    global allVarInfo
    statement = ''.join(listOfVar)
    variables = statement.split(",")
    for v in variables:
        if varDeclared(v):
            print(v + " already declared!")
            return False
        else:
            allVarInfo.append([typeOfVar, v, None])
    return True


def declareAndInitializeVariables(typeOfVar, listOfVar):
    global allVarInfo
    statement = ''.join(listOfVar)
    assignments = statement.split(",")
    for a in assignments:
        var = a.split("=")[0]
        val = a.split("=")[1]
        if varDeclared(val):
            print(var + " already declared!")
            return False
        else:
            if not val.isdigit():
                val = getVariableValue(val)
            allVarInfo.append([typeOfVar, var, val])
    return True


def checkEquation(contract):
    global allVarInfo
    try:
        result = EQUATION.parseString(contract)
        lhs = str(result.LHS)
        rhs = str(result.RHS)
        cmp = str(result.COMPARISON)

        if not lhs.isdigit():
            if varDeclared(lhs):
                if getVariableValue(lhs) is not None:
                    lhs = getVariableValue(lhs)
                else:
                    return False
            else:
                return False

        if not rhs.isdigit():
            if varDeclared(rhs):
                if getVariableValue(rhs) is not None:
                    rhs = getVariableValue(rhs)
                else:
                    return False
            else:
                return False

        eqn = lhs + cmp + rhs
        return eval(eqn)

    except:
        print("Error in contract")


# ------------------------GRAMMAR FOR THE PROGRAMMING LANGUAGE-------------------------------
OPERATOR = pp.oneOf('+ - * /').setResultsName("OPERATOR")
NUMBER = pp.Regex('[0-9]+').setResultsName("NUMBER")
VARNAME = pp.Regex('[a-zA-Z][a-zA-Z0-9]*').setResultsName("VARNAME")
LVARNAME = pp.Regex('[a-zA-Z][a-zA-Z0-9]*').setResultsName("LVARNAME")
VARTYPE = (pp.Literal('int') | pp.Literal('float')).setResultsName("VARTYPE")
VARLIST = (VARNAME + pp.ZeroOrMore(',' + VARNAME)).setResultsName("VARLIST")
TERM = (VARNAME | NUMBER).setResultsName("TERM")
VARLIST_INITIALISED = (VARNAME + '=' + TERM + pp.ZeroOrMore(',' + VARNAME + '=' + TERM)).setResultsName(
    "VARLIST_INITIALISED")
EXPRESSION = (TERM + OPERATOR + TERM + pp.ZeroOrMore(OPERATOR + TERM)).setResultsName("EXPRESSION")
STMT_DECLARATION_WITHOUT_INITIALISATION = VARTYPE + pp.White(' ') + VARLIST + ';'
STMT_DECLARATION_WITH_INITIALISATION = VARTYPE + pp.White(' ') + VARLIST_INITIALISED + ';'
STMT_ASSIGNMENT = LVARNAME + '=' + TERM + ';'
STMT_ASSIGNMENT_OPERATION = LVARNAME + '=' + EXPRESSION + ';'
STMT = STMT_DECLARATION_WITHOUT_INITIALISATION | STMT_DECLARATION_WITH_INITIALISATION | STMT_ASSIGNMENT | STMT_ASSIGNMENT_OPERATION
COMPARISON = pp.oneOf('< > == <= >=').setResultsName("COMPARISON")
LHS = TERM.setResultsName("LHS")
RHS = TERM.setResultsName("RHS")
EQUATION = LHS + COMPARISON + RHS
IF = pp.Literal('if')
OPEN_IF_BRACKET = pp.Literal('{')
CLOSE_IF_BRACKET = pp.Literal('}')
OPEN_IF_STMT = IF + pp.White(' ') + EQUATION + pp.White(' ') + OPEN_IF_BRACKET
CLOSE_IF_STM = CLOSE_IF_BRACKET | STMT + CLOSE_IF_BRACKET
#  ------------------------------------------------------------------------------------------


programFileName = sys.argv[1]

lines = [line.rstrip('\n') for line in open(programFileName)]

start = 0
contract = None
if lines[0] != "begin:":
    contract = lines[0]
    start = 1

numOfLines = len(lines)

allVarInfo = []

error = False

for i in range(start, numOfLines):
    try:
        if i == start:
            if lines[i] != "begin:":
                print("Error in line " + str(i + 1) + ". Program should start with 'begin:'")
                error = True
                break
        elif i == numOfLines - 1:
            if lines[i] != "end":
                print("Error in line " + str(i + 1) + ". Program should end with 'end'")
        else:
            STMT.parseString(lines[i], parseAll=True)
            try:
                result = STMT_DECLARATION_WITHOUT_INITIALISATION.parseString(lines[i], parseAll=True)
                retVal = declareVariables(result.VARTYPE, result.VARLIST)
                if not retVal:
                    print("Semantic error in line " + str(i + 1) + ".")
                    error = True
                    break
            except:
                try:
                    result = STMT_DECLARATION_WITH_INITIALISATION.parseString(lines[i], parseAll=True)
                    retVal = declareAndInitializeVariables(result.VARTYPE, result.VARLIST_INITIALISED)
                    if not retVal:
                        print("Semantic error in line " + str(i + 1) + ".")
                        error = True
                        break
                except:
                    try:
                        result = STMT_ASSIGNMENT.parseString(lines[i], parseAll=True)
                        retVal = assignVariableValue(result.LVARNAME, result.TERM)
                        if not retVal:
                            print("Semantic error in line " + str(i + 1) + ".")
                            error = True
                            break
                    except:
                        try:
                            result = STMT_ASSIGNMENT_OPERATION.parseString(lines[i], parseAll=True)
                            retVal = computeExpressionAndAssignValue(result.LVARNAME, result.EXPRESSION)
                            if not retVal:
                                print("Semantic error in line " + str(i + 1) + ".")
                                error = True
                                break
                        except:
                            print("Error in line " + str(i + 1))
                            error = True
                            break
    except:
        try:
            result = OPEN_IF_STMT.parseString(lines[i], parseAll=True)
            eqn = result.LHS + result.COMPARISON + result.RHS
            retVal = checkEquation(eqn)
        except:
            try:
                result = CLOSE_IF_STM.parseString(lines[i], parseAll=True)
            except:
                print("Syntax error in line " + str(i + 1))
                error = True
                break

if not error:
    print("Variables and their info: " + str(allVarInfo))
    if lines[0] != "begin:":
        contract = lines[0]
        result = checkEquation(contract)
        print("Contract: " + str(result))
