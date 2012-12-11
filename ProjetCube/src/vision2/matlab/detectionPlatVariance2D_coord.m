function [plat, erreur] = detectionPlatVariance2D_coord(image, largeurFenetre)
%cette fonction n'est pas très propre! il faudra songer a rendre ça beau


nombreDePlat=2; 
[hauteur, largeur]=size(image);
pas=round(largeurFenetre/2); 
erreur=0;

x=[1:pas:largeur-largeurFenetre];
y=[1:pas:hauteur-largeurFenetre];

% Construction de la variance : 
plat=[];
variance=[];
for i=1:length(x)
	for j=1:length(y)
		img=image(y(j):y(j)+largeurFenetre,x(i):x(i)+largeurFenetre);
		variance(j,i)=var(var(img));
	end
end
varianceLog=log(variance);

% Affichage de la variance 
figure
surf(varianceLog')
title('Logarithme de la Variance de l image')

% Recherche du seuil 
maxVarianceLog=max(max(varianceLog));
plat=[];
for i=1:nombreDePlat
	[plat,varianceLog]=trouverUnPlat(plat, varianceLog, maxVarianceLog, pas, largeur, hauteur);
end

% Affichage de la variance 
figure
surf(varianceLog')
title('Logarithme de la Variance de l image')

% Verification des distances entre les plats choisis : 
seuilMoy=1;

% Je trie les plats 'surs'

distX = distances_euclidienne(plat(:,1),plat(:,1));
% J'enlève les 0 sur les diagonales :
distX = distX+max(max(distX))*eye(size(distX));
% Vérification des distances : 
minDistX=min(distX);
moyMinDistX=mean(minDistX);
indiceX=find(minDistX<seuilMoy);%*moyMinDistX);
plat=plat(indiceX,:);


distY = distances_euclidienne(plat(:,2),plat(:,2));
% J'enlève les 0 sur les diagonales :
distY=distY+max(max(distY))*eye(size(distY));
% Vérification des distances : 
minDistY=min(distY);
moyMinDistY=mean(minDistY);
indiceY=find(minDistY<seuilMoy);
if(length(indiceY)!=0)
	plat=plat(indiceY,:);
end

[nombreDePlatEnCours, rien]=size(plat);

% Je construit un model qui représente  le rubic avec les points que je connais : 

[valeurX, I, J]=unique(plat(:,1));
lX=length(valeurX);
[valeurY, I, J]=unique(plat(:,2));
lY=length(valeurY);
distanceX=max(valeurX)-min(valeurX);
distanceY=max(valeurY)-min(valeurY);

if(lX==1)
	if(valeurX>3*largeur/4)
		% si le 1 point à droite: 
			ordonneX=[valeurX ; valeurX-distanceY; valeurX-2*distanceY];
	else
		% si le 1 point à gauche: 
		if(valeurX<2*largeur/4)
			ordonneX=[valeurX ; valeurX+distanceY; valeurX+2*distanceY];
		else
		% si les un points au milieu :
			ordonneX=[valeurX ; valeurX-distanceY; valeurX+distanceY];
		end
	end
end

if(lX==2)
	if(max(valeurX)>3*largeur/4)
		% si les 2 points sont voisins et à droite: 
		if(min(valeurX)>2*largeur/4)
			ordonneX=[valeurX ; min(valeurX)-distanceX];
		% si les deux points ont un point entre eux :
		else
			ordonneX=[valeurX ; min(valeurX)+round(distanceX/2)];
		end
	else 
		% si les 2 points sont voisins et à gauche: 
		ordonneX=[valeurX ; max(valeurX)+distanceX];
	end
end

if(lX==3)
	ordonneX=valeurX;
end

if(lX==4)
	[x, ix]=min(valeurX);
	ordonneX=[valeurX(1:ix-1,:) ; valeurX(ix+1:end,:)];	
end


if(lY==1)
	if(valeurY>2*hauteur/3)
		% si le 1 point à droite: 
			ordonneY=[valeurY ; valeurY-distanceX; valeurY-2*distanceX];
	else
		% si le 1 point à gauche: 
		if(valeurY<hauteur/3)
			ordonneY=[valeurY ; valeurY+distanceX; valeurY+2*distanceX];
		else
		% si les un points au milieu :
			ordonneY=[valeurY ; valeurY-distanceX; valeurY+distanceX];
		end
	end
end

if(lY==2)
	if(max(valeurY)>2*hauteur/3)
		% si les 2 points sont voisins et à droite: 
		if(min(valeurY)>hauteur/3)
			ordonneY=[valeurY ; min(valeurY)-distanceY];
		% si les deux points ont un point entre eux :
		else
			ordonneY=[valeurY ; min(valeurY)+round(distanceY/2)];
		end
	else 
		% si les 2 points sont voisins et à gauche: 
		ordonneY=[valeurY ; max(valeurY)+distanceY];
	end
end

if(lY==3)
	ordonneY=valeurY;
end

if(lY==4)
	dist_vY = distances_euclidienne(valeurY,valeurY);
	% J'enlève les 0 sur les diagonales :
	dist_vY=dist_vY+max(max(dist_vY))*eye(size(dist_vY));
	[y, iy]=max(min(dist_vY));
	ordonneY=[valeurY(1:iy-1,:) ; valeurY(iy+1:end,:)];
end

if((lY>4) || (lX>4) || (lX==1 && lY==1))
	erreur=1;
	plat=[];
else
	if( (min(ordonneX)<0) || (max(ordonneX)>largeur) || (min(ordonneY)<0) || (max(ordonneY)>hauteur) ) % s'il y a une coordonnée erronee 
		erreur=1;
		plat=[];
	else
	platX = [ordonneX; ordonneX; ordonneX];
	platY = [ordonneY(1)*ones(3,1); ordonneY(2)*ones(3,1); ordonneY(3)*ones(3,1)];
	plat=[platX, platY];
	end
end

