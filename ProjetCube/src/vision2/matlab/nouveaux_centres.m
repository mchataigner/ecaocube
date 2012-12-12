function nouveauCentre = nouveaux_centres(X, z)

K=max(z);
for i=1:K 
	ind=find(z==i);
	Xk=X(ind,:);
	nouveauCentre(i,:)=mean(Xk,1);
end 