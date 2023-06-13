package uaic.fii;

import uaic.fii.generators.subscriptions.SubscriptionGenerator;
import uaic.fii.generators.subscriptions.fields.CityFieldGenerator;
import uaic.fii.managers.PublicationManager;
import uaic.fii.managers.SubscriptionManager;

public class Main {
    public static void main(String[] args) throws Exception {
//        PublicationManager publicationManager = new PublicationManager();
//        publicationManager.generatePublicationsWithThreadParallelization(100, 4, true);
//        publicationManager.generatePublicationsWithoutParallelization(1000000);

        SubscriptionManager subscriptionManager = new SubscriptionManager();
//        subscriptionManager.generateSubscriptionsWithoutParallelization(12, 0.5, 0.25, 0.25, 0.5);
        subscriptionManager.generateSubscriptionsWithoutParallelization(10000, 1, 1, 1, 1, 0.8, true);
    }
}