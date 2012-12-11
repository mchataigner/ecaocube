clear all
close all 

mu=[15,15,15 ; 15,150,150 ; 255,255,255 ; 100,15,15 ; 15,250,100 ; 250,45,100];
sigma=25;

taille=9;

x=zeros(taille*6,3);
y=zeros(taille*6,1);

for i=0:5 
	x(1+i*taille:(i+1)*taille,:)=ones(taille,1)*mu(i+1,:)+sigma*randn(taille,3);
	y(1+i*taille:(i+1)*taille,:)=(i+1)*ones(taille,1);
end

nbIteration = 10 ;
epsilon = 0.001 ; 

[nouveauMu, ynew]=k_moyenne(x,6 ,nbIteration, epsilon, mu );

affichage(mu,1:6,1);
nbClasse=affichage(x, y, 2)

affichage(nouveauMu,1:6,1);
nbClasse=affichage(x, ynew, 2)

% Calcule de l'erreur 
er = sum(ynew!=y)
