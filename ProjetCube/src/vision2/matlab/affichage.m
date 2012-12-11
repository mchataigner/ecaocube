function [cardClasse]=affichage(x, y, type_affichage)

if(type_affichage==1)
	figure()
	vectAffichage=['bo'; 'yo'; 'ro'; 'mo'; 'go'; 'co'];
else 
	vectAffichage=['b*'; 'y*'; 'r*'; 'm*'; 'g*'; 'c*'];
end 

if(type_affichage==2) 
	hold on 
end

indiceC=find(y==1);
plot3(x(indiceC,1),x(indiceC,2),x(indiceC,3),vectAffichage(1,:))
[cardClasse(1) rien]=size(indiceC);

if(type_affichage==1) 
	hold on 
end

for i=2:6
	indiceC=find(y==i);
	plot3(x(indiceC,1),x(indiceC,2),x(indiceC,3),vectAffichage(i,:))
	[cardClasse(i) rien]=size(indiceC);
end
hold off
