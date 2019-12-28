package at.jojokobi.donatengine.level;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import at.jojokobi.donatengine.objects.GameObject;

public abstract class TileMapParser {
	
	public TileMapParser () {
		
	}
	
	public abstract List<GameObject> parse(int id, double x, double y, double z);
	
	public static final int[][][] loadTilemap (InputStream input, int layerLength) {
		StringBuilder text = new StringBuilder();
		Scanner scan = new Scanner(input);
		while (scan.hasNextLine()) {
			text.append(scan.nextLine());
			if (scan.hasNextLine()) {
				text.append("\n");
			}
		}
		scan.close();
		return loadTilemap(text.toString(), layerLength);
	}
	
	public static final int[][][] loadTilemap (String text, int layerLength) {
		String[] lines = text.split("\n");
		//The map
		List<int[][]> map = new LinkedList<>();
		//The current layer of the map
		List<int[]> currentLayer = null;
//		int[][][] map = new int[(int) Math.ceil((double) rows.length/layerLength)][layerLength][];
		//The current row in the layer
		int row = 0;
		for (int i = 0; i < lines.length; i++) {
			if (!lines[i].trim().isEmpty()) {
				if (row == 0) {
					if (currentLayer != null) {
						map.add(currentLayer.toArray(new int[][] {}));
					}
					currentLayer = new ArrayList<>();
				}
				String[] cols = lines[i].split(Pattern.quote(","));
				int[] colInts = new int[cols.length];
				//Parse the row
				for (int j = 0; j < colInts.length; j++) {
					System.out.print(cols[j] + " ");
					colInts[j] = Integer.parseInt(cols[j].trim());
				}
				System.out.println();
				currentLayer.add(colInts);
				row++;
				if (row >= layerLength) {
					row = 0;
				}
			}
//			map[i] = new int[layerLength][];
//			for (int j = 0; j < col.length; j++) {
//				map[i][j] = Integer.parseInt(col[j].trim());
//			}
//			if ((i + 1) % layerLength == 0) {
//				layer++;
//			
//			}
		}
		map.add (currentLayer.toArray(new int[][] {}));
		return map.toArray(new int[][][] {});
	}

}
