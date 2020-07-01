package com.ashwin.java;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

public class KafkaTopicConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaTopicConsumer.class);

    public static void main(String[] args)  {
        String topic = args[0];

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "group1");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "5000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        logger.info("Starting Kafka Consumer...");
        consumer.subscribe(Arrays.asList(topic));
        logger.info("Kafka Consumer subscribed to topic: {}", topic);

        // For periodic poll
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                ConsumerRecords<String, String> records = consumer.poll(5000);
                if (records != null && !records.isEmpty()) {
                    for (ConsumerRecord<String, String> record : records) {
                        logger.info("Message received: {}", record.value());
                    }
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 0, 5000);

        // For continuous poll
        /*while (true) {
            ConsumerRecords<String, String> records = consumer.poll(5000);
            if (records != null && !records.isEmpty()) {
                for (ConsumerRecord<String, String> record : records) {
                    logger.info("Message received: {}", record.value());
                }
            }
        }*/
    }
}
