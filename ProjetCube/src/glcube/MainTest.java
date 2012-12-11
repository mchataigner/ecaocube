
/**
* Main qui permet d'initialiser un cube en openGL
* @TRAOUROUDER PARES
*/
package glcube;

import cube.Cube;
import cube.Face;
import cube.Couleur;
import cube.MouvementElementaire;

import java.util.Scanner;


/* Ceci est un main de test à l'attention des branquignols de l'ECAO Rubik's Cube */

public class MainTest
{



    public static void main(String[] arg) throws Throwable
    {
        Face faceU = new Face(Couleur.JAUNE, Couleur.JAUNE, Couleur.JAUNE, Couleur.JAUNE, Couleur.JAUNE, Couleur.JAUNE, Couleur.JAUNE, Couleur.JAUNE, Couleur.JAUNE);
        Face faceD = new Face(Couleur.BLANC, Couleur.BLANC, Couleur.BLANC, Couleur.BLANC, Couleur.BLANC, Couleur.BLANC, Couleur.BLANC, Couleur.BLANC, Couleur.BLANC);
        Face faceF = new Face(Couleur.ORANGE, Couleur.ORANGE, Couleur.ORANGE, Couleur.ORANGE, Couleur.ORANGE, Couleur.ORANGE, Couleur.ORANGE, Couleur.ORANGE, Couleur.ORANGE);
        Face faceB = new Face(Couleur.ROUGE, Couleur.ROUGE, Couleur.ROUGE, Couleur.ROUGE, Couleur.ROUGE, Couleur.ROUGE, Couleur.ROUGE, Couleur.ROUGE, Couleur.ROUGE);
        Face faceL = new Face(Couleur.VERT, Couleur.VERT, Couleur.VERT, Couleur.VERT, Couleur.VERT, Couleur.VERT, Couleur.VERT, Couleur.VERT, Couleur.VERT);
        Face faceR = new Face(Couleur.BLEU, Couleur.BLEU, Couleur.BLEU, Couleur.BLEU, Couleur.BLEU, Couleur.BLEU, Couleur.BLEU, Couleur.BLEU, Couleur.BLEU);

        Cube cube = new Cube(faceU, faceD, faceR, faceL, faceF, faceB); /* On crée un bête cube de test */
        
        GLCube.runGL(cube, true); /* On lance l'affichage OpenGL */
        
        Scanner sc = new Scanner(System.in);
        String cmd;

        while (sc.hasNext()) {
            cmd = sc.nextLine(); /* On récupère un mouvement depuis l'entrée stantard */

            try {
                MouvementElementaire mvt = MouvementElementaire.valueOf(cmd);
                GLCube.playMovement(mvt); /* On lance l'animation du mouvement */
                GLCube.waitForMovementEnd(); /* On met le thread courant (ici le thread main) en pause tant que le mouvement n'est pas fini */
                cube.effectuerMouvementElementaire(mvt); /* Le mouvement n'a qu'été "joué", il faut donc l'appliquer "physiquement"
                                                            au modèle interne APRES que le mouvement ait été joué */
            } catch (Exception e) {
                System.err.println("Le mouvement " + cmd + " n'est pas autorisé.\nC'est con, hein ?");
            }
        }
        
        System.exit(0); /* Quitte la fenêtre OpenGL */
    }
}

