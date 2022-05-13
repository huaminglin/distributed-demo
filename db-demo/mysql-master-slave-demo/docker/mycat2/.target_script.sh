#!/usr/bin/env bash

cd $(dirname $0)

sudo docker exec -i docker_mysql-master_1 mysql --password="root" <<EOF
CREATE USER mycat identified by '123456';
GRANT XA_RECOVER_ADMIN ON *.* TO 'mycat'@'%';
GRANT ALL ON *.* to mycat;
EOF

java -Dfile.encoding=UTF-8 -DMYCAT_HOME=$PWD/conf -jar $HOME/source/Mycat2/mycat2/target/mycat2-1.21-release-jar-with-dependencies.jar
