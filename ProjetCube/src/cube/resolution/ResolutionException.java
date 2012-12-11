package cube.resolution;

/**
* Classe qui supervise toutes les exceptions qui peuvent survenir dans lors de l'exécution des méthodes de la resolution d'un Rubiks Cube.
* 
* ResolutionException herite de java.lang.Exception;
*
* @author Groupe ECAO Rubik's Cube: Bonnet Chataigner.
* @see java.lang.Exception
*/
public class ResolutionException extends java.lang.Exception
{
    /**
    * Construit une exception ResolutionException
    */
    public ResolutionException()
    {
	    super();
    }

    /**
    * Construit une exception ResolutionException avec message
    *@param msg Le message à transmettre
    */
    public ResolutionException(String msg)
    {
	    super(msg);
    }
}
