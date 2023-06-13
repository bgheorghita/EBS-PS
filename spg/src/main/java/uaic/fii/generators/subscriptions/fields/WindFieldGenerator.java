package uaic.fii.generators.subscriptions.fields;

import uaic.fii.generators.NumberGenerator;
import uaic.fii.generators.OperatorGenerator;
import uaic.fii.models.SubscriptionField;

import java.util.ArrayList;
import java.util.List;

public class WindFieldGenerator {
    public static final int MIN_WIND = 0;
    public static final int MAX_WIND = 100;
    public static final String WIND_FIELD_KEY = "wind";
    private final int totalNumberOfWindFields;

    public WindFieldGenerator(int totalNumberOfWindFields) {
        this.totalNumberOfWindFields = totalNumberOfWindFields;
    }

    public List<SubscriptionField> generateWindFields() {
        java.util.List<SubscriptionField> list = new ArrayList<>();
        for(int i = 0; i< totalNumberOfWindFields; i++){
            list.add(new SubscriptionField(WIND_FIELD_KEY,
                    String.valueOf(NumberGenerator.getRandomInt(MIN_WIND, MAX_WIND)), OperatorGenerator.getRandomOperator()));
        }
        return list;
    }
}
