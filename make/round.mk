.PHONY: clean
SUFFIX := This is a secret suffix

flag: $(FLAGNAME).flag

dj-zip: $(SHORTNAME).zip

$(FLAGNAME).flag:
	printf "$(PROBLEMNAME)$(SUFFIX)" | sha256sum > $@

dj-contest:
	mkdir -p $@

dj-contest/data/secret: dj-contest
	mkdir -p $@

dj-contest/data/secret/1.in: dj-contest/data/secret
	touch $@

dj-contest/data/secret/1.ans: dj-contest/data/secret flag
	cp $(FLAGNAME).flag $@

dj-contest/domjudge-problem.ini: dj-contest
	printf "name = $(PROBLEMNAME)" > $@

$(SHORTNAME).zip: dj-contest/domjudge-problem.ini dj-contest/data/secret/1.in dj-contest/data/secret/1.ans
	(cd dj-contest && zip ../$@ `printf "$^" | sed "s/dj-contest\\///g" -`)

clean::
	rm -rf dj-contest $(FLAGNAME).flag $(SHORTNAME).zip
