PROBLEMNAME := Simple Sum
FLAGNAME := simplesum
SHORTNAME := B
INPUTFILES := input/notapuzzle.txt

input/notapuzzle.txt: generator/generator.py
	generator/generator.py > input/notapuzzle.txt

simplesum.ans: solution/solution.py input/notapuzzle.txt
	solution/solution.py input/notapuzzle.txt > simplesum.ans

include ../../make/round.mk

clean::
	rm -f input/notapuzzle.txt simplesum.ans

