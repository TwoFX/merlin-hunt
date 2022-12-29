PROBLEMNAME := Twistly Little Passages
FLAGNAME := twistlylittlepassages
SHORTNAME := E
INPUTFILES := hint.txt

include ../../make/round.mk

image/merlin-init.sql:
	$(MAKE) -C generator generator
	java -jar generator/target/quarkus-app/quarkus-run.jar -o image/merlin-init.sql -a twistylittlepassages.ans

image/merlin-init.sql.gz: image/merlin-init.sql
	gzip -k image/merlin-init.sql

docker: image/merlin-init.sql.gz
	docker build -f image/Dockerfile -t blubb image

clean::
	rm -f image/merlin-init.sql image/merlin-init.sql.gz
	$(MAKE) -C generator clean
