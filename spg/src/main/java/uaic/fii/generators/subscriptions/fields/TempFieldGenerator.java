package uaic.fii.generators.subscriptions.fields;

import uaic.fii.generators.NumberGenerator;
import uaic.fii.generators.OperatorGenerator;
import uaic.fii.models.Operator;
import uaic.fii.models.SubscriptionField;

import java.util.ArrayList;
import java.util.List;

public class TempFieldGenerator {
    public static final int MIN_TEMP = -40;
    public static final int MAX_TEMP = 50;
    private final int totalNumberOfTempFields;
    private final double minFreqEqualOperatorForTempField;

    public TempFieldGenerator(int totalNumberOfTempFields, double minFreqEqualOperatorForTempField) {
        this.totalNumberOfTempFields = totalNumberOfTempFields;
        this.minFreqEqualOperatorForTempField = minFreqEqualOperatorForTempField;
    }

    public List<SubscriptionField> generateTempFields() {
        List<SubscriptionField> list = new ArrayList<>();

        int fieldsWithEqualOp = (int) (minFreqEqualOperatorForTempField * totalNumberOfTempFields);
        for(int i = 0; i < fieldsWithEqualOp; i++){
            list.add(new SubscriptionField("temp", String.valueOf(NumberGenerator.getRandomInt(MIN_TEMP, MAX_TEMP)), Operator.EQUAL.getOperator()));
        }

        for(int i = 0; i < (totalNumberOfTempFields - fieldsWithEqualOp); i++){
            list.add(new SubscriptionField("temp", String.valueOf(NumberGenerator.getRandomInt(MIN_TEMP, MAX_TEMP)), OperatorGenerator.getRandomOperator()));
        }
        return list;
    }
}
