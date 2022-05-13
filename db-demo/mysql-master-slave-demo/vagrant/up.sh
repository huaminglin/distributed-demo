#!/usr/bin/env bash

cd $(dirname $0)

export VAGRANT_HOME=/media/$USER/t5h-ext4/vagrant_home

vagrant up
