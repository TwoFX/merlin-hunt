PROBLEMNAME := Practice Makes Perfect
FLAGNAME := practicemakesperfect
SHORTNAME := A
INPUTFILES := input/README.txt input/request-hint.flag

include ../../make/round.mk

input/request-hint.flag: request-hint.flag.in
	sha256sum $< > $@

clean::
	rm -rf input/request-hint.flag
