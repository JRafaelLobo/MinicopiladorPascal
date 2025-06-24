program test9;
var
  flag: boolean;

begin
  flag := true;
  if flag and not false then
    flag := false;
end.