package io.zipcoder.itemparser;

import io.zipcoder.ItemParser;
import io.zipcoder.utils.Item;
import io.zipcoder.utils.ItemParseException;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FunctionTesting {

    String input = "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##naME:BreaD;price:1.23;type:Food;expiration:1/02/2016##NAMe:BrEAD;price:1.23;type:Food;expiration:2/25/2016##naMe:MiLK;price:3.23;type:Food^expiration:1/11/2016##";
    String input2 = "naMe:MiLK;priCe:;type:Food;expiration:1/11/2016##naMe:Cookies;price:2.25;type:Food;expiration:1/25/2016##naMe:Co0kieS;pRice:2.25;type:Food;expiration:1/25/2016##";
    String whole = "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##naME:BreaD;price:1.23;type:Food;expiration:1/02/2016##NAMe:BrEAD;price:1.23;type:Food;expiration:2/25/2016##naMe:MiLK;price:3.23;type:Food^expiration:1/11/2016##naMe:Cookies;price:2.25;type:Food%expiration:1/25/2016##naMe:CoOkieS;price:2.25;type:Food*expiration:1/25/2016##naMe:COokIes;price:2.25;type:Food;expiration:3/22/2016##naMe:COOkieS;price:2.25;type:Food;expiration:1/25/2016##NAME:MilK;price:3.23;type:Food;expiration:1/17/2016##naMe:MilK;price:1.23;type:Food!expiration:4/25/2016##naMe:apPles;price:0.25;type:Food;expiration:1/23/2016##naMe:apPles;price:0.23;type:Food;expiration:5/02/2016##NAMe:BrEAD;price:1.23;type:Food;expiration:1/25/2016##naMe:;price:3.23;type:Food;expiration:1/04/2016##naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##naME:BreaD;price:1.23;type:Food@expiration:1/02/2016##NAMe:BrEAD;price:1.23;type:Food@expiration:2/25/2016##naMe:MiLK;priCe:;type:Food;expiration:1/11/2016##naMe:Cookies;price:2.25;type:Food;expiration:1/25/2016##naMe:Co0kieS;pRice:2.25;type:Food;expiration:1/25/2016##naMe:COokIes;price:2.25;type:Food;expiration:3/22/2016##naMe:COOkieS;Price:2.25;type:Food;expiration:1/25/2016##NAME:MilK;price:3.23;type:Food;expiration:1/17/2016##naMe:MilK;priCe:;type:Food;expiration:4/25/2016##naMe:apPles;prIce:0.25;type:Food;expiration:1/23/2016##naMe:apPles;pRice:0.23;type:Food;expiration:5/02/2016##NAMe:BrEAD;price:1.23;type:Food;expiration:1/25/2016##naMe:;price:3.23;type:Food^expiration:1/04/2016##";

    @Test
    public void splitOnObject_BadFormat(){
        // Given
        String input = this.input2;
        Integer expected = 3;
        ArrayList<String> arr = new ArrayList<>();

        // When
        Pattern p = Pattern.compile(".*?##");
        Matcher m = p.matcher(input);
        while(m.find()){
            arr.add(m.group());
        }
        Integer actual = arr.size();

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void splitOnObject_GoodFormat(){
        // Given
        String input = this.input;
        Integer expected = 4;
        ArrayList<String> arr = new ArrayList<>();

        // When
        Pattern p = Pattern.compile(".*?##");
        Matcher m = p.matcher(input);
        while(m.find()){
            arr.add(m.group());
        }
        Integer actual = arr.size();

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void parseMultiple_goodInput(){
        // Given
        ItemParser itemParser = new ItemParser();
        String input = this.input;
        String input2 = this.input2;
        String expected = "name:milk price:3.23 type:food expiration:1/25/2016, name:bread price:1.23 type:food expiration:1/02/2016, name:bread price:1.23 type:food expiration:2/25/2016";

        // When
        String actual = itemParser.parseItemList(input).toString();
        actual = actual.substring(1, actual.length() - 1);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void parseMultiple_badInput(){
        // Given
        ItemParser itemParser = new ItemParser();
        String input = this.input2;
        String expected = "name:cookies price:2.25 type:food expiration:1/25/2016";

        // When
        String actual = itemParser.parseItemList(input).toString();
        actual = actual.substring(1, actual.length() - 1);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void simplifyInput_goodInput(){
        // Given
        String input = this.input;
        String input2 = this.input2;
        String expected = "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##naME:BreaD;price:1.23;type:Food;expiration:1/02/2016##NAMe:BrEAD;price:1.23;type:Food;expiration:2/25/2016##naMe:MiLK;price:3.23;type:Food:expiration:1/11/2016##";

        // When
        String actual = ItemParser.simplifyJerkson(input);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void simplifyInput_invalidInput(){
        // Given
        String input = this.input2;
        String expected = "naMe:MiLK;priCe:;type:Food;expiration:1/11/2016##naMe:Cookies;price:2.25;type:Food;expiration:1/25/2016##naMe:Co0kieS;pRice:2.25;type:Food;expiration:1/25/2016##";

        // When
        String actual = ItemParser.simplifyJerkson(input);
        System.out.println(ItemParser.simplifyJerkson(input));

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void itemParserExceptionCount(){
        // Given
        ItemParser itemParser = new ItemParser();
        String input = this.whole;
        Integer expected = 11;

        // When
        List<Item> items = itemParser.parseItemList(input);
        Integer actual = itemParser.getExceptionCount();

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void parseSingleItem_goodInput(){
        // Given
        ItemParser itemParser = new ItemParser();
        String input = "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##";
        Item expected = new Item("milk", 3.23, "food", "1/25/2016");

        // When
        Item actual = new Item(null, null, null, null);
        try {
            actual = itemParser.parseSingleItem(input);
        } catch (ItemParseException e) {
            e.printStackTrace();
        }

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = ItemParseException.class)
    public void parseSingleItem_badInput() throws ItemParseException {
        // Given
        ItemParser itemParser = new ItemParser();
        String input = "naMe:Milkion:1/25/2016##";
        Item expected = new Item("milk", 3.23, "food", "1/25/2016");

        // When
        Item actual = new Item(null, null, null, null);
        try {
            actual = itemParser.parseSingleItem(input);
        } catch (ItemParseException e) {
            throw new ItemParseException();
        }
    }
}
