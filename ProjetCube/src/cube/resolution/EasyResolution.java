package cube.resolution;

import cube.*;

import org.xml.sax.SAXException;
import java.io.IOException;

/**
 *classe EasyResolution
 *@author Groupe ECAO Rubik's Cube : Pierre_Bienaime Bastien_Bonnet Mathieu_Chataigner Mathieu Fresquet.
 */
public class EasyResolution implements ResolutionDuCube
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
    * Construit une EasyResolution
    *@param _cube le Cube à résoudre
    */
    public EasyResolution(Cube _cube)
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
    public Algorithme trouverSolution() throws CubeException
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
        //System.err.println(this.solution);
    }

    /**
    * Résout la troisième couronne du Cube
    */
    private void resoudreTroisiemeCouronne() throws PositionNonValideException

    {
        Algorithme setupFinal=null;

        while(repererTypeOrientation()!=TypeOrientation.RESOLUE)
        {
	        resoudreOrientation(repererTypeOrientation());
        }

        while(repererTypePermutation()!=TypePermutation.RESOLUE)
        {
	        resoudrePermutation(repererTypePermutation());
        }
        while(cube.obtenirCouleurDUnePositionOrientation(new Position (3,2,3), Orientation.F) != COULEUR_F)
        {
	        if(setupFinal == null)
		        setupFinal=new Algorithme("U");
	        else
		        setupFinal.concatenerAlgorithmes(new Algorithme("U"));
				
	        this.solution.concatenerAlgorithmes(setupFinal);
	        setupFinal.executerSurCube(this.cube);
        }		
    }

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

    /**
    * Repère le type d'oriention (forme caractéristique) de la troisième couronne
    *@return le TypeOrientation auquel on fait face
    */
    private TypeOrientation repererTypeOrientation() throws PositionNonValideException
    {
        // teste si genre croix
        if( (cube.obtenirCouleurDUnePositionOrientation(new Position (3,2,3), Orientation.U) == COULEUR_U) && (cube.obtenirCouleurDUnePositionOrientation(new Position (2,3,3), Orientation.U) == COULEUR_U) && (cube.obtenirCouleurDUnePositionOrientation(new Position (1,2,3), Orientation.U) == COULEUR_U) && (cube.obtenirCouleurDUnePositionOrientation(new Position (2,1,3), Orientation.U) == COULEUR_U) )
        {	
	        // teste si RESOLUE
	        if((cube.obtenirCouleurDUnePositionOrientation(new Position (3,1,3), Orientation.U) == COULEUR_U) && (cube.obtenirCouleurDUnePositionOrientation(new Position (3,3,3), Orientation.U) == COULEUR_U) && (cube.obtenirCouleurDUnePositionOrientation(new Position (1,3,3), Orientation.U) == COULEUR_U) && (cube.obtenirCouleurDUnePositionOrientation(new Position (1,1,3), Orientation.U) == COULEUR_U) )
		        return TypeOrientation.RESOLUE;
		
	        else
		        // teste si CROIX
		        if( (cube.obtenirCouleurDUnePositionOrientation(new Position (3,1,3), Orientation.U) != COULEUR_U) && (cube.obtenirCouleurDUnePositionOrientation(new Position (3,3,3), Orientation.U) != COULEUR_U) && (cube.obtenirCouleurDUnePositionOrientation(new Position (1,3,3), Orientation.U) != COULEUR_U) && (cube.obtenirCouleurDUnePositionOrientation(new Position (1,1,3), Orientation.U) != COULEUR_U) )
			        return TypeOrientation.CROIX;
			
		        else
			        // teste si POISSON
			        if( ((cube.obtenirCouleurDUnePositionOrientation(new Position (3,1,3), Orientation.U) == COULEUR_U) && (cube.obtenirCouleurDUnePositionOrientation(new Position (3,3,3), Orientation.U) != COULEUR_U) && (cube.obtenirCouleurDUnePositionOrientation(new Position (1,3,3), Orientation.U) != COULEUR_U) && (cube.obtenirCouleurDUnePositionOrientation(new Position (1,1,3), Orientation.U) != COULEUR_U)) ||
			
			        ((cube.obtenirCouleurDUnePositionOrientation(new Position (3,1,3), Orientation.U) != COULEUR_U) && (cube.obtenirCouleurDUnePositionOrientation(new Position (3,3,3), Orientation.U) == COULEUR_U) && (cube.obtenirCouleurDUnePositionOrientation(new Position (1,3,3), Orientation.U) != COULEUR_U) && (cube.obtenirCouleurDUnePositionOrientation(new Position (1,1,3), Orientation.U) != COULEUR_U)) ||
			
			        ((cube.obtenirCouleurDUnePositionOrientation(new Position (3,1,3), Orientation.U) != COULEUR_U) && (cube.obtenirCouleurDUnePositionOrientation(new Position (3,3,3), Orientation.U) != COULEUR_U) && (cube.obtenirCouleurDUnePositionOrientation(new Position (1,3,3), Orientation.U) == COULEUR_U) && (cube.obtenirCouleurDUnePositionOrientation(new Position (1,1,3), Orientation.U) != COULEUR_U)) ||
			
			        ((cube.obtenirCouleurDUnePositionOrientation(new Position (3,1,3), Orientation.U) != COULEUR_U) && (cube.obtenirCouleurDUnePositionOrientation(new Position (3,3,3), Orientation.U) != COULEUR_U) && (cube.obtenirCouleurDUnePositionOrientation(new Position (1,3,3), Orientation.U) != COULEUR_U) && (cube.obtenirCouleurDUnePositionOrientation(new Position (1,1,3), Orientation.U) == COULEUR_U))
			        )
				        return TypeOrientation.POISSON;
				
			        else
				        return TypeOrientation.MARTEAU_DOUBLEPOISSON;
			
        }



        else 
	        // teste si POINT
	        if( (cube.obtenirCouleurDUnePositionOrientation(new Position (3,2,3), Orientation.U) != COULEUR_U) && (cube.obtenirCouleurDUnePositionOrientation(new Position (2,3,3), Orientation.U) != COULEUR_U) && (cube.obtenirCouleurDUnePositionOrientation(new Position (1,2,3), Orientation.U) != COULEUR_U) && (cube.obtenirCouleurDUnePositionOrientation(new Position (2,1,3), Orientation.U) != COULEUR_U) )
	        {
		        return TypeOrientation.POINT;
	        }
	
	        else
		        // teste si BARRE
		        if( ((cube.obtenirCouleurDUnePositionOrientation(new Position (3,2,3), Orientation.U) == COULEUR_U) && (cube.obtenirCouleurDUnePositionOrientation(new Position (1,2,3), Orientation.U) == COULEUR_U)) || ((cube.obtenirCouleurDUnePositionOrientation(new Position (3,2,3), Orientation.U) != COULEUR_U) && (cube.obtenirCouleurDUnePositionOrientation(new Position (1,2,3), Orientation.U) != COULEUR_U)) )
		        {
			        return TypeOrientation.BARRE;
		        }
		
		        else
			        return TypeOrientation.HORLOGE;

    }

    /**
    * Résout le cas d'orientation dans lequel on se trouve
    *@param _orientation L'orientation à effectuer
    */
    private void resoudreOrientation(TypeOrientation _orientation) throws PositionNonValideException
    {
        Algorithme algoOrientation=null;
        Algorithme setupOrientation=null;
        switch(_orientation)
        {
	        case POINT:
	        {
		        //algoOrientation=new Algorithme("F R U Rp Up Fp f R U Rp Up fp");
		        algoOrientation=baseAlgo.obtenirOLL(2);
	        }
	        break;
	
	        case BARRE:
	        {
		        if(cube.obtenirCouleurDUnePositionOrientation(new Position (3,2,3), Orientation.U) == COULEUR_U)
		        {
			        //algoOrientation=new Algorithme("U F R U Rp Up Fp");
			        algoOrientation=new Algorithme("U");
			        algoOrientation.concatenerAlgorithmes(baseAlgo.obtenirOLL(21));
                }
		        else
			        algoOrientation=baseAlgo.obtenirOLL(21);
	        }
	        break;
	
	        case HORLOGE:
	        {
		        if( (cube.obtenirCouleurDUnePositionOrientation(new Position (3,2,3), Orientation.U) == COULEUR_U) && (cube.obtenirCouleurDUnePositionOrientation(new Position (2,1,3), Orientation.U) == COULEUR_U) )
		        {
			        //algoOrientation=new Algorithme("Up f R U Rp Up fp");
			        algoOrientation=new Algorithme("Up");
			        algoOrientation.concatenerAlgorithmes(baseAlgo.obtenirOLL(43));
		        }
		        else if( (cube.obtenirCouleurDUnePositionOrientation(new Position (3,2,3), Orientation.U) == COULEUR_U) && (cube.obtenirCouleurDUnePositionOrientation(new Position (2,3,3), Orientation.U) == COULEUR_U) )
		        {
			        //algoOrientation=new Algorithme("f R U Rp Up fp");
			        algoOrientation=baseAlgo.obtenirOLL(43);
		        }
		        else if( (cube.obtenirCouleurDUnePositionOrientation(new Position (1,2,3), Orientation.U) == COULEUR_U) && (cube.obtenirCouleurDUnePositionOrientation(new Position (2,3,3), Orientation.U) == COULEUR_U) )
		        {
			        //algoOrientation=new Algorithme("U f R U Rp Up fp");
			        algoOrientation=new Algorithme("U");
			        algoOrientation.concatenerAlgorithmes(baseAlgo.obtenirOLL(43));
		        }	
		        else
		        {
			        //algoOrientation=new Algorithme("U U f R U Rp Up fp");
			        algoOrientation=new Algorithme("U U");
			        algoOrientation.concatenerAlgorithmes(baseAlgo.obtenirOLL(43));
                }
                	        				    
	        }
	        break;
	
	        case CROIX:
	        {				
		        while( !(cube.obtenirCouleurDUnePositionOrientation(new Position (3,1,3), Orientation.F) != COULEUR_U) && (cube.obtenirCouleurDUnePositionOrientation(new Position (3,3,3), Orientation.F) != COULEUR_U) )
		        {
			        if(setupOrientation == null)
				        setupOrientation=new Algorithme("U");
			        else
				        setupOrientation.concatenerAlgorithmes(new Algorithme("U"));
			
			        this.solution.concatenerAlgorithmes(setupOrientation);
			        setupOrientation.executerSurCube(this.cube);
		        }
			        //algoOrientation=new Algorithme("R U Rp U R U U Rp");
			        algoOrientation=baseAlgo.obtenirOLL(56);
	        }
	        break;	
	
	        case MARTEAU_DOUBLEPOISSON:
	        {
		        while( cube.obtenirCouleurDUnePositionOrientation(new Position (3,1,3), Orientation.F) != COULEUR_U )
		        {
			        if(setupOrientation == null)
				        setupOrientation=new Algorithme("U");
			        else
				        setupOrientation.concatenerAlgorithmes(new Algorithme("U"));
			
			        this.solution.concatenerAlgorithmes(setupOrientation);
			        setupOrientation.executerSurCube(this.cube);
		        }
			        //algoOrientation=new Algorithme("R U Rp U R U U Rp");
			        algoOrientation=baseAlgo.obtenirOLL(56);
	        }
	        break;
	
	        case POISSON:
	        {
		        while( cube.obtenirCouleurDUnePositionOrientation(new Position (3,3,3), Orientation.U) != COULEUR_U )
		        {
			        if(setupOrientation == null)
				        setupOrientation=new Algorithme("U");
			        else
				        setupOrientation.concatenerAlgorithmes(new Algorithme("U"));
			
			        this.solution.concatenerAlgorithmes(setupOrientation);
			        setupOrientation.executerSurCube(this.cube);
		        }
		        if( cube.obtenirCouleurDUnePositionOrientation(new Position (3,1,3), Orientation.F) == COULEUR_U )
		        {	
				        //algoOrientation=new Algorithme("Lp Up L Up Lp U U L");
				        algoOrientation=baseAlgo.obtenirOLL(55);
                }
		        else
                {
				        //algoOrientation=new Algorithme("U R U Rp U R U U Rp");
				        algoOrientation=new Algorithme("U");
				        algoOrientation.concatenerAlgorithmes(baseAlgo.obtenirOLL(56));
		        }
	        }	
	        break;
	
	        default :
		        algoOrientation=null;
	        break;

        }
        this.solution.concatenerAlgorithmes(algoOrientation);
        algoOrientation.executerSurCube(this.cube);
    }


    /**
    * Repère le type de permutation (forme caractéristique) de la troisième couronne
    *@return le type de permutation auquel on fait face
    */
    private TypePermutation repererTypePermutation() throws PositionNonValideException
    {
        if( (cube.obtenirCouleurDUnePositionOrientation(new Position (3,1,3), Orientation.F) == (cube.obtenirCouleurDUnePositionOrientation(new Position (3,3,3), Orientation.F))) && (cube.obtenirCouleurDUnePositionOrientation(new Position (3,3,3), Orientation.R) == (cube.obtenirCouleurDUnePositionOrientation(new Position (1,3,3), Orientation.R)))  )
	        if( (cube.obtenirCouleurDUnePositionOrientation(new Position (3,2,3), Orientation.F) == (cube.obtenirCouleurDUnePositionOrientation(new Position (3,1,3), Orientation.F))) && 
	        (cube.obtenirCouleurDUnePositionOrientation(new Position (2,3,3), Orientation.R) == (cube.obtenirCouleurDUnePositionOrientation(new Position (3,3,3), Orientation.R))) )
		        return TypePermutation.RESOLUE;
	        else
		        return TypePermutation.EDGES;
        else
	        return TypePermutation.CORNERS;	
    }
    
    /**
    * Résout le cas de permutation dans lequel on se trouve
    *@param _permutation La permutation à effectuer
    */
    private void resoudrePermutation(TypePermutation _permutation) throws PositionNonValideException
    {
        Algorithme algoPermutation=null;
        switch(_permutation)
        {
	        case EDGES:
	        /* On est dans les cas où les corners sont tous bien permutés, seulement des edges à permuter */
	        {
	            /* Teste si 3-cycle sens anti-trigo càd algo PLL 1 */
		        if( (cube.obtenirCouleurDUnePositionOrientation(new Position (3,2,3), Orientation.F) != cube.obtenirCouleurDUnePositionOrientation(new Position (3,1,3), Orientation.F)) &&
		        (cube.obtenirCouleurDUnePositionOrientation(new Position (2,3,3), Orientation.R) != cube.obtenirCouleurDUnePositionOrientation(new Position (3,3,3), Orientation.R)) &&
		        (cube.obtenirCouleurDUnePositionOrientation(new Position (1,2,3), Orientation.B) != cube.obtenirCouleurDUnePositionOrientation(new Position (1,3,3), Orientation.B)) &&
		        (cube.obtenirCouleurDUnePositionOrientation(new Position (2,1,3), Orientation.L) != cube.obtenirCouleurDUnePositionOrientation(new Position (1,1,3), Orientation.L)) )
		        {   
			        //algoPermutation=new Algorithme("R R U R U Rp Up Rp Up Rp U Rp");
                    algoPermutation=baseAlgo.obtenirPLL(1);
		        }		        
		        else
		            /* Si côté plein en F */
			        if (cube.obtenirCouleurDUnePositionOrientation(new Position (3,2,3), Orientation.F) == cube.obtenirCouleurDUnePositionOrientation(new Position (3,1,3), Orientation.F))
			        {
			            algoPermutation=new Algorithme("U U");
			            /* Si PLL 1*/
				        if((cube.obtenirCouleurDUnePositionOrientation(new Position (1,2,3), Orientation.B) == cube.obtenirCouleurDUnePositionOrientation(new Position (1,3,3), Orientation.R)))
				        {
					        //algoPermutation=new Algorithme("U U R R U R U Rp Up Rp Up Rp U Rp");
			                algoPermutation.concatenerAlgorithmes(baseAlgo.obtenirPLL(1));
		                }
		                /* Sinon PLL 2 */
				        else
				        {
					        //algoPermutation=new Algorithme("U U R Up R U R U R Up Rp Up R R");
					        algoPermutation.concatenerAlgorithmes(baseAlgo.obtenirPLL(2));
				        }
			        }
			        /* Sinon si côté plein en L */
			        else if(cube.obtenirCouleurDUnePositionOrientation(new Position (2,1,3), Orientation.L) == cube.obtenirCouleurDUnePositionOrientation(new Position (1,1,3), Orientation.L))
			        {
			        algoPermutation=new Algorithme("U");
			        
			            /* Si PLL 1 */
				        if((cube.obtenirCouleurDUnePositionOrientation(new Position (2,3,3), Orientation.R) == cube.obtenirCouleurDUnePositionOrientation(new Position (3,3,3), Orientation.F)))
				        {
					        //algoPermutation=new Algorithme("U R R U R U Rp Up Rp Up Rp U Rp");
					        algoPermutation.concatenerAlgorithmes(baseAlgo.obtenirPLL(1));
				        }
				        /* Sinon PLL 2 */
				        else
				        {
					        //algoPermutation=new Algorithme("U R Up R U R U R Up Rp Up R R");
					        algoPermutation.concatenerAlgorithmes(baseAlgo.obtenirPLL(2));
				        }
			        }
			        /* Sinon si côté plein en B */
			        else if(cube.obtenirCouleurDUnePositionOrientation(new Position (1,2,3), Orientation.B) == cube.obtenirCouleurDUnePositionOrientation(new Position (1,3,3), Orientation.B))
			        {
			            /* Si PLL 1*/
				        if((cube.obtenirCouleurDUnePositionOrientation(new Position (3,2,3), Orientation.F) == cube.obtenirCouleurDUnePositionOrientation(new Position (3,1,3), Orientation.L)))
				        {
					        //algoPermutation=new Algorithme("R R U R U Rp Up Rp Up Rp U Rp");
					        algoPermutation=baseAlgo.obtenirPLL(1);
				        }
				        /* Sinon PLL 2*/
				        else
				        {
					        //algoPermutation=new Algorithme("R Up R U R U R Up Rp Up R R");
					        algoPermutation=baseAlgo.obtenirPLL(2);
				        }
			        }
			        /* Sinon il ne reste que côté plein en R possible ou alors il y avait 4 edges mal permutés */
			        else
	                {
	                    algoPermutation=new Algorithme("Up");
	                    /* Si PLL 1 */
				        if((cube.obtenirCouleurDUnePositionOrientation(new Position (2,1,3), Orientation.L) == cube.obtenirCouleurDUnePositionOrientation(new Position (1,1,3), Orientation.B)))
				        {
					        //algoPermutation=new Algorithme("Up R R U R U Rp Up Rp Up Rp U Rp");
					        algoPermutation.concatenerAlgorithmes(baseAlgo.obtenirPLL(1));
				        }
				        /* Sinon PLL 2*/
				        else
				        {
					        //algoPermutation=new Algorithme("Up R Up R U R U R Up Rp Up R R");
					        algoPermutation.concatenerAlgorithmes(baseAlgo.obtenirPLL(2));
				        }
			        }
					
	        }
	        break;
	
	        case CORNERS:
	        {
	            /* Si 2 corners à permuter en diagonale càd PLL 12 */
		        if( (cube.obtenirCouleurDUnePositionOrientation(new Position (3,1,3), Orientation.F) != (cube.obtenirCouleurDUnePositionOrientation(new Position (3,3,3), Orientation.F)))
		        && (cube.obtenirCouleurDUnePositionOrientation(new Position (3,3,3), Orientation.R) != (cube.obtenirCouleurDUnePositionOrientation(new Position (1,3,3), Orientation.R)))
		        && (cube.obtenirCouleurDUnePositionOrientation(new Position (3,1,3), Orientation.L) != (cube.obtenirCouleurDUnePositionOrientation(new Position (1,1,3), Orientation.L))) 
		        && (cube.obtenirCouleurDUnePositionOrientation(new Position (1,1,3), Orientation.B) != (cube.obtenirCouleurDUnePositionOrientation(new Position (1,3,3), Orientation.B))) )
		          	algoPermutation=baseAlgo.obtenirPLL(12);
		          	
	          	/* Sinon c'est qu'il y a 2 corners adjacents à permuter */
		        else
		        {
		            /* Si les 2 corners bien placés sont en F */
			        if (cube.obtenirCouleurDUnePositionOrientation(new Position (3,1,3), Orientation.F) == (cube.obtenirCouleurDUnePositionOrientation(new Position (3,3,3), Orientation.F)))
			        {
			           //algoPermutation=new Algorithme("Up F F R U Rp F F L Dp L D L L");
			            algoPermutation=new Algorithme("Up");
			            algoPermutation.concatenerAlgorithmes(baseAlgo.obtenirPLL(8));
			        }
			        /* Sinon si les 2 corners bien placés sont en R */
			        else if (cube.obtenirCouleurDUnePositionOrientation(new Position (3,3,3), Orientation.R) == (cube.obtenirCouleurDUnePositionOrientation(new Position (1,3,3), Orientation.R)))
			        {
                                    algoPermutation=baseAlgo.obtenirPLL(8);
			        }
			        /* Sinon si les 2 corners bien placés sont en L */
			        else if(cube.obtenirCouleurDUnePositionOrientation(new Position (3,1,3), Orientation.L) == (cube.obtenirCouleurDUnePositionOrientation(new Position (1,1,3), Orientation.L)))
			        {
	                    algoPermutation=baseAlgo.obtenirPLL(7);
			        }
			        /* Sinon les 2 corners bien placés sont forcément  en B */
			        else
			        {
			            algoPermutation=new Algorithme("U");
			            algoPermutation.concatenerAlgorithmes(baseAlgo.obtenirPLL(8));
			        }
		        }				
	        }
	        break;
        }
        this.solution.concatenerAlgorithmes(algoPermutation);
        algoPermutation.executerSurCube(this.cube);
    }
}

