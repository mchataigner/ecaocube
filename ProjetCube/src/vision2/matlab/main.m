clear
close all

imgcolor = imread('../image/conf4/face3.png');
img = rgb2gray(imgcolor);
%imshow(img);

%1) test de la rotation

[angle incertitude]=choixRotation(img,5,10)%image, angle de rotation, nombre de rotation

img2=imrotate(img,angle,'bicubic','crop',255);%nearest bilinear bicubic
figure()
imshow(img2)

tailleimg=size(img2);
projX=sum(img2,1)/tailleimg(2);
projY=sum(img2,2)/tailleimg(1);

%2) filtrage du signal
wsize=3;
projX=filter(ones(1,wsize)/wsize,1,projX);
projX=projX((wsize+1):end);
%projX=projX(1:50)=projX(51);

%projY=filter(ones(1,wsize)/wsize,1,projY);
%projY=projY((wsize+1):end);
%projY=projY(1:50)=projY(51);

figure()
plot(projX)
title('projection en X de la meilleure image')
print -dpng /tmp/projX.png
figure()
plot(projY)
title('projection en Y de la meilleure image')

%3) detection des pic  par dérivee

if(1)
img3=img2;
indicePicX=detectionPicDerivee(projX,2,tailleimg(2)/9) 
indicePicY=detectionPicDerivee(projY',2,tailleimg(1)/9)
%Remarque : l'epaisseur du pic est 1/9 ième de la taille de l'image 
img3(:,indicePicX)=0;
img3(indicePicY,:)=0;
figure()
imshow(img3)
title('detectionPicDerive')
end

% 4) detection des pic  par seuil 

if(1)
img4=img2;
indicePicX2=detectionPicBarreGlissante(projX)
indicePicY2=detectionPicBarreGlissante(projY')
img4(:,indicePicX2)=0;
img4(indicePicY2,:)=0;
figure()
imshow(img4)
title('detectionPicBarreGlissante')
print -dpng /tmp/detectionBarreGlissante
end


% Etude de la variance 
img5=img2;

% X sans traitement 
varianceX=varianceCoordonnee(projX, 25);
figure()
plot(varianceX)

% X avec traitement
projX(1:25)=ones(size(projX(1:25)))*projX(25);
varianceX2=varianceCoordonnee(projX, 25);
figure()
plot(varianceX2)


% Y sans traitement 
varianceY=varianceCoordonnee(projY', 25);
figure()
plot(varianceY)

% Affichage variance 
indiceTrouX=find(varianceX>70);
indiceTrouY=find(varianceY>70);

img5(:,indiceTrouX)=0;
img5(indiceTrouY,:)=0;

figure()
imshow(img5)
title('detection par l etude de la variance')

%a faire : 
% carré glissant, 
% variance sans rotation
% benchmark
% validation sur image



%~ % Etude de la autocorrelation 
%~ autoCorrX=xcorr(img2, 'biased')
%~ figure(9) 
%~ plot(autoCorrX)
