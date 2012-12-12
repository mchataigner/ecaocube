#!/bin/bash
if [[ $(aptitude show libusb-dev | grep "not installed") ]];then
echo "install libusb-dev first";
exit ;
fi

if [[ $(aptitude show ant | grep "not installed") ]];then
echo "install ant first";
exit ;
fi

if [[ `pwd | grep " "` ]];then
echo "rename your path without space in the name"
exit ;
fi

if [[ $(uname -m| grep 64) ]];then
arch=amd64;
else
arch=i586;
fi

cd lib
wget -c http://download.java.net/media/jogl/builds/archive/jsr-231-1.1.1a/jogl-1.1.1a-linux-$arch.zip
unzip jogl-1.1.1a-linux-$arch.zip

#wget -c http://download.java.net/media/jogl/builds/archive/jsr-231-beta5/jogl-natives-linux-$arch.jar
#jar xvf jogl-natives-linux-$arch.jar
#wget -c http://download.java.net/media/jogl/builds/archive/jsr-231-beta5/jogl.jar

#wget -c http://download.java.net/media/gluegen/builds/archive/2.0b05/webstart/gluegen-rt-natives-linux-$arch.jar
#jar xvf gluegen-rt-natives-linux-$arch.jar
#wget -c http://download.java.net/media/gluegen/builds/archive/2.0b05/webstart/gluegen-rt.jar


#cd ..


wget -c http://download.java.net/media/jai/builds/release/1_1_3/jai-1_1_3-lib-linux-$arch.tar.gz
tar xvvzf jai-1_1_3-lib-linux-$arch.tar.gz

wget -c http://downloads.sourceforge.net/project/lejos/lejos-NXJ/0.8.5beta/lejos_NXJ_0_8_5beta.tar.gz?use_mirror=switch
#wget -c http://sourceforge.net/projects/lejos/files/lejos-NXJ/0.8.5beta/lejos_NXJ_0_8_5beta.tar.gz/download
tar xvvzf lejos_NXJ_0_8_5beta.tar.gz




cd lejos_nxj/build
sed -i 's/jbluez,//g' build.xml
ant
cd ../../..

echo "télécharger jmf 2.1.1.e sur http://java.sun.com/javase/technologies/desktop/media/jmf/2.1.1/download.html"

echo -e "\n\n\n----------------------------------------------------------------------\n\n\nInstallation terminée\n\nVous devez maintenant ajouter une règle à udev, sinon vous serez obligés d'excécuter le code en root pour accéder au NXT\n\nPour cela, en root, créez un fichier nommé :\n\n/etc/udev/rules.d/70-lego.rules \n\net y insérez le contenu suivant :\n\n# Lego NXT\nBUS==\"usb\", SYSFS{idVendor}==\"03eb\", GROUP=\"lego\", MODE=\"0660\" \nBUS==\"usb\", SYSFS{idVendor}==\"0694\", GROUP=\"lego\", MODE=\"0660\" \n\nEnfin il faut créer un groupe nommée lego et y ajouter l'utilisateur courant, se relogger, compiler, exécuter"

