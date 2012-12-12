


package glcube;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLAutoDrawable;
import com.sun.opengl.util.Animator;

import javax.swing.JFrame;
import java.awt.event.*;

import java.util.ArrayList;

import cube.Cube;
import cube.Face;
import cube.Couleur;
import cube.MouvementElementaire;


public class GLCube implements GLEventListener, MouseMotionListener, MouseListener {

    private static GLU glu = new GLU();
    private static GLCanvas canvas = new GLCanvas();
    private static Animator animator = new Animator(canvas);
    private static JFrame frame = new JFrame("CubeGL");
    private float cameraPhi = -30f;
    private float cameraTheta = 30f;
    private int oldX = 0, oldY = 0;
    private boolean cameraMoving = false;
    private static boolean cubeMoving = false;
    private float step = 0.25f;
    private float stepFactor = 90;
    private static float rotate = 0.0f;
    private float cubieSize = 0.95f;
    private static Cube cube;
    private static MouvementElementaire mvt = MouvementElementaire.F;
    private long oldNanos = 0, currentNanos;
    private int n = 10;
    private double fps;

  

    private void setColor(GL gl, Couleur c) {
        float r, g, b;

        switch (c) {
            case BLANC:
                r = 1.0f;
                g = 1.0f;
                b = 255;
                break;

            case ORANGE:
                r = 1.0f;
                g = 0.4f;
                b = 0;
                break;

            case BLEU:
                r = 0;
                g = 0;
                b = 1.0f;
                break;

            case ROUGE:
                r = 1.0f;
                g = 0;
                b = 0;
                break;

            case VERT:
                r = 0;
                g = 0.8f;
                b = 0;
                break;

            case JAUNE:
                r = 1.0f;
                g = 1.0f;
                b = 0;
                break;

            default:
                r = 0;
                g = 0;
                b = 0;
                break;
        }

        gl.glColor3f(r, g, b);
    }



    private void drawCuboid(GL gl, float width, float height, float depth, Couleur cFront, Couleur cBack, Couleur cUp, Couleur cDown, Couleur cRight, Couleur cLeft) {
        gl.glPushMatrix();

        gl.glTranslatef((1 - width) / 2, (1 - height) / 2, (1 - depth) / 2);

        gl.glBegin(GL.GL_QUADS);

        // Front
        setColor(gl, cFront);

        gl.glVertex3f(0.0f, height, depth);
        gl.glVertex3f(width, height, depth);
        gl.glVertex3f(width, 0.0f, depth);
        gl.glVertex3f(0.0f, 0.0f, depth);

        // Back
        setColor(gl, cBack);

        gl.glVertex3f(width, height, 0.0f);
        gl.glVertex3f(0.0f, height, 0.0f);
        gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glVertex3f(width, 0.0f, 0.0f);

        // Up
        setColor(gl, cUp);

        gl.glVertex3f(0.0f, height, 0.0f);
        gl.glVertex3f(width, height, 0.0f);
        gl.glVertex3f(width, height, depth);
        gl.glVertex3f(0.0f, height, depth);

        // Down
        setColor(gl, cDown);

        gl.glVertex3f(0.0f, 0.0f, depth);
        gl.glVertex3f(width, 0.0f, depth);
        gl.glVertex3f(width, 0.0f, 0.0f);
        gl.glVertex3f(0.0f, 0.0f, 0.0f);

        // Right
        setColor(gl, cRight);

        gl.glVertex3f(width, height, depth);
        gl.glVertex3f(width, height, 0.0f);
        gl.glVertex3f(width, 0.0f, 0.0f);
        gl.glVertex3f(width, 0.0f, depth);

        // Left
        setColor(gl, cLeft);

        gl.glVertex3f(0.0f, height, 0.0f);
        gl.glVertex3f(0.0f, height, depth);
        gl.glVertex3f(0.0f, 0.0f, depth);
        gl.glVertex3f(0.0f, 0.0f, 0.0f);

        gl.glEnd();

        gl.glPopMatrix();
    }



    private void drawCubeRightToLeft(GL gl) {
        ArrayList faces = (ArrayList) cube.toFace();

        gl.glTranslatef(0.5f, 0.5f, 0.5f);

        if (cubeMoving && (mvt.equals(MouvementElementaire.Rp) || mvt.equals(MouvementElementaire.rp))) {
            gl.glRotatef(rotate, 1.0f, 0, 0);
        }

        if (cubeMoving && (mvt.equals(MouvementElementaire.R) || mvt.equals(MouvementElementaire.r))) {
            gl.glRotatef(-rotate, 1.0f, 0, 0);
        }

        if (cubeMoving && (mvt.equals(MouvementElementaire.R2) || mvt.equals(MouvementElementaire.r2))) {
            gl.glRotatef(2*rotate, 1.0f, 0, 0);
        }

        gl.glTranslatef(-0.5f, -0.5f, -0.5f);

        gl.glTranslatef(1.0f, 0, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(2)).obtenirCouleur(5), Couleur.AUCUNE);

        gl.glTranslatef(0, 1.0f, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(0)).obtenirCouleur(6), Couleur.AUCUNE, ((Face) faces.get(2)).obtenirCouleur(2), Couleur.AUCUNE);

        gl.glTranslatef(0, 0, -1.0f);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, ((Face) faces.get(5)).obtenirCouleur(1), ((Face) faces.get(0)).obtenirCouleur(3), Couleur.AUCUNE, ((Face) faces.get(2)).obtenirCouleur(3), Couleur.AUCUNE);

        gl.glTranslatef(0, -1.0f, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, ((Face) faces.get(5)).obtenirCouleur(4), Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(2)).obtenirCouleur(6), Couleur.AUCUNE);

        gl.glTranslatef(0, -1.0f, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, ((Face) faces.get(5)).obtenirCouleur(7), Couleur.AUCUNE, ((Face) faces.get(1)).obtenirCouleur(9), ((Face) faces.get(2)).obtenirCouleur(9), Couleur.AUCUNE);

        gl.glTranslatef(0, 0, 1.0f);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(1)).obtenirCouleur(6), ((Face) faces.get(2)).obtenirCouleur(8), Couleur.AUCUNE);

        gl.glTranslatef(0, 0, 1.0f);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, ((Face) faces.get(4)).obtenirCouleur(9), Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(1)).obtenirCouleur(3), ((Face) faces.get(2)).obtenirCouleur(7), Couleur.AUCUNE);

        gl.glTranslatef(0, 1.0f, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, ((Face) faces.get(4)).obtenirCouleur(6), Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(2)).obtenirCouleur(4), Couleur.AUCUNE);

        gl.glTranslatef(0, 1.0f, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, ((Face) faces.get(4)).obtenirCouleur(3), Couleur.AUCUNE, ((Face) faces.get(0)).obtenirCouleur(9), Couleur.AUCUNE, ((Face) faces.get(2)).obtenirCouleur(1), Couleur.AUCUNE);

        gl.glPopMatrix();
        gl.glPushMatrix();

        gl.glTranslatef(0.5f, 0.5f, 0.5f);

        if (cubeMoving && (mvt.equals(MouvementElementaire.l) || mvt.equals(MouvementElementaire.rp) || mvt.equals(MouvementElementaire.M))) {
            gl.glRotatef(rotate, 1.0f, 0, 0);
        }

        if (cubeMoving && (mvt.equals(MouvementElementaire.lp) || mvt.equals(MouvementElementaire.r) || mvt.equals(MouvementElementaire.Mp))) {
            gl.glRotatef(-rotate, 1.0f, 0, 0);
        }

        if (cubeMoving && (mvt.equals(MouvementElementaire.l2) || mvt.equals(MouvementElementaire.r2) || mvt.equals(MouvementElementaire.M2))) {
            gl.glRotatef(2*rotate, 1.0f, 0, 0);
        }

        gl.glTranslatef(-0.5f, -0.5f, -0.5f);

        gl.glTranslatef(0, 1.0f, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(0)).obtenirCouleur(5), Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE);

        gl.glTranslatef(0, 0, -1.0f);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, ((Face) faces.get(5)).obtenirCouleur(2), ((Face) faces.get(0)).obtenirCouleur(2), Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE);

        gl.glTranslatef(0, -1.0f, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, ((Face) faces.get(5)).obtenirCouleur(5), Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE);

        gl.glTranslatef(0, -1.0f, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, ((Face) faces.get(5)).obtenirCouleur(8), Couleur.AUCUNE, ((Face) faces.get(1)).obtenirCouleur(8), Couleur.AUCUNE, Couleur.AUCUNE);

        gl.glTranslatef(0, 0, 1.0f);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(1)).obtenirCouleur(5), Couleur.AUCUNE, Couleur.AUCUNE);

        gl.glTranslatef(0, 0, 1.0f);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, ((Face) faces.get(4)).obtenirCouleur(8), Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(1)).obtenirCouleur(2), Couleur.AUCUNE, Couleur.AUCUNE);

        gl.glTranslatef(0, 1.0f, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, ((Face) faces.get(4)).obtenirCouleur(5), Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE);

        gl.glTranslatef(0, 1.0f, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, ((Face) faces.get(4)).obtenirCouleur(2), Couleur.AUCUNE, ((Face) faces.get(0)).obtenirCouleur(8), Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE);

        gl.glPopMatrix();

        gl.glTranslatef(0.5f, 0.5f, 0.5f);

        if (cubeMoving && (mvt.equals(MouvementElementaire.l) || mvt.equals(MouvementElementaire.L))) {
            gl.glRotatef(rotate, 1.0f, 0, 0);
        }

        if (cubeMoving && (mvt.equals(MouvementElementaire.lp) || mvt.equals(MouvementElementaire.Lp))) {
            gl.glRotatef(-rotate, 1.0f, 0, 0);
        }

        if (cubeMoving && (mvt.equals(MouvementElementaire.l2) || mvt.equals(MouvementElementaire.L2))) {
            gl.glRotatef(2*rotate, 1.0f, 0, 0);
        }

        gl.glTranslatef(-0.5f, -0.5f, -0.5f);

        gl.glTranslatef(-1.0f, 0, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(3)).obtenirCouleur(5));

        gl.glTranslatef(0, 1.0f, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(0)).obtenirCouleur(4), Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(3)).obtenirCouleur(2));

        gl.glTranslatef(0, 0, -1.0f);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, ((Face) faces.get(5)).obtenirCouleur(3), ((Face) faces.get(0)).obtenirCouleur(1), Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(3)).obtenirCouleur(1));

        gl.glTranslatef(0, -1.0f, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, ((Face) faces.get(5)).obtenirCouleur(6), Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(3)).obtenirCouleur(4));

        gl.glTranslatef(0, -1.0f, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, ((Face) faces.get(5)).obtenirCouleur(9), Couleur.AUCUNE, ((Face) faces.get(1)).obtenirCouleur(7), Couleur.AUCUNE, ((Face) faces.get(3)).obtenirCouleur(7));

        gl.glTranslatef(0, 0, 1.0f);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(1)).obtenirCouleur(4), Couleur.AUCUNE, ((Face) faces.get(3)).obtenirCouleur(8));

        gl.glTranslatef(0, 0, 1.0f);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, ((Face) faces.get(4)).obtenirCouleur(7), Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(1)).obtenirCouleur(1), Couleur.AUCUNE, ((Face) faces.get(3)).obtenirCouleur(9));

        gl.glTranslatef(0, 1.0f, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, ((Face) faces.get(4)).obtenirCouleur(4), Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(3)).obtenirCouleur(6));

        gl.glTranslatef(0, 1.0f, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, ((Face) faces.get(4)).obtenirCouleur(1), Couleur.AUCUNE, ((Face) faces.get(0)).obtenirCouleur(7), Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(3)).obtenirCouleur(3));
    }


    private void drawCubeUpToDown(GL gl) {
        ArrayList faces = (ArrayList) cube.toFace();

        gl.glTranslatef(0.5f, 0.5f, 0.5f);

        if (cubeMoving && (mvt.equals(MouvementElementaire.Up) || mvt.equals(MouvementElementaire.up))) {
            gl.glRotatef(rotate, 0, 1.0f, 0);
        }

        if (cubeMoving && (mvt.equals(MouvementElementaire.U) || mvt.equals(MouvementElementaire.u))) {
            gl.glRotatef(-rotate, 0, 1.0f, 0);
        }

        if (cubeMoving && (mvt.equals(MouvementElementaire.U2) || mvt.equals(MouvementElementaire.u2))) {
            gl.glRotatef(2*rotate, 0, 1.0f, 0);
        }

        gl.glTranslatef(-0.5f, -0.5f, -0.5f);

        gl.glTranslatef(0, 1.0f, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(0)).obtenirCouleur(5), Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE);

        gl.glTranslatef(0, 0, -1.0f);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, ((Face) faces.get(5)).obtenirCouleur(2), ((Face) faces.get(0)).obtenirCouleur(2), Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE);

        gl.glTranslatef(1.0f, 0, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, ((Face) faces.get(5)).obtenirCouleur(1), ((Face) faces.get(0)).obtenirCouleur(3), Couleur.AUCUNE, ((Face) faces.get(2)).obtenirCouleur(3), Couleur.AUCUNE);

        gl.glTranslatef(0, 0, 1.0f);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(0)).obtenirCouleur(6), Couleur.AUCUNE, ((Face) faces.get(2)).obtenirCouleur(2), Couleur.AUCUNE);

        gl.glTranslatef(0, 0, 1.0f);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, ((Face) faces.get(4)).obtenirCouleur(3), Couleur.AUCUNE, ((Face) faces.get(0)).obtenirCouleur(9), Couleur.AUCUNE, ((Face) faces.get(2)).obtenirCouleur(1), Couleur.AUCUNE);

        gl.glTranslatef(-1.0f, 0, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, ((Face) faces.get(4)).obtenirCouleur(2), Couleur.AUCUNE, ((Face) faces.get(0)).obtenirCouleur(8), Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE);

        gl.glTranslatef(-1.0f, 0, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, ((Face) faces.get(4)).obtenirCouleur(1), Couleur.AUCUNE, ((Face) faces.get(0)).obtenirCouleur(7), Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(3)).obtenirCouleur(3));

        gl.glTranslatef(0, 0, -1.0f);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(0)).obtenirCouleur(4), Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(3)).obtenirCouleur(2));

        gl.glTranslatef(0, 0, -1.0f);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, ((Face) faces.get(5)).obtenirCouleur(3), ((Face) faces.get(0)).obtenirCouleur(1), Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(3)).obtenirCouleur(1));

        gl.glPopMatrix();
        gl.glPushMatrix();

        gl.glTranslatef(0.5f, 0.5f, 0.5f);

        if (cubeMoving && (mvt.equals(MouvementElementaire.E) || mvt.equals(MouvementElementaire.up) || mvt.equals(MouvementElementaire.d))) {
            gl.glRotatef(rotate, 0, 1.0f, 0);
        }

        if (cubeMoving && (mvt.equals(MouvementElementaire.Ep) || mvt.equals(MouvementElementaire.u) || mvt.equals(MouvementElementaire.dp))) {
            gl.glRotatef(-rotate, 0, 1.0f, 0);
        }

        if (cubeMoving && (mvt.equals(MouvementElementaire.E2) || mvt.equals(MouvementElementaire.u2) || mvt.equals(MouvementElementaire.d2))) {
            gl.glRotatef(2*rotate, 0, 1.0f, 0);
        }

        gl.glTranslatef(-0.5f, -0.5f, -0.5f);

        gl.glTranslatef(0, 0, -1.0f);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, ((Face) faces.get(5)).obtenirCouleur(5), Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE);

        gl.glTranslatef(1.0f, 0, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, ((Face) faces.get(5)).obtenirCouleur(4), Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(2)).obtenirCouleur(6), Couleur.AUCUNE);

        gl.glTranslatef(0, 0, 1.0f);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(2)).obtenirCouleur(5), Couleur.AUCUNE);

        gl.glTranslatef(0, 0, 1.0f);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, ((Face) faces.get(4)).obtenirCouleur(6), Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(2)).obtenirCouleur(4), Couleur.AUCUNE);

        gl.glTranslatef(-1.0f, 0, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, ((Face) faces.get(4)).obtenirCouleur(5), Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE);

        gl.glTranslatef(-1.0f, 0, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, ((Face) faces.get(4)).obtenirCouleur(4), Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(3)).obtenirCouleur(6));

        gl.glTranslatef(0, 0, -1.0f);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(3)).obtenirCouleur(5));

        gl.glTranslatef(0, 0, -1.0f);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, ((Face) faces.get(5)).obtenirCouleur(6), Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(3)).obtenirCouleur(4));

        gl.glPopMatrix();

        gl.glTranslatef(0.5f, 0.5f, 0.5f);

        if (cubeMoving && (mvt.equals(MouvementElementaire.D) || mvt.equals(MouvementElementaire.d))) {
            gl.glRotatef(rotate, 0, 1.0f, 0);
        }

        if (cubeMoving && (mvt.equals(MouvementElementaire.Dp) || mvt.equals(MouvementElementaire.dp))) {
            gl.glRotatef(-rotate, 0, 1.0f, 0);
        }

        if (cubeMoving && (mvt.equals(MouvementElementaire.D2) || mvt.equals(MouvementElementaire.d2))) {
            gl.glRotatef(2*rotate, 0, 1.0f, 0);
        }

        gl.glTranslatef(-0.5f, -0.5f, -0.5f);

        gl.glTranslatef(0, -1.0f, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(1)).obtenirCouleur(5), Couleur.AUCUNE, Couleur.AUCUNE);

        gl.glTranslatef(0, 0, -1.0f);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, ((Face) faces.get(5)).obtenirCouleur(8), Couleur.AUCUNE, ((Face) faces.get(1)).obtenirCouleur(8), Couleur.AUCUNE, Couleur.AUCUNE);

        gl.glTranslatef(1.0f, 0, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, ((Face) faces.get(5)).obtenirCouleur(7), Couleur.AUCUNE, ((Face) faces.get(1)).obtenirCouleur(9), ((Face) faces.get(2)).obtenirCouleur(9), Couleur.AUCUNE);

        gl.glTranslatef(0, 0, 1.0f);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(1)).obtenirCouleur(6), ((Face) faces.get(2)).obtenirCouleur(8), Couleur.AUCUNE);

        gl.glTranslatef(0, 0, 1.0f);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, ((Face) faces.get(4)).obtenirCouleur(9), Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(1)).obtenirCouleur(3), ((Face) faces.get(2)).obtenirCouleur(7), Couleur.AUCUNE);

        gl.glTranslatef(-1.0f, 0, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, ((Face) faces.get(4)).obtenirCouleur(8), Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(1)).obtenirCouleur(2), Couleur.AUCUNE, Couleur.AUCUNE);

        gl.glTranslatef(-1.0f, 0, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, ((Face) faces.get(4)).obtenirCouleur(7), Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(1)).obtenirCouleur(1), Couleur.AUCUNE, ((Face) faces.get(3)).obtenirCouleur(9));

        gl.glTranslatef(0, 0, -1.0f);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(1)).obtenirCouleur(4), Couleur.AUCUNE, ((Face) faces.get(3)).obtenirCouleur(8));

        gl.glTranslatef(0, 0, -1.0f);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, ((Face) faces.get(5)).obtenirCouleur(9), Couleur.AUCUNE, ((Face) faces.get(1)).obtenirCouleur(7), Couleur.AUCUNE, ((Face) faces.get(3)).obtenirCouleur(7));
    }


    private void drawCubeFrontToBack(GL gl) {
        ArrayList faces = (ArrayList) cube.toFace();

        gl.glTranslatef(0.5f, 0.5f, 0.5f);

        if (cubeMoving && (mvt.equals(MouvementElementaire.Fp) || mvt.equals(MouvementElementaire.fp))) {
            gl.glRotatef(rotate, 0, 0, 1.0f);
        }

        if (cubeMoving && (mvt.equals(MouvementElementaire.F2) || mvt.equals(MouvementElementaire.f2))) {
            gl.glRotatef(2*rotate, 0, 0, 1.0f);
        }

        if (cubeMoving && (mvt.equals(MouvementElementaire.F) || mvt.equals(MouvementElementaire.f))) {
            gl.glRotatef(-rotate, 0, 0, 1.0f);
        }

        gl.glTranslatef(-0.5f, -0.5f, -0.5f);

        gl.glTranslatef(0, 0, 1.0f);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, ((Face) faces.get(4)).obtenirCouleur(5), Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE);

        gl.glTranslatef(0, 1.0f, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, ((Face) faces.get(4)).obtenirCouleur(2), Couleur.AUCUNE, ((Face) faces.get(0)).obtenirCouleur(8), Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE);

        gl.glTranslatef(1.0f, 0, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, ((Face) faces.get(4)).obtenirCouleur(3), Couleur.AUCUNE, ((Face) faces.get(0)).obtenirCouleur(9), Couleur.AUCUNE, ((Face) faces.get(2)).obtenirCouleur(1), Couleur.AUCUNE);

        gl.glTranslatef(0, -1.0f, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, ((Face) faces.get(4)).obtenirCouleur(6), Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(2)).obtenirCouleur(4), Couleur.AUCUNE);

        gl.glTranslatef(0, -1.0f, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, ((Face) faces.get(4)).obtenirCouleur(9), Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(1)).obtenirCouleur(3), ((Face) faces.get(2)).obtenirCouleur(7), Couleur.AUCUNE);

        gl.glTranslatef(-1.0f, 0, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, ((Face) faces.get(4)).obtenirCouleur(8), Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(1)).obtenirCouleur(2), Couleur.AUCUNE, Couleur.AUCUNE);

        gl.glTranslatef(-1.0f, 0, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, ((Face) faces.get(4)).obtenirCouleur(7), Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(1)).obtenirCouleur(1), Couleur.AUCUNE, ((Face) faces.get(3)).obtenirCouleur(9));

        gl.glTranslatef(0, 1.0f, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, ((Face) faces.get(4)).obtenirCouleur(4), Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(3)).obtenirCouleur(6));

        gl.glTranslatef(0, 1.0f, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, ((Face) faces.get(4)).obtenirCouleur(1), Couleur.AUCUNE, ((Face) faces.get(0)).obtenirCouleur(7), Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(3)).obtenirCouleur(3));

        gl.glPopMatrix();
        gl.glPushMatrix();

        gl.glTranslatef(0.5f, 0.5f, 0.5f);

        if (cubeMoving && (mvt.equals(MouvementElementaire.Sp) || mvt.equals(MouvementElementaire.fp) || mvt.equals(MouvementElementaire.b))) {
            gl.glRotatef(rotate, 0, 0, 1.0f);
        }

        if (cubeMoving && (mvt.equals(MouvementElementaire.S) || mvt.equals(MouvementElementaire.f) || mvt.equals(MouvementElementaire.bp))) {
            gl.glRotatef(-rotate, 0, 0, 1.0f);
        }

        if (cubeMoving && (mvt.equals(MouvementElementaire.S2) || mvt.equals(MouvementElementaire.f2) || mvt.equals(MouvementElementaire.b2))) {
            gl.glRotatef(2*rotate, 0, 0, 1.0f);
        }


        gl.glTranslatef(-0.5f, -0.5f, -0.5f);

        gl.glTranslatef(0, 1.0f, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(0)).obtenirCouleur(5), Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE);

        gl.glTranslatef(1.0f, 0, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(0)).obtenirCouleur(6), Couleur.AUCUNE, ((Face) faces.get(2)).obtenirCouleur(2), Couleur.AUCUNE);

        gl.glTranslatef(0, -1.0f, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(2)).obtenirCouleur(5), Couleur.AUCUNE);

        gl.glTranslatef(0, -1.0f, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(1)).obtenirCouleur(6), ((Face) faces.get(2)).obtenirCouleur(8), Couleur.AUCUNE);

        gl.glTranslatef(-1.0f, 0, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(1)).obtenirCouleur(5), Couleur.AUCUNE, Couleur.AUCUNE);

        gl.glTranslatef(-1.0f, 0, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(1)).obtenirCouleur(4), Couleur.AUCUNE, ((Face) faces.get(3)).obtenirCouleur(8));

        gl.glTranslatef(0, 1.0f, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(3)).obtenirCouleur(5));



        gl.glTranslatef(0, 1.0f, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(0)).obtenirCouleur(4), Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(3)).obtenirCouleur(2));

        gl.glPopMatrix();

        gl.glTranslatef(0.5f, 0.5f, 0.5f);

        if (cubeMoving && (mvt.equals(MouvementElementaire.B) || mvt.equals(MouvementElementaire.b))) {
            gl.glRotatef(rotate, 0, 0, 1.0f);
        }

        if (cubeMoving && (mvt.equals(MouvementElementaire.Bp) || mvt.equals(MouvementElementaire.bp))) {
            gl.glRotatef(-rotate, 0, 0, 1.0f);
        }
        
        if (cubeMoving && (mvt.equals(MouvementElementaire.B2) || mvt.equals(MouvementElementaire.b2))) {
            gl.glRotatef(2*rotate, 0, 0, 1.0f);
        }
        

        gl.glTranslatef(-0.5f, -0.5f, -0.5f);

        gl.glTranslatef(0, 0, -1.0f);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, ((Face) faces.get(5)).obtenirCouleur(5), Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE);

        gl.glTranslatef(0, 1.0f, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, ((Face) faces.get(5)).obtenirCouleur(2), ((Face) faces.get(0)).obtenirCouleur(2), Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE);

        gl.glTranslatef(1.0f, 0, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, ((Face) faces.get(5)).obtenirCouleur(1), ((Face) faces.get(0)).obtenirCouleur(3), Couleur.AUCUNE, ((Face) faces.get(2)).obtenirCouleur(3), Couleur.AUCUNE);

        gl.glTranslatef(0, -1.0f, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, ((Face) faces.get(5)).obtenirCouleur(4), Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(2)).obtenirCouleur(6), Couleur.AUCUNE);

        gl.glTranslatef(0, -1.0f, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, ((Face) faces.get(5)).obtenirCouleur(7), Couleur.AUCUNE, ((Face) faces.get(1)).obtenirCouleur(9), ((Face) faces.get(2)).obtenirCouleur(9), Couleur.AUCUNE);

        gl.glTranslatef(-1.0f, 0, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, ((Face) faces.get(5)).obtenirCouleur(8), Couleur.AUCUNE, ((Face) faces.get(1)).obtenirCouleur(8), Couleur.AUCUNE, Couleur.AUCUNE);

        gl.glTranslatef(-1.0f, 0, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, ((Face) faces.get(5)).obtenirCouleur(9), Couleur.AUCUNE, ((Face) faces.get(1)).obtenirCouleur(7), Couleur.AUCUNE, ((Face) faces.get(3)).obtenirCouleur(7));

        gl.glTranslatef(0, 1.0f, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, ((Face) faces.get(5)).obtenirCouleur(6), Couleur.AUCUNE, Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(3)).obtenirCouleur(4));

        gl.glTranslatef(0, 1.0f, 0);
        drawCuboid(gl, cubieSize, cubieSize, cubieSize, Couleur.AUCUNE, ((Face) faces.get(5)).obtenirCouleur(3), ((Face) faces.get(0)).obtenirCouleur(1), Couleur.AUCUNE, Couleur.AUCUNE, ((Face) faces.get(3)).obtenirCouleur(1));
    }


    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glClearColor(0.4f, 0.4f, 0.4f, 1.f);
        gl.glEnable(GL.GL_DEPTH_TEST);

        drawable.addMouseMotionListener(this);
        drawable.addMouseListener(this);
    }



    public void display(GLAutoDrawable drawable) {

        n -= 1;

        if (n == 1) {
            currentNanos = System.nanoTime();
            fps = 10000000 / ((double)(currentNanos - oldNanos) / 1000);
            // System.out.println(fps);
            oldNanos = currentNanos;
            n = 10;

            step = (float) (stepFactor / fps);
        }

        GL gl = drawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        gl.glPushMatrix();

        gl.glTranslatef(0, 0, -20.0f);

        gl.glBegin(GL.GL_QUADS);

        gl.glColor3f(0, 0, 0);
        gl.glVertex3f(-40f, 10f, 0);
        gl.glVertex3f(40f, 10f, 0);

        gl.glColor3f(1f, 1f, 1f);
        gl.glVertex3f(40f, -10f, 0);
        gl.glVertex3f(-40f, -10f, 0);

        gl.glEnd();

        gl.glPopMatrix();

        gl.glTranslatef(0.0f, 0.0f, -10.0f);

        gl.glRotatef(cameraPhi, 0f, 1f, 0f);
        gl.glRotatef(cameraTheta, 1f, 0f, 0f);

        if (cubeMoving) {
            rotate += step;
        }

        if (rotate > 90f) {
            rotate = 90f;
            cube.effectuerMouvementElementaire(mvt);
            cubeMoving = false;
        }

        if (cubeMoving && mvt.equals(MouvementElementaire.x)) {
            gl.glRotatef(-rotate, 1.0f, 0, 0);
        }
        if (cubeMoving && mvt.equals(MouvementElementaire.x2)) {
            gl.glRotatef(-2*rotate, 1.0f, 0, 0);
        }
        if (cubeMoving && mvt.equals(MouvementElementaire.xp)) {
            gl.glRotatef(rotate, 1.0f, 0, 0);
        }

        if (cubeMoving && mvt.equals(MouvementElementaire.y)) {
            gl.glRotatef(-rotate, 0, 1.0f, 0);
        }
        if (cubeMoving && mvt.equals(MouvementElementaire.y2)) {
            gl.glRotatef(-2*rotate, 0, 1.0f, 0);
        }
        if (cubeMoving && mvt.equals(MouvementElementaire.yp)) {
            gl.glRotatef(rotate, 0, 1.0f, 0);
        }

        if (cubeMoving && mvt.equals(MouvementElementaire.z)) {
            gl.glRotatef(-rotate, 0, 0, 1.0f);
        }
        if (cubeMoving && mvt.equals(MouvementElementaire.z2)) {
            gl.glRotatef(-2*rotate, 0, 0, 1.0f);
        }
        if (cubeMoving && mvt.equals(MouvementElementaire.zp)) {
            gl.glRotatef(rotate, 0, 0, 1.0f);
        }

        // Center
        gl.glTranslatef(-0.5f, -0.5f, -0.5f);

        gl.glPushMatrix();

        switch (mvt) {
            case R:
            case Rp:
            case R2:
            case r:
            case rp:
            case r2:
            case L:
            case Lp:
            case L2:
            case l:
            case lp:
            case l2:
            case M:
            case Mp:
            case M2:
                drawCubeRightToLeft(gl);
                break;

            case U:
            case Up:
            case U2:
            case u:
            case up:
            case u2:
            case D:
            case Dp:
            case D2:
            case d:
            case dp:
            case d2:
            case E:
            case Ep:
            case E2:
                drawCubeUpToDown(gl);
                break;

            default:
                drawCubeFrontToBack(gl);
                break;
        }
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        if (height <= 0) {
            height = 1;
        }
        float h = (float) width / (float) height;
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(50.0f, h, 1.0, 1000.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void mouseDragged(MouseEvent e) {
        int x = e.getX(), y = e.getY();
        cameraPhi -= 3 * (oldX - x);
        oldX = x;
        cameraTheta += 3 * (oldY - y);
        oldY = y;
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        cameraMoving = true;
        oldX = e.getX();
        oldY = e.getY();
    }

    public void mouseReleased(MouseEvent e) {
        cameraMoving = false;
    }

    private static void exitWindow() {
        animator.stop();
        frame.dispose();
    }



    public static void playMovement(MouvementElementaire _mvt)
    {
        mvt = _mvt;
        rotate = 0;
        cubeMoving = true;
    }

    public static void waitForMovementEnd() throws InterruptedException
    {
        while (cubeMoving)
            Thread.sleep(10);
        
    }

    public static void runGL(Cube _cube, final boolean exitOnClose) throws Throwable
    {
        cube = _cube;
        canvas.addGLEventListener(new GLCube());
        frame.add(canvas);
        frame.setSize(640, 480);
        frame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                exitWindow();
                if(exitOnClose)
                    System.exit(0);
            }
        });

        frame.setVisible(true);
        animator.start();
        canvas.requestFocus();
    }
}

