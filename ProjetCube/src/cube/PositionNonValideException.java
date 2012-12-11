/**
* Classe qui gere les exceptions relatives a l'entree d'une position qui n'est pas valide
*
* PositionNonValideException herite de CubeException
* 
* @author Groupe ECAO Rubik's Cube: Bienaime Bonnet Chataigner Fresquet.
* @see CubeException
*/

package cube;

public class PositionNonValideException extends CubeException
{
    /**
    * Construit une exception PositionNonValideException
    */
    public PositionNonValideException ()
    {
	    super();
    }
    
    /**
    * Construit une exception PositionNonValideException avec message
    *@param msg Le message à transmettre
    */
    public PositionNonValideException(String msg)
    {
	    super(msg);
    }
}

