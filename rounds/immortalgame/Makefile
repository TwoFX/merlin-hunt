PROBLEMNAME := The Immortal Game
FLAGNAME := immortalgame
SHORTNAME := D
INPUTFILES := input/immortalgame.wmv input/instructions.rtf

include ../../make/round.mk
include ../../make/has-server.mk

input/instructions.rtf: input/openapi.yaml
	cp $< $@

