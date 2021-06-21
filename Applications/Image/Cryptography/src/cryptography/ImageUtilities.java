/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptography;

import static cryptography.Encrypt.BI;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
/**
 *
 * @author Gmeetg
 */
public class ImageUtilities {
    
    
     
    public static int getRGB(BufferedImage image,int x, int y ) {
        return image.getRGB(x,y);
    }

    public static Color getColor(BufferedImage image, int x, int y )
    {
        int rgb = image.getRGB(x,y);
        Color c = new Color(rgb);
        return c;
    }
    
    public static int getRed(BufferedImage image, int x, int y )
    {
        int rgb = image.getRGB(x,y);
        Color c = new Color(rgb);
        return c.getRed();
        
    }
    
     public static int getBlue(BufferedImage image, int x, int y)
    {
        int rgb = image.getRGB(x,y);
        Color c = new Color(rgb);
        return c.getBlue();
        
    }
    
      public static int getGreen(BufferedImage image, int x, int y)
    {
        int rgb = image.getRGB(x,y);
           Color c = new Color(rgb);
        return c.getGreen();
        
    }
    
      public static boolean setRGB(BufferedImage image, int x, int y, Color RGB)
      {
          boolean flag=false;
      image.setRGB(x, y, RGB.getRGB());
      
      return flag;
      }
      
   public static boolean setRGB(BufferedImage image, int x, int y, int RGB)
      {
          boolean flag=false;
      image.setRGB(x, y, RGB);
      
      return flag;
      }
public static boolean setRed(BufferedImage image, int x, int y, int Red)
      {
          boolean flag=false;
          
          int Green=getGreen(image,x,y);
          int Blue=getBlue(image,x,y);
          Color RGB=new Color(Red,Green,Blue);
          image.setRGB(x, y, RGB.getRGB());
      
      return flag;
      }
   
public static boolean setGreen(BufferedImage image, int x, int y, int Green)
      {
          boolean flag=false;
          
          int Red=getRed(image,x,y);
          int Blue=getBlue(image,x,y);
          Color RGB=new Color(Red,Green,Blue);
          image.setRGB(x, y, RGB.getRGB());
      
      return flag;
      }
   
public static boolean setBlue(BufferedImage image, int x, int y, int Blue)
      {
          boolean flag=false;
          
          int Green=getGreen(image,x,y);
          int Red=getRed(image,x,y);
          Color RGB=new Color(Red,Green,Blue);
          image.setRGB(x, y, RGB.getRGB());
       return flag;
      }
     public static void ImShow(String path,String title)
     {
         JFrame jf=new JFrame(title);
        JPanel jp=new JPanel();
        JLabel jl=new JLabel();

        jl.setIcon(new ImageIcon(path));
            jp.add(jl); //  to add or to put label ito the panel
            jf.add(jp); // to put the panel into the frame
            jf.setVisible(true);
            jp.setVisible(true);
            jl.setVisible(true);
            jf.setSize(500,500);
     }
     public static BufferedImage ImRead(String path,String txt)
     {
         System.out.println(path);
         System.out.println(txt);
         File f=new File(path);
         try {
         BI=ImageIO.read(f);
         
        } catch (IOException ex) {
         
            System.out.println("Image Not readable or Available ::"+ex);
        }
         
         return BI;
     }
      public static BufferedImage ImReadDec(String path)
     {
        
         System.out.println(path);
         
         File f=new File(path);
         try {
         BI=ImageIO.read(f);
         
        } catch (IOException ex) {
         
            System.out.println("Image Not readable or Available ::"+ex);
        }
         System.out.println("Reutrn Ho gya");
         return BI;
     }
     
     public static void imWrite(BufferedImage BI)
     {
            File file=new File("F:\\Hello.png");
              try {
            ImageIO.write(BI, "png", file);
                    //flag=true;
        } catch (IOException ex) {
         
            System.out.println("Image Not readable or Available ::"+ex);
            //flag=false;
        }
     }
public static  BufferedImage openImgInNewFrame(JFrame jf,File file)
{

JPanel jp=new JPanel();
JLabel jl=new JLabel();
BufferedImage BI=null;

ImageIcon Img=new ImageIcon(file.getAbsolutePath());  // to make icon from the image
                                        jl.setIcon(Img); // to put Image icon into the label
                                       jp.add(jl); //  to add or to put label ito the panel
                                       jf.add(jp); // to put the panel into the frame
                                       jf.setVisible(true);
                                       jp.setVisible(true);
                                       jl.setVisible(true);
                                       jf.setSize(500,500);
//jf.setLocation(100, 100);    
        try {
            BI=ImageIO.read(file);
        } catch (IOException ex) {
         
            System.out.println("Image Not readable or Available ::"+ex);
        }

return BI;
}

public static BufferedImage openImgDialog(JFrame jf)
{

File fl=null;
BufferedImage BI=null;

JFileChooser fc = new JFileChooser();

int returnVal = fc.showOpenDialog(null); //2
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					fl = fc.getSelectedFile();
                                        //file.getAbsolutePath()
ImageUtilities.openImgInNewFrame(jf, fl);
                                    
                     	} 
                                else {
			
				}
//jf.setLocation(100, 200);    
        try {
            BI=ImageIO.read(fl);
        } catch (IOException ex) {
         
            System.out.println("Image Not readable or Available ::"+ex);
        }

return BI;
}


public static boolean SaveImgDialog(BufferedImage BI, JFrame jf)
{
File file=null;
final JFileChooser fc = new JFileChooser();
boolean flag=false;


int returnVal = fc.showSaveDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					file = fc.getSelectedFile();
                                        
                                        file =new File(file.getAbsolutePath()+".png");
                                       
                     	}
    try {
            ImageIO.write(BI, "png", file);
                    flag=true;
        } catch (IOException ex) {
         
            System.out.println("Image Not readable or Available ::"+ex);
            flag=false;
        }
                         ImageUtilities.openImgInNewFrame(jf, file);
                             jf.setLocation(100, 200);    
return flag;
}

    static void ImShow(String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

