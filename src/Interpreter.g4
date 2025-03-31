grammar Interpreter;

options { caseInsensitive = true; }

// --- Reglas del parser ---
program : PROGRAM ID ';' block '.' ;

block : declarations? BEGIN statements? END ; // <- Cambio importante aquí

declarations : VAR_KW varDeclList? (functionDecl | procedureDecl)* ;

varDeclList : varDecl (';' varDecl)* ';'? ;

varDecl : ID (',' ID)* ':' type ;

type : INTEGER | CHAR | STRING | BOOLEAN | ARRAY '[' indexList ']' OF type ;

indexList : INT_CONST '..' INT_CONST (',' INT_CONST '..' INT_CONST)* ;

statements : statement (';' statement)* ';'? ;

statement
    : assignment
    | ifStatement
    | whileStatement
    | forStatement
    | repeatStatement
    | readStatement
    | writeStatement
    | functionCall
    | block ;

assignment : (ID | arrayAccess) ASSIGN expression ;

ifStatement : IF expression THEN statement (ELSE statement)? ;

whileStatement : WHILE expression DO statement ;

forStatement : FOR ID ASSIGN expression (TO | DOWNTO) expression DO (block | statement) ;

repeatStatement : REPEAT statements UNTIL expression ;

readStatement : READ '(' ID ')' ;

writeStatement : WRITE '(' (expression (',' expression)*)? ')' ;

functionCall : ID '(' (expression (',' expression)*)? ')' ;

arrayAccess : ID '[' expression (',' expression)* ']' ;

expression : logicalExpr ;

logicalExpr : comparisonExpr ((AND | OR) comparisonExpr)* ;

comparisonExpr : arithmeticExpr ((EQUAL | NOTEQUAL | LT | LE | GT | GE) arithmeticExpr)? ;

arithmeticExpr : term ((PLUS | MINUS) term)* ;

term : factor ((MULT | DIV_OP | DIV | MOD) factor)* ;

factor : (PLUS | MINUS)? (ID | INT_CONST | CHAR_CONST | STRING_CONST | arrayAccess | functionCall | '(' expression ')' | NOT factor) ;

// --- Reglas para funciones y procedimientos ---
functionDecl : FUNCTION ID ':' type ';' BEGIN statements? END ';' ;

procedureDecl : PROCEDURE ID ('(' parameters? ')')? ';' block ';' ;

parameters : paramList ;

paramList : param (',' param)* ;

param : (VAR_KW)? ID (',' ID)* ':' type ;

// --- Reglas del lexer (tokens) ---
PROGRAM   : 'PROGRAM';
VAR_KW    : 'VAR';
BEGIN     : 'BEGIN';
END       : 'END';
INTEGER   : 'INTEGER';
CHAR      : 'CHAR';
STRING    : 'STRING';
BOOLEAN   : 'BOOLEAN';
ARRAY     : 'ARRAY';
OF        : 'OF';
FUNCTION  : 'FUNCTION';
PROCEDURE : 'PROCEDURE';
IF        : 'IF';
THEN      : 'THEN';
ELSE      : 'ELSE';
WHILE     : 'WHILE';
DO        : 'DO';
FOR       : 'FOR';
TO        : 'TO';
DOWNTO    : 'DOWNTO';
REPEAT    : 'REPEAT';
UNTIL     : 'UNTIL';
READ      : 'READ';
WRITE     : 'WRITE';
MOD       : 'MOD';
DIV       : 'DIV';
AND       : 'AND';
OR        : 'OR';
NOT       : 'NOT';
MAIN      : 'MAIN';

// Operadores y símbolos
ASSIGN    : ':=';
PLUS      : '+';
MINUS     : '-';
MULT      : '*';
DIV_OP    : '/';
EQUAL     : '=';
NOTEQUAL  : '<>';
LT        : '<';
LE        : '<=';
GT        : '>';
GE        : '>=';
LPAREN    : '(';
RPAREN    : ')';
LBRACKET  : '[';
RBRACKET  : ']';
COLON     : ':';
SEMICOLON : ';';
COMMA     : ',';
DOT       : '.';

// Identificadores
ID : [a-zA-Z_][a-zA-Z0-9_]* ;

// Constantes
INT_CONST  : [0-9]+ ;
CHAR_CONST : '\'' . '\'' ;
STRING_CONST : '\'' ( ~'\'' | '\'\'' )* '\'' ;

// Comentarios
COMMENT : '{' .*? '}' -> skip ;

// Espacios en blanco y nuevas líneas
WS : [ \t\r\n]+ -> skip ;

// Errores léxicos
ERROR : . { System.err.println("Error léxico en línea " + getLine() +
            ", columna " + getCharPositionInLine() + ": carácter inesperado '" + getText() + "'"); } -> skip ;
