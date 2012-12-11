/**
 * Interface qui determine ce qu'est une Detection.
 *
 * @author Groupe ECAO Rubik's Cube: Bienaime Bonnet Chataigner Fresquet.
 */

package acquisition;

import cube.*;


public interface Detection
{
    /**
    * Methode permettant de creer un Cube a partir de 6 images.
    *@return Un objet rubik's Cube
    */
    //public Cube detecter(Face faceU, Face faceD, Face faceR, Face faceL, Face faceF, Face faceB) throws CubeException;	
    public Cube detecter() throws CubeException;	
}
