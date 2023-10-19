package it.unimi.di.sweng;

class DoublyLinkedList<T>{
    private class Node<Q>{
        private Node<Q> prev;
        private Node<Q> next;
        private final Q value;

        public Node(Q value){
            prev = null;
            next = null;
            this.value = value;
        }
        public Node(Node<Q> prev, Q value){
            this.prev = prev;
            this.next = null;
            this.value = value;
        }
        public Node(Node<Q> prev, Node<Q> next,Q value){
            this.prev = prev;
            this.next = next;
            this.value = value;
        }

    }

    private Node<T> head;
    private Node<T> tail;

    public DoublyLinkedList(){
        head = null;
        tail = null;
    }

    public void push(T value){
        if(head == null){
            head = new Node<>(value);
            tail = head;

            return;
        }

        Node<T> temp = new Node<>(tail,value);
        tail.next = temp;
        tail = temp;
    }

    public T pop(){
        if(tail == null) throw new IllegalStateException();
        T ans = tail.value;
        tail = tail.prev;

        if(tail != null) tail.next = null;

        return ans;
    }

    @Override
    public String toString() {
        if(head == null) return "Empty list.";
        Node<T> it = head;
        StringBuilder str = new StringBuilder("[");

        while(it != null){
            str.append(it.value);
            it = it.next;
            if(it != null) str.append(", ");
        }

        return str.append(']').toString();
    }

    public T shift(){
        if(head == null) throw new IllegalStateException();
        T temp = head.value;
        head = head.next;
        if(head != null) head.prev = null;

        return temp;
    }

    public void unshift(T value){
        if(head == null){
            push(value);
            return;
        }

        Node<T> temp = new Node<>(value);
        temp.next = head;
        head.prev = temp;
        head = temp;
    }

    public static void main(String[] args) {
        DoublyLinkedList<Integer> d = new DoublyLinkedList<>();
        d.push(20);
        d.push(30);

        System.out.println(d.pop());
    }
}