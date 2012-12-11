A=1:9;

fid = fopen('test.txt', 'w');
for i=1:6
	fprintf(fid,'%i\t %i\t %i\t %i\t %i\t %i\t %i\t %i\t %i\n',A);
end
fclose(fid);
