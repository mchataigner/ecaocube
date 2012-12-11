/**
* Classe qui stocke des mouvements effectués par le robot
* @author SOSI Camille Saillard, Nicolas Oliveira, Julien Pladeau
*/

package cube;
import java.util.*;

public class Mouvements
{
	/**
	* Stocke les mouvements de résolution élémentaires
	*/
	private ArrayList<MouvementElementaire> tableauDeMouvementElementaire;
	/**
	*Stocke les mouvements convertis
	*/
	private ArrayList<MouvementRobot> tableauDeMouvementRobot;
	/**
	* Atribut Stoquant les mouvements de base du robot
	*/
	private ArrayList<MouvementRobot> x,	y, z, zp, yp, xp, D, Dp;

	/**
	* Constructeur permettant d'initialiser le tableau de mouvement élémentaire
	* ainsi que les mouvements de base pour le robot
	* @param algoResolution
	*/
	public Mouvements(Algorithme algoResolution)
	{
		tableauDeMouvementRobot = new ArrayList<MouvementRobot>();
		tableauDeMouvementElementaire =  algoResolution.getAlgorithme();
		this.initMouvementDeBase();
	}
	
	/**
	* initialiser le tableau des mouvements de base pour le robot
	*/
	private void initMouvementDeBase()
	{
		x=new ArrayList<MouvementRobot>();
		x.add(MouvementRobot.A);
		
		y=new ArrayList<MouvementRobot>();
		y.add(MouvementRobot.B);
		
		Dp=new ArrayList<MouvementRobot>();
		Dp.add(MouvementRobot.C);
		
		xp=new ArrayList<MouvementRobot>(x);
		xp.add(MouvementRobot.A);
		xp.add(MouvementRobot.A);
		
		yp=new ArrayList<MouvementRobot>(y);
		yp.add(MouvementRobot.B);
		yp.add(MouvementRobot.B);
		
		D=new ArrayList<MouvementRobot>(Dp);
		D.add(MouvementRobot.C);
		D.add(MouvementRobot.C);
		
		zp=new ArrayList<MouvementRobot>(y);
		zp.addAll(x);
		zp.addAll(yp);
		
		z=new ArrayList<MouvementRobot>(yp);
		z.addAll(x);
		z.addAll(y);
	}
	
	
	/**
	* Méthode utilisé pour simplifier les mouvements doubles
	* Ex : R R ou L L etc
	*/
	public void simplifierLesDoublons()
	{
		int indice = 0,i=0,indiceTmp = 0;
		int tailleInit = tableauDeMouvementElementaire.size();
		
		while ( i<tailleInit -1)
		{
			//Il faut que 2 mouvements consécutifs soient identiques
			if (tableauDeMouvementElementaire.get(indice) == tableauDeMouvementElementaire.get(indice+1))
			{
				indiceTmp = remplacerDoublons(indice);
				//Dans le cas où on a remplacé un doublon
				if (indice!=indiceTmp)
				{
					i++; // SI il y a un doublons, alors on avance de 2 au lieu de 1
					indice = indiceTmp; // On prend le prochain indice du tableau pour ne rien ecraser
				}
				else indice++;
			}
			else indice++;
			i++;
		}
	}
	
	/**
	* Remplace les doublons à l'indice indice
	* @param indice indque le premier doublon
	* @return indique l'indice de fin
	*/
	public int remplacerDoublons(int indice)
	{
		switch (tableauDeMouvementElementaire.get(indice))
		{
			case R : 
				tableauDeMouvementElementaire.set(indice++,MouvementElementaire.z);
				tableauDeMouvementElementaire.add(indice++,MouvementElementaire.D);
				tableauDeMouvementElementaire.set(indice++,MouvementElementaire.D);
				tableauDeMouvementElementaire.add(indice++,MouvementElementaire.zp);
				break; 
			case Rp : 
				tableauDeMouvementElementaire.set(indice++,MouvementElementaire.z);
				tableauDeMouvementElementaire.add(indice++,MouvementElementaire.Dp);
				tableauDeMouvementElementaire.set(indice++,MouvementElementaire.Dp);
				tableauDeMouvementElementaire.add(indice++,MouvementElementaire.zp);
				break;
			case L : 
				tableauDeMouvementElementaire.set(indice++,MouvementElementaire.zp);
				tableauDeMouvementElementaire.add(indice++,MouvementElementaire.D);
				tableauDeMouvementElementaire.set(indice++,MouvementElementaire.D);
				tableauDeMouvementElementaire.add(indice++,MouvementElementaire.z);
				break; 
			case Lp : 
				tableauDeMouvementElementaire.set(indice++,MouvementElementaire.zp);
				tableauDeMouvementElementaire.add(indice++,MouvementElementaire.Dp);
				tableauDeMouvementElementaire.set(indice++,MouvementElementaire.Dp);
				tableauDeMouvementElementaire.add(indice++,MouvementElementaire.z);
				break;
		}
		return indice;
	}
	
	
	/**
	* Convertit l'algorithme de résolution en mouvement Robot
	*/
	public void conversion()
	{
		this.simplifierLesDoublons();

		for(int i=0;i<tableauDeMouvementElementaire.size();i++)
		{
			this.conversionDunMouvementElementaire(tableauDeMouvementElementaire.get(i));
			
		}
		this.simplification();
		this.conversionUltimeAlphonse();

	}
	
	/**
	* Convertit l'algorithme de résolution en mouvement Robot
	*/
	public void conversionUltimeAlphonse()
	{
	    if(!(tableauDeMouvementRobot.isEmpty()))
		    for(int i=0 ; i<(tableauDeMouvementRobot.size()) ; i++)
		    {
		    
		    
		    
		    
		        if (i<tableauDeMouvementRobot.size()-1)
		        {
			        //Simplification des B
			        if ( (tableauDeMouvementRobot.get(i) == MouvementRobot.B) && (tableauDeMouvementRobot.get(i+1) == MouvementRobot.B) )
			        {
			        	tableauDeMouvementRobot.remove(i+1);
                        tableauDeMouvementRobot.remove(i);
                        tableauDeMouvementRobot.add(i, MouvementRobot.B2);
			        
			        	if(i<(tableauDeMouvementRobot.size()-2) && (tableauDeMouvementRobot.get(i+1) == MouvementRobot.B))
			        	{			        		
                            tableauDeMouvementRobot.remove(i+1);
                            tableauDeMouvementRobot.remove(i);
                            tableauDeMouvementRobot.add(i, MouvementRobot.B3);
						}
					}


					// Simplification des C
					if ( (tableauDeMouvementRobot.get(i) == MouvementRobot.C) && (tableauDeMouvementRobot.get(i+1) == MouvementRobot.C) )
			        {
			        	tableauDeMouvementRobot.remove(i+1);
                        tableauDeMouvementRobot.remove(i);
                        tableauDeMouvementRobot.add(i, MouvementRobot.C2);
			        
			        	if(i<(tableauDeMouvementRobot.size()-2) && (tableauDeMouvementRobot.get(i+1) == MouvementRobot.C))
			        	{			        		
                            tableauDeMouvementRobot.remove(i+1);
                            tableauDeMouvementRobot.remove(i);
                            tableauDeMouvementRobot.add(i, MouvementRobot.C3);
						}
					}
					
					
				}
				                



            }                                           
	}
	
	
	/**
	* Affichage
	*/
	public String toString()
	{
		return tableauDeMouvementRobot.toString();
	}
	
	/**
	* Getter pour avoir le nombre de mouvement
	*/
	public int getNbMouvements()
	{
		return tableauDeMouvementRobot.size();
	}

	/**
	* Geter pour avoir le tableau de mouvements robots
	*/ 
	public ArrayList<MouvementRobot> getTableauMouvementsRobots()
	{	
		return tableauDeMouvementRobot;
	}
	/**
	* Simplifie la chaine d'instruction robot, si il y a 4 instructions identique de suite, elle sont enlevées
	*/
	public void simplification()
	{
		boolean aSupprime=true;
		ArrayList<Integer> tableauDIndice;
		int i ; 
		while (aSupprime)
		{
			i = 0 ;
			tableauDIndice = new ArrayList<Integer>();
						
			while (i<tableauDeMouvementRobot.size()-4)
			{
				if (tableauDeMouvementRobot.get(i) == tableauDeMouvementRobot.get(i+1) && tableauDeMouvementRobot.get(i) == tableauDeMouvementRobot.get(i+2)  && tableauDeMouvementRobot.get(i) == tableauDeMouvementRobot.get(i+3) )
				{
					tableauDIndice.add(i);
					i+=4;
				}
				else i++;
			}
			if (!tableauDIndice.isEmpty())
			{
				for (i=tableauDIndice.size()-1;i>=0;i--)
				{
					tableauDeMouvementRobot.remove(tableauDIndice.get(i)+3);
					tableauDeMouvementRobot.remove(tableauDIndice.get(i)+2);
					tableauDeMouvementRobot.remove(tableauDIndice.get(i)+1);
					tableauDeMouvementRobot.remove((int)tableauDIndice.get(i));
				}
			}
			else aSupprime = false;
		}
	}
	
	/** 
	* Switch case convertissant les mouvements elemenetaire en mouvement robot
	* @param ME indique un mouvement elementaire voulant etre converti
	*/
	public void conversionDunMouvementElementaire(MouvementElementaire ME)
	{
		switch(ME)
		{
			case x : 
				tableauDeMouvementRobot.addAll(x);
				break;
			case xp : 
				tableauDeMouvementRobot.addAll(xp);
				break;
			case y : 
				tableauDeMouvementRobot.addAll(y);
				break;
			case yp : 
				tableauDeMouvementRobot.addAll(yp);
				break;
			case z : 
				tableauDeMouvementRobot.addAll(z);
				break;
			case zp : 
				tableauDeMouvementRobot.addAll(zp);
				break;
			case D : 
				tableauDeMouvementRobot.addAll(D);
				break;
			case Dp : 
				tableauDeMouvementRobot.addAll(Dp);
				break;
			case L : 
				tableauDeMouvementRobot.addAll(zp);
				tableauDeMouvementRobot.addAll(D);
				tableauDeMouvementRobot.addAll(z);
				break;
			case Lp :
				tableauDeMouvementRobot.addAll(zp);
				tableauDeMouvementRobot.addAll(Dp);
				tableauDeMouvementRobot.addAll(z);
				break;
			case R : 
				tableauDeMouvementRobot.addAll(z);
				tableauDeMouvementRobot.addAll(D);
				tableauDeMouvementRobot.addAll(zp);
				break; 
			case Rp : 
				tableauDeMouvementRobot.addAll(z);
				tableauDeMouvementRobot.addAll(Dp);
				tableauDeMouvementRobot.addAll(zp);
				break;
			case U : 
				tableauDeMouvementRobot.addAll(x);
				tableauDeMouvementRobot.addAll(x);
				tableauDeMouvementRobot.addAll(D);
				tableauDeMouvementRobot.addAll(x);
				tableauDeMouvementRobot.addAll(x);
				break;
			case Up : 
				tableauDeMouvementRobot.addAll(x);
				tableauDeMouvementRobot.addAll(x);
				tableauDeMouvementRobot.addAll(Dp);
				tableauDeMouvementRobot.addAll(x);
				tableauDeMouvementRobot.addAll(x);
				break;
			case B : 
				tableauDeMouvementRobot.addAll(x);
				tableauDeMouvementRobot.addAll(D);
				tableauDeMouvementRobot.addAll(xp);
				break;
			case Bp : 
				tableauDeMouvementRobot.addAll(x);
				tableauDeMouvementRobot.addAll(Dp);
				tableauDeMouvementRobot.addAll(xp);
				break;
			case F : 
				tableauDeMouvementRobot.addAll(xp);
				tableauDeMouvementRobot.addAll(D);
				tableauDeMouvementRobot.addAll(x);
				break;
			case Fp : 
				tableauDeMouvementRobot.addAll(xp);
				tableauDeMouvementRobot.addAll(Dp);
				tableauDeMouvementRobot.addAll(x);
				break;
			case r : 
				tableauDeMouvementRobot.addAll(zp);
				tableauDeMouvementRobot.addAll(D);
				tableauDeMouvementRobot.addAll(z);
				break;
			case rp : 
				tableauDeMouvementRobot.addAll(zp);
				tableauDeMouvementRobot.addAll(Dp);
				tableauDeMouvementRobot.addAll(z);
				break;
			case lp : 
				tableauDeMouvementRobot.addAll(z);
				tableauDeMouvementRobot.addAll(Dp);
				tableauDeMouvementRobot.addAll(zp);
				break;
			case l : 
				tableauDeMouvementRobot.addAll(z);
				tableauDeMouvementRobot.addAll(D);
				tableauDeMouvementRobot.addAll(zp);
				break;
			case u : 
				tableauDeMouvementRobot.addAll(D);
				break;
			case up : 
				tableauDeMouvementRobot.addAll(Dp);
				break;
			case d : 
				tableauDeMouvementRobot.addAll(x);
				tableauDeMouvementRobot.addAll(x);
				tableauDeMouvementRobot.addAll(D);
				tableauDeMouvementRobot.addAll(x);
				tableauDeMouvementRobot.addAll(x);
				break;
			case dp : 
				tableauDeMouvementRobot.addAll(x);
				tableauDeMouvementRobot.addAll(x);
				tableauDeMouvementRobot.addAll(Dp);
				tableauDeMouvementRobot.addAll(x);
				tableauDeMouvementRobot.addAll(x);
				break;
			case b : 
				tableauDeMouvementRobot.addAll(xp);
				tableauDeMouvementRobot.addAll(D);
				tableauDeMouvementRobot.addAll(x);
				break;
			case bp : 
				tableauDeMouvementRobot.addAll(xp);
				tableauDeMouvementRobot.addAll(Dp);
				tableauDeMouvementRobot.addAll(x);
				break;
			case f : 
				tableauDeMouvementRobot.addAll(x);
				tableauDeMouvementRobot.addAll(D);
				tableauDeMouvementRobot.addAll(xp);
				break;
			case fp : 
				tableauDeMouvementRobot.addAll(x);
				tableauDeMouvementRobot.addAll(Dp);
				tableauDeMouvementRobot.addAll(xp);
				break;		
			case M : 
				tableauDeMouvementRobot.addAll(z);
				tableauDeMouvementRobot.addAll(D);
				tableauDeMouvementRobot.addAll(zp);
				tableauDeMouvementRobot.addAll(zp);	
				tableauDeMouvementRobot.addAll(Dp);
				tableauDeMouvementRobot.addAll(z);
				break;
			case Mp : 
				tableauDeMouvementRobot.addAll(z);
				tableauDeMouvementRobot.addAll(Dp);
				tableauDeMouvementRobot.addAll(zp);
				tableauDeMouvementRobot.addAll(zp);
				tableauDeMouvementRobot.addAll(D);
				tableauDeMouvementRobot.addAll(z);
				break;		
			case E : 
				tableauDeMouvementRobot.addAll(D);
				tableauDeMouvementRobot.addAll(x);
				tableauDeMouvementRobot.addAll(x);
				tableauDeMouvementRobot.addAll(Dp);
				tableauDeMouvementRobot.addAll(x);
				tableauDeMouvementRobot.addAll(x);
				break;		
			case Ep : 
				tableauDeMouvementRobot.addAll(Dp);
				tableauDeMouvementRobot.addAll(x);
				tableauDeMouvementRobot.addAll(x);
				tableauDeMouvementRobot.addAll(D);
				tableauDeMouvementRobot.addAll(x);
				tableauDeMouvementRobot.addAll(x);
				break;
			case S : 
				tableauDeMouvementRobot.addAll(x);
				tableauDeMouvementRobot.addAll(D);
				tableauDeMouvementRobot.addAll(x);
				tableauDeMouvementRobot.addAll(x);
				tableauDeMouvementRobot.addAll(Dp);
				tableauDeMouvementRobot.addAll(x);
				break;		
			case Sp : 
				tableauDeMouvementRobot.addAll(x);
				tableauDeMouvementRobot.addAll(Dp);
				tableauDeMouvementRobot.addAll(x);
				tableauDeMouvementRobot.addAll(x);
				tableauDeMouvementRobot.addAll(D);
				tableauDeMouvementRobot.addAll(x);
				break;
		}
	}

}


