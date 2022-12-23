.PHONY: server docker clean openapi

server:
	$(MAKE) -C server server

docker:
	$(MAKE) -C server docker

clean::
	$(MAKE) -C server clean

openapi::
	$(MAKE) -C server openapi
