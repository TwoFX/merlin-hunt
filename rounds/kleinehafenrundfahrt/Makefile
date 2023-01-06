PROBLEMNAME := Kleine Hafenrundfahrt
FLAGNAME := kleinehafenrundfahrt
SHORTNAME := H
INPUTFILES := input/TODO.txt

include ../../make/round.mk

program/fast: program/fast.cpp
	g++ -O2 program/fast.cpp -o program/fast

kleinehafenrundfahrt.ans: program/fast
	$< > $@
