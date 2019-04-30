package ua.procamp.bst;

import lombok.Data;

import java.util.function.Consumer;

import static java.lang.Math.max;
import static java.util.Optional.ofNullable;

@SuppressWarnings("all")
public class RecursiveBinarySearchTree<T extends Comparable> implements BinarySearchTree<T> {

    private Node<T> root;
    private int size;
    private int heightLeft;
    private int heightRight;

    @Override
    public boolean insert(T element) {
        if (this.root == null) {
            this.root = new Node<>(element);
            this.size++;
            return true;
        } else {
            return insertRecursively(root, element);
        }
    }

    @Override
    public boolean search(T element) {
        if (this.root == null) {
            return false;
        } else {
            return searchRecursively(root, element);
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int height() {
        return max(heightLeft, heightRight);
    }

    @Override
    public void inOrderTraversal(Consumer<T> consumer) {
        inOrderTraversal(root, consumer);
    }

    @Data
    private static class Node<T> {
        T element;
        Node<T> left;
        Node<T> right;

        Node(T element) {
            this.element = element;
        }
    }

    private boolean insertRecursively(Node<T> node, T element) {
        if (node.getElement().compareTo(element) > 0) {
            return insertIntoLeftSubtree(node, element);
        } else if (node.getElement().compareTo(element) < 0) {
            return insertIntoRightSubtree(node, element);
        } else {
            return false;
        }
    }

    private boolean insertIntoRightSubtree(Node<T> node, T element) {
        return ofNullable(node.getRight())
                .map(rightNode -> insertRecursively(rightNode, element))
                .orElseGet(() -> {
                    node.setRight(new Node<>(element));
                    this.size++;
                    this.heightRight++;
                    return true;
                });
    }

    private boolean insertIntoLeftSubtree(Node<T> node, T element) {
        return ofNullable(node.getLeft())
                .map(leftNode -> insertRecursively(leftNode, element))
                .orElseGet(() -> {
                    node.setLeft(new Node<>(element));
                    this.size++;
                    this.heightLeft++;
                    return true;
                });
    }

    private void inOrderTraversal(Node<T> node, Consumer<T> consumer) {
        if (node != null) {
            inOrderTraversal(node.getLeft(), consumer);
            consumer.accept(node.getElement());
            inOrderTraversal(node.getRight(), consumer);
        }
    }

    private boolean searchRecursively(Node<T> node, T element) {
        if (node.getElement().compareTo(element) > 0) {
            return ofNullable(node.getLeft())
                    .map(leftNode -> searchRecursively(leftNode, element)).orElse(false);
        } else if (node.getElement().compareTo(element) < 0) {
            return ofNullable(node.getRight())
                    .map(rigthtNode -> searchRecursively(rigthtNode, element)).orElse(false);
        } else {
            return true;
        }
    }
}