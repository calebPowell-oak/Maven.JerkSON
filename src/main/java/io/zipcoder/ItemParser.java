package io.zipcoder;

import io.zipcoder.utils.Item;
import io.zipcoder.utils.ItemParseException;
import io.zipcoder.utils.match.Match;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemParser {
    private Integer exceptionCount = 0;
    public List<Item> parseItemList(String valueToParse) {
        ArrayList<Item> realItems = new ArrayList<>();
        ArrayList<String> itemStrings = new ArrayList<>();

        Pattern p = Pattern.compile(".*?##");
        Matcher m = p.matcher(simplifyJerkson(valueToParse));

        while(m.find()){
            itemStrings.add(m.group());
        }

        for(String x : itemStrings){
            try {
                realItems.add(parseSingleItem(x));
            } catch (ItemParseException e) {
                System.out.println("Invalid jerkson format.");
                exceptionCount++;
            }
        }

        return realItems;
    }

    public static String simplifyJerkson(String stringToSimplify){
        Pattern pattern = Pattern.compile("[@^*%]");
        Matcher m = pattern.matcher(stringToSimplify);
        return m.replaceAll(":");
    }

    public Item parseSingleItem(String singleItem) throws ItemParseException {
        //naMe:Milk; price:3.23; type:Food; expiration:1/25/2016 (##)
        String name;
        Double price;
        String type;
        String expiration;

        Pattern[] patterns = {
            Pattern.compile("name:(\\w*);", Pattern.CASE_INSENSITIVE),
            Pattern.compile("price:(\\d*\\.\\d*);", Pattern.CASE_INSENSITIVE),
            Pattern.compile("type:(\\w*);", Pattern.CASE_INSENSITIVE),
            Pattern.compile("expiration:(\\d+/\\d+/\\d+)##", Pattern.CASE_INSENSITIVE)
        };

        Matcher[] matchers = {
                patterns[0].matcher(singleItem),
                patterns[1].matcher(singleItem),
                patterns[2].matcher(singleItem),
                patterns[3].matcher(singleItem)
        };

        try {
            matchers[0].find();
            name = matchers[0].group(1);
            matchers[1].find();
            price = Double.parseDouble(matchers[1].group(1));
            matchers[2].find();
            type = matchers[2].group(1);
            matchers[3].find();
            expiration = matchers[3].group(1);
        } catch (IllegalStateException e) {
            exceptionCount++;
            return null;
        }

        if(name.equals("")) {
            exceptionCount++;
            return null;
        }

        return new Item(name, price, type, expiration);
    }
}
