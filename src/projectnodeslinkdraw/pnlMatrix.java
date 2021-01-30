
package projectnodeslinkdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;


public class pnlMatrix extends JPanel implements Runnable{

    FrmDikstra dik;
    
    public pnlMatrix(FrmDikstra dik) {
        this.dik=dik;
        setPreferredSize(new Dimension(500, 720));
    }
    
    public void paintComponent(Graphics g) {
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, 500, 720);
        g.setColor(Color.BLACK);
        
        for(int i=0;i<dik.dist.length+1;i++) {
            g.drawLine(20, 30*i, 120, 30*i);
        }
        
        g.drawLine(20, 20, 20, dik.dist.length*30);
        g.drawLine(120, 20, 120, dik.dist.length*30);
        
        g.setFont(new Font("Monospaced", Font.BOLD, 22));
        int j=30;
        for(int i=1;i<dik.graph.length;i++) {
            g.setColor(Color.WHITE);
            String s="";
            if(dik.dist[i]==Integer.MAX_VALUE) {
                s=String.valueOf(Character.toChars(Integer.parseInt("221E", 16)));;
            }
            else {
                s=""+dik.dist[i];
            }
            g.drawString(s, 32, j+=30);
        }
        g.setFont(new Font("Monospaced", Font.BOLD, 12));
        g.drawString("Distance From Source", 20, dik.dist.length*30+35);
        for(int i=1;i<dik.dist.length;i++) {
            String s="Distance from 1 to "+i+" is ";
            if(dik.dist[i]==Integer.MAX_VALUE) {
                s+=String.valueOf(Character.toChars(Integer.parseInt("221E", 16)));
            }
            else {
                s+=""+dik.dist[i];
            }
            g.drawString(s, 20, dik.dist.length*30+35+(i*25));
        }
    }
    
    @Override
    public void run() {
        while(true) {
            repaint();
            try {
                Thread.sleep(4000);
            } catch (InterruptedException ex) {
                Logger.getLogger(pnlMatrix.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}