function [z, er, erCard]=affectation(M)

[minM, z]=min(M,[],2);
[N, longueurK]=size(M);
er=0;
erCard=1;
infiny=max(max(M));

erCard=[];
for k=1:longueurK 
	ind=find(z==k);
	erCard=[erCard , length(ind)];
	if(length(ind)!=9)
		er=1;
	end
end


