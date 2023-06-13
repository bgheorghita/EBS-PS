package uaic.fii.client.utils;

import java.util.HashMap;
import java.util.Map;

public class CriteriaHelper {
    private static final Map<String, String> criteria;

    static {
        criteria = new HashMap<>();
        criteria.put("avg_temp", "temp");
        criteria.put("avg_wind", "wind");
        criteria.put("avg_rain", "rain");
    }

    public static String getCriteria(String key){
        if(criteria.containsKey(key)){
            return criteria.get(key);
        }
       throw new RuntimeException("Criteria '" + key + "' is not implemented");
    }
}
