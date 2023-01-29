.PHONY: server docker clean openapi server-native docker-native

server:
	./mvnw package

docker: server
	docker build -f src/main/docker/Dockerfile.jvm -t markushimmel.de:5000/merlinhunt/$(CONTAINERNAME) .
	docker push markushimmel.de:5000/merlinhunt/$(CONTAINERNAME)

server-native:
	 ./mvnw package -Pnative -Dquarkus.native.container-build=true -Dquarkus.native.builder-image=quay.io/quarkus/ubi-quarkus-graalvmce-builder-image:22.3-java19 -Dquarkus.native.additional-build-args=-J--enable-preview

docker-native: server-native
	docker build -f src/main/docker/Dockerfile.native-micro -t markushimmel.de:5000/merlinhunt/$(CONTAINERNAME) .
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


