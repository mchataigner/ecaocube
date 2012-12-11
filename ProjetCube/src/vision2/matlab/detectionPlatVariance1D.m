function [plat] = detectionPlatVariance1D(vecteur, longueurVariance, nombreDePlat, largeurPlat)

variancefinale= varianceCoordonnee(vecteur, longueurVariance); 

fin=round(length(variancefinale)-largeurPlat);
pas=round(largeurPlat/2);

seuil=max(variancefinale)/4;
pasSeuil=max(variancefinale)/10;
plat=zeros(1,nombreDePlat+5);
% /!\ pour rentrer une fois dans la boucle 

% Recherche du seuil : 
while(length(plat)>nombreDePlat)
	j=1;
	plat_hold=plat;
	plat=[];
	for i=pas:pas:fin 
		Variances(j)=(mean(variancefinale(i:i+largeurPlat))<seuil);
		if(Variances(j)==1)
			plat=[plat , i];
		end
		j=j+1;
	end
	seuil=seuil-pasSeuil ; 
end

% Je n'ai pas exatement le bon nombre de plat : 
if(length(plat)<nombreDePlat) 
	plat=plat_hold;
	while(length(plat)>nombreDePlat)
		% Je calcul la matrice des distances : 
		D=distances_euclidienne(plat',plat');
		% Je supprime celui qui est le plus proche des autres ! 
		d=sum(D);
		[minPlat,indiceAEnlever]=min(d);
		indiceAEnlever=min(indiceAEnlever);
		plat=[plat(1:indiceAEnlever-1) plat(indiceAEnlever+1:end)];
	end
end
