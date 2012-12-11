function [plat,varianceLog]=trouverUnPlat(plat, varianceLog, maxVarianceLog, pas, largeur, hauteur)

	margeZone=2;

	[minVarianceLog2D,I]=min(varianceLog); % Limité varianceLog au début ? 
	[minVarianceLog,J]=min(minVarianceLog2D);
	% Ajout des coordonnées du nouveau plat
	plat=[plat; J*pas I(J)*pas];
	% Effacage la zone pour le prochain tirage du min : 
	debut_i=I(J)-margeZone;
	fin_i=I(J)+margeZone;
	debut_j=J-margeZone;
	fin_j=J+margeZone;
	% Vérification des bornes :
	while(debut_i<1)
		debut_i=debut_i+1;
	end
	while(fin_i>largeur)
		fin_i=fin_i-1;
	end
	while(debut_j<1)
		debut_j=debut_j+1;
	end
	while(fin_j>hauteur)
		fin_j=fin_j-1;
	end
	varianceLog(debut_i:fin_i,debut_j:fin_j)=maxVarianceLog;
