package io.zipcoder;

import io.zipcoder.utils.FileReader;
import io.zipcoder.utils.Item;

import java.util.*;

public class GroceryReporter {
    private final String originalFileText;

    public GroceryReporter(String jerksonFileName) {
        this.originalFileText = FileReader.readFile(jerksonFileName);
    }

    @Override
    public String toString() {
        ItemParser ip = new ItemParser();
        Map<String, Map<Double, Integer>> map = recordPrices(ip.parseItemList(originalFileText));
        StringBuilder result = new StringBuilder();

        for(String x : map.keySet()){
            result.append("item: " + "\n" + x + "\n");
            result.append("highest $: " + "\n" + highestPrice(map.get(x))+ "\n");
            result.append("lowest $: "  + "\n"+ lowestPrice(map.get(x))+ "\n");
        }

        result.append("\n" + "Errors: " + ip.getExceptionCount());
        return result.toString();
    }

    public Map<String, Map<Double, Integer>> recordPrices(List<Item> items){
        Map<String, Map<Double, Integer>> result = new HashMap<>();
        for(Item x: items){
            if(!result.containsKey(x.getName())){
                result.put(x.getName(), new HashMap<>());
            }
            if(!result.get(x.getName()).containsKey(x.getPrice())){
                result.get(x.getName()).put(x.getPrice(), 1);
            } else {
                result.get(x.getName()).replace(x.getPrice(), result.get(x.getName()).get(x.getPrice()) + 1);
            }
        }
        return result;
    }

    public Double lowestPrice(Map<Double, Integer> map){
        Set<Double> priceSet = map.keySet();
        Double lowest = Double.MAX_VALUE;
        for(Double price : priceSet){
            if(price < lowest) lowest = price;
        }
        return lowest;
    }

    public Double highestPrice(Map<Double, Integer> map){
        Set<Double> priceSet = map.keySet();
        Double highest = Double.MIN_VALUE;
        for(Double price : priceSet){
            if(price > highest) highest = price;
        }
        return highest;
    }
}
