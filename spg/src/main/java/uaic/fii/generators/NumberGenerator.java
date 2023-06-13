package uaic.fii.generators;

import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;

public class NumberGenerator {
    public static int getRandomInt(int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static double getRandomDouble(double min, double max){
        DecimalFormat df = new DecimalFormat("0.00");
        double generatedDouble = ThreadLocalRandom.current().nextDouble(min, max + 1.0);
        return Double.parseDouble(df.format(generatedDouble));
    }
}
