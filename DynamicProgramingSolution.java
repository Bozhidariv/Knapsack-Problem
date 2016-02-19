package com.fmi.jones.indiana;

import java.util.List;

public class DynamicProgramingSolution {
	private int numberOfItems;
	private int capacity;

	public int getNumberOfItems() {
		return numberOfItems;
	}

	public void setNumberOfItems(int numberOfItems) {
		this.numberOfItems = numberOfItems;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public DynamicProgramingSolution(int numberOfitems, int capacity) {
		this.numberOfItems = numberOfItems;
		this.capacity = capacity;
	}

	public void solve(int numberOfItems, int capacity, List<Item> items) {
		int[][] valuesMatrix = new int[numberOfItems + 1][capacity + 1];
		int[][] takeIfItemFitsMatrix = new int[numberOfItems + 1][capacity + 1];

		//for (int w = 0; w < capacity; w++) {
		//	valuesMatrix[0][capacity] = 0;
		//}

		for (int item = 0; item <= numberOfItems; item++) {
			for (int weight = 0; weight <= capacity; weight++) {
				if(item == 0 || weight == 0){
					valuesMatrix[item][weight] = 0;
					takeIfItemFitsMatrix[item][weight] = 0;
				}
				else if (items.get(item - 1).getWeight() <= weight) {
					valuesMatrix[item][weight] = Math.max(valuesMatrix[item - 1][weight],
					        items.get(item - 1).getValue() + valuesMatrix[item - 1][weight - items.get(item - 1).getWeight()]);
					takeIfItemFitsMatrix[item][weight] =  items.get(item - 1).getValue() 
							+ valuesMatrix[item - 1][weight - items.get(item - 1).getWeight()] > valuesMatrix[item - 1][weight] ? 1
									: 0;
				} else {
					valuesMatrix[item][weight] = valuesMatrix[item - 1][weight];
					takeIfItemFitsMatrix[item][weight] = 0;
				}
				// System.out.print(takeIfItemFitsMatrix[item][weight] + " ");
			}
		}

		int w = capacity;
		int[] selected = new int[numberOfItems];

		for (int i = numberOfItems; i >= 1; i--) {
			if (takeIfItemFitsMatrix[i][w] == 1) {
				selected[i - 1] = 1;
				w -= items.get(i - 1).getWeight();
			}
		}

		int maxValue = valuesMatrix[numberOfItems][capacity];
		int maxWeight = 0;
		System.out.println("Maximum value of items to put in the knapsack is " + maxValue);
		System.out.println("The selected items are:");
		for (int i = 0; i < selected.length; i++) {
			if (selected[i] == 1) {
			    maxWeight += items.get(i).getWeight();
				System.out.println("Name: " + items.get(i).getName() + " with Weight: " + items.get(i).getWeight() + " and value: " +items.get(i).getValue());
			}
		}
		System.out.println("Maximum capacity used " + maxWeight );

	}
}
