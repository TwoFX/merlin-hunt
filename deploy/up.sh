#!/bin/sh

sudo ufw-docker delete allow nginx
sudo ufw-docker delete allow twisty
sudo ufw-docker delete allow gitlab

docker compose up -d

sudo ufw-docker allow nginx 80
sudo ufw-docker allow nginx 443
sudo ufw-docker allow twisty 5432
sudo ufw-docker allow gitlab 22
sudo ufw reload
