/**
 *classe qui gère les mouvements du robot
 *@author Groupe ECAO Rubik's Cube : Pierre_Bienaime Bastien_Bonnet Mathieu_Chataigner Mathieu Fresquet.
 */

package acquisition;

import cube.*;
import cube.resolution.*;
import vision.*;
import java.util.Scanner;

public class FausseDetection implements Detection
{
	private Algorithme algoDeMelange;
	private Cube leCube;

	/* DEPRECATED
	public FausseDetection()
	{	
		System.out.println("Saisissez votre algorithme de mélange, chaque mouvement étant séparé par un espace: ");
		Scanner sca = new Scanner(System.in);
		String algoSaisi = sca.nextLine();
		this.algoDeMelange=new Algorithme(algoSaisi);
	}
	*/

    /**
    * Constructeur qui initialise les mouvements qui vont permettre le mélange du cube
    *@param algorithmeDeMelange algorithme contenant les mouvements pour le melange
    */
	public FausseDetection(Algorithme algorithmeDeMelange)
	{    
		this.algoDeMelange=algorithmeDeMelange.copier();
	}	
		
    /**
    * Effectue l'algorithme de mélange sur le cube
    *@param faceU face supérieur
    *@param faceD face inférieur
    *@param faceR face de droite
    *@param faceL face de gauche
    *@param faceF face en face
    *@param faceB face de derrière
    *@return Cube 
    */
	public Cube detecter()throws CubeException
	{	
		Cube leCube = new Cube();
		algoDeMelange.executerSurCube(leCube);
		return leCube;
	}
}
