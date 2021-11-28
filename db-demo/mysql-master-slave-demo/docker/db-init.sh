#!/usr/bin/env bash

cd $(dirname $0)

# Create users for Java clients

sudo docker exec -i docker_mysql-master_1 mysql --password="root" <<EOF
CREATE USER 'client'@'%' IDENTIFIED BY 'client';
GRANT ALL PRIVILEGES ON *.* TO 'client'@'%';FLUSH PRIVILEGES;
EOF

sudo docker exec -i docker_mysql-slave_1 mysql --password="root" <<EOF
CREATE USER 'client'@'%' IDENTIFIED BY 'client';
GRANT ALL PRIVILEGES ON *.* TO 'client'@'%';FLUSH PRIVILEGES;
EOF
