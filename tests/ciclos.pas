program ciclos;
{declaramos las variables}
var
  lenght, i, j, x, y, cont: Integer;
  arreglo: Array[1..50] of Integer;
  arreglo_bidi: Array[1..10,1..5] of Integer;
  s: String;

Function f: Integer;
Begin
  f := 3;
End;

begin
 {iniciamos las variables}
 lenght := 50;
 x := 10;
 y := 5;
 cont := 1;

 {llenamos el arreglo bidimensional}
 For i := x Downto 1 Do
 Begin
   For j := y Downto 1 Do
   Begin
     arreglo_bidi[i, j] := (cont + (x - y) + 5) mod 15;
   End;
 End;

 {reiniciamos el contador}
 cont := 1;

 {llenamos el arreglo unidimensional}
 For i := 1 To x Do
  For j := 1 To y Do
  Begin
    arreglo[cont] := arreglo_bidi[i, j];
    cont := cont + 1;
  End;

 {reiniciamos el contador}
 cont := 1;

 {imprimimos el arreglo unidimensional}
 While (cont <> lenght+1) Do
 Begin
   write('arreglo[', cont, '] = ');
   writeln(arreglo[cont]);
   cont := cont + 1;  { Importante para evitar bucles infinitos }
 End;

 writeln('Gracias por usar Pascal!');
end.
