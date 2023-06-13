package uaic.fii.generators;

import java.util.HashMap;
import java.util.Map;

public class CityGenerator {
    private static Map<Integer, String> cities;
    public static final int MAX_CITIES = 5;

    static {
        initCities();
    }

    private static void initCities(){
        cities = new HashMap<>();
        StringBuilder sb = new StringBuilder("a");
        for (int i = 1; i <= MAX_CITIES; i++) {
            cities.put(i, sb.toString());

            int index = sb.length() - 1;
            while (index >= 0 && sb.charAt(index) == 'z') {
                sb.setCharAt(index, 'a');
                index--;
            }
            if (index < 0) {
                sb.append('a');
            } else {
                char ch = sb.charAt(index);
                sb.setCharAt(index, (char) (ch + 1));
            }
        }
    }
    public static String getRandomCity(){
        int randomCityMapId = NumberGenerator.getRandomInt(1, cities.size());
        return cities.get(randomCityMapId);
    }
}
