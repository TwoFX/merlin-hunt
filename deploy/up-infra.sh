#!/bin/sh

sudo ufw-docker delete allow registry

docker compose -f docker-compose.infra.yml up -d

sudo ufw-docker allow registry 5000
sudo ufw reload
