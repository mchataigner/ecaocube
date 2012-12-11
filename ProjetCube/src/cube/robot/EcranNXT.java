    /**
     * classe gérant l'affichage de l'ecran NXT du robot
     * @author Groupe ECAO Rubik's Cube : Pierre_Bienaime Bastien_Bonnet Mathieu_Chataigner Mathieu Fresquet
     */


package cube.robot;

import lejos.nxt.LCD;


public class EcranNXT extends Thread
{
	private RobotRubik alphonse;
	private boolean attenteUSB;
	
	private boolean quitter=false;
	
	private String [] mvts={"Q","A ","B","C"};
	private String [] indices={""," ","2","'"};
	

    /**
    * Initialise l'ecran NXT
    *@param _alphonse le nom du robot
    */
	public EcranNXT(RobotRubik _alphonse)
	{
		alphonse=_alphonse;
		attenteUSB=true;
		
	}
	
    /**
    * Message titre
    */
	private void drawTitle()
	{
		LCD.drawString("#################", 0, 0);
		LCD.drawString("## Robot Cube ###", 0, 1);
		LCD.drawString("#################", 0, 2);
	}
	
    /**
    * Message d'attente
    */
	private void drawAttente()
	{
		LCD.drawString("Attente de l'USB", 0, 4);
		
	}
	
    /**
    * Message du mouvement
    *@param rotation 
    *@param indice
    */
	private void drawMvt(int rotation,int indice){
		LCD.drawString(mvts[rotation],0,4);
		if(rotation==2||rotation==3)
			LCD.drawString(indices[indice],1,4);
	}
	
    /**
    * Message pour valider la connection USB
    */
	public void USBOK()
	{
		attenteUSB=false;
	}

    /**
    * Message affichant le mouvement
    * mvt nom du mouvement
    * indice 
    */
	public void ecrireMvt(char mvt,int indice){
		char[] mvts={mvt};
		LCD.drawString(new String(mvts),0,5);
		LCD.drawString((new Integer(indice)).toString(),1,5);
		
	}

    /**
    * Message en fonctionnement
    */
	public void run(){
		drawTitle();
		while(attenteUSB)
		{
			drawAttente();
		}
		LCD.drawString("                ", 0, 4);
		
		
	}
}
