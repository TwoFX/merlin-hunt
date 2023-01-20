PROBLEMNAME := Buggy Relay
FLAGNAME := buggyrelay
SHORTNAME := E
INPUTFILES := input/2then1

include ../../make/round.mk
include ../../make/has-server.mk

build:
	mkdir -p build

build/exported.tar: build docker-native
	docker save markushimmel.de:5000/merlinhunt/buggyrelay/generator -o $@

input/2then1: build/exported.tar
	cp $< $@

clean::
	rm -r build/ input/2then1
