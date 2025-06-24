program test8;
var
  result: integer;

function inc(x: integer): integer;
begin
  inc := x + 1;
end;

function double(x: integer): integer;
begin
  double := x * 2;
end;

begin
  result := double(inc(3));
end.