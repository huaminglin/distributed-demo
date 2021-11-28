#!/usr/bin/env bash

cd $(dirname $0)

sudo docker exec -i docker_mysql-slave_1 mysql --password="root" <<EOF

CREATE USER 'repl'@'%' IDENTIFIED BY 'password';

GRANT REPLICATION SLAVE ON *.* TO 'repl'@'%';
EOF

# Setup secure public key from slave to master
sudo docker exec -i docker_mysql-slave_1 mysql -h docker_mysql-master_1 -u repl --password="password" --get-server-public-key <<EOF
show databases;
EOF

# Show master status
sudo docker exec -i docker_mysql-master_1 mysql --password="root" <<EOF
show master status;
EOF
