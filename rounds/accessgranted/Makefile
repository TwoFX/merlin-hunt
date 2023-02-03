PROBLEMNAME := Access Granted
FLAGNAME := accessgranted
SHORTNAME := A
INPUTFILES := input/README.txt input/request-hint.flag
STARTPROBLEM := Yes

include ../../make/round.mk

input/request-hint.flag: request-hint.flag.in
	sha256sum $< > $@

clean::
	rm -rf input/request-hint.flag
