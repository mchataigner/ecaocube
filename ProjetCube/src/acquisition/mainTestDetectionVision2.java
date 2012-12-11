import cube.*;
import acquisition.*;
public class mainTestDetectionVision2 {
	public static void main(String[] args)throws Throwable
	{
		DetectionVision2 detect ;
		Cube cubeDetecte ; 
		detect = new DetectionVision2();
		cubeDetecte = detect.detecter();
		if(cubeDetecte!=null)
			System.out.println(cubeDetecte);
	}
}
