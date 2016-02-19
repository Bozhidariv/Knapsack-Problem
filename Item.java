package com.fmi.jones.indiana;

import java.util.Comparator;

public class Item {
    String name;
    int value;
    int weight;

    public Item(String name, int weight, int value) {
        this.name = name;
        this.weight = weight;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public double ratio() {
        return this.value / this.weight;
    }

    public static Comparator<Item> byRatio() {
        return new Comparator<Item>() {
            public int compare(Item firstItem, Item secondItem) {
                return Double.compare(secondItem.ratio(), firstItem.ratio());
            }
        };
    }

}
