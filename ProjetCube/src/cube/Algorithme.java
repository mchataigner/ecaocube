/**
 * Classe qui stocke des mouvements elementaires et qui permet de realiser des operations sur ces mouvements elementaires
 * @author Groupe ECAO Rubik's Cube: Bienaime Bonnet Chataigner Fresquet.
 */

package cube;

import java.util.*;
import cube.MouvementElementaire.*;
public class Algorithme
{	
    private ArrayList<MouvementElementaire> solution;
    private Integer MAX_VALUE=10000;
	private String dernierMvt="";
	private int dernierIndice=-1;
    /**
     * Constructeur de la classe Algorithme
     */	
    public Algorithme()
    {
		solution = new ArrayList<MouvementElementaire>();
		/*dernierIndice=solution.size()-1;
		if(dernierIndice>=0)
			dernierMvt=solution.get(dernierIndice).toString();
		else
			dernierMvt="";*/
    }

    /**
     * Constructeur de la classe Algorithme a partir de plusieurs mouvements elemntaires
     */
    public Algorithme(MouvementElementaire... mouvements)
    {
		this();
		this.ajouterMouvements(mouvements);	
    }

	public Algorithme(Algorithme algo){
		this();
		this.ajouterMouvements(algo.getAlgorithme());
	}

    /**
     * Constructeur de la classe Algorithme a partir d'une suite de chaines de caracteres représentants un algorithme
     */
    public Algorithme(String chaineAlgo)
    {
		this();
		MouvementElementaire mouvement;
		//System.out.println("\""+chaineAlgo+"\"");
		if(chaineAlgo.trim().length()!=0){
			String[] tableauChaineAlgo=chaineAlgo.trim().split(" ");
			//System.out.println(tableauChaineAlgo.length);
			for(int i=0;i<=tableauChaineAlgo.length-1;i++)
			{
				try
				{
					//System.out.println("\""+tableauChaineAlgo[i]+"\"");
					mouvement=MouvementElementaire.valueOf(tableauChaineAlgo[i]);
					this.ajouterMouvement(mouvement);
				}
				catch(IllegalArgumentException e)
				{
					System.err.println("Tentative de construire un algorithme avec une chaine de caractere non valide");
					e.printStackTrace();
				}	
			}
		}
		//System.out.println("\""+this.toString()+"\"");
    }


    public Algorithme inverserAlgorithme()
    {
        Algorithme algo=null;
        ArrayList<MouvementElementaire> listeMvt = new ArrayList<MouvementElementaire>();
        
        for(MouvementElementaire mvt: solution)
            {
                switch(mvt)
                    {
                    case R : listeMvt.add(0,MouvementElementaire.Rp);break;
                    case R2: listeMvt.add(0,MouvementElementaire.R2);break;
                    case Rp: listeMvt.add(0,MouvementElementaire.R );break;
                    case r : listeMvt.add(0,MouvementElementaire.rp);break;
                    case r2: listeMvt.add(0,MouvementElementaire.r2);break;
                    case rp: listeMvt.add(0,MouvementElementaire.r );break;
                    case U : listeMvt.add(0,MouvementElementaire.Up);break;
                    case U2: listeMvt.add(0,MouvementElementaire.U2);break;
                    case Up: listeMvt.add(0,MouvementElementaire.U );break;
                    case u : listeMvt.add(0,MouvementElementaire.up);break;
                    case u2: listeMvt.add(0,MouvementElementaire.u2);break;
                    case up: listeMvt.add(0,MouvementElementaire.u );break;
                    case L : listeMvt.add(0,MouvementElementaire.Lp);break;
                    case L2: listeMvt.add(0,MouvementElementaire.L2);break;
                    case Lp: listeMvt.add(0,MouvementElementaire.L );break;
                    case l : listeMvt.add(0,MouvementElementaire.lp);break;
                    case l2: listeMvt.add(0,MouvementElementaire.l2);break;
                    case lp: listeMvt.add(0,MouvementElementaire.l );break;
                    case D : listeMvt.add(0,MouvementElementaire.Dp);break;
                    case D2: listeMvt.add(0,MouvementElementaire.D2);break;
                    case Dp: listeMvt.add(0,MouvementElementaire.D );break;
                    case d : listeMvt.add(0,MouvementElementaire.dp);break;
                    case d2: listeMvt.add(0,MouvementElementaire.d2);break;
                    case dp: listeMvt.add(0,MouvementElementaire.d );break;
                    case B : listeMvt.add(0,MouvementElementaire.Bp);break;
                    case B2: listeMvt.add(0,MouvementElementaire.B2);break;
                    case Bp: listeMvt.add(0,MouvementElementaire.B );break;
                    case b : listeMvt.add(0,MouvementElementaire.bp);break;
                    case b2: listeMvt.add(0,MouvementElementaire.b2);break;
                    case bp: listeMvt.add(0,MouvementElementaire.b );break;
                    case F : listeMvt.add(0,MouvementElementaire.Fp);break;
                    case F2: listeMvt.add(0,MouvementElementaire.F2);break;
                    case Fp: listeMvt.add(0,MouvementElementaire.F );break;
                    case f : listeMvt.add(0,MouvementElementaire.fp);break;
                    case f2: listeMvt.add(0,MouvementElementaire.f2);break;
                    case fp: listeMvt.add(0,MouvementElementaire.f );break;
                    case M : listeMvt.add(0,MouvementElementaire.Mp);break;
                    case M2: listeMvt.add(0,MouvementElementaire.M2);break;
                    case Mp: listeMvt.add(0,MouvementElementaire.M );break;
                    case E : listeMvt.add(0,MouvementElementaire.Ep);break;
                    case E2: listeMvt.add(0,MouvementElementaire.E2);break;
                    case Ep: listeMvt.add(0,MouvementElementaire.E );break;
                    case S : listeMvt.add(0,MouvementElementaire.Sp);break;
                    case S2: listeMvt.add(0,MouvementElementaire.S2);break;
                    case Sp: listeMvt.add(0,MouvementElementaire.S );break;
                    case x : listeMvt.add(0,MouvementElementaire.xp);break;
                    case x2: listeMvt.add(0,MouvementElementaire.x2);break;
                    case xp: listeMvt.add(0,MouvementElementaire.x );break;
                    case y : listeMvt.add(0,MouvementElementaire.yp);break;
                    case y2: listeMvt.add(0,MouvementElementaire.y2);break;
                    case yp: listeMvt.add(0,MouvementElementaire.y );break;
                    case z : listeMvt.add(0,MouvementElementaire.zp);break;
                    case z2: listeMvt.add(0,MouvementElementaire.z2);break;
                    case zp: listeMvt.add(0,MouvementElementaire.z );break;
                    }
            }
        algo = new Algorithme(listeMvt.toArray(new MouvementElementaire[0]));
        return algo;
    }

    /**
     * methode permettant d'executer un Algorithme sur un Cube
     * @param cube le Cube sur lequel nous souhaitons effectuer des algorithmes
     */
    public void executerSurCube(Cube cube)
    {
	if(!estVide())
	    for(Iterator i = solution.iterator();i.hasNext();)
		{
		    cube.effectuerMouvementElementaire((MouvementElementaire)i.next());			
		}
    }
    
    
    public void executerSurCubeOpenGL(Cube cube)
    {
	Scanner sca = new Scanner(System.in);
	if(!estVide())
	    for(Iterator i = solution.iterator();i.hasNext();)
		{
		    System.out.println("Avancement=  %");
		    System.out.println("Souhaitez vous continuer? (o/n)");
		    String continuer = sca.nextLine();
		    if(continuer.equalsIgnoreCase("o") || continuer.equalsIgnoreCase("O"))
			{
			    cube.effectuerMouvementElementaire((MouvementElementaire)i.next());
			}	
			    		
		}
    }
    
    public MouvementElementaire obtenirIEmeMouvement(int i)
    {
    	if(i<solution.size())
	    {
		return solution.get(i);
	    }
	else
	    return null;
    }
    
    public Algorithme copier()
    {
    	Algorithme alg = new Algorithme(this);
    	return alg;
    }
    
    

    /**
     * methode permettant de savoir si un Algorithme est vide.
     * @return un booleen qui indique si l'algorithme est vide ou non.
     */
    public boolean estVide()
    {
	return this.solution.isEmpty();
    }

    /**
     * methode permettant de connaitre la taille de l'Algorithme, c'est a dire le nombre de mouvements elementaires qu'il contient
     * @return un int qui represente la taille de l'Algorithme
     */
    public int taille()
    {
	return this.solution.size();
    }

    /**
     * methode permettant d'obtenir le premier mouvement elementaire d'un algorithme
     * @return le premier mouvement elementaire de l'algorithme
     */
    /* ATTENTION: METHODE PROBABLEMENT INUTILE
       public MouvementElementaire obtenirPremierMouvement()
       {
       return this.solution.peek();
       }
    */


    /**
     * methode permettant d'ajouter un mouvement elementaire a notre algorithme
     * @return un booleen qui indique si l'ajout s'est bien deroule
     */
    public synchronized boolean ajouterMouvement(MouvementElementaire mouvement)
    {
		boolean reussi=false;
		//System.out.print(dernierMvt+" ");
		
		if(dernierIndice>=0 && dernierMvt.length()!=0){
			//MouvementElementaire mvt=this.solution.get(dernierIndice);
			//String dernierMvt=mvt.toString();
			String leMvt=mouvement.toString();
			int i=0,j=0;
			
			
			if(dernierMvt.charAt(0)==leMvt.charAt(0)){
				if(dernierMvt.length()==1)
					i=1;
				else if(dernierMvt.charAt(1)=='2')
					i=2;
				else if(dernierMvt.charAt(1)=='p')
					i=3;
						
				if(leMvt.length()==1)
					j=1;
				else if(leMvt.charAt(1)=='2')
					j=2;
				else if(leMvt.charAt(1)=='p')
					j=3;
					
					
				i=(i+j)%4;
				
				if(i==1)
					dernierMvt=""+dernierMvt.substring(0,1);
				else if(i==2)
					dernierMvt=""+dernierMvt.substring(0,1)+"2";
				else if(i==3)
					dernierMvt=""+dernierMvt.substring(0,1)+"p";
				else if(i==0)
					{
						dernierMvt="";
						this.solution.remove(dernierIndice);
						dernierIndice--;
						if(dernierIndice>=0){
							dernierMvt=solution.get(dernierIndice).toString();
						}
						return true;
					}
				if(dernierMvt.length()!=0){
					this.solution.set(dernierIndice,MouvementElementaire.valueOf(dernierMvt));
					reussi=true;
					//System.out.println("\""+MouvementElementaire.valueOf(dernierMvt)+"\"");
				}
				else{
					dernierMvt="";
					this.solution.remove(dernierIndice);
					dernierIndice--;
					if(dernierIndice>=0){
						dernierMvt=solution.get(dernierIndice).toString();
					}
					return true;
				}
				
				return reussi;
				}
			}
		reussi=this.solution.add(mouvement);
		dernierIndice++;
		dernierMvt=mouvement.toString();

		//System.out.println(reussi);
		return reussi;
    }


    /**
     * methode permettant d'ajouter une suite de mouvements elementaires a notre algorithme
     * @return un booleen qui indique si l'ajout s'est bien deroule
     */
    public boolean ajouterMouvements(MouvementElementaire... mouvements)
    {
		List<MouvementElementaire> liste= Arrays.asList(mouvements);
		return ajouterMouvements(liste);
    }

	public boolean ajouterMouvements(List<MouvementElementaire> liste){
		boolean reussi=true;
		for(MouvementElementaire i:liste)
			if(!ajouterMouvement(i))
				reussi=false;
		return reussi;
	}

    /**
     * methode permettant de retirer le premier mouvement elementaire d'un algorithme
     * @return le mouvement elementaire que nous venons de retirer, ou null si le retrait est impossible 
     */
    /* ATTENTION: METHODE PROBABLEMENT INUTILE
       public MouvementElementaire retirerMouvement();
       {
       return this.solution.poll();
       }
    */

    /**
     * methode permettant de concatener un algorithme a l'agorithme courant
     * @return un booleen qui indique si des modifications ont ete apportee a l'algorithme courant
     */
    public boolean concatenerAlgorithmes(Algorithme algo)
    {
		if(algo!=null)
			return ajouterMouvements(algo.getAlgorithme());
		else
			return false;
    }

    /**
     * methode permettant de recuperer la suite de mouvements elementaires contenus dans l'algorithme.
     * @return un booleen qui indique si des modifications ont ete apportee a l'algorithme courant
     */
    public ArrayList<MouvementElementaire> getAlgorithme()
    {
		return this.solution;
    }


    /**
     * methode permettant d'afficher une chaine de caractere representant l'algorithme
     * @return la chaine de caractere qui correspond a l'agorithme
     */
    public String toString()
    {
	StringBuilder solutionEnTexte=new StringBuilder();
	for(Iterator i = solution.iterator();i.hasNext();)
	    {
		solutionEnTexte.append(((MouvementElementaire)i.next()).toString());	
		solutionEnTexte.append(" ");	
	    }
	return solutionEnTexte.toString();
    }
}

