/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package WebService;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Yarib
 */
public class AppendImage {
    int Width,Height,Type;
    BufferedImage Barra,imagen;
    String name,path;
     public AppendImage(String path,String append,String img,String name){
         this.path=path;
         this.name=name;
         File Barra_file=new File(append);
         File imagen_file=new File(img);
         System.out.println(append+"  "+img);
         try {
             Barra=ImageIO.read(Barra_file);
             imagen=ImageIO.read(imagen_file);
             Width=imagen.getWidth();
             Height=imagen.getHeight();
             Type=imagen.getType();
             
         } catch (IOException ex) {
            System.err.println(ex.getMessage());
         }
     }
      
     public String Append() throws IOException{
         BufferedImage finalImg = new BufferedImage(Width,Height+130,BufferedImage.TYPE_3BYTE_BGR); 
         finalImg.createGraphics().drawImage(imagen, 0 , 0 , null);  
         finalImg.createGraphics().drawImage(Barra, 0 , Height , null);
         String fname="Final"+name+".png";
         System.out.println("SAVING..."+path+fname);
         ImageIO.write(finalImg, "png", new File(path+fname));  
         File f=new File(path+fname) ;
         return f.getAbsolutePath();
     }
}
