# Setup MySQL master and slave

## Step 1: update.sh and db-init.sh

## Docker, mysql and heredoc

sudo docker exec -i docker_mysql-master_1 mysql --password="root" <<EOF
show databases;
EOF


## Step 2: Prepare a user for replication

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

## register-slave.sh

## Slave fails to connect master

```
Last_IO_Error: error connecting to master 'root@mysql-master:3306' - retry-time: 60 retries: 1 message: Host 'docker_mysql-slave_1.docker_default' is not allowed to connect to this MySQL server
```

```
Last_IO_Error: error connecting to master 'repl@docker_mysql-master_1:3306' - retry-time: 60 retries: 1 message: Authentication plugin 'caching_sha2_password' reported error: Authentication requires secure connection.
```
"--get-server-public-key" can resolve the above error.

## sudo docker exec -it docker_mysql-master_1 bash

## mysqlbinlog

Download MySQL binary form https://dev.mysql.com/downloads/mysql/.

Mount it to Docker container


sudo docker exec -it docker_mysql-master_1 find / -iname "*-bin.*"
/var/lib/mysql/replicas-mysql-bin.000002
/var/lib/mysql/replicas-mysql-bin.000003
/var/lib/mysql/replicas-mysql-bin.000001
/var/lib/mysql/replicas-mysql-bin.index

sudo docker exec -it docker_mysql-master_1 /mybin/bin/mysqlbinlog /var/lib/mysql/replicas-mysql-bin.000003
