package com.mycompany.mo_tinkov_lab2_ochered;

import java.util.NoSuchElementException;

public class Ochered {

    // Главная исполняемая процедура для проверки работы очередей
    public static void main(String[] args) {
        
        System.out.println("=== Testirovanie Ocheredi (FIFO) ===");
        LinkedQueue<Integer> queue = new LinkedQueue<>();
        
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        
        System.out.println("Kolichestvo elementov: " + queue.count()); 
        System.out.println("Pervyj element v ocheredi (peek): " + queue.peek()); 
        System.out.println("Izvlechen element (dequeue): " + queue.dequeue()); 
        System.out.println("Izvlechen element (dequeue): " + queue.dequeue()); 
        System.out.println("Ostavsheesya kolichestvo: " + queue.count()); 

        System.out.println("\n========================================\n");

        System.out.println("=== Testirovanie Dvustoronnej Ocheredi (Kolcevoj massiv) ===");
        ArrayDeque<String> deque = new ArrayDeque<>();
        
        deque.enqueueLast("Vtoroj");
        deque.enqueueFirst("Pervyj");
        deque.enqueueLast("Tretij");
        
        System.out.println("Kolichestvo elementov v deke: " + deque.count()); 
        System.out.println("Element s nachala (peekFirst): " + deque.peekFirst()); 
        System.out.println("Element s konca (peekLast): " + deque.peekLast()); 
        
        System.out.println("Udalen s nachala (dequeueFirst): " + deque.dequeueFirst()); 
        System.out.println("Udalen s konca (dequeueLast): " + deque.dequeueLast()); 
        System.out.println("Ostavshijsya element: " + deque.peekFirst()); 
    }
}

// очередь на основе односвязного списка
class LinkedQueue<T> {
    
    // Внутренний класс для описания одиночного узла списка
    private static class Node<T> {
        T value;
        Node<T> next;
        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> head; 
    private Node<T> tail; 
    private int size = 0;

    // Метод добавления нового значения в конец очереди
    public void enqueue(T value) {
        Node<T> newNode = new Node<>(value);
        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    // Метод удаления и возврата значения из начала очереди
    public T dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("Ochered pusta");
        }
        T value = head.value;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
        return value;
    }

    // Метод получения значения из начала очереди без его удаления
    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException("Ochered pusta");
        }
        return head.value;
    }

    // Получение текущего количества элементов в очереди
    public int count() {
        return size;
    }

    // Проверка структуры данных на отсутствие элементов
    public boolean isEmpty() {
        return size == 0;
    }
}

// Класс двусторонней очереди на базе динамического кольцевого массива
class ArrayDeque<T> {
    private T[] items;
    private int size = 0;
    private int head = 0;
    private int tail = -1;

    // Конструктор для создания пустого массива значений
    @SuppressWarnings("unchecked")
    public ArrayDeque() {
        items = (T[]) new Object[0];
    }

    // Внутренний метод изменения размера массива с сохранением порядка следования узлов
    @SuppressWarnings("unchecked")
    private void allocateNewArray(int startingIndex) {
        int newLength = (size == 0) ? 4 : size * 2;
        T[] newArray = (T[]) new Object[newLength];
        if (size > 0) {
            int targetIndex = startingIndex;
            if (tail < head) {
                for (int index = head; index < items.length; index++) {
                    newArray[targetIndex] = items[index];
                    targetIndex++;
                }
                for (int index = 0; index <= tail; index++) {
                    newArray[targetIndex] = items[index];
                    targetIndex++;
                }
            } else {
                for (int index = head; index <= tail; index++) {
                    newArray[targetIndex] = items[index];
                    targetIndex++;
                }
            }
            head = startingIndex;
            tail = targetIndex - 1;
        } else {
            head = 0;
            tail = -1;
        }
        items = newArray;
    }

    // Метод добавления нового значения в начало двусторонней очереди
    public void enqueueFirst(T item) {
        if (items.length == size) {
            allocateNewArray(1);
        }
        if (head > 0) {
            head--;
        } else {
            head = items.length - 1;
        }
        items[head] = item;
        size++;
        if (size == 1) {
            tail = head;
        }
    }

    // Метод добавления нового значения в конец двусторонней очереди
    public void enqueueLast(T item) {
        if (items.length == size) {
            allocateNewArray(0);
        }
        if (tail == items.length - 1) {
            tail = 0;
        } else {
            tail++;
        }
        items[tail] = item;
        size++;
        if (size == 1) {
            head = tail;
        }
    }

    // Метод удаления и возврата значения из начала двусторонней очереди
    public T dequeueFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Ochered pusta");
        }
        T value = items[head];
        items[head] = null; 
        if (head == items.length - 1) {
            head = 0;
        } else {
            head++;
        }
        size--;
        return value;
    }

    // Метод удаления и возврата значения с конца двусторонней очереди
    public T dequeueLast() {
        if (size == 0) {
            throw new NoSuchElementException("Ochered pusta");
        }
        T value = items[tail];
        items[tail] = null; 
        if (tail == 0) {
            tail = items.length - 1;
        } else {
            tail--;
        }
        size--;
        return value;
    }

    // Метод получения значения из начала двусторонней очереди без его удаления
    public T peekFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Ochered pusta");
        }
        return items[head];
    }

    // Метод получения значения с конца двусторонней очереди без его удаления
    public T peekLast() {
        if (size == 0) {
            throw new NoSuchElementException("Ochered pusta");
        }
        return items[tail];
    }

    // Получение текущего количества элементов в двусторонней очереди
    public int count() {
        return size;
    }

    // Проверка структуры данных на отсутствие элементов
    public boolean isEmpty() {
        return size == 0;
    }
}
