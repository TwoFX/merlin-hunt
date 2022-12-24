.PHONY: clean dj-zip server docker flag zip answer

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
	$(MAKE) -C $$i flag; done

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
