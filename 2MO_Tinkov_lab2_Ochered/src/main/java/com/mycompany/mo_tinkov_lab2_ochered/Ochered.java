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
