function [nouveauMu, z, er]=k_moyenne(X,nbCluster,nbIteration, epsilon, initMu )

[rien, nbDonnees]=size(X);
nouveauMu=initMu;
ancienMu=nouveauMu;
Mu=nouveauMu+1000*epsilon; 
z=[];

while ( (i<nbIteration) && (abs(mean(mean(Mu-nouveauMu)))>epsilon) )
	M=distances_euclidienne(X, nouveauMu,0); 
	[z, er,erCard]=affectation(M);

	Mu=nouveauMu;
	nouveauMu=nouveaux_centres(X,z);
end

if (er==1)
	clusterAVider = find(erCard>9);
	clusterARemplir = find(erCard<9);
	while(length(clusterAVider)!=0)

%clusterAVider
%clusterARemplir
%erCard

		indice = find(z==clusterAVider(1));
		Mer=distances_euclidienne(X(indice,:), nouveauMu(clusterARemplir,:),0);
		% Nouvelle affectation 
		[minMer,indMinMer]=min(Mer);
		[minMinMer,indMinMinMer]=min(minMer);

		z(indice(indMinMer(indMinMinMer)))=clusterARemplir(indMinMinMer);

		iASupprimer = 1 ;
		iARemplir = indMinMinMer ;
		erCard(clusterAVider(iASupprimer)) = erCard(clusterAVider(iASupprimer)) -1 ;
		erCard(clusterARemplir(iARemplir)) = erCard(clusterARemplir(iARemplir)) +1 ;


		if(erCard(clusterAVider(iASupprimer))==9)
			clusterAVider = [clusterAVider(1:iASupprimer-1) clusterAVider(iASupprimer+1:end)];
		end
		if(erCard(clusterARemplir(iARemplir)) ==9)
			clusterARemplir = [clusterARemplir(1:iARemplir-1) clusterARemplir(iARemplir+1:end)];
		end 
	end
end 
