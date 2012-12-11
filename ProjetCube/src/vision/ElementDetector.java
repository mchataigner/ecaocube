/**
 * @author Thibault COLLEONY
 */
package vision;

import javax.media.jai.PlanarImage;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import cube.Couleur;

/**
 * Classe de détection des éléments d'un Rubik's Cube.
 * @author thibault
 */
public class ElementDetector {

    /**
     * Extrait les zones correspondant aux éléments d'un Rubik's cube d'une image
     * représentant les contours du Rubik's cube.
     * @param input Image des contours du Rubik's Cube.
     * @param elementSize Taille de l'élément à détecter.
     * @return Liste de rectangles correspondant aux zones.
     */
    public static ArrayList<Rectangle2D> computeZones(PlanarImage input,
            int elementSize) {
        Rectangle candidate = null;
        int[][] data = new int[4][elementSize * elementSize];
        int mean = 0;
        ArrayList<Rectangle2D> result = new ArrayList<Rectangle2D>();

        for (int j = 0; j < input.getHeight() - elementSize; j += 5) {
            for (int i = 50; i < input.getWidth() - elementSize - 60; i += 5) {
                mean = 0;
                candidate = new Rectangle(i, j, elementSize, elementSize);
                // On divise le rectangle en 4
                // Taille trop importante sinon !!!??
                input.getData(candidate).getPixels(i, j, elementSize / 2,
                        elementSize / 2, data[0]);
                input.getData(candidate).getPixels(i + elementSize / 2, j,
                        elementSize / 2,
                        elementSize / 2, data[1]);
                input.getData(candidate).getPixels(i, j + elementSize / 2,
                        elementSize / 2,
                        elementSize / 2, data[2]);
                input.getData(candidate).getPixels(i + elementSize / 2,
                        j + elementSize / 2,
                        elementSize / 2,
                        elementSize / 2, data[3]);

                for (int k = 0; k < elementSize * elementSize; k++) {
                    for (int l = 0; l < 4; l++) {
                        mean += data[l][k];
                    }
                }

                mean /= elementSize * elementSize;
                if (mean < 20) {
                    result.add(new Rectangle2D.Float(i, j, elementSize,
                            elementSize));
                }
            }
        }
        result = removeRedundancy(result);
        if (result.size() == 8) {
            addSpecial(result);
        }
        sort(result);

        return result;
    }

    /**
     * Extrait les couleurs d'un Rubik's Cube
     * @param input Image originale du Rubik's Cube.
     * @param zones Zones correspondant aux éléments du Rubik's cube.
     * @return Liste de Couleur correspondant au éléments du Rubik's Cube.
     */
    public static ArrayList<Couleur> computeColors(PlanarImage input,
            ArrayList<Rectangle2D> zones) {
        ArrayList<Couleur> result = new ArrayList<Couleur>();
        Rectangle zone;
        int[][] data = null;
        int R = 0, G = 0, B = 0;
        for (int i = 0; i < zones.size(); i++) {

            // On Récupère les pixels dans l'image ...
            zone = new Rectangle((int) zones.get(i).getX(),
                    (int) zones.get(i).getY(),
                    (int) zones.get(i).getWidth(),
                    (int) zones.get(i).getHeight());

            // On Récupère les composantes R,G,B dans les pixels...
            for (int j = (int) zone.getX(); j < (int) zone.getX() + (int) zone.getWidth(); j++) {
                for (int k = (int) zone.getY(); k < (int) zone.getY() + (int) zone.getHeight(); k++) {
                    R += input.getData(zone).getSample(j, k, 0);
                    G += input.getData(zone).getSample(j, k, 1);
                    B += input.getData(zone).getSample(j, k, 2);
                }
            }
            // Et on moyenne tout ça.
            R = R / ((int) zone.getWidth() * (int) zone.getHeight());
            G = G / ((int) zone.getWidth() * (int) zone.getHeight());
            B = B / ((int) zone.getWidth() * (int) zone.getHeight());

            // Puis on demande à quoi ça correspond
			
            result.add(computeColor(R, G, B));

        // DEBUG
        System.out.println("R = "+R+", G = "+G+", B = "+B+" = "+computeColor(R,G,B));
        }

        return result;
    }

    // pour la face placée diféremment.
    public static ArrayList<Couleur> retournerCouleurs(ArrayList<Couleur> couleurs) {
        ArrayList<Couleur> result = new ArrayList<Couleur>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result.add(couleurs.get((2 - i) * 3 + j));
            }
        }
        return result;
    }

    private static ArrayList<Rectangle2D> removeRedundancy(ArrayList<Rectangle2D> elements) {
        ArrayList<Rectangle2D> result = new ArrayList<Rectangle2D>();
        int nRects = 0;
        boolean stillIntersects = false;

        while (!elements.isEmpty()) {
            result.add(elements.remove(0));
            for (int i = 0; i < elements.size(); i++) {
                if (result.get(nRects).intersects(elements.get(i))) {
                    result.set(nRects, result.get(nRects).createIntersection(elements.remove(i)));
                    stillIntersects = true;
                }
            }
            nRects++;
        }
        if (stillIntersects) {
            result = removeRedundancy(result);
        }
        return result;
    }

    private static void addSpecial(ArrayList<Rectangle2D> elements) {
        int x = 0;
        int y = 0;
        int width = 0;
        int height = 0;

        for (int i = 0; i < 8; i++) {
            x += elements.get(i).getX();
            y += elements.get(i).getY();
            width += elements.get(i).getWidth();
            height += elements.get(i).getHeight();
        }
        x /= 8;
        y /= 8;
        width /= 8;
        height /= 8;

        elements.add(4, new Rectangle2D.Float(x, y, width, height));
    }

    private static void sort(ArrayList<Rectangle2D> elements) {
        boolean changed = true;
        while (changed) {
            changed = false;
            for (int i = 0; i < 2; i++) {
                if (elements.get(i).getX() > elements.get(i + 1).getX()) {
                    elements.add(i, elements.remove(i + 1));
                    changed = true;
                }
                if (elements.get(i + 3).getX() > elements.get(i + 4).getX()) {
                    elements.add(i + 3, elements.remove(i + 4));
                    changed = true;
                }
                if (elements.get(i + 6).getX() > elements.get(i + 7).getX()) {
                    elements.add(i + 6, elements.remove(i + 7));
                    changed = true;
                }
            }
        }
    }

    private static Couleur computeColor(int R, int G, int B) {
		
		
        if (R > 212 && R < 257 && G > 209 && G < 257 && B > 92 && B < 125) {
            return Couleur.JAUNE;
        }
        if (R > 191 && R < 257 && G > 131 && G < 204 && B > 119 && B < 169) {
            return Couleur.ORANGE;
        }
        if (R > 195 && R < 257 && G > 106 && G < 160 && B > 160 && B < 206) {
            return Couleur.ROUGE;
        }
        if (R > 108 && R < 166 && G > 187 && G < 257 && B > 134 && B < 171) {
            return Couleur.VERT;
        }
        if (R > 57 && R < 95 && G > 113 && G < 156 && B > 232 && B < 257) {
            return Couleur.BLEU;
        }
        if (R > 171 && R < 257 && G > 171 && G < 257 && B > 171 && B < 257) {
            return Couleur.BLANC;
        }

        return Couleur.BLANC;
    }
}
