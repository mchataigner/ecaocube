function [y_new] = kppv( x_new,x, y) 

Dist=[];


y_new=[];
[Nj, rien]=size(x_new);
[N, rien]=size(x);
estACreer=(length(Dist)==0);

for j=1:Nj
	% Initialisation du vecteur des distances
	for i=1:N
	        val=(x_new(j,:)-x(i,:)).^2;
	        Dist(j,i) = sqrt(sum(val));
	end

	[rien, indiceMin]=min(Dist(j,:));
	y_new(j)=y(indiceMin);
end
