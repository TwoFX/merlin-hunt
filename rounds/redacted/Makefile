PROBLEMNAME := [Redacted]
FLAGNAME := redacted
SHORTNAME := C
INPUTFILES := input/confidential

include ../../make/round.mk

build:
	mkdir -p build

build/censor: scripts/censor.cpp build
	g++ -O2 $< -o $@

build/censored.ppm: build/censor images/base.ppm
	build/censor 1579 1045 2920 490 < images/base.ppm > $@

input/confidential: build/censored.ppm
	cp $< $@

clean::
	rm -r build input/confidential
