package uaic.fii.generators.subscriptions.fields;

import uaic.fii.generators.CityGenerator;
import uaic.fii.generators.OperatorGenerator;
import uaic.fii.models.Operator;
import uaic.fii.models.SubscriptionField;

import java.util.ArrayList;
import java.util.List;

public class CityFieldGenerator {
    private final int totalNumberOfCityFields;
    private final double freqEqualOperator;


    public CityFieldGenerator(int totalNumberOfCityFields, double minFrequencyEqualOperator) {
        this.totalNumberOfCityFields = totalNumberOfCityFields;
        this.freqEqualOperator = minFrequencyEqualOperator;
    }

    public List<SubscriptionField> generateCityFields() {
        double numberOfCityFieldsWithEqualOperatorDouble = freqEqualOperator * totalNumberOfCityFields;
        int numberOfCityFieldsWithEqualOperatorInt = (int) numberOfCityFieldsWithEqualOperatorDouble;
        System.out.println("START City Fields Info");
        System.out.println("Operators Lost And Replaced Due To Conversion: " + (numberOfCityFieldsWithEqualOperatorDouble - numberOfCityFieldsWithEqualOperatorInt));
        System.out.println("Equal Operator Frequency For City Fields: " + freqEqualOperator);
        System.out.println("END City Fields Info");

        List<SubscriptionField> list = new ArrayList<>();
        for(int cityFieldIndex = 0; cityFieldIndex < numberOfCityFieldsWithEqualOperatorInt; cityFieldIndex++){
            list.add(new SubscriptionField("city", CityGenerator.getRandomCity(), Operator.EQUAL.getOperator()));
        }

        int cityFieldsLeftToGenerate = totalNumberOfCityFields - numberOfCityFieldsWithEqualOperatorInt;
        for(int cityFieldIndex = 0; cityFieldIndex < cityFieldsLeftToGenerate; cityFieldIndex++){
            list.add(new SubscriptionField("city", CityGenerator.getRandomCity(), OperatorGenerator.getRandomBetweenEqualAndDifferentOperator()));
        }

        return list;
    }
}
