#!/usr/bin/env bash

cd $(dirname $0)

$HOME/bin/mysql-router-8.0.27-linux-glibc2.17-x86_64-minimal/bin/mysqlrouter --config $PWD/mysqlrouter.conf
