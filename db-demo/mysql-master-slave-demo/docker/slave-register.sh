#!/usr/bin/env bash

cd $(dirname $0)

sudo docker exec -i docker_mysql-slave_1 mysql --password="root" <<EOF
CHANGE MASTER TO
    MASTER_HOST='docker_mysql-master_1',
    MASTER_USER='repl',
    MASTER_PASSWORD='password',
    MASTER_LOG_FILE='replicas-mysql-bin.000003',
    MASTER_LOG_POS=1388;
stop slave;
start slave;
show slave status\G;
EOF
