package uaic.fii.generators;

import java.util.HashMap;
import java.util.Map;

public class DateGenerator {
    private static Map<Integer, String> dates;

    static {
        initDates();
    }

    private static void initDates(){
        dates = new HashMap<>();
        dates.put(1, "02.02.2023");
        dates.put(2, "12.03.2023");
        dates.put(3, "22.04.2023");
        dates.put(4, "06.05.2023");
        dates.put(5, "17.06.2023");
        dates.put(6, "31.07.2023");
        dates.put(7, "09.08.2023");
        dates.put(8, "25.09.2023");
        dates.put(9, "20.10.2023");
        dates.put(10, "19.11.2023");
        dates.put(11, "10.12.2023");
        dates.put(12, "01.12.2023");
    }

    public static String getRandomDate() {
        int randomDateMapId = NumberGenerator.getRandomInt(1, dates.size());
        return dates.get(randomDateMapId);
    }
}
