PROBLEMNAME := Zwei Walküren
FLAGNAME := zweiwalkueren
SHORTNAME := F
INPUTFILES := input/TODO.txt

include ../../make/round.mk

strokes/strokes.txt: strokes/strokes.py
	make -C strokes strokes.txt

zweiwalkueren.ans: strokes/strokes.txt
	cp $< $@

clean::
	rm -rf zweiwalkueren.ans
	make -C strokes clean
