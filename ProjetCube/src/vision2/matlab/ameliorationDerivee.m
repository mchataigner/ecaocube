function [indicePic]=ameliorationDerivee(V,indicePic,epaisseurPic)
	seuil = 10;
	indicePic=sort(indicePic);
	espaceInterPic=indicePic(2)-indicePic(1);
	variance1=var(V((indicePic(1)-espaceInterPic):indicePic(1)));
	variance2=var(V(indicePic(2):(indicePic(2)+espaceInterPic)));
	if(abs(variance1 - variance2)>seuil)
		indicePic2=detectionPicDerivee(V, 3, epaisseurPic);
		indicePic2=sort(indicePic2);
		indiceNouveauPic=setdiff(indicePic2,indicePic);
		if(indicePic(1)<indiceNouveauPic && indicePic(2)>indiceNouveauPic)
			espaceInterPic=round(espaceInterPic/2);
			variance1=var(V((indicePic(1)-espaceInterPic):indicePic(1)));
			variance2=var(V(indicePic2:(indicePic(2)+espaceInterPic)));
			if(variance1>variance2)
				indicePic=indicePic2(2:3);
			else
				indicePic=indicePic2(1:2);
			end
		else 
			if(indiceNouveauPic>indicePic(2))
				indicePic=indicePic2(2:3);
			else
				indicePic=indicePic2(1:2);
			end
		end
		
	end
