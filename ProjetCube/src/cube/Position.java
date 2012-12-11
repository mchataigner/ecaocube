/**
* Classe qui définit ce qu'est une Position pour un Rubiks Cube.
* 
* La classe Position permet de déterminer la position dans l'espace des cubies qui composent le Rubiks Cube.
*
* @author Groupe ECAO Rubik's Cube: Bienaime Bonnet Chataigner Fresquet.
*/

package cube;

public class Position
{	

    /**
    *Attributs
    */

    private int x;
    private int y;
    private int z;


    /**
    * le constructeur de Position a 3 parametres qui sont des entiers compris entre 1 et 3
    * @param _x la coordonnee x de la position
    * @param _y la coordonne y de la position
    * @param _z la coordonne z de la position 
    * @throws PositionNonValideException
    */	
    public Position(int _x, int _y, int _z) throws PositionNonValideException
    {
	    if(_x<=3 && _x>=1 && _y<=3 && _y>=1 && _z<=3 && _z>=1)
	    {
		    this.x=_x;
		    this.y=_y;
		    this.z=_z;
	    }
	    else
	    {
		    throw new PositionNonValideException("Les entiers de la position doivent etre compris entre 1 et 3");
	    }
    }

    /**
    * Methodes
    */

    /**
    * Permet d'obtenir la coordonnee x d'une Position
    * @return un entier compris entre 1 et 3
    */
    public int obtenirX()
    {
	    return this.x;
    }

    /**
    * Permet d'obtenir la coordonnee y d'une Position
    * @return un entier compris entre 1 et 3
    */
    public int obtenirY()
    {
	    return this.y;
    }

    /**
    * Permet d'obtenir la coordonnee z d'une Position
    * @return un entier compris entre 1 et 3
    */
    public int obtenirZ()
    {
	    return this.z;
    }

    /**
    * Redéfinition de la méthode equals héritée de la classe mère Object
    *@return un booleen indiquant true si les positions sont les mêmes 
    */
    public boolean equals(Position p)
    {
        return x==p.obtenirX()&&y==p.obtenirY()&&z==p.obtenirZ();
    }

    /**
    * Permet d'obtenir la chaine de caractere qui decrit la position
    * @return un String qui correspond a la position, dans l'ordre: x, y, z.
    */
    public String toString()
    {
	    String positionEnTexte=((Integer)x).toString()+" "+((Integer)y).toString()+" "+((Integer)z).toString();
	    return positionEnTexte;
    } 
}


