# -*- mode: ruby -*-
# vi: set ft=ruby :

# Vagrantfile API/syntax version. Don't touch unless you know what you're doing!
VAGRANTFILE_API_VERSION = "2"

Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|
  config.vm.box = "ubuntu/trusty64"

  # Disable automatic box update checking. If you disable this, then
  # boxes will only be checked for updates when the user runs
  # `vagrant box outdated`. This is not recommended.
  # config.vm.box_check_update = false

  # Create a forwarded port mapping which allows access to a specific port
  # within the machine from a port on the host machine. In the example below,
  # accessing "localhost:8080" will access port 80 on the guest machine.
  # config.vm.network "forwarded_port", guest: 80, host: 8080

  config.vm.define :master do |master|
    master.vm.network "private_network", ip: "172.20.100.2"
    master.vm.network "forwarded_port", guest: 8500, host: 8500
    master.vm.provision "shell", path: "provision/install_consul.sh"
    master.vm.hostname = "master"
  end
  config.vm.define :backend1 do |master|
    master.vm.network "private_network", ip: "172.20.100.5"
    master.vm.provision "shell", path: "provision/install_consul.sh"
    master.vm.provision "shell", path: "provision/install_java.sh"
    master.vm.hostname = "backend1"
  end
  config.vm.define :backend2 do |master|
    master.vm.network "private_network", ip: "172.20.100.6"
    master.vm.provision "shell", path: "provision/install_consul.sh"
    master.vm.provision "shell", path: "provision/install_java.sh"
    master.vm.hostname = "backend2"
  end
  config.vm.define :webapp1 do |master|
    master.vm.network "private_network", ip: "172.20.100.7"
    master.vm.provision "shell", path: "provision/install_consul.sh"
    master.vm.provision "shell", path: "provision/install_java.sh"
    master.vm.hostname = "webapp1"
  end
  config.vm.define :webapp2 do |master|
    master.vm.network "private_network", ip: "172.20.100.8"
    master.vm.provision "shell", path: "provision/install_consul.sh"
    master.vm.provision "shell", path: "provision/install_java.sh"
    master.vm.hostname = "webapp2"
  end

end
