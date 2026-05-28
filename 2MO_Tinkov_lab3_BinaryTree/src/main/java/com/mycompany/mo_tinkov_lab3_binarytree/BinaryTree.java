package com.mycompany.mo_tinkov_lab3_binarytree;

import java.util.function.Consumer;

// Двоичное дерево поиска с поддержкой сравнения сопоставимых величин
public class BinaryTree<T extends Comparable<T>> {

    // Внутренний класс для описания одиночного узла дерева
    private static class BinaryTreeNode<T> {
        private T value;
        private BinaryTreeNode<T> left;
        private BinaryTreeNode<T> right;

        public BinaryTreeNode(T value) {
            this.value = value;
        }

        public T getValue() { return value; }
        public void setValue(T value) { this.value = value; }
        public BinaryTreeNode<T> getLeft() { return left; }
        public void setLeft(BinaryTreeNode<T> left) { this.left = left; }
        public BinaryTreeNode<T> getRight() { return right; }
        public void setRight(BinaryTreeNode<T> right) { this.right = right; }
    }

       // Корневой узел и счетчик общего количества элементов дерева
    private BinaryTreeNode<T> head;
    private int count = 0;

    // Публичный метод вставки нового значения в дерево
    public void add(T value) {
        if (head == null) {
            head = new BinaryTreeNode<>(value);
        } else {
            addTo(head, value);
        }
        count++;
    }

    // Рекурсивный метод поиска места вставки и добавления узла
    private void addTo(BinaryTreeNode<T> node, T value) {
        if (value.compareTo(node.getValue()) < 0) {
            if (node.getLeft() == null) {
                node.setLeft(new BinaryTreeNode<>(value));
            } else {
                addTo(node.getLeft(), value);
            }
        } else {
            if (node.getRight() == null) {
                node.setRight(new BinaryTreeNode<>(value));
            } else {
                addTo(node.getRight(), value);
            }
        }
    }


    // Метод проверки наличия заданного значения в дереве
    public boolean contains(T value) {
        NodeParentPair<T> pair = findWithParent(value);
        return pair.current != null;
    }

    // Вспомогательный класс для возврата найденного узла и его родителя
    private static class NodeParentPair<T> {
        BinaryTreeNode<T> current;
        BinaryTreeNode<T> parent;
        NodeParentPair(BinaryTreeNode<T> current, BinaryTreeNode<T> parent) {
            this.current = current;
            this.parent = parent;
        }
    }


    // Метод поиска узла и его родительского элемента в дереве
    private NodeParentPair<T> findWithParent(T value) {
        BinaryTreeNode<T> current = head;
        BinaryTreeNode<T> parent = null;

        while (current != null) {
            int result = current.getValue().compareTo(value);
            if (result > 0) {
                parent = current;
                current = current.getLeft();
            } else if (result < 0) {
                parent = current;
                current = current.getRight();
            } else {
                break;
            }
        }
        return new NodeParentPair<>(current, parent);
    }


    // Метод удаления узла из дерева с перестройкой связей потомков
    public boolean remove(T value) {
        NodeParentPair<T> pair = findWithParent(value);
        BinaryTreeNode<T> current = pair.current;
        BinaryTreeNode<T> parent = pair.parent;

        if (current == null) {
            return false;
        }

        count--;

        if (current.getRight() == null) {
            if (parent == null) {
                head = current.getLeft();
            } else {
                int result = parent.getValue().compareTo(current.getValue());
                if (result > 0) {
                    parent.setLeft(current.getLeft());
                } else if (result < 0) {
                    parent.setRight(current.getLeft());
                }
            }
        }
        else if (current.getRight().getLeft() == null) {
            current.getRight().setLeft(current.getLeft());
            if (parent == null) {
                head = current.getRight();
            } else {
                int result = parent.getValue().compareTo(current.getValue());
                if (result > 0) {
                    parent.setLeft(current.getRight());
                } else if (result < 0) {
                    parent.setRight(current.getRight());
                }
            }
        }
        else {
            BinaryTreeNode<T> leftmost = current.getRight().getLeft();
            BinaryTreeNode<T> leftmostParent = current.getRight();

            while (leftmost.getLeft() != null) {
                leftmostParent = leftmost;
                leftmost = leftmost.getLeft();
            }

            leftmostParent.setLeft(leftmost.getRight());
            leftmost.setLeft(current.getLeft());
            leftmost.setRight(current.getRight());

            if (parent == null) {
                head = leftmost;
            } else {
                int result = parent.getValue().compareTo(current.getValue());
                if (result > 0) {
                    parent.setLeft(leftmost);
                } else if (result < 0) {
                    parent.setRight(leftmost);
                }
            }
        }
        return true;
    }


    // Начало прямого обхода по порядку: Корень, Левая ветка, Правая ветка
    public void preOrderTraversal(Consumer<T> action) {
        preOrderTraversal(action, head);
    }

  // Рекурсивный метод выполнения прямого обхода дерева
    private void preOrderTraversal(Consumer<T> action, BinaryTreeNode<T> node) {
        if (node != null) {
            action.accept(node.getValue());
            preOrderTraversal(action, node.getLeft());
            preOrderTraversal(action, node.getRight());
        }
    }

    // Начало обратного обхода по порядку: Левая ветка, Правая ветка, Корень
    public void postOrderTraversal(Consumer<T> action) {
        postOrderTraversal(action, head);
    }

    // Рекурсивный метод выполнения обратного обхода дерева
    private void postOrderTraversal(Consumer<T> action, BinaryTreeNode<T> node) {
        if (node != null) {
            postOrderTraversal(action, node.getLeft());
            postOrderTraversal(action, node.getRight());
            action.accept(node.getValue());
        }
    }

    // Начало внутреннего обхода по порядку: Левая ветка, Корень, Правая ветка
    public void inOrderTraversal(Consumer<T> action) {
        inOrderTraversal(action, head);
    }

    // Рекурсивный метод выполнения симметричного обхода дерева
    private void inOrderTraversal(Consumer<T> action, BinaryTreeNode<T> node) {
        if (node != null) {
            inOrderTraversal(action, node.getLeft());
            action.accept(node.getValue());
            inOrderTraversal(action, node.getRight());
        }
    }

    // Выдача текущего количества накопленных значений внутри разветвления
    public int getCount() {
        return count;
    }

    // Полное очищение дерева и сброс количества значений
    public void clear() {
        head = null;
        count = 0;
    }

   // Главная исполняемая процедура для проверки работы дерева
    public static void main(String[] args) {
        BinaryTree<Integer> tree = new BinaryTree<>();

        System.out.println("--- Proverka Raboty Dvoichnogo Dereva ---");
        System.out.println("Dobavlyaem znacheniya: 8, 4, 10, 2, 6, 3, 7");
        tree.add(8);
        tree.add(4);
        tree.add(10);
        tree.add(2);
        tree.add(6);
        tree.add(3);
        tree.add(7);

        System.out.println("Chislo uzlov vnutri: " + tree.getCount());

        System.out.print("Simmetrichnyi obhod (ot menshego k bolshemu): ");
        tree.inOrderTraversal(element -> System.out.print(element + " "));
        System.out.println();

        System.out.println("Nahoditsya li vnutri chislo 6? " + tree.contains(6));
        System.out.println("Nahoditsya li vnutri chislo 15? " + tree.contains(15));

        System.out.println("Udalyayem znachenie 4...");
        tree.remove(4);

        System.out.print("Simmetrichnyi obhod posle udaleniya: ");
        tree.inOrderTraversal(element -> System.out.print(element + " "));
        System.out.println();
        System.out.println("Chislo uzlov posle udaleniya: " + tree.getCount());
    }
}
