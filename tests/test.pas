PROGRAM RecursiveExample;
VAR
    num, result: INTEGER;

FUNCTION Factorial(n: INTEGER): INTEGER;
BEGIN
    IF n = 0 THEN
        Factorial := 1
    ELSE
        Factorial := n * Factorial(n - 1);
END;

BEGIN
    num := 5;
    result := Factorial(num);
    WRITE('El factorial de ', num, ' es ', result);
END.

