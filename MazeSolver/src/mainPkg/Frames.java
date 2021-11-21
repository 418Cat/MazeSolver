package mainPkg;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Frames {

    public static JFrame mainFrame = new JFrame();
    public static JPanel mainPanel = new JPanel();
    
    //Initialise la fenêtre
    public static void mainFrame() {
        
        mainFrame.setSize(Main.img.getWidth(), Main.img.getHeight());
        mainFrame.setUndecorated(true);
        mainPanel.setBackground(Color.white);
        mainFrame.add(mainPanel);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLocation(0, 0);
        mainFrame.setVisible(true);
        
    }
    
    //Colorie un pixel en x, y (dans la map) avec une couleur de code rgb {color[0], color[1], color[2]}
    public static void addPx(int x, int y, int[] color) {
        
    	int[] rectSize = {mainFrame.getWidth()/(mainFrame.getWidth()/Main.pxSize), mainFrame.getHeight()/(mainFrame.getHeight()/Main.pxSize)};
    	
        Graphics g = mainPanel.getGraphics();
        g.setColor(new Color(color[0], color[1], color[2]));
        g.fillRect(x*Main.pxSize, y*Main.pxSize, rectSize[0], rectSize[1]);
        g.setColor(Color.magenta);
    }
    
    //Renvoie les coordonnées dans la map avec comme paramètre les coordonnées en pixel de l'image initiale
    public static int[] pxToCoords(int[] xy) {
        return(new int[] {
                xy[0]/(mainFrame.getWidth()/(mainFrame.getWidth()/Main.pxSize)),
                xy[1]/(mainFrame.getHeight()/(mainFrame.getHeight()/Main.pxSize))
        });
    }
}