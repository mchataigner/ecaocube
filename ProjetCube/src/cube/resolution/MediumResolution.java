package cube.resolution;

import cube.*;

import org.xml.sax.SAXException;
import java.io.IOException;

/**
 *classe MediumResolution
 *@author Groupe ECAO Rubik's Cube : Pierre_Bienaime Bastien_Bonnet Mathieu_Chataigner Mathieu Fresquet.
 */
public class MediumResolution implements ResolutionDuCube
{

    /* Attributs */
    private Cube cube;
    private Algorithme solution;
    /*	private static final Couleur COULEUR_U = Couleur.JAUNE;
    private static final Couleur COULEUR_D = Couleur.BLANC;
    private static final Couleur COULEUR_L = Couleur.VERT;
    private static final Couleur COULEUR_R = Couleur.BLEU;
    private static final Couleur COULEUR_F = Couleur.ORANGE;
    private static final Couleur COULEUR_B = Couleur.ROUGE;
    */
    private Couleur COULEUR_U;
    private Couleur COULEUR_D;
    private Couleur COULEUR_L;
    private Couleur COULEUR_R;
    private Couleur COULEUR_F;
    private Couleur COULEUR_B;
    private BaseAlgorithmes baseAlgo;

    /* Constructeur */
    /**
    * Construit une MediumResolution
    *@param _cube le Cube à résoudre
    */
    public MediumResolution(Cube _cube)
    {
        this.cube = _cube;
        solution = new Algorithme();
        try
	        {
		        COULEUR_U = cube.obtenirCouleurDUnePositionOrientation(new Position(2,2,3),Orientation.U);
		        COULEUR_D = cube.obtenirCouleurDUnePositionOrientation(new Position(2,2,1),Orientation.D);
		        COULEUR_L = cube.obtenirCouleurDUnePositionOrientation(new Position(2,1,2),Orientation.L);
		        COULEUR_R = cube.obtenirCouleurDUnePositionOrientation(new Position(2,3,2),Orientation.R);
		        COULEUR_F = cube.obtenirCouleurDUnePositionOrientation(new Position(3,2,2),Orientation.F);
                        COULEUR_B = cube.obtenirCouleurDUnePositionOrientation(new Position(1,2,2),Orientation.B);
                        baseAlgo=new BaseAlgorithmes("src/xml/algo.xml");
	        }
        catch(CubeException e)
	        {
                    System.err.println("Initialisation des couleurs du cube impossible");
                    e.printStackTrace();
	        }
        catch(SAXException e)
            {
                System.err.println("Initialisation des couleurs du cube impossible");
                e.printStackTrace();
            }
        catch(IOException e)
            {
                System.err.println("Initialisation des couleurs du cube impossible");
                e.printStackTrace();
            }
    }

    /* Méthodes */
    /**
    * Retourne la solution qui résout le Cube sous forme d'Algorithme
    *@return l'Algorithme contenant la solution
    */
    public Algorithme trouverSolution() throws CubeException,ResolutionException
    {
        resoudrePremiereCouronne();
        resoudreDeuxiemeCouronne();
        resoudreTroisiemeCouronne();
        return this.solution;	
    }

    /**
    * Résout la première couronne du Cube
    */
    private void resoudrePremiereCouronne() throws CubeException
    {
        resoudreCroix();
        resoudreCorners();
    }

    /**
    * Résout la deuxième couronne du Cube
    */
    private void resoudreDeuxiemeCouronne() throws CubeException
    {
        Algorithme rotation = new Algorithme("y");
        for(int i=1;i<=4;i++)
        {
	        placerBelge(repererBelge());
	        this.solution.concatenerAlgorithmes(rotation);
	        rotation.executerSurCube(this.cube);
        }
        //System.err.println("solution après f2l : "+this.solution);
    }

    /**
    * Résout la troisième couronne du Cube
    */
    private void resoudreTroisiemeCouronne() throws PositionNonValideException,CubeException,ResolutionException

    {
        Algorithme setupFinal=null;

        if(! repererOrientation().equals("UUUUUUUUU"))
        {
	        resoudreOrientation(repererOrientation());
        }

        if(!repererPermutation().equals("FFFRRRBBBLLL"))
        {
            //System.err.println(repererPermutation());
            //System.err.println(repererTypePermutation());
            //System.err.println(baseAlgo.obtenirPLL(repererPermutation()));
            resoudrePermutation(repererPermutation());
        }
    }

/****************************************

PREMIÈRE COURONNE

*****************************************/


    /**
    * Permet d'obtenir la croix, première étape de la résolution
    */
    private void resoudreCroix() throws CubeException
    {
        Algorithme rotation = new Algorithme("y");
        for(int i=1;i<=4;i++)
        {
	        placerEdge(repererEdge());
	        this.solution.concatenerAlgorithmes(rotation);
	        rotation.executerSurCube(this.cube);
        }
    }

    /**
    * Repère un edge sur le Cube, et retourne sa position
    *@return La position de l'edge sur le Cube
    */
    private Position repererEdge() throws CubeException
    {
        return cube.obtenirPosition(COULEUR_D,cube.obtenirCouleurDUnePositionOrientation(new Position(3,2,2),Orientation.F),Couleur.AUCUNE);
    }

    /**
    * Place un edge qui se trouve à une position donnée à sa place correcte
    *@return La Position de l'edge sur le cube avant placement
    */
    private void placerEdge(Position position) throws CubeException
    {
        Algorithme algoEdge1=null;
        Algorithme algoEdge2=null;
        boolean estUnCasSimple = false;
        if(position.obtenirZ()==1)
        {
	        if(position.obtenirX()==2 && position.obtenirY()==3)
	        {
		        algoEdge1 = new Algorithme("R R U");
	        }
	        else if(position.obtenirX()==1 && position.obtenirY()==2)
	        {
		        algoEdge1 = new Algorithme("B B U U");
	        }
	        else if(position.obtenirX()==2 && position.obtenirY()==1)
	        {
		        algoEdge1 = new Algorithme("L L Up");
	        }
	        else
	        {
		        if(cube.obtenirOrientationDUnePositionCouleur(new Position(3,2,1),COULEUR_D) == Orientation.D)
			        estUnCasSimple = true;
		        else	
			        algoEdge1 = new Algorithme("F F");
	        }
        }
        else if(position.obtenirZ()==2)
        {
	        if(position.obtenirX()==3 && position.obtenirY()==3)
	        {
		        if(cube.obtenirOrientationDUnePositionCouleur(new Position(3,3,2),COULEUR_D) == Orientation.R)
		        {
			        estUnCasSimple = true;
			        algoEdge1 = new Algorithme("F");
			        this.solution.concatenerAlgorithmes(algoEdge1);
			        algoEdge1.executerSurCube(this.cube);
		        }
		        algoEdge1 = new Algorithme("Fp");
	        }
	        else if(position.obtenirX()==1 && position.obtenirY()==3)
	        {
		        algoEdge1 = new Algorithme("Rp U R");
	        }
	        else if(position.obtenirX()==1 && position.obtenirY()==1)
	        {
		        algoEdge1 = new Algorithme("L Up Lp");
	        }
	        else
	        {
		        if(cube.obtenirOrientationDUnePositionCouleur(new Position(3,1,2),COULEUR_D) == Orientation.L)
		        {
			        estUnCasSimple = true;
			        algoEdge1 = new Algorithme("Fp");
			        this.solution.concatenerAlgorithmes(algoEdge1);
			        algoEdge1.executerSurCube(this.cube);
		        }
		        else
			        algoEdge1 = new Algorithme("F");
	        }
        }
        else
        {
	        if(position.obtenirX()==2 && position.obtenirY()==3)
	        {
		        algoEdge1 = new Algorithme("U");
	        }
	        else if(position.obtenirX()==1 && position.obtenirY()==2)
	        {
		        algoEdge1 = new Algorithme("U U");
	        }
	        else if(position.obtenirX()==2 && position.obtenirY()==1)
	        {
		        algoEdge1 = new Algorithme("Up");
	        }
        }

        if(estUnCasSimple == false)
        {
	        if(algoEdge1!=null)
	        {
		        this.solution.concatenerAlgorithmes(algoEdge1);
		        algoEdge1.executerSurCube(this.cube);
	        }
	        /* L'edge est maintenant placé à la position 2,3,3 */

	        Orientation orientationEdge = cube.obtenirOrientationDUnePositionCouleur(new Position(3,2,3),COULEUR_D);
	
	        if(orientationEdge == Orientation.U)
	        {
		        algoEdge2= new Algorithme("F F");
	        }
	        else
	        {
		        algoEdge2= new Algorithme("Up Rp F R");
	        }
	        this.solution.concatenerAlgorithmes(algoEdge2);
	        algoEdge2.executerSurCube(this.cube);
        }
    }



    /**
    * Résout les corners de la première couronne successivement
    */
    private void resoudreCorners() throws CubeException
    {
        Algorithme rotation = new Algorithme("y");
        for(int i=1;i<=4;i++)
        {
	        placerCorner(repererCorner());
	        this.solution.concatenerAlgorithmes(rotation);
	        rotation.executerSurCube(this.cube);
        }
    }


    /**
    * Repère un corner à placer pour la première couronne
    *@return La Position à laquelle se trouve le Corner trouvé
    */
    private Position repererCorner() throws CubeException
    {
        return cube.obtenirPosition(COULEUR_D,cube.obtenirCouleurDUnePositionOrientation(new Position(3,2,2),Orientation.F),cube.obtenirCouleurDUnePositionOrientation(new Position(2,3,2),Orientation.R));
    }

    /**
    * Place un Corner qui se trouve à une Position donnée
    */
    private void placerCorner(Position position) throws CubeException
    {
        Algorithme algoCorner1=null;
        Algorithme algoCorner2=null;
        boolean estUnCasSimple = false;
        if(position.obtenirZ()==1)
        {
            if(position.obtenirX()==1 && position.obtenirY()==3)
	        {
		        algoCorner1 = new Algorithme("B U Bp");
	        }
	        else if(position.obtenirX()==1 && position.obtenirY()==1)
	        {
		        algoCorner1 = new Algorithme("L U Lp U");
	        }
	        else if(position.obtenirX()==3 && position.obtenirY()==1)
	        {
		        algoCorner1 = new Algorithme("Lp Up L");
	        }
	        else
	        {
		        if(cube.obtenirOrientationDUnePositionCouleur(new Position(3,3,1),COULEUR_D) == Orientation.D)
			        estUnCasSimple = true;
		        else
			        algoCorner1 = new Algorithme("R U Rp Up");
	        }
        }
        else if(position.obtenirZ()==3)
        {
	        if(position.obtenirX()==1 && position.obtenirY()==3)
	        {
		        algoCorner1 = new Algorithme("U");
	        }
	        else if(position.obtenirX()==3 && position.obtenirY()==1)
	        {
		        algoCorner1 = new Algorithme("Up");
	        }
	        else if(position.obtenirX()==1 && position.obtenirY()==1)
	        {
		        algoCorner1 = new Algorithme("U U");
	        }
        }

        if(estUnCasSimple == false)
        {
	        this.solution.concatenerAlgorithmes(algoCorner1);
	        if(algoCorner1!=null)
		        algoCorner1.executerSurCube(this.cube);
	        /* Le corner est maintenant placé à la position 3,3,3 */

	        Orientation orientationCorner = cube.obtenirOrientationDUnePositionCouleur(new Position(3,3,3),COULEUR_D);
	        if(orientationCorner == Orientation.F)
	        {
		        algoCorner2= new Algorithme("Fp Up F");
	        }
	        else if(orientationCorner == Orientation.U)
	        {
		        algoCorner2= new Algorithme("R Up Rp U Fp U F");
	        }
	        else
	        {
		        algoCorner2= new Algorithme("Up Fp U F");
	        }

	        this.solution.concatenerAlgorithmes(algoCorner2);
	        algoCorner2.executerSurCube(this.cube);
        }


    }

/*********************************************

DEUXIÈME COURONNE

*********************************************/




    /**
    * Repère les edges pour compléter la deuxième couronne
    *@return la Position où se trouve l'edge de deuxième couronne à placer
    */
    private Position repererBelge()throws CubeException
    {
        return cube.obtenirPosition(cube.obtenirCouleurDUnePositionOrientation(new Position(3,2,2),Orientation.F),cube.obtenirCouleurDUnePositionOrientation(new Position(2,3,2),Orientation.R),Couleur.AUCUNE);
    }

    /**
    * Sort un edge présent dans la deuxième couronne mais mal placé, en le plaçant sur la face U
    *@param laPosition à laquelle se trouve l'edge "bloqué"
    */
    private void debloquerBelge(Position laPosition) throws CubeException
    {
        Algorithme debloc=null;
        if(laPosition.equals(new Position(2,1,3)))
		        debloc=new Algorithme("Up");
        else if(laPosition.equals(new Position(2,3,3)))
		        debloc=new Algorithme("U");
        else if(laPosition.equals(new Position(1,2,3)))
		        debloc=new Algorithme("U U");
        else if(laPosition.equals(new Position(1,3,2)))
		        debloc=new Algorithme("Rp Up R U B U Bp");
        else if(laPosition.equals(new Position(1,1,2)))
		        debloc=new Algorithme("L U Lp Up Bp Up B");
        else if(laPosition.equals(new Position(3,1,2)))
		        debloc=new Algorithme("Lp Up L U F U Fp U U");
        else if(laPosition.equals(new Position(3,3,2))&&!cube.obtenirCouleurDUnePositionOrientation(new Position(3,2,2),Orientation.F).equals	(cube.obtenirCouleurDUnePositionOrientation(new Position(3,3,2),Orientation.F)))
		        debloc=new Algorithme("R U Rp Up Fp Up F U U");
	        if(debloc!=null)
		        {
				        this.solution.concatenerAlgorithmes(debloc);
				        debloc.executerSurCube(cube);
		        }
    }

    /**
    * Place un edge qui appartient à la deuxième couronne à partir de sa position
    *@param laPosition La Position du Cubie avant placement
    */
    private void placerBelge(Position laPosition)throws CubeException
    {
	        Algorithme lAlgo=null;
	        debloquerBelge(laPosition);
	        if(!repererBelge().equals(new Position(3,3,2)))
		        if(cube.obtenirCouleurDUnePositionOrientation(new Position(3,2,2),Orientation.F).equals(cube.obtenirCouleurDUnePositionOrientation(new Position(3,2,3),Orientation.F)))
			        lAlgo=new Algorithme("U R Up Rp Up Fp U F");
		        else
			        lAlgo=new Algorithme("U U Fp U F U R Up Rp");
	
	        if(lAlgo!=null)
		        {
				        this.solution.concatenerAlgorithmes(lAlgo);
				        lAlgo.executerSurCube(cube);
		        }						
    }


/***********************************************

TROISIÈME COURONNE

************************************************/
  
  
    
    /**
    * Repère l'oriention (forme caractéristique) de la troisième couronne
    *@return le chaîne représentant les orientations
    */
    private String repererOrientation() throws PositionNonValideException,CubeException
    {
        String orientations="";
        Orientation o;
        for(int x=1; x<=3; x++)
        {
            for(int y=1; y<=3; y++)
            {
                o=cube.obtenirOrientationDUnePositionCouleur(new Position(x,y,3),COULEUR_U);
                orientations=orientations.concat(o.toString());
            }    
        }
        
        return orientations;     
    }
    
    /**
    * Résout le cas d'orientation dans lequel on se trouve
    *@param _orientation L'orientation à effectuer
    */
    private void resoudreOrientation(String orientations) throws PositionNonValideException,ResolutionException
    {
        int i=0;
        Algorithme u=new Algorithme("U");
        Algorithme oll=null;
        oll=baseAlgo.obtenirOLL(orientations);
        //System.err.println(orientations);
        //System.err.println(oll);
        
        while(oll==null && i<3)
        {
            u=new Algorithme("U");
            this.solution.concatenerAlgorithmes(u);
            u.executerSurCube(this.cube);
            try
                {
                    orientations=repererOrientation();
                }
            catch(CubeException e){System.err.println("Cube non valide");};
            oll=baseAlgo.obtenirOLL(orientations);
            i++;
            
            
            //System.err.println("Orientation recherchée : "+orientations);
            //System.err.println("OLL correspondant : "+oll);
            //System.err.println("Nombre de U effectués : "+i);
            
        }
        if(oll==null)
        {
            System.err.println("OLL non trouvé");
            throw new ResolutionException("OLL non résolue");
        }
        else
        {
            this.solution.concatenerAlgorithmes(oll);
            oll.executerSurCube(this.cube);
        }
        
        
    }




    private String repererPermutation() throws PositionNonValideException
    {
        StringBuilder str=new StringBuilder();
        Couleur c1 = cube.obtenirCouleurDUnePositionOrientation(new Position (3,2,2), Orientation.F);
        Couleur c2 = cube.obtenirCouleurDUnePositionOrientation(new Position (2,3,2), Orientation.R);
        Couleur c3 = cube.obtenirCouleurDUnePositionOrientation(new Position (1,2,2), Orientation.B);
        Couleur c4 = cube.obtenirCouleurDUnePositionOrientation(new Position (2,1,2), Orientation.L);
        /*System.err.println(c1);
        System.err.println(c2);
        System.err.println(c3);
        System.err.println(c4);*/

        
        for(int i=1;i<=4;i++)
            for(int j=1;j<=3;j++)
                {
                    Position position = null; 
                    Orientation orientation = null;
                    switch(i)
                        {
                        case 1 : orientation = Orientation.F; position = new Position(3,j,3);break;
                        case 2 : orientation = Orientation.R; position = new Position(4-j,3,3);break;
                        case 3 : orientation = Orientation.B; position = new Position(1,4-j,3);break;
                        case 4 : orientation = Orientation.L; position = new Position(j,1,3);break;
                        }
                    Couleur c=cube.obtenirCouleurDUnePositionOrientation(position, orientation);
                    //System.err.println(c);
                    if(c==c1)
                        str.append("F");
                    else if(c==c2)
                        str.append("R");
                    else if(c==c3)
                        str.append("B");
                    else if(c==c4)
                        str.append("L");
                }
        //System.err.println(str.toString());
        return str.toString();
    }
    
    /**
    * Résout le cas de permutation dans lequel on se trouve
    *@param _permutation La permutation à effectuer
    */
    private void resoudrePermutation(String _permutation) throws PositionNonValideException,ResolutionException
    {

        Algorithme algoPermutation=new Algorithme();
        String pllstring = _permutation;
        //System.out.println(_permutation);
        Algorithme pll = baseAlgo.obtenirPLL(pllstring);
        boolean trouve=false;
        for(int i=0;i<4;i++)
            {
                for(int j=0;j<4;j++)
                    {
                        if(!trouve)
                            {
                                algoPermutation.concatenerAlgorithmes(new Algorithme("U"));
                                (new Algorithme("U")).executerSurCube(this.cube);
                                pll = baseAlgo.obtenirPLL(repererPermutation());
                                pllstring = repererPermutation();
                                trouve=(pll!=null);
                                //System.err.println(pll);
                                //System.err.println(pllstring);
                            }
                    }
                if(!trouve)
                    {
                        algoPermutation.concatenerAlgorithmes(new Algorithme("y"));
                        (new Algorithme("y")).executerSurCube(this.cube);
                    }
            }
            
        if(pll==null)
        {
            System.err.println("pLL non trouvé : "+pllstring);
            throw new ResolutionException("PLL non résolu");
        }
        else
        {
            algoPermutation.concatenerAlgorithmes(pll);
            pll.executerSurCube(this.cube);
            this.solution.concatenerAlgorithmes(algoPermutation);
        }
    }
}

