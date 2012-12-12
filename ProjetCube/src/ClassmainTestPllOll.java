import cube.*;
import cube.emetteur.*;
import cube.resolution.*;
import java.util.*;
import glcube.*;

import vision.*;

import acquisition.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ClassmainTestPllOll{
    public static String benchmark(String stringalgo)throws Throwable
    {
	FausseDetection fake;
        Algorithme algo= new Algorithme(stringalgo);
        fake = new FausseDetection(algo);
        
	Cube leCube = fake.detecter(null,null,null,null,null,null);
        Cube test = Cube.creerCube(leCube.toFace());

	/* Résolution */
	Algorithme Soluce = new Algorithme();
	ResolutionDuCube er = new MediumResolution(leCube);
	Soluce = er.trouverSolution();
        return Soluce.toString();
        
    }


    public static void main(String[] args)throws Throwable
    {
        BaseAlgorithmes bd=new BaseAlgorithmes("src/xml/algo.xml");
        HashMap oll,pll;
        oll=bd.obtenirListeOLL();
        pll=bd.obtenirListePLL();
        
        for(Object i:oll.values())
            {
                System.out.println("oll "+i+" U\n    "+benchmark((new Algorithme((String)i+" U")).inverserAlgorithme().toString()));
                
            }

        for(Object i:pll.values())
            {
                System.out.println("pll "+i+" U\n    "+benchmark((new Algorithme((String)i+" U")).inverserAlgorithme().toString()));


            }

        
    }
} 
