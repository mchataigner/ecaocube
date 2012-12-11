
/**
 *classe transformant un mouvement humain en mouvement robot
 *@author Groupe ECAO Rubik's Cube : Pierre_Bienaime Bastien_Bonnet Mathieu_Chataigner Mathieu Fresquet.
 */


package cube.robot;

import java.util.ArrayList;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import lejos.nxt.*;

import java.io.IOException;
import java.io.DataInputStream;



public class RobotRecepteur extends Thread
{


	private String [] Rarray ={"B1","A1"};		private String [] Rnarray ={"B2","A1","B1"};
	private String [] Larray ={"B3","A1"};		private String [] Lnarray ={"B2","A1","B3"};
	private String [] Uarray ={"A1","A1"};		private String [] Unarray ={"A1","A1"};
	private String [] Darray ={};				private String [] Dnarray ={};
	private String [] Farray ={"B2","A1"};		private String [] Fnarray ={"B2","A1"};
	private String [] Barray ={"A1"};			private String [] Bnarray ={"A1","A1","A1"};
	private String [] rarray ={"B3","A1"};		private String [] rnarray ={"B1","A1","B3"};
	private String [] larray ={"B1","A1"};		private String [] lnarray ={"B1","A1","B1"};
	private String [] uarray ={};				private String [] unarray ={"B3"};
	private String [] darray ={"A1","A1"};		private String [] dnarray ={"A1","A1","B1"};
	private String [] farray ={"A1"};			private String [] fnarray ={"B1","A1","B2"};
	private String [] barray ={"B2","A1"};		private String [] bnarray ={"B1","A1"};
	private String [] rparray={"B3","A1"};		private String [] rpnarray={"B3","A1","B3"};
	private String [] lparray={"B1","A1"};		private String [] lpnarray={"B3","A1","B1"};
	private String [] uparray={};				private String [] upnarray={"B1"};
	private String [] dparray={"A1","A1"};		private String [] dpnarray={"A1","A1","B3"};
	private String [] fparray={"A1"};        	private String [] fpnarray={"B3","A1"};
	private String [] bparray={"B2","A1"};		private String [] bpnarray={"B3","A1"};
	private String [] r2array={"B3","A1"};		private String [] r2narray={"A1","B3"};
	private String [] l2array={"B1","A1"};		private String [] l2narray={"A1","B1"};
	private String [] u2array={};				private String [] u2narray={"B2"};
	private String [] d2array={"A1","A1"};		private String [] d2narray={"A1","A1","B2"};
	private String [] f2array={"A1"};			private String [] f2narray={"A1","B2"};
	private String [] b2array={"B2","A1"};		private String [] b2narray={"A1"};

	private String [] x={"A1"};
	private String [] y={"B3"};
	private String [] z={"B1","A1","B3"};
	private String [] x2={"A1","A1"};
	private String [] y2={"B2"};
	private String [] z2={"B1","A1","A1","B3"};
	private String [] xp={"A1","A1","A1"};
	private String [] yp={"B1"};
	private String [] zp={"B3","A1","B1"};


    // private String [] Rarray; private String [] Rnarray;
    // private String [] Larray; private String [] Lnarray;
    // private String [] Uarray; private String [] Unarray;
    // private String [] Darray; private String [] Dnarray;
    // private String [] Farray; private String [] Fnarray;
    // private String [] Barray; private String [] Bnarray;

    // private String [] rarray; private String [] rnarray;
    // private String [] larray; private String [] lnarray;
    // private String [] uarray; private String [] unarray;
    // private String [] darray; private String [] dnarray;
    // private String [] farray; private String [] fnarray;
    // private String [] barray; private String [] bnarray;

    // private String [] r2array; private String [] r2narray;
    // private String [] l2array; private String [] l2narray;
    // private String [] u2array; private String [] u2narray;
    // private String [] d2array; private String [] d2narray;
    // private String [] f2array; private String [] f2narray;
    // private String [] b2array; private String [] b2narray;

    // private String [] rparray; private String [] rpnarray;
    // private String [] lparray; private String [] lpnarray;
    // private String [] uparray; private String [] upnarray;
    // private String [] dparray; private String [] dpnarray;
    // private String [] fparray; private String [] fpnarray;
    // private String [] bparray; private String [] bpnarray;
	
    // private String [] x; private String [] xp; private String [] x2;
    // private String [] y; private String [] yp; private String [] y2;
    // private String [] z; private String [] zp; private String [] z2;
	
	
    private ArrayList<String>  previousSetup=new ArrayList<String>();
    private ArrayList<String>  nextSetup=new ArrayList<String>();
    private ArrayList<String>  currentSetup=new ArrayList<String>();

    private DataInputStream din;
    private DataOutputStream dout;
    private RobotRubik alphonse;
    private boolean quitter=false;
    private boolean fake=false;

	
    private char mvt;
    private int indice=0;
	
    /**
    * Construit le robotrecepteur, liaison entre robot et pc
    *@param _din flux de donnée entrant
    *@param _dout flux de donnée sortant
    *@param _alphonse le nom désignant le robot
    */
    public RobotRecepteur(DataInputStream _din,DataOutputStream _dout,RobotRubik _alphonse){
	this.din=_din;
	this.dout=_dout;
	this.alphonse=_alphonse;
	init();
    }


    public void init(){
    }

    /**
    * Clos la liaison
    */
    public void quitter(){
	this.quitter=true;
    }

    /**
    * Initialise la connection et effectue la transformation de mouvement
    */
    public void run(){
	int reussi=0;
	while(!quitter){
	    try{
		reussi=0;
		mvt=din.readChar();
		indice=din.readInt();
		if(mvt!=0){
		    LCD.drawString(""+mvt+indice,0,8);
		    switch(mvt){
		    case 'Q': quitter();reussi=1;break;
		    case 'q': quitter();reussi=1;break;
		    case 'R':effectuerMvtRobot(Rarray,Rnarray,indice);reussi=1;break;
		    case 'L':effectuerMvtRobot(Larray,Lnarray,indice);reussi=1;break;
		    case 'F':effectuerMvtRobot(Farray,Fnarray,indice);reussi=1;break;
		    case 'B':effectuerMvtRobot(Barray,Bnarray,indice);reussi=1;break;
		    case 'U':effectuerMvtRobot(Uarray,Unarray,indice);reussi=1;break;
		    case 'D':effectuerMvtRobot(Darray,Dnarray,indice);reussi=1;break;
		    
		    case 'r':
			switch(indice){
			case 1:effectuerMvtRobot(rarray,rnarray,indice);reussi=1;break;
			case 2:effectuerMvtRobot(r2array,r2narray,indice);reussi=1;break;
			case 3:effectuerMvtRobot(rparray,rpnarray,indice);reussi=1;break;
			};break;
		    case 'l':
			switch(indice){
			case 1:effectuerMvtRobot(larray,lnarray,indice);reussi=1;break;
			case 2:effectuerMvtRobot(l2array,l2narray,indice);reussi=1;break;
			case 3:effectuerMvtRobot(lparray,lpnarray,indice);reussi=1;break;
			};break;
		    case 'f':
			switch(indice){
			case 1:effectuerMvtRobot(farray,fnarray,indice);reussi=1;break;
			case 2:effectuerMvtRobot(f2array,f2narray,indice);reussi=1;break;
			case 3:effectuerMvtRobot(fparray,fpnarray,indice);reussi=1;break;
			};break;
		    case 'b':
			switch(indice){
			case 1:effectuerMvtRobot(barray,bnarray,indice);reussi=1;break;
			case 2:effectuerMvtRobot(b2array,b2narray,indice);reussi=1;break;
			case 3:effectuerMvtRobot(bparray,bpnarray,indice);reussi=1;break;
			};break;
		    case 'u':
			switch(indice){
			case 1:effectuerMvtRobot(uarray,unarray,indice);reussi=1;break;
			case 2:effectuerMvtRobot(u2array,u2narray,indice);reussi=1;break;
			case 3:effectuerMvtRobot(uparray,upnarray,indice);reussi=1;break;
			};break;
		    case 'd':
			switch(indice){
			case 1:effectuerMvtRobot(darray,dnarray,indice);reussi=1;break;
			case 2:effectuerMvtRobot(d2array,d2narray,indice);reussi=1;break;
			case 3:effectuerMvtRobot(dparray,dpnarray,indice);reussi=1;break;
			};break;
		    
		    case 'x':
			switch(indice){
			case 1:effectuerMvtRobotSimple(x);reussi=1;break;
			case 2:effectuerMvtRobotSimple(x2);reussi=1;break;
			case 3:effectuerMvtRobotSimple(xp);reussi=1;break;
			};break;
		    case 'y':
			switch(indice){
			case 1:effectuerMvtRobotSimple(y);reussi=1;break;
			case 2:effectuerMvtRobotSimple(y2);reussi=1;break;
			case 3:effectuerMvtRobotSimple(yp);reussi=1;break;
			};break;
		    case 'z':
			switch(indice){
			case 1:effectuerMvtRobotSimple(z);reussi=1;break;
			case 2:effectuerMvtRobotSimple(z2);reussi=1;break;
			case 3:effectuerMvtRobotSimple(zp);reussi=1;break;
			};break;
		    default:break;
		    }
		    LCD.drawString("   ",0,6);

		    dout.writeInt(reussi);
		    dout.flush();
		}
		else
		    {
			dout.writeInt(1);
			dout.flush();
			System.exit(1);
		    }
	    }
	    catch(Throwable e){
		try{
		    dout.writeInt(0);
		    dout.flush();
		}
		catch (Throwable er){};
	    }
	}
    }
	
    /**
    * Effectue la rotation pour un mouvement robot
    *@param i valeur du mouvement robot
    */
    public void effectuerRotation(String i){
	int j=0;
	if(fake)
	    {
		if(i.charAt(0)=='A')
		    LCD.drawString(i+" ",0,6);
		else if(i.charAt(0)=='B')
		    LCD.drawString(i,0,6);
		else if(i.charAt(0)=='C')
		    LCD.drawString(i,0,6);
		
		try{
		    Thread.sleep(600);
		}
		catch(Throwable e){}
	    }
	else
	    {
		if(i.charAt(0)=='A'){
			j=Integer.parseInt(i.substring(1));
		    alphonse.faireA(j);
		}
		else if(i.charAt(0)=='B'){
			j=Integer.parseInt(i.substring(1));
		    alphonse.faireB(j);
		}
		else if(i.charAt(0)=='C'){
		    j=new Integer(i.substring(1));
		    alphonse.faireC(j);
		}
	    }
	
	
    }
    
    /**
    * Effectue le mouvement robot
    *@param setup mouvement du setup du mouvement souhaité
    *@param desetup mouvement du desetup du mouvement souhaité
    *@param i nombre de tour du mouvement robot C
    */
    public void effectuerMvtRobot(String[] setup,String[] desetup,int i){
		ArrayList<String> simplification;
		currentSetup=new ArrayList<String>();
		currentSetup.addAll(previousSetup);
		currentSetup.addAll(setup);
		currentSetup.add("C"+i);
		simplification=simplifier(currentSetup);
		for(String j:simplification){
			effectuerRotation(j);
		}
		previousSetup=new ArrayList<String>();
		previousSetup.addAll(desetup);
    }

    /**
    * Simplifie l'algorithme
    *@param algo tableaux de mouvements
    *@return ArrayList<String>
    */
    private ArrayList<String> simplifier(ArrayList<String> algo){
		boolean aChange=false;
		ArrayList<String> newAlgo;
		int i,j;
		String courant="",suivant="";
		do{
			aChange=false;
			newAlgo=new ArrayList<String>();
			i=j=0;
			while(i<algo.size()-1){
				courant=algo.get(i);
				suivant=algo.get(i+1);
				if(courant.charAt(0)==suivant.charAt(0)){
					aChange=true;
					j=(Integer.parseInt(courant.substring(1))+Integer.parseInt(suivant.substring(1)))%4;
					courant=""+courant.charAt(0)+j;
					if(j!=0)
						newAlgo.add(courant);
					i+=2;
				}
				else{
					i++;
					newAlgo.add(courant);
				}
			}
			newAlgo.add(algo.get(algo.size()-1));
			algo=newAlgo;
		}while(aChange);
		return algo;
    }
    
    /**
    * Permet de faire les mouvements x,y,z
    *@param setup mouvement pour le setup
    *@param i nombre de tour du mouvement robot C
    */
    public void effectuerMvtRobotSimple(String[] setup){
        for(String i:setup)
            effectuerRotation(i);
        //previousSetup.addAll(setup);
   }
}
