package io.zipcoder.itemparser;

import io.zipcoder.ItemParser;
import io.zipcoder.utils.Item;
import io.zipcoder.utils.match.Match;
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
    public void scratchPaper1(){
        String input = this.input;
        String input2 = this.input2;
        ArrayList<String> arr = new ArrayList<>();
        Pattern p = Pattern.compile(".*?##");
        Matcher m = p.matcher(ItemParser.simplifyJerkson(input2));

        while(m.find()){
            arr.add(m.group());
        }

        System.out.println(arr.size());
        for(String x : arr){
            System.out.println(x);
        }
    }

    @Test
    public void scratchPaper2(){
        ItemParser itemParser = new ItemParser();
        String input = this.input;
        String input2 = this.input2;

        itemParser.parseItemList(input);
    }

    @Test
    public void scratchPaper3(){
        ItemParser itemParser = new ItemParser();
        String input = this.input;
        String input2 = this.input2;

        itemParser.parseItemList(ItemParser.simplifyJerkson(input));
    }

    @Test
    public void scratchPaper4(){
        ItemParser itemParser = new ItemParser();
        String in = this.whole;

        List<Item> items = itemParser.parseItemList(in);

        for(Item x : items){
            if(x != null) {
                System.out.println(x.getName().toLowerCase());
            }
        }
    }
}
