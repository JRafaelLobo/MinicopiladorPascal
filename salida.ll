; Código LLVM generado
declare i32 @printf(i8*, ...)
@.str = private constant [4 x i8] c"%d\0A\00"
define i32 @main() {
entry:
  %x = alloca i32
  %y = alloca i32
  %t0 = alloca i1
  %t1 = alloca i1
  %z = alloca i32
  store i32 5, i32* %x
  store i32 10, i32* %y
  %tmp0 = load i32, i32* %y
  %tmp1 = mul i32 %tmp0, 2
  store i32 %tmp1, i32* %t0
  %tmp2 = load i32, i32* %x
  %tmp3 = load i32, i32* %t0
  %tmp4 = add i32 %tmp2, %tmp3
  store i32 %tmp4, i32* %t1
  %tmp5 = load i32, i32* %t1
  store i32 %tmp5, i32* %z
  %tmp6 = load i32, i32* %z
  call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.str, i32 0, i32 0), i32 %tmp6)
  ret i32 0
}
