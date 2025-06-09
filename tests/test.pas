program PruebaSemantica;

var
  x: integer;
  y: boolean;

function Sumar: integer;
begin
  Sumar := 10;
end;

begin
  x := 5;
  y := true;
  x := Sumar();  { ✅ Esta llamada pasa, aunque no verifiques parámetros aún }
end.
