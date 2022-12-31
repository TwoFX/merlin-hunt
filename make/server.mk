.PHONY: server docker clean openapi

server:
	./mvnw package

docker: server
	docker build -f src/main/docker/Dockerfile.jvm -t markushimmel.de:5000/merlinhunt/$(CONTAINERNAME) .
	docker push markushimmel.de:5000/merlinhunt/$(CONTAINERNAME)

target/openapi.yaml:
	curl localhost:8080/q/openapi > $@
	sed -i '6i servers:' $@
	sed -i "7i \ \ - url: $(SERVERNAME)" $@

openapi: target/openapi.yaml
	mkdir -p ../input
	cp $< ../input

clean:
	./mvnw clean


