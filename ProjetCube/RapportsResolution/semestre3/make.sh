#!/bin/bash

cd images

for i in `ls *dia`;do dia -t eps $i;done
for i in `ls *eps`;do epstopdf $i;done
cd ..


