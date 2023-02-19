.PHONY: push

information.pdf: information.tex
	latexmk -pdf information.tex

push: information.pdf information.html
	scp information.pdf markus@markushimmel.de:files
	scp information.html markus@markushimmel.de:files
	scp image.png markus@markushimmel.de:files
