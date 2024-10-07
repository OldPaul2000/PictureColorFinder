package tests;

public class Main2 {
    public static void main(String[] args) {

        String phrase = "Ana are mere";
        System.out.println(phrase.substring(0,5));
        System.out.println();

        int lowerLimit = 0;
        int highestLimit = 100;
        int value = 101;

        int result = Math.min(highestLimit,Math.max(lowerLimit, value));

        System.out.println(result);


    }
}
