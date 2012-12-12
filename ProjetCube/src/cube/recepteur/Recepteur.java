package cube.recepteur;

import lejos.nxt.*;

import java.io.IOException;
import java.io.DataInputStream;

import cube.*;



/**
 * Describe class <code>RobotRecepteur</code> here.
 *
 * @author <a href="mailto:mathieu.chataigner@insa-rouen.fr">Mathieu CHATAIGNER</a>
 * @version 1.0
 */
public class RobotRecepteur extends Thread
{

	private DataInputStream din;
	private RobotRubik alphonse;
	private boolean quitter=false;
	
	
    /**
     * Creates a new <code>RobotRecepteur</code> instance.
     *
     * @param _din a <code>DataInputStream</code> value
     * @param _alphonse a <code>RobotRubik</code> value
     */
    public RobotRecepteur(DataInputStream _din,RobotRubik _alphonse){
		this.din=_din;
		this.alphonse=_alphonse;
	}
	
    /**
     * Describe <code>run</code> method here.
     *
     */
    public void run(){
		int rotation,indice;
		while(!quitter){
			try{
				rotation=din.readInt();
				indice=din.readInt();
				effectuerRotation(rotation,indice);
			}
			catch(Throwable e){
			}
		}
	}
	
    /**
     * Describe <code>quitter</code> method here.
     *
     */
    public void quitter(){
		this.quitter=true;
	}
	
	private void effectuerRotation(int rotation,int indice) throws Throwable{
		switch(rotation){
			case 1: alphonse.faireA();
					break;
			case 2: alphonse.faireB(indice);
					break;
			case 3: alphonse.faireC(indice);
					break;
			case 4: this.quitter();
					alphonse.quitter();
					break;
			default: throw new Throwable("Reception error !");
		}
	}
}
