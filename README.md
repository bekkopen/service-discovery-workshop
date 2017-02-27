# service-discovery-workshop


Initial setup
-------------

Start five virtual servers with

```bash
vagrant up
```

Start a consul server on the master node

```bash
vagrant ssh master
nohup consul agent -server -bootstrap-expect 1 -data-dir /tmp/consul -config-dir /etc/consul.d/ -ui-dir /opt/consul-web/ -bind 172.20.100.2 -client 0.0.0.0 -node master &
```

Start a consul agent on an all the application nodes, and connect it to the master

Have a look at the Web UI on http://localhost:8500/ui

```bash
### Setup service1
vagrant ssh service1
nohup consul agent -data-dir /tmp/consul -config-dir /etc/consul.d/ -bind 172.20.100.5 -node service1 &
sleep 2 # Wait for consul to start
consul join 172.20.100.2
consul members # list all members in the cluster
exit

# Setup service2
vagrant ssh service2
nohup consul agent -data-dir /tmp/consul -config-dir /etc/consul.d/ -bind 172.20.100.6 -node service2 &
sleep 2 # Wait for consul to start
consul join 172.20.100.2
consul members # list all members in the cluster
exit

# Setup webapp1
vagrant ssh webapp1
nohup consul agent -data-dir /tmp/consul -config-dir /etc/consul.d/ -bind 172.20.100.7 -node webapp1 &
sleep 2 # Wait for consul to start
consul join 172.20.100.2
consul members # list all members in the cluster
exit

# Setup webapp2
vagrant ssh webapp2
nohup consul agent -data-dir /tmp/consul -config-dir /etc/consul.d/ -bind 172.20.100.8 -node webapp2 &
sleep 2 # Wait for consul to start
consul join 172.20.100.2
consul members # list all members in the cluster
exit
```

# The backend

Run ```curl http://localhost:8080/backend/info``` on webapp1 and webapp2 to check if they are running.


# Healthchecks

All apps configured with health endpoints

curl http://localhost:8081/manage/health
