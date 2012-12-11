
/**
 * @author Thibault COLLEONY
 */
package acquisition;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import javax.swing.JDialog;

import javax.media.Buffer;
import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;
import javax.swing.JButton;
import javax.swing.JComponent;

import javax.media.control.FormatControl;


import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;


public class Webcam  extends Panel// implements ActionListener
{
  public static Player player = null;
  public CaptureDeviceInfo webcamInfo = null;
  public MediaLocator mediaLocator = null;
  public JButton capture = null;
  public Buffer buf = null;
  public Image img = null;
  public VideoFormat vf = null;
  public BufferToImage btoi = null;
  private Frame f;
  public Webcam(Player _player) {
    f = new Frame("SwingCapture");

    setLayout(new BorderLayout());
    setSize(640,480);
	this.player=_player;
	Component comp;
	
	try{
	if ((comp = player.getVisualComponent()) != null) {
	    add(comp,BorderLayout.CENTER);
	  }
    } catch (Exception e) {
	e.printStackTrace();
    }
    f.addWindowListener(new WindowAdapter() {
	public void windowClosing(WindowEvent e) {
	  f.dispose();
	  //playerclose();
	  //System.exit(0);
	  }});

    f.add("Center",this);
    f.pack();
    f.setSize(new Dimension(640,480));
    f.setVisible(true);

	FormatControl formatControl = (FormatControl)player.getControl("javax.media.control.FormatControl");
	//player.stop();
	Component co = formatControl.getControlComponent();
	if (co != null) {
		player.stop();
		JDialog d = new JDialog(f,"Format Control",true);
		f.add(co);
		f.pack();
		f.setLocationRelativeTo(f);
		f.setVisible(true);
		f.dispose();
		player.start();
	}
  }
}
