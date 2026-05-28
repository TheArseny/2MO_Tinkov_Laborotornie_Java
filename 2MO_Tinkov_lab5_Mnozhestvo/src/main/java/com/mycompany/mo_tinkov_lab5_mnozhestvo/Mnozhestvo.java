package com.mycompany.mo_tinkov_lab5_mnozhestvo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// Структура данных множество на основе встроенного динамического списка
public class Mnozhestvo<T extends Comparable<T>> implements Iterable<T> {

    // Внутренний список для хранения уникальных значений множества
    private final List<T> items = new ArrayList<>();

    // Конструктор для создания пустого множества
    public Mnozhestvo() {
    }

    // Конструктор для создания множества на основе переданного перечня значений
    public Mnozhestvo(Iterable<T> items) {
        addRange(items);
    }

    // Метод добавления нового значения с проверкой на дубликаты
    public void add(T item) {
        if (contains(item)) {
            throw new IllegalStateException("Element uzhe sushchestvuet v mnozhestve");
        }
        items.add(item);
    }

    // Метод добавления перечня значений в текущее множество
    public void addRange(Iterable<T> items) {
        for (T item : items) {
            add(item);
        }
    }

    // Метод удаления значения из множества с возвратом успешности операции
    public boolean remove(T item) {
        return items.remove(item);
    }

    // Метод проверки наличия заданного значения внутри множества
    public boolean contains(T item) {
        return items.contains(item);
    }

    // Получение текущего количества элементов в множестве
    public int count() {
        return items.size();
    }

    // Операция объединения текущего множества с другим множеством
    public Mnozhestvo<T> union(Mnozhestvo<T> other) {
        Mnozhestvo<T> result = new Mnozhestvo<>(this.items);
        for (T item : other.items) {
            if (!this.contains(item)) {
                result.add(item);
            }
        }
        return result;
    }

    // Операция пересечения текущего множества с другим множеством
    public Mnozhestvo<T> intersection(Mnozhestvo<T> other) {
        Mnozhestvo<T> result = new Mnozhestvo<>();
        for (T item : this.items) {
            if (other.contains(item)) {
                result.add(item);
            }
        }
        return result;
    }

    // Операция разности текущего множества с другим множеством
    public Mnozhestvo<T> difference(Mnozhestvo<T> other) {
        Mnozhestvo<T> result = new Mnozhestvo<>(this.items);
        for (T item : other.items) {
            result.remove(item);
        }
        return result;
    }

    // Операция симметрической разности текущего множества с другим множеством
    public Mnozhestvo<T> symmetricDifference(Mnozhestvo<T> other) {
        Mnozhestvo<T> unionSet = this.union(other);
        Mnozhestvo<T> intersectionSet = this.intersection(other);
        return unionSet.difference(intersectionSet);
    }

    // Реализация возможности стандартного перебора множества в циклах
    @Override
    public Iterator<T> iterator() {
        return items.iterator();
    }

    // Метод запуска демонстрационных тестов структуры данных
    public static void main(String[] args) {
        System.out.println("--- Testirovanie Mnozhestva ---");
        
        Mnozhestvo<Integer> setA = new Mnozhestvo<>();
        setA.add(1);
        setA.add(2);
        setA.add(3);
        setA.add(4);

        Mnozhestvo<Integer> setB = new Mnozhestvo<>();
        setB.add(3);
        setB.add(4);
        setB.add(5);
        setB.add(6);

        System.out.print("Mnozhestvo A: ");
        setA.forEach(item -> System.out.print(item + " "));
        System.out.println();

        System.out.print("Mnozhestvo B: ");
        setB.forEach(item -> System.out.print(item + " "));
        System.out.println();

        System.out.print("Obedinenie: ");
        setA.union(setB).forEach(item -> System.out.print(item + " "));
        System.out.println();

        System.out.print("Peresechenie: ");
        setA.intersection(setB).forEach(item -> System.out.print(item + " "));
        System.out.println();

        System.out.print("Raznost A - B: ");
        setA.difference(setB).forEach(item -> System.out.print(item + " "));
        System.out.println();

        System.out.print("Simmetricheskaya raznost: ");
        setA.symmetricDifference(setB).forEach(item -> System.out.print(item + " "));
        System.out.println();
    }
}
