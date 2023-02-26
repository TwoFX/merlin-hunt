PROBLEMNAME := Zwei Walküren
FLAGNAME := zweiwalkueren
SHORTNAME := F
INPUTFILES := input/bruennhilde input/siegrune

include ../../make/round.mk

build:
	mkdir -p build

build/walkuerenritt_orig.flac: build
	curl https://static.markushimmel.de/walkuerenritt.flac > $@

build/poster.flac: build
	curl https://static.markushimmel.de/poster.flac > $@

build/hiddentext.flac: build
	curl https://static.markushimmel.de/hiddentext.flac > $@

build/mixed.flac: build/walkuerenritt_orig.flac build/poster.flac build/hiddentext.flac build
	sox "|sox -v 0.7 build/hiddentext.flac -r 96000 -p pad 4 4" "|sox -v 0.7 build/poster.flac -r 96000 -p" -p | sox -m -v 0.008 - build/walkuerenritt_orig.flac $@
	metaflac --remove-all $@
	metaflac "--set-tag=TITLE=Richard Wagner's Walkürenritt, but every prime-numbered byte has all its bits flipped. I repeat, the bits in all prime-numbered bytes are flipped. To make it perfectly clear: you take the bits in all prime-numbered bytes and flip them. Simply count the bytes in the file, starting from zero. Take all bytes whose number is prime, and invert all bits in them. Got it? Flip all prime-numbered bytes? How hard can it be? Look at your file. Take all the bytes whose zero-based position is a prime number, and turn zeros into ones and ones into zeros. You do know what a prime number is, right? For example, if the byte in position 41 is 01101100, then it should be turned into 10010011. You need to flip this byte because 41 is a prime number. A prime number is a number whose only proper divisor is 1." $@

build/mixed_flipped.flac: build/mixed.flac solution/FlipBytes.java
	java solution/FlipBytes.java 0 build/mixed.flac $@

build/walkuerenritt.flac: build/walkuerenritt_orig.flac
	cp $< $@
	metaflac --remove-all $@
	metaflac "--set-tag=TITLE=Richard Wagner's Walkürenritt, but every abundantly-numbered byte has all its bits flipped. I repeat, the bits in all abundantly-numbered bytes are flipped. In number theory, an abundant number or excessive number is a number for which the sum of its proper divisors is greater than the number. The integer 12 is the first abundant number. Its proper divisors are 1, 2, 3, 4 and 6 for a total of 16. The amount by which the sum exceeds the number is the abundance. The number 12 has an abundance of 4, for example. Eine natürliche Zahl heißt abundant (lat. abundans „überladen“), wenn ihre echte Teilersumme (die Summe aller Teiler ohne die Zahl selbst) größer ist als die Zahl selbst. Ist die Teilersumme dagegen gleich der Zahl, spricht man von einer vollkommenen Zahl, ist sie kleiner, so spricht man von einer defizienten Zahl. For your convenience, I will repeat the key term a bunch of times so you can't miss it: abundant abundant abundant abundant abundant abundant abundant abundant abundant abundant abundant abundant abundant. There you go." $@

build/walkuerenritt_flipped.flac: build/walkuerenritt.flac solution/FlipBytes.java
	java solution/FlipBytes.java 1 build/walkuerenritt.flac $@

input/bruennhilde: build/mixed_flipped.flac
	cp $< $@

input/siegrune: build/walkuerenritt_flipped.flac
	cp $< $@

strokes/strokes.txt: strokes/strokes.py
	make -C strokes strokes.txt

zweiwalkueren.ans: strokes/strokes.txt
	cp $< $@

clean::
	rm -rf zweiwalkueren.ans build/
	make -C strokes clean
