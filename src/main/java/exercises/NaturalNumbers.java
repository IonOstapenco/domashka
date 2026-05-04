package exercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class NaturalNumbers {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        /* if N = 15, M = 5
        then output 15 16 17 18 19
        * */
        System.out.println("enter  number M");
        int numberM = Integer.parseInt(bufferedReader.readLine());
        System.out.println("enter number N");
        int numberN = Integer.parseInt(bufferedReader.readLine());
        System.out.println("the number M is " + numberM + " and N is " + numberN);
        naturalNumbers(numberM, numberN);

    }

    private static void naturalNumbers(int numberM, int numberN) {
        ArrayList<Integer> numbers = new ArrayList<>(numberM);

        //adding to array list
        for (int i = 0; i < numberM; i++) {
            numbers.add(numberN + i);
        }
        for (Integer number : numbers){
            System.out.print(number+" ");
        }

        /*
        ArrayList<String> fruits = new ArrayList<>();
fruits.add("Apple");
fruits.add("Banana");

for (String fruit : fruits) {
    System.out.println(fruit);
}
        * */

    }


}
