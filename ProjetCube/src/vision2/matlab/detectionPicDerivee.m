function [indicePic]=detectionPicDerivee(V, nbPic, epaisseurPic)

% Trouver les extremums 
deriveeV=diff(V); 
signeV=sign(deriveeV);
signeV_decale=[0 signeV];
signeV=[signeV 0];
changementSign=signeV+signeV_decale;

indicePic=find(changementSign==0);

% Trier les minimums 

AllPic=zeros(1,length(V));
AllPic(indicePic)=(signeV(indicePic)==1);

% Trier le bon nombre de pic 
% Trier en fonction des distance 

distanceRespectee = 0; % true à changer 
indicePic=[];
indiceAllPic=find(AllPic==1);

while (nbPic>length(indicePic))
	[minPicTeste, indicePicTeste]=min(V(indiceAllPic));
	if(length(indicePic)==0)
		indicePic=indiceAllPic(indicePicTeste);
		V(indiceAllPic(indicePicTeste))=max(V);
	else 
		% Je vérifie que le nouveau pic n'ai pas trop proche d'un autre pic déjà connu 
		if(abs(min(indicePic-indiceAllPic(indicePicTeste)))>epaisseurPic)
			distanceRespectee = 0; % true à changer 
		else 
			distanceRespectee = 1;		
		end 
		if (distanceRespectee == 0)
			indicePic=[indicePic indiceAllPic(indicePicTeste)];
		end
		V(indiceAllPic(indicePicTeste))=max(V);
	end
end

