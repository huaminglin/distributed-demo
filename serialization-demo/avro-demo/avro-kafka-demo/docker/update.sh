#!/usr/bin/env bash

cd $(dirname $0)

sudo docker-compose down
sudo docker-compose up --no-start
sudo docker-compose start
