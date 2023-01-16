PROBLEMNAME := The Great Undoing
FLAGNAME := greatundoing
SHORTNAME := I
INPUTFILES := input/ring input/n.elapamoipy

.PHONY: push

include ../../make/round.mk
include ../../make/has-server.mk

build:
	mkdir -p build

push: build
	rm -rf build/server
	cp -r server/ build/
	(cd build/server && git init && git add . && git commit -m "Initial commit" && git remote add origin ssh://git@gitlab.markushimmel.de:2222/merlin-hunt/the-great-undoing.git && git push --force origin master)

build/client: client/client.nim
	nim compile -d:release -o:$@ $<

input/n.elapamoipy: input/openapi.yaml scripts/shuffle.py
	scripts/shuffle.py input/openapi.yaml $@

input/ring: build/client
	cp $< $@

clean::
	rm -rf build
