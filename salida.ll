declare i32 @printf(i8*, ...)
@.str = private constant [4 x i8] c"%d\0A\00"
define i32 @main() {
entry:
  %i = alloca i32
  %t0 = alloca i1
  %t1 = alloca i1
  store i32 0, i32* %i
  br label %L0
L0:
  %tmp0 = load i32, i32* %i
  %tmp1 = icmp slt i32 %tmp0, 5
  store i1 %tmp1, i1* %t0
  %tmp2 = load i1, i1* %t0
  br i1 %tmp2, label %continuetmp3, label %L1
continuetmp3:
  %tmp4 = load i32, i32* %i
  %tmp5 = add i32 %tmp4, 1
  store i32 %tmp5, i32* %t1
  %tmp6 = load i32, i32* %t1
  store i32 %tmp6, i32* %i
  %tmp7 = load i32, i32* %i
  call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.str, i32 0, i32 0), i32 %tmp7)
  br label %L0
L1:
  ret i32 0
}
