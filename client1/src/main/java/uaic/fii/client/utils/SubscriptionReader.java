package uaic.fii.client.utils;

import uaic.fii.models.Subscription;
import uaic.fii.models.SubscriptionField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionReader {
    public List<Subscription> readSubscriptionsFromFile(String fileName) {
        List<Subscription> subscriptions = new ArrayList<>();
        ClassLoader classLoader = getClass().getClassLoader();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(classLoader.getResourceAsStream(fileName)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Subscription subscription = createSubscriptionFromLine(line);
                if (subscription != null) {
                    subscriptions.add(subscription);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return subscriptions;
    }

    private Subscription createSubscriptionFromLine(String line) {
        line = line.trim();
        if (line.startsWith("Subscription {") && line.endsWith("}")) {
            String fieldsString = line.substring("Subscription {".length(), line.length() - 1);
            List<SubscriptionField> fields = new ArrayList<>();
            String[] fieldStrings = fieldsString.split(";");
            for (String fieldString : fieldStrings) {
                SubscriptionField field = createSubscriptionFieldFromString(fieldString.trim());
                if (field != null) {
                    fields.add(field);
                }
            }
            return new Subscription(fields);
        }
        return null;
    }

    private SubscriptionField createSubscriptionFieldFromString(String fieldString) {
        if (fieldString.startsWith("(") && fieldString.endsWith(")")) {
            fieldString = fieldString.substring(1, fieldString.length() - 1);
            String[] parts = fieldString.split(",");
            if (parts.length == 3) {
                String key = parts[0].trim();
                String operator = parts[1].trim();
                String value = parts[2].trim();
                return new SubscriptionField(key, value, operator);
            }
        }
        return null;
    }
}
