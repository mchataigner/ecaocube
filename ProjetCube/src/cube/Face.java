/**
* Classe qui definit ce qu'est une Face pour un Rubiks Cube.
* 
* Un Rubiks Cube possede 6 faces.
* Une Face possède 9 cases.
*
* @author Groupe ECAO Rubik's Cube: Bienaime Bonnet Chataigner Fresquet.
*/

package cube;

import java.util.ArrayList;

public class Face
{	


    /**
    * Attributs
    */
    private Couleur[] couleursDeLaFace;



    /**
    * le constructeur de Face a 9 parametres qui sont les couleurs des 9 cases de cette face.
    * @param couleur1 la couleur de la premiere case, c'est a dire de la ligne 1 et de la colonne 1.
    * @param couleur2 la couleur de la deuxieme case, c'est a dire de la ligne 1 et de la colonne 2.
    * @param couleur3 la couleur de la troisieme case, c'est a dire de la ligne 1 et de la colonne 3.
    * @param couleur4 la couleur de la quatrieme case, c'est a dire de la ligne 2 et de la colonne 1.
    * @param couleur5 la couleur de la cinquieme case, c'est a dire de la ligne 2 et de la colonne 2.
    * @param couleur6 la couleur de la sixieme case, c'est a dire de la ligne 2 et de la colonne 3.
    * @param couleur7 la couleur de la septieme case, c'est a dire de la ligne 3 et de la colonne 1.
    * @param couleur8 la couleur de la huitieme case, c'est a dire de la ligne 3 et de la colonne 2.
    * @param couleur9 la couleur de la neuvieme case, c'est a dire de la ligne 3 et de la colonne 3.
    */	

    public Face(Couleur couleur1, Couleur couleur2, Couleur couleur3, Couleur couleur4, Couleur couleur5, Couleur couleur6, Couleur couleur7, Couleur couleur8, Couleur couleur9)
    {
        this.couleursDeLaFace=new Couleur[9];
	    this.couleursDeLaFace[0] = couleur1;
	    this.couleursDeLaFace[1] = couleur2;
	    this.couleursDeLaFace[2] = couleur3;
	    this.couleursDeLaFace[3] = couleur4;
	    this.couleursDeLaFace[4] = couleur5;
	    this.couleursDeLaFace[5] = couleur6;
	    this.couleursDeLaFace[6] = couleur7;
	    this.couleursDeLaFace[7] = couleur8;
	    this.couleursDeLaFace[8] = couleur9;		
    }

	public Face(ArrayList<cube.Couleur> arraylist)
	{
		this.couleursDeLaFace=new Couleur[9];
	    for (int i=0;i<=8;i++){
			this.couleursDeLaFace[i]=arraylist.get(i);
		}
	}

    /**
    * Methodes
    */

    /**
    * Permet d'obtenir la couleur d'une case d'une face, a partir d'un indice compris entre 1 et 9
    * @return la couleur de la case
    */
    public Couleur obtenirCouleur(int indiceCouleur)
    {
	    return this.couleursDeLaFace[indiceCouleur-1];
    }

    /**
    * Permet d'obtenir un tableau contenant toutes les couleurs des cases de la face.
    * @return la couleur de la case
    */
    public Couleur[] obtenirCouleurs()
    {
	    return this.couleursDeLaFace;
    }

    /**
    * Redéfinition de la méthode toString() héritée de la classe mère Object
    *@return une String contenant les informatons associées à une face
    */
    public String toString()
    {
        StringBuilder str=new StringBuilder();

        for(int i=0;i<9;i++)
            {
	            str.append(this.couleursDeLaFace[i]);
	            str.append(" ");
	            if((i+1)%3==0&&(i+1)%9!=0)
	                {
		                str.append("\n");
	                }
            }
        String str2=str.toString();
        String tiret="----------------------------";
        str2=str2.replaceAll(" "," | ");
        str2=str2.replaceFirst("|","| ");
        str2=str2.replaceAll("\n","\n| ");

        str2=str2.replaceAll("VERT"," VERT ");
        str2=str2.replaceAll("JAUNE","JAUNE ");
        str2=str2.replaceAll("BLANC","BLANC ");
        str2=str2.replaceAll("BLEU"," BLEU ");
        str2=str2.replaceAll("ROUGE","ROUGE ");
        str2=str2.replaceAll(" \n","\n");

        String str3=tiret.concat("\n".concat(str2.concat("\n".concat(tiret))));
        str3=str3.replaceAll(" \n","\n");
        return str3;
    }
}



