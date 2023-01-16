PROBLEMNAME := The Great Undoing
FLAGNAME := greatundoing
SHORTNAME := I
INPUTFILES := input/hint.txt

.PHONY: push

include ../../make/round.mk
include ../../make/has-server.mk

push:
	rm -rf build/server
	mkdir -p build
	cp -r server/ build/
	(cd build/server && git init && git add . && git commit -m "Initial commit" && git remote add origin ssh://git@gitlab.markushimmel.de:2222/merlin-hunt/the-great-undoing.git && git push --force origin master)

clean::
	rm -rf build
