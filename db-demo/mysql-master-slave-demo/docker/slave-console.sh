#!/usr/bin/env bash

cd $(dirname $0)

sudo docker exec -it docker_mysql-slave_1 mysql --password="root"
