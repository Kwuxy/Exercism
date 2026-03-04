class DoublyLinkedList<T> {
    private Element<T> head;

    void push(T value) {
        unshift(value);
        head = head.next;
    }

    T pop() {
        head = head.prev;
        return shift();
    }

    void unshift(T value) {
        Element<T> oldHead = head;
        head = new Element<>(value);
        if (oldHead == null)
            return;

        head.next = oldHead;
        head.prev = oldHead.prev;

        oldHead.prev.next = head;
        oldHead.prev = head;
    }

    T shift() {
        Element<T> element = head;

        if (element.next.value == element.value) {
            head = null;
            return element.value;
        }

        head = element.next;
        element.next.prev = element.prev;
        element.prev.next = element.next;
        return element.value;
    }

    private static final class Element<T> {
        private final T value;
        private Element<T> prev;
        private Element<T> next;

        Element(T value) {
            this.value = value;
            this.prev = this;
            this.next = this;
        }
    }
}
