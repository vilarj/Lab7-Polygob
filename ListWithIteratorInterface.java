package lab7Polygon;

import java.util.Iterator;

public interface ListWithIteratorInterface<T> extends ListInterface<T>, Iterable<T> {
	public Iterator<T> getListIterator();
} // end ListWithIteratorInterface