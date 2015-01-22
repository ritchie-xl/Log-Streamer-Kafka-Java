import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class logGenerator{

    Executor executor;
    Logger logger;

    public logGenerator(){

        executor = Executors.newSingleThreadExecutor();
        logger = Logger.getLogger(logGenerator.class);
    }

    private void writeLogs() throws Exception {
        double mean = 100.0;
        double variance = 10.0;
        Random random = new Random();
        
        while(true) {
            // Generate a test log every 0.5 sec
            double newNumber = mean + random.nextGaussian()*variance;
            Thread.sleep(500);
            System.out.println(newNumber);
            logger.info(newNumber+"");
        }
    }

    public static void main(String[] args) throws Exception {
        // Initiate the log4j
        String log4jConfPath = "conf/log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);

        logGenerator loggingClient = new logGenerator();
        loggingClient.writeLogs();
    }
}
