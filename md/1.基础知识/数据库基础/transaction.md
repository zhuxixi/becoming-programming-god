<script type="text/javascript" src="../../js/flowchart.js"></script>
# 什么是事务
事务（Transaction）是并发控制的基本单位。所谓的事务，它是一个操作序列，
这些操作要么都执行，要么都不执行，它是一个不可分割的工作单位。例如，银行转账工作：
从一个账号扣款并使另一个账号增款，这两个操作要么都执行，要么都不执行。
所以，应该把它们看成一个事务。事务是数据库维护数据一致性的单位，
在每个事务结束时，都能保 持数据一致性。
我们以Msql数据库的操作为例，再进一步解释一下数据库事务：
首先我们用以下命令查看该Mysql会话的事务隔离级别，关于事务隔离级别及其作用，
我们在后面的章节中会进行详细介绍，这里只要简单知道数据库可以设置不同的事务隔离级别，
不同的隔离级别会对事务的操作产生不同的效果即可。
使用以下命令可以查询当前Mysql会话的事务隔离级别，
可以看到，Mysql默认的事务隔离级别是REPEATABLE-READ。
```
mysql> select @@tx_isolation;
+-----------------+
| @@tx_isolation  |
+-----------------+
| REPEATABLE-READ |
+-----------------+
```

## 实战

### 建表，插入数据
为了用实例来解释事务，我们创建了如下的bank数据表，并插入一条数据，
```
mysql> describe bank;
+---------+---------------+------+-----+---------+----------------+
| Field   | Type          | Null | Key | Default | Extra          |
+---------+---------------+------+-----+---------+----------------+
| id      | int(11)       | NO   | PRI | NULL    | auto_increment |
| name    | varchar(40)   | NO   |     | NULL    |                |
| balance | decimal(10,2) | YES  |     | NULL    |                |
+---------+---------------+------+-----+---------+----------------+
mysql> select * from bank;
+----+------+---------+
| id | name | balance |
+----+------+---------+
|  3 | fufu | 2000.00 |
+----+------+---------+
```
### 使用start transaction命令开启数据库事务
```
mysql> start transaction;
Query OK, 0 rows affected (0.00 sec)
```

### 在事务中做一些更新和插入操作
更新id为3的行的balance值为3000.00
```
mysql> update bank set balance = 3000 where id = 3;
Query OK, 1 row affected (0.09 sec)
Rows matched: 1  Changed: 1  Warnings: 0

mysql> select * from bank;
+----+------+---------+
| id | name | balance |
+----+------+---------+
|  3 | fufu | 3000.00 |
+----+------+---------+
1 row in set (0.00 sec)
```

此时我们可以看到，select语句查询到的id为3的行的balance值已经修改为3000.00，
接下来我们再尝试插入一条新数据，
```
mysql> insert into bank (name, balance) values ('melo', 1000);
Query OK, 1 row affected (0.06 sec)

mysql> select * from bank;
+----+------+---------+
| id | name | balance |
+----+------+---------+
|  3 | fufu | 3000.00 |
|  4 | melo | 1000.00 |
+----+------+---------+
2 rows in set (0.00 sec)
```
由于以上的update和insert操作都是在start transaction命令开启事务之后，
所以直到事务结束，这些操作都属于同一事务，假设我们在insert操作时产生了错误，
可以根据事务的定义得知，这些属于同一事务的所有操作要么都执行要么都不执行，
我们可以验证一下，使用rollback命令，模拟事务失败回滚。
### 使用rollback回滚
```
mysql> rollback;
Query OK, 0 rows affected (0.01 sec)
```
### 再查询验证数据无变化
此时我们在查询数据库中的所有数据，发现数据恢复到了update命令执行前的状态，
id为3的行的balance值等于2000没有变化。
```
mysql> select * from bank;
+----+------+---------+
| id | name | balance |
+----+------+---------+
|  3 | fufu | 2000.00 |
+----+------+---------+
1 row in set (0.00 sec)
```

### 小结
到此，我们阐述了数据库事务的定义并用简单的Mysql操作说明了事务的操作
方式，我们可以总结出数据库事务的生命周期如下：

```flow
st=>start: Start
op=>operation: Your Operation
cond=>condition: Yes or No?
e=>end

st->op->cond
cond(yes)->e
cond(no)->op
```

