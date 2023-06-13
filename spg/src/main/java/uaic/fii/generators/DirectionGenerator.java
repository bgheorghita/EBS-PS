package uaic.fii.generators;

import java.util.HashMap;
import java.util.Map;

public class DirectionGenerator {
    private static Map<Integer, String> directions;

    static {
        initDirections();
    }

    private static void initDirections(){
        directions = new HashMap<>();
        directions.put(1, "N");
        directions.put(2, "S");
        directions.put(3, "E");
        directions.put(4, "V");
        directions.put(5, "NE");
        directions.put(6, "NV");
        directions.put(7, "SE");
        directions.put(8, "SV");
    }

    public static String getRandomDirection(){
        int directionMapId = NumberGenerator.getRandomInt(1, directions.size());
        return directions.get(directionMapId);
    }
}
