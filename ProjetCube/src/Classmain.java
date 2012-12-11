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

public class Classmain{
  public static void main(String[] args)throws Throwable
  {
    boolean virtuel=false;
    Emetteur e=null;
    try
      {
	e=new Emetteur();
      }
    catch(Exception ex){virtuel=true;}
        
        
    /* Fake detection qui fonctionne avec saisie de l'algo de mélange par l'utilisateur */
    FausseDetection fake;
	
    if(args.length>=1 && ! args[0].equals(""))
      {
	for(int ii=0;ii<args.length;ii++)
	  System.out.println("\""+args[ii]+"\"");
	fake = new FausseDetection(new Algorithme(args[0]));
      }
    else 
      {
	Algorithme algo=new Algorithme("R U R U F D F U");
	fake = new FausseDetection(algo);
        
	if(!virtuel)
	  for(int ii=0;ii<algo.taille();ii++){
	    e.effectuerMouvementElementaire(algo.obtenirIEmeMouvement(ii).toString());
        
	System.out.println("toto");
	  }
      }
    // DetectionVision2 fake = new DetectionVision2();	

    Cube leCube = fake.detecter();
	
	
    /* Affichage de la vue éclatée sur la sortie standard */
    System.out.println("Cube après détection");
    System.out.println(leCube);

    /* Résolution */
    Algorithme Soluce = new Algorithme();
    //EasyResolution er = new EasyResolution(leCube);
    ResolutionDuCube er = new MediumResolution(leCube);
    //ResolutionDuCube er = new EasyResolution(leCube);
    Soluce = er.trouverSolution();
	
    /* Affichage de la solution sur la sortie standard */
    System.out.println("solution complète : "+Soluce.toString());
    System.out.println("Taille de la solution= "+Soluce.taille());
    System.out.println("Cube après résolution");
    System.out.println(leCube);

    /* Copie (clone) de la solution pour sauvegarde */
    Algorithme SoluceSave = Soluce.copier();


    /******************************************************************
	Partie algorithme robot
    ******************************************************************/
	
    /* Conversion de la solution en mouvement machine */	
    Mouvements algoRobot = new Mouvements(Soluce);
    algoRobot.conversion();
    /* Affichage de la solution machine */
    System.out.println(algoRobot);
    System.out.println(algoRobot.getNbMouvements()); 

    /******************************************************************
	Partie Affichage OpenGL
    ******************************************************************/

    /* On crée un cube identique au mélange pour l'affichage OpenGL */
    Cube copieDuCube = fake.detecter();
    GLCube.runGL(copieDuCube, true);

    Thread.sleep(5000);
	
    int avancement=0;
	
    /* Fénêtre affichant l'avancement de la résolution */
    JFrame maFrame = new JFrame("Avancement de la résolution");
    JPanel monPanel=new JPanel();
    maFrame.add(monPanel);
	
    JLabel description = new JLabel("Avancement de la résolution: ");
    JLabel compteur = new JLabel("0 %    ");
    monPanel.add(description,BorderLayout.CENTER);
    monPanel.add(compteur,BorderLayout.SOUTH);
	
    maFrame.pack();
    maFrame.setVisible(true);

    /* Résolution OpenGL */
    for(int i=0;i<SoluceSave.taille();i++)
      {
	avancement=((100*(i+1))/SoluceSave.taille());
	compteur.setText(avancement+" %");
	//System.out.print("Avancement de la résolution: "+avancement+"%");
	System.out.println(SoluceSave.obtenirIEmeMouvement(i).toString());

	//		Thread.sleep(500);


	GLCube.playMovement(SoluceSave.obtenirIEmeMouvement(i));
	//		GLCube.playMovement(MouvementElementaire.xp);
	GLCube.waitForMovementEnd();


	//copieDuCube.effectuerMouvementElementaire(SoluceSave.obtenirIEmeMouvement(i));
	if(!virtuel)
	  e.effectuerMouvementElementaire(SoluceSave.obtenirIEmeMouvement(i).toString());

      }
    if(!virtuel)
      e.effectuerMouvementElementaire("Q1");
    //System.exit(0);

  }
    
} 
