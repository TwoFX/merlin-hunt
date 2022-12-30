.PHONY: push

information.pdf: information.tex
	latexmk -pdf information.tex

push: information.pdf
	scp information.pdf markus@markushimmel.de:files
