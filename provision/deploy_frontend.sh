#!/usr/bin/env bash

sudo apt-get install --yes nginx

sudo cp -r /vagrant/frontend/src/* /usr/share/nginx/html
