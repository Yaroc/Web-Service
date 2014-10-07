/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package WService;

import FADD.AES;
import FADD.Ciphering;
import FADD.PNG;
import FADD.StegaWithPNG;
import FADD.Text_XML;
import com.google.zxing.WriterException;
import java.io.File;
import java.io.IOException;
import javax.jws.WebService;

/**
 *
 * @author Yarib
 */
@WebService(serviceName = "Prueba")
public class Prueba {
    
    String FileName;
    public String Firma(String bse64, String NameFile, String message) throws IOException, WriterException {

        String Password="123";
        FileAux fa=new FileAux();
        String zipfile=fa.CreateFile(NameFile,bse64,"zip");
        String imagen=fa.unzipfile(zipfile,fa.getpath()+"/Images/");
       // System.err.println("path imagen:"+imagen);
        String extension=getExt(imagen);
              switch(extension){
              case "jpg":
             // System.err.println("archivo es jpg");
              imagen=fa.jpgToPng(imagen);  
              break;
              case "JPG":
               //   System.err.println("archivo es jpg");
              imagen=fa.jpgToPng(imagen);  break;          
      }
      
       this.FileName=fa.ImageName;
        String rutafirmada=firmar(message,Password,imagen);
        
        String rutaAbsoluta=fa.getPath(rutafirmada);
        HiloSession session=new HiloSession(rutafirmada);
        session.start();
        
        //String zip=fa.zipFile(rutafirmada, NameFile);
        //String b64=fa.getb64(zip);
        //7fa.DeleteFile(imagen);
        //fa.DeleteFile(zipfile);
       // fa.DeleteFile(zip);
        return rutaAbsoluta;
       //return imagen;
    }
    
    private String firmar(String info, String pass, String nomArch){
        String ruta="";
        try {
            File archivoBMP = new File(nomArch);
            PNG img = new PNG(archivoBMP);
            Ciphering cifrador = null;
            cifrador = new AES(); 
            byte[] mensajeCifrado = cifrador.encripta(info, pass, "AES");
            
            //Proceso de BMP
            if (getExt(archivoBMP.getName()).equalsIgnoreCase("png")) {
                StegaWithPNG stega = new StegaWithPNG(img);
                System.out.println("Is valid: "+img.isValid());
                ruta=System.getProperty("user.home") + "/Documents/NetbeansProjects/Web-Service/web/Imagenes/Images/"+FileName+"Firmada.png";
                boolean execStega = stega.execStega(ruta, mensajeCifrado, "LSBs");
                System.out.println("execStega: "+execStega);
                if (execStega) {//img.savePNG(nFile, stega.getPNG().getChunks())
                   return ruta; 
                } else {
                    return "No se pudo salvar la imagen";
                }
            }
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            return "Error: al abrir "+nomArch;
        }
        return ruta;
    }
    
    
     private String consultar(String pass, String nomArch){
        try {
            File archivoBMP = new File(nomArch);
            PNG img = new PNG(archivoBMP);
            byte[] cifrado=new StegaWithPNG(img).getInfo(img, "AES");
            Ciphering cifrador=null;
            cifrador=new AES();
           
            String mensaje_txt=" -Error- ";
            String mensaje_xml=" -Error- ";
            mensaje_xml=cifrador.decripta(cifrado, pass,"LSBs");
            mensaje_xml=mensaje_xml!=null?mensaje_xml:"";

            if(mensaje_xml.startsWith("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>")){
                Text_XML txtxml = new Text_XML();
                mensaje_txt = txtxml.toText(mensaje_xml, "\n");
                mensaje_txt = mensaje_txt!=null?mensaje_txt:"ERROR";
                return mensaje_txt;
            }
            return mensaje_xml;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            //Logger.getLogger(WSFADD.class.getName()).log(Level.SEVERE, null, ex);
            return "Error ocurrido: "+ex.getMessage();
        }
        
        //return "Final..";
    }
    
    private String getExt(String fileName) {
        if (fileName.length() < 0) {
            return null;
        }
        //Devolverá si el DD es de tipo BMP, PNG, etc...
        char[] nChar = new char[]{fileName.charAt(fileName.length() - 3), fileName.charAt(fileName.length() - 2), fileName.charAt(fileName.length() - 1)};
        return new String(nChar, 0, 3);
    }

        public String ConsultaFirma( String base64,String nameFile) throws IOException {
        FileAux fa=new FileAux();
      //  byte []nte= fa.decodeBase64(base64);
        String pathfile=fa.CreateFile(nameFile,base64,"zip");
        String pathImage=fa.unzipfile(pathfile, fa.getpath()+"/Images/");
        System.out.println("Created file"+pathImage);
        String resultado=consultar("123",pathImage);
        fa.DeleteFile(pathImage);
        fa.DeleteFile(pathfile);
         return  resultado; 
    }
        
        
     public String CreateQR(String Message) {
        String result = "";
        Other o=new Other();
        try {
            FileAux fa=new FileAux();
            
                result =o.CreateQR(Message);
        } catch (WriterException | IOException ex) {
            System.err.println(ex.getMessage());
        }
        return result;
    }
    
}
