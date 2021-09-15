package DataStructure;

import java.util.*;

public class RedBlackTree<K, V> {
    private enum Color {
        RED,
        BLACK
    }

    // an element which represent a NULL node
    private final Node<K, V> NULL_NODE = new Node<>(Color.BLACK);

    {
        NULL_NODE.right = NULL_NODE;
        NULL_NODE.left = NULL_NODE;
        NULL_NODE.parent = NULL_NODE;
    }

    // root of the tree
    private Node<K, V> root = NULL_NODE;

    // comparator which is used to compare nodes
    private final Comparator<K> comparator;

    // a number of nodes in the tree
    int size = 0;

    public RedBlackTree(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    private static class Node<K, V> {
        private K key;
        private V value;
        private Color color;
        private Node<K, V> parent;
        private Node<K, V> left;
        private Node<K, V> right;

        public Node(Color color) {
            this.color = color;
        }

        public Node(K key, V value, Color color, Node<K, V> parent, Node<K, V> left, Node<K, V> right) {
            this.key = key;
            this.value = value;
            this.color = color;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }
    }

    /* Internal private methods */
    private void leftRotate(Node<K, V> x) {
        // не рассматриваем случай, когда x.right == NULL_NODE
        if (x.right == NULL_NODE)
            return;

        Node<K, V> y = x.right;
        x.right = y.left; // установить бетта
        if (y.left != NULL_NODE)
            y.left.parent = x;

        // передать родителя x узлу y
        y.parent = x.parent;

        // проверить случай, когда x - корень дерева
        if (x == root) // также ознчает, что x.parent == NULL_NODE
            root = y;
        else if (x.parent.left == x)
            x.parent.left = y;
        else
            x.parent.right = y;

        // установка связи между узлами y и x
        y.left = x;
        x.parent = y;
    }

    private void rightRotate(Node<K, V> y) {
        // не рассматриваем случай, когда y.left == NULL_NODE
        if (y.left == NULL_NODE)
            return;

        Node<K, V> x = y.left;
        y.left = x.right; // установить бетта
        if (x.right != NULL_NODE)
            x.right.parent = y;

        // передать родителя y узлу x
        x.parent = y.parent;
        // проверить случай, когда y - корень дерева
        if (y == root) // также ознчает, что y.parent == NULL_NODE
            root = x;
        else if (y.parent.left == y)
            y.parent.left = x;
        else
            y.parent.right = x;

        // установка связи между узлами x и y
        x.right = y;
        y.parent = x;
    }

    private Node<K, V> findNodeByKey(K key) {
        Node<K, V> cur = root;
        while (cur != NULL_NODE) {
            int valueCompared = comparator.compare(key, cur.key);
            if (valueCompared == 0)
                // означает, что элемент найден
                return cur;
            else if (valueCompared < 0)
                cur = cur.left;
            else
                cur = cur.right;
        }
        return NULL_NODE;
    }

    /**
     * Ситуацие при вставке, которые нарушают структуру
     * (не удовлетворяют пяти свойствам) красно-черного дерева:
     * 1) Вставленный красный узел является корнем дерева. Будет нарушено свойство (2);
     * 2) Родитель вставленного красного узла - так же красный. Будет нарушено свойство (4);
     * @param z - вставляемый в дерево новый элемент
     */
    private void addOperationFixUpRBT(Node<K, V> z) {
        while (z.parent.color == Color.RED) {
            // случай, если текущий узел находится в левом поддереве относительно прародителя
            if (z.parent == z.parent.parent.left) {
                Node<K, V> uncle = z.parent.parent.right;
                // если дядя красный
                if (uncle.color == Color.RED) {
                    z.parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.right) {
                        z = z.parent;
                        leftRotate(z);
                    }
                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    rightRotate(z.parent.parent);
                }
            }
            // случай, если текущий узел находится в правод поддереве относительно прародителя
            else {
                Node<K, V> uncle = z.parent.parent.left;
                // если дядя красный
                if (uncle.color == Color.RED) {
                    z.parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.left) {
                        z = z.parent;
                        rightRotate(z);
                    }
                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    leftRotate(z.parent.parent);
                }
            }
        }
        root.color = Color.BLACK;
    }

    // input:  parent -> u -> v
    // output: parent -> v. Node 'u' has been deleted
    private void transplantRemoveHelper(Node<K, V> u, Node<K, V> v) {
        if (u.parent == NULL_NODE)
            root = v;
        else if (u == u.parent.left)
            u.parent.left = v;
        else
            u.parent.right = v;
        v.parent = u.parent;
    }

    private void removeOperationFixUpRBT(Node<K, V> x) {
        while (x != root && x.color == Color.BLACK) {
            if (x == x.parent.left) {
                Node<K, V> w = x.parent.right;
                if (w.color == Color.RED) {
                    w.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }
                if (w.left.color == Color.BLACK && w.right.color == Color.BLACK) {
                    w.color = Color.RED;
                    x = x.parent;
                }
                else {
                    if (w.right.color == Color.BLACK) {
                        w.left.color = Color.BLACK;
                        w.color = Color.RED;
                        rightRotate(w);
                        w = x.parent.right;
                    }
                    w.color = x.parent.color;
                    x.parent.color = Color.BLACK;
                    w.right.color = Color.BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            }
            else {
                Node<K, V> w = x.parent.left;
                if (w.color == Color.RED) {
                    w.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    leftRotate(x.parent);
                    w = x.parent.left;
                }
                if (w.right.color == Color.BLACK && w.left.color == Color.BLACK) {
                    w.color = Color.RED;
                    x = x.parent;
                }
                else {
                    if (w.left.color == Color.BLACK) {
                        w.right.color = Color.BLACK;
                        w.color = Color.RED;
                        rightRotate(w);
                        w = x.parent.left;
                    }
                    w.color = x.parent.color;
                    x.parent.color = Color.BLACK;
                    w.left.color = Color.BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            }
        }
        x.color = Color.BLACK;
    }

    public Node<K, V> getMaximumNodeRelatively(Node<K, V> node) {
        while (node.right != NULL_NODE)
            node = node.right;
        return node;
    }

    public Node<K, V> getMinimumNodeRelatively(Node<K, V> node) {
        while (node.left != NULL_NODE)
            node = node.left;
        return node;
    }

    public boolean containsKey(K key) {
        Node<K, V> foundNode = findNodeByKey(key);
        return foundNode != NULL_NODE;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public V get(K key) {
        Node<K, V> rootLocal = root;
        while (rootLocal != NULL_NODE) {
            int valueCompared =  comparator.compare(key, rootLocal.key);
            if (valueCompared < 0)
                rootLocal = rootLocal.left;
            else if (valueCompared > 0)
                rootLocal = rootLocal.right;
            else {
                return rootLocal.value;
            }
        }
        return null;
    }

    public V put(K key, V value) {
        Node<K, V> rootLocal = root;
        Node<K, V> prev = NULL_NODE;
        // поиск необходимого места для вставки узла
        while (rootLocal != NULL_NODE) {
            prev = rootLocal;
            int valueCompared =  comparator.compare(key, rootLocal.key);
            if (valueCompared < 0)
                rootLocal = rootLocal.left;
            else if (valueCompared > 0)
                rootLocal = rootLocal.right;
            else {
                rootLocal.value = value;
                return value;
            }
        }
        // создание нового узла, окрашеного в КРАСНЫЙ цвет
        Node<K, V> newTreeNode = new Node<>(key, value, Color.RED, prev, NULL_NODE, NULL_NODE);
        if (root == NULL_NODE)
            root = newTreeNode;
        else if (comparator.compare(key, prev.key) < 0)
            prev.left = newTreeNode;
        else
            prev.right = newTreeNode;
        this.size++;
        addOperationFixUpRBT(newTreeNode);
        return value;
    }

    public V remove(K key) {
        Node<K, V> z = findNodeByKey((key));
        if (z == NULL_NODE)
            return null;
        Node<K, V> y = z;
        Node<K, V> x;
        Color yOriginalColor = y.color;
        if (z.left == NULL_NODE) {
            x = z.right;
            transplantRemoveHelper(z, z.right);
        }
        else if (z.right == NULL_NODE) {
            x = z.left;
            transplantRemoveHelper(z, z.left);
        }
        else {
            y = getMinimumNodeRelatively(z.right);
            yOriginalColor = y.color;
            x = y.right;
            if (y.parent == z)
                x.parent = y;
            else {
                transplantRemoveHelper(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            transplantRemoveHelper(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        if (yOriginalColor == Color.BLACK)
            removeOperationFixUpRBT(x);
        return z.value;
    }
}