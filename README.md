# service-discovery-workshop

![systems overview](systems-overview.png)

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

## 1. Register services

The first exercise is to register our webapplications serving API for our
frontend. We will use [Consul's service
files](https://www.consul.io/intro/getting-started/services.html). The
`/etc/consul.d` directory is already present.

Register the webapp using the JSON file. The application is running on port
8080. The file must be created on the node the application is running on.

Use `vagrant ssh webapp1` and `vagrant ssh webapp2` to connect to the two
servers running the application.

You should also start the application by running `backend.sh start`.

Use the [Consul Web
API](https://www.consul.io/docs/agent/http/catalog.html#catalog_services) to
query and check if you services has been registered. The easiest way is to use
curl from inside one of the application servers. If you called you service
`backend`, the command should be: `curl
http://localhost:8500/v1/catalog/service/backend`

We also also installed Consul UI for you. Open a browser on
(http://localhost:8500/) to see you registered service.

# The backend

Run ```curl http://localhost:8080/backend/info``` on webapp1 and webapp2 to check if they are running.


# Healthchecks

All apps configured with health endpoints

curl http://localhost:8081/manage/health
