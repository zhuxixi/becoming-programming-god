# Redis配置文件详解
## 1. 常规命令

### 1.1 `./redis-server /path/to/redis.conf`

启动redis并使配置文件生效

### 1.2 `include /path/to/local.conf` 
include 可以使用多个配置文件，如果配置文件有相同值，后面的会覆盖前面的:  
```
include /path/to/local.conf
include /path/to/other.conf
```
### 1.3 `loadmodule /path/to/my_module.so`

加载modules 没什么用好像
```
loadmodule /path/to/my_module.so
loadmodule /path/to/other_module.so
```

### 1.4 `bind 127.0.0.1`
绑定ip地址，为了安全最好都绑定

### 1.5 `protected-mode yes`
保护模式，如果保护模式开了，而且redis既没有bind ip，也没设置密码，那redis只接收127.0.0.1的连接。
**默认都开**

### 1.6 `port 6379`
端口，设置为0就不会监听

### 1.7 `tcp-backlog 511` 
linux 内核tcp_max_syn_backlog和somaxconn 参数调优 
### 1.8 `unixsocket /tmp/redis.sock unixsocketperm 700`
unix socket ，默认不监听，没用
### 1.9 `timeout 0`
连接闲置N秒时关闭连接
### 1.10 `tcp-keepalive 300`
开启TCP长连接，如果设置非0，会使用系统的SO_KEEPALIVE间隔发送TCP ACK给客户端，以防连接被弃用。这个很有用：  
* 检测死掉的连接。
* 如果网络之间还有其他的网络设备，可以连接保活
注意，如果想依靠这个机制关闭连接，可能需要两倍的时间，主要取决于kernel的配置
默认值是300。

## 2. 标准配置

### 2.1 `daemonize yes`
默认情况redis不会按照守护进程的模式去运行。如果你需要，可以设置来开启
注意，如果开启守护进程模式，会生成`/var/run/redis.pid`保存pid

### 2.2 `supervised no` 

```
 If you run Redis from upstart or systemd, Redis can interact with your
 supervision tree. Options:
   supervised no      - no supervision interaction
   supervised upstart - signal upstart by putting Redis into SIGSTOP mode
   supervised systemd - signal systemd by writing READY=1 to $NOTIFY_SOCKET
   supervised auto    - detect upstart or systemd method based on
                        UPSTART_JOB or NOTIFY_SOCKET environment variables
 Note: these supervision methods only signal "process is ready."
       They do not enable continuous liveness pings back to your supervisor.
```
### 2.3 `pidfile /var/run/redis_6379.pid`
pid文件路径 ，默认值`/var/run/redis.pid`
如果在非守护进程模式下，而且也没配置pidfile路径，那么不会生成pid文件。如果是守护进程模式，
pidfile总会生成，没配置pidfile就会用默认路径。
### 2.4 `loglevel notice`
指定服务的日志级别：
* debug
* verbose
* notice
* warning
默认 notice
### 2.5 `logfile ""`
指定redis日志文件名称和路径。你也可以设置`logfile ""`强制redis将日志输出的标准输出。
注意，如果你使用标准输出，而且redis使用守护进程模式运行，那log日志会被发送给/dev/null，就没了
`logfile ""`
### 2.6 `syslog-enabled no`
```
To enable logging to the system logger, just set 'syslog-enabled' to yes,
and optionally update the other syslog parameters to suit your needs.
```
### 2.7 `syslog-ident redis` 
Specify the syslog identity.

### 2.8 `syslog-facility local0`
Specify the syslog facility. Must be USER or between LOCAL0-LOCAL7.

### 2.9 `databases 16`
```
使用集群模式时，database就是0
设置数据库的数量。redis默认的数据库就是0，你可以选择不同的数据库，在一个redis连接中
执行selcet <dbid> ，dbid可选的范围是0~(databases-1)，默认就是0~15
```
### 2.10 `always-show-logo yes`
搞笑配置，永远显示redis的logo

## 3. 快照相关

### 3.1 `save 900 1`
开启RDB持久化。  
save <seconds> <changes> ，save 900 1，就是900秒有一次更改就做一次rdb快照到磁盘。
禁用rdb就是注释掉save行，如果你配置了`save ""`，也可以禁用rdb
下面是默认值
```
save 900 1
save 300 10
save 60 10000
```
### 3.2 `stop-writes-on-bgsave-error yes`
默认情况下，如果RDB快照功能开启而且最后一次rdb快照save失败时，redis会停止接收写请求
这其实就是一种强硬的方式来告知用户数据持久化不正常，否则没有人会知道当前系统出大问题了。
如果后台save进程正常工作了(正常保存了rdb文件)，那么redis会自动的允许写请求。
不过如果你已经设置了一些监控到redis服务器，你可能想要禁用这个功能，这样redis在磁盘出问题时依旧
可以继续处理写请求。只要set `stop-writes-on-bgsave-error yes`
### 3.3 `rdbcompression yes`
使用LZF算法对rdb文件进行压缩，如果要节省一些CPU，可以设置为no。
### 3.4 `rdbchecksum yes`
自从redis 5.0，rdb文件的末尾会设置一个CRC64校验码(循环冗余码)。这可以起到一定的的纠错作用，但是也要
付出10%的性能损失，你可以关闭这个功能来获取最大的性能。
如果rdb文件校验功能关闭，那么系统读取不到检验码时会自动跳过校验。
`rdbchecksum yes`
### 3.5  `dbfilename dump.rdb`
rdb文件名
### 3.6 `dir ./`
工作目录 ，rdb文件会创建在这个目录下，aof文件也一样。
### 3.7 `slaveof <masterip> <masterport>`
主从复制。使用slaveof配置将redis实例变为其他redis服务器的一个拷贝。
* redis的主从复制时异步的，但是当主节点无法连接到给定数量的从节点时，你可以设置主节点停止处理写请求
* 如果主从复制断了一小段时间，redis从节点可以执行一次局部的重新同步，你可能需要设置
	复制的backlog size
* 主从复制时自动的无需用户干预。如果网络中间断了，从节点会自动重连主节点，并发起一次重新同步。
### 3.8 `masterauth <master-password>`
如果主节点有密码，从节点必须配置这个密码，否则主节点拒绝复制请求。
### 3.9 `slave-serve-stale-data yes`
当主从同步失败时，从节点有两种行为：
* 配置为yes，从节点可以继续响应客户端的请求。
* 配置为no，从节点直接报错"SYNC with master in progress"，不过INFO和SALVEOF命令是可以执行的。
### 3.10 `slave-read-only yes`
你可以设置从节点能够处理写请求。向从节点写入一些临时数据有时候是有用的(因为数据在resync后很快
就会删除)，如果配错了也可能会造成一些问题。
2.6版本以后默认都是read-only
read-only不是设计成对抗那些不可信的客户端的。只是怕客户端用错命令。read-only模式下一些管理类命令
还是会输出的。如果要限制这种命令，你可以使用rename-command来重命名那些管理类命令

### 3.11 `repl-diskless-sync no`
主从同步策略：disk或socket。

警告：diskless复制目前只是试验阶段
当出现新的从节点或重连的从节点无法进行增量同步时，就需要做一次全量同步(full synchronization)。
一个RDB文件会从主节点传输到从节点，传输方式有两种：
* disk-backed：主节点创建一个新的进程将RDB文件写到磁盘。然后这个文件会被主进程逐步传送给多个从节点
* diskless:主节点创建一个新的进程，直接将RDB文件写给从节点的socket连接，从头到尾不会碰磁盘。

使用disk-backed复制，在rdb文件生成完毕后，主节点会为每个从节点创建队列来传说RDB文件，直到传输结束。
使用diskless复制，一旦开始传输rdb，当时有多少从节点建立连接，就只能并行传输多少从节点，如果此时有新的从节点发起全量同步，就只能等之前的都传完。
如果使用diskless复制，主节点会在传输之前等待一小段时间(这个时间可以配置)，这样可以让多个从节点到达
，并做并行传输。
如果磁盘贼慢，网络带宽特别好，diskless复制策略效果会更好一些。
### 3.12 `repl-diskless-sync-delay 5`
如果开启了diskless复制，需要配置一个延迟时间，让主节点等待所有从节点都到达。
这是非常重要的，因为一旦开始传输，主节点就无法响应新的从节点的全量复制请求，只能先到队列中等待下一次RDB传输，所以主节点需要等待一段时间，让所有从节点全量复制都到达。
这个延迟时间的单位是秒，默认是5秒。关闭这个特性可以将其设置成0，这样传输总是最快开始。
### 3.13 `repl-ping-slave-period 10`
从节点在一定间隔时间发送ping到主节点。默认是是10秒。
### 3.14 `repl-timeout 60`
这个值对三个场景都有效：
1. 大量的I/O操作，从节点收到主节点的响应时间。
2. 从节点认为主节点的超时时间
3. 主节点认为从节点的超时时间

注意，这个值一定要设置的比`repl-ping-slave-period`大，否则每次心跳检测都超时
### 3.15 `repl-disable-tcp-nodelay no`
在从节点socket 发起SYNC同步后是否需要关闭TCP_NODELAY？
如果选择YES，redis会使用较小的tcp packet和较小的带宽去发送数据到从节点。但是这会让主从复制增加部分延迟，差不多40毫秒，取决于linux kernel配置。
如果选择no，主从复制延迟会稍微减少，但是会消耗更大的网络带宽。
默认我们倾向于低延迟，但是如果网络状况不好的情况时将这个选项置为yes或许是个好方案。
### 3.16 `repl-backlog-size 1mb`
设置主从复制backlog大小。backlog是一个缓冲区。
当主从不同步时，主节点缓存主从复制数据到backlog缓冲区中，当从节点重新连接到主节点时，从节点可以从缓冲区中拿到增量同步数据，并进行增量同步(partitial synchronization)。
backlog越大，允许从节点断线的时间就越长。backlog缓冲区只有在最少有一个从节点连接时才会创建。
### 3.17 `repl-backlog-ttl 3600`
如果主节点再也没有连接到从节点，那个从节点的backlog会被释放。
当从节点断线开始，这个配置的时间就开始计时了。

### 3.18 `slave-priority 100`
这个配置是给哨兵模式用的，当主节点挂掉时，哨兵会选取一个priority最小的从节点去升主，如果某个redis节点的这个值配成0，那么这个节点永远都不会被升为主节点。默认值就是100。

### 3.19 `min-slaves-to-write 3和min-slaves-max-lag 10`
如果lag秒内主节点在线的从节点少于N个，主节点停止接收写请求。
例如10秒内最少3个从节点在线时，主节点才接受写请求，可以用如下配置：
```
min-slaves-to-write 3
min-slaves-max-lag 10
```
将这两个配置任意一个设置为0，就禁用此功能。默认是禁用的。
### 3.20 ``
有多种方式可以显示主节点当前在线的从节点的ip和端口。
例如，info replication 部分，或者在主节点执行ROLE命令。

#
# The listed IP and address normally reported by a slave is obtained
# in the following way:
#
#   IP: The address is auto detected by checking the peer address
#   of the socket used by the slave to connect with the master.
#
#   Port: The port is communicated by the slave during the replication
#   handshake, and is normally the port that the slave is using to
#   list for connections.
#
# However when port forwarding or Network Address Translation (NAT) is
# used, the slave may be actually reachable via different IP and port
# pairs. The following two options can be used by a slave in order to
# report to its master a specific set of IP and port, so that both INFO
# and ROLE will report those values.
#
# There is no need to use both the options if you need to override just
# the port or the IP address.
#
# slave-announce-ip 5.5.5.5
# slave-announce-port 1234

## 4. 安全
### 4.1 `requirepass foobared`
给redis设置密码，因为redis快的一逼，一秒钟攻击者能尝试150000的密码，所以你的密码必须非常强壮
否则很容易被破解。
### 4.2 `rename-command CONFIG ""和rename-command CONFIG b840fc02d524045429941cc15f59e41cb7be6c52`
完全杀掉一个命令就用rename-command CONFIG ""
rename-command CONFIG b840fc02d524045429941cc15f59e41cb7be6c52可以将命令改掉，这样彩笔程序员就不会使用
危险命令了。
注意，如果你把命令给改名了，那么从节点什么的都要统一改名字，否则会有问题。
## 5. 客户端
### 5.1 `maxclients 10000`
设置同一时刻的最大客户端数。
默认值是10000，只要达到最大值，redis会关闭所有新的链接，并且发送一个错误“max number of clients readched”
给客户端。
## 6. 内存管理
### 6.1 maxmemory <bytes>
设置一个内存的最大值。当内存达到最大值之后，redis会按照选择的内存淘汰策略去删除key。
如果redis根据淘汰策略无法删除key，或者淘汰策略是noeviction，客户端发送写请求时redis会开始返回报错，并且不会使用
更多的内存。但是读请求还是会继续支持的。
注意，如果你有很多从节点，那么内存设置不能太大，否则从节点发起全量同步时，output buffer占用的内存也在这个
maxmemory的范围内，例如，最大值配的是4GB，如果内存已经3G了，此时一个从节点发起全量同步，outputbuffer你设置的是2G
这样内存直接就满了，然后就要开始淘汰key，这肯定不是我们想要的。
### 6.2 `maxmemory-policy noeviction`
内存淘汰策略，决定了当redis内存满时如何删除key。
默认值是noeviction。

* volatile-lru -> 在过期key中使用近似LRU驱逐
* allkeys-lru -> 在所有key中使用近似LRU
* volatile-lfu -> 在过期key中使用近似LFU驱逐
* allkeys-lfu -> 在所有key中使用近似LFU
* volatile-random -> 在过期key中随机删除一个
* allkeys-random -> 在所有的key中随机删除一个
* volatile-ttl -> 谁快过期就删谁
* noeviction -> 不删除任何key，直接返回报错

 LRU means Least Recently Used
 LFU means Least Frequently Used

LRU, LFU and volatile-ttl 基于近似随机算法实现。
注意，使用上述策略时，如果没有合适的key去删除时，redis在处理写请求时都会返回报错。

At the date of writing these commands are: set setnx setex append
incr decr rpush lpush rpushx lpushx linsert lset rpoplpush sadd
sinter sinterstore sunion sunionstore sdiff sdiffstore zadd zincrby
zunionstore zinterstore hset hsetnx hmset hincrby incrby decrby
getset mset msetnx exec sort。

### 6.3 `maxmemory-samples 5`
LRU, LFU and minimal TTL algorithms不是精确的算法，是一个近似的算法(主要为了节省内存)，
所以你可以自己权衡速度和精确度。默认redis会检查5个key，选择一个最近最少使用的key，你可以
改变这个数量。
默认的5可以提供不错的结果。你用10会非常接近真实的LRU但是会耗费更多的CPU，用3会更快，但是
就不那么精确了。


## 7. LAZY FREEZING 懒释放
redis有两个删除key的基本命令。一个是DEL，这是一个阻塞的删除。DEL会让redis停止处理新请求
，然后redis会用一种同步的方式去回收DEL要删除的对象的内存。如果这个key对应的是一个非常小的
对象，那么DEL的执行时间会非常短，接近O(1)或者O(log n)。不过，如果key对应的对象很大,redis
就会阻塞很长时间来完成这个命令。
鉴于上述的问题，redis也提供了非阻塞删除命令，例如UNLINK(非阻塞的DEL)和异步的删除策略：
FLUSHALL和FLUSHDB，这样可以在后台进行内存回收。这些命令的执行时间都是常量时间。一个新的线程
会在后台渐进的删除并释放内存。

上面说的那些命令都是用户执行的，具体用哪种命令，取决于用户的场景。
但是redis本身也会因为一些原因去删除key或flush掉整个内存数据库。
除了用户主动删除，redis自己去删除key的场景有以下几个：

* 内存淘汰(eviction)，设置了内存淘汰策略后，为了给新数据清理空间，需要删除被淘汰的数据，
	否则内存就爆了。
* 过期(expire)，当一个key过期时
* key已经存在时的一些边际影响。例如，set一个已经存在的key，旧的value需要被删除，然后设置新的key。
* 主从复制时，从节点执行一个全量同步，从节点之前的内存数据需要被flush掉。
如果你希望上面那四种场景使用异步删除，可以使用如下配置：
```
lazyfree-lazy-eviction no
lazyfree-lazy-expire no
lazyfree-lazy-server-del no
slave-lazy-flush no
```

############################## APPEND ONLY MODE ###############################
## 8. AOF
### 8.1 `appendonly no`
默认情况下，redis异步的dump内存镜像到磁盘(RDB)。这个模式虽然已经很不错了，但是
如果在发起dump之前机器宕机，就会丢失一些数据。
AOF(Append only file)是一种可选的持久化策略提供更好数据安全性。使用默认配置的情况下，
redis最多丢失一秒钟的写入数据，你甚至可以提高级别，让redis最多丢失一次write操作。
AOF和RDB持久化可以同时开启。如果开了AOF，redis总会先加载AOF的文件，因为AOF提供更高的可用性。
### 8.2 `appendfilename "appendonly.aof"`
aof 文件的名称。

### 8.3 `appendfsync everysec`
对操作系统的fsync()调用告诉操作系统将output buffer中的缓冲数据写入到磁盘。有些操作系统会
真正的写磁盘，有一些会尽量去写，也可能会等一下。
redis 支持三种方式：
* no: 不去主动调用fsync()，让操作系统自己决定何时写磁盘
* always：每次write操作之后都调用fsync()，非常慢，但是数据安全性最高。
* everysec:每秒调用一次fsync()，一个折中的策略。

默认就是everysec，一般也是推荐的策略，平衡了速度和数据安全性。
```
appendfsync always
appendfsync everysec
appendfsync no
```
### 8.4 `no-appendfsync-on-rewrite no`
当AOF fsync 策略设置成always或者everysec，而且一个后台的save进程(可能RDB的bgsave进程，也可能是
AOF rewrite进程)正在执行大量磁盘I/O操作，在一些linux配置中，redis可能会对fsync()执行太长的调用。
这个问题目前没什么办法修复，也就是说就算起一个后台进程去做fsync，如果之前已经有进程再做fsync了，后来的调用
会被阻塞。
为了缓和这个问题，可以使用下面的配置，当已经有BGSAVE和BGREWRITEAOF在做fsync()时，就不要再起新进程
了。
如果已经有子进程在做bgsave或者其他的磁盘操作时，redis无法继续写aof文件，等同于appendsync none。
在实际情况中，这意味着可能会丢失多达30秒的日志。也就是说，这是会丢数据的，如果对数据及其敏感，要注意这个问题。
如果你有延迟类问题，可以设置成yes,否则设置为no，这样能保证数据的安全性最高，极少丢数据。

### 8.5 `auto-aof-rewrite-percentage 100和auto-aof-rewrite-min-size 64mb`
自动重写aof文件。当aof文件增大到某个百分比时，redis会重写aof文件。
redis会记住上次rewrite后aof文件的大小（如果启动后还没发生过rewrite，那么会使用aof原始大小）。
这个size大小会和当前aof文件的size大小做比较。如果当前size大于指定的百分比，就做rewrite。
并且，还要指定最小的size，如果当前aof文件小于最小size，不会触发rewrite，这是为了防止文件其实很小，但是
已经符合增长百分比时的多余的rewrite操作。

 Also
# you need to specify a minimal size for the AOF file to be rewritten, this
# is useful to avoid rewriting the AOF file even if the percentage increase
# is reached but it is still pretty small.
#
# Specify a percentage of zero in order to disable the automatic AOF
# rewrite feature.

auto-aof-rewrite-percentage 100
auto-aof-rewrite-min-size 64mb

# An AOF file may be found to be truncated at the end during the Redis
# startup process, when the AOF data gets loaded back into memory.
# This may happen when the system where Redis is running
# crashes, especially when an ext4 filesystem is mounted without the
# data=ordered option (however this can't happen when Redis itself
# crashes or aborts but the operating system still works correctly).
#
# Redis can either exit with an error when this happens, or load as much
# data as possible (the default now) and start if the AOF file is found
# to be truncated at the end. The following option controls this behavior.
#
# If aof-load-truncated is set to yes, a truncated AOF file is loaded and
# the Redis server starts emitting a log to inform the user of the event.
# Otherwise if the option is set to no, the server aborts with an error
# and refuses to start. When the option is set to no, the user requires
# to fix the AOF file using the "redis-check-aof" utility before to restart
# the server.
#
# Note that if the AOF file will be found to be corrupted in the middle
# the server will still exit with an error. This option only applies when
# Redis will try to read more data from the AOF file but not enough bytes
# will be found.
aof-load-truncated yes

# When rewriting the AOF file, Redis is able to use an RDB preamble in the
# AOF file for faster rewrites and recoveries. When this option is turned
# on the rewritten AOF file is composed of two different stanzas:
#
#   [RDB file][AOF tail]
#
# When loading Redis recognizes that the AOF file starts with the "REDIS"
# string and loads the prefixed RDB file, and continues loading the AOF
# tail.
#
# This is currently turned off by default in order to avoid the surprise
# of a format change, but will at some point be used as the default.
aof-use-rdb-preamble no

################################ LUA SCRIPTING  ###############################

# Max execution time of a Lua script in milliseconds.
#
# If the maximum execution time is reached Redis will log that a script is
# still in execution after the maximum allowed time and will start to
# reply to queries with an error.
#
# When a long running script exceeds the maximum execution time only the
# SCRIPT KILL and SHUTDOWN NOSAVE commands are available. The first can be
# used to stop a script that did not yet called write commands. The second
# is the only way to shut down the server in the case a write command was
# already issued by the script but the user doesn't want to wait for the natural
# termination of the script.
#
# Set it to 0 or a negative value for unlimited execution without warnings.
lua-time-limit 5000

################################ REDIS CLUSTER  ###############################
#
# ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
# WARNING EXPERIMENTAL: Redis Cluster is considered to be stable code, however
# in order to mark it as "mature" we need to wait for a non trivial percentage
# of users to deploy it in production.
# ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
#
# Normal Redis instances can't be part of a Redis Cluster; only nodes that are
# started as cluster nodes can. In order to start a Redis instance as a
# cluster node enable the cluster support uncommenting the following:
#
# cluster-enabled yes

# Every cluster node has a cluster configuration file. This file is not
# intended to be edited by hand. It is created and updated by Redis nodes.
# Every Redis Cluster node requires a different cluster configuration file.
# Make sure that instances running in the same system do not have
# overlapping cluster configuration file names.
#
# cluster-config-file nodes-6379.conf

# Cluster node timeout is the amount of milliseconds a node must be unreachable
# for it to be considered in failure state.
# Most other internal time limits are multiple of the node timeout.
#
# cluster-node-timeout 15000

# A slave of a failing master will avoid to start a failover if its data
# looks too old.
#
# There is no simple way for a slave to actually have an exact measure of
# its "data age", so the following two checks are performed:
#
# 1) If there are multiple slaves able to failover, they exchange messages
#    in order to try to give an advantage to the slave with the best
#    replication offset (more data from the master processed).
#    Slaves will try to get their rank by offset, and apply to the start
#    of the failover a delay proportional to their rank.
#
# 2) Every single slave computes the time of the last interaction with
#    its master. This can be the last ping or command received (if the master
#    is still in the "connected" state), or the time that elapsed since the
#    disconnection with the master (if the replication link is currently down).
#    If the last interaction is too old, the slave will not try to failover
#    at all.
#
# The point "2" can be tuned by user. Specifically a slave will not perform
# the failover if, since the last interaction with the master, the time
# elapsed is greater than:
#
#   (node-timeout * slave-validity-factor) + repl-ping-slave-period
#
# So for example if node-timeout is 30 seconds, and the slave-validity-factor
# is 10, and assuming a default repl-ping-slave-period of 10 seconds, the
# slave will not try to failover if it was not able to talk with the master
# for longer than 310 seconds.
#
# A large slave-validity-factor may allow slaves with too old data to failover
# a master, while a too small value may prevent the cluster from being able to
# elect a slave at all.
#
# For maximum availability, it is possible to set the slave-validity-factor
# to a value of 0, which means, that slaves will always try to failover the
# master regardless of the last time they interacted with the master.
# (However they'll always try to apply a delay proportional to their
# offset rank).
#
# Zero is the only value able to guarantee that when all the partitions heal
# the cluster will always be able to continue.
#
# cluster-slave-validity-factor 10

# Cluster slaves are able to migrate to orphaned masters, that are masters
# that are left without working slaves. This improves the cluster ability
# to resist to failures as otherwise an orphaned master can't be failed over
# in case of failure if it has no working slaves.
#
# Slaves migrate to orphaned masters only if there are still at least a
# given number of other working slaves for their old master. This number
# is the "migration barrier". A migration barrier of 1 means that a slave
# will migrate only if there is at least 1 other working slave for its master
# and so forth. It usually reflects the number of slaves you want for every
# master in your cluster.
#
# Default is 1 (slaves migrate only if their masters remain with at least
# one slave). To disable migration just set it to a very large value.
# A value of 0 can be set but is useful only for debugging and dangerous
# in production.
#
# cluster-migration-barrier 1

# By default Redis Cluster nodes stop accepting queries if they detect there
# is at least an hash slot uncovered (no available node is serving it).
# This way if the cluster is partially down (for example a range of hash slots
# are no longer covered) all the cluster becomes, eventually, unavailable.
# It automatically returns available as soon as all the slots are covered again.
#
# However sometimes you want the subset of the cluster which is working,
# to continue to accept queries for the part of the key space that is still
# covered. In order to do so, just set the cluster-require-full-coverage
# option to no.
#
# cluster-require-full-coverage yes

# This option, when set to yes, prevents slaves from trying to failover its
# master during master failures. However the master can still perform a
# manual failover, if forced to do so.
#
# This is useful in different scenarios, especially in the case of multiple
# data center operations, where we want one side to never be promoted if not
# in the case of a total DC failure.
#
# cluster-slave-no-failover no

# In order to setup your cluster make sure to read the documentation
# available at http://redis.io web site.

########################## CLUSTER DOCKER/NAT support  ########################

# In certain deployments, Redis Cluster nodes address discovery fails, because
# addresses are NAT-ted or because ports are forwarded (the typical case is
# Docker and other containers).
#
# In order to make Redis Cluster working in such environments, a static
# configuration where each node knows its public address is needed. The
# following two options are used for this scope, and are:
#
# * cluster-announce-ip
# * cluster-announce-port
# * cluster-announce-bus-port
#
# Each instruct the node about its address, client port, and cluster message
# bus port. The information is then published in the header of the bus packets
# so that other nodes will be able to correctly map the address of the node
# publishing the information.
#
# If the above options are not used, the normal Redis Cluster auto-detection
# will be used instead.
#
# Note that when remapped, the bus port may not be at the fixed offset of
# clients port + 10000, so you can specify any port and bus-port depending
# on how they get remapped. If the bus-port is not set, a fixed offset of
# 10000 will be used as usually.
#
# Example:
#
# cluster-announce-ip 10.1.1.5
# cluster-announce-port 6379
# cluster-announce-bus-port 6380

################################## SLOW LOG ###################################

# The Redis Slow Log is a system to log queries that exceeded a specified
# execution time. The execution time does not include the I/O operations
# like talking with the client, sending the reply and so forth,
# but just the time needed to actually execute the command (this is the only
# stage of command execution where the thread is blocked and can not serve
# other requests in the meantime).
#
# You can configure the slow log with two parameters: one tells Redis
# what is the execution time, in microseconds, to exceed in order for the
# command to get logged, and the other parameter is the length of the
# slow log. When a new command is logged the oldest one is removed from the
# queue of logged commands.

# The following time is expressed in microseconds, so 1000000 is equivalent
# to one second. Note that a negative number disables the slow log, while
# a value of zero forces the logging of every command.
slowlog-log-slower-than 10000

# There is no limit to this length. Just be aware that it will consume memory.
# You can reclaim memory used by the slow log with SLOWLOG RESET.
slowlog-max-len 128

################################ LATENCY MONITOR ##############################

# The Redis latency monitoring subsystem samples different operations
# at runtime in order to collect data related to possible sources of
# latency of a Redis instance.
#
# Via the LATENCY command this information is available to the user that can
# print graphs and obtain reports.
#
# The system only logs operations that were performed in a time equal or
# greater than the amount of milliseconds specified via the
# latency-monitor-threshold configuration directive. When its value is set
# to zero, the latency monitor is turned off.
#
# By default latency monitoring is disabled since it is mostly not needed
# if you don't have latency issues, and collecting data has a performance
# impact, that while very small, can be measured under big load. Latency
# monitoring can easily be enabled at runtime using the command
# "CONFIG SET latency-monitor-threshold <milliseconds>" if needed.
latency-monitor-threshold 0

############################# EVENT NOTIFICATION ##############################

# Redis can notify Pub/Sub clients about events happening in the key space.
# This feature is documented at http://redis.io/topics/notifications
#
# For instance if keyspace events notification is enabled, and a client
# performs a DEL operation on key "foo" stored in the Database 0, two
# messages will be published via Pub/Sub:
#
# PUBLISH __keyspace@0__:foo del
# PUBLISH __keyevent@0__:del foo
#
# It is possible to select the events that Redis will notify among a set
# of classes. Every class is identified by a single character:
#
#  K     Keyspace events, published with __keyspace@<db>__ prefix.
#  E     Keyevent events, published with __keyevent@<db>__ prefix.
#  g     Generic commands (non-type specific) like DEL, EXPIRE, RENAME, ...
#  $     String commands
#  l     List commands
#  s     Set commands
#  h     Hash commands
#  z     Sorted set commands
#  x     Expired events (events generated every time a key expires)
#  e     Evicted events (events generated when a key is evicted for maxmemory)
#  A     Alias for g$lshzxe, so that the "AKE" string means all the events.
#
#  The "notify-keyspace-events" takes as argument a string that is composed
#  of zero or multiple characters. The empty string means that notifications
#  are disabled.
#
#  Example: to enable list and generic events, from the point of view of the
#           event name, use:
#
#  notify-keyspace-events Elg
#
#  Example 2: to get the stream of the expired keys subscribing to channel
#             name __keyevent@0__:expired use:
#
#  notify-keyspace-events Ex
#
#  By default all notifications are disabled because most users don't need
#  this feature and the feature has some overhead. Note that if you don't
#  specify at least one of K or E, no events will be delivered.
notify-keyspace-events ""

############################### ADVANCED CONFIG ###############################

# Hashes are encoded using a memory efficient data structure when they have a
# small number of entries, and the biggest entry does not exceed a given
# threshold. These thresholds can be configured using the following directives.
hash-max-ziplist-entries 512
hash-max-ziplist-value 64

# Lists are also encoded in a special way to save a lot of space.
# The number of entries allowed per internal list node can be specified
# as a fixed maximum size or a maximum number of elements.
# For a fixed maximum size, use -5 through -1, meaning:
# -5: max size: 64 Kb  <-- not recommended for normal workloads
# -4: max size: 32 Kb  <-- not recommended
# -3: max size: 16 Kb  <-- probably not recommended
# -2: max size: 8 Kb   <-- good
# -1: max size: 4 Kb   <-- good
# Positive numbers mean store up to _exactly_ that number of elements
# per list node.
# The highest performing option is usually -2 (8 Kb size) or -1 (4 Kb size),
# but if your use case is unique, adjust the settings as necessary.
list-max-ziplist-size -2

# Lists may also be compressed.
# Compress depth is the number of quicklist ziplist nodes from *each* side of
# the list to *exclude* from compression.  The head and tail of the list
# are always uncompressed for fast push/pop operations.  Settings are:
# 0: disable all list compression
# 1: depth 1 means "don't start compressing until after 1 node into the list,
#    going from either the head or tail"
#    So: [head]->node->node->...->node->[tail]
#    [head], [tail] will always be uncompressed; inner nodes will compress.
# 2: [head]->[next]->node->node->...->node->[prev]->[tail]
#    2 here means: don't compress head or head->next or tail->prev or tail,
#    but compress all nodes between them.
# 3: [head]->[next]->[next]->node->node->...->node->[prev]->[prev]->[tail]
# etc.
list-compress-depth 0

# Sets have a special encoding in just one case: when a set is composed
# of just strings that happen to be integers in radix 10 in the range
# of 64 bit signed integers.
# The following configuration setting sets the limit in the size of the
# set in order to use this special memory saving encoding.
set-max-intset-entries 512

# Similarly to hashes and lists, sorted sets are also specially encoded in
# order to save a lot of space. This encoding is only used when the length and
# elements of a sorted set are below the following limits:
zset-max-ziplist-entries 128
zset-max-ziplist-value 64

# HyperLogLog sparse representation bytes limit. The limit includes the
# 16 bytes header. When an HyperLogLog using the sparse representation crosses
# this limit, it is converted into the dense representation.
#
# A value greater than 16000 is totally useless, since at that point the
# dense representation is more memory efficient.
#
# The suggested value is ~ 3000 in order to have the benefits of
# the space efficient encoding without slowing down too much PFADD,
# which is O(N) with the sparse encoding. The value can be raised to
# ~ 10000 when CPU is not a concern, but space is, and the data set is
# composed of many HyperLogLogs with cardinality in the 0 - 15000 range.
hll-sparse-max-bytes 3000

# Active rehashing uses 1 millisecond every 100 milliseconds of CPU time in
# order to help rehashing the main Redis hash table (the one mapping top-level
# keys to values). The hash table implementation Redis uses (see dict.c)
# performs a lazy rehashing: the more operation you run into a hash table
# that is rehashing, the more rehashing "steps" are performed, so if the
# server is idle the rehashing is never complete and some more memory is used
# by the hash table.
#
# The default is to use this millisecond 10 times every second in order to
# actively rehash the main dictionaries, freeing memory when possible.
#
# If unsure:
# use "activerehashing no" if you have hard latency requirements and it is
# not a good thing in your environment that Redis can reply from time to time
# to queries with 2 milliseconds delay.
#
# use "activerehashing yes" if you don't have such hard requirements but
# want to free memory asap when possible.
activerehashing yes

# The client output buffer limits can be used to force disconnection of clients
# that are not reading data from the server fast enough for some reason (a
# common reason is that a Pub/Sub client can't consume messages as fast as the
# publisher can produce them).
#
# The limit can be set differently for the three different classes of clients:
#
# normal -> normal clients including MONITOR clients
# slave  -> slave clients
# pubsub -> clients subscribed to at least one pubsub channel or pattern
#
# The syntax of every client-output-buffer-limit directive is the following:
#
# client-output-buffer-limit <class> <hard limit> <soft limit> <soft seconds>
#
# A client is immediately disconnected once the hard limit is reached, or if
# the soft limit is reached and remains reached for the specified number of
# seconds (continuously).
# So for instance if the hard limit is 32 megabytes and the soft limit is
# 16 megabytes / 10 seconds, the client will get disconnected immediately
# if the size of the output buffers reach 32 megabytes, but will also get
# disconnected if the client reaches 16 megabytes and continuously overcomes
# the limit for 10 seconds.
#
# By default normal clients are not limited because they don't receive data
# without asking (in a push way), but just after a request, so only
# asynchronous clients may create a scenario where data is requested faster
# than it can read.
#
# Instead there is a default limit for pubsub and slave clients, since
# subscribers and slaves receive data in a push fashion.
#
# Both the hard or the soft limit can be disabled by setting them to zero.
client-output-buffer-limit normal 0 0 0
client-output-buffer-limit slave 256mb 64mb 60
client-output-buffer-limit pubsub 32mb 8mb 60

# Client query buffers accumulate new commands. They are limited to a fixed
# amount by default in order to avoid that a protocol desynchronization (for
# instance due to a bug in the client) will lead to unbound memory usage in
# the query buffer. However you can configure it here if you have very special
# needs, such us huge multi/exec requests or alike.
#
# client-query-buffer-limit 1gb

# In the Redis protocol, bulk requests, that are, elements representing single
# strings, are normally limited ot 512 mb. However you can change this limit
# here.
#
# proto-max-bulk-len 512mb

# Redis calls an internal function to perform many background tasks, like
# closing connections of clients in timeout, purging expired keys that are
# never requested, and so forth.
#
# Not all tasks are performed with the same frequency, but Redis checks for
# tasks to perform according to the specified "hz" value.
#
# By default "hz" is set to 10. Raising the value will use more CPU when
# Redis is idle, but at the same time will make Redis more responsive when
# there are many keys expiring at the same time, and timeouts may be
# handled with more precision.
#
# The range is between 1 and 500, however a value over 100 is usually not
# a good idea. Most users should use the default of 10 and raise this up to
# 100 only in environments where very low latency is required.
hz 10

# When a child rewrites the AOF file, if the following option is enabled
# the file will be fsync-ed every 32 MB of data generated. This is useful
# in order to commit the file to the disk more incrementally and avoid
# big latency spikes.
aof-rewrite-incremental-fsync yes

# Redis LFU eviction (see maxmemory setting) can be tuned. However it is a good
# idea to start with the default settings and only change them after investigating
# how to improve the performances and how the keys LFU change over time, which
# is possible to inspect via the OBJECT FREQ command.
#
# There are two tunable parameters in the Redis LFU implementation: the
# counter logarithm factor and the counter decay time. It is important to
# understand what the two parameters mean before changing them.
#
# The LFU counter is just 8 bits per key, it's maximum value is 255, so Redis
# uses a probabilistic increment with logarithmic behavior. Given the value
# of the old counter, when a key is accessed, the counter is incremented in
# this way:
#
# 1. A random number R between 0 and 1 is extracted.
# 2. A probability P is calculated as 1/(old_value*lfu_log_factor+1).
# 3. The counter is incremented only if R < P.
#
# The default lfu-log-factor is 10. This is a table of how the frequency
# counter changes with a different number of accesses with different
# logarithmic factors:
#
# +--------+------------+------------+------------+------------+------------+
# | factor | 100 hits   | 1000 hits  | 100K hits  | 1M hits    | 10M hits   |
# +--------+------------+------------+------------+------------+------------+
# | 0      | 104        | 255        | 255        | 255        | 255        |
# +--------+------------+------------+------------+------------+------------+
# | 1      | 18         | 49         | 255        | 255        | 255        |
# +--------+------------+------------+------------+------------+------------+
# | 10     | 10         | 18         | 142        | 255        | 255        |
# +--------+------------+------------+------------+------------+------------+
# | 100    | 8          | 11         | 49         | 143        | 255        |
# +--------+------------+------------+------------+------------+------------+
#
# NOTE: The above table was obtained by running the following commands:
#
#   redis-benchmark -n 1000000 incr foo
#   redis-cli object freq foo
#
# NOTE 2: The counter initial value is 5 in order to give new objects a chance
# to accumulate hits.
#
# The counter decay time is the time, in minutes, that must elapse in order
# for the key counter to be divided by two (or decremented if it has a value
# less <= 10).
#
# The default value for the lfu-decay-time is 1. A Special value of 0 means to
# decay the counter every time it happens to be scanned.
#
# lfu-log-factor 10
# lfu-decay-time 1

########################### ACTIVE DEFRAGMENTATION #######################
#
# WARNING THIS FEATURE IS EXPERIMENTAL. However it was stress tested
# even in production and manually tested by multiple engineers for some
# time.
#
# What is active defragmentation?
# -------------------------------
#
# Active (online) defragmentation allows a Redis server to compact the
# spaces left between small allocations and deallocations of data in memory,
# thus allowing to reclaim back memory.
#
# Fragmentation is a natural process that happens with every allocator (but
# less so with Jemalloc, fortunately) and certain workloads. Normally a server
# restart is needed in order to lower the fragmentation, or at least to flush
# away all the data and create it again. However thanks to this feature
# implemented by Oran Agra for Redis 4.0 this process can happen at runtime
# in an "hot" way, while the server is running.
#
# Basically when the fragmentation is over a certain level (see the
# configuration options below) Redis will start to create new copies of the
# values in contiguous memory regions by exploiting certain specific Jemalloc
# features (in order to understand if an allocation is causing fragmentation
# and to allocate it in a better place), and at the same time, will release the
# old copies of the data. This process, repeated incrementally for all the keys
# will cause the fragmentation to drop back to normal values.
#
# Important things to understand:
#
# 1. This feature is disabled by default, and only works if you compiled Redis
#    to use the copy of Jemalloc we ship with the source code of Redis.
#    This is the default with Linux builds.
#
# 2. You never need to enable this feature if you don't have fragmentation
#    issues.
#
# 3. Once you experience fragmentation, you can enable this feature when
#    needed with the command "CONFIG SET activedefrag yes".
#
# The configuration parameters are able to fine tune the behavior of the
# defragmentation process. If you are not sure about what they mean it is
# a good idea to leave the defaults untouched.

# Enabled active defragmentation
# activedefrag yes

# Minimum amount of fragmentation waste to start active defrag
# active-defrag-ignore-bytes 100mb

# Minimum percentage of fragmentation to start active defrag
# active-defrag-threshold-lower 10

# Maximum percentage of fragmentation at which we use maximum effort
# active-defrag-threshold-upper 100

# Minimal effort for defrag in CPU percentage
# active-defrag-cycle-min 25

# Maximal effort for defrag in CPU percentage
# active-defrag-cycle-max 75

