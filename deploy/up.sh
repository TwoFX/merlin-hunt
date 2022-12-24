#!/bin/sh

sudo ufw-docker delete allow registry
sudo ufw-docker delete allow nginx

docker compose up -d

sudo ufw-docker allow registry 5000
sudo ufw-docker allow nginx 80
sudo ufw-docker allow nginx 443
sudo ufw reload
