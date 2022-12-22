.PHONY: server docker clean

CONTAINERNAME := thegreatundoing/server

server:
	./mvnw package -Pnative -Dquarkus.native.container-build=true

docker: server
	docker build -f src/main/docker/Dockerfile.native-micro -t markushimmel.de:5000/merlinhunt/$(CONTAINERNAME) .
	docker push markushimmel.de:5000/merlinhunt/$(CONTAINERNAME)

clean:
	./mvnw clean

