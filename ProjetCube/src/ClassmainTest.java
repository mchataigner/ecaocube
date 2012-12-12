import cube.*;
import cube.emetteur.*;
import cube.resolution.*;
import java.util.Scanner;
import glcube.*;

import vision.*;

import acquisition.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;

public class ClassmainTest{
    public static Algorithme easy(String stringalgo)throws Throwable
    {
	FausseDetection fake;
        Algorithme algo= new Algorithme(stringalgo);
        fake = new FausseDetection(algo);
        
	Cube leCube = fake.detecter(null,null,null,null,null,null);
        Cube test = Cube.creerCube(leCube.toFace());

	/* Résolution */
	ResolutionDuCube er2 = new EasyResolution(test);
	Algorithme Soluce2 = new Algorithme();
	Soluce2 = er2.trouverSolution();
	
        return Soluce2;
        
    }

    public static Algorithme medium(String stringalgo)throws Throwable
    {
	FausseDetection fake;
        Algorithme algo= new Algorithme(stringalgo);
        fake = new FausseDetection(algo);
        
	Cube leCube = fake.detecter(null,null,null,null,null,null);
        Cube test = Cube.creerCube(leCube.toFace());

	/* Résolution */
	Algorithme Soluce = new Algorithme();
	//EasyResolution er = new EasyResolution(leCube);
	ResolutionDuCube er = new MediumResolution(leCube);
	Soluce = er.trouverSolution();
	
        return Soluce;
        
    }


    public static void main(String[] args)throws Throwable
    {
        /*        for(String i:args)
            {
                System.out.println(i+" : "+benchmark(i));
            }
        */
        String chaine="";
        String fichier ="test";
        Algorithme easy,medium;
        int nb=0;
        int moy=0;
        int moyEasy=0;
        int moyMedium=0;
        /*InputStream ips=new FileInputStream(fichier); 
        InputStreamReader ipsr=new InputStreamReader(ips);
        BufferedReader br=new BufferedReader(ipsr);*/
        FileReader f=new FileReader(fichier);
        
        BufferedReader br=new BufferedReader(f);
        String ligne;
        while ((ligne=br.readLine())!=null){
            //System.out.println(ligne+" : "+benchmark(ligne));
            //System.out.println(benchmark(ligne));
            chaine+=ligne+"\n";
            nb++;
            easy=easy(ligne);
            medium=medium(ligne);
            
            moy+=medium.taille()-easy.taille();
            moyEasy+=easy.taille();
            moyMedium+=medium.taille();
        }
        if(nb!=0)
            {
                
                float m=((float)moy)/((float)nb);
                float me=((float)moyEasy)/((float)nb);
                float mm=((float)moyMedium)/((float)nb);
                
                System.out.println("moyenne : "+m+"/"+me+" avec en moyenne "+mm+" sur "+nb+" mélanges");
                //System.out.println(moy+" "+moyEasy+" "+moyMedium+" "+nb);
                
                
            }
        
        br.close(); 
        
    }
    
} 
