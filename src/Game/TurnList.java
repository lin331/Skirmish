package Game;

import Map.Position;
import Player.Path;
import Player.Unit;

public class TurnList {
    private int num; // number of units in list
    private Node head; // start of list

    public TurnList() {
        head = null;
        num = 0;
    }

    /** Node class to be used in list */
    private class Node {
        private Unit unit;
        private Node next;

        public Node(Unit unit, Node next) {
            this.unit = unit;
            this.next = next;
        }
    }

    /** Check if list is empty */
    public boolean isEmpty() {
        return num == 0;
    }

    /** Returns size of list */
    public int size() {
        return num;
    }

    /**
     * Add unit to list
     * 
     * @param unit
     *            Unit to be added
     */
    public void add(Unit unit) {
        head = new Node(unit, head);
        num += 1;
    }

    /** Removes specified unit */
    public void remove(Unit unit) {
        if (head.unit.equals(unit)) {
            head = head.next;
            return;
        }

        Node temp = head;
        Node prev = null;

        while (temp != null && !temp.unit.equals(unit)) {
            prev = temp;
            temp = temp.next;
        }

        if (temp == null) {
            System.out.println("Unit not found");
            return;
        }

        prev.next = temp.next;
        num -= 1;
    }

    public void process() {
        Node node = head;
        while (node != null) {
            Unit u = node.unit;
            Path p = u.getPath();
            if (!p.hasNext()) {
                remove(u);
            } else {
                Position pos = p.getNext();
                u.setPos(pos);
            }
            node = node.next;
        }
    }

    public void print() {
        Node node = head;
        while (node != null) {
            System.out.println(node.unit.getTeam().toString() + " - "
                    + node.unit.toString() + ": "
                    + node.unit.getPath().toString());
            node = node.next;
        }
    }
}
