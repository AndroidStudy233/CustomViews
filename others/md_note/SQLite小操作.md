1. 创建表

		create table hello(_id integer primary key autoincrement, name varchar(20), age varchar(3));

2. 增

		insert into hello(name, age) values ("dadong", 23);

3. 删

		delete from hello where name = "dadong";

4. 改

		update hello set age = 23 where name = "yaoyao";

5. 查

		select name from hello where age = 23;
		select * from hello where name like '%朱%';


6. 两个关联表的查询

		select number from numbers where id = (select idx from types where name ="运营商");

7. 增加一个字段

		alter table hello add hight varchar(5);

8. 删除一个字段(SQLite目前不支持)

9. 删除表

		drop table hello;