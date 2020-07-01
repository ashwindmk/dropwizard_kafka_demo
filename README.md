# Dropwizard Kafka Demo

### Running the Application

**1. Start Zookeeper and kafka-broker servers**

**2. Start producer**

```shell
cd producer
mvn clean package
java -jar target/kafka-producer-0.0.1.jar server producer.yml
```

**3. Start consumer**

```shell
cd consumer
mvn clean package
java -jar target/kafka-consumer-0.0.1.jar mytopic1
```

**4. Send Messages**

```
POST: http://localhost:4000/kafka

{
	"topic": "mytopic1",
	"message": "Hello Kafka"
}
```

You should see the messages in the consumer console.
