.PHONY: clean dj-zip server docker flag zip answer

export SECRETSUFFIX

target:
	mkdir -p target

target/%: target
	mkdir -p $@

dj-zip: target/domjudge
	@for i in $(PROBLEMS); do \
	$(MAKE) -C $$i dj-zip; done

zip: target/problems
	@for i in $(PROBLEMS); do \
	$(MAKE) -C $$i zip; done

flag: target/flags
	@for i in $(PROBLEMS); do \
	$(MAKE) -C $$i flag; cat target/flags/$$i.flag >> target/flags/flags.txt; done

clean:
	rm -rf target
	@for i in $(PROBLEMS); do \
	$(MAKE) -C $$i clean; done

server:
	@for i in $(SERVERPROBLEMS); do \
	$(MAKE) -C $$i server; done

docker:
	@for i in $(SERVERPROBLEMS); do \
	$(MAKE) -C $$i docker; done

answer: target/answers
	@for i in $(PROBLEMS); do \
	$(MAKE) -C $$i answer; done

assemble: zip flag answer
	(cd target && ../../scripts/assemble.py $(PROBLEMS))

target/problems/$(firstword $(PROBLEMS)).zip.sha256: assemble
	(cd target/problems && sha256sum $(firstword $(PROBLEMS)).zip > $(firstword $(PROBLEMS)).zip.sha256)

push: target/problems/$(firstword $(PROBLEMS)).zip.sha256
	scp target/problems/$(firstword $(PROBLEMS)).zip markus@markushimmel.de:files
	scp target/problems/$(firstword $(PROBLEMS)).zip.sha256 markus@markushimmel.de:files
