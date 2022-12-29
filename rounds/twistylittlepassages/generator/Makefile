.PHONY: clean server docker

target/twisty-little-passages-1.0.0-SNAPSHOT.jar: src/main/java/de/markushimmel/merlinhunt/twistylittlepassages/GenerateBuildScriptCommand.java
	./mvnw package

generator: target/twisty-little-passages-1.0.0-SNAPSHOT.jar

clean:
	./mvnw clean
