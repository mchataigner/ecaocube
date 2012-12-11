/**
 * Classe qui determine ce qu'est une Detection.
 *
 * @author Groupe Vision ECAO Rubik's Cube: Soula Wiart.
 */

package acquisition;

import cube.*;
import java.io.*;

public class DetectionVision2 implements Detection
{

	private String nomFichier ;
	private BufferedReader bf; 

	private int erContour,erReflet;
	private String[] ligneFace ; 
	private int[][] matriceCarre ;

	private Face[] faces ; 
	private Couleur[] couleurs ;

	private Couleur choixCouleur (String i){
		Couleur couleur=null;
		if(i.equals("1")){couleur=Couleur.BLANC;}
		else if(i.equals("2")){couleur=Couleur.ROUGE;}
		else if(i.equals("3")){couleur=Couleur.JAUNE;}
		else if(i.equals("4")){couleur=Couleur.BLEU;}
		else if(i.equals("5")){couleur=Couleur.ORANGE;}
		else if(i.equals("6")){couleur=Couleur.VERT;}
		return couleur;
	}

	public DetectionVision2(){
		this.nomFichier = "test.txt";
		ligneFace = new String[6];
		matriceCarre = new int[6][9];
		faces = new Face[6];
		couleurs = new Couleur[9];
		try {
			bf=new BufferedReader(new FileReader(this.nomFichier));
		}catch (FileNotFoundException e){e.printStackTrace();}
	}

	public DetectionVision2(String _nomFichier){
		this.nomFichier = _nomFichier ;
		ligneFace = new String[6];
		matriceCarre = new int[6][9];
		faces = new Face[6];
		couleurs = new Couleur[9];
		try {
			bf=new BufferedReader(new FileReader(this.nomFichier));
		}catch (FileNotFoundException e){e.printStackTrace();}
	}

	/**
	* Methode permettant de creer un Cube a partir de 6 images.
	*@return Un objet rubik's Cube
	*/
	public Cube detecter(Face faceU, Face faceD, Face faceR, Face faceL, Face faceF, Face faceB) throws CubeException{
		return new Cube(faceU, faceD, faceR, faceL, faceF, faceB);
	}
	
	public Cube detecter()throws CubeException{
		int i,j;
		Cube cube = null;

		try{

			erContour=Integer.parseInt(bf.readLine());
			erReflet=Integer.parseInt(bf.readLine());

			if(erContour==1) {
				System.out.println("Warning : Les images acquises du rubic's cube ont des reflets\nAucun résultat n'a pu être produit");
				//bf.close();
				// throw new InitialisationVision2Exception();
			}
			else

				if(erReflet==1) 
					System.out.println("Warning : Les images acquises du rubic's cube ont des reflets\nLe résultat proposé est peut être faux\nNous vous conseillons de refaire une acquisition en supprimant les reflets eventuels sur le rubic's cube");
				for(i=0;i<6;i++){
					ligneFace[i]=bf.readLine();
				}

		}catch (IOException e) {e.printStackTrace();}

		if(erContour==0){

			String [][]matriceTest=new String[6][9];
	
			for(i=0;i<6;i++){
				matriceTest[i]=ligneFace[i].split("\t");
			}
	
			for(i=0;i<6;i++){
				for(j=0;j<9;j++){
					couleurs[j]=choixCouleur(matriceTest[i][j]);
				}
				faces[i]=new Face(couleurs[0],couleurs[1],couleurs[2],couleurs[3],couleurs[4],couleurs[5],couleurs[6],couleurs[7],couleurs[8]); 
			}
			cube = new Cube(faces[0],faces[2],faces[3],faces[5],faces[1],faces[4]);
		}

		try{
		bf.close();
		}catch (IOException e) {e.printStackTrace();}

		return cube;
	}

}
