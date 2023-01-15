PROBLEMNAME := Twistly Little Passages
FLAGNAME := twistylittlepassages
SHORTNAME := H
INPUTFILES := input/lowercase

include ../../make/round.mk

input/lowercase: caesar/caesar
	$< 1 < caesar/hint.txt > $@

caesar/caesar: caesar/caesar.cpp
	g++ $< -o $@

image/merlin-init.sql:
	$(MAKE) -C generator generator
	java -jar generator/target/quarkus-app/quarkus-run.jar -o image/merlin-init.sql -a twistylittlepassages.ans

image/merlin-init.sql.gz: image/merlin-init.sql
	gzip -k image/merlin-init.sql

server: image/merlin-init.sql.gz

docker: image/merlin-init.sql.gz
	docker build -f image/Dockerfile -t markushimmel.de:5000/merlinhunt/twistylittlepassages/db image
	docker push markushimmel.de:5000/merlinhunt/twistylittlepassages/db

clean::
	rm -f image/merlin-init.sql image/merlin-init.sql.gz input/lowercase caesar/caesar
	$(MAKE) -C generator clean
