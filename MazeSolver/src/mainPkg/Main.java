package mainPkg;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;

public class Main {
    
    public static int pxSize = 0;
    
    public static ArrayList<ArrayList<Integer>> map = new ArrayList<>();
        
    public static int[] entryPx = {0,0};

    public static File txt;
    public static FileWriter writer;
    public static File imgFile;
    public static BufferedImage img;
    
    public static void main(String[] args) throws IOException {
        
        try {
            
            imgFile = new File(System.getProperty("user.home") + "//Desktop//maze.png");
            img = ImageIO.read(imgFile);
            
        } catch (Exception e) {
            System.out.println("\nCould not load maze file, make sure it is on your Desktop and named \"maze.png\".\n\nIf you don't have a maze, you can find one at : https://keesiemeijer.github.io/maze-generator/#generate\nDon't forget to put the entries in \"top and bottom\".\n");
            System.exit(0);
        }
        
        boolean pxSizeFound = false;
        boolean entryPxFound = false;
        
        //S'occupe de calculer la taille d'un pixel et de trouver le pixel de début
        if(!pxSizeFound) {
            for (int i = 0; i < img.getWidth()-1; i++) {
                if(new Color(img.getRGB(i, 0)).getRed() == 255 && new Color(img.getRGB(i, 0)).getGreen() == 255 && new Color(img.getRGB(i, 0)).getBlue() == 255) {
                    if(!entryPxFound) {
                        entryPx[0] = i;
                        entryPxFound = true;
                    }
                    pxSize++;
                }
            }
            if(!entryPxFound) {
                System.out.println("\nThe map isn't in a valid format, the start point must be white (255, 255, 255) and at the top of the map.\n");
                System.exit(0);
            } else {
                System.out.println("The starting point has been found.\nStarting map initialisation.\n-------------------------\n");
            }
        }
        
        Frames.mainFrame();
        
        //Un petit wait sinon le frame a pas le temps de s'initaliser et il manque quelques pixels au milieu
        wait(200);
        
        //S'occupe de faire la map
        for (int i = 0; i < (img.getHeight())/pxSize; i++) {
            
            
            ArrayList<Integer> mapX = new ArrayList<>();
            
            for (int j = 0; j < (img.getWidth())/pxSize; j++) {
                
                Color pix = new Color(img.getRGB(j*pxSize, i*pxSize));
                int[] rgb = {
                        pix.getRed(),
                        pix.getGreen(),
                        pix.getBlue(),                    
                };
                
                if(rgb[0] == 0 && rgb[1] == 0 && rgb[2] == 0) {
                    mapX.add(0);
                } else if(rgb[0] == 255 && rgb[1] == 255 && rgb[2] == 255 && i != ((img.getHeight())/pxSize)-1){
                    mapX.add(1);
                } else if(rgb[0] == 255 && rgb[1] == 255 && rgb[2] == 255 && i == ((img.getHeight())/pxSize)-1) {
                	mapX.add(3);
                }
                else {
                    System.out.println("The map isn't in a valid format, the only colors accepted are : \nwhite (255, 255, 255) for the paths and \nblack (0, 0, 0) for the walls\nError at pixel (\"" + j + "\", \"" + i + "\"), please use a valid color.\n");
                    System.exit(0);
                }
                
                Frames.addPx(j, i, rgb);
                
            }
            map.add(mapX);
        }
        map.get(0).set(Frames.pxToCoords(new int[] {entryPx[0], entryPx[1]})[0], 2);
        System.out.println("map initialisation finished.\nStarting solving process.\n-------------------------\n");
        MazeSolver.main();
    }
    
    
    //Fonction wait, permet d'attendre un temps ms en millisecondes
    public static void wait(int ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (Exception e) {
            
        }
    }

}