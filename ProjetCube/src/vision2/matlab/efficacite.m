clear
close all

% Notations utilisées : 
% image_coloree : image en 'couleur'
% image_no : image non orientée 
% image_o : image orientée 
% projX_nf_no, projY_nf_no : projection en X et Y non filtrée issus d'une image non orienté
% projX_nf_o, projY_nf_o : projection en X et Y non filtrée issus d'une image orienté
% projX_f_no,projY_f_no : projection en X et Y filtrée issus d'une image non orienté
% projX_f_o,projY_f_o : projection en X et Y filtrée issus d'une image orienté

% images : tableau des images à étudier 

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
dossiers=["conf4"; "conf5"; "conf6"; "conf7"; "conf8"];
faces=["face1"; "face2"; "face3"; "face4"; "face5"; "face6"];

[nombreFaces, rien]=size(faces);
[nombreDossiers,rien]=size(dossiers);

for idos=1:nombreDossiers
	for ifac=1:nombreFaces

%%%%%%%%%%%%%%%%%%%%%%%%%%%%% INITIALISATION DES IMAGES ET DES PROJECTIONS %%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		% IMAGE NO 
		fichierEntree=["../image/" dossiers(idos,:) "/" faces(ifac,:) ".png"];
		[image_coloree] = imread(fichierEntree);
		image_no = rgb2gray(image_coloree);

		% PROJ NF NO
		tailleimg=size(image_no);
		projX_nf_no=sum(image_no,1)/tailleimg(2);
		projY_nf_no=sum(image_no,2)/tailleimg(1);

		% PROJ F NO
		wsize=3;

		projX_f_no=filter(ones(1,wsize)/wsize,1,projX_nf_no);
		projX_f_no=projX_f_no((wsize+1):end);

		projY_f_no=filter(ones(1,wsize)/wsize,1,projY_nf_no);
		projY_f_no=projY_f_no((wsize+1):end);

		% IMAGE O
		[angle incertitude]=choixRotation(image_no,5,10)
		% image, angle de rotation, nombre de rotation
		image_o=imrotate(image_no,angle,"bicubic", "crop", 255);
		% nearest bilinear bicubic

		% PROJ NF O
		projX_nf_o=sum(image_o,1)/tailleimg(2);
		projY_nf_o=sum(image_o,2)/tailleimg(1);

		% PROJ F O

		projX_f_o=filter(ones(1,wsize)/wsize,1,projX_nf_o);
		projX_f_o=projX_f_o((wsize+1):end);

		projY_f_o=filter(ones(1,wsize)/wsize,1,projY_nf_o);
		projY_f_o=projY_f_o((wsize+1):end);

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% ETUDE DES DIFFERENTES METHHODES %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

		% METHODE : DERIVEE
	% Image 1 : 
		indicePicX=detectionPicDerivee(projX_f_no,2,tailleimg(2)/9) ;
		indicePicY=detectionPicDerivee(projY_f_no',2,tailleimg(2)/9) ;

		image_n=image_no;
		image_n(:,indicePicX)=0;
		image_n(indicePicY,:)=0;
		fichierSortie=["ImagesGenerees/" dossiers(idos,:) "/" faces(ifac,:) "/image_MetDerivee_f_no.png"];
		imwrite(image_n, fichierSortie);

	% Image 2 : 
		indicePicX=detectionPicDerivee(projX_f_o,2,tailleimg(2)/9) ;
		indicePicY=detectionPicDerivee(projY_f_o',2,tailleimg(2)/9) ;

		image_n=image_o;
		image_n(:,indicePicX)=0;
		image_n(indicePicY,:)=0;
		fichierSortie=["ImagesGenerees/" dossiers(idos,:) "/" faces(ifac,:) "/image_MetDerivee_f_o.png"];
		imwrite(image_n, fichierSortie);

	% Image 3 : 
		indicePicX=detectionPicDerivee(projX_nf_o,2,tailleimg(2)/9) ;
		indicePicY=detectionPicDerivee(projY_nf_o',2,tailleimg(2)/9) ;

		image_n=image_o;
		image_n(:,indicePicX)=0;
		image_n(indicePicY,:)=0;
		fichierSortie=["ImagesGenerees/" dossiers(idos,:) "/" faces(ifac,:) "/image_MetDerivee_nf_o.png"];
		imwrite(image_n, fichierSortie);

	% Image 4 : 
		indicePicX=detectionPicDerivee(projX_nf_no,2,tailleimg(2)/9) ;
		indicePicY=detectionPicDerivee(projY_nf_no',2,tailleimg(2)/9) ;

		image_n=image_no;
		image_n(:,indicePicX)=0;
		image_n(indicePicY,:)=0;
		fichierSortie=["ImagesGenerees/" dossiers(idos,:) "/" faces(ifac,:) "/image_MetDerivee_nf_no.png"];
		imwrite(image_n, fichierSortie);

		% METHODE : SEUILLAGE 
	% Image 1 : 
		indicePicX=detectionPicBarreGlissante(projX_f_o);
		indicePicY=detectionPicBarreGlissante(projY_f_o');

		image_n=image_o;
		image_n(:,indicePicX)=0;
		image_n(indicePicY,:)=0;
		fichierSortie=["ImagesGenerees/" dossiers(idos,:) "/" faces(ifac,:) "/image_MetSeuillage_f_o.png"];
		imwrite(image_n, fichierSortie);

	% Image 2 : 
		indicePicX=detectionPicBarreGlissante(projX_f_no);
		indicePicY=detectionPicBarreGlissante(projY_f_no');

		image_n=image_no;
		image_n(:,indicePicX)=0;
		image_n(indicePicY,:)=0;
		fichierSortie=["ImagesGenerees/" dossiers(idos,:) "/" faces(ifac,:) "/image_MetSeuillage_f_no.png"];
		imwrite(image_n, fichierSortie);

	% Image 3 : 
		indicePicX=detectionPicBarreGlissante(projX_nf_o);
		indicePicY=detectionPicBarreGlissante(projY_nf_o');

		image_n=image_o;
		image_n(:,indicePicX)=0;
		image_n(indicePicY,:)=0;
		fichierSortie=["ImagesGenerees/" dossiers(idos,:) "/" faces(ifac,:) "/image_MetSeuillage_nf_o.png"];
		imwrite(image_n, fichierSortie);

	% Image 4 : 
		indicePicX=detectionPicBarreGlissante(projX_nf_no);
		indicePicY=detectionPicBarreGlissante(projY_nf_no');

		image_n=image_no;
		image_n(:,indicePicX)=0;
		image_n(indicePicY,:)=0;
		fichierSortie=["ImagesGenerees/" dossiers(idos,:) "/" faces(ifac,:) "/image_MetSeuillage_nf_no.png"];
		imwrite(image_n, fichierSortie);

		% METHODE : VARIANCE 1D 
	% Image 1 : 
		platX = detectionPlatVariance1D(projX_f_o, 25, 3, tailleimg(2)/9);
		platY = detectionPlatVariance1D(projY_f_o', 25, 3, tailleimg(1)/9);

		image_n=image_o;
		image_n(:,platX)=0;
		image_n(platY, :)=0;
		fichierSortie=["ImagesGenerees/" dossiers(idos,:) "/" faces(ifac,:) "/image_MetVariance1D_f_o.png"];
		imwrite(image_n, fichierSortie);

	% Image 2 : 
		platX = detectionPlatVariance1D(projX_f_no, 25, 3, tailleimg(2)/9);
		platY = detectionPlatVariance1D(projY_f_no', 25, 3, tailleimg(1)/9);

		image_n=image_no;
		image_n(:,platX)=0;
		image_n(platY, :)=0;
		fichierSortie=["ImagesGenerees/" dossiers(idos,:) "/" faces(ifac,:) "/image_MetVariance1D_f_no.png"];
		imwrite(image_n, fichierSortie);

	% Image 3 : 
		platX = detectionPlatVariance1D(projX_nf_o, 25, 3, tailleimg(2)/9);
		platY = detectionPlatVariance1D(projY_nf_o', 25, 3, tailleimg(1)/9);

		image_n=image_o;
		image_n(:,platX)=0;
		image_n(platY, :)=0;
		fichierSortie=["ImagesGenerees/" dossiers(idos,:) "/" faces(ifac,:) "/image_MetVariance1D_nf_o.png"];
		imwrite(image_n, fichierSortie);

	% Image 4 : 
		platX = detectionPlatVariance1D(projX_nf_no, 25, 3, tailleimg(2)/9);
		platY = detectionPlatVariance1D(projY_nf_no', 25, 3, tailleimg(1)/9);

		image_n=image_no;
		image_n(:,platX)=0;
		image_n(platY, :)=0;
		fichierSortie=["ImagesGenerees/" dossiers(idos,:) "/" faces(ifac,:) "/image_MetVariance1D_nf_no.png"];
		imwrite(image_n, fichierSortie);

		% METHODE : VARIANCE 2D 
	% Image 1 : 
		[hauteur, largeur]=size(image_no);
		largeurFenetre=hauteur/8;
		nombreDePlat=9; 
		[plat, er] = detectionPlatVariance2D_coord(image_no, largeurFenetre, nombreDePlat);
		if(er==0)
			platX=plat(:,1)';
			platY=plat(:,2)';
	
			image_n=image_no;
			image_n(:,platX)=0;
			image_n(platY, :)=0;
			fichierSortie=["ImagesGenerees/" dossiers(idos,:) "/" faces(ifac,:) "/image_MetVariance2D_no.png"];
			imwrite(image_n, fichierSortie);
		else
			% Erreur : pas de solution par variance 2D
			printf(["Echec de la méthode Variance 2D pour " dossiers(idos,:) "/" faces(ifac,:) "\n"])
		end

	% Image 2 : 
		[hauteur, largeur]=size(image_o);
		largeurFenetre=hauteur/8;
		nombreDePlat=9; 
		[plat, er] = detectionPlatVariance2D_coord(image_no, largeurFenetre, nombreDePlat);
		if(er==0)
			platX=plat(:,1)';
			platY=plat(:,2)';
	
			image_n=image_o;
			image_n(:,platX)=0;
			image_n(platY, :)=0;
			fichierSortie=["ImagesGenerees/" dossiers(idos,:) "/" faces(ifac,:) "/image_MetVariance2D_o.png"];
			imwrite(image_n, fichierSortie);
		else
			% Erreur : pas de solution par variance 2D
			printf(["Echec de la méthode Variance 2D pour " dossiers(idos,:) "/" faces(ifac,:) "\n"])
		end

	end
end 
