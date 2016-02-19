package com.fmi.jones.indiana;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import com.fmi.jones.indiana.Item;

public class BranchAndBoundSolution{
   public List<Item> items;
   public int capacity;
   private class Node implements Comparable<Node> {
      
      public int height;
      List<Item> itemsToTake;
      public int bound;
      public int value;
      public int weight;
      
      public Node() {
         itemsToTake = new ArrayList<Item>();
      }
      
      public Node(Node parent) {
         height = parent.height + 1;
         itemsToTake = new ArrayList<Item>(parent.itemsToTake);
         bound = parent.bound;
         value = parent.value;
         weight = parent.weight;
      }
      
      public int compareTo(Node swap) {
         return (int) (swap.bound - bound);
      }
      
      public void computeBound() {
         int i = height;
         double currentWeight = weight;
         bound = value;
         Item item;
         do {
            item = items.get(i);
            if (currentWeight + item.weight > capacity) break;
            currentWeight += item.weight;
            bound += item.value;
            i++;
         } while (i < items.size());
         bound += (capacity - currentWeight) * item.ratio();
      }
   }
   
   public BranchAndBoundSolution(List<Item> items, int capacity) {
      this.items = items;
      this.capacity = capacity;
   }
   
   public void solve() {
      
      Collections.sort(items, Item.byRatio());
      
      Node best = new Node();
      Node root = new Node();
      root.computeBound();
      
      PriorityQueue<Node> q = new PriorityQueue<Node>();
      q.offer(root);
      
      while (!q.isEmpty()) {
         Node node = q.poll();
         
         if (node.bound > best.value && node.height < items.size() - 1) {
            
            Node with = new Node(node);
            Item item = items.get(node.height);
            with.weight += item.weight;
            
            if (with.weight <= capacity) {
            
               with.itemsToTake.add(items.get(node.height));
               with.value += item.value;
               with.computeBound();
               
               if (with.value > best.value) {
                  best = with;
               }
               if (with.bound > best.value) {
                  q.offer(with);
               }
            }
            
            Node without = new Node(node);
            without.computeBound();
            
            if (without.bound > best.value) {
               q.offer(without);
            }
         }
      }
      System.out.println("Maximum value of items to put in the knapsack is " + best.value);
      System.out.println("The selected items are:");
      for(Item item:best.itemsToTake){
          System.out.println("Name: " + item.getName() + " with Weight: " + item.getWeight() + " and value: " + item.getValue());
      }
      System.out.println("Maximum capacity used " + best.weight);
      
   }
}