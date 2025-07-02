declare i32 @printf(i8*, ...)
@.str = private constant [4 x i8] c"%d\0A\00"
define i32 @main() {
entry:
  %j = alloca i32
  %t0 = alloca i1
  %t1 = alloca i1
  store i32 0, i32* %j
  br label %L0
L0:
  %tmp0 = load i32, i32* %j
  %tmp1 = add i32 %tmp0, 2
  store i32 %tmp1, i32* %t0
  %tmp2 = load i32, i32* %t0
  store i32 %tmp2, i32* %j
  %tmp3 = load i32, i32* %j
  %tmp4 = icmp sge i32 %tmp3, 12
  store i1 %tmp4, i1* %t1
  %tmp5 = load i1, i1* %t1
  br i1 %tmp5, label %continuetmp6, label %L0
continuetmp6:
  %tmp7 = load i32, i32* %j
  call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.str, i32 0, i32 0), i32 %tmp7)
  ret i32 0
}
