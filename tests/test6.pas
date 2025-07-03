program test6;
var
  r: integer;

function mul(x, y: integer): integer;
begin
  mul := x * y;
end;

begin
  r := mul(3, 4);
  write(r);
end.