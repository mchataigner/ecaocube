clear
close all

imgcolor = imread('../image/conf4/face3.png');
img = rgb2gray(imgcolor);

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Etude de l'image sans rotation et sans filtrage (3 plats)                     %
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% 1 ) Initialisation de l'image étudiée : 
imageEtudiee=img ;

% 2 ) Initialisation des projections liées à cette image
tailleimg=size(imageEtudiee);
projX=sum(imageEtudiee,1)/tailleimg(2);
projY=sum(imageEtudiee,2)/tailleimg(1);

% 3 ) Traitement de ces projections : 
projXTraite=projX;
projYTraite=projY;

% 4 ) Recherche de plat par la dérivée :
tic 
platX = detectionPlatVariance1D(projX, 25, 3, tailleimg(2)/4)
tempsCalculX=toc
tic 
platY = detectionPlatVariance1D(projY', 25, 3, tailleimg(1)/4)
tempsCalculY=toc
%Remarque : l'epaisseur du pic est 1/9 ième de la taille de l'image 

% 5 ) Affichage des pics trouvés : 
imgTraite=img;
imgTraite(:,platX)=0;
imgTraite(platY, :)=0;

figure()
imshow(imgTraite)
title('detection par la methode de la variance 1D : image non orientee, non filtree, espace =0.25% entre les plats')


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Etude de l'image sans rotation et sans filtrage (3 plats)                     %
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% 1 ) Initialisation de l'image étudiée : 
imageEtudiee=img ;

% 2 ) Initialisation des projections liées à cette image
tailleimg=size(imageEtudiee);
projX=sum(imageEtudiee,1)/tailleimg(2);
projY=sum(imageEtudiee,2)/tailleimg(1);

% 3 ) Traitement de ces projections : 
projXTraite=projX;
projYTraite=projY;

% 4 ) Recherche de plat par la dérivée :
tic 
platX = detectionPlatVariance1D(projX, 25, 3, tailleimg(2)/9)
tempsCalculX=toc
tic 
platY = detectionPlatVariance1D(projY', 25, 3, tailleimg(1)/9)
tempsCalculY=toc
%Remarque : l'epaisseur du pic est 1/9 ième de la taille de l'image 

% 5 ) Affichage des pics trouvés : 
imgTraite=img;
imgTraite(:,platX)=0;
imgTraite(platY, :)=0;

figure()
imshow(imgTraite)
title('detection par la methode de la variance 1D : image non orientee, non filtree, espace =0.11% entre les plats')

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Etude de l'image sans rotation et avec filtrage                               %
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% 1 ) Initialisation de l'image étudiée : 
imageEtudiee=img ;

% 2 ) Initialisation des projections liées à cette image
tailleimg=size(imageEtudiee);
projX=sum(imageEtudiee,1)/tailleimg(2);
projY=sum(imageEtudiee,2)/tailleimg(1);

% 3 ) Traitement de ces projections : 
wsize=3;

projXTraite=filter(ones(wsize)/wsize,1,projX);
projXTraite=projXTraite((wsize+1):end);

projYTraite=filter(ones(wsize)/wsize,1,projY);
projYTraite=projYTraite((wsize+1):end);

% 4 ) Recherche de plat par la dérivée :
tic 
platX = detectionPlatVariance1D(projX, 25, 3, tailleimg(2)/9)
tempsCalculX=toc
tic 
platY = detectionPlatVariance1D(projY', 25, 3, tailleimg(1)/9)
tempsCalculY=toc
%Remarque : l'epaisseur du pic est 1/9 ième de la taille de l'image 

% 5 ) Affichage des pics trouvés : 
imgTraite=img;
imgTraite(:,platX)=0;
imgTraite(platY, :)=0;

figure()
imshow(imgTraite)
title('detection par la methode de la variance 1D : image non orientee, filtree')

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Etude de l'image avec rotation et avec filtrage                               %
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% 1 ) Initialisation de l'image étudiée : 

[angle incertitude]=choixRotation(img,5,10)
% image, angle de rotation, nombre de rotation
imageEtudiee=imrotate(img,angle,'bicubic','crop',255);
% nearest bilinear bicubic

% 2 ) Initialisation des projections liées à cette image
tailleimg=size(imageEtudiee);
projX=sum(imageEtudiee,1)/tailleimg(2);
projY=sum(imageEtudiee,2)/tailleimg(1);

% 3 ) Traitement de ces projections : 
wsize=3;

projXTraite=filter(ones(wsize)/wsize,1,projX);
projXTraite=projXTraite((wsize+1):end);

projYTraite=filter(ones(wsize)/wsize,1,projY);
projYTraite=projYTraite((wsize+1):end);

% 4 ) Recherche de plat par la dérivée :
tic 
platX = detectionPlatVariance1D(projX, 25, 3, tailleimg(2)/9)
tempsCalculX=toc
tic 
platY = detectionPlatVariance1D(projY', 25, 3, tailleimg(1)/9)
tempsCalculY=toc
%Remarque : l'epaisseur du pic est 1/9 ième de la taille de l'image 

% 5 ) Affichage des pics trouvés : 
imgTraite=img;
imgTraite(:,platX)=0;
imgTraite(platY, :)=0;

figure()
imshow(imgTraite)
title('detection par la methode de la variance 1D : image orientee, filtree')

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Etude de l'image avec rotation et sans filtrage                               %
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% 1 ) Initialisation de l'image étudiée : 

[angle incertitude]=choixRotation(img,5,10)
% image, angle de rotation, nombre de rotation
imageEtudiee=imrotate(img,angle,'bicubic','crop',255);
% nearest bilinear bicubic

% 2 ) Initialisation des projections liées à cette image
tailleimg=size(imageEtudiee);
projX=sum(imageEtudiee,1)/tailleimg(2);
projY=sum(imageEtudiee,2)/tailleimg(1);

% 3 ) Traitement de ces projections : 
projXTraite=projX;
projYTraite=projY;

% 4 ) Recherche de plat par la dérivée :
tic 
platX = detectionPlatVariance1D(projX, 25, 3, tailleimg(2)/9)
tempsCalculX=toc
tic 
platY = detectionPlatVariance1D(projY', 25, 3, tailleimg(1)/9)
tempsCalculY=toc
%Remarque : l'epaisseur du pic est 1/9 ième de la taille de l'image 

% 5 ) Affichage des pics trouvés : 
imgTraite=img;
imgTraite(:,platX)=0;
imgTraite(platY, :)=0;

figure()
imshow(imgTraite)
title('detection par la methode de la variance 1D : image orientee, non filtree')

