/**
 *classe qui gère les mouvements du robot
 *@author Groupe ECAO Rubik's Cube : Pierre_Bienaime Bastien_Bonnet Mathieu_Chataigner Mathieu Fresquet.
 */

package cube.robot;

import cube.*;

import lejos.nxt.*; // this is required for all programs that run on the NXT

import java.io.DataInputStream;
import java.io.DataOutputStream;
import lejos.nxt.comm.USB;
import lejos.nxt.comm.USBConnection;
import lejos.nxt.remote.*;

public class RobotRubik {
 
  private NXTRegulatedMotor moteurSocle; 
  private NXTRegulatedMotor moteurPince; 

  private boolean pinceModeBloqueurEnHaut;
  private boolean pinceModePoussoirEnHaut;
  
  private DataOutputStream dOut;
  private DataInputStream dIn;
  private EcranNXT ecran;
  private RobotRecepteur recepteur;


  /**
   * Constructeur qui initialise le robot et ses moteurs
   *@param moteurSocle le moteur socle (base) du robot
   *@param moteurPince le moteur pince du robot
   */
  public RobotRubik(NXTRegulatedMotor moteurSocle, NXTRegulatedMotor moteurPince) {
    this.moteurSocle=moteurSocle;
    this.moteurPince=moteurPince;
    pinceModeBloqueurEnHaut=false;
    pinceModePoussoirEnHaut=false;
    // moteurPince.setPower(10);
    moteurPince.setSpeed(300);
	
    //demarre l'ecran
    ecran = new EcranNXT(this);
    ecran.start();
		
    //attend une connection USB
    USBConnection conn = USB.waitForConnection();
    ecran.USBOK();
	
	
    //ouvre les flux d'entree sortie
    dOut = conn.openDataOutputStream();
    dIn = conn.openDataInputStream();
	
    //lance le recepteur USB du nxt
    recepteur = new RobotRecepteur(dIn,dOut, this);
    recepteur.start();
  }

  /**
   * Permet de récupérer l'ecran du NXT
   *@return EcranNXT 
   */
  public EcranNXT getEcran(){
    return ecran;
  }

  /**
   * Gère le mouvement qui bascule le cube
   */
  // Methodes mouvements elementaires
  public void leverPincePoussoir() {
    if (!pinceModeBloqueurEnHaut || !pinceModePoussoirEnHaut) {
      moteurPince.setSpeed(250);
      // moteurPince.setPower(70);
      moteurPince.rotate(48);
      // moteurPince.setPower(100);
      moteurPince.setSpeed(100);
      moteurPince.rotate(35);
      pinceModePoussoirEnHaut=true;
    }
  }
  /**
   * Gère le mouvement de la pince pour revenir à l'etat initiale après avoir basculé le cube
   */
  public void descendrePincePoussoir() {
    if (pinceModePoussoirEnHaut && !pinceModeBloqueurEnHaut) {
      moteurPince.setSpeed(50);
      moteurPince.rotate(-82);
      pinceModePoussoirEnHaut=false;
      moteurPince.setSpeed(300);
    }
  }

  /**
   * Gère le mouvement de la pince afin de bloquer le cube
   */
  public void leverPinceBloqueur() {
    if (!pinceModeBloqueurEnHaut || !pinceModePoussoirEnHaut) {
      moteurPince.setSpeed(70);
      moteurPince.rotate(55);
      pinceModeBloqueurEnHaut=true;
      moteurPince.lock(100);
    }
  }

  /**
   * Gère le mouvement de la pince pour revenir à l'etat initiale après avoir bloqué le cube
   */
  public void descendrePinceBloqueur() {
    if (!pinceModePoussoirEnHaut && pinceModeBloqueurEnHaut) {
      moteurPince.rotate(-54);
      pinceModeBloqueurEnHaut=false;
    }
  }

  // methodes mouvements composites

  /**
   * Gère le mouvement des robots pour faire le mouvement A
   */
  public void faireA() {
    //	LCD.drawString("A",0,4);
    leverPincePoussoir();    
    descendrePincePoussoir();
    //leverPinceBloqueur();
    //descendrePinceBloqueur();
  }

  /**
   * Gère le mouvement des robots pour faire le mouvement A une ou plusieurs fois
   *@param nombre entier indiquant le nombre de fois que le mouvement doit être effectué
   */
  public void faireA(int nombre){
    for(int i=1;i<=nombre%4;i++){
      faireA();
    }
  }

  /**
   * Gère le mouvement des robots pour faire le mouvement B
   *@param sensHoraire entier indiquant le nombre de fois que le mouvement doit être effectué
   */
  public void faireB(int sensHoraire) {
    //	LCD.drawString("B",0,4);
	
    int nbDegres=0;
    //nbDegres=nbDegres + (sensHoraire-1)*210;
    switch(sensHoraire){
    case 1 : nbDegres=630;
      moteurSocle.setSpeed(700);
      //LCD.drawString(" ",1,4);
      break;
    case 2 : nbDegres=630*2;
      moteurSocle.setSpeed(700);
      //LCD.drawString("2",1,4);
      break;
    case 3 : nbDegres=630*3;
      moteurSocle.setSpeed(700);
      //LCD.drawString("'",1,4);
      break;
    }
    // moteurSocle.setPower(100);
    moteurSocle.rotate(nbDegres);
  }

  /**
   * Gère le mouvement des robots pour faire le mouvement C
   *@param sensHoraire entier indiquant le nombre de fois que le mouvement doit être effectué
   */
  public void faireC(int sensHoraire) {
    //	LCD.drawString("C",0,4);
    int ajustage=0;
    leverPinceBloqueur();
    faireB(sensHoraire);
    switch(sensHoraire){
    case 1 : ajustage=125;
      moteurSocle.setSpeed(700);
      break;
    case 2 : ajustage=125;
      moteurSocle.setSpeed(800);
      break;
    case 3 : ajustage=125;
      moteurSocle.setSpeed(700);
      break;
    }
    moteurSocle.rotate(ajustage);
    moteurSocle.rotate(-1*ajustage);
    descendrePinceBloqueur();
  }
  
  /**
   * Ferme le flux
   */
  public void fermerFlux(){
    try{
      dOut.close();
      dIn.close();
    }
    catch(Throwable e){}
  }
  
  /**
   * Clos la connection
   */
  public void quitter()throws Throwable{
    this.fermerFlux();
    //System.exit(0);
  }
  
  public static void main(String[] args) throws Throwable {
    int i=0;
    
    RobotRubik r=new RobotRubik(Motor.A,Motor.B);
	
    LCD.drawString("Test", 0, 0);
    
    for(i=0;i<5;i++){
      r.faireA();
      r.faireB(1);
      r.faireC(1);
      r.faireA();
      r.faireB(2);
      r.faireC(2);
      r.faireA();
      r.faireB(3);
      r.faireC(3);
      //56 dents
    }
    Button.ESCAPE.waitForPressAndRelease();
    r.quitter();
  }
  
}
