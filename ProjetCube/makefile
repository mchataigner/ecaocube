.PHONY : clean,libraryExport

# ARCH= amd64 or i586
ARCH=amd64
LIB_PERSO=NO

LEJOS_VERSION=9.1
JOGL_VERSION=1.1.1

SOURCEPATH=src
CLASS=classes
LIB=lib

ifeq ($(LEJOS_VERSION),9.1)
	LEJOS_CLASSPATH=$(LIB)/lejos_nxj9.1/lib/nxt/classes.jar:$(LIB)/lejos_nxj9.1/lib/pc/pccomm.jar:$(LIB)/lejos_nxj9.1/lib/pc/jtools.jar:$(LIB)/lejos_nxj9.1/lib/pc/pctools.jar
	NXJCOMP=$(LIB)/lejos_nxj9.1/bin/nxjc
	NXJRUN=$(LIB)/lejos_nxj9.1/bin/nxj

else
	LEJOS_CLASSPATH=lejos_nxj8.5/lib/classes.jar:lejos_nxj8.5/lib/jtools.jar:lejos_nxj8.5/lib/pccomm.jar:lejos_nxj8.5/lib/pctools.jar
	NXJCOMP=$(LIB)/lejos_nxj8.5/bin/nxjc
	NXJRUN=$(LIB)/lejos_nxj8.5/bin/nxj
endif

ifeq ($(JOGL_VERSION),1.1.1)
	JOGL_CLASSPATH=$(LIB)/jogl-1.1.1-linux-$(ARCH)/lib/jogl.jar:$(LIB)/jogl-1.1.1-linux-$(ARCH)/lib/gluegen-rt.jar
	LD_LIBRARY_PATH=$(LIB)/jogl-1.1.1-linux-$(ARCH)/lib/
else
	JOGL_CLASSPATH=lib/jogl/build/jogl-all.jar:lib/gluegen/build/gluegen-rt
endif

ifeq ($(LIB_PERSO),YES)
	JAI_CLASSPATH=/usr/share/sun-jai-bin/lib/jai_codec.jar:/usr/share/sun-jai-bin/lib/jai_core.jar:/usr/share/sun-jai-bin/lib/mlibwrapper_jai.jar:/usr/lib64/sun-jai-bin
	JMF_CLASSPATH=/usr/share/jmf-bin/lib/jmf.jar:/usr/share/jmf-bin/lib/customizer.jar:/usr/share/jmf-bin/lib/mediaplayer.jar:/usr/share/jmf-bin/lib/multiplayer.jar
else
	JAI_CLASSPATH=$(LIB)/jai-1_1_3-$(ARCH)/lib/jai_codec.jar:$(LIB)/jai-1_1_3-$(ARCH)/lib/jai_core.jar:$(LIB)/jai-1_1_3-$(ARCH)/lib/mlibwrapper_jai.jar
	JMF_CLASSPATH=$(LIB)/JMF-2.1.1e/lib/customizer.jar:$(LIB)/JMF-2.1.1e/lib/jmf.jar:$(LIB)/JMF-2.1.1e/lib/mediaplayer.jar:$(LIB)/JMF-2.1.1e/lib/multiplayer.jar
endif

CLASSPATH=$(CLASS):$(LEJOS_CLASSPATH):$(JOGL_CLASSPATH):$(JAI_CLASSPATH):$(JMF_CLASSPATH)



JAVACOPT=-d $(CLASS) -sourcepath $(SOURCEPATH) -classpath $(CLASSPATH)



all:
	$(NXJCOMP) -d $(CLASS) -cp $(CLASSPATH) -sourcepath $(SOURCEPATH) src/cube/robot/RobotRubik.java
	javac $(JAVACOPT) $(SOURCEPATH)/Classmain.java
	javac $(JAVACOPT) $(SOURCEPATH)/ClassmainSansVision.java
	javac $(JAVACOPT) $(SOURCEPATH)/ClassmainTest.java
	javac $(JAVACOPT) $(SOURCEPATH)/ClassmainTestPllOll.java
	javac $(JAVACOPT) $(SOURCEPATH)/acquisition/ScanRubicsCube.java
	javac $(JAVACOPT) $(SOURCEPATH)/vision/CubeFactory.java
	javac $(JAVACOPT) $(SOURCEPATH)/acquisition/mainTestDetectionVision2.java
	javac $(JAVACOPT) $(SOURCEPATH)/glcube/MainTest.java


execClassmain: libraryExport
	java -classpath $(CLASSPATH) Classmain

execClassMainSansVision: libraryExport
	java -classpath $(CLASSPATH) ClassmainSansVision

execClassMainTest: libraryExport
	java -classpath $(CLASSPATH) ClassmainTest

execnxj: libraryExport
	$(NXJRUN) -r -u -cp $(CLASSPATH) cube.robot.RobotRubik

libraryExport:
	export LD_LIBRARY_PATH=$(LD_LIBRARY_PATH)
	export NXJ_HOME=lejos_nxj9.1/
	export LD_LIBRARY_PATH=$(LD_LIBRARY_PATH):NXJ_HOME/bin

clean:
	clear
	rm -rf $(CLASS)/*
