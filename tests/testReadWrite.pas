program TestWrite;

var
  a: integer;
  b: string;
  c: boolean;
  d: char;

begin
  a := 10;
  b := 'Hola mundo';
  d := 'x';
  c := true;

  write(a);        { Válido }
  write(b);        { Válido }
  write(d);        { Válido }
  write(c);        { error: tipo boolean no permitido }
  write(x);        { error: variable no declarada }

end.
