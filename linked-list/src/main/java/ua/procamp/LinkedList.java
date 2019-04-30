package ua.procamp;

import lombok.Data;

import java.util.Objects;

/**
 * {@link LinkedList} is a list implementation that is based on singly linked generic nodes. A node is implemented as
 * inner static class {@link Node<T>}. In order to keep track on nodes, {@link LinkedList} keeps a reference to a head node.
 *
 * @param <T> generic type parameter
 */
@SuppressWarnings("all")
public class LinkedList<T> implements List<T> {

    private Node<T> head;
    private int size;

    /**
     * This method creates a list of provided elements
     *
     * @param elements elements to add
     * @param <T>      generic type
     * @return a new list of elements the were passed as method parameters
     */
    public static <T> List<T> of(T... elements) {
        List<T> list = new LinkedList<>();
        for (T element : elements) {
            list.add(element);
        }
        return list;
    }

    /**
     * Adds an element to the end of the list
     *
     * @param element element to add
     */
    @Override
    public void add(T element) {
        add(size, element);
    }

    /**
     * Adds a new element to the specific position in the list. In case provided index in out of the list bounds it
     * throws {@link IndexOutOfBoundsException}
     *
     * @param index   an index of new element
     * @param element element to add
     */
    @Override
    public void add(int index, T element) {
        Node<T> newNode = new Node<>(element);
        if (index == 0) {
            if (this.head == null) {
                this.head = newNode;
            } else {
                newNode.next = this.head;
                this.head = newNode;
            }
        } else {
            Node<T> nodeByIndex = findNodeByIndex(index - 1);
            newNode.next = nodeByIndex.next;
            nodeByIndex.next = newNode;
        }
        this.size++;
    }

    /**
     * Changes the value of an list element at specific position. In case provided index in out of the list bounds it
     * throws {@link IndexOutOfBoundsException}
     *
     * @param index   an position of element to change
     * @param element a new element value
     */
    @Override
    public void set(int index, T element) {
        Node<T> nodeByIndex = findNodeByIndex(index);
        nodeByIndex.setElement(element);
    }

    /**
     * Retrieves an elements by its position index. In case provided index in out of the list bounds it
     * throws {@link IndexOutOfBoundsException}
     *
     * @param index element index
     * @return an element value
     */
    @Override
    public T get(int index) {
        return findNodeByIndex(index).getElement();
    }

    /**
     * Removes an elements by its position index. In case provided index in out of the list bounds it
     * throws {@link IndexOutOfBoundsException}
     *
     * @param index element index
     */
    @Override
    public void remove(int index) {
        if (index == 0) {
            this.head = this.head.next;
        } else {
            Node<T> nodeByIndex = findNodeByIndex(index);
            Node<T> previous = findNodeByIndex(index - 1);
            previous.next = nodeByIndex.next;
            nodeByIndex = null;
        }
        this.size--;
    }

    /**
     * Checks if a specific exists in he list
     *
     * @return {@code true} if element exist, {@code false} otherwise
     */
    @Override
    public boolean contains(T element) {
        Node<T> curr = this.head;
        for (int i = 0; i < size; i++) {
            if (curr.getElement().equals(element)) {
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    /**
     * Checks if a list is empty
     *
     * @return {@code true} if list is empty, {@code false} otherwise
     */
    @Override
    public boolean isEmpty() {
        return this.size == 0 ? true : false;
    }

    /**
     * Returns the number of elements in the list
     *
     * @return number of elements
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Removes all list elements
     */
    @Override
    public void clear() {
        this.head = null;
        this.size = 0;
    }

    @Data
    private static class Node<T> {
        T element;
        Node<T> next;

        Node(T element) {
            this.element = element;
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Objects.checkIndex(index, size);
        Node<T> currNode = this.head;
        for (int i = 0; i < index; i++) {
            currNode = currNode.next;
        }
        return currNode;
    }
}