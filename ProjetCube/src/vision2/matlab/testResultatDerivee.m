function [er]=testResultatDerivee(V,indicePic)

er=0; % ok 
indicePic=sort(indicePic);
d=abs(indicePic(2)-indicePic(1));

if ( (indicePic(1)-d)<0 || (indicePic(2)+d)>length(V) )
	er =1;
else 

	picPossible = [indicePic(1)-d, indicePic(1), indicePic(2), indicePic(2)+d];
	for i=1:length(picPossible)-1 
		varInterPic(i)=[var(V(picPossible(i):picPossible(i+1)))];
	end
	%if()

end
