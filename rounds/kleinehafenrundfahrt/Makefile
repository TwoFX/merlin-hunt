PROBLEMNAME := Kleine Hafenrundfahrt
FLAGNAME := kleinehafenrundfahrt
SHORTNAME := H
INPUTFILES := input/gummiboot input/irrelevant.txt

include ../../make/round.mk

build:
	mkdir -p build

build/fast: program/fast.cpp
	g++ -O2 program/fast.cpp -o build/fast

kleinehafenrundfahrt.ans: build/fast
	$< > $@

build/cosmo: build
	mkdir -p build/cosmo

build/cosmo/cosmopolitan.zip: build/cosmo
	curl https://justine.lol/cosmopolitan/cosmopolitan.zip -o $@

build/cosmo/cosmopolitan.a: build/cosmo/cosmopolitan.zip
	(cd build/cosmo && unzip cosmopolitan.zip)

build/transform: program/transform.cpp build
	g++ -O2 $< -o $@

build/gummiboot.c: build/transform program/obfuscated.py
	build/transform < program/obfuscated.py > $@

build/gummiboot.com.dbg: build/gummiboot.c build/cosmo/cosmopolitan.a
	gcc -g -Os -static -nostdlib -nostdinc -fno-pie -no-pie -mno-red-zone \
		-fno-omit-frame-pointer -pg -mnop-mcount -mno-tls-direct-seg-refs -gdwarf-4 \
		-o build/gummiboot.com.dbg build/gummiboot.c -fuse-ld=bfd -Wl,-T,build/cosmo/ape.lds -Wl,--gc-sections \
		-include build/cosmo/cosmopolitan.h build/cosmo/crt.o build/cosmo/ape-no-modify-self.o build/cosmo/cosmopolitan.a

build/gummiboot.com: build/gummiboot.com.dbg
	objcopy -S -O binary $< $@

build/gummiboot.qcow2: build/gummiboot.com
	qemu-img convert -f raw -O qcow2 $< $@

input/gummiboot: build/gummiboot.qcow2
	cp $< $@

clean::
	rm -rf build kleinehafenrundfahrt.ans input/gummiboot
