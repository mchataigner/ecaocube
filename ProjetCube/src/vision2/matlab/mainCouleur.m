clear
close all

% Définition : %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Cube = 6 Face 													%
% Face = 2 image orientée:colorée et non colorée,1 projection en X,1 projection en Y,9 Carre,1 Centre,VarianceCentre	%
% Carre = 4 Point, 1 PointCentre, (R G B) Couleurs 									%
% Centre = 1 Point 													%
% Point = (X,Y) Coordonnees, 		 										%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% Exemple : %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% cube(icu).face(ifa).centre				%
% cube(icu).face(ifa).image				%
% cube(icu).face(ifa).projX				%
% cube(icu).face(ifa).carre(ica).point(ipo).coord	%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% Initialisation (à changer)---------------------------------------------------------------
castest=0;
casKMean=1;
casKPPV=0; 

% Initialisation (à ne pas changer)---------------------------------------------------------------

faces=['face1.png'; 'face2.png'; 'face3.png'; 'face4.png'; 'face5.png'; 'face6.png' ];
%faces=['face1.jpg'; 'face2.jpg'; 'face3.jpg'; 'face4.jpg'; 'face5.jpg'; 'face6.jpg' ];

erreurContour=0;
erreurReflet=0;
dossier='../../../images';
%dossier='../image/conf5';

wsize=5;
toleranceCarre=0.2;

vectcube=1;
vectface=1:6;
vectcarre=1:9;
vectpoint=1:4;


% Boucle d'initialisation : 

for icu=vectcube
	% ------------------------------------------------------------------ Initialisation Cube 
	for ifa=vectface
		% --------------------------------------------------------- Initialisation Image 
		% Lecture du fichier contenant l'image -----------------------------------------
		% ------------------------------------------------------------------------------
		fichier=[dossier '/' faces(ifa,:)];
		imgcolor = imread(fichier);
		img = rgb2gray(imgcolor);
		% ------------------------------------------------------------------------------
		% Rotation de l'image ----------------------------------------------------------
		% ------------------------------------------------------------------------------
		[angle incertitude]=choixRotation(img,5,10);	%image, angle de rotation, nombre de rotation
		img_o=imrotate(img,angle,'bicubic','crop',255);	%nearest bilinear bicubic
		cube(icu).face(ifa).image=img_o ; 
		imgcolor_o=imrotate(imgcolor,angle,'bicubic','crop',255);	%nearest bilinear bicubic
		cube(icu).face(ifa).image=imgcolor_o ; 
		% ------------------------------------------------------------------------------
		% Projection de l'image --------------------------------------------------------
		% ------------------------------------------------------------------------------
		tailleimg=size(img_o);
		% ------------------------------------------------------------------------------
		projX=sum(img_o,1)/tailleimg(2);
		projX=filter(ones(1,wsize)/wsize,1,projX);
		projX=projX((wsize+1):end);
		cube(icu).face(ifa).projX=projX((wsize+1):end);
		% ------------------------------------------------------------------------------
		projY=sum(img_o,2)/tailleimg(1);
		projY=filter(ones(1,wsize)/wsize,1,projY);
		projY=projY((wsize+1):end);
		cube(icu).face(ifa).projY=projY((wsize+1):end);

		% ---------------------------------------------------------- Initialisation Face
		% Récupérer contour de la face -------------------------------------------------
		% ------------------------------------------------------------------------------
								 %avirer
		vectX=[1:length(projX)];
		vectY=[1:length(projY)];

		figure()
		plot(vectX,projX,'b')
		legend('projX')

		figure()
		plot(vectY,projY', 'r')
		legend('projY')

		indicePicX=detectionPicDerivee(projX,2,tailleimg(2)/9) ;		
		%indicePicX=ameliorationDerivee(projX,indicePicX,tailleimg(2)/9);
		erX=testResultatDerivee(projX,indicePicX);
		indicePicY=detectionPicDerivee(projY',2,tailleimg(1)/9);
		%indicePicY=ameliorationDerivee(projY',indicePicY,tailleimg(2)/9);
		erY=testResultatDerivee(projY',indicePicY);
		
		%%%%%%%%%%%%%%%Partie test à virer%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
			
		if(erY && castest)
			figure()
			image_n=img_o;
			image_n(:,indicePicX)=0;
			image_n(indicePicY,:)=0;
			imshow(image_n)
		end
			
		%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

		erreurContour=erreurContour||erX||erY ;

		if(erreurContour==0)	 %%%%%%%% Si on a une erreur de contour on ne cherche pas à mettre des couleurs 

			% ------------------------------------------------------------------------------
			% Initialiser les distances caractéristiques d'un carré ------------------------
			% ------------------------------------------------------------------------------
			dX=abs(indicePicX(1)-indicePicX(2));
			dY=abs(indicePicY(1)-indicePicY(2));
			d=0.5*(dX+dY);
			% --------------------------------------------------------- Initialisation Carre
			% Coordonnée des points et du centre caractéristiques d'un carré----------------
			% ------------------------------------------------------------------------------
			if(castest==1)
				figure()
				imshow(img_o)
			end
			ica=0;
			for iy=[-0.5 0.5 1.5]
				for ix=[-0.5 0.5 1.5]
					
					ica=ica+1;
					coordonnee=round([min(indicePicX)+ix*d, min(indicePicY)+iy*d]);
					if(castest==1)	
						c1=round(coordonnee+toleranceCarre*d*ones(size(coordonnee)));
						c2=round(coordonnee-toleranceCarre*d*ones(size(coordonnee)));
						c3=round(coordonnee+[toleranceCarre*d -toleranceCarre*d]);
						c4=round(coordonnee+[-toleranceCarre*d toleranceCarre*d]);
						hold on 
						plot(coordonnee(1), coordonnee(2),'ro')
						plot(c1(1),c1(2),'bo')
						plot(c2(1),c2(2),'bo')
						plot(c3(1),c3(2),'bo')
						plot(c4(1),c4(2),'bo')
						hold off
					end
					cube(icu).face(ifa).carre(ica).centrepoint.coord=coordonnee;
					cube(icu).face(ifa).carre(ica).point(1).coord=round(coordonnee-toleranceCarre*d*ones(size(coordonnee)));
					cube(icu).face(ifa).carre(ica).point(2).coord=round(coordonnee+toleranceCarre*d*ones(size(coordonnee)));
					cube(icu).face(ifa).carre(ica).point(3).coord=round(coordonnee+[toleranceCarre*d -toleranceCarre*d]);
					cube(icu).face(ifa).carre(ica).point(4).coord=round(coordonnee+[-toleranceCarre*d toleranceCarre*d]);
				end
			end
			% ------------------------------------------------------------------------------
			% Initialiser la couleur d'un carré --------------------------------------------
			% ------------------------------------------------------------------------------
			for ica=vectcarre
				cube(icu).face(ifa).carre(ica).RGB=[0 0 0];
				for iCouleur=1:3
					cube(icu).face(ifa).carre(ica).RGB(iCouleur)=mean(mean(imgcolor_o(cube(icu).face(ifa).carre(ica).point(1).coord(2):cube(icu).face(ifa).carre(ica).point(2).coord(2),cube(icu).face(ifa).carre(ica).point(1).coord(1):cube(icu).face(ifa).carre(ica).point(2).coord(1),iCouleur)));
				end
			end
			% ------------------------------------------------------------------------------
			% Tester la variance des carrées centraux---------------------------------------
			% ------------------------------------------------------------------------------
			ica=5;
			cube(icu).face(ifa).varianceCentre=0;
			cube(icu).face(ifa).varianceCentre=var(var(img_o(cube(icu).face(ifa).carre(ica).point(1).coord(2):cube(icu).face(ifa).carre(ica).point(2).coord(2),cube(icu).face(ifa).carre(ica).point(1).coord(1):cube(icu).face(ifa).carre(ica).point(2).coord(1))));

		end        %%%%%%%% Si on a une erreur de contour on ne cherche pas à mettre des couleurs 	
	end
end

if(erreurContour==0)	    %%%%%%%% Si on a une erreur de contour on ne cherche pas à mettre des couleurs 

	% ------------------------------------------------------------------------------
	%Représentation des éléments RGB des 9 carrés des faces ------------------------
	% ------------------------------------------------------------------------------
	x=[];
	for icu=vectcube
		for ifa=vectface
			% récupération de l'information couleur
			for ica=vectcarre
				x=[x ; cube(icu).face(ifa).carre(ica).RGB];
			end
		end
	end

	if(castest==1)
		figure()
		plot3(x(:,1),x(:,2),x(:,3),'+')
		xlabel('R');
		ylabel('G');
		zlabel('B');
		title('representation des 54 carre en RGB');
	end

	% Construction du vecteur de référence composé des centres ------------------------
	xCentre=[];
	for icu=vectcube
		VarCentre=[];
		for ifa=vectface
			ica=5; % J'étudie le centre des faces
			xCentre(ifa,:,icu)=cube(icu).face(ifa).carre(ica).RGB;
		
			% récupération de l'information variance
			VarCentre=[VarCentre; cube(icu).face(ifa).varianceCentre];
		end
		[i,indiceCentreBlanc]=max(VarCentre);
		x(5+(indiceCentreBlanc-1)*9,:)=[255, 255, 255];
		xCentre(indiceCentreBlanc,:,icu)=[255, 255, 255];

		% Affichage de la variance des centres 	
		if (castest==1)
			figure()
			plot(VarCentre)
			title('variance des carres centraux')
		end
	end

	% Construction du vecteur contenant tous les carrés non centres 
	for icu=vectcube
		i=0;
		for ifa=vectface
			for ica=[1:4,6:9]
				i=i+1;
				xNonCentre(i,:,icu)=cube(icu).face(ifa).carre(ica).RGB;
			end
		end
	end

	yCentre= [1;2;3;4;5;6];

	% Test avec les plus proches centres %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	if(casKPPV==1)

		[yNonCentre]=kppv( xNonCentre,xCentre, yCentre) 

		if(castest==1)
			figure()
			vectAffichage1=['bo'; 'yo'; 'ro'; 'mo'; 'go'; 'co'];
			vectAffichage2=['b*'; 'y*'; 'r*'; 'm*'; 'g*'; 'c*'];
			plot3(xCentre(1,1),xCentre(1,2),xCentre(1,3),'bo')
			hold on 
			indiceC=find(yNonCentre==1);
			plot3(xNonCentre(indiceC,1),xNonCentre(indiceC,2),xNonCentre(indiceC,3),vectAffichage2(1,:))
			size(indiceC)
			for i=2:6
				plot3(xCentre(i,1),xCentre(i,2),xCentre(i,3),vectAffichage1(i,:))
				indiceC=find(yNonCentre==i);
				plot3(xNonCentre(indiceC,1),xNonCentre(indiceC,2),xNonCentre(indiceC,3),vectAffichage2(i,:))
				size(indiceC)
			end
			hold off
			xlabel('R');
			ylabel('G');
			zlabel('B');
			title('representation des 54 carre en RGB');
		end

		% Association à tous les carrés centres de leur cluster
		i=0;
		for icu=vectcube
			for ifa=vectface
				i=1+i;
				cube(icu).face(ifa).carre(5).cluster=yCentre(i);
			end
		end

		% Association à tous les carrés non centres de leur cluster
		for icu=vectcube
			i=0;
			for ifa=vectface
				for ica=[1:4,6:9]
					i=i+1;
					cube(icu).face(ifa).carre(ica).cluster=yNonCentre(i);
				end
			end
		end

	end

% Test avec K-Means %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	if(casKMean==1)

		nbIteration=10;
		epsilon=0.01;
		[nouveauMu, y, er]=k_moyenne(x,6 ,nbIteration, epsilon, xCentre );
		erreurReflet=erreurReflet||er ; 

		if(castest==1)
			affichage(nouveauMu,1:6,1);
			nbClasse=affichage(x, y, 2);
			title('representation des Mu et des points de chaque cluster')
			xlabel('R');
			ylabel('G');
			zlabel('B');
		end 

		% Association à tous les carrés non centres de leur cluster
		for icu=vectcube
			i=0;
			for ifa=vectface
				for ica=vectcarre
					i=i+1;
					cube(icu).face(ifa).carre(ica).cluster=y(i);
				end
			end
		end
	end

end 			 %%%%%%%% Si on a une erreur de contour on ne cherche pas à mettre des couleurs 

% Création du fichier %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
fid = fopen('test.txt', 'w');
fprintf(fid,'%i\n',erreurContour);
fprintf(fid,'%i\n',erreurReflet);
if(erreurContour==0)
	for ifa=vectface
		for ica=vectcarre
			fprintf(fid,'%i\t',cube(1).face(ifa).carre(ica).cluster);
		end
		fprintf(fid,'\n');
	end
end
fclose(fid);
