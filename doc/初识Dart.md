# Dart
### 关键字
abstract 1
do
import 1
super
as 1
dynamic 1
in
switch
assert
else
interface 1
sync* 2
async 2
enum
is
this
async* 2
export 1
library 1
throw
await 2
external 1
mixin 1
true
break
extends
new
try
case
factory 1
null
typedef 1
catch
false
operator 1
var
class
final
part 1
void
const
finally
rethrow
while
continue
for
return
with
covariant 1
get 1
set 1
yield 2
default
if
static 1
yield* 2
deferred 1
implements 1

### 变量
		
	//声明类型为String的属性
	String name = 'bob';
	
	//声明name属性，编译器会推断出他是String类型
	var name = 'bob';
	
	//动态类型，会被推断为String，name属性也可以存入别的类型的数据，使用Object来声明有类似效果
	//此时name是String类型
	dynamic name = 'bob';
	//此时name是int类型
 	name = 233; 
	---
	测试下var 和dynamic一样是动态类型

### 默认值
dart中所有的属性都是对象，包括基本类型，他们的默认值都是null

	int lineCount;
	assert(lineCount == null);//is true

	
### final & const
	//可以不使用类型注释
	final name = 'bob';
	
	//这样写则会报错
	// name = ‘bob';

	//也可以定义确定的类型
	final String name = ’bob';
	
	//const是编译时常量，隐含了final
	const bar = 10000;
	//可以使用表达式
	const double atm = 2.33*bar;
	//const也可以修饰构造函数
	var foo = const[];
	foo = [];
	final bar = const[];
	//final修饰的属性无法改变
	//bar = [];
	const baz = const[];
	//const修饰的属性无法改变
	//baz = [];
	

### 内置类型
dart中有如下特殊类型
1. numbers
2. strings
3. booleans
4. lists
5. maps
6. runes
7. symbols
### Numbers
有两种风格：
#### int
int的值无法超过64bits.在Dart VM中的取值范围是 -2<sup>63</sup> to 2<sup>63</sup> - 1,Dart编译为JavaScript时，取值范围是-2<sup>53</sup> to 2<sup>53</sup> - 1

#### double
64位浮点数，按照`IEEE 754`标准定制

`int`和`double`都是`num`的子类。`num`类型支持基本的运算符+、-、*、/，您还可以找到`abs()``ceil()``floor`等方法(按位操作如`>>`被定义在`int`类中)，如果`num`和他的子类找不到您要的操作，可以试试`dart:math`库。

#### String和number的互相转换
	//String -> int
	var one = int.parse('123');
	
	//String -> double
	var one = double.parse('2.33');
	
	// int -> String
	'233'.toString();
	
	// double -> String
	'2.33'.toString();
	
#### int的按位操作shift(<< >>) and(&) or(|)
	assert(3<<1); // 0011 << 1 = 0110 = 6
	assert(3>>1); // 0011 >> 1 = 0001 = 1
	assert(3&4);  // 0011 & 0100 = 0000 = 0
	assert(3|4);  // 0011 | 0100 = 0111 = 7

### String
dart字符串是UTF-16编码单元的序列，可以用单引号或者双引号创建一个字符串

	var s1 = 'single quotes work well for String literals';
	
	var s2 = "double quotes just as well";
	//单引号声明String时，中间的‘需要用\转移
	var s3 = 'it\'s easy to escape the string delimiter';
	var s4 = "it's even easier to use the other delimiter";

#### dart支持字符串模板(kotlin类似) `${expression}`。如果是个变量则可以省略大括号`$field`
```dart
	String name = 'bob';
	String saySay = "My name is $name";
	String say
```
	
#### mulit-line String 使用三个单引号或者双引号包裹，效果类似html的`<pre>`标签

```dart
	var s1 = ''' 
		少年不识愁滋味，爱上层楼。
		爱上层楼。为赋新词强说愁。 
		而今识尽愁滋味，欲说还休。
		欲说还休。却道天凉好个秋。''';
```
#### 可以使用`r`前缀创建一个`raw`的String(类似html中的`xmp`标签，把包裹的内容全部当做文本)
```dart
var s = r"In a raw string, even \n isn't special";
```
#### 只要内插表达式是编译时常量，他的结果是null、数字、布尔、字符串，字符串文字就是编译时常量
	//These work in a count String
	const aCountNum = 0;
	const aCountBool = true;
	const aCountString = 'a count String';
	
	//These do Not work in a count String
	var aNum = 0;
	var aBool = true;
	var aString = 'a String';
	const aConstList = const[1,2,3];
	
	const validConstString = ’$aCountNum $aCountBool $aCountString';
	//const invalidCountString = '$aNum $aBool $aString $aConstList';

### Booleans  
dart中布尔类型名为`bool` 只有两种字面值 `true` `false` 两个都是编译时常量

### Lists
Dart中List是 `List` 对象

### Maps

### Runes
Dart中，Runes是UTF-32编码的String

### Symbol

### Function
Dart是真正面向对象的语言，因此也是对象，并且具有类型和函数。意思是函数可以赋值给变量或者当做参数传到别的函数
	
	String job(String input){
		return input;
	}
	
	
#### 虽然Effective Dart中不推荐，但是函数的类型注释可以省略不写

	job(input){
		return input;
	}

#### 剪头语法 
	//上面的函数可以这样写
	String job(String input) =>  {return input；}
	
	//当函数中只有一个表达式时，可以用简洁语法
	String job(String input) => return；
	
	
#### 可选参数
一个函数可以有两种参数，必须参数和可选参数。必选参数在列表前面，可选参数跟在后面。
可选参数可能是`位置参数`或者`命名参数`，但不可两种同时存在
	
 `命名参数`
访问方法时，可以指定参数名 `paramName:value`例如
```dart
//访问明明参数
enableFlags(bold:true, hidden:false);
//定义命名参数  用大括号包裹
void enableFiags({bool bold, bool hidden}){
}
```

`位置参数`
被`[]`包裹的参数集合
```dart
//device就是声明的位置参数
String say(String from, String msg, [String device]) {
  var result = '$from says $msg';
  if (device != null) {
    result = '$result with a $device';
  }
  return result;
}

//可以选择不传入位置参数
say('Bob', 'Howdy')；

//按照位置传递 第三个对应的就是位置参数
say('Bob', 'Howdy', 'smoke signal')；
```
`参数默认值`
可选参数可以使用`=`来定义默认值，默认值必须是编译时常量，如果未提供默认值，那么可选参数的值就是`null`
```dart
//命名参数的默认值
void enableFlags({bool bold = true, bool hidden = false}) {}
//位置参数的默认值
String say(String from, String msg, [String device = "---"]) {
  var result = '$from says $msg';
  if (device != null) {
    result = '$result with a $device';
  }
  return result;
}

```
<sub>旧版本中定义默认值使用的是`:`而不是`=` </sub>

#### main方法
每个app都会有顶级的`main()`方法作为app的入口。main方法返回`void`并且有一个可选的`List<String>`参数

#### 方法作为第一类对象
你可以把方法当做其他方法的参数
```dart
void printElement(int element){
	print(element);
}

var list = [1,2,3];

list.forEach(printElement);
//也可以把方法当做一个变量
var loudify = (msg)=>'!!!${msg}!!!';
```

#### 匿名方法(Anonymous functions)
大部分方法都有名字，比如`main`方法，你也可以通过匿名方法、lamda、闭包的方式创建没名字的方法。你可以把匿名方法赋值给一个变量
```dart
var list = ['apple','bananas','granges'];
list.forEach((item){
	print('${list.indexOf(item)}:$item');
});
//或者用箭头函数
list.forEach((item)=>prinf('${list.indexOf(item)}:$item'});

```

#### 作用域(Lexical scope)
dart是一种有词法作用域的语言，这意味着变量的作用域是静态决定的，仅仅由代码布局决定。
```dart
bool topLevel = true;

void main(){
	var insideMain = true;
	void myFunction(){
		var insideMyFunction = true;
		void nestedFunction(){
			var insideNestedFunction = true;
			
			assert(topLevel);
			assert(insideMain);
			assert(insideMyFunction);
			assert(insideNestedFunction);
		}
	}
	
}
```

#### 闭包(Lexical closures)
闭包是一个方法对象，他可以访问其作用域内的对象，即使函数在原始范围外使用
```dart
void makerAdder(int addBy){
	return (num i)=>addBy+1;
}

void main(){
	var add2 = makerAdder(2);
	var add4 = makerAdder(4);
	
	assert(add2(2) ==5);
	assert(add4(2) ==7);
}
```
#### 测试方法的相等性
```dart
foo(){}

class A{
	static void bar(){};
	void baz(){};
}



```

### 操作符(Operators)
`/`正经的除法(可能得到一个浮点数)
`~/`取整
`%`取余
### 操作符

#### 操作符表 

|功能描述             |操作符|
| :------------: | ----------------------- |
|一元后缀 | `expr++`    `expr--`    `()`    `[]`    `.`    `?.`|
|一元前缀|`-*expr*`    `!*expr*`    `~*expr*`    `++*expr*`    `--*expr*`  |
| 乘除 | `*`    `/`    `%`  `~/` |
| 加减 | `+`    `-` |
| 位移                    | `<<`    `>>`                   |
| 位操作 和           | `&`                                                          |
| 位操作 异或           | `^`                                                          |
| 位操作 或             | `|`                                                          |
| 关系测试 | `>=`    `>`    `<=`    `<`              |
|类型测试|`as`    `is`    `is!`|
| 相等性                 | `==`    `!=`                                                 |
| 逻辑 和              | `&&`                                                         |
| 逻辑 或               | `||`                                                         |
| if null                  | `??`                                                         |
| 条件语句            | `*expr1* ? *expr2* : *expr3*`                                |
| cascade       | `..`                                                         |
| 赋值               | `=`    `*=`    `/=`    `~/=`    `%=`    `+=`    `-=`    `<<=`    `>>=`    `&=`    `^=`    `|=`    `??=` |

表中的操作符 优先级从上到下递减

#### 算术操作符 (Arithmetic operators)

| Operator  | Meaning                                                      |
| --------- | ------------------------------------------------------------ |
| `+`       | Add                                                          |
| `–`       | Subtract                                                     |
| `-*expr*` | 负号，Unary minus, also known as negation (reverse the sign of the expression) |
| `*`       | Multiply                                                     |
| `/`       | Divide                                                       |
| `~/`      | 除法，取整。Divide, returning an integer result              |
| `%`       | 取模。Get the remainder of an integer division (modulo)      |

#### 自增(和java相同)

| Operator  | Meaning                                               |
| --------- | ----------------------------------------------------- |
| `++*var*` | `*var* = *var* + 1` (expression value is `*var* + 1`) |
| `*var*++` | `*var* = *var* + 1` (expression value is `*var*`)     |
| `--*var*` | `*var* = *var* – 1` (expression value is `*var* – 1`) |
| `*var*--` | `*var* = *var* – 1` (expression value is `*var*`)     |

#### 关系操作符

| Operator | Meaning                     |
| -------- | --------------------------- |
| `==`     | Equal; see discussion below |
| `!=`     | Not equal                   |
| `>`      | Greater than                |
| `<`      | Less than                   |
| `>=`     | Greater than or equal to    |
| `<=`     | Less than or equal to       |

这里`==`和java中的equals方法一样，如果想要类似java中`==`的效果，那么请使用`identical`方法。

如果a和b都为null,那么 a== b结果为true

//todo x.==(y)

#### 类型测试操作符

| Operator | Meaning                                    |
| :------- | ------------------------------------------ |
| `as`     | Typecast 类型转换                          |
| `is`     | True if the object has the specified type  |
| `is!`    | False if the object has the specified type |

`is`操作符和java的`instanceof`效果一样，`is！`就是和`is`的结果相反

#### 赋值操作符

| `=`  | `–=` | `/=`  | `%=`  | `>>=` | `^=` |
| ---- | ---- | ----- | ----- | ----- | ---- |
| `+=` | `*=` | `~/=` | `<<=` | `&=`  | `|=` |

#### 逻辑操作符

| Operator  | Meaning                                                      |
| :-------- | ------------------------------------------------------------ |
| `!*expr*` | inverts the following expression (changes false to true, and vice versa) |
| `||`      | logical OR                                                   |
| `&&`      | logical AND                                                  |

#### 按位和位移操作符

| Operator  | Meaning                                               |
| :-------- | ----------------------------------------------------- |
| `&`       | AND                                                   |
| `|`       | OR                                                    |
| `^`       | XOR                                                   |
| `~*expr*` | Unary bitwise complement (0s become 1s; 1s become 0s) |
| `<<`      | Shift left                                            |
| `>>`      | Shift right                                           |

#### 条件表达式

dart有两种求值表达式来代替`if else`

##### `*condition* ? *expr1* : *expr2* `

和java的三元运算符一样，如果`condition`是true,那么执行`expr1`否则执行`expr2`

##### `*expr1* ?? *expr2* `

如果`expr1`不是null，返回expr1，否则返回`expr2`

#### 级联操作 (Cascade notation)

Cascade(..)允许你为同一个对象执行一系列的操作.

```dart
void main() {
//new一个对象，然后使用..对这个对象操作
 var result = new CasCade()
  ..append("1")
  ..append("2")
  ..append("3")
  ..append("4");
  print(result.value);
}

class CasCade{
  var value = '';
  void append(String s){
    value+=s;
  }
}
```



#### 其他操作符

| Operator | Name                      | Meaning                                                      |
| -------- | ------------------------- | ------------------------------------------------------------ |
| `()`     | Function application      | Represents a function call                                   |
| `[]`     | List access               | Refers to the value at the specified index in the list       |
| `.`      | Member access             | Refers to a property of an expression; example: `foo.bar` selects property `bar`from expression `foo` |
| `?.`     | Conditional member access | Like `.`, but the leftmost operand can be null; example: `foo?.bar` selects property `bar` from expression `foo` unless `foo` is null (in which case the value of `foo?.bar` is null)        当对象为null时，不调用方法 |

### 控制流声明(Control flow statements)

- `if` and `else`
- `for` loops
- `while` and `do`-`while` loops
- `break` and `continue`
- `switch` and `case`
- `assert`

基本和java相同，其中`switch` and `case`略有不同

#### Switch and case

switch支持`integer` `string`或者编译时常量。被比较的对象必须是相同类的实例(不能是它的子类型)，

### 异常(Exceptions)

#### Throw
使用`throw`抛出异常
```dart
//抛出异常
throw FormatException('error');
//你可以抛出任何对象
throw ‘out of llamas’；
```
#### Catch
```dart
try {
//一些操作

//on 关键字后面指定详细的异常类型
} on OutOfLlamasException {
  // 一些操作
  buyMoreLlamas();
  
//on 指定类型 catch 得到异常对象，可以得到异常信息
} on Exception catch (e) {
  // 一些操作
  
  //如果你不关心异常类型 也可以直接catch
} catch (e) {
  // 打印异常的信息
  print('Something really unknown: $e');
}

```

#### Finally
和java一样，会在异常捕捉之后执行

### 类(Classes)
#### 使用类的成员
1. 使用`.`来访问对象的方法或变量
2. 使用`?.`在对象不为null时访问数据

```dart
var p = Point(2, 2);

// Set the value of the instance variable y.
p.y = 3;

// If p is non-null, set its y value to 4.
p?.y = 4;
```
#### 使用构造函数
你可以使用构造函数创建一个对象，构造函数的名字可以是`ClassName`或者`ClassName.identifier`中的一种。
``` dart
var p1 = Point(2, 2);
var p2 = Point.fromJson({'x': 1, 'y': 2});
//也可以使用new关键字,和上面的代码效果相同
var p1 = new Point(2, 2);
var p2 = new Point.fromJson({'x': 1, 'y': 2});
```
>要使用常量构造函数创建编译时常量，请将`const`放在构造函数名之前。构造两个相同的编译时常量，会返回单一的实例(全等 identical)

``` dart
	var a = const ImmutablePoint(1, 1);
	var b = const ImmutablePoint(1, 1);
	
	// They are the same instance!
	assert(identical(a, b)); 
```
在静态上下文中，可以省略构造函数前的`const`
```dart
// Lots of const keywords here.
const pointAndLine = const {
  'point': const [const ImmutablePoint(0, 0)],
  'line': const [const ImmutablePoint(1, 10), const ImmutablePoint(-2, 11)],
};

// Only one const, which establishes the constant context.
const pointAndLine = {
  'point': [ImmutablePoint(0, 0)],
  'line': [ImmutablePoint(1, 10), ImmutablePoint(-2, 11)],
};
```
#### 获取对象的类型
获取对象的运行时类型，可以使用`runtimeType`属性，会返回一个`Type`对象
```dart
print('The type of a is ${a.runtimeType}');
```
#### 实例属性
未初始化的的属性都有默认值`null`
#### 构造函数 
可以通过与类名相同的函数声明构造函数(另外，还可以加上命名构造函数中描述的附加标识符)
```dart
class Point {
  num x, y;

  // Syntactic sugar for setting x and y
  // before the constructor body runs.
  Point(this.x, this.y);
  
  // Named constructor
  Point.origin() {
    x = 0;
    y = 0;
  }
}
```
##### 默认构造函数
如果你没有声明构造函数，那么会有个一个默认的无参构造函数供你使用
##### 构造函数无法被继承
子类无法继承父类的构造函数，如果子类没声明构造函数，那么只有默认构造函数
##### 调用父类非默认构造函数
默认情况下，一个构造器会调用父类的默认构造函数，父类的构造器会在这个构造器的方法体之前执行，如果一些初始化器列表会被用到，他们会在父类的构造器之前执行。执行顺序如下：
1. 初始化器列表
2. 父类的构造器
3. 你调用的类构造器
如果父类没有未命名的无参构造器，你可以手动的调用父类的一个构造器，在`:`后面、函数体前面指定你想调用的父构造器

注意：因为父类的构造器会先执行，所以你不能调用父构造器的时候使用`this`关键字，或者使用实例的方法
##### 初始化器列表
除了调用父构造器，你也可以构造器体执行前初始化实例变量，使用`,`隔开
```dart
// Initializer list sets instance variables before
// the constructor body runs.
Point.fromJson(Map<String, num> json)
    : x = json['x'],
      y = json['y'] {
  print('In Point.fromJson(): ($x, $y)');
}
```
##### 重定向构造器
有时候一个构造器的作用是重定向到同一个类的另一个构造器。重定向构造器方法体是空的，访问的构造器在`:`后面
```dart
class Point {
  num x, y;

  // The main constructor for this class.
  Point(this.x, this.y);

  // Delegates to the main constructor.
  Point.alongXAxis(num x) : this(x, 0);
}
```
##### *** 静态构造函数
如果你生成的类对象永远不变，你可以把这些对象作为编译时常量。为此，定义`const`修饰构造器，并且将所有的实例变量声明为`final`
```dart
class ImmutablePoint {
  static final ImmutablePoint origin =
      const ImmutablePoint(0, 0);

  final num x, y;

  const ImmutablePoint(this.x, this.y);
}
```
##### *** 工厂构造函数
实现一个不是总创建类的实例的时候，使用`factory`关键字。例如一个工厂构造函数可能从cache中返回一个实例，也可能从从子类中返回一个子类的实例

#### 方法
方法是为对象提供行为的函数
##### 实例方法
##### *** getter & setter
这是支持读写对象的属性的特殊方法，每个实例变量都由一个隐式的getter适当情况下还会有setter.你可以使用`get``set`关键字来实现getter和setter
```dart
class Rectangle {
  num left, top, width, height;

  Rectangle(this.left, this.top, this.width, this.height);

  //Define two calculated properties: right and bottom
  num get right => left + width;

  set right(num value) => left = value - width;

  num get bottom => top + height;

  set bottom(num value) => top = value - height;
}
```

#### 抽象类
使用`abstract`关键字创建抽象类，抽象类用来定义一些接口和一些实现。
抽象类无法实例化，如果你实例化抽象类，那么你需要定义`factory`构造器。
```dart
//定义抽象接口
abstract class Doer{
```
#### 抽象方法
实例，访问器和设置器方法可以是抽象的，定义接口，把实现留给其他类。
抽象方法只存在于抽象类中，你可以使用`;`代替方法体。
```dart
abstract class Doer{
	//定义抽象接口
	void doSomething();
```

#### 隐式接口
每一个类隐式定义定义一个接口，该接口包含类的所有实力成员和他实现的任何接口。如果你想创建一个类A支持类B的所有API而不继承B，那么A应该实现B的接口。一个类可以实现多个接口，使用`implements`
```dart
// A person. The implicit interface contains greet().
class Person {
  // In the interface, but visible only in this library.
  final _name;

  // Not in the interface, since this is a constructor.
  Person(this._name);

  // In the interface.
  String greet(String who) => 'Hello, $who. I am $_name.';
}

// An implementation of the Person interface.
class Impostor implements Person {
  get _name => '';

  String greet(String who) => 'Hi $who. Do you know who I am?';
}
```
#### 继承
使用`extend`关键字实现继承法， 使用`super`来获得父类的引用

#### 重写成员
子类可以重写实例方法，访问器和设置器，你可以使用`@override`注解表明有意重载成员
#### 可重载的操作符
下面是你可以重写的操作符。(如果你重写了`==`,那么你也要重写`hashCode`的访问器)
`<` `+` `|` `[]`
`>` `/` `^` `[]=`
`<=` `~/` `&` `~`
`>=` `*` `<<` `==`
`-` `%` `>>` 
```dart
class Vector {
  final int x, y;

  Vector(this.x, this.y);

  Vector operator +(Vector v) => Vector(x + v.x, y + v.y);
  Vector operator -(Vector v) => Vector(x - v.x, y - v.y);

  // Operator == and hashCode not shown. For details, see note below.
  // ···
}
```
#### noSuchMethod()
要检测或响应代码识图使用不存在的方法或实例变量时，可以重写`noSuchMethod()`
你不能调用未实现的方法，除非下列任何一个是正确的
1. 接收者具有静态类型`dynamic`
*** 2. 接受方有未实现的方法的静态类型(抽象也OK)，接收方的动态类型实现了noSuchMethod.这与类对象中的方法不同

#### 枚举
一种特殊的类，用来表示固定数量的常量值
使用`enum`声明枚举类型
```dart
enum Color { red, green, blue }

void enumValue(){
  // 枚举 的值具有索引 索引从0开始
  assert(Color.red.index == 0);
  assert(Color.green == 1);
  assert(Color.blue == 2);
  // 可以使用 values 属性或者枚举的值的数组
  List<Color> colors = Color.values;
  assert(colors[2] == Color.blue);
}
```

#### 向类添加特性:minix
mixin在多个类层次结构中重用类代码的方式
要使用mixin，使用`with`关键字后面跟多个类名
```dart
class Maetro extends Person with Musical, Aggressive, Demented {
}

```
#### 类的属性和方法
使用`static`创建类级属性和方法

### 泛型(Generics)
基本和java相同 不记录了
### 库及其可见性(Libraries and visibility)
`import`和`library`	指令可以帮你创建模块化和可分享的代码库，Libraries不仅提供API，还可以使用`_`前缀标识私有的单元。每个dart应用都是library,即使他不是用`library`指令
#### 使用库
使用`import`来指定在另一个库的范围内如何使用一个库的名称空间,唯一需要导入的参数是指定库的URI。对于内置库，URI具有特殊的`dart:`协议。对于其他库，可以使用文件系统路径或者`package:`协议，`package:`协议指定包管理器(如pub工具)提供的库
##### 指定库前缀
如果你导入的两个库有冲突的标识符，你可以给他们中的一个或全部指定前缀。
```dart
import 'package:lib1/lib1.dart';
import 'package:lib2/lib2.dart' as lib2;

// Uses Element from lib1.
Element element1 = Element();

// Uses Element from lib2.
lib2.Element element2 = lib2.Element();
```
##### 只导入一个库的一部分
`show``hide`
```dart
// Import only foo.
import 'package:lib1/lib1.dart' show foo;

// Import all names EXCEPT foo.
import 'package:lib2/lib2.dart' hide foo;
```
##### 延迟加载库
要使用懒加载库，使用`deferred as`关键字，然后在你使用这个库之前调用`loadLibrary()`
```dart
import 'package:greetings/hello.dart' deferred as hello;

Future greet() async {
  await hello.loadLibrary();
  hello.printGreeting();
}
```
你可以多次调用`loadLibrary()`，这个库只会被加载一次。但是以下情况要注意
1. 延迟库中的常量在库加载前是不存在的
2. 不能在导入文件中使用延迟库中的类型。
3. dart隐式的把`loadLibrary()`插入到定义`deferred as`的命名空间
#### 实现库
1. 如何组织你的源代码
2. 如何使用`export`指令
3. 如何使用`part`指令


### 异步支持(Asynchrony support)
dart充满了返回`future`和`stream`的对象。这些方法是异步的:他们在设置可能耗时的操作(如I/O)后返回，而不该等待操作完成
`async`和`await`支持异步编程，允许你编写类似同步代码的异步代码

#### 处理`future`
当你需要一个完整的`future`对象，你有两个选择：
1. 使用`async`和`await`
2. 使用`Future` API
使用使用`async`和`await`的代码是异步的，不过看起来像同步代码
使用`await`的代码，必须在异步方法 ——`async`标记的方法
`await`表达式，通常返回一个`future`对象，如果他不是，那么这个对象会被`future`包裹，这个`future`对象表示返回对象的承诺。等待表达式的值是返回的对象。等待表达式使执行暂定，直到该对象可用为止
```dart
	Future checkVersion() async {
	var version = await lookUpVersion();
```

#### 处理 `stream`
当你需要从`stream`中获取值，你有两个选择：
1. 使用`async`和异步for循环(`await for`)
2. 使用`stream` API

```dart
await for (num temp in expression){
	print(temp);
}
```
`expression`的值必须有`stream`类型，执行过程如下：
1. 等待`stream`发出一个结果
2. 执行`for`的方法体，将变量的值设置为发出的值
3. 重复执行前两步直到`stream`关闭
要停止监听`stream`，可以使用`break``return`语句，该语句跳出`for`循环并从`stream`中取消订阅

### 生成器(Generators)
当你需要延迟生成一个值序列，请考虑使用生成器。Dart支持两种生成器功能
1. 同步生成器，返回`iterable`对象
2. 异步生成器，返回`Stream`对象

#### 实现同步生成器方法，请使用`sync*`标记方法体，并使用`yield`声明传值
```dart
Iterable<int> naturalsTo(int n) sync*{
	int k = 0;
	while(k<n) yield k++;
}
```
#### 要实现异步生成器方法，请使用`async*`标记方法体，并使用`yield`声明传值
```dart
Stream<int> asynchronousNaturalsTo(int n) async*{
	int k=0;
	while(k<n) yield k++;
```
#### 如果你生成递归，你可以使用`yield*`提升性能
```dart
Iterable<int> naturalsDownFrom(int n) sync*{
	if(n>0){
		yield n;
		yield* naturalsDownFrom(n-1);
	}	
}
```

### 可调用的类(Callable classes)
使你的类像方法一样被调用，实现`call()`方法
```dart
	class WannabeFunctin{
		call(String a, String b, String c)=>'$a $b $c';
	}
	main(){
		var wf = new WannabeFunction();
		var out = wf('hi','there', 'gang');
		print('$out');
	}
```

### 隔离器(Isolate)
大多数计算机，甚至在移动平台上，都有多核cpu。为了利用所有这些核心，开发人员通常使用同时运行的共享内存线程。但是共享状态并发容易出错，可能导致复杂的代码。

所有Dart代码都运行在隔离器内部，而不是线程。每个隔离都有自己的内存堆，确保从任何其他隔离中都无法访问隔离的状态。

### Typedefs
在Dart中，函数是对象，就像字符串和数字是对象一样。typedef或function-type别名为函数类型提供一个名称，您可以在声明字段和返回类型时使用这个名称。当函数类型被分配给变量时，typedef保留类型信息。
```dart
typedef Compare<T> = int Function(T a, T b);

int sort(int a, int b) => a - b;

void main() {
  assert(sort is Compare<int>); // True!
}

```

### 元数据(Metadata)
类似java的注解
定义一注解
```dart
library todo;
class Todo{
	final String who;
	final String what;
	const Todo(this.who, this.what);
}
```

### 注释(Comments)
#### 单行 
`//`
#### 多行 
`/* */`
#### 注释文档 
`///` `/**` `[params]`

---
参考文档
> https://www.dartlang.org/guides/language/language-tour#strings

