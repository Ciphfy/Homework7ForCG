#this is a makefile for GC class

#####
## if you want to specify a directory java installed explicitly,
## set the java binary directory here
#####
JAVABIN=
#  JAVABIN=/usr/lib/jvm/default-java/bin/

#####
## set javafx-sdk directory your installed 
#####
JAVAFXMODULE=../javafx-sdk-11.0.2/lib

#####
##  set proxy server address and port number, if your machine connects
##  to the internet via proxy
JAVADOCPROXY=
#  JAVADOCPROXY=-J-Dhttp.proxyHost=proxy.csc.titech.ac.jp -J-Dhttp.proxyPort=8080

# OS-dependent commands and separator
ifeq ($(OS),Windows_NT)
	SEP=;
	RM=rd /s /q
	FIND=echo
else
	SEP=:
	RM=rm -rf
	FIND=find
endif

JAVA=$(JAVABIN)java $(JAVAFLAGS)
JAVAC=$(JAVABIN)javac $(JAVACFLAGS)
JAVADOC0=$(JAVABIN)javadoc $(JAVADOCFLAGS)
MKDIR=mkdir
MAKE=make

MODULEPATH=$(JAVAFXMODULE)
CLASSPATH=lib/jar/*$(SEP)
CLASSFLAGS=-classpath "bin$(SEP)$(CLASSPATH)resource"
JAVACCLASSFLAGS=-classpath "$(CLASSPATH)"
#MODULEFLAGS=--module-path $(MODULEPATH) --add-modules javafx.controls,javafx.swing
JAVACFLAGS= -encoding utf8 -d bin -sourcepath src $(MODULEFLAGS) $(JAVACCLASSFLAGS) -Xlint:deprecation -Xdiags:verbose -Xlint:unchecked
JAVAFLAGS = $(MODULEFLAGS) $(CLASSFLAGS)
JAVADOCFLAGS= -html5 -encoding utf-8 -charset utf-8 -package -d javadoc -sourcepath src $(JAVADOCPROXY) -link https://docs.oracle.com/javase/jp/14/docs/api -link https://openjfx.io/javadoc/11 $(MODULEFLAGS) $(CLASSFLAGS)

SERVADDR=localhost
NUMBER=`echo \`whoami\`0 | md5 | sed -e "s/[^0-9]//g" |cut -c 1-6`
PARAMETER=$(SERVADDR)

.PHONY: clean cleanall doc tar

.SUFFIXES: .class .java

.java.class:
	echo $@

.class:
	$(MAKE) bin
	$(eval CLS := $(subst /,.,$(@:src/%=%)))
	$(JAVAC) $@.java 
	$(JAVA) $(CLS)  $(PARAMETER)

ALL:: SimpleExample6Objs

SimpleExample6Objs:
	$(MAKE) src/$@

bin:
	$(MKDIR) bin

javadoc:
	$(MKDIR) javadoc

clean:: javadoc bin
	$(RM) bin
	$(RM) javadoc

cleanall:: javadoc bin
	$(FIND) . -name "._*" -exec $(RM) {} \;
	$(RM) bin javadoc

doc:: javadoc
	$(FIND) . -name "._*" -exec $(RM) {} \;
	$(JAVADOC0) para para.calc para.graphic.shape para.graphic.target para.graphic.parser para.graphic.camera

tar: SimpleExample
	$(RM) -R bin javadoc
	cd ..; $(TAR) zcvf jogltest.tar.gz JOGLTest
