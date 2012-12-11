/**
 * Interface qui determine si une classe est une resolution du Rubiks cube.
 *
 * @author Groupe ECAO Rubik's Cube: Bienaime Bonnet Chataigner Fresquet.
 */

package cube.resolution;

import cube.*;


public interface ResolutionDuCube
{
    /**
    * Retourne la solution qui résout le Cube sous forme d'Algorithme
    *@return L'Algorithme contenant la solution
    */
    public Algorithme trouverSolution() throws CubeException,ResolutionException;	
}
