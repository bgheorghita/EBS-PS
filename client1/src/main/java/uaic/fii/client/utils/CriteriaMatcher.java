package uaic.fii.client.utils;


public class CriteriaMatcher {
    public static boolean match(String firstValue, String operator, String secondValue){
        double firstValueDouble;
        double secondValueDouble;
        try{
            firstValueDouble = Double.parseDouble(firstValue);
            secondValueDouble = Double.parseDouble(secondValue);
        } catch (NumberFormatException e){
            return false;
        }

        switch (operator){
            case "=" -> {
               return firstValueDouble == secondValueDouble;
            }
            case "!=" -> {
                return firstValueDouble != secondValueDouble;
            }
            case ">" -> {
                return firstValueDouble > secondValueDouble;
            }
            case "<" -> {
                return firstValueDouble < secondValueDouble;
            }
            case ">=" -> {
                return firstValueDouble >= secondValueDouble;
            }
            case "<=" -> {
                return firstValueDouble <= secondValueDouble;
            }
            default -> throw new RuntimeException("Operator " + operator + " is not implemented.");
        }
    }
}
