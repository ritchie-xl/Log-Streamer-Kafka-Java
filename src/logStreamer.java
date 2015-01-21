import java.io.*;
import java.util.*;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class logStreamer {

    private File logFile; // The log file to be streamed
    private String topic; // The topic created on kafka server
    private String kafkaServer; // The kafka server's IP address
    private String brokerList;  // Brokers of the kafka cluster
    private String record;      // The file to save how many bytes of the log has been streamed
    boolean keepRunning;        // The flag to specify the producer is keeping running or not
    long orgLogSize;            //  The log file's size in bytes

    //Constructor for the logProducer
    public logStreamer(String[] args)
    {
        if (args.length < 5) {
            System.out.println("Please enter all the required arguments as following:");
            System.out.println("Usage: java logProducer [file/path] [kafkaServer] [brokerList] [topic]");
            System.exit(1);
        }
        keepRunning = true;
        logFile = new File(args[0]);
        kafkaServer = args[1];
        brokerList = args[2];
        topic = args[3];
        record = args[4];
    }

    // Streaming the log file
    public void run() throws Exception
    {
        Properties props = new Properties();

        FileInputStream fis=null;
        Scanner sc = null;
        FileWriter fw = null;
        String lastByte;

        BufferedReader br = new BufferedReader(new FileReader(record));

        // Set producer properties
        props.put("metadata.broker.list", brokerList);
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("request.required.acks", "1");
        ProducerConfig config = new ProducerConfig(props);
        Producer<String, String> producer = new Producer<String, String>(config);

        // Read how much log file has been streamed in bytes
        lastByte = br.readLine();
        orgLogSize = Long.parseLong(lastByte);

        // Run the producer to streaming the newly added logs
        while(keepRunning){
            // If there are any logs added, streaming the newly added logs
            if(logFile.length() > orgLogSize){
                fw = new FileWriter(record);
                fis = new FileInputStream(logFile);
                fis.skip(orgLogSize);
                sc = new Scanner(fis);
                String line;
                while(sc.hasNextLine()){
                    line = sc.nextLine();
                    KeyedMessage<String, String> data = new KeyedMessage<String, String>(topic, kafkaServer, line);
                    producer.send(data);
                }
                orgLogSize = logFile.length();
                fw.write(String.valueOf(orgLogSize));
                fw.flush();
            }
        }

        // Close all the fileWrite and bufferReader
        if(fw!=null){
            fw.close();
        }

        if(br!=null){
            br.close();
        }

    }

    public void stop() throws Exception
    {
        //TODO: when catching the interrupted signal like ctrl+c or quit signal, close the streaming file
        keepRunning = false;
    }

    public static void main(String[] args) throws Exception
    {
        logStreamer lp = new logStreamer(args);
        lp.run();
    }
}