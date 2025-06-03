program ScopeVisibility;

var
  x: integer;

procedure Inner;
var
  x: string;
  y: integer;
begin
  x := 'Hola';    { OK: usa el x del scope local }
  y := 5;         { OK }
  z := 10;        { Error: z no existe en ningún scope }
end;

begin
  x := 3;         { OK: usa el x global }
  y := 2;         { Error: y no está visible aquí }
end.
