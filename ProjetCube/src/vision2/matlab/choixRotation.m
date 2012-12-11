function [angle incertitude]=choixRotation(img,angle,nbRotation)
	rot=linspace(-angle,angle,nbRotation);
	tailleimg=size(img);
	X=zeros(nbRotation,tailleimg(2));
	Y=zeros(tailleimg(1),nbRotation);
	for j=1:nbRotation
		img2=imrotate(img,rot(j),'nearest','crop',255);%nearest bilinear bicubic
		X(j,:)=sum(img2,1);
		Y(:,j)=sum(img2,2);
	end

	%récupération de la meilleur rotation    
	minX=min(X')'; % minimum pour chaque courbe
	minY=min(Y); % minimum pour chaque courbe
	[tmp,indiceRotaX]=min(minX);
	[tmp,indiceRotaY]=min(minY);
	incertitude = abs(indiceRotaX -	indiceRotaY);
	indiceRota=round(mean([indiceRotaX,indiceRotaY],2));
	angle=rot(indiceRota);


	if(0)
	figure()
	plot(X')
	title('visualisation de la projection en X')
	figure()
	plot(Y)
	title('visualisation de la projection en Y')
	end
