/**
* Classe qui supervise toutes les exceptions qui peuvent survenir dans lors de la resolution d'un Rubiks Cube.
* 
* CubeException herite de java.lang.Exception;
*
* @author Groupe ECAO Rubik's Cube: Bienaime Bonnet Chataigner Fresquet.
* @see java.lang.Exception
*/

package cube;

public class CubeException extends java.lang.Exception
{
    /**
    * Construit une exception CubeException
    */
    public CubeException()
    {
	    super();
    }

    /**
    * Construit une exception CubeException avec message
    *@param msg Le message à transmettre
    */
    public CubeException(String msg)
    {
	    super(msg);
    }
}



