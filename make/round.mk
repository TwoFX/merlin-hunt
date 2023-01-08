.PHONY: clean flag dj-zip zip answer

flag: $(FLAGNAME).flag
	cp $< ../target/flags

dj-zip: $(SHORTNAME).zip
	cp $< ../target/domjudge

answer: $(FLAGNAME).ans
	cp $< ../target/answers

$(FLAGNAME).flag.in:
	printf "$(PROBLEMNAME)$(SECRETSUFFIX)" > $@

$(FLAGNAME).flag: $(FLAGNAME).flag.in
	sha256sum $< > $@

dj-contest:
	mkdir -p $@

dj-contest/data/secret: dj-contest
	mkdir -p $@

dj-contest/data/secret/1.in: dj-contest/data/secret
	touch $@

dj-contest/data/secret/1.ans: dj-contest/data/secret $(FLAGNAME).flag
	cp $(FLAGNAME).flag $@

dj-contest/domjudge-problem.ini: dj-contest
	printf "name = $(PROBLEMNAME)" > $@

$(SHORTNAME).zip: dj-contest/domjudge-problem.ini dj-contest/data/secret/1.in dj-contest/data/secret/1.ans
	(cd dj-contest && zip ../$@ `printf "$^" | sed "s/dj-contest\\///g" -`)

clean::
	rm -rf dj-contest $(FLAGNAME).flag $(SHORTNAME).zip $(FLAGNAME).zip $(FLAGNAME).flag.in

zip: $(FLAGNAME).zip
	cp $< ../target/problems

$(FLAGNAME).zip: $(INPUTFILES)
	(cd input && zip ../$@ `printf "$^" | sed "s/input\\///g" -`)
