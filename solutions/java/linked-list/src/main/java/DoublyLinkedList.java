import java.io.Serializable;

class DoublyLinkedList<E extends Serializable> {
    private Node first;

    DoublyLinkedList() {
        first = null;
    }

    void push(E element) {
        final Node lastNode = getLastNode();

        if(lastNode == null) {
            first = new Node<>(null, element, null);
            return;
        }

        lastNode.next = new Node<>(lastNode, element, null);
    }

    @SuppressWarnings("unchecked")
    E pop() {
        final Node lastNode = getLastNode();
        if(lastNode == null) {
            throw new IllegalStateException("Cannot pop an empty list");
        } else if(lastNode.previous == null) {
            first = null;
        }else{
            lastNode.previous.next = null;
        }
        return (E) lastNode.value;
    }

    @SuppressWarnings("unchecked")
    E shift() {
        if(first == null) {
            return null;
        }

        Node nodeToShift = first;
        first = nodeToShift.next;
        if(first != null) {
            first.previous = null;
        }

        return (E) nodeToShift.value;
    }

    void unshift(E element) {
        if(first == null) {
            first = new Node<>(null, element, null);
        }else{
            first = new Node<>(null, element, first);
            first.next.previous = first;
        }
    }

    @SuppressWarnings("unchecked")
    private Node getLastNode() {
        if(first == null) return null;

        Node element = first;
        Node previous = null;

        while(element != null) {
            previous = element;
            element = element.next;
        }

        return previous;
    }

    private class Node<E> {
        E value;
        Node next;
        Node previous;

        Node(Node previous, E value, Node next) {
            this.value = value;
            this.previous = previous;
            this.next = next;
        }
    }
}
