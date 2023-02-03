.PHONY: clean flag dj-zip zip answer

flag: $(FLAGNAME).flag
	cp $< ../target/flags

flag-dj: $(FLAGNAME).flag.dj
	cp $< ../target/flags

dj-zip: $(SHORTNAME).zip
	cp $< ../target/domjudge

answer: $(FLAGNAME).ans
	cp $< ../target/answers

$(FLAGNAME).flag.in:
	printf "$(PROBLEMNAME)$(SECRETSUFFIX)" > $@

$(FLAGNAME).flag: $(FLAGNAME).flag.in
	sha256sum $< > $@

$(FLAGNAME).flag.dj: $(FLAGNAME).flag
	sha256sum - < $< > $@

dj-contest:
	mkdir -p $@

dj-contest/data/sample: dj-contest
	mkdir -p $@

dj-contest/data/sample/1.in: dj-contest/data/sample
	touch $@

dj-contest/data/sample/1.ans: $(FLAGNAME).flag.dj dj-contest/data/sample
	cp $< $@

dj-contest/domjudge-problem.ini: dj-contest
	printf "name = $(PROBLEMNAME)" > $@

$(SHORTNAME).zip: dj-contest/domjudge-problem.ini dj-contest/data/sample/1.in dj-contest/data/sample/1.ans
	(cd dj-contest && zip ../$@ `printf "$^" | sed "s/dj-contest\\///g" -`)

clean::
	rm -rf dj-contest $(FLAGNAME).flag $(SHORTNAME).zip $(FLAGNAME).zip $(FLAGNAME).flag.in input/$(FLAGNAME) $(FLAGNAME).flag.dj

zip: $(FLAGNAME).zip
	cp $< ../target/problems

$(FLAGNAME).zip: $(INPUTFILES)
	mkdir -p input/$(FLAGNAME)
	cp $^ input/$(FLAGNAME)
	(cd input && zip ../$@ $(addprefix $(FLAGNAME)/,$(notdir $^)))
