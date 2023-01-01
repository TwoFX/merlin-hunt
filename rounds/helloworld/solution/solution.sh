#!/bin/sh

curl -d '{"name":"world"}' -X POST -H "Content-Type: application/json" https://greeting.markushimmel.de
