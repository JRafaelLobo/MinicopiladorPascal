program StrongTyping;

var
  a: integer;
  b: string;
  c: boolean;
  d: char;

begin
  a := 5;         { Ok }
  b := 'Hola';    { Ok️ }
  c := true;      { Ok }
  d := 'x';       { Ok }

  a := 'Hola';    { string → integer }
  b := 10;        { integer → string }
  c := 'true';    { string → boolean }
  d := 100;       { integer → char }
end.
