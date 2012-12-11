clear
close all

%Cette image est trop penché 
%imgcolor = imread('../image/conf4/face5.png');
%imgcolor = imread('../image/conf4/face4.png');
%imgcolor = imread('../image/conf7/face1.png');
imgcolor = imread('../image/conf7/face5.png');
img = rgb2gray(imgcolor);
%imshow(img)

[hauteur, largeur]=size(img);

largeurFenetre=hauteur/8;
[plat,er] = detectionPlatVariance2D_coord(img, largeurFenetre);
platX=plat(:,1)';
platY=plat(:,2)';
er

% 5 ) Affichage des plats trouvés : 
imgTraite=img;

figure()
imshow(imgTraite)
title('detection par la methode de la variance 2D ')
hold on 
plot(platX,platY,'r*')
hold off
