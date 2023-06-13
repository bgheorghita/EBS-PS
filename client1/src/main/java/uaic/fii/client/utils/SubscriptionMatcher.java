package uaic.fii.client.utils;

import uaic.fii.models.Publication;
import uaic.fii.models.PublicationField;
import uaic.fii.models.Subscription;
import uaic.fii.models.SubscriptionField;

import java.security.InvalidParameterException;

public class SubscriptionMatcher {

    public static boolean match(Subscription subscription, Publication publication) {
        for (SubscriptionField subscriptionField : subscription.getFieldList()) {
            String subscriptionFieldKey = subscriptionField.getKey();
            String subscriptionFieldOperator = subscriptionField.getOperator();
            String subscriptionFieldValue = subscriptionField.getValue();
            boolean matchFound = false;

            for (PublicationField publicationField : publication.getPublicationFieldList()) {
                String publicationFieldKey = publicationField.getKey();
                String publicationFieldValue = publicationField.getValue();

                if (publicationFieldKey.equals(subscriptionFieldKey)) {
                    switch (subscriptionFieldOperator) {
                        case "=" -> {
                            if (publicationFieldValue.equalsIgnoreCase(subscriptionFieldValue)) {
                                matchFound = true;
                            }
                        }
                        case ">=" -> {
                            if (Integer.parseInt(publicationFieldValue) >= Integer.parseInt(subscriptionFieldValue)) {
                                matchFound = true;
                            }
                        }
                        case ">" -> {
                            if (Integer.parseInt(publicationFieldValue) > Integer.parseInt(subscriptionFieldValue)) {
                                matchFound = true;
                            }
                        }
                        case "<=" -> {
                            if (Integer.parseInt(publicationFieldValue) <= Integer.parseInt(subscriptionFieldValue)) {
                                matchFound = true;
                            }
                        }
                        case "<" -> {
                            if (Integer.parseInt(publicationFieldValue) < Integer.parseInt(subscriptionFieldValue)) {
                                matchFound = true;
                            }
                        }
                        case "!=" -> {
                            if (!publicationFieldValue.equalsIgnoreCase(subscriptionFieldValue)) {
                                matchFound = true;
                            }
                        }
                        default -> throw new InvalidParameterException("Operator '" + subscriptionFieldOperator + "' is not defined!");
                    }
                } // end if with switch cases
                if(matchFound){
                    break;
                }
            } // end inner for loop
            if (!matchFound) {
                return false;
            }
        } // end outer for loop
        return true;
    }
}
