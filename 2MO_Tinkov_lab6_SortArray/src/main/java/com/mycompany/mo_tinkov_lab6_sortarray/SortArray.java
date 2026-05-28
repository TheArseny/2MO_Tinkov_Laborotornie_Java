package com.mycompany.mo_tinkov_lab6_sortarray;

import java.util.Random;

// Класс для реализации динамического массива и алгоритмов сортировки элементов
public class SortArray<T extends Comparable<T>> {

    private T[] items;
    private int size = 0;

    // Конструктор для создания пустого массива с базовой емкостью ячеек
    @SuppressWarnings("unchecked")
    public SortArray(int capacity) {
        items = (T[]) new Comparable[capacity];
    }

    // Публичный метод добавления нового значения в конец структуры
    public void add(T value) {
        if (size == items.length) {
            T[] newArray = (T[]) new Comparable[items.length * 2];
            System.arraycopy(items, 0, newArray, 0, size);
            items = newArray;
        }
        items[size++] = value;
    }

    // Метод получения копии заполненной части массива для тестирования с созданием нужного типа
    @SuppressWarnings("unchecked")
    public T[] getArrayCopy(Class<T> clazz) {
        T[] copy = (T[]) java.lang.reflect.Array.newInstance(clazz, size);
        System.arraycopy(items, 0, copy, 0, size);
        return copy;
    }

    // Вспомогательный метод обмена значений в массиве по двум индексах
    private void swap(T[] array, int left, int right) {
        if (left != right) {
            T temp = array[left];
            array[left] = array[right];
            array[right] = temp;
        }
    }

    // Алгоритм пузырьковой сортировки элементов массива по возрастанию
    public void bubbleSort(T[] array) {
        boolean swapped;
        do {
            swapped = false;
            for (int i = 1; i < array.length; i++) {
                if (array[i - 1].compareTo(array[i]) > 0) {
                    swap(array, i - 1, i);
                    swapped = true;
                }
            }
        } while (swapped);
    }

    // Публичный интерфейс для запуска быстрой сортировки массива
    public void quickSort(T[] array) {
        if (array.length > 0) {
            quicksortInternal(array, 0, array.length - 1);
        }
    }

    // Внутренний рекурсивный алгоритм деления и сортировки частей массива
    private void quicksortInternal(T[] array, int left, int right) {
        if (left < right) {
            int pivotIndex = left + (right - left) / 2;
            int newPivot = partition(array, left, right, pivotIndex);
            quicksortInternal(array, left, newPivot - 1);
            quicksortInternal(array, newPivot + 1, right);
        }
    }

    // Опорное разделение элементов массива вокруг выбранного значения
    private int partition(T[] array, int left, int right, int pivotIndex) {
        T pivotValue = array[pivotIndex];
        swap(array, pivotIndex, right);
        int storeIndex = left;
        for (int i = left; i < right; i++) {
            if (array[i].compareTo(pivotValue) < 0) {
                swap(array, i, storeIndex);
                storeIndex++;
            }
        }
        swap(array, storeIndex, right);
        return storeIndex;
    }

    // Метод запуска автоматических измерений скорости работы алгоритмов
    public static void main(String[] args) {
        Random rnd = new Random();
        
        System.out.println("=== 1. Izmerenie dlya nebolshogo massiva (100 elementov) ===");
        SortArray<Integer> smallTest = new SortArray<>(100);
        for (int i = 0; i < 100; i++) {
            smallTest.add(rnd.nextInt(1000));
        }
        
        Integer[] smallArray1 = smallTest.getArrayCopy(Integer.class);
        long startTime = System.nanoTime();
        smallTest.bubbleSort(smallArray1);
        long bubbleSmallTime = System.nanoTime() - startTime;
        System.out.println("Vremya puzyrkovoj sortirovki: " + bubbleSmallTime + " ns");

        Integer[] smallArray2 = smallTest.getArrayCopy(Integer.class);
        startTime = System.nanoTime();
        smallTest.quickSort(smallArray2);
        long quickSmallTime = System.nanoTime() - startTime;
        System.out.println("Vremya bystroj sortirovki:    " + quickSmallTime + " ns");

        System.out.println("\n=== 2. Izmerenie dlya krupnogo massiva (10000 elementov) ===");
        SortArray<Integer> largeTest = new SortArray<>(10000);
        for (int i = 0; i < 10000; i++) {
            largeTest.add(rnd.nextInt(100000));
        }

        Integer[] largeArray1 = largeTest.getArrayCopy(Integer.class);
        startTime = System.nanoTime();
        largeTest.bubbleSort(largeArray1);
        long bubbleLargeTime = System.nanoTime() - startTime;
        System.out.println("Vremya puzyrkovoj sortirovki: " + bubbleLargeTime + " ns (" + (bubbleLargeTime / 1_000_000) + " ms)");

        Integer[] largeArray2 = largeTest.getArrayCopy(Integer.class);
        startTime = System.nanoTime();
        largeTest.quickSort(largeArray2);
        long quickLargeTime = System.nanoTime() - startTime;
        System.out.println("Vremya bystroj sortirovki:    " + quickLargeTime + " ns (" + (quickLargeTime / 1_000_000) + " ms)");
    }
}
    
