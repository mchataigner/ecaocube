package cube.emetteur;

import cube.*;

import lejos.pc.comm.*;
import java.io.*;

public class USBComm
{
	private NXTConnector conn;


    private DataInputStream inDat;
    private DataOutputStream outDat;

    public USBComm() throws NXTException{
        this(new NXTConnector());
    }

    public USBComm(NXTConnector conn) throws NXTException{
        this.conn=conn;
        if(!getConnector().connectTo("usb://"))
            throw new NXTException("No NXT found using USB.");
        inDat=getConnector().getDataIn();
        outDat=getConnector().getDataOut();
    }

    public NXTConnector getConnector(){
        return this.conn;
    }

    public DataInputStream getIS(){
        return inDat;
    }
	
	public void faireRotation(int rotation, int indice){
		try{
			outDat.writeInt(rotation);
			outDat.writeInt(indice);
			outDat.flush();
		}
		catch(Throwable e){}
	}

    public void flush(){
        try{
            outDat.flush();
        }
        catch(IOException ioe){
            System.err.println("error");
        }
    }

    public void close(){
        try{
            inDat.close();
            System.out.println("System closed.");
            outDat.close();
            System.out.println("System closed.");
            conn.close();
            System.out.println("System closed.");
        } catch (IOException ioe) {
            System.err.println("Error while closing.");
            System.exit(0);
        }
    }
}



