#!/usr/bin/env bash

cd $(dirname $0)

sudo docker exec -i docker_mysql-slave_1 mysql --password="root" <<EOF
show master status\G;
show slave status\G;
EOF
