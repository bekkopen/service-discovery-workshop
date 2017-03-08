#!/usr/bin/env bash

sudo cp -r /vagrant/frontend/src/* /var/www/html
sudo mkdir -p /etc/nginx/sites-available/
sudo cp /vagrant/provision/nginx-site /etc/nginx/sites-available/default

sudo apt-get install --yes nginx

sudo service nginx reload
