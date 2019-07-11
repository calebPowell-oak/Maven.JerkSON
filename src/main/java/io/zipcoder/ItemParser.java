package io.zipcoder;

import io.zipcoder.utils.Item;
import io.zipcoder.utils.ItemParseException;
import io.zipcoder.utils.match.Match;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemParser {
    private Integer exceptionCount = 0;

    public List<Item> parseItemList(String valueToParse) {
        List<String> itemStrings = new ArrayList<>();
        Pattern pObject = Pattern.compile(".*?##");
        Matcher matcher = pObject.matcher(valueToParse);
        while(matcher.find())
            itemStrings.add(matcher.group());
        return itemStrings.stream().map(x -> {
            try {
                return parseSingleItem(x);
            } catch (ItemParseException e) {
                exceptionCount++;
                return null;
            }
        }).filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static String simplifyJerkson(String stringToSimplify){
        Pattern pattern = Pattern.compile("[@^*%]");
        Matcher m = pattern.matcher(stringToSimplify);
        return m.replaceAll(":");
    }

    public Item parseSingleItem(String singleItem) throws ItemParseException {
        if(singleItem == null) throw new ItemParseException();

        Pattern extract = Pattern.compile(
                "\\w*:([A-Za-z]+);\\w*:(\\d*\\.\\d*);\\w*:(\\w+);\\w*:(\\d+\\/\\d+\\/\\d+)##");
        Matcher m = extract.matcher(simplifyJerkson(singleItem.toLowerCase()));
        m.find();

        if(m.matches()){
            return new Item(m.group(1), Double.valueOf(m.group(2)), m.group(3), m.group(4));
        } else {
            throw new ItemParseException();
        }

    }

    public Integer getExceptionCount() {
        return exceptionCount;
    }
}
