package cube.emetteur;

import cube.recepteur.*;
import cube.*;

import lejos.pc.comm.*;
import java.io.*;


import java.util.Scanner;



/**
 * La classe <code>Emetteur</code> permet faire le lien entre le modèle du pc et le robot lego NXT.
 *
 * @author <a href="mailto:">Groupe ECAO Rubik's Cube: Bienaime Bonnet Chataigner Fresquet.</a>
 * @version 1.0
 */
public class Emetteur
{
    private NXTConnector conn;
    private DataInputStream inDat;
    private DataOutputStream outDat;


    /**
     * Créé une nouvelle instance d'<code>Emetteur</code>.
     * 
     * @exception NXTException si aucun nxt n'est branché.
     */
    public Emetteur() throws NXTException{
        this(new NXTConnector());
    }

    /**
     * Créé une nouvelle instance d'<code>Emetteur</code>.
     *
     * @param conn un objet <code>NXTConnector</code>
     * @exception NXTException si aucun nxt n'est branché
     */
    public Emetteur(NXTConnector conn) throws NXTException{
        this.conn=conn;
        if(!getConnector().connectTo("usb://")){
            throw new NXTException("No NXT found using USB.");
        }
        inDat=(DataInputStream)getConnector().getInputStream();
        outDat=(DataOutputStream)getConnector().getOutputStream();
    }



    /**
     * La méthode <code>getConnector</code> renvoie l'attribut correspondant au NXTConnector.
     *
     * @return a <code>NXTConnector</code> value
     */
    public NXTConnector getConnector(){
        return this.conn;
    }

    /**
     * La méthode <code>effectuerMouvementElementaire</code> permet d'envoyer le mouvement élémentaire au nxt.
     * Cette méthode est bloquante et attend que le nxt renvoie un booléen correspondant au succès/échec du mouvement.
     * @param mvt, le mouvement à effectuer.
     * @return un <code>boolean</code> correspondant au succès échec.
     */
    public boolean effectuerMouvementElementaire(String mvt){
        int reussi=0;
        int i=0;
        int j=0;
        mvt=mvt+" ";
        try{
            switch(mvt.charAt(0)){
            case 'R': i=1;
                break;
            case 'L': i=2;
                break;
            case 'U': i=3;
                break;
            case 'D': i=4;
                break;
            case 'F': i=5;
                break;
            case 'B': i=6;
                break;
            case 'Q': i=0;
                break;
            default: break;
            }
            switch(mvt.charAt(1)){
            case ' ': j=1;break;
            case '2': j=2;break;
            case 'p': j=3;break;
            default : j=0;break;
            }
			
            outDat.writeChar(mvt.charAt(0));
            outDat.writeInt(j);
            outDat.flush();
            reussi=inDat.readInt();
			
        }
        catch(Throwable e){
            erreur();
        }
        if(reussi!=1)
            System.err.println("erreur dans la suite de mvt "+mvt+" "+reussi);
        return reussi==1;
    }

    private void erreur(){
        System.err.println("erreur dans la saisie");
    }
	

    /**
     * La méthode <code>close</code> permet de fermer correctement tous les flux.
     * 
     */
    public void close(){
        try{
            inDat.close();
            outDat.close();
            conn.close();
            System.out.println("System closed.");
        } catch (IOException ioe) {
            System.err.println("Error while closing.");
            System.exit(0);
        }
    }

}
