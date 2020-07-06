Lua记录
---

简单的脚本语言

#### 交互式编程

Lua 交互式编程模式可以通过命令 lua -i 或 lua 来启用

#### 脚本式编程

##### 代码保存到 lua 结尾的文件中

```shell
lua hello.lua
```

##### shell脚本执行

文件头指定lua解释器

```shell
#!/usr/local/bin/lua
print("Hello World！")
```

#### 注释

##### 单行注释

两个减号是单行注释

```lua
--
```

##### 多行注释

```lua
--[[
--]]
```

## 关键词

以下列出了 Lua 的保留关键字。保留关键字不能作为常量或变量或其他用户自定义标示符：

| and      | break | do    | else   |
| -------- | ----- | ----- | ------ |
| elseif   | end   | false | for    |
| function | if    | in    | local  |
| nil      | not   | or    | repeat |
| return   | then  | true  | until  |
| while    | goto  |       |        |

一般约定，以下划线开头连接一串大写字母的名字（比如 _VERSION）被保留用于 Lua 内部全局变量。

##### 全局变量

在默认情况下，变量总是认为是全局的。

全局变量不需要声明，给一个变量赋值后即创建了这个全局变量，访问一个没有初始化的全局变量也不会出错，只不过得到的结果是：`nil`

如果你想删除一个全局变量，只需要将变量赋值为`nil`。

#### 数据类型

| 数据类型 | 描述                                                         |
| :------- | :----------------------------------------------------------- |
| nil      | 这个最简单，只有值nil属于该类，表示一个无效值（在条件表达式中相当于false）。 |
| boolean  | 包含两个值：false和true。                                    |
| number   | 表示双精度类型的实浮点数                                     |
| string   | 字符串由一对双引号或单引号来表示                             |
| function | 由 C 或 Lua 编写的函数                                       |
| userdata | 表示任意存储在变量中的C数据结构                              |
| thread   | 表示执行的独立线路，用于执行协同程序                         |
| table    | Lua 中的表（table）其实是一个"关联数组"（associative arrays），数组的索引可以是数字、字符串或表类型。在 Lua 里，table 的创建是通过"构造表达式"来完成，最简单构造表达式是{}，用来创建一个空表。 |

##### 判断变量或者值的类型

使用 `type()` 函数变量或者值的类型,该函数的返回结果是个`string`

##### nil（空）

nil 类型表示一种没有任何有效值，它只有一个值 -- nil，例如打印一个没有赋值的变量，便会输出一个 nil 

##### boolean

Lua 把 false 和 nil 看作是 false，其他的都为 true，数字 0 也是 true

##### number

Lua 默认只有一种 number 类型 -- double（双精度）类型（默认类型可以修改 luaconf.h 里的定义

##### string

字符串由一对双引号`""`或单引号`''`来表示

用两个方括号 `[[]]` 表示原始字符串

对字符串使用算数操作符时，Lua会把`string`转为`number`,失败时会报错

```shell
> print("2" + 6)
8.0
> print("2" + "6")
8.0
> print("2 + 6")
2 + 6
> print("-2e2" * "6")
-1200.0
> print("error" + 1)
stdin:1: attempt to perform arithmetic on a string value
stack traceback:
        stdin:1: in main chunk
        [C]: in ?
>
```

字符串连接使用两个点`..`

```shell
> print("a" .. 'b')
ab
> print(157 .. 428)
157428
```

使用 # 来计算字符串的长度，放在字符串前面

```lua
print(#"hello")
lenth = "hello"
print(#lenth)
```

##### table

在 Lua 里，table 的创建是通过"构造表达式"来完成，最简单构造表达式是{}，用来创建一个空表。也可以在表里添加一些数据，直接初始化表

```lua
-- 创建一个空的 table
local tbl1 = {}
 
-- 直接初始表
local tbl2 = {"apple", "pear", "orange", "grape"}
```

Lua 中的表（table）其实是一个"关联数组"（associative arrays），数组的索引可以是数字或者是字符串。

```lua
a = {}
a["key"] = "value"
key = 10
a[key] = 22
a[key] = a[key] + 11
for k, v in pairs(a) do
    print(k .. " : " .. v)
end
```

 Lua 里表的默认初始索引一般以 1 开始。

table 不会固定长度大小，有新数据添加时 table 长度会自动增长，没初始的 table 都是 nil。

##### function

在 Lua 中，函数是被看作是"第一类值（First-Class Value）"，函数可以存在变量里

function 可以以匿名函数（anonymous function）的方式通过参数传递

###### 声明和调用

  lua中函数作为表中元素时有三种定义方式与两种调用方式
###### 定义方式

``` lua
-- 
tab.func=function ( 参数)
  -- body
end
-- 
function tab.func( 参数)
  -- body
end
-- 
function tab:func( 参数)
  -- body
end
```
两种定义方式与非表元素的函数的定义一样。方式③采用`:`来定义，实际上隐藏了一个形参的声明，这个形参会截获调用函数时的第一个实参并把它赋值给self。

###### 调用方式

```lua
tab.func(参数)
tab:func(参数)
```

1调用方式与非表元素的函数的调用一样。

2采用`:`来调用函数，实际上隐式的把tab自己当作第一个实参传递，即**tab:func(参数)**相当于**tab.func(tab,参数)**。

##### thread

在 Lua 里，最主要的线程是协同程序（coroutine）。它跟线程（thread）差不多，拥有自己独立的栈、局部变量和指令指针，可以跟其他协同程序共享全局变量和其他大部分东西。

线程跟协程的区别：线程可以同时多个运行，而协程任意时刻只能运行一个，并且处于运行状态的协程只有被挂起（suspend）时才会暂停。

##### userdata

userdata 是一种用户自定义数据，用于表示一种由应用程序或 C/C++ 语言库所创建的类型，可以将任意 C/C++ 的任意数据类型的数据（通常是 struct 和 指针）存储到 Lua 变量中调用。

#### 变量

Lua 变量有三种类型：全局变量、局部变量、表中的域。

应该尽可能的使用局部变量，有两个好处：

1. 避免命名冲突。
2. 访问局部变量的速度比全局变量更快。

Lua 中的变量全是全局变量，那怕是语句块或是函数里，除非用 local 显式声明为局部变量。

局部变量的作用域为从声明位置开始到所在语句块结束。

变量的默认值均为 nil。

##### 赋值

###### 多个变量同时赋值

`a,b = 10,2*3`

1. 变量个数 > 值的个数, 按变量个数补足nil
2. 变量个数 < 值的个数 ,多余的值会被忽略

遇到赋值语句Lua会先计算右边所有的值然后再执行赋值操作，所以可以这样交换变量的值：

```lua
j,k =  7,8,9
print(j,k)
j,k = k,j
print(j,k)
```

##### 索引

对 table 的索引使用方括号 []。Lua 也提供了 . 操作。

```lua
t = {i=223}
print(t["i"])
print(t.i)-- 当索引为字符串类型时的一种简化写法
gettable_event(t,i) -- 采用索引访问本质上是一个类似这样的函数调用
```

##### 循环

###### while

和java基本相同，`do`和`end`之间是代码块	

```lua
local whilea = 10
while(whilea<20)
do 
    print("a 的值为：",whilea)
    whilea = whilea+1
end
```

###### for

语法如下，三个参数起始值`start`，结束值`end`，每次的变化量`step`,`step`可以省略，默认为1

```lua
for var = start,end,step do

statement

end
```

实例

```lua
-- 递增 每次增加2
for a =1, 10,2 do
  print(a)
end

-- 递减 每次减少2
for b = 10,4,-2 do
    print(b)
end
```

###### 泛型for

这个类似java的for in.

ipairs是Lua提供的一个迭代器函数，返回两个参数 第一个是table的索引，第二个是table的值

``` lua
tab = {'a','b','c'}
for i, v in ipairs(tab) do
  print(i, v)
end
```

###### repeat...until

和java的do...while一样，先循环。后判断

```lua
r = 0
repeat
    print(r)
    r= r+1
    until(r>10)
```

###### break

和java相同，结束最近一层的循环

```lua
a = 10
while(a<20)
do
    print("a 为：",a)
    a = a+1
    if(a>15)
    then
        break
    end
end
```

###### goto

goto 语句允许将控制流程无条件地转到被标记的语句处。

跳转`goto label`

 声明标签`::label::`

```lua
i=0
::l2:: do
    print(i)
    i=i+1
end
if i>3 then 
    os.exit()
end
goto l2
-- 使用goto可以实现continue
for i=1, 3 do
    if i <= 2 then
        print(i, "yes continue")
        goto continue
    end
    print(i, " no continue")
    ::continue::
    print([[i'm end]])
end
```

#### 流程控制

```lua
if(boolean)
then
	print("if true")
elseif(boolean)
  print("elseif true")
end
```

#### 函数

```lua
optional_function_scope function function_name(arg1,arg2...)
  function_body
  return result_params
end
```

-   **optional_function_scope:** 未设置该参数默认为全局函数,局部函数使用关键字 **local**。
-   **function_name:** 指定函数名称。
-   **argument1, argument2, argument3..., argumentn:** 函数参数，多个参数以逗号隔开，函数也可以不带参数。
-   **function_body:** 函数体，函数中需要执行的代码语句块。
-   **result_params_comma_separated:** 函数返回值，Lua语言函数可以返回多个值，每个值以逗号隔开。

##### 可变参数

Lua 函数可以接受可变数目的参数，和 C 语言类似，在函数参数列表中使用三点 **...** 表示函数有可变的参数。

有时候我们可能需要几个固定参数加上可变参数，固定参数必须放在变长参数之前。

使用`#{...}`可以得到参数的长度

```lua
function average(...)
    result = 0
    local arg={...} -- 获取table
    for i,v in ipairs(arg) do
        result = result+v
    end
    print("all "..#arg.." 个数") --获取参数长度 
    print("all "..#{...}.." 个数") --获取参数长度
    print("all "..select("#",...).." 个数") --获取参数长度
    a,b,c = select(2,...) -- 获取从2开始所有的参数 越界会返回nil
    print(a,b,c)
    return result/#arg
end
print("平均值为",average(12,23,nil,456,567))
```
通常在遍历变长参数的时候只需要使用 **{…}**，然而变长参数可能会包含一些 **nil**，那么就可以用 **select** 函数来访问变长参数了：**select('#', …)** 或者 **select(n, …)**
**select('#', …)** 返回可变参数的长度
**select(n, …)** 用于访问 **n** 到 **select('#',…)** 的参数

#### 运算符

##### 算数运算符

和java相同

| 操作符 | 描述 | 实例               |
| :----- | :--- | :----------------- |
| +      | 加法 | A + B 输出结果 30  |
| -      | 减法 | A - B 输出结果 -10 |
| *      | 乘法 | A * B 输出结果 200 |
| /      | 除法 | B / A w输出结果 2  |
| %      | 取余 | B % A 输出结果 0   |
| ^      | 乘幂 | A^2 输出结果 100   |
| -      | 负号 | -A 输出结果 -10    |

##### 关系运算符

需要注意不等于是`~=`

| 操作符 | 描述                                                         | 实例                  |
| :----- | :----------------------------------- | :-------------------- |
| ==     | 等于，检测两个值是否相等，相等返回 true，否则返回 false      | (A == B) 为 false。   |
| **~=** | 不等于，检测两个值是否相等，相等返回 false，否则返回 true    | (A ~= B) 为 true。    |
| >      | 大于，如果左边的值大于右边的值，返回 true，否则返回 false    | (A > B) 为 false。    |
| <      | 小于，如果左边的值大于右边的值，返回 false，否则返回 true    | (A < B) 为 true。     |
| >=     | 大于等于，如果左边的值大于等于右边的值，返回 true，否则返回 false | (A >= B) 返回 false。 |
| <=     | 小于等于， 如果左边的值小于等于右边的值，返回 true，否则返回 false | (A <= B) 返回 true。  |

##### 逻辑运算符

基本相同，只是都用关键字

| 操作符 | 描述                              | 实例                   |
| :----- | :--------------------------------- | :--------------------- |
| and    | 逻辑与操作符。 若 A 为 false，则返回 A，否则返回 B。         | (A and B) 为 false。   |
| or     | 逻辑或操作符。 若 A 为 true，则返回 A，否则返回 B。          | (A or B) 为 true。     |
| not    | 逻辑非操作符。与逻辑运算结果相反，如果条件为 true，逻辑非为 false。 | not(A and B) 为 true。 |

##### 其他运算符

| 操作符 | 描述                       | 实例                                                         |
| :----- | :--------------------------------- | :----------------------------------------------------------- |
| ..     | 连接两个字符串                     | a..b ，其中 a 为 "Hello " ， b 为 "World", 输出结果为 "Hello World"。 |
| #      | 一元运算符，返回字符串或表的长度。 | #"Hello" 返回 5                                              |

#### 数组

索引值是以 1 为起始，也可以指定 0 开始，或者是负数

```lua
array= {}
for i= -2,2,1 do
    array[i] = i*2
end
for i,v in ipairs(array) do
    print(array)
end
```

##### 迭代器

```lua
for arg... in func, const, var
do
  statements
end
```

```arg...```是函数 `func`返回值的列表，`const`是一个常量,也是传入`func`的第一个参数，`var`是一个变量量,也是传入`func`的第二个参数

自己试着实现下`ipairs`

```lua
function iter (a, i)
    i = i + 1
    local v = a[i]
    if v then
       return i, v
    end
end
 
function ipairs (a)
    return iter, a, 0 -- 这里第三个参数是0 所以执行iter时，输出的第一个就是1了
end
```

##### Table

| 序号     | 方法 & 用途                                                  |
| :--- | :----------------------------------------------- |
| 1    | **table.concat (table [, sep [, start [, end]]]):**concat是concatenate(连锁, 连接)的缩写. table.concat()函数列出参数中指定table的数组部分从start位置到end位置的所有元素, 元素间以指定的分隔符(sep)隔开。 |
| 2    | **table.insert (table, [pos,] value):**在table的数组部分指定位置(pos)插入值为value的一个元素. pos参数可选, 默认为数组部分末尾. |
| 3    | **table.maxn (table)**指定table中所有正数key值中最大的key值. 如果不存在key值为正数的元素, 则返回0。(**Lua5.2之后该方法已经不存在了,本文使用了自定义函数实现**) |
| 4    | **table.remove (table [, pos])**返回table数组部分位于pos位置的元素. 其后的元素会被前移. pos参数可选, 默认为table长度, 即从最后一个元素删起。 |
| 5    | **table.sort (table [, comp])**对给定的table进行升序排序。   |

#### 模块与包

Lua 的模块是由变量、函数等已知元素组成的 table，因此创建一个模块很简单，就是创建一个 table，然后把需要导出的常量、函数放入其中，最后返回这个 table 就行。

```lua
-- 文件名为 module.lua
-- 定义一个名为 module 的模块
module = {}
 
-- 定义一个常量
module.constant = "这是一个常量"
 
-- 定义一个函数
function module.func1()
    io.write("这是一个公有函数！\n")
end
 
local function func2()
    print("这是一个私有函数！")
end
 
function module.func3()
    func2()
end
 
return module
```

##### 加载模块(require函数)

Lua提供了一个名为require的函数用来加载模块。

```
require("<模块名>")
require "<模块名>"
```

执行 require 后会返回一个由模块常量或函数组成的 table，并且还会定义一个包含该 table 的全局变量。

##### 加载机制



##### 打印堆栈信息

```lua
print(debug.traceback())
```