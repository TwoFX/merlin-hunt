PROBLEMNAME := An Old Friend
FLAGNAME := anoldfriend
SHORTNAME := C
INPUTFILES := input/schnitzel

include ../../make/round.mk

build:
	mkdir -p build

build/solution: program/solution.cpp
	g++ -O2 -o $@ $<

anoldfriend.ans: build/solution
	$< > $@

build/archive.tar.xz: build program/programm.java
	(cd program && tar -cJf ../$@ programm.java)

input/schnitzel: build/archive.tar.xz
	cp $< $@

clean::
	rm -rf build anoldfriend.ans input/schnitzel
