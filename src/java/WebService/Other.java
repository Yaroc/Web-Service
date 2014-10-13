/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package WebService;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import javax.imageio.ImageIO;

/**
 *
 * @author Yarib
 */
public class Other {
    
    
     public static byte[] createChecksum(String filename) throws Exception {
       InputStream fis =  new FileInputStream(filename);
       byte[] buffer = new byte[1024];
       MessageDigest complete = MessageDigest.getInstance("MD5");
       int numRead;
       do {
           numRead = fis.read(buffer);
           if (numRead > 0) {
               complete.update(buffer, 0, numRead);
           }
       } while (numRead != -1);

       fis.close();
       return complete.digest();
   }
     
      public static String getMD5Checksum(String filename) throws Exception {
       byte[] b = createChecksum(filename);
       String result = "";

       for (int i=0; i < b.length; i++) {
           result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
       }
       return result;
   }
      
      private BufferedImage invertirColores(BufferedImage imagen) {
        for (int i = 0; i < 170; i++) {
            for (int j = 0; j < 170; j++) {
                int rgb = imagen.getRGB(i, j);
                if (rgb == -16777216) {
                    imagen.setRGB(i, j, -1);
                } else {
                    imagen.setRGB(i, j, -16777216);
                }
            }
        }
        return imagen;       
}
      
       public String CreateQR(String message) throws WriterException, FileNotFoundException, IOException{
             BitMatrix bm;
             File dir = new File(System.getProperty("user.home")+"\\Documents\\NetBeansProjects\\Web-Service\\web\\Imagenes\\QR");
             Writer writer = new QRCodeWriter();                                                 
             bm = writer.encode(message, BarcodeFormat.QR_CODE, 170, 170);
                    // Crear un buffer para escribir la imagen
             BufferedImage imagex = new BufferedImage(170, 170, BufferedImage.TYPE_INT_RGB);
                     for (int i = 0; i < 170; i++) {
                         for (int j = 0; j < 170; j++) {
                                int grayValue = (bm.get(j, i) ? 1 : 0) & 0xff;
                                imagex.setRGB(j, i, (grayValue == 0 ? 0 : 0xFFFFFF));
                            }
                         }
                        imagex = invertirColores(imagex);
                        FileOutputStream qrCode = new FileOutputStream(dir+"/fiber.jpg");                        
                        ImageIO.write(imagex, "jpg", qrCode);
                        qrCode.close();  
                        ImageIO.read(new File(dir+"/fiber.jpg"));
             if(!dir.exists())
                    {
                        dir.mkdirs();
                    }    
             
               return "todo bien"; 
           
        }
    
}
