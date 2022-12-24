PROBLEMS := immortalgame thegreatundoing
SERVERPROBLEMS := immortalgame thegreatundoing

.PHONY: clean dj-zip server docker flag zip

target:
	mkdir -p target

target/domjudge: target
	mkdir -p target/domjudge

target/problems: target
	mkdir -p target/problems

target/flags: target
	mkdir -p target/flags

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

