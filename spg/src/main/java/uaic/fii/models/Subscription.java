package uaic.fii.models;

import java.util.ArrayList;
import java.util.List;

public class Subscription {
    private final List<SubscriptionField> fieldList;

    public Subscription(List<SubscriptionField> fieldList) {
        this.fieldList = new ArrayList<>(fieldList);
    }

    public void removeSubscriptionField(SubscriptionField subscriptionField){
        fieldList.remove(subscriptionField);
    }

    public SubscriptionField getSubscriptionField(String key){
        for (SubscriptionField subscriptionField : fieldList) {
            if (subscriptionField.getKey().equals(key)) {
                return subscriptionField;
            }
        }
        return null;
    }

    public List<SubscriptionField> getFieldList(){
        return new ArrayList<>(fieldList);
    }

    public long getFieldListSize(){
        return fieldList.size();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Subscription {");
        for (SubscriptionField subscriptionField : fieldList) {
            s.append(subscriptionField);
            s.append(";");
        }
        s.deleteCharAt(s.length() - 1);
        s.append("}");
        return s.toString();
    }
}