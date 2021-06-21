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

/**
 *
 * @author PriyaPreet
 */
public class Decrypt_Extract {
   
    
       public static String extractText(String st)
    {
        BufferedImage  BI=null;
       
                File f=new File(st);
       try {
         BI=ImageIO.read(f);
        } catch (IOException ex) {
         
            System.out.println("Image Not readable or Available ::"+ex);
        }
       
       int width=BI.getWidth();
        int height=BI.getHeight();

     //   BufferedImage BI=new BufferedImage(f);
         int TotalChars=ImageUtilities.getBlue(BI, width-1, height-1);

         char Chars[]=new char[TotalChars];
         int ind=0;
         
         for(int w=0; w<width; w++)
         {
             
         for(int h=0; h<height; h++)
         {
             if(ind<TotalChars)
             {
                 
           if(ind%3==0)
           Chars[ind]=(char) ImageUtilities.getRed(BI,w,h);
           else if(ind%3==1)
             Chars[ind]=(char) ImageUtilities.getGreen(BI,w,h);
           else if(ind%3==2)
               Chars[ind]=(char) ImageUtilities.getBlue(BI,w,h);
           ind++;
             }
         }
       
         }
    return(new String(Chars));
    
    
    }
}         
     
    

