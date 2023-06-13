package uaic.fii.generators.subscriptions;

import uaic.fii.generators.NumberGenerator;
import uaic.fii.generators.subscriptions.fields.CityFieldGenerator;
import uaic.fii.generators.subscriptions.fields.TempFieldGenerator;
import uaic.fii.generators.subscriptions.fields.WindFieldGenerator;
import uaic.fii.models.Subscription;
import uaic.fii.models.SubscriptionField;

import java.util.*;
import java.util.concurrent.Callable;

public class SubscriptionGenerator implements Callable<List<Subscription>> {
    private final int start;
    private final int end;
    private double cityFreq;
    private double tempFreq;
    private double windFreq;
    private final double minFreqEqualOperatorForCityField;
    private final double minFreqEqualOperatorForTempField;

    public SubscriptionGenerator(int start, int end, double cityFreq, double tempFreq, double windFreq, double minFreqEqualOperatorForCityField, double minFreqEqualOperatorForTempField) {
        this.start = start;
        this.end = end;
        this.cityFreq = cityFreq;
        this.tempFreq = tempFreq;
        this.windFreq = windFreq;
        this.minFreqEqualOperatorForCityField = minFreqEqualOperatorForCityField;
        this.minFreqEqualOperatorForTempField = minFreqEqualOperatorForTempField;
    }

    private List<Subscription> computeSubscriptions(int noOfSubs, List<SubscriptionField> cityFields, List<SubscriptionField> tempFields, List<SubscriptionField> windFields){
        List<Subscription> subscriptions = new ArrayList<>();

        for(int i=0; i<noOfSubs; i++){
            SubscriptionField cityField = cityFields.get(i);
            SubscriptionField tempField = tempFields.get(i);
            SubscriptionField windField = windFields.get(i);
            subscriptions.add(new Subscription(List.of(cityField, tempField, windField)));
        }

        // remove extra fields to fit the given frequencies
        int cityFieldsToRemove = (int) (noOfSubs - noOfSubs * cityFreq);
        int tempFieldsToRemove = (int) (noOfSubs - noOfSubs * tempFreq);
        int windFieldsToRemove = (int) (noOfSubs - noOfSubs * windFreq);

        int cityRemoved = 0;
        int tempRemoved = 0;
        int windRemoved = 0;

        Set<Subscription> visitedSubscriptions = new HashSet<>();
        while (cityRemoved < cityFieldsToRemove){
            int random = NumberGenerator.getRandomInt(0, noOfSubs-1);
            Subscription subscription = subscriptions.get(random);

            while (visitedSubscriptions.contains(subscription)){
                random = NumberGenerator.getRandomInt(0, noOfSubs-1);
                subscription = subscriptions.get(random);
            }
            visitedSubscriptions.add(subscription);

            if(subscription.getFieldListSize() > 1){
                SubscriptionField cityField = subscription.getSubscriptionField("city");
                subscription.removeSubscriptionField(cityField);
                cityRemoved++;
            }
        }

        visitedSubscriptions = new HashSet<>();
        while (tempRemoved < tempFieldsToRemove){
            int random = NumberGenerator.getRandomInt(0, noOfSubs-1);
            Subscription subscription = subscriptions.get(random);

            while (visitedSubscriptions.contains(subscription)){
                random = NumberGenerator.getRandomInt(0, noOfSubs-1);
                subscription = subscriptions.get(random);
            }
            visitedSubscriptions.add(subscription);

            if(subscription.getFieldListSize() > 1){
                SubscriptionField tempField = subscription.getSubscriptionField("temp");
                subscription.removeSubscriptionField(tempField);
                tempRemoved++;
            }
        }

        visitedSubscriptions = new HashSet<>();
        while (windRemoved < windFieldsToRemove){
            int random = NumberGenerator.getRandomInt(0, noOfSubs-1);
            Subscription subscription = subscriptions.get(random);

            while (visitedSubscriptions.contains(subscription)){
                random = NumberGenerator.getRandomInt(0, noOfSubs-1);
                subscription = subscriptions.get(random);
            }

            visitedSubscriptions.add(subscription);
            if(subscription.getFieldListSize() > 1){
                SubscriptionField windField = subscription.getSubscriptionField("wind");
                subscription.removeSubscriptionField(windField);
                windRemoved++;
            }
        }

        return subscriptions;
    }


    @Override
    public List<Subscription> call() {
        checkFrequencies();
        int numberOfSubscriptions = end - start;
        System.out.println("Parameters info");
        System.out.println("City Freq: " + cityFreq);
        System.out.println("Temp Freq: " + tempFreq);
        System.out.println("Wind Freq: " + windFreq);
        System.out.println("Asked Subscriptions: " + numberOfSubscriptions);
        System.out.println();

        List<SubscriptionField> cityFields;
        List<SubscriptionField> tempFields;
        List<SubscriptionField> windFields;

        double numberOfCityFieldsToGenerate = numberOfSubscriptions * cityFreq;
        double numberOfTempFieldsToGenerate = numberOfSubscriptions * tempFreq;
        double numberOfWindFieldsToGenerate = numberOfSubscriptions * windFreq;

        int sizeOfCityFields = (int)numberOfCityFieldsToGenerate;
        int sizeOfTempFields = (int)numberOfTempFieldsToGenerate;
        int sizeOfWindFields = (int)numberOfWindFieldsToGenerate;

        double totalFieldsSizeDouble = numberOfCityFieldsToGenerate + numberOfTempFieldsToGenerate + numberOfWindFieldsToGenerate;
        int totalFieldsSizeInt = sizeOfCityFields + sizeOfTempFields + sizeOfWindFields;
        System.out.println("Fields Lost And Replaced Due To Conversion: " + (totalFieldsSizeDouble - totalFieldsSizeInt));
        System.out.println();

        cityFields = new CityFieldGenerator(numberOfSubscriptions, minFreqEqualOperatorForCityField).generateCityFields();
        tempFields = new TempFieldGenerator(numberOfSubscriptions, minFreqEqualOperatorForTempField).generateTempFields();
        windFields = new WindFieldGenerator(numberOfSubscriptions).generateWindFields();
        System.out.println();
        System.out.println("City Fields Generated: " + cityFields.size());
        System.out.println("Temp Fields Generated: " + tempFields.size());
        System.out.println("Wind Fields Generated: " + windFields.size());

        List<Subscription> subscriptionList = computeSubscriptions(numberOfSubscriptions, cityFields, tempFields, windFields);
        System.out.println("Generated Subscriptions: " + subscriptionList.size());

        return subscriptionList;
    }

    private void checkFrequencies() {
        if(cityFreq > 1){
            cityFreq = 1;
        } else if(cityFreq < 0){
            cityFreq = 0;
        }

        if(tempFreq > 1){
            tempFreq = 1;
        } else if (tempFreq < 0) {
            tempFreq = 0;
        }

        if(windFreq > 1){
            windFreq = 1;
        } else if(windFreq < 0){
            windFreq = 0;
        }

        if((cityFreq + tempFreq + windFreq) < 1)   {
            cityFreq = 1;
            tempFreq = 1;
            windFreq = 1;
        }
    }
}
