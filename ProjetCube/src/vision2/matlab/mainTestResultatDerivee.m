clear
close all

faces=['face1.png' ;'face2.png' ;'face3.png' ;'face4.png' ;'face5.png' ;'face6.png'];

for i=1:6

imgcolor = imread(['../image/conf10/' faces(i,:)]);
img = rgb2gray(imgcolor);

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Etude de l'image avec rotation et avec filtrage                               %
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% 1 ) Initialisation de l'image étudiée : 

[angle incertitude]=choixRotation(img,5,10);
% image, angle de rotation, nombre de rotation
img_o=imrotate(img,angle,'bicubic','crop',255);
% nearest bilinear bicubic

% 2 ) Initialisation des projections liées à cette image
tailleimg=size(img_o);
projX=sum(img_o,1)/tailleimg(2);
projY=sum(img_o,2)/tailleimg(1);

% 3 ) Traitement de ces projections : 
wsize=3;

projXTraite=filter(ones(wsize)/wsize,1,projX);
projXTraite=projXTraite((wsize+1):end);

projYTraite=filter(ones(wsize)/wsize,1,projY);
projYTraite=projYTraite((wsize+1):end);

% 4 ) Recherche de pics par la dérivée :
tic 
indicePicX=detectionPicDerivee(projXTraite,2,tailleimg(2)/9) 
erX=testResultatDerivee(projXTraite,indicePicX)
tempsCalculX=toc

tic 
indicePicY=detectionPicDerivee(projYTraite',2,tailleimg(1)/9)
erY=testResultatDerivee(projYTraite',indicePicY)

tempsCalculY=toc
%Remarque : l'epaisseur du pic est 1/9 ième de la taille de l'image 

% 5 ) Affichage des pics trouvés : 

imgTraite=img_o;
imgTraite(:,indicePicX)=0;
imgTraite(indicePicY,:)=0;
figure()
imshow(imgTraite)
title('detection par la methode de la derivee : image orientee, filtree')

end
