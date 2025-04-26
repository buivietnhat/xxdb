grammar Sql;

sql
    : statement';'+ EOF
    ;

statement
    : selectStatement
    | insertStatement
    | updateStatement
    | createTableStatement
    ;

selectStatement
    : SELECT columnList FROM tableName joinClause? whereClause? limitClause?
    ;

joinClause
    : JOIN tableName ON condition
    ;

limitClause
    : LIMIT NUMBER
    ;

insertStatement
    : INSERT INTO tableName '(' columnList ')' VALUES valueSetList
    ;

updateStatement
    : UPDATE tableName SET assignmentList whereClause?
    ;

createTableStatement
    : CREATE TABLE tableName '(' columnDefinitionList ')'
    ;

columnDefinitionList
    : columnDefinition (',' columnDefinition)*
    ;

columnDefinition
    : columnName dataType
    ;

assignmentList
    : assignment (',' assignment)*
    ;

assignment
    : columnName '=' value
    ;

columnList
    : columnName (',' columnName)*
    ;

valueSetList
    : '(' valueList ')' (',' '(' valueList ')')*
    ;

valueList
    : value (',' value)*
    ;

whereClause
    : WHERE condition
    ;

condition
    : condition AND condition          # AndCondition
    | condition OR condition           # OrCondition
    | columnName operator value        # SimpleValueCondition
    | columnName operator columnName   # SimpleColumnCondition
    ;

operator
    : '='
    | '>'
    | '<'
    | '<='
    | '>='
    | '!='
    ;

dataType
    : INT_TYPE
    | VARCHAR_TYPE
    ;

columnName
    : IDENTIFIER ('.' IDENTIFIER)?
    ;

tableName
    : IDENTIFIER
    ;

value
    : STRING
    | NUMBER
    ;

SELECT : 'SELECT';
FROM   : 'FROM';
WHERE  : 'WHERE';
INSERT : 'INSERT';
INTO   : 'INTO';
VALUES : 'VALUES';
UPDATE : 'UPDATE';
SET    : 'SET';
CREATE : 'CREATE';
TABLE  : 'TABLE';
JOIN   : 'JOIN';
ON     : 'ON';
LIMIT  : 'LIMIT';
AND    : 'AND';
OR     : 'OR';

INT_TYPE  : 'INT';
VARCHAR_TYPE : 'VARCHAR';

IDENTIFIER: [a-zA-Z_][a-zA-Z0-9_]*;
NUMBER    : [0-9]+;
STRING    : '\'' .*? '\'';

WS : [ \t\r\n]+ -> skip;
