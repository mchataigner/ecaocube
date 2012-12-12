function [dist] = distances_euclidienne(X,mu)
[l_X c_X] = size(X);
[l_mu c_mu] = size(mu);

for i=1:l_X 
	for j=1:l_mu
		dist(i,j) = norm(X(i, :)-mu(j, :));
	end 
end

