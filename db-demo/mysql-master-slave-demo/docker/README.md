# Setup MySQL master and slave

## Step 1: update.sh

## ker, mysql and heredoc

sudo docker exec -i docker_mysql-master_1 mysql --password="root" <<EOF
show databases;
EOF


## Prepare a user for replication

user-repl.sh

## Check status

sudo docker exec -it docker_mysql-slave_1 mysql --password="root"

```
show master status;
+----------------------------------+----------+--------------+------------------+-------------------+
| File                             | Position | Binlog_Do_DB | Binlog_Ignore_DB | Executed_Gtid_Set |
+----------------------------------+----------+--------------+------------------+-------------------+
| replicas-mysql-slave1-bin.000002 |      156 |              | mysql            |                   |
+----------------------------------+----------+--------------+------------------+-------------------+
1 row in set (0.00 sec)
```

sudo docker exec -it docker_mysql-master_1 mysql --password="root"

```
show master status;
+---------------------------+----------+--------------+------------------+-------------------+
| File                      | Position | Binlog_Do_DB | Binlog_Ignore_DB | Executed_Gtid_Set |
+---------------------------+----------+--------------+------------------+-------------------+
| replicas-mysql-bin.000002 |      156 |              | mysql            |                   |
+---------------------------+----------+--------------+------------------+-------------------+
1 row in set (0.00 sec)
```

## sudo docker exec -it docker_mysql-slave_1 mysql --password="root"

```
CHANGE MASTER TO

    MASTER_HOST='docker_mysql-master_1',

    MASTER_USER='repl',

    MASTER_PASSWORD='password',

    MASTER_LOG_FILE='replicas-mysql-bin.000003',

    MASTER_LOG_POS=689;

stop slave;
start slave;
show slave status\G;
```


## Slave fails to connect master

```
Last_IO_Error: error connecting to master 'root@mysql-master:3306' - retry-time: 60 retries: 1 message: Host 'docker_mysql-slave_1.docker_default' is not allowed to connect to this MySQL server
```

```
Last_IO_Error: error connecting to master 'repl@docker_mysql-master_1:3306' - retry-time: 60 retries: 1 message: Authentication plugin 'caching_sha2_password' reported error: Authentication requires secure connection.
```
"--get-server-public-key" can resolve the above error.