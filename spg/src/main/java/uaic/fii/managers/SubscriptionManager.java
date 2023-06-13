package uaic.fii.managers;

import uaic.fii.generators.subscriptions.SubscriptionGenerator;
import uaic.fii.models.Subscription;
import uaic.fii.utils.Writer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class SubscriptionManager {
    public List<Subscription> generateSubscriptionsWithoutParallelization(int numberOfSubscriptions, double cityFreq, double tempFreq, double windFreq, double minFreqEqualOperatorForCityField, double minFreqEqualOperatorForTempField, boolean save) throws IOException {
        SubscriptionGenerator subscriptionGenerator = new SubscriptionGenerator(0, numberOfSubscriptions, cityFreq, tempFreq, windFreq, minFreqEqualOperatorForCityField, minFreqEqualOperatorForTempField);
        long startTime = System.currentTimeMillis();
        List<Subscription> subscriptions = subscriptionGenerator.call();
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Total time took to generate " + numberOfSubscriptions + " subscriptions without parallelization: " + totalTime + " ms\n");

        if(save){
            saveSubscriptionsToFile(subscriptions);
        }

        return subscriptions;
    }

    public List<Subscription> generateSubscriptionsWithThreadParallelization(int numberOfSubscriptions, double cityFreq, double tempFreq, double windFreq, double minFreqEqualOperatorForCityField, double minFreqEqualOperatorForTempField, int maxThreadsToUse, boolean save) throws IOException {
        List<Subscription> subscriptions = new ArrayList<>();
        int numOfCores = Runtime.getRuntime().availableProcessors();
        if(maxThreadsToUse > numOfCores){
            maxThreadsToUse = numOfCores;
            System.out.println("Info. Max threads has been set to the maximum of the available CPU cores.\n");
        } else if(maxThreadsToUse < 1){
            maxThreadsToUse = 1;
            System.out.println("Info. Max threads has been set to 1.\n");
        }

        int maxSubscriptionsPerThread = numberOfSubscriptions / maxThreadsToUse;

        System.out.println("System CPU Cores: " + numOfCores);
        System.out.println("Threads Used: " + maxThreadsToUse);
        System.out.println("Publications/Thread: " + maxSubscriptionsPerThread);

        ExecutorService executor = Executors.newFixedThreadPool(maxThreadsToUse);
        List<Future<List<Subscription>>> futureSubscriptionsList = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        for(int thread = 0; thread < maxThreadsToUse; thread ++){
            int start = thread * maxSubscriptionsPerThread;
            int end = (thread + 1) * maxSubscriptionsPerThread;
            if(thread == maxThreadsToUse - 1){
                end = numberOfSubscriptions;
            }
            System.out.println("thread " + thread + " range [" + start + " - " + end + ")");
            Callable<List<Subscription>> subscriptionListCallable = new SubscriptionGenerator(start, end, cityFreq, tempFreq, windFreq, minFreqEqualOperatorForCityField, minFreqEqualOperatorForTempField);
            Future<List<Subscription>> futureSubscriptionList = executor.submit(subscriptionListCallable);
            futureSubscriptionsList.add(futureSubscriptionList);
        }
        System.out.println();

        for(Future<List<Subscription>> subscriptionList : futureSubscriptionsList){
            try {
                subscriptions.addAll(subscriptionList.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Total time taken to generate " + subscriptions.size() + " subscription with thread parallelization: " + totalTime + " ms");

        if(save){
            saveSubscriptionsToFile(subscriptions);
        }

        return subscriptions;
    }

    private void saveSubscriptionsToFile(List<Subscription> subscriptions) throws IOException {
        String filename = "subscriptions.txt";
        System.out.println("Saving subscriptions ...");
        new Writer<Subscription>().saveToFile(subscriptions, "subscriptions.txt");
        System.out.println("Subscriptions saved to file " + filename);
    }
}
