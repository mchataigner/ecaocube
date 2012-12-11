function [variancefinale] = varianceCoordonnee(vecteur, longueurVariance)

longueurVecteur=length(vecteur);
variancefinale=zeros(size(vecteur));

% J'initalise les premières valeurs 
%vecteur(1:longueurVariance)=ones(size(vecteur(1:longueurVariance)))*vecteur(longueurVariance);
for i=1:longueurVariance
	variancefinale(i)=var(vecteur(1:i));
end

% J'initialise les valeurs suivantes comme la variance des 'longueurVariance' point précédents 
for i=(longueurVariance+1):longueurVecteur
	debut=i-longueurVariance ;
	variancefinale(i)=var(vecteur(debut:i));
end

