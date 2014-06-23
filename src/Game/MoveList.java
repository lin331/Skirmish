package Game;

public class MoveList {
	// private final int MAXMOVES = 50;
	private int num; // number of moves in list
	private Node head; // start of list
	
	public MoveList() {
		head = new Node();
		num = 0;
		System.out.println(head.move);
	}
	
	private class Node {
		private Move move;
		private Node next;
	}
	
	public boolean isEmpty() {
		return num == 0;
	}
	
	public int size() {
		return num;
	}
	
	/**
	 * Adds move in list based on distance
	 * @param move Move to be added
	 */
	public void add(Move move) {
		if (head == null) {
			Node node = new Node();
			node.move = move;
			node.next = null;
			head = node;
		}
		else {
			Node temp = head;
			while (temp != null) {
				if (move.getDist() <= temp.move.getDist()) {
					Node node = new Node();
					node.move = move;
					node.next = temp.next;
				}
				temp = temp.next;
			}
		}
		num += 1;
	}
	
	/**
	 * Removes first item in list
	 * @return Move object to be processed
	 */
	public Move remove() {
		Move move = head.move;
		head = head.next;
		num -= 1;
		return move;
	}
	
	public void process() {
		boolean current = true;
		while (current) {
			if (head.move.getDist() == 1) {
				Move move = remove();
				move.process();
			}
			else {
				current = false;
			}
		}
	}
	
	public void printMoves() {
		Node temp = head;
		while (temp != null) {
			temp.move.toString();
		}
	}
}
