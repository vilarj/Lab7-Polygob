package lab7Polygon;

import java.util.ListIterator;
import java.util.NoSuchElementException;


public class DoublyLinkedList<T> implements ListWithIteratorInterface<T> {
	private DoubleNode head;
	private DoubleNode tail;
	private int numberOfEntries;

	/**
	 * It construct a doubly-LinkedList
	 */
	public DoublyLinkedList() {
		initializeDataFields();
	}

	private class DoubleNode {
		private T data;
		private DoubleNode next;
		private DoubleNode prev;

		public DoubleNode(T data) {
			this.data = data;
			next = null;
			prev = null;
		}

		private T getData() {
			return data;
		}

		private DoubleNode getNext() {
			return next;
		}

		private void setNext(DoubleNode next) {
			this.next = next;
		}

		private DoubleNode getPrev() {
			return prev;
		}

		private void setPrev(DoubleNode prev) {
			this.prev = prev;
		}

		public void setData(T newEntry) {
			this.data = newEntry;

		}
	}
	
	private enum Move {NEXT, PREV};
	

	private class IteratorForLinkedList implements ListIterator<T> {

		
		private DoubleNode nextNode;
		private DoubleNode currNode;
		private DoubleNode prevNode;
		private boolean nextWasCalled = false;
		private Move lastMove;

		public IteratorForLinkedList() {
			nextNode = head; // was getFirstNode();
			currNode = null;
			prevNode = null;
			if (nextNode == null) {
				throw new IllegalStateException("Cannot iterate on empty list");
			}
		}

		public boolean hasNext() {
			return (nextNode != null);
		}

		public T next() {
			if (!hasNext())
				throw new NoSuchElementException("Illegal call: iterator after the end of the list");
			T result = nextNode.getData();
			prevNode = currNode;
			currNode = nextNode;
			nextNode = nextNode.getNext();
			nextWasCalled = true;
			lastMove = Move.NEXT;
			return result;
		}

		public void remove() {
			if (!nextWasCalled)
				throw new IllegalStateException("call to remove " + "without call to next");
			if (prevNode != null)
				prevNode.setNext(nextNode);
			else
				head = nextNode;
			currNode = prevNode;
			nextWasCalled = false;
			lastMove = Move.PREV;
		}

		public boolean hasPrevious() {
			return (currNode != null);
		} // end hasPrevious

		public T previous() {
			if (hasPrevious()) {
				T result = currNode.getData();
				nextNode = currNode;
				currNode = prevNode;
				if(hasPrevious())
					prevNode = prevNode.getPrev();
				nextWasCalled = true;
				return result;
			} else
				throw new NoSuchElementException("Illegal call to previous(); " + "iterator is before beginning of list.");
		} // end previous
		
		public int nextIndex() {
			return DoublyLinkedList.this.getPosition(nextNode.getData());
		}
		
		public int previousIndex() {
			return DoublyLinkedList.this.getPosition(prevNode.getData());
		}

		public void add(T newEntry) {
			nextWasCalled = false;
			// Insert newEntry immediately before the the iterator's current position
			DoublyLinkedList.this.add(nextIndex(), newEntry);
			
		} // end add

		public void set(T newEntry) {
			if (nextWasCalled) {
				if (lastMove.equals(Move.NEXT))
					prevNode.setData(newEntry); // Replace entry last returned by
				// next()
				else {
					assert lastMove.equals(Move.PREV);
					nextNode.setData(newEntry); // Replace entry last returned by
					// previous()
				} // end if
			} else
				throw new IllegalStateException("Illegal call to set(); " + "next() or previous() was not called, OR "
						+ "add() or remove() called since then.");
		} // end set

	}

	
	

	public ListIterator<T> getListIterator() {
		return iterator();
	} // end getIterator
	
	public ListIterator<T> iterator() {
		return new IteratorForLinkedList();
	} // end iterator
	
	private void initializeDataFields() {
		head = null;
		tail = null;
		numberOfEntries = 0;
	}

	@Override
	public void add(int newPosition, T newEntry) {
		if ((newPosition >= 1) && (newPosition <= numberOfEntries + 1)) {
			DoubleNode newNode = new DoubleNode(newEntry);
			if (newPosition == 1) {
				newNode.setNext(head);
				head = newNode;
				tail = newNode;
			} else if(newPosition == numberOfEntries) {
				newNode.setPrev(tail);
				tail.setNext(newNode);
				tail = newNode;
			} else {
				DoubleNode nodeBefore = getNodeAt(newPosition - 1);
				DoubleNode nodeAfter = nodeBefore.getNext();
				newNode.setNext(nodeAfter);
				newNode.setPrev(nodeBefore);
				nodeAfter.setPrev(newNode);
				nodeBefore.setNext(newNode);
			}
			numberOfEntries++;
		} else {
			throw new IndexOutOfBoundsException("Illegal position given to add operation.");
		}
	}

	private DoubleNode getNodeAt(int givenPosition) {
		assert (head != null) && (1 <= givenPosition) && (givenPosition <= numberOfEntries);
		DoubleNode currentNode = head;
		for (int i = 1; i < givenPosition; i++) {
			currentNode = currentNode.getNext();
		}
		assert currentNode != null;
		return currentNode;
	}

	@Override
	public T remove(int givenPosition) {
		T result = null;
		if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
			assert !isEmpty();
			if (givenPosition == 1) {
				result = head.getData();
				head = head.getNext();
			} else if(givenPosition == numberOfEntries){
				result = tail.getData();
				tail = tail.getPrev();
			} else {
				DoubleNode nodeBefore = getNodeAt(givenPosition - 1);
				DoubleNode nodeToRemove = nodeBefore.getNext();
				result = nodeToRemove.getData();
				DoubleNode nodeAfter = nodeToRemove.getNext();
				nodeBefore.setNext(nodeAfter);
			}
			numberOfEntries--;
			return result;
		} else {
			throw new IndexOutOfBoundsException("Illegal position given to remove operation.");
		}
	}

	@Override
	public void clear() {
		initializeDataFields();
	}

	@Override
	public T replace(int givenPosition, T newEntry) {
		if ((givenPosition >= 1) && (givenPosition <= getLength())) {
			assert !isEmpty();
			DoubleNode desiredNode = getNodeAt(givenPosition);
			T originalEntry = desiredNode.getData();
			desiredNode.setData(newEntry);
			return originalEntry;
		} else {
			throw new IndexOutOfBoundsException("Illegal position given to replace operation.");
		}
	}

	@Override
	public T getEntry(int givenPosition) {
		if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
			assert !isEmpty();
			return getNodeAt(givenPosition).getData();
		} else {
			throw new IndexOutOfBoundsException("Illegal position given to getEntry operation");
		}
	}

	@Override
	public T[] toArray() {
		@SuppressWarnings("unchecked")
		T[] result = (T[]) new Object[numberOfEntries];

		int i = 0;
		DoubleNode currentNode = head;
		while ((i < numberOfEntries) && (currentNode != null)) {
			result[i] = currentNode.getData();
			currentNode = currentNode.getNext();
			i++;
		}
		return result;
	}

	@Override
	public boolean contains(T anEntry) {
		boolean found = false;
		DoubleNode currentNode = head;

		while (!found && (currentNode != null)) {
			if (anEntry.equals(currentNode.getData()))
				found = true;
			else
				currentNode = currentNode.getNext();
		}
		return found;
	}
	
	private int getPosition(T anEntry) {
		boolean found = false;
		int count = 0;
		DoubleNode currentNode = head;
		while (!found && (currentNode != null)) {
			if (anEntry.equals(currentNode.getData()))
				found = true;
			else
				currentNode = currentNode.getNext();
			count++;
		}
		return count;
	}

	@Override
	public int getLength() {
		return numberOfEntries;
	}

	@Override
	public boolean isEmpty() {
		boolean result;
		if (numberOfEntries == 0) {
			assert head == null;
			assert tail == null;
			result = true;
		} else {
			assert head != null;
			assert tail != null;
			result = false;
		}
		return result;
	}

	public void reverse() {
		DoubleNode temp = null;
		DoubleNode curr = head;
		tail = curr;
		while (curr != null) {
			temp = curr.getPrev();
			curr.setPrev(curr.getNext());
			curr.setNext(temp);
			curr = curr.getPrev();
		}
		if (temp != null) {
			head = temp.getPrev();
		}

	}

	@Override
	public void add(T newEntry) {
		add(++numberOfEntries, newEntry);

	}
}
