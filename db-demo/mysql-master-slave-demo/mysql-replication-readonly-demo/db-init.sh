#!/usr/bin/env bash

cd $(dirname $0)

sudo docker exec -i docker_mysql-master_1 mysql --password="root" <<EOF
use db1;
create table goods(code varchar(80), name varchar(80));
EOF
