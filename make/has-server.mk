.PHONY: server docker clean openapi server-native docker-native

server:
	$(MAKE) -C server server

docker:
	$(MAKE) -C server docker

server-native:
	$(MAKE) -C server server-native

docker-native:
	$(MAKE) -C server docker-native

clean::
	$(MAKE) -C server clean

openapi::
	$(MAKE) -C server openapi
