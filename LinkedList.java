/**
 * Represents a list of Nodes. 
 */
public class LinkedList {
	
	private Node first; // pointer to the first element of this list
	private Node last;  // pointer to the last element of this list
	private int size;   // number of elements in this list
	
	/**
	 * Constructs a new list.
	 */ 
	public LinkedList () {
		first = null;
		last = first;
		size = 0;
	}
	
	/**
	 * Gets the first node of the list
	 * @return The first node of the list.
	 */		
	public Node getFirst() {
		return this.first;
	}

	/**
	 * Gets the last node of the list
	 * @return The last node of the list.
	 */		
	public Node getLast() {
		return this.last;
	}
	
	/**
	 * Gets the current size of the list
	 * @return The size of the list.
	 */		
	public int getSize() {
		return this.size;
	}
	
	/**
	 * Gets the node located at the given index in this list. 
	 * 
	 * @param index
	 *        the index of the node to retrieve, between 0 and size
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than the list's size
	 * @return the node at the given index
	 */		
	public Node getNode(int index) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException(
					"index must be between 0 and size");
		}
		//Initializes a list iterator
		ListIterator itr = this.iterator();
		//Iterates through the list until we get to the desired index
		for (int i = 0; i < index; i++){
			itr.next();
		}
		return itr.current;
	}

	
	/**
	 * Creates a new Node object that points to the given memory block, 
	 * and inserts the node at the given index in this list.
	 * <p>
	 * If the given index is 0, the new node becomes the first node in this list.
	 * <p>
	 * If the given index equals the list's size, the new node becomes the last 
	 * node in this list.
     * <p>
	 * The method implementation is optimized, as follows: if the given 
	 * index is either 0 or the list's size, the addition time is O(1). 
	 * 
	 * @param block
	 *        the memory block to be inserted into the list
	 * @param index
	 *        the index before which the memory block should be inserted
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than the list's size
	 */
	public void add(int index, MemoryBlock block) {
		//Checks that the index is well-defined
		if (index < 0 || index > size) {
			throw new IllegalArgumentException("index must be between 0 and size");
		}
		//Creates a new node with the given memory block
		Node insert = new Node(block);
		//If the index is 0, add to the beginning of the list
		if (index == 0){
			insert.next = first;
			first = insert;
			//If the size is 0, then the node will be the first and the last
			if (size == 0) {
				last = insert;
			}
		}
		//If the index is the size, add to the end of the list
		if (index == size){
			last.next = insert;
			last = insert;
		}
		//Inserting the node in the middle
		else{
			ListIterator itr = this.iterator();
			for (int i = 0; i < index; i++){
				itr.next();
			}
			insert.next = itr.current.next;
			itr.current.next = insert;
		}
		size++;
	}

	/**
	 * Creates a new node that points to the given memory block, and adds it
	 * to the end of this list (the node will become the list's last element).
	 * 
	 * @param block
	 *        the given memory block
	 */
	public void addLast(MemoryBlock block) {
		this.add(size, block);
	}
	
	/**
	 * Creates a new node that points to the given memory block, and adds it 
	 * to the beginning of this list (the node will become the list's first element).
	 * 
	 * @param block
	 *        the given memory block
	 */
	public void addFirst(MemoryBlock block) {
		this.add(0, block);
	}

	/**
	 * Gets the memory block located at the given index in this list.
	 * 
	 * @param index
	 *        the index of the retrieved memory block
	 * @return the memory block at the given index
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than or equal to size
	 */
	public MemoryBlock getBlock(int index) {
		//Checks that the index is well-defined
		if (index < 0 || index > size) {
			throw new IllegalArgumentException("index must be between 0 and size");
		}
		//Gets the node at the desired index
		Node current = getNode(index);
		return current.block;
	}	

	/**
	 * Gets the index of the node pointing to the given memory block.
	 * 
	 * @param block
	 *        the given memory block
	 * @return the index of the block, or -1 if the block is not in this list
	 */
	public int indexOf(MemoryBlock block) {
		//Calls the iterator
		ListIterator itr = this.iterator();
		int index = 0;
		//Breaks from the loop when the iterator goes through the whole list
		while (itr.hasNext()){
			//returns the index if it finds the block
			if (itr.current.block.equals(block)){
				return index;
			}
			itr.next();
			index++;
		}
		return -1;
	}

	/**
	 * Removes the given node from this list.	
	 * 
	 * @param node
	 *        the node that will be removed from this list
	 */
	public void remove(Node node) {
		//Gets the block of the given node
		MemoryBlock block1 = node.block;
		ListIterator itr = iterator();
		//Stops when current is the given node
		while (itr.hasNext() && !itr.current.block.equals(block1)){
			itr.next();
		}
		//Removes given node
		if (itr.current != null) {
			node.next = itr.current.next.next;
			itr.current.next = node;
			size--;
		}
	}

	/**
	 * Removes from this list the node which is located at the given index.
	 * 
	 * @param index the location of the node that has to be removed.
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than or equal to size
	 */
	public void remove(int index) {
		//Checks that the index is well-defined
		if (index < 0 || index > size) {
			throw new IllegalArgumentException("index must be between 0 and size");
		}
		//Removing the node
		ListIterator itr = this.iterator();
		int count = 0;
		while (count != index){
			itr.next();
			count ++;
		}
		remove(itr.current);
	}

	/**
	 * Removes from this list the node pointing to the given memory block.
	 * 
	 * @param block the memory block that should be removed from the list
	 * @throws IllegalArgumentException
	 *         if the given memory block is not in this list
	 */
	public void remove(MemoryBlock block) {
		//Finds the index of the block
		int index = indexOf(block);
		//The memory block is not in the list
		if (index == -1){
			throw new IllegalArgumentException("Memory block is not in this list.");
		}
		//Removes the node pointing to the memory block
		else {
			this.remove(index);
		}
	}	

	/**
	 * Returns an iterator over this list, starting with the first element.
	 */
	public ListIterator iterator(){
		return new ListIterator(first);
	}
	
	/**
	 * A textual representation of this list, for debugging.
	 */
	public String toString() {
		return " ";
	}
}