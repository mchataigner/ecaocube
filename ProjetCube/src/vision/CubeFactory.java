/**
 * @author Thibault COLLEONY
 */
package vision;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.TiledImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.renderable.ParameterBlock;

import cube.Cube;
import cube.Face;
import cube.CubeException;

import com.sun.media.jai.widget.DisplayJAI;

/**
 * Classe créatrice de Rubik's cube.
 * @author thibault
 */
public class CubeFactory {

    private PlanarImage[] imagesFaces = null;
    private PlanarImage[] edgesFaces = null;
    private EdgeDetector edge = null;
    private ArrayList<ArrayList<Rectangle2D>> zones = null;
    private int windowSize = 0;
    private boolean initialise = false;

    /**
     * Créée une usine de cube avec une taille de fenêtre par défaut.
     */
    public CubeFactory() {
        windowSize = 35;
    }

    /**
     * Créée une usine de cube avec une fenêtre de taille définie.
     * @param _windowSize
     */
    public CubeFactory(int _windowSize) {
        windowSize = _windowSize;
    }

    /**
     * Créée un Cube à partir de 6 fichiers représentant les faces.
     * L'ordre des faces est le suivant : F, L, B, R, D, U.
     * @param filenames Tableau de String contenant les noms des fichiers
     * images.
     * @return Le Cube créé.
     */
    public Cube fromImages(String[] filenames) {
        imagesFaces = new PlanarImage[6];
        edgesFaces = new PlanarImage[6];
        edge = new EdgeDetector("Chen");
        zones = new ArrayList<ArrayList<Rectangle2D>>();
        Cube result = null;
        Face faceU, faceD, faceR, faceL, faceF, faceB;

        for (int i = 0; i < 6; i++) {
            ParameterBlock params = new ParameterBlock();
            params.addSource(JAI.create("fileload", filenames[i]));
            params.add(0.5D);
            params.add(0.5D);
            params.add(0.0F);
            params.add(0.0F);
            imagesFaces[i] = (PlanarImage) JAI.create("SubsampleAverage",
                    params, null);

            edgesFaces[i] = edge.compute(imagesFaces[i]);
            zones.add(ElementDetector.computeZones(edgesFaces[i], windowSize));
        }
        faceF = new Face(ElementDetector.computeColors(imagesFaces[0], zones.get(0)));
        faceL = new Face(ElementDetector.computeColors(imagesFaces[1], zones.get(1)));
        faceB = new Face(ElementDetector.computeColors(imagesFaces[2], zones.get(2)));
        faceR = new Face(ElementDetector.computeColors(imagesFaces[3], zones.get(3)));
        faceD = new Face(ElementDetector.computeColors(imagesFaces[4], zones.get(4)));
        faceU = new Face(ElementDetector.retournerCouleurs(ElementDetector.computeColors(
                imagesFaces[5], zones.get(5))));

        try {
            result = new Cube(faceU, faceD, faceR, faceL, faceF, faceB);
        } catch (CubeException e) {
            System.out.println("Exception lors de la creation du cube :" + e.getMessage());
        }
        initialise = true;

        return result;
    }

    /**
     * Affiche le résultat du traitement des images sous forme graphique.
     */
    public void displayComputedData() {
        if (initialise) {
            JFrame frame = new JFrame();
            GridLayout layout = new GridLayout(3, 4);
            ArrayList<Graphics2D> buffers = new ArrayList<Graphics2D>();

            frame.getContentPane().setLayout(layout);

            for (int i = 0; i < 6; i++) {
                // Construction d'un affichable (original)
                DisplayJAI face = new DisplayJAI(imagesFaces[i]);

                buffers.add(new TiledImage(edgesFaces[i], true).createGraphics());
                for (int j = 0; j < zones.get(i).size(); j++) {
                    buffers.get(i).draw(zones.get(i).get(j));
                    buffers.get(i).drawString(
                            (String) ElementDetector.computeColors(imagesFaces[i],
                            zones.get(i)).get(j).toString(),
                            (float) zones.get(i).get(j).getX(),
                            (float) zones.get(i).get(j).getY());
                }

                // construction d'un affichage (contours)
                DisplayJAI edge = new DisplayJAI(edgesFaces[i]);

                // Ajout dans le layout
                frame.getContentPane().add(face);
                frame.getContentPane().add(edge);
            }

            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        }
    }

    public int getWindowSize() {
        return windowSize;
    }
}
