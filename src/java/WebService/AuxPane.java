/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package WebService;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Yarib
 */
public class AuxPane extends JPanel {
      ImageIcon qr_icon,info_icon;
      Image qr_image,info_image;
      BufferedImage  bi;
      public AuxPane(int s,String qr){
        qr_icon=new ImageIcon(qr);
        info_icon=new ImageIcon("barra.png");
        info_image=info_icon.getImage();
        qr_image=qr_icon.getImage();
        this.setBackground(Color.WHITE);
        this.setSize(s, 150);
        Rectangle r=this.getBounds();
        bi=new BufferedImage(r.width,r.height-20,BufferedImage.TYPE_3BYTE_BGR); 
        System.out.println("w:"+r.width+"  h:"+r.height);
    }
    
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.red);
        g2.drawImage(qr_image, 100, 10, 120, 120, null);
        g2.drawImage(info_image,300,20,400,100,null); 
    }

    public void save() throws IOException {
        Graphics g=bi.getGraphics();
        paint(g);
        ImageIO.write(bi, "png", new File("prueba.png"));
        System.out.println("Saving");
    }
}
