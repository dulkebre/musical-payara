package my.company.rest.lab;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Singleton
public class HelloService {
    private static Logger LOGGER = Logger.getLogger(HelloService.class.getName());

    @Inject
    @ConfigProperty(name="secondsToWait")
    private Long secondsToWait;

    public String processNewHello() {
        LOGGER.info("Starting to slowly process a new hello");
        waitForSeconds(secondsToWait);
        return "Hello processed for "+secondsToWait;
    }

    private void waitForSeconds(Long waitTime) {
        LOGGER.info("We need to wait for "+waitTime+" seconds");
        try {
            TimeUnit.SECONDS.sleep(waitTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
