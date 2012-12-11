function position = detectionPicBarreGlissante(X)
	plot(X)
	tolerance=round(1/9*length(X));
	pas=5;
	valMin=round(min(X));
	valMoy=round(mean(X,2));
	vect=[valMin:pas:valMoy];
	i=1;
	while(i < length(vect)+1)
		resultat=find (X < vect(i));
		resultatmp=[resultat 0]-[0 resultat];
		resultatmp=resultatmp(2:length(resultatmp)-2);
		if (max(resultatmp)>tolerance)
			position=resultat;
			i=length(vect)+1;
		end
		i=i+1;
	end
	
	

