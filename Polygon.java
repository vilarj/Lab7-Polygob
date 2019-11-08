package lab7Polygon;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public class Polygon {
	DoublyLinkedList<Line2d> lines;

	public Polygon (String filename) {
		lines = new DoublyLinkedList <Line2d>();
		try {
			File f = new File(filename);
			Scanner scan = new Scanner(f);
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				
				double x = 0;
				double y = 0;
				double x2 = 0;
				double y2 = 0;

				String[] parts = line.split(" ");
				
				for (int i = 0; i < parts.length; i++) {
			    	parts[i] = parts[i].replace("(", "").replace(",", "").replace(")", "");
			    	double temp = Double.parseDouble(parts[i]);
			    	if (i == 0) {
			    		x = temp;
			    	} else if (i == 1) {
			    		y = temp;
			    	} else if (i == 2) {
			    		x2 = temp;
			    	} else {
			    		y2 = temp;
			    	}
			    }
				
				Line2d toAdd = new Line2d(x, y, x2, y2);
				lines.add(toAdd);
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void display() {
		Iterator <Line2d> iter = lines.iterator();
		while (iter.hasNext())
			System.out.println(iter.next());
	}

	public void reverse() {
		lines.reverse();
		Iterator <Line2d> iter = lines.iterator();
		while (iter.hasNext()) {
			iter.next().reverse();
		}
	}

	public DoublyLinkedList<Line2d> getLines() {
		return lines;
	}

}
