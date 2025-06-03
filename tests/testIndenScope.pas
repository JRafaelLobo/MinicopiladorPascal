program Redecl;

var
  x: integer;
  y: string;
  x: boolean;    { Error: redeclaración de 'x' }

procedure Test;
var
  a: integer;
  a: char;       { Error: redeclaración de 'a' }
begin
end;

function Test: integer;  { Error: redeclaración de 'Test' como función }
begin
  Test := 1;
end;

begin
end.
