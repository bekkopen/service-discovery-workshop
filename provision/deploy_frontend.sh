#!/usr/bin/env bash

sudo service haproxy stop

sudo apt-get install --yes nginx

sudo cp -r /vagrant/frontend/src/* /var/www/html
sudo cp /vagrant/provision/nginx-site /etc/nginx/sites-available/default

sudo service nginx reload
sudo service haproxy start
