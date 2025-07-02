program test5;
var
  j: integer;
begin
  j := 0;
  repeat
    j := j + 2;
    write(j);
  until j >= 24;
end.
