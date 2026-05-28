package com.mycompany.mo_tinkov_lab4_list;

import java.util.Iterator;
import java.util.NoSuchElementException;

// Структура данных двусвязный список с поддержкой перебора элементов
public class List<T> implements Iterable<T> {

    // Внутренний класс для описания одиночного узла списка с двумя связями
    private static class LinkedListNode<T> {
        private T value;
        private LinkedListNode<T> next;
        private LinkedListNode<T> previous;

        public LinkedListNode(T value) {
            this.value = value;
        }

        public T getValue() { return value; }
        public void setValue(T value) { this.value = value; }
        public LinkedListNode<T> getNext() { return next; }
        public void setNext(LinkedListNode<T> next) { this.next = next; }
        public LinkedListNode<T> getPrevious() { return previous; }
        public void setPrevious(LinkedListNode<T> previous) { this.previous = previous; }
    }

    // Ссылки на первый и последний узлы, а также счетчик количества элементов
    private LinkedListNode<T> head = null;
    private LinkedListNode<T> tail = null;
    private int count = 0;

    // Публичный метод добавления нового значения в конец списка
    public void add(T value) {
        addLast(value);
    }

    // Метод добавления нового значения в начало списка
    public void addFirst(T value) {
        LinkedListNode<T> node = new LinkedListNode<>(value);
        LinkedListNode<T> temp = head;
        head = node;
        head.setNext(temp);
        if (count == 0) {
            tail = head;
        } else {
            temp.setPrevious(head);
        }
        count++;
    }

    // Метод добавления нового значения в конец списка
    public void addLast(T value) {
        LinkedListNode<T> node = new LinkedListNode<>(value);
        if (count == 0) {
            head = node;
        } else {
            tail.setNext(node);
            node.setPrevious(tail);
        }
        tail = node;
        count++;
    }

    // Метод удаления первого узла из начала списка
    public void removeFirst() {
        if (count != 0) {
            head = head.getNext();
            count--;
            if (count == 0) {
                tail = null;
            } else {
                head.setPrevious(null);
            }
        }
    }

    // Метод удаления последнего узла из конца списка
    public void removeLast() {
        if (count != 0) {
            if (count == 1) {
                head = null;
                tail = null;
            } else {
                tail.getPrevious().setNext(null);
                tail = tail.getPrevious();
            }
            count--;
        }
    }

    // Метод удаления первого найденного узла с заданным значением
    public boolean remove(T item) {
        LinkedListNode<T> previousNode = null;
        LinkedListNode<T> current = head;

        while (current != null) {
            if (current.getValue().equals(item)) {
                if (previousNode != null) {
                    previousNode.setNext(current.getNext());
                    if (current.getNext() == null) {
                        tail = previousNode;
                    } else {
                        current.getNext().setPrevious(previousNode);
                    }
                    count--;
                } else {
                    removeFirst();
                }
                return true;
            }
            previousNode = current;
            current = current.getNext();
        }
        return false;
    }

    // Метод проверки наличия заданного значения внутри списка
    public boolean contains(T item) {
        LinkedListNode<T> current = head;
        while (current != null) {
            if (current.getValue().equals(item)) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    // Получение текущего количества элементов в списке
    public int size() {
        return count;
    }

    // Проверка структуры данных на отсутствие элементов
    public boolean isEmpty() {
        return count == 0;
    }

    // Полное очищение списка и сброс количества значений
    public void clear() {
        head = null;
        tail = null;
        count = 0;
    }

    // Реализация возможности стандартного перебора списка в циклах
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private LinkedListNode<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T value = current.getValue();
                current = current.getNext();
                return value;
            }
        };
    }

    // Метод запуска демонстрационных тестов структуры данных
    public static void main(String[] args) {
        System.out.println("--- Testirovanie Dvustoronnego Spiska ---");
        List<Integer> list = new List<>();

        list.addLast(20);
        list.addFirst(10);
        list.addLast(30);

        System.out.println("Kolichestvo elementov: " + list.size()); 
        System.out.println("Soderzhit li 20? " + list.contains(20)); 

        System.out.println("Udalyem element 20...");
        list.remove(20);

        System.out.print("Obhod spiska ot nachala k koncu: ");
        for (Integer item : list) {
            System.out.print(item + " ");
        }
        System.out.println();

        list.removeFirst();
        System.out.println("Kolichestvo posle udalenija pervogo: " + list.size());
    }
}
