#!/usr/bin/env bash

sudo apt-get install --yes haproxy

sudo cp /vagrant/provision/haproxy.cfg /etc/haproxy/haproxy.cfg

sudo service haproxy reload
