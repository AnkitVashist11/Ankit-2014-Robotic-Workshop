/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cryptography;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author PriyaPreet
 */
public class Encrypt_Hide {
    static BufferedImage BI=null;
     public static void hideText(String pth,String txt)
    {
        System.out.println(pth);
        System.out.println(txt);
        File f=new File(pth);
        byte bytes[]=txt.getBytes();
         try {
         BI=ImageIO.read(f);
        } catch (IOException ex) {
         
            System.out.println("Image Not readable or Available ::"+ex);
        }
        int TotalChars=bytes.length;
        int width=BI.getWidth();
         int height=BI.getHeight();
        if(TotalChars*10>width*height)
             System.out.println("Please Choose a Large size photo for better o/p");
         int ind=0;
       
         for(int w=0; w<width; w++)
         {
         for(int h=0; h<height; h++)
         {
             if(ind<TotalChars)
             {
                 
           if(ind%3==0)
              ImageUtilities.setRed(BI, w, h,bytes[ind]);
           else if(ind%3==1)
              ImageUtilities.setGreen(BI, w, h,bytes[ind]); 
           else if(ind%3==2)
              ImageUtilities.setBlue(BI, w, h,bytes[ind]); 
           ind++;
             }
         }
      }
         
              ImageUtilities.setBlue(BI, width-1, height-1,TotalChars);
     File file=new File("F:\\Hello.png");
              try {
            ImageIO.write(BI, "png", file);
                    //flag=true;
        } catch (IOException ex) {
         
            System.out.println("Image Not readable or Available ::"+ex);
            //flag=false;
        }
    }
     
    }
    

