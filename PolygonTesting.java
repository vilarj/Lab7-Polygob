package lab7Polygon;

import java.util.ListIterator;


public class PolygonTesting {

	public static void main(String[] args) {
		String filename = "polygon.txt";
		
		Polygon p = new Polygon(filename);
		DoublyLinkedList <Line2d> lines = p.getLines();
		ListIterator <Line2d> it = lines.getListIterator();

//		Line2d prevLine = it.next();
//		Line2d firstLine = prevLine;
//		Line2d currLine, gapLine;
                                    /* the following piece is used in the bonus part
		* while (it.hasNext()) {
		*	currLine = it.next();
		* gapLine = Polygon.fillGap (prevLine, currLine);
		*  insert this line in the list 
		*
		* }
		* This piece is for bonus part
		* gapLine = Polygon.fillGap (prevLine, firstLine);
		 *  insert this line in the list 
		*/	
		it = lines.getListIterator();
		while (it.hasNext())
			System.out.println (it.next());

		System.out.println ("======Now backwards======");
		while (it.hasPrevious())
			System.out.println (it.previous());
		
		System.out.println ("======Reverse======");	
		p.reverse();
		p.display();
	}

}
