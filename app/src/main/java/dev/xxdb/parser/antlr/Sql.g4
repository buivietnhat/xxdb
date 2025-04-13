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
    : SELECT columnList FROM tableName whereClause?
    ;

insertStatement
    : INSERT INTO tableName '(' columnList ')' VALUES '(' valueList ')'
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

valueList
    : value (',' value)*
    ;

whereClause
    : WHERE condition
    ;

condition
    : columnName operator value
    ;

operator
    : '='
    | '>'
    | '<'
    ;

dataType
    : INT_TYPE
    | VARCHAR_TYPE
    ;

columnName
    : IDENTIFIER
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

INT_TYPE  : 'INT';
VARCHAR_TYPE : 'VARCHAR';

IDENTIFIER: [a-zA-Z_][a-zA-Z0-9_]*;
NUMBER    : [0-9]+;
STRING    : '\'' .*? '\'';

WS : [ \t\r\n]+ -> skip;
