clear
close all

img = rgb2gray(imread('../image/conf4/face3.png'));
t=1;
%bench calcul de la rotation
tic;
[angle incertitude]=choixRotation(img,5,5)
imgrot=imrotate(img,angle,'bicubic','crop',255);
temps(t++)=toc;

%préparation des données 
tailleimg=size(imgrot);
projX=sum(imgrot,1)/tailleimg(2);
projY=sum(imgrot,2)/tailleimg(1);

%bench du filtrage :
tic
wsize=3;
projX=filter(ones(wsize)/wsize,1,projX);
projX=projX((wsize+1):end);
temps(t++)=toc;

%méthode dérivée
tic
indicePicX=detectionPicDerivee(projX,2,tailleimg(2)/9) ;
indicePicY=detectionPicDerivee(projY',2,tailleimg(1)/9);
temps(t++)=toc;

%méthode seuil
tic
indicePicX2=detectionPicBarreGlissante(projX);
indicePicY2=detectionPicBarreGlissante(projY');
temps(t++)=toc;

%méthode variance 1d
tic
varianceX=varianceCoordonnee(projX, 25);
indiceTrouX=find(varianceX>70);
varianceY=varianceCoordonnee(projY', 25);
indiceTrouY=find(varianceY>70);
temps(t++)=toc;
%méthode variance 2d
tic
[hauteur, largeur]=size(img);
largeurFenetre=hauteur/8;
nombreDePlat=9; 
[plat, er] = detectionPlatVariance2D_coord(img, largeurFenetre, nombreDePlat);
temps(t++)=toc;	

temps

bar(temps);

