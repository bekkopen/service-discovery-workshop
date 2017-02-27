#!/bin/bash

CONSUL=consul_0.7.4_linux_amd64.zip

wget -q -nc "https://releases.hashicorp.com/consul/0.7.4/${CONSUL}"
sudo apt-get update

sudo apt-get -y install unzip

unzip "${CONSUL}"
sudo mkdir -p /usr/local/bin
sudo cp consul /usr/local/bin/
sudo chmod 755 /usr/local/bin/consul

sudo mkdir -p /etc/consul.d
sudo chown ubuntu /etc/consul.d

### Install Web-gui

WEB_GUI="consul_0.7.4_web_ui.zip"

wget -q -nc "https://releases.hashicorp.com/consul/0.7.4/${WEB_GUI}"
sudo mkdir -p /opt/consul-web
sudo unzip "${WEB_GUI}" -d /opt/consul-web/
sudo chown -R ubuntu /opt/consul-web/

### Install consul-template

CONSUL_TEMPLATE="consul-template_0.18.1_linux_amd64.zip"

wget -q -nv "https://releases.hashicorp.com/consul-template/0.18.1/${CONSUL_TEMPLATE}"
unzip "${CONSUL_TEMPLATE}"
sudo cp consul-template /usr/local/bin/
sudo chmod 755 /usr/local/bin/consul-template

