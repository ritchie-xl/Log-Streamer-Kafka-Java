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
        int count =0;
        while(true) {
            // Generate a test log every 0.5 sec
            Thread.sleep(500);
            count = count + 1;
            logger.info("line=" + (count) + " this is test:" + count + " test  test  test  test  test ");
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
