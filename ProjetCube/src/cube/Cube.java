package cube;

import cube.MouvementElementaire;

import java.util.*;

/**
 *classe Cube
 *@author Groupe ECAO Rubik's Cube : Pierre_Bienaime Bastien_Bonnet Mathieu_Chataigner Mathieu_Fresquet
 */

public class Cube
{

    /**
     * classe interne Cubie
     * @author Groupe ECAO Rubik's Cube : Pierre_Bienaime Bastien_Bonnet Mathieu_Chataigner Mathieu Fresquet
     */
    public class Cubie{
	
        /*Attributs*/
        private Couleur couleurUD;
        private Couleur couleurFB;
        private Couleur couleurLR;
        private NatureCubie typeCubie;
	
	
	
        /*Constructeur*/
        /**
         *Construit une instance de Cubie à partir d'un type de cubie et de 3 couleurs
         *@param _typeCubie le type de cubie que l'on veut créer
         *@param _couleurUD la couleur en UD
         *@param _couleurFB la couleur en FB
         *@param _couleurLR la couleur en LR
         */
        public Cubie(NatureCubie _typeCubie, Couleur _couleurUD, Couleur _couleurFB, Couleur _couleurLR){
            typeCubie=_typeCubie;
            couleurUD=_couleurUD;
            couleurFB=_couleurFB;
            couleurLR=_couleurLR;
        }
	
        /*Méthodes*/
        /**
         *Retourne la couleur du cubie en UD
         *@return la couleur du cubie en UD
         */
        public Couleur obtenirCouleurUD(){
            return couleurUD;
        }
	
        /**
         *Retourne la couleur du cubie en FB
         *@return la couleur du cubie en FB
         */
        public Couleur obtenirCouleurFB(){
            return couleurFB;
        }
	
        /**
         *Retourne la couleur du cubie en LR
         *@return la couleur du cubie en LR
         */
        public Couleur obtenirCouleurLR(){
            return couleurLR;
        }
	
        /**
         *Répercute les conséquences d'un mouvement U, U', D ou D' sur un cubie
         */
        protected void effectuerUD(){
            Couleur temp=couleurFB;
            couleurFB=couleurLR;
            couleurLR=temp;
        }
	
        /**
         *Répercute les conséquences d'un mouvement F, F', B ou B' sur un cubie
         */
        protected void effectuerFB(){
            Couleur temp=couleurUD;
            couleurUD=couleurLR;
            couleurLR=temp;	
        }
	
        /**
         * Répercute les conséquences d'un mouvement L, L', R ou R' sur un cubie
         */
        protected void effectuerLR(){
            Couleur temp=couleurUD;
            couleurUD=couleurFB;
            couleurFB=temp;
        }

        /**
         * Redéfinition de la méthode toString() héritée de la classe mère Object
         * @return une String contenant les informations associées au Cubie
         */
        public String toString()
        {
            return couleurUD+" "+couleurFB+" "+couleurLR;
        }
    }


    /*Attributs*/
    private ArrayList<Cubie> lesCubies;
    private ArrayList<Position> lesPositions;

    private static Face  faceU=new Face(Couleur.JAUNE, Couleur.JAUNE, Couleur.JAUNE, Couleur.JAUNE, Couleur.JAUNE, Couleur.JAUNE, Couleur.JAUNE, Couleur.JAUNE, Couleur.JAUNE);
    private static Face  faceD=new Face(Couleur.BLANC,Couleur.BLANC,  Couleur.BLANC, Couleur.BLANC, Couleur.BLANC, Couleur.BLANC, Couleur.BLANC, Couleur.BLANC, Couleur.BLANC);
    private static Face  faceF=new Face(Couleur.ORANGE, Couleur.ORANGE, Couleur.ORANGE, Couleur.ORANGE, Couleur.ORANGE, Couleur.ORANGE, Couleur.ORANGE, Couleur.ORANGE, Couleur.ORANGE);
    private static Face  faceB=new Face(Couleur.ROUGE, Couleur.ROUGE, Couleur.ROUGE, Couleur.ROUGE, Couleur.ROUGE, Couleur.ROUGE, Couleur.ROUGE, Couleur.ROUGE, Couleur.ROUGE);
    private static Face  faceL=new Face(Couleur.VERT, Couleur.VERT, Couleur.VERT, Couleur.VERT, Couleur.VERT, Couleur.VERT, Couleur.VERT, Couleur.VERT, Couleur.VERT);
    private static Face  faceR=new Face(Couleur.BLEU, Couleur.BLEU, Couleur.BLEU, Couleur.BLEU, Couleur.BLEU, Couleur.BLEU, Couleur.BLEU, Couleur.BLEU, Couleur.BLEU);
	

    /*Constructeur*/
    
    
    /**
     *Construit une Cube en position resolue.
     */
    public Cube()throws CubeException
    {	
	this(faceU, faceD, faceR, faceL, faceF, faceB);
    }

    public static Cube creerCube(Collection<Face> lesFaces) throws CubeException
    {
        Face[] tabFace=lesFaces.toArray(new Face[0]);
        return new Cube(tabFace[0],tabFace[1],tabFace[2],tabFace[3],tabFace[4],tabFace[5]);
    }

    /**
     *Construit une nouvelle instance de Cube à partir de 6 faces.
     *@param faceU la face Up
     *@param faceD la face Down
     *@param faceR la face Right
     *@param faceL la face Left
     *@param faceF la face Front
     *@param faceB la face Back
     */
    public Cube(Face faceU,Face faceD,Face faceR,Face faceL,Face faceF,Face faceB)throws CubeException
    {
        lesCubies=new ArrayList<Cubie>(27);
        lesPositions=new ArrayList<Position>(27);
        for(int y=1;y<=3;y++)
            for(int z=1;z<=3;z++)
                for(int x=1;x<=3;x++)
                    lesPositions.add(new Position(x,y,z));
        lesCubies.add(new Cubie(NatureCubie.CORNER,faceD.obtenirCouleur(7),faceB.obtenirCouleur(9),faceL.obtenirCouleur(7)));
        lesCubies.add(new Cubie(NatureCubie.EDGE,faceD.obtenirCouleur(4),Couleur.AUCUNE,faceL.obtenirCouleur(8)));
        lesCubies.add(new Cubie(NatureCubie.CORNER,faceD.obtenirCouleur(1),faceF.obtenirCouleur(7),faceL.obtenirCouleur(9)));
        lesCubies.add(new Cubie(NatureCubie.EDGE,Couleur.AUCUNE,faceB.obtenirCouleur(6),faceL.obtenirCouleur(4)));
        lesCubies.add(new Cubie(NatureCubie.CENTER,Couleur.AUCUNE,Couleur.AUCUNE,faceL.obtenirCouleur(5)));
        lesCubies.add(new Cubie(NatureCubie.EDGE,Couleur.AUCUNE,faceF.obtenirCouleur(4),faceL.obtenirCouleur(6)));
        lesCubies.add(new Cubie(NatureCubie.CORNER,faceU.obtenirCouleur(1),faceB.obtenirCouleur(3),faceL.obtenirCouleur(1)));
        lesCubies.add(new Cubie(NatureCubie.EDGE,faceU.obtenirCouleur(4),Couleur.AUCUNE,faceL.obtenirCouleur(2)));
        lesCubies.add(new Cubie(NatureCubie.CORNER,faceU.obtenirCouleur(7),faceF.obtenirCouleur(1),faceL.obtenirCouleur(3)));
	          
        lesCubies.add(new Cubie(NatureCubie.EDGE,faceD.obtenirCouleur(8),faceB.obtenirCouleur(8),Couleur.AUCUNE));
        lesCubies.add(new Cubie(NatureCubie.CENTER,faceD.obtenirCouleur(5),Couleur.AUCUNE,Couleur.AUCUNE));
        lesCubies.add(new Cubie(NatureCubie.EDGE,faceD.obtenirCouleur(2),faceF.obtenirCouleur(8),Couleur.AUCUNE));
        lesCubies.add(new Cubie(NatureCubie.CENTER,Couleur.AUCUNE,faceB.obtenirCouleur(5),Couleur.AUCUNE));
        lesCubies.add(new Cubie(NatureCubie.CORE,Couleur.AUCUNE,Couleur.AUCUNE,Couleur.AUCUNE));
        lesCubies.add(new Cubie(NatureCubie.CENTER,Couleur.AUCUNE,faceF.obtenirCouleur(5),Couleur.AUCUNE));
        lesCubies.add(new Cubie(NatureCubie.EDGE,faceU.obtenirCouleur(2),faceB.obtenirCouleur(2),Couleur.AUCUNE));
        lesCubies.add(new Cubie(NatureCubie.CENTER,faceU.obtenirCouleur(5),Couleur.AUCUNE,Couleur.AUCUNE));
        lesCubies.add(new Cubie(NatureCubie.EDGE,faceU.obtenirCouleur(8),faceF.obtenirCouleur(2),Couleur.AUCUNE));

        lesCubies.add(new Cubie(NatureCubie.CORNER,faceD.obtenirCouleur(9),faceB.obtenirCouleur(7),faceR.obtenirCouleur(9)));
        lesCubies.add(new Cubie(NatureCubie.EDGE,faceD.obtenirCouleur(6),Couleur.AUCUNE,faceR.obtenirCouleur(8)));
        lesCubies.add(new Cubie(NatureCubie.CORNER,faceD.obtenirCouleur(3),faceF.obtenirCouleur(9),faceR.obtenirCouleur(7)));
        lesCubies.add(new Cubie(NatureCubie.EDGE,Couleur.AUCUNE,faceB.obtenirCouleur(4),faceR.obtenirCouleur(6)));
        lesCubies.add(new Cubie(NatureCubie.CENTER,Couleur.AUCUNE,Couleur.AUCUNE,faceR.obtenirCouleur(5)));
        lesCubies.add(new Cubie(NatureCubie.EDGE,Couleur.AUCUNE,faceF.obtenirCouleur(6),faceR.obtenirCouleur(4)));
        lesCubies.add(new Cubie(NatureCubie.CORNER,faceU.obtenirCouleur(3),faceB.obtenirCouleur(1),faceR.obtenirCouleur(3)));
        lesCubies.add(new Cubie(NatureCubie.EDGE,faceU.obtenirCouleur(6),Couleur.AUCUNE,faceR.obtenirCouleur(2)));
        lesCubies.add(new Cubie(NatureCubie.CORNER,faceU.obtenirCouleur(9),faceF.obtenirCouleur(3),faceR.obtenirCouleur(1)));

    }


    /**méthode permettant de fixer un cubie à une position sur le cube
     *@param leCubie le Cubie à fixer
     *@param laPosition la Position où le fixer
     */
    private void fixerCubie(Cubie leCubie,Position laPosition)
    {
        lesCubies.set(lesPositions.indexOf(laPosition),leCubie);
    }

    /**méthode permettant d'obtenir le Cubie se trouvant à une Position donnée
     *@param laPosition la Position à regarder
     *@return le Cubie trouvé
     */
    private Cubie obtenirCubie(Position laPosition)
    {
        return lesCubies.get(lesPositions.indexOf(laPosition));
    }

    /**méthode permettant d'effectuer un mouvement élémentaire sur le cube
     *@param mvtE le Mouvement Élémentaire à effectuer
     */
    public void effectuerMouvementElementaire(MouvementElementaire mvtE)
    {
        switch(mvtE){
        case U:
            effectuerMouvementElementaireU();
            break;
        case D:
            effectuerMouvementElementaireD();
            break;
        case R:
            effectuerMouvementElementaireR();
            break;
        case L:
            effectuerMouvementElementaireL();
            break;
        case F:
            effectuerMouvementElementaireF();
            break;
        case B:
            effectuerMouvementElementaireB();
            break;
        case x:
            effectuerMouvementElementaireX();
            break;
        case y:
            effectuerMouvementElementaireY();
            break;
        case z:
            effectuerMouvementElementaireZ();
            break;
        case M:
            effectuerMouvementElementaireM();
            break;
        case S:
            effectuerMouvementElementaireS();
            break;
        case E:
            effectuerMouvementElementaireE();
            break;
        case U2:
            effectuerMouvementElementaireU();
            effectuerMouvementElementaireU();
            break;
        case D2:
            effectuerMouvementElementaireD();
            effectuerMouvementElementaireD();
            break;
        case R2:
            effectuerMouvementElementaireR();
            effectuerMouvementElementaireR();
            break;
        case L2:
            effectuerMouvementElementaireL();
            effectuerMouvementElementaireL();
            break;
        case F2:
            effectuerMouvementElementaireF();
            effectuerMouvementElementaireF();
            break;
        case B2:
            effectuerMouvementElementaireB();
            effectuerMouvementElementaireB();
            break;
        case x2:
            effectuerMouvementElementaireX();
            effectuerMouvementElementaireX();
            break;
        case y2:
            effectuerMouvementElementaireY();
            effectuerMouvementElementaireY();
            break;
        case z2:
            effectuerMouvementElementaireZ();
            effectuerMouvementElementaireZ();
            break;
        case M2:
            effectuerMouvementElementaireM();
            effectuerMouvementElementaireM();
            break;
        case S2:
            effectuerMouvementElementaireS();
            effectuerMouvementElementaireS();
            break;
        case E2:
            effectuerMouvementElementaireE();
            effectuerMouvementElementaireE();
            break;
        case xp:
            effectuerMouvementElementaireX();
            effectuerMouvementElementaireX();
            effectuerMouvementElementaireX();
            break;
        case yp:
            effectuerMouvementElementaireY();
            effectuerMouvementElementaireY();
            effectuerMouvementElementaireY();
            break;
        case zp:
            effectuerMouvementElementaireZ();
            effectuerMouvementElementaireZ();
            effectuerMouvementElementaireZ();
            break;
        case Mp:
            effectuerMouvementElementaireM();
            effectuerMouvementElementaireM();
            effectuerMouvementElementaireM();
            break;
        case Sp:
            effectuerMouvementElementaireS();
            effectuerMouvementElementaireS();
            effectuerMouvementElementaireS();
            break;
        case Ep:
            effectuerMouvementElementaireE();
            effectuerMouvementElementaireE();
            effectuerMouvementElementaireE();
            break;
        case Rp:
            {
                effectuerMouvementElementaireR();
                effectuerMouvementElementaireR();
                effectuerMouvementElementaireR();
            }
            break;
        case Lp:
            {
                effectuerMouvementElementaireL();
                effectuerMouvementElementaireL();
                effectuerMouvementElementaireL();
            }
            break;
        case Up:
            {
                effectuerMouvementElementaireU();
                effectuerMouvementElementaireU();
                effectuerMouvementElementaireU();
            }
            break;
        case Dp:
            {
                effectuerMouvementElementaireD();
                effectuerMouvementElementaireD();
                effectuerMouvementElementaireD();
            }
            break;
        case Fp:
            {
                effectuerMouvementElementaireF();
                effectuerMouvementElementaireF();
                effectuerMouvementElementaireF();
            }
            break;
        case Bp:
            {
                effectuerMouvementElementaireB();
                effectuerMouvementElementaireB();
                effectuerMouvementElementaireB();
            }
            break;
        case r:
            {
                effectuerMouvementElementaireM();
                effectuerMouvementElementaireM();
                effectuerMouvementElementaireM();
                effectuerMouvementElementaireR();
            }
            break;
        case r2:
            {
                effectuerMouvementElementaireM();
                effectuerMouvementElementaireM();
                effectuerMouvementElementaireR();
                effectuerMouvementElementaireR();
            }
            break;
        case rp:
            {
                effectuerMouvementElementaireM();
                effectuerMouvementElementaireR();
                effectuerMouvementElementaireR();
                effectuerMouvementElementaireR();
            }
            break;
        case l:
            {
                effectuerMouvementElementaireL();
                effectuerMouvementElementaireM();
            }
            break;
        case l2:
            {
                effectuerMouvementElementaireL();
                effectuerMouvementElementaireL();
                effectuerMouvementElementaireM();
                effectuerMouvementElementaireM();
            }
            break;
        case lp:
            {
                effectuerMouvementElementaireL();
                effectuerMouvementElementaireL();
                effectuerMouvementElementaireL();
                effectuerMouvementElementaireM();
                effectuerMouvementElementaireM();
                effectuerMouvementElementaireM();
            }
            break;
        case f:
            {
                effectuerMouvementElementaireF();
                effectuerMouvementElementaireS();
            }
            break;
        case f2:
            {
                effectuerMouvementElementaireF();
                effectuerMouvementElementaireS();
                effectuerMouvementElementaireF();
                effectuerMouvementElementaireS();
            }
            break;
        case fp:
            {
                effectuerMouvementElementaireF();
                effectuerMouvementElementaireS();
                effectuerMouvementElementaireF();
                effectuerMouvementElementaireS();
                effectuerMouvementElementaireF();
                effectuerMouvementElementaireS();
            }
            break;
        case b:
            {
                effectuerMouvementElementaireB();
                effectuerMouvementElementaireS();
                effectuerMouvementElementaireS();
                effectuerMouvementElementaireS();
            }
            break;
        case b2:
            {
                effectuerMouvementElementaireB();
                effectuerMouvementElementaireB();
                effectuerMouvementElementaireS();
                effectuerMouvementElementaireS();
            }
            break;
        case bp:
            {
                effectuerMouvementElementaireB();
                effectuerMouvementElementaireB();
                effectuerMouvementElementaireB();
                effectuerMouvementElementaireS();
            }
            break;
        case u:
            {
                effectuerMouvementElementaireU();
                effectuerMouvementElementaireE();
                effectuerMouvementElementaireE();
                effectuerMouvementElementaireE();
            }
            break;
        case u2:
            {
                effectuerMouvementElementaireU();
                effectuerMouvementElementaireU();
                effectuerMouvementElementaireE();
                effectuerMouvementElementaireE();
            }
            break;
        case up:
            {
                effectuerMouvementElementaireU();
                effectuerMouvementElementaireU();
                effectuerMouvementElementaireU();
                effectuerMouvementElementaireE();
            }
            break;
        case d:
            {
                effectuerMouvementElementaireD();
                effectuerMouvementElementaireE();
            }
            break;
        case d2:
            {
                effectuerMouvementElementaireD();
                effectuerMouvementElementaireE();
                effectuerMouvementElementaireD();
                effectuerMouvementElementaireE();
            }
            break;
        case dp:
            {
                effectuerMouvementElementaireD();
                effectuerMouvementElementaireE();
                effectuerMouvementElementaireD();
                effectuerMouvementElementaireE();
                effectuerMouvementElementaireD();
                effectuerMouvementElementaireE();
            }
            break;

        }
    }

    /**
     * Effectue le mouvement élémentaire U sur le Cube
     */
    private void effectuerMouvementElementaireU()
    {
        Cubie leCubieTmp;
        lesCubies.get(8).effectuerUD();
        lesCubies.get(26).effectuerUD();
        lesCubies.get(24).effectuerUD();
        lesCubies.get(6).effectuerUD();

        lesCubies.get(15).effectuerUD();
        lesCubies.get(7).effectuerUD();
        lesCubies.get(17).effectuerUD();
        lesCubies.get(25).effectuerUD();

        leCubieTmp=lesCubies.get(6);
        lesCubies.set(6,lesCubies.get(8));
        lesCubies.set(8,lesCubies.get(26));
        lesCubies.set(26,lesCubies.get(24));
        lesCubies.set(24,leCubieTmp);
        leCubieTmp=lesCubies.get(15);
        lesCubies.set(15,lesCubies.get(7));
        lesCubies.set(7,lesCubies.get(17));
        lesCubies.set(17,lesCubies.get(25));
        lesCubies.set(25,leCubieTmp);
    }

    /**
     * Effectue le mouvement élémentaire D sur le Cube
     */
    private void effectuerMouvementElementaireD()
    {
        Cubie leCubieTmp;
        lesCubies.get(18).effectuerUD();
        lesCubies.get(20).effectuerUD();
        lesCubies.get(2).effectuerUD();
        lesCubies.get(0).effectuerUD();

        lesCubies.get(1).effectuerUD();
        lesCubies.get(9).effectuerUD();
        lesCubies.get(19).effectuerUD();
        lesCubies.get(11).effectuerUD();


        leCubieTmp=lesCubies.get(18);
        lesCubies.set(18,lesCubies.get(20));
        lesCubies.set(20,lesCubies.get(2));
        lesCubies.set(2,lesCubies.get(0));
        lesCubies.set(0,leCubieTmp);
        leCubieTmp=lesCubies.get(1);
        lesCubies.set(1,lesCubies.get(9));
        lesCubies.set(9,lesCubies.get(19));
        lesCubies.set(19,lesCubies.get(11));
        lesCubies.set(11,leCubieTmp);

    }

    /**
     * Effectue le mouvement élémentaire R sur le Cube
     */
    private void effectuerMouvementElementaireR()
    {
        Cubie leCubieTmp;
        lesCubies.get(18).effectuerLR();
        lesCubies.get(24).effectuerLR();
        lesCubies.get(26).effectuerLR();
        lesCubies.get(20).effectuerLR();

        lesCubies.get(19).effectuerLR();
        lesCubies.get(21).effectuerLR();
        lesCubies.get(25).effectuerLR();
        lesCubies.get(23).effectuerLR();


        leCubieTmp=lesCubies.get(18);
        lesCubies.set(18,lesCubies.get(24));
        lesCubies.set(24,lesCubies.get(26));
        lesCubies.set(26,lesCubies.get(20));
        lesCubies.set(20,leCubieTmp);
        leCubieTmp=lesCubies.get(19);
        lesCubies.set(19,lesCubies.get(21));
        lesCubies.set(21,lesCubies.get(25));
        lesCubies.set(25,lesCubies.get(23));
        lesCubies.set(23,leCubieTmp);

    }

    /**
     * Effectue le mouvement élémentaire L sur le Cube
     */
    private void effectuerMouvementElementaireL()
    {
        Cubie leCubieTmp;
        lesCubies.get(0).effectuerLR();
        lesCubies.get(6).effectuerLR();
        lesCubies.get(8).effectuerLR();
        lesCubies.get(2).effectuerLR();

        lesCubies.get(1).effectuerLR();
        lesCubies.get(3).effectuerLR();
        lesCubies.get(7).effectuerLR();
        lesCubies.get(5).effectuerLR();


        leCubieTmp=lesCubies.get(0);
        lesCubies.set(0,lesCubies.get(2));
        lesCubies.set(2,lesCubies.get(8));
        lesCubies.set(8,lesCubies.get(6));
        lesCubies.set(6,leCubieTmp);
        leCubieTmp=lesCubies.get(1);
        lesCubies.set(1,lesCubies.get(5));
        lesCubies.set(5,lesCubies.get(7));
        lesCubies.set(7,lesCubies.get(3));
        lesCubies.set(3,leCubieTmp);

    }

    /**
     * Effectue le mouvement élémentaire F sur le Cube
     */
    private void effectuerMouvementElementaireF()
    {
        Cubie leCubieTmp;
        lesCubies.get(8).effectuerFB();
        lesCubies.get(2).effectuerFB();
        lesCubies.get(20).effectuerFB();
        lesCubies.get(26).effectuerFB();

        lesCubies.get(5).effectuerFB();
        lesCubies.get(11).effectuerFB();
        lesCubies.get(23).effectuerFB();
        lesCubies.get(17).effectuerFB();

        leCubieTmp=lesCubies.get(8);
        lesCubies.set(8,lesCubies.get(2));
        lesCubies.set(2,lesCubies.get(20));
        lesCubies.set(20,lesCubies.get(26));
        lesCubies.set(26,leCubieTmp);
        leCubieTmp=lesCubies.get(5);
        lesCubies.set(5,lesCubies.get(11));
        lesCubies.set(11,lesCubies.get(23));
        lesCubies.set(23,lesCubies.get(17));
        lesCubies.set(17,leCubieTmp);

    }

    /**
     * Effectue le mouvement élémentaire U sur le Cube
     */
    private void effectuerMouvementElementaireB()
    {
        Cubie leCubieTmp;
        lesCubies.get(0).effectuerFB();
        lesCubies.get(6).effectuerFB();
        lesCubies.get(24).effectuerFB();
        lesCubies.get(18).effectuerFB();

        lesCubies.get(3).effectuerFB();
        lesCubies.get(15).effectuerFB();
        lesCubies.get(21).effectuerFB();
        lesCubies.get(9).effectuerFB();

        leCubieTmp=lesCubies.get(0);
        lesCubies.set(0,lesCubies.get(6));
        lesCubies.set(6,lesCubies.get(24));
        lesCubies.set(24,lesCubies.get(18));
        lesCubies.set(18,leCubieTmp);
        leCubieTmp=lesCubies.get(3);
        lesCubies.set(3,lesCubies.get(15));
        lesCubies.set(15,lesCubies.get(21));
        lesCubies.set(21,lesCubies.get(9));
        lesCubies.set(9,leCubieTmp);

    }

    /**
     * Effectue le mouvement élémentaire M sur le Cube
     */
    private void effectuerMouvementElementaireM()
    {
        Cubie leCubieTmp;
        lesCubies.get(9).effectuerLR();
        lesCubies.get(11).effectuerLR();
        lesCubies.get(15).effectuerLR();
        lesCubies.get(17).effectuerLR();

        lesCubies.get(10).effectuerLR();
        lesCubies.get(12).effectuerLR();
        lesCubies.get(14).effectuerLR();
        lesCubies.get(16).effectuerLR();

        leCubieTmp=lesCubies.get(9);
        lesCubies.set(9,lesCubies.get(11));
        lesCubies.set(11,lesCubies.get(17));
        lesCubies.set(17,lesCubies.get(15));
        lesCubies.set(15,leCubieTmp);

        leCubieTmp=lesCubies.get(10);
        lesCubies.set(10,lesCubies.get(14));
        lesCubies.set(14,lesCubies.get(16));
        lesCubies.set(16,lesCubies.get(12));
        lesCubies.set(12,leCubieTmp);

    }
        
    /**
     * Effectue le mouvement élémentaire S sur le Cube
     */
    private void effectuerMouvementElementaireS()
    {
        Cubie leCubieTmp;
        lesCubies.get(1).effectuerFB();
        lesCubies.get(19).effectuerFB();
        lesCubies.get(25).effectuerFB();
        lesCubies.get(7).effectuerFB();

        lesCubies.get(4).effectuerFB();
        lesCubies.get(10).effectuerFB();
        lesCubies.get(22).effectuerFB();
        lesCubies.get(16).effectuerFB();

        leCubieTmp=lesCubies.get(1);
        lesCubies.set(1,lesCubies.get(19));
        lesCubies.set(19,lesCubies.get(25));
        lesCubies.set(25,lesCubies.get(7));
        lesCubies.set(7,leCubieTmp);

        leCubieTmp=lesCubies.get(4);
        lesCubies.set(4,lesCubies.get(10));
        lesCubies.set(10,lesCubies.get(22));
        lesCubies.set(22,lesCubies.get(16));
        lesCubies.set(16,leCubieTmp);

    }

    /**
     * Effectue le mouvement élémentaire E sur le Cube
     */
    private void effectuerMouvementElementaireE()
    {
        Cubie leCubieTmp;
        lesCubies.get(3).effectuerUD();
        lesCubies.get(5).effectuerUD();
        lesCubies.get(23).effectuerUD();
        lesCubies.get(21).effectuerUD();

        lesCubies.get(4).effectuerUD();
        lesCubies.get(14).effectuerUD();
        lesCubies.get(22).effectuerUD();
        lesCubies.get(12).effectuerUD();

        leCubieTmp=lesCubies.get(3);
        lesCubies.set(3,lesCubies.get(21));
        lesCubies.set(21,lesCubies.get(23));
        lesCubies.set(23,lesCubies.get(5));
        lesCubies.set(5,leCubieTmp);

        leCubieTmp=lesCubies.get(4);
        lesCubies.set(4,lesCubies.get(12));
        lesCubies.set(12,lesCubies.get(22));
        lesCubies.set(22,lesCubies.get(14));
        lesCubies.set(14,leCubieTmp);


    }

    /**
     * Effectue le mouvement élémentaire X sur le Cube
     */
    private void effectuerMouvementElementaireX()
    {
        effectuerMouvementElementaireR();
        effectuerMouvementElementaireL();
        effectuerMouvementElementaireL();
        effectuerMouvementElementaireL();
        effectuerMouvementElementaireM();
        effectuerMouvementElementaireM();
        effectuerMouvementElementaireM();
    }

    /**
     * Effectue le mouvement élémentaire Y sur le Cube
     */
    private void effectuerMouvementElementaireY()
    {
        effectuerMouvementElementaireU();
        effectuerMouvementElementaireD();
        effectuerMouvementElementaireD();
        effectuerMouvementElementaireD();
        effectuerMouvementElementaireE();
        effectuerMouvementElementaireE();
        effectuerMouvementElementaireE();
    }

    /**
     * Effectue le mouvement élémentaire Z sur le Cube
     */
    private void effectuerMouvementElementaireZ()
    {
        effectuerMouvementElementaireF();
        effectuerMouvementElementaireS();
        effectuerMouvementElementaireB();
        effectuerMouvementElementaireB();
        effectuerMouvementElementaireB();
    }


    /**
     * Méthode permettant de trouver la position d'un Cubie sur le Cube
     *@param leCubie, le Cubie à retrouver
     *@return la Position trouvée
     */
    private Position obtenirPosition(Cubie leCubie)
    {
        return lesPositions.get(lesCubies.indexOf(leCubie));
    }

    /**
     * Méthode permettant de retrouver une Position à partir de 3 couleurs
     *@param clr1 la première couleur
     *@param clr2 la deuxième couleur
     *@param clr3 la troisième couleur
     *@return la Position trouvée
     */
    public Position obtenirPosition(Couleur clr1,Couleur clr2,Couleur clr3)throws CubeException
    {
        boolean trouve=false;
        Cubie leCubie=null;
        Iterator<Cubie> i=lesCubies.iterator(); 
        while(!trouve&&i.hasNext())
            {
                leCubie=i.next();
                if((leCubie.obtenirCouleurFB().equals(clr1)&&leCubie.obtenirCouleurLR().equals(clr2)&&leCubie.obtenirCouleurUD().equals(clr3))||
                   (leCubie.obtenirCouleurFB().equals(clr1)&&leCubie.obtenirCouleurLR().equals(clr3)&&leCubie.obtenirCouleurUD().equals(clr2))||
                   (leCubie.obtenirCouleurFB().equals(clr2)&&leCubie.obtenirCouleurLR().equals(clr1)&&leCubie.obtenirCouleurUD().equals(clr3))||
                   (leCubie.obtenirCouleurFB().equals(clr2)&&leCubie.obtenirCouleurLR().equals(clr3)&&leCubie.obtenirCouleurUD().equals(clr1))||
                   (leCubie.obtenirCouleurFB().equals(clr3)&&leCubie.obtenirCouleurLR().equals(clr1)&&leCubie.obtenirCouleurUD().equals(clr2))||
                   (leCubie.obtenirCouleurFB().equals(clr3)&&leCubie.obtenirCouleurLR().equals(clr2)&&leCubie.obtenirCouleurUD().equals(clr1)))
                    {
                        trouve=true;
                    }
            }
        if(!trouve)
            {
                throw new CubeException("couleurs non trouvées sur le cube");
            }
        return obtenirPosition(leCubie);
    }

    /**
     * Retourne l'orientation de la couleur d'un cubie qui se situe à une position donnée
     *@param laPosition position du cubie considéré
     *@param laCouleur couleur du cubie dont on veut connaître l'orientation
     *@return l'orientation de cette couleur
     */
    public Orientation obtenirOrientationDUnePositionCouleur(Position laPosition,Couleur laCouleur)throws CubeException
    {
        int index;
        Iterator i=lesPositions.iterator();
        Position laPositionTrouve=null;
        boolean trouve=false;
        Orientation lOrientation=null;
        while(!trouve&&i.hasNext())
            {
                laPositionTrouve=(Position)i.next();
                if(laPositionTrouve.equals(laPosition))
                    trouve=true;
            }
        Cubie leCubie=lesCubies.get(lesPositions.indexOf(laPositionTrouve));
        if(leCubie.obtenirCouleurUD().equals(laCouleur))
            if(laPosition.obtenirZ()==1)
                lOrientation=Orientation.D;
            else
                lOrientation=Orientation.U;
        else if(leCubie.obtenirCouleurLR().equals(laCouleur))
            if(laPosition.obtenirY()==1)
                lOrientation=Orientation.L;
            else
                lOrientation=Orientation.R;
        else if(leCubie.obtenirCouleurFB().equals(laCouleur))
            if(laPosition.obtenirX()==1)
                lOrientation=Orientation.B;
            else
                lOrientation=Orientation.F;
        else
            throw new CubeException("la couleur n'est pas trouvée à la position donnée.");
        return lOrientation;
    }

    /**
     * Retourne la couleur qui se trouve dans une position donné à une orientation donnée
     *@param laPosition position à laquelle on veut trouver la couleur recherchée
     *@param lOrientation orientation à laquelle on veut trouver la couleur recherchée
     *@return la Couleur à la Position et à l'Orientation considérées
     */
    public Couleur obtenirCouleurDUnePositionOrientation(Position laPosition,Orientation lOrientation)
    {
        int index;
        Iterator i=lesPositions.iterator();
        Position laPositionTrouve=null;
        boolean trouve=false;
        Couleur laCouleurTrouve=null;
        while(!trouve&&i.hasNext())
            {
                laPositionTrouve=(Position)i.next();
                if(laPositionTrouve.equals(laPosition))
                    trouve=true;
            };
        if(lOrientation==Orientation.U||lOrientation==Orientation.D)
            laCouleurTrouve=lesCubies.get(lesPositions.indexOf(laPositionTrouve)).obtenirCouleurUD();
        if(lOrientation==Orientation.F||lOrientation==Orientation.B)
            laCouleurTrouve=lesCubies.get(lesPositions.indexOf(laPositionTrouve)).obtenirCouleurFB();
        if(lOrientation==Orientation.R||lOrientation==Orientation.L)
            laCouleurTrouve=lesCubies.get(lesPositions.indexOf(laPositionTrouve)).obtenirCouleurLR();
        return laCouleurTrouve;
    }

    /**
     * Méthode permettant de recréer les faces du cube
     *@return une Collection des Faces du cube.
     */
    public Collection<Face> toFace()
    {
        Face faceU;
        Face faceD;
        Face faceR;
        Face faceL;
        Face faceF;
        Face faceB;
        faceU=new Face(lesCubies.get(6).obtenirCouleurUD(),
                       lesCubies.get(15).obtenirCouleurUD(),
                       lesCubies.get(24).obtenirCouleurUD(),
                       lesCubies.get(7).obtenirCouleurUD(),
                       lesCubies.get(16).obtenirCouleurUD(),
                       lesCubies.get(25).obtenirCouleurUD(),
                       lesCubies.get(8).obtenirCouleurUD(),
                       lesCubies.get(17).obtenirCouleurUD(),
                       lesCubies.get(26).obtenirCouleurUD());

        faceD=new Face(lesCubies.get(2).obtenirCouleurUD(),
                       lesCubies.get(11).obtenirCouleurUD(),
                       lesCubies.get(20).obtenirCouleurUD(),
                       lesCubies.get(1).obtenirCouleurUD(),
                       lesCubies.get(10).obtenirCouleurUD(),
                       lesCubies.get(19).obtenirCouleurUD(),
                       lesCubies.get(0).obtenirCouleurUD(),
                       lesCubies.get(9).obtenirCouleurUD(),
                       lesCubies.get(18).obtenirCouleurUD());

        faceR=new Face(lesCubies.get(26).obtenirCouleurLR(),
                       lesCubies.get(25).obtenirCouleurLR(),
                       lesCubies.get(24).obtenirCouleurLR(),
                       lesCubies.get(23).obtenirCouleurLR(),
                       lesCubies.get(22).obtenirCouleurLR(),
                       lesCubies.get(21).obtenirCouleurLR(),
                       lesCubies.get(20).obtenirCouleurLR(),
                       lesCubies.get(19).obtenirCouleurLR(),
                       lesCubies.get(18).obtenirCouleurLR());

        faceL=new Face(lesCubies.get(6).obtenirCouleurLR(),
                       lesCubies.get(7).obtenirCouleurLR(),
                       lesCubies.get(8).obtenirCouleurLR(),
                       lesCubies.get(3).obtenirCouleurLR(),
                       lesCubies.get(4).obtenirCouleurLR(),
                       lesCubies.get(5).obtenirCouleurLR(),
                       lesCubies.get(0).obtenirCouleurLR(),
                       lesCubies.get(1).obtenirCouleurLR(),
                       lesCubies.get(2).obtenirCouleurLR());

        faceF=new Face(lesCubies.get(8).obtenirCouleurFB(),
                       lesCubies.get(17).obtenirCouleurFB(),
                       lesCubies.get(26).obtenirCouleurFB(),
                       lesCubies.get(5).obtenirCouleurFB(),
                       lesCubies.get(14).obtenirCouleurFB(),
                       lesCubies.get(23).obtenirCouleurFB(),
                       lesCubies.get(2).obtenirCouleurFB(),
                       lesCubies.get(11).obtenirCouleurFB(),
                       lesCubies.get(20).obtenirCouleurFB());

        faceB=new Face(lesCubies.get(24).obtenirCouleurFB(),
                       lesCubies.get(15).obtenirCouleurFB(),
                       lesCubies.get(6).obtenirCouleurFB(),
                       lesCubies.get(21).obtenirCouleurFB(),
                       lesCubies.get(12).obtenirCouleurFB(),
                       lesCubies.get(3).obtenirCouleurFB(),
                       lesCubies.get(18).obtenirCouleurFB(),
                       lesCubies.get(9).obtenirCouleurFB(),
                       lesCubies.get(0).obtenirCouleurFB());

        ArrayList<Face> lesFaces=new ArrayList<Face>(6);
        lesFaces.add(faceU);
        lesFaces.add(faceD);
        lesFaces.add(faceR);
        lesFaces.add(faceL);
        lesFaces.add(faceF);
        lesFaces.add(faceB);

        return lesFaces;
    }

    /**
     * Redéfinition de la méthode toString() héritée de la classe mère Object
     *@return une String contenant les informations associées au Cube
     */
    public String toString()
    {
        StringBuilder str=new StringBuilder();
        ArrayList<Face> lesFaces=(ArrayList<Face>)toFace();
        String str2="                              ";


        String[] laFaceU=lesFaces.get(0).toString().split("\n");
        for(String i:laFaceU)
            {
                str.append(str2);
                str.append(i);
                str.append("\n");
            }

        String[] laFaceR=lesFaces.get(2).toString().split("\n");
        String[] laFaceL=lesFaces.get(3).toString().split("\n");
        String[] laFaceF=lesFaces.get(4).toString().split("\n");
        String[] laFaceB=lesFaces.get(5).toString().split("\n");

        for(int i=0;i<=4;i++)
            {
                str.append(laFaceL[i]);
                str.append("  ");
                str.append(laFaceF[i]);
                str.append("  ");
                str.append(laFaceR[i]);
                str.append("  ");
                str.append(laFaceB[i]);
                str.append("\n");
            }

        String[] laFaceD=lesFaces.get(1).toString().split("\n");
        for(String i:laFaceD)
            {
                str.append(str2);
                str.append(i);
                str.append("\n");
            }

        return str.toString();
    }
}
