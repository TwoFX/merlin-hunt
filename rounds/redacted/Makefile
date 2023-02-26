PROBLEMNAME := [Redacted]
FLAGNAME := redacted
SHORTNAME := C
INPUTFILES := input/confidential

include ../../make/round.mk

build:
	mkdir -p build

build/base.ppm: build
	curl https://static.markushimmel.de/base.ppm > $@

build/censor: scripts/censor.cpp build
	g++ -O2 $< -o $@

build/censored.ppm: build/censor build/base.ppm
	build/censor 1579 1045 2920 490 < build/base.ppm > $@

input/confidential: build/censored.ppm
	cp $< $@

clean::
	rm -rf build input/confidential
