import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/***
 * The goal of this challenge is to design a cash register program. You will be given two decimal numbers.
 * The first is the purchase price (PP) of the item.
 * The second is the cash (CH) given by the customer.
 * Your register currently has the following bills/coins within it:
 * 'PENNY': .01,
 * 'NICKEL': .05,
 * 'DIME': .10,
 * 'QUARTER': .25,
 * 'HALF DOLLAR': .50,
 * 'ONE': 1.00,
 * 'TWO': 2.00,
 * 'FIVE': 5.00,
 * 'TEN': 10.00,
 * 'TWENTY': 20.00,
 * 'FIFTY': 50.00,
 * 'ONE HUNDRED': 100.00
 * The aim of the program is to calculate the change that has to be returned to the customer.
 * Input:
 * Your program should read lines of text from standard input. Each line contains two numbers which are separated by a semicolon. The first is the Purchase price (PP) and the second is the cash(CH) given by the customer.
 * Output:
 * For each line of input print a single line to standard output which is the change to be returned to the customer. In case the CH < PP, print out ERROR. If CH == PP, print out ZERO. For all other cases print the amount that needs to be returned, in terms of the currency values provided. The output should be alphabetically sorted.
 * Test 1
 * Test Input
 * 15.94;16.00
 * Expected Output
 * NICKEL,PENNY
 * Test 2
 * Test Input
 * 17;16
 * Expected Output
 * ERROR
 * Test 3
 * Test Input
 * 35;35
 * Expected Output
 * ZERO
 * Test 4
 * Test Input
 * 45;50
 * Expected Output
 * FIVE
 */
public class PaymentCashierProblem {
    public static void main(String[] args) {
        System.out.println("Enter two decimal numbers separated by a semicolon :");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        if(input == null) {
            System.out.println("ERROR");
        } else {
            String[] inputArray = input.split(";");
            if(inputArray.length != 2) {
                System.out.println("ERROR");
            }
            Long pp = (long) (Double.valueOf(inputArray[0]) * 100);
            Long ch = (long) (Double.valueOf(inputArray[1]) * 100);
            System.out.println(getCash(pp, ch));
        }
    }

    public static String getCash(Long pp, Long ch) {
        if(pp > ch) {
            return "ERROR";
        }

        if(pp == ch) {
            return "ZERO";
        }

        LinkedHashMap<String, Long> cashMap = new LinkedHashMap<>();
        cashMap.put("ONE HUNDRED", 10000L);
        cashMap.put("FIFTY", 5000L);
        cashMap.put("TWENTY", 2000L);
        cashMap.put("TEN", 1000L);
        cashMap.put("FIVE", 500L);
        cashMap.put("TWO", 200L);
        cashMap.put("ONE", 100L);
        cashMap.put("HALF DOLLAR", 50L);
        cashMap.put("QUARTER", 25L);
        cashMap.put("DIME", 10L);
        cashMap.put("NICKEL", 5L);
        cashMap.put("PENNY", 1L);

        List<String> cash = new ArrayList<>();
        Long change = ch - pp;
        for(Map.Entry<String, Long> entry : cashMap.entrySet()) {
            if(change == 0) break;
            Long value = entry.getValue();
            while(change >= value) {
                change -= value;
                cash.add(entry.getKey());
            }
        }

        String[] result = cash.toArray(new String[0]);
        Arrays.sort(result);
        return String.join(",", result);
    }
}
