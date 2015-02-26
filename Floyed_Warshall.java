import java.awt.DisplayMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import spiffy.core.util.TwoDHashMap;


public class Floyed_Warshall {
	
	public static TwoDHashMap<Integer, Integer, Integer> mapD = new TwoDHashMap<Integer, Integer, Integer>();
	public static TwoDHashMap<Integer, Integer, Integer> mapS = new TwoDHashMap<Integer, Integer, Integer>();
	
	public static TwoDHashMap<Integer, Integer, Integer> mapDTmp = new TwoDHashMap<Integer, Integer, Integer>();
	public static TwoDHashMap<Integer, Integer, Integer> mapSTmp = new TwoDHashMap<Integer, Integer, Integer>();
	
	public static int vertex;
	public static int iteration;
	public static int iterationMax;
	public static int var1 = 0;
	public static int var2 = 0;
	public static int var3 = 0;
	public static int var4 = 0;
	public static int var5 = 0;
	
	
	public static void calculateMaps() {
		// Berechnung zum f�llen von D1
		// C(ij) aus D(k) = 0 (Feld ist frei), dann
		// C(ij) aus D(k-1) > ( C(ik) + C(kj) ) aus D(k-1)
		// JA: C(ij) aus D(k) = ( C(ik) + C(kj) ) aus D(k-1)
		// NEIN: C(ij) aus D(k) = C(ij) aus D(k-1)
				
		// Berechnung zum f�llen von S1
		// Wenn C(ij) aus D(k-1) und C(ij) aus D(k) gleich sind, dann S(ij) von S(k-1) nach S(ij) von S(k) kopieren
		// Wenn C(ij) aus D(k-1) und C(ij) aus D(k) ungleich sind, dann S(ij) von S(k) = k
		
		for (int i = 1; i <= vertex; i++) {
			for (int j = 1; j <= vertex; j++) {
				//System.out.println(i + " " + j);
				if (mapDTmp.get(i, j) == 0) {
					//System.out.println(mapDTmp.get(i, j));
					
					// Felder auslesen
					var1 = mapD.get(i, j);
					var2 = ( mapD.get(i, iteration) + mapD.get(iteration, j) );
					
					// Wenn einer der beiden Werte (i,k) oder (k,j) oo ist, dann wird var2 auf oo gesetzt
					if ( (mapD.get(i, iteration) == Integer.MAX_VALUE) || (mapD.get(iteration, j) == Integer.MAX_VALUE) ) {
						var2 = Integer.MAX_VALUE;
					}

					if (var1 > var2) {
						mapDTmp.set(i, j, var2);
					} else {
						mapDTmp.set(i, j, var1);
					}
					
					// f�lle S(i,j)
					var3 = mapD.get(i, j);
					var4 = mapDTmp.get(i, j);
					var5 = mapS.get(i, j);
					
					if ( var3 == var4 ) {
						mapSTmp.set(i, j, var5);
					} else {
						mapSTmp.set(i, j, iteration);
					}
				}
								
			}
		}
		
		// copy mapDTmp / mapSTmp -> mapD / mapS
		for (int i = 1; i <= vertex; i++) {
			for (int j = 1; j <= vertex; j++) {
				mapD.set(i, j, mapDTmp.get(i,j));
				mapS.set(i, j, mapSTmp.get(i,j));
			}
		}
	}
	
	public static void fillMaps() {
		// Map mapDTmp / mapSTmp, Zeile/Spalte der Iteration k aus mapD / mapS kopieren
		// Zeile/Spalte k aus d(k-1)/s(k-1) kopieren
				
				// f�lle mapDTmp
				for (int i = 1; i <= vertex; i++) {
					for (int j = 1; j <= vertex; j++) {
						if (i == j) {
							mapDTmp.set(i, j, -1);
						
						} else if ((i == iteration) || (j == iteration) ) {
							mapDTmp.set(i, j, mapD.get(i, j));
						} else {
							mapDTmp.set(i, j, 0);
						}
					}
				}
				
				System.out.println("");
				System.out.println("Iteration: " + iteration + " / " + iterationMax);
				System.out.println("mapDTmp - " + iteration + ". Spalte/Zeile aus D" + (iteration-1) + " kopiert");
				for (int i = 1; i <= vertex; i++) {
					System.out.print("[ ");
					for (int j = 1; j <= vertex; j++) {
						System.out.print(mapDTmp.get(i, j) + " ");
					}
					System.out.print("]");
					System.out.println("");
				}
				
				// f�lle mapSTmp
				for (int i = 1; i <= vertex; i++) {
					for (int j = 1; j <= vertex; j++) {
						if (i == j) {
							mapSTmp.set(i, j, -1);
						
						} else if ((i == iteration) || (j == iteration) ) {
							mapSTmp.set(i, j, mapS.get(i, j));
						} else {
							mapSTmp.set(i, j, 0);
						}
						
					}
				}
				
				System.out.println("");
				System.out.println("Iteration: " + iteration + " / " + iterationMax);
				System.out.println("mapSTmp - " + iteration + ". Spalte/Zeile aus S" + (iteration-1) + " kopiert");
				for (int i = 1; i <= vertex; i++) {
					System.out.print("[ ");
					for (int j = 1; j <= vertex; j++) {
						System.out.print(mapSTmp.get(i, j) + " ");
					}
					System.out.print("]");
					System.out.println("");
				}
	}
	
	public static void displayMaps() {
		
		System.out.println("");
		System.out.println("mapDTmp nach Berechnung");
		System.out.println("Iteration: " + iteration + " / " + iterationMax);
		for (int i = 1; i <= vertex; i++) {
			System.out.print("[ ");
			for (int j = 1; j <= vertex; j++) {
				System.out.print(mapDTmp.get(i, j) + " ");
			}
			System.out.print("]");
			System.out.println("");
		}
		
		System.out.println("");
		System.out.println("mapSTmp nach Berechnung");
		System.out.println("Iteration: " + iteration + " / " + iterationMax);
		for (int i = 1; i <= vertex; i++) {
			System.out.print("[ ");
			for (int j = 1; j <= vertex; j++) {
				System.out.print(mapSTmp.get(i, j) + " ");
			}
			System.out.print("]");
			System.out.println("");
		}
	}

	public static void main(String[] args) {
		
		vertex = 4;
		iteration = 0;
		iterationMax = vertex - 1;
				
		// F�lle mapD
		mapD.set(1, 2, 2);
		mapD.set(2, 1, 2);
		mapD.set(1, 3, 4);
		mapD.set(3, 1, 4);
		mapD.set(3, 1, 4);
		mapD.set(1, 4, Integer.MAX_VALUE);
		mapD.set(4, 1, Integer.MAX_VALUE);
		mapD.set(2, 3, 1);
		mapD.set(3, 2, 1);
		mapD.set(2, 4, 5);
		mapD.set(4, 2, 5);
		mapD.set(3, 4, 3);
		mapD.set(4, 3, 3);

		mapD.set(1, 1, -1);
		mapD.set(2, 2, -1);
		mapD.set(3, 3, -1);
		mapD.set(4, 4, -1);
	
		
		System.out.println("mapD0 - gef�llt mit Ausgangsdaten");
		for (int i = 1; i <= 4; i++) {
			System.out.print("[ ");
			for (int j = 1; j <= 4; j++) {
				System.out.print(mapD.get(i, j) + " ");
			}
			System.out.print("]");
			System.out.println("");
		}

		// f�lle mapS
		for (int i = 1; i <= 4; i++) {
			for (int j = 1; j <= 4; j++) {
				if (i == j) {
					mapS.set(i, j, -1);
				
				} else {
					mapS.set(i, j, j);
				}
			}
		}
		
		System.out.println("");
		System.out.println("mapS0 - gef�llt mit Ausgangsdaten");
		for (int i = 1; i <= 4; i++) {
			System.out.print("[ ");
			for (int j = 1; j <= 4; j++) {
				System.out.print(mapS.get(i, j) + " ");
			}
			System.out.print("]");
			System.out.println("");
		}
		
		for (iteration = 1; iteration <= iterationMax; iteration++) {
			// Kopiere k. Zeile/Spalte nach mapDTmp / mapSTmp
			fillMaps();
			
			// Berechne Map f�r aktuelle Iteration
			calculateMaps();
			
			// Ausgabe
			displayMaps();
		}

		


	}
	
}