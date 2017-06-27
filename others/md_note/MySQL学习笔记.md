# 数据库--MySql
>数据仓库.就与我们之前学过的纯文本,properties这些技术一样.用来保存数据.并提供对数据进行增删改查的操作.我们以后做项目时,项目中的数据都是保存在数据库中的.

### 一、为什么要用数据库,数据库的特点

	1. 实现数据共享
		
	2. 减少数据的冗余度
	
	3. 数据实现集中控制
	
	4. 数据一致性,完整性和可维护性，以确保数据的安全性和可靠性
	
	5. 故障恢复

---
### 二、常用数据库

1. MySQL---MySQL是最受欢迎的开源SQL数据库管理系统，它由 MySQL AB开发、发布和支持。MySQL AB是一家基于MySQL开发人员的商业公司.MySQL是一个关系数据库管理系统。MySQL是开源的。已经被Oracle收购。

2. SQL Server---SQL Server是由微软开发的数据库管理系统,它只能在Wdows上运行.

3. Oracle---提起数据库，第一个想到的公司，一般都会是Oracle(甲骨文)。该公司成立于1977年，最初是一家专门开发数据库的公司。Oracle在数据库领域一直处于领先地位。 Oracle数据库成为世界上使用最广泛的关系数据系统之一。

4. Sybase（退隐） 配套的数据库设计软件。 power designer 数据库设计软件。建模工具。1984年，Mark B. Hiffman和Robert Epstern创建了Sybase公司，并在1987年推出了Sybase数据库产品。

5. DB2 IBM开发的。也是收费数据库。

---
### 三、MySQL安装

>安装位置有两个.

数据库服务位置: MySQL Server  
数据库数据文件位置: Server data files

数据安装包的类型:
		1.老版数据库(没被oracle收购之前的版本)=>在新的操作系统中安装失败的几率较高.占用空间较小.
		2.新版数据库(收购之后的版本)=>系统兼容性较好.占用空间较大.

mysql数据库 管理员名称为root。 


> 常见问题：

1. mysql卸载

	* 运行卸载程序
	* 删除目录

2. 安装时忘记配置字符集编码. 安装时的配置会体现在 my.ini文件中。该文件在数据库服务所在路径下。 找到配置文件修改保存。 要想生效需要重启数据库（重启服务即可）。

3. 忘记管理员密码。==》 参照resource文件夹下的方法。

4. 忘记勾选自动配置环境变量。找到数据库服务安装目录下的bin目录。将该目录路径配置到path环境变量下。

5. 安装新版mysql ，提示需要安装framework 4.0。
 

---
### 四、SQL语句

DDL 数据库定义语言 Data Definition Language  || create alter drop

DCL 数据库控制语言 Data Control Language || grant rollback commit.

DML 数据库操纵语言 Data Manipulation Language || insert update delete select
	(DQL 数据库查询语言 Data Query Language || select)

* SQL中的注释

	* 单行 :  --   
	* 多行 :  /* */
	
	
* sql不区分大小写吗?
	
	* 语句不区分大小写.
	* 数据区分大小写.
	
* mysql中sql的结束使用";"号表示.

* 退出的命令 => exit

* 连接数据库服务的命令

		mysql -u root -p

	根据提示输入密码 ,即可建立连接.


---
### 五、数据库定义语言(库的操作) DDL 

>[]里面的内容为可加可不加

1. 创建一个库
	
	create database 库名称 [character set 码表名称 collate 字符校对集名称]
	
	create database day15;
	
		1. 看到Query OK, 1 row affected (0.00 sec) 表示执行成功.
		
		2. sql语句语法: 结尾应使用";"号.
		
		3. 字符校对集: 决定数据排序的。 
	
2. 显示mysql中都有哪些库了.

		show databases;

		+--------------------+
		| Database           |
		+--------------------+
		| information_schema |
		| day13             |
		| mysql              |
		| performance_schema |
		| test               |
		+--------------------+
	
	可以看到除了自己创建的库之外，还有一些其他库。其他库（除了test）不要乱动。因为是保存mysql的配置信息，账户信息等等。
	test库 ： 自动创建用于测试的。
		
3. 删除一个数据库
	
		drop database 数据库名称;
	
		drop database day15; 

4. 修改数据库码表和字符校对（不常用）
	
		alter database 数据库名称 character set 数据库码表 [collate 校对集名称];
	
		alter database day15 character set utf8 collate utf8_bin;
	
5. 当前要使用的库（重要）

		use 库名
		
		use day15;
		
		出现Database changed,说明切换完成.

6. 查看当前选择的数据库

		select database();
		+------------+
		| database() |
		+------------+
		| day15      |
		+------------+
		
7. 显示创建库的语句.

		show create database 数据库名称;
		show create database day15;
		
		| day15 | CREATE DATABASE `day15` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */ |
	
> 小结

		创建库	create database 库名 [character set 码表  collate 字符校对集]

		显示所有库	show databases;

		删除库		drop database 库名;

		修改数据库码表	alter database 库名 character set 码表  collate 字符校对集

		使用数据库	use 库名;

		查看当前使用的库 select database();

		显示创建库语句	show create database 库名;

---
### 六、数据库中的数据类型  (了解,知道在什么情况下应该使用什么类型的数据)

1. 数字型
	
		整型:
		TINYINT    1字节    	byte
		SMALLINT   2字节    	short
		MEDIUMINT  3字节   
		// 一般就用到 int 和 bigint
		INT        4字节     int
		BIGINT     8字节		long

		浮点型:
		FLOAT   单精度4字节  	float
 		DOUBLE  8字节		 	double 
		DECIMAL 没有精度损失 	

	DOUBLE 和 	DECIMAL 区别？

		DOUBLE类型在运算时会有精度的缺失。
		DECIMAL 就是解决精度缺失问题的。（底层使用字符串来保存数字）
		
		单纯想表示小数属性时，使用double。
		需要频繁参与运算的小数，使用decimal。
	
2. 字符串类型  
> 字符串类型要使用单引号包裹.
	
* 短字符串类型

		CHAR / VARCHAR  (最大长度255字节)        
		====================================================
			问题:char和varchar有什么区别?
				char定长字符串.varchar表示变长字符串.
				同时指定长度为10。当存储 abc
				char =》  'abc       '
				varchar => 'abc'
				结论： 开发中varchar用的最多。 char只在表示固定长度的枚举中使用。例如 ：性别（用01,02表示）
		====================================================
		
* 长字符串类型(流类型)

		TEXT / CLOB 保存文本(字符流) --> 当要保存的内容超过255字节时使用.  java中的writer 字符
		BLOB      保存字节(字节流) --> 开发中用不到				 java中的stream 字节
		
		Character Large Object 
		binary Large Object
		
		区别：
			text：只能存储字符数据.
			BLOB：可以存储字符和多媒体信息(图片 声音 图像)
		

3. 日期和时间类型

		date   		只记录日期   			 2015-01-14
		time   		只记录时间   			 11:36:25
		year   		只记录年      			 2015
		datetime  	又记录日期 又记录 时间     2015-01-14 11:36:25
		timestamp 	同上 					 2015-01-14 11:36:25
			
	问题: datetime 和 timestamp 区别?
		这两种类型记录的数据是一模一样.
		区别在于插入的时候,如果插入datatime类型时,没有 传值,那么该类型默认值就是null;
						   如果插入timestamp类型时,没有 传值,那么该类型默认值就是当前时间;

---
### 七、与创建表相关的语句(DDL) (记住,能够手写)

	CREATE TABLE table_name
	(
		field1  datatype  约束/主键约束 auto_increment,
		field2  datatype  约束,
		field3  datatype  约束
	)[character set 字符集 collate 校对规则]

1.创建表
	
	create table t_user(
		id int,
		name varchar(20),
		salary	double(4,3),
		birthday datetime,
		hiredate timestamp
	);
	
	varchar最好指定长度
	整型一般不指定.
	
2.查看当前库中有哪些表

	show tables;
	
3.查看表的结构

	desc 表名;	 description
	desc t_user;

4.删除表

	drop table 表名;
	drop table t_user;

5.添加一列

	alter table 表名 add 列名 类型;
	alter table t_user add photo blob;

6.修改列的类型
		
	alter table 表名 modify 列名 类型;
		
	alter table t_user modify photo varchar(20);
		
7.修改列(名称 + 类型)
		
	alter table 表名 change  旧列名  新列名 数据类型；
		
	将 photo这一列 改名为 image
	alter table t_user change photo image varchar(20);
		
8.删除某列
	
	alter table 表名 drop 列名;
	
	alter table t_user drop image;
	
9.修改表的名称
	
	rename table 旧表名 to 新名;
	
	rename table t_user to user;
	
10(用的极少)修改表的字符集. (如果创建表时不指定,默认使用数据库的字符集)
	
	alter table 表名 character set 字符集 collate 校对集;
	
	alter table t_user character set utf8 collate utf8_bin;
		
---
### 八、列的约束 (掌握)	
>保证数据的完整性的.
	
1. 非空约束(not null)  指定非空约束的列, 在插入记录时 必须包含值.

		id int not null;

2. 唯一约束(unique)  该列的内容在表中. 值是唯一的.
	
		id int unique key;

3. 主键约束(primary key)  当想要把某一列的值,作为该列的唯一标示符时,可以指定主键约束(包含 非空约束和唯一约束). 一个表中只能指定一个主键约束列.
				
		主键约束 , 可以理解为 非空+唯一. 
		注意： 并且一张表中只能有一个主键约束.约束体现数据库的完整性.
		
		例如:创建带有约束的表
	
		create table t_user2(
			id int primary key auto_increament,					-- 员工编号
			name varchar(10) not null,		-- 员工姓名
			loginname varchar(10) not null unique,  -- 登陆名称
			password varchar(20) not null,	-- 密码
			age int(3) not null,				-- 年龄
			birthday datetime not null,		-- 生日
			hiredate timestamp not null		-- 入职日期
		);
	
4. 默认值约束(default) 指该列的的字段如果没有赋值会有一个默认值

		create table t_user2(
			clothsize int default 175; 
			name varchar(10) not null,		-- 员工姓名
		);

5. 外键约束

>比如一张用户表有多个订单,用户表和订单表之间是通过订单表中一个叫做user__id的字段来实现关联的. 可是如果删除了一个用户id为233的用户,那么订单中user_id为233的所有订单都成了废数据.为了避免这种情况.产生了外键约束-- foreign key.(一般而言在开发阶段不添加该约束方便测试,待上线时添加)


格式: alter table 从表名称 add foreign key(外键名称) references 主表名称(主键);
(如果从表已经有垃圾数据了,那么无法关联)  

添加外键之后:

	1. 主表中不能删除从表中已引用的数据

	2. 从表中不能添加主表中主键值没有的数据
		

>又比如一张订单表中有多个商品,一个商品又会出现在不同的订单中. 相对于上面的一对多,这就是多对多的概念了.这种情况一般会引入一个中间表.

格式和上面一样

alter table middle add foreign key(or_id) references orders(id);

alter table middle add foreign key(pr_id) references products(id);


---
### 主键自动增长 (掌握)
>tip

1. 前提某个表的主键是数字. 我们可以将该主键设置为自增. 

2. 使用主键自增可能会造成主键的断层。

3. mysql, sqlserver, sqlite这三个数据库具有该功能.

4. 主键自增只能给主键约束的列加。自增就是 每次插入记录时不需要指定值. 该字段自己维护自己的值.
维护方式就是每次加1.

语法:
	
	create table t_user(
		id  int primary key auto_increment,
		password varchar(30) not null,
		age	int not null,
		birthday datetime not null,
		hiredate timestamp not null,
		number int unique
	);
	

---
#### 九、创建表练习
	
	CREATE TABLE employee (
	   id INT(10),
	   NAME VARCHAR(10),
	   gender VARCHAR(10),
	   birthday DATETIME,
	   entry_date TIMESTAMP,
	   job VARCHAR(5),
	   salary DOUBLE(5,3),
	   RESUME TEXT
	);


1. 在上面员工表的基础上增加一个image列。

		alter table employee add image varchar(20);

2. 修改job列，使其长度为60。

		alter table employee modify job varchar(60);
	

3. 删除gender列。
 
		alter table employee drop gender;
	

4. 表名改为user。

		rename table employee to user;
	

5. 修改表的字符集为utf8

6. 列名name修改为username

		alter table employee change name username varchar(20);
	

---
## 十、对表中数据的增删改(DML)

	create table t_user(
		id int primary key auto_increment,
		name varchar(20) not null,
		email varchar(20) unique
	)

---
#### 为表添加记录 (必须掌握)

	insert into 表名[(列名1,列名2...)] values (值1,值2...);
		
1. 插入一条数据

	1) 指定要插入哪些列
	
		insert into t_user(name,email) values('tom','tom@itcast.cn');
	
		****注意: 数据类型为字符串类型的.需要使用单 / 双引号包裹.

	2) 不指定插入哪些列, 那么就要指定每一列的值(id是自增长的直接给null就行)
	
		insert into  t_user values(null,'jerry','jerry@itcast.cn');
		
		insert into  t_user(name,email) values('汤姆','tom2@itcast.cn');

2. SHOW VARIABLES LIKE '%character%';    ==> 查看字符编码配置


		| character_set_client     | gbk   客户端的编码 ***
		           |
		| character_set_results    | gbk    结果集的编码 ***
		           |
		| character_set_connection | utf8   客户端连接的编码
		           |
		| character_set_database   | utf8   数据库默认使用的编码
		           |
		| character_set_filesystem | BINARY  文件系统存放时使用的编码
		           |
		| character_set_server     | utf8    服务器编码 安装时指定的
		           |
		| character_set_system     | utf8    内部系统编码
	
	
>结论: 	如果使用cmd 命令控制台操作 数据库,注意character\_set\_client 和  character\_set\_results  需要设置成GBK, 因为我们的命令控制行使用gbk码表显示中文.

使用如下命令设置:

	set  character_set_client=gbk
	set  character_set_results=gbk
	
	注意：
		 每次重新连接数据库都要重新设置. 
		 如果使用的cmd窗口操作数据库. 就修改如下的码表为gbk（cmd窗口使用的是gbk码表）. 
		 这种做法影响的范围只在你当前链接中.

---
#### 修改一条记录 (必须掌握)

		update 表名 set 列名1 = 值 , 列名2 = 值 ....[where 条件1,条件2...]

		create table t_user(
			id int primary key auto_increment,
			name varchar(20) not null,
			email varchar(20) unique
		)
	
修改表中id为3 的记录, 将name修改为rose;

	update t_user set name='rose' where id=3;
	update t_user set name='rose';(不加条件就是尼玛所有name都改)

	CREATE TABLE employee (
	   id INT primary key,
	   name VARCHAR(20),
	   gender VARCHAR(20),
	   birthday DATE,
	   entry_date DATE,
	   job VARCHAR(30),
	   salary DOUBLE,
	   resume LONGTEXT
	);

	INSERT INTO employee VALUES(1,'zs','male','1980-12-12','2000-12-12','coder',4000,NULL);
	INSERT INTO employee VALUES(2,'ls','male','1983-10-01','2010-12-12','master',7000,NULL);
	INSERT INTO employee VALUES(3,'ww','female','1985-03-08','2008-08-08','teacher',2000,NULL);
	INSERT INTO employee VALUES(4,'wu','male','1986-05-13','2012-12-22','hr',3000,NULL);	
	

-- 要求
-- 将所有员工薪水修改为5000元。
	
	update t_user set salary = 5000;

-- 将姓名为’zs’的员工薪水修改为3000元。

	update t_user set salary = 3000 where name = 'zs';

-- 将姓名为’ls’的员工薪水修改为4000元,job改为ccc。

	update t_user set salary = 4000, job = ccc where name = 'ls';

-- 将wu的薪水在原有基础上增加1000元。
	
	
	update t_user set salary = (salary + 1000) where name = 'wu';

---
## 删除表记录相关
>删除记录语句 (必须掌握)

* DELETE FROM   表名  [WHERE 条件];

	1. 删除表中名称为’rose’的记录。

		DELETE FROM employee WHERE NAME='rose';

	2. 删除表中所有记录。

		DELETE FROM employee ;

	3. 使用truncate删除表中记录。
	
		TRUNCATE TABLE employee;



* DELETE 删除 和 TRUNCATE删除(了解) 两者有什么区别?

	首先,这两种都是删除表中的记录.
	
	不同的是:
		1. delete 是逐行标记删除. TRUNCATE 是将整张表包括表结构都移除,然后将表重新创建.
		2. delete DML语句。 TRUNCATE DDL语句。
		3. delete 删除的记录可以被恢复，TRUNCATE 不能回复。
		4. delete 不释放空间，TRUNCATE 释放空间.
		5. TRUNCATE  会提交事务. (还没学)


### 十二、DQL语句(DML) 查询语句.  (必须掌握)

语法：

	SELECT selection_list /*要查询的列名称*/

	FROM table_list /*要查询的表名称*/

	WHERE condition /*行条件*/

	GROUP BY grouping_columns /*对结果分组*/

	HAVING condition /*分组后的行条件*/

	ORDER BY sorting_columns /*对结果排序*/

	LIMIT offset_start, row_count /*结果限定*/

	
	CREATE TABLE stu ( --学生表
		sid	CHAR(6),	 -- 学生编号
		sname		VARCHAR(50), -- 学生姓名
		age		INT,	-- 年龄
		gender	VARCHAR(50)	-- 性别
	);

	INSERT INTO stu VALUES('S_1001', 'liuYi', 35, 'male');
	
	INSERT INTO stu VALUES('S_1002', 'chenEr', 15, 'female');
	
	INSERT INTO stu VALUES('S_1003', 'zhangSan', 95, 'male');
	
	INSERT INTO stu VALUES('S_1004', 'liSi', 65, 'female');
	
	INSERT INTO stu VALUES('S_1005', 'wangWu', 55, 'male');
	
	INSERT INTO stu VALUES('S_1006', 'zhaoLiu', 75, 'female');
	
	INSERT INTO stu VALUES('S_1007', 'sunQi', 25, 'male');
	
	INSERT INTO stu VALUES('S_1008', 'zhouBa', 45, 'female');
	
	INSERT INTO stu VALUES('S_1009', 'wuJiu', 85, 'male');
	
	INSERT INTO stu VALUES('S_1010', 'zhengShi', 5, 'female');
	
	INSERT INTO stu VALUES('S_1011', 'xxx', NULL, NULL);



	CREATE TABLE emp(	-- 员工表
		empno		INT,	-- 员工编号
		ename		VARCHAR(50), -- 员工姓名
		job		VARCHAR(50),	-- 工作
		mgr		INT,			-- 员工上司的编号
		hiredate	DATE,		-- 入职日期
		sal		DECIMAL(7,2),	-- 工资
		comm		DECIMAL(7,2), -- 奖金
		deptno		INT		-- 部门编号
	);

	INSERT INTO emp VALUES(7369,'SMITH','CLERK',7902,'1980-12-17',800,NULL,20);
	INSERT INTO emp VALUES(7499,'ALLEN','SALESMAN',7698,'1981-02-20',1600,300,30);
	INSERT INTO emp VALUES(7521,'WARD','SALESMAN',7698,'1981-02-22',1250,500,30);
	INSERT INTO emp VALUES(7566,'JONES','MANAGER',7839,'1981-04-02',2975,NULL,20);
	INSERT INTO emp VALUES(7654,'MARTIN','SALESMAN',7698,'1981-09-28',1250,1400,30);
	INSERT INTO emp VALUES(7698,'BLAKE','MANAGER',7839,'1981-05-01',2850,NULL,30);
	INSERT INTO emp VALUES(7782,'CLARK','MANAGER',7839,'1981-06-09',2450,NULL,10);
	INSERT INTO emp VALUES(7788,'SCOTT','ANALYST',7566,'1987-04-19',3000,NULL,20);
	INSERT INTO emp VALUES(7839,'KING','PRESIDENT',NULL,'1981-11-17',5000,NULL,10);
	INSERT INTO emp VALUES(7844,'TURNER','SALESMAN',7698,'1981-09-08',1500,0,30);
	INSERT INTO emp VALUES(7876,'ADAMS','CLERK',7788,'1987-05-23',1100,NULL,20);
	INSERT INTO emp VALUES(7900,'JAMES','CLERK',7698,'1981-12-03',950,NULL,30);
	INSERT INTO emp VALUES(7902,'FORD','ANALYST',7566,'1981-12-03',3000,NULL,20);
	INSERT INTO emp VALUES(7934,'MILLER','CLERK',7782,'1982-01-23',1300,NULL,10);

## 查询语句

### 1

* 查询所有行所有列 
		
		select * from stu;

	*号 是通配符.通配所有列. 上面语句与下面是一模一样的

		select 	sid,sname,age,gender from stu;

	谁的效率更高?	
		下面的效率更高. *需要运算.

* 查询所有行指定列

	select sname from stu;

### 2

* 条件查询介绍
>条件查询就是在查询时给出WHERE子句，在WHERE子句中可以使用如下运算符及关键字：

	?	=、!=、<>、<、<=、>、>=；
	?	BETWEEN…AND；
	?	IN(SET)/NOT IN(SET)
	?	IS NULL/IS NOT NULL
	//---条件连接符
	?	AND； &&
	?	OR；  ||
	?	NOT； !

* 查询性别为女，并且年龄小于50的记录

		select * from stu where gender='female'  and age < 50;

* 查询学号为S_1001，或者姓名为liSi的记录

		select * from stu where sid='S_1001' or sname='liSi';

		数据库中,sql语句不区分大小写 ,但是 数据区分大小写.

* 查询学号为S_1001，S_1002，S_1003的记录

		select * from stu where sid='S_1001' or  sid='S_1002' or  sid='S_1003';
		
		select * from stu where sid in('S_1001','S_1002','S_1003');
 
* 查询学号不是S_1001，S_1002，S_1003的记录
	 
		select * from stu where not (sid='S_1001' or  sid='S_1002' or  sid='S_1003');
		
		select * from stu where sid not in('S_1001','S_1002','S_1003');
  
* 查询年龄为null的记录  
	
		select * from stu where age=null;
		
		null的特性: null不等于null 所以判断时应如下写法:
		
		select * from stu where age is null;
	
* 查询年龄在20到40之间的学生记录
	
		select * from stu where age >= 20 and age <= 40;
	
		select * from stu where age between 20 and 40;
		
 
* 查询性别非男的学生记录
	
		select * from stu where gender!= 'male';
		
		select * from stu where not gender='male';
	
		select * from stu where gender not in ('male');

	
* 查询姓名不为null的学生记录

		select * from stu where  sname is not null;
		
		select * from stu where not  sname is  null;


>where 字段 like '表达式'; 
% 通配 通配任意个字符.
_ 通配 通配单个字符.
说明: LIKE 条件后 根模糊查询表达式,  "_" 代表一个任意字符


### 3

* 查询姓名由5个字母构成的学生记录

		select * from stu where sname like '_____';
	

*  查询姓名由5个字母构成，并且第5个字母为“i”的学生记录

		select * from stu where sname like '____i';

* 查询姓名以“z”开头的学生记录		说明: "%"该通配符匹配任意长度的字符.

		select * from stu where sname like 'z%';

* 查询姓名中第2个字母为“i”的学生记录

		select * from stu where sname like '_i%';

* 查询姓名中包含“a”字母的学生记录

		select * from stu where sname like '%a%';


### 4
* 去除重复记录

		 关键词: distinct => 去除重复查询结果记录.
		 select gender from stu; ==> 出现大量重复的记录
		
		 select distinct gender from stu; =>去除重复的记录
	

* 查看雇员的年薪与佣金之和


		select sal*12+comm from emp; 
		
		null与任何数字计算结果都是null.上面的写法是错误的.
		使用IFNULL(参数1,参数2) 函数解决. 判断参数1的值是否为null,如果为null返回参数2的值.
		
		select sal*12 + IFNULL(comm,0) from emp;
		
		*这个函数在所有数据库通用吗？
			不通用.

* 给列名添加别名
		
		select sal*12 + IFNULL(comm,0) as '年收入' from emp;
	

### 5

* 查询所有学生记录，按年龄升序排序
	
		asc: 升序
		desc:降序
	
		select * from stu order by age asc;
		
		默认就是升序
	
		select * from stu order by age;
	

* 查询所有学生记录，按年龄降序排序
	
		select * from stu order by age desc;

* 查询所有雇员，按月薪降序排序，如果月薪相同时，按编号升序排序

		select * from emp order by sal desc , empno asc;

>聚合函数---聚合函数是用来做纵向运算的函数：

	?	COUNT()：统计指定列不为NULL的记录行数；
	?	MAX()：计算指定列的最大值，如果指定列是字符串类型，那么使用字符串排序运算；
	?	MIN()：计算指定列的最小值，如果指定列是字符串类型，那么使用字符串排序运算；
	?	SUM()：计算指定列的数值和，如果指定列类型不是数值类型，那么计算结果为0；
	?	AVG()：计算指定列的平均值，如果指定列类型不是数值类型，那么计算结果为0；

### 6
* COUNT
>当需要纵向统计时可以使用COUNT()。

	1. 查询emp表中记录数：
	
			select count(*) from emp;
	
	2. 查询emp表中有佣金的人数：
		
			select count(*) from emp where comm is not null and comm >0;
	
	3. 查询emp表中月薪大于2500的人数：
	
			select count(*) from emp where sal > 2500;
	
	4. 统计月薪与佣金之和大于2500元的人数：
		
			select count(*) from emp where sal+IFNULL(comm,0) > 2500;
		
	5. 查询有佣金的人数并且有领导的人数：
	
			select count(*) from emp where comm > 0 and  mgr is not null;


* SUM(计算总和)和AVG(计算平均值)
>当需要纵向求和时使用sum()函数。

	1. 查询所有雇员月薪和：
	
			select sum(sal) from emp;

	2. 查询所有雇员月薪和，以及所有雇员佣金和：
	
			select sum(sal),sum(comm) from emp;
	
	3. 查询所有雇员月薪+佣金和：
	
			select sum(sal+IFNULL(comm,0)) from emp;
	
	4. 统计所有员工平均工资：
			
			select avg(sal) from emp;
	
* MAX和MIN
	
	查询最高工资和最低工资：

			select max(sal),min(sal) from emp;

	
## 分组查询

>当需要分组查询时需要使用GROUP BY子句，例如查询每个部门的工资和，这说明要使用部分来分组。


	1. 查询每个部门的部门编号和每个部门的工资和：

		
		select deptno,sum(sal) from emp group by deptno;
		

	2. 查询每个部门的部门编号以及每个部门的人数：
	
		select deptno,count(ename) from emp group by deptno;


	3. 查询每个部门的部门编号以及每个部门工资大于1500的人数：
	
		select deptno,count(ename) from emp where sal>1500 group by deptno ;

		
	HAVING子句
	4. 查询工资总和大于9000的部门编号以及工资和：
	
		select deptno,sum(sal) from emp group by deptno having sum(sal)>9000;
		
	使用having在分组之后加条件.

	where和having都可以加条件?
		
		1.where在分组之前加条件.
		2.having在分组之后加条件.

		where的效率要远远高于having. 分组本身消耗资源非常大.


## 分页
>LIMIT（MySQL方言） (必须掌握) LIMIT用来限定查询结果的起始行，以及总行数。

1. 查询5行记录，起始行从0开始

		select * from emp limit 0,5;

2. 查询10行记录，起始行从3开始

		select * from emp limit 3,10;

3. 如果一页记录为5条，希望查看第3页记录应该怎么查呢？

		第一页记录起始行为0，一共查询5行；

			select * from emp limit 0,5;

		第二页记录起始行为5，一共查询5行；

			select * from emp limit 5,5;

		第三页记录起始行为10，一共查询5行；

			select * from emp limit 10,5;


## 关联查询

>一张用户表,一张订单表,订单表中的user_id字段为外键关联用户表中的id

1. 内连接

	查询用户的订单,没有订单的用户不显示.

	select user.* ,oder.* from user,order where user.id = order.user_id;(隐式) 

	select user.* ,order.* from user join order on user.id = order.user_id;(显式)

2. 左外连接
	
	select tabone.* ,tabtwo.* from tabone left join tabletwo on tabone.xx = tabtwo.xx;

	查询所有用户的订单详情.
	左外连接:先展示join join join左边表user的所有数据,再根据关联查询的条件join右边表order,有符合的就显示出来,没有符合的显示为NULL

	select user.* ,order.* from user left join order on user.id = order.user_id;


3. 右外连接

	select tabone.* ,tabtwo.* from tabtwo right join tabone on tabone.xx = tabtwo.xx;

	查询所有订单的用户详情.
	右外连接:先展示join join join右边表order的所有数据,再根据关联查询的条件join左边表user,有符合的就显示出来,没有符合的显示为NULL

	select order.* ,user.* from user right join order on user.id = order.user_id;
	
	其实两个差不多.用左连接实现
	select order.* ,user.* from order left join user on user.id = order.user_id;


4. 子查询

	* 查询用户为张三的订单详情

	select * from order where user_id = (select id from user where name = '张三');

	* 查询订单价格大于1000的所有用户信息

	select * from user where id in (select user_id from order where price > 1000);

	* 查询订单价格大于1000的订单信息及相关用户的的信息
	
	select order.* ,user.* from order,user where user.id = order.user_id and order.price > 1000;
	
	// 方式2 利用中间表

	select  user.* ,tmp.* from user,(select * from order where price > 1000) as tmp where user.id = tmp.user_id;