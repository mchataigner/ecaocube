/**
 * @author Thibault COLLEONY
 */
package vision;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.KernelJAI;
import java.awt.image.renderable.ParameterBlock;

import javax.media.jai.operator.*;

import cube.Couleur;

/**
 * Classe de détection de contours d'une image.
 * @author thibault
 */
public class EdgeDetector {

    private KernelJAI kern_h = null;
    private KernelJAI kern_v = null;

    /**
     * Créée un EdgeDetector utilisant l'opérateur spécifié (TODO : ajouter
     * autres méthodes que Chen)
     * @param method Opérateur à utiliser
     */
    public EdgeDetector(String method) {
        if (method.toLowerCase().equals("chen")) {
            // Creation des kernels
            float chen_h[] = new float[]{1.0F, 0.0F, -1.0F,
                1.414F, 0.0F, -1.414F,
                1.0F, 0.0F, -1.0F};
            float chen_v[] = new float[]{-1.0F, -1.414F, -1.0F,
                0.0F, 0.0F, 0.0F,
                1.0F, 1.414F, 1.0F};

            kern_h = new KernelJAI(3, 3, chen_h);
            kern_v = new KernelJAI(3, 3, chen_v);
        }
    }

    /**
     * Calcule les contours d'une image.
     * @param input L'image en entrée de l'opération.
     * @return L'image en sortie de l'opération.
     */
    public PlanarImage compute(PlanarImage input) {
        PlanarImage result = null;
        ParameterBlock pb = new ParameterBlock();

        pb.addSource(smoother(input, 7));
        pb.add(kern_h);
        pb.add(kern_v);

        result = (PlanarImage) JAI.create("gradientmagnitude", pb);

        return result;
    }

    /**
     * Filtrage médian pour flouter l'image et réduire le bruit.
     * @param input L'image en entrée de l'opération.
     * @param times Le nombre de fois où est appliqué le filtrage médian.
     * @return L'image en sortie de l'opération.
     */
    public PlanarImage smoother(PlanarImage input, int times) {
        PlanarImage result = null;
		/*ParameterBlock pb = new ParameterBlock();
		pb.addSource(input);
		pb.add(MedianFilterDescriptor.MEDIAN_MASK_SQUARE);
		pb.add(5);*/
        result = (PlanarImage) JAI.create("medianfilter", input);
        for (int i = 0; i < times - 1; i++) {
            result = (PlanarImage) JAI.create("medianfilter", result);
        }

        return result;
    }
}
