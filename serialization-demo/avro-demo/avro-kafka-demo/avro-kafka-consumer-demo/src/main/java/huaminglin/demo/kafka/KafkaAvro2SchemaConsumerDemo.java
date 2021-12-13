package huaminglin.demo.kafka;

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import org.apache.avro.Schema;
import org.apache.avro.Schema.Parser;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.LongDeserializer;

public final class KafkaAvro2SchemaConsumerDemo {

  public static void consume() throws IOException {
    Properties props = new Properties();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "mygroup");
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
    props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
    props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
    props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "2");
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
    props.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://127.0.0.1:8085");

    Schema newSchema;
    {
      InputStream inputStream = KafkaAvro2SchemaConsumerDemo.class.getClassLoader()
          .getResourceAsStream("avro/v2/userV2.avsc");
      newSchema = new Parser().parse(inputStream);
    }
    SchemaRegistryClient schemaRegistry = new CachedSchemaRegistryClient("http://127.0.0.1:8085", 1024);
    KafkaAvroDeserializer deserializer = new KafkaAvroDeserializer(schemaRegistry, (Map) props);

    KafkaConsumer<Long, byte[]> consumer = new KafkaConsumer<>(props);
    String topicName = "my-topic";
    TopicPartition topicPartition = new TopicPartition(topicName, 0);
    consumer.assign(Collections.singletonList(topicPartition));
    consumer.seekToBeginning(Collections.singletonList(topicPartition));
    System.out.println(consumer.assignment());
    ConsumerRecords<Long, byte[]> records = consumer.poll(Duration.ofSeconds(5));
    System.out.println("Got records: " + records.count());
    for (ConsumerRecord<Long, byte[]> record : records) {
      System.out.printf("offset = %d, key = %s \n", record.offset(), record.key());
      Object value = deserializer.deserialize(topicName, record.value(), newSchema);
      System.out.println(value);
    }
    /*
    offset = 0, key = 56
    {"name": "Charlie", "favoriteNumber": null, "favoriteColor": "blue", "sex": "male", "favoriteMovie": "007"}
    offset = 1, key = 87
    {"name": "Charlie", "favoriteNumber": null, "favoriteColor": "blue", "sex": "male", "favoriteMovie": null}
     */
    // Old data to the new schema get default value for new field.

    consumer.commitSync();
    consumer.close();
  }

  public static void main(String[] args) throws IOException {
    consume();
  }

}
