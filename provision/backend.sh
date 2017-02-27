#!/usr/bin/env bash

set -e

PID_FILE=/home/ubuntu/backend.pid

cd `dirname $0`

info() { echo -e "\033[1;32m:) $1\033[0m"; }
warn() { echo -e "\033[1;33m:| $1\033[0m"; }
fail() {
    echo -e "\033[1;31m:( $1\033[0m";
    exit 1
}

if [ ! $# -eq 1 ]; then
    info "Usage: ./backend.sh [start|stop|status|restart]"
    exit 1
fi

cmd=$1
if [[ ${cmd} != "start" && ${cmd} != "stop" && ${cmd} != "status" && ${cmd} != "restart" ]]; then
    fail "Unknown '$cmd'. Did you mean 'start' or 'stop'?"
fi

_start() {
    if [ ! -f ${PID_FILE} ]; then
        nohup java -jar backend-*.jar 2>> backend-error.log >> backend-server.log &
        PID=$!
        echo ${PID} > ${PID_FILE}
        info "Started with PID $PID"
    else
      warn "Already started"
    fi
}

_stop() {
    if [ -f ${PID_FILE} ]; then
        PID=`cat ${PID_FILE}`
        kill $PID || warn "PID $PID doesn't, removing $PID_FILE ..."
        rm ${PID_FILE}
    info "Stopped app with PID $PID"
    else
        warn "Couldn't find PID file. App doesn't run."
    fi
}

if [ ${cmd} = "status" ]; then
    if [ -f ${PID_FILE} ]; then
        pid=`cat ${PID_FILE}`
        info "Running (with PID $pid)"
    else
        warn "App doesn't run"
    fi
fi

if [ ${cmd} = "start" ]; then
    _start
fi

if [ ${cmd} = "stop" ]; then
    _stop
fi

if [ ${cmd} = "restart" ]; then
    _stop
    _start
fi
