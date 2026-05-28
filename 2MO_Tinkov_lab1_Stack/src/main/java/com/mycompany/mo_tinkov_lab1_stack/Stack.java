package com.mycompany.mo_tinkov_lab1_stack;

import java.util.EmptyStackException;

// Структура данных стек на основе односвязного списка
public class Stack<T> {

    // Внутренний класс для описания одиночного узла списка
    private static class Node<T> {
        private final T value;
        private final Node<T> next;

        public Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }
    }

    // Ссылка на верхний узел и счетчик количества элементов
    private Node<T> top = null;
    private int count = 0;

    // Метод добавления нового элемента на вершину стека
    public void push(T value) {
        top = new Node<>(value, top);
        count++;
    }

    // Метод удаления и возврата элемента с вершины стека
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        T result = top.value;
        top = top.next;
        count--;
        return result;
    }

    // Метод получения значения на вершине стека без его удаления
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return top.value;
    }

    // Получение текущего количества элементов в стеке
    public int size() {
        return count;
    }

    // Проверка структуры данных на отсутствие элементов
    public boolean isEmpty() {
        return count == 0;
    }

    // Метод запуска демонстрационных тестов структуры данных
    public static void main(String[] args) {
        System.out.println("--- Testirovanie Steka ---");
        
        Stack<String> stack = new Stack<>();
        
        stack.push("Pervyi");
        stack.push("Vtoroi");
        stack.push("Tretii");
        
        System.out.println("Razmer steka: " + stack.size());
        System.out.println("Verkhniy element (peek): " + stack.peek());
        
        System.out.println("Izvlechen: " + stack.pop());
        System.out.println("Izvlechen: " + stack.pop());
        
        System.out.println("Novyi razmer steka: " + stack.size());
    }
}
