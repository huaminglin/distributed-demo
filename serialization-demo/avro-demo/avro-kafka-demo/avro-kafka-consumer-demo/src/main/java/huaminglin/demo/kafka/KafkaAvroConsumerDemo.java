package huaminglin.demo.kafka;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.LongDeserializer;

public final class KafkaAvroConsumerDemo {

  public static void consume() throws IOException {
    Properties props = new Properties();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "mygroup");
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
    props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
    props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
    props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "2");
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
    props.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://127.0.0.1:8085");
    {
//      props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, "true");
    /*
    Caused by: org.apache.kafka.common.errors.SerializationException: Could not find class example.avro.User specified in writer's schema whilst finding reader's schema for a SpecificRecord.
	at io.confluent.kafka.serializers.AbstractKafkaAvroDeserializer.getSpecificReaderSchema(AbstractKafkaAvroDeserializer.java:265)
	at io.confluent.kafka.serializers.AbstractKafkaAvroDeserializer.getReaderSchema(AbstractKafkaAvroDeserializer.java:247)
	at io.confluent.kafka.serializers.AbstractKafkaAvroDeserializer.getDatumReader(AbstractKafkaAvroDeserializer.java:194)
     */
      // Even we provide example.avro.User, it has nothing to do with the target schema.
      // It means if we parse the old data into User of new schema, the new fields in new schema get no default value from the schema.
    }

    {
//    props.put(KafkaAvroDeserializerConfig.SCHEMA_REFLECTION_CONFIG, "true");
    /*
    Caused by: org.apache.kafka.common.errors.SerializationException: Could not find class example.avro.User specified in writer's schema whilst finding reader's schema for a reflected class.
	at io.confluent.kafka.serializers.AbstractKafkaAvroDeserializer.getReflectionReaderSchema(AbstractKafkaAvroDeserializer.java:290)
	at io.confluent.kafka.serializers.AbstractKafkaAvroDeserializer.getReaderSchema(AbstractKafkaAvroDeserializer.java:244)
	at io.confluent.kafka.serializers.AbstractKafkaAvroDeserializer.getDatumReader(AbstractKafkaAvroDeserializer.java:194)
	at io.confluent.kafka.serializers.AbstractKafkaAvroDeserializer$DeserializationContext.read(AbstractKafkaAvroDeserializer.java:375)
	at io.confluent.kafka.serializers.AbstractKafkaAvroDeserializer.deserialize(AbstractKafkaAvroDeserializer.java:112)
	at io.confluent.kafka.serializers.AbstractKafkaAvroDeserializer.deserialize(AbstractKafkaAvroDeserializer.java:86)
	at io.confluent.kafka.serializers.KafkaAvroDeserializer.deserialize(KafkaAvroDeserializer.java:55)
	at org.apache.kafka.common.serialization.Deserializer.deserialize(Deserializer.java:60)
	at org.apache.kafka.clients.consumer.internals.Fetcher.parseRecord(Fetcher.java:1420)
     */
    }

    KafkaConsumer<Long, GenericRecord> consumer = new KafkaConsumer<>(props);
    TopicPartition topicPartition = new TopicPartition("my-topic", 0);
    consumer.assign(Collections.singletonList(topicPartition));
    consumer.seekToBeginning(Collections.singletonList(topicPartition));
    System.out.println(consumer.assignment());
    ConsumerRecords<Long, GenericRecord> records = consumer.poll(Duration.ofSeconds(5));
    System.out.println("Got records: " + records.count());
    for (ConsumerRecord<Long, GenericRecord> record : records) {
      System.out.printf("offset = %d, key = %s\n", record.offset(), record.key());
      System.out.println(record.value());
    }
    /*
    [my-topic-0]
Got records: 2
offset = 0, key = 56
{"name": "Charlie", "favoriteNumber": null, "favoriteColor": "blue", "sex": "male"}
offset = 1, key = 87
{"name": "Charlie", "favoriteNumber": null, "favoriteColor": "blue", "sex": "male", "favoriteMovie": null}
*/
    // Old data to the new schema doesn't get default value for new field.
    // Actually the consumer has no idea on the new Schema.

    // class example.avro.User cannot be cast to class example.avro.UserV2
    // For some reason the KafkaConsumer can figure out the message is example.avro.User.
    // This will not happen in production scenario and there is only latest UserV2 is available.
    consumer.commitSync();
    consumer.close();
  }

  public static void main(String[] args) throws IOException {
    consume();
  }

}
