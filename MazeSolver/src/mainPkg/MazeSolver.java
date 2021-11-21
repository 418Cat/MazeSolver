package mainPkg;

import java.util.ArrayList;

public class MazeSolver{

	public static ArrayList<int[]> pathsAvailable = new ArrayList<>();
	public static ArrayList<int[]> pathTaken = new ArrayList<>();
	public static int[] coords = {Frames.pxToCoords(Main.entryPx)[0], Frames.pxToCoords(Main.entryPx)[1]};
	public static int[] colors = {255, 51, 51};
	public static int shortestPathSteps = 0;
	public static void main() {

		boolean exitFound = false;
		Frames.addPx(coords[0], coords[1], colors);

		while(!exitFound) {
			
			//Ajoute les chemins adjacents au pixel dans la liste de ceux que le programme peut prendre
			for (int[] i : pathNext(coords)) {
				pathsAvailable.add(i);
			}
			
			pathTaken.add(new int[] {coords[0], coords[1]});
			
			//Si le programme ne peut plus avancer, il va supprimer tous les pixels qui sont entre sa position et le dernier croisement vu
			if(!isPathNext(coords)) {
				for(int[] i : pathTaken) {
					for(int[] j : pathNext(i)) {
						if(j[0] == pathsAvailable.get(pathsAvailable.size()-1)[0] && j[1] == pathsAvailable.get(pathsAvailable.size()-1)[1]) {
							pathTaken.removeIf(n -> pathTaken.indexOf(n) > pathTaken.indexOf(i));
						}
					}
				}
			}  
			
			//Le programme va aux coordonnées du dernier chemin disponible dans la liste puis le supprime de la liste
			coords[0] = pathsAvailable.get(pathsAvailable.size()-1)[0];
			coords[1] = pathsAvailable.get(pathsAvailable.size()-1)[1];
			pathsAvailable.remove(pathsAvailable.size()-1);
			Main.map.get(coords[1]).set(coords[0], 4);
			
			exitFound = (Main.map.get(coords[1]+1).get(coords[0]) == 3);
		}
		
		pathTaken.add(new int[] {coords[0], coords[1]});

		int colorMin = 1; //Valeur minimale de la couleur, à garder entre 0 et 255 exclus
		int colorGradientSlowness = 1; //Nombre de pas nécessaires pour faire changer la couleur
		//Dessine sur la fenêtre le chemin trouvé
		for(int[] i : pathTaken) {
			//S'occupe de faire le changement graduel de couleur
			if(pathTaken.indexOf(i)%colorGradientSlowness == 0) {
				if(colors[0] == 255 && colors[1] < 255 && colors[2] == colorMin) {
					colors[1] += 1;
				} else if(colors[1] == 255 && colors[0] > colorMin) {
					colors[0] += -1;
				} else if(colors[0] == colorMin && colors[2] < 255) {
					colors[2] += 1;
				} else if(colors[1] > colorMin && colors[2] == 255) {
					colors[1] += -1;
				} else if(colors[0] < 255 && colors[1] == colorMin) {
					colors[0] += 1;
				} else if(colors[0] == 255 && colors[2] > colorMin) {
					colors[2] += -1;
				}
			}
			Frames.addPx(i[0], i[1], colors);
		}
		Frames.addPx(coords[0], coords[1]+1, colors);
		System.out.println("The exit was found.\n-------------------------\n");
		
	};
	
	
	
	//Renvoie vrai s'il existe au moins un chemin dans les 4 pixels adjacents
	public static boolean isPathNext(int[] xy) {
		int x = xy[0];
		int y = xy[1];
		boolean path = false;
		
		try {
			path = ((Main.map.get(y).get(x-1) == 1) || (Main.map.get(y).get(x+1) == 1) || (Main.map.get(y-1).get(x) == 1) || (Main.map.get(y+1).get(x) == 1));
		} catch(Exception e) {
		}

		return(path);
	}

	//renvoie, s'ils existent, les chemins adjacents aux coordonnées
	public static ArrayList<int[]> pathNext(int[] xy) {

		int x = xy[0];
		int y = xy[1];

		ArrayList<int[]> tempPaths = new ArrayList<>();
		
		if(Main.map.get(y).get(x-1) == 1) {
			int[] path1 = {x-1, y};
			tempPaths.add(path1);
		}
		if(Main.map.get(y).get(x+1) == 1) {
			int[] path2 = {x+1, y};
			tempPaths.add(path2);
		}
		
		try {
	        if(Main.map.get(y-1).get(x) == 1) {
				int[] path3 = {x, y-1};
				tempPaths.add(path3);
	        }
		} catch(Exception e) {
			
		}
		if(Main.map.get(y+1).get(x) == 1) {
			int[] path4 = {x, y+1};
			tempPaths.add(path4);
		}

		return(tempPaths);
	}

}