include makevars

##################
# make section
#

all: subdirsall | $(TARGET)
	$(POSTMAKE)

subdirsall:
	for dir in $(SUBDIRS); do\
		make -C $$dir all;\
	done

.SUFFIXES : 	.pdf\
		.eps\
		.tex\
		.fig\
		.dia
			
.eps.pdf:
	epstopdf $*.eps
	
.fig.pdf:
	fig2dev -L pdf $*.fig $*.pdf
	
.dia.eps:
	dia --export=$*.eps --filter=eps-pango $*.dia
	
.tex.pdf:
	TEXINPUTS=$$TEXINPUTS:$(CLSPATH);\
	export TEXINPUTS;\
	pdflatex $*.tex;\
	pdflatex $*.tex;
	
##################
#	Clean section
#

cleanall: clean subdirscleanall
	rm -f *.pdf;\
	rm -f $(TARGET);\
	$(POSTCLEANALL)

subdirscleanall:
	for dir in $(SUBDIRS); do\
		make -C $$dir cleanall;\
	done
	
clean: subdirsclean
	rm -f *.aux
	rm -f *.cb
	rm -f *.cb2
	rm -f *.log
	rm -f *.out
	rm -f *.toc
	rm -f *~
	rm -f *.backup

subdirsclean:
	for dir in $(SUBDIRS); do\
		make -C $$dir clean;\
	done

 
 
