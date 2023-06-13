package uaic.fii.client.configs.subscriptions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SubscriptionProcessorConfig {

    @Value("${process.subscriptions.simple:true}")
    private String processSimpleSubscriptions;

    @Value("${process.subscriptions.complex:false}")
    private String processComplexSubscriptions;

    @Value("${process.subscriptions.complex.window-size-millis}")
    private String windowSizeMillisComplexSubscriptions;

    public boolean processSimpleSubscriptionsIsSet(){
        return processSimpleSubscriptions.equalsIgnoreCase("true");
    }

    public boolean processComplexSubscriptionsIsSet(){
        return processComplexSubscriptions.equalsIgnoreCase("true");
    }

    public String getProcessSimpleSubscriptions() {
        return processSimpleSubscriptions;
    }

    public String getProcessComplexSubscriptions() {
        return processComplexSubscriptions;
    }

    public String getWindowSizeMillisComplexSubscriptions() {
        return windowSizeMillisComplexSubscriptions;
    }
}
