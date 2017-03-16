#!/usr/bin/env bash

sudo cp /vagrant/provision/haproxy.cfg /etc/haproxy/haproxy.cfg

sudo service haproxy reload
