package huaminglin.demo.kafka;

import example.avro.UserV2;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.LongDeserializer;

public final class KafkaAvroConsumerDemo {

  public static void consume() {
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
//    props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, "true");
    props.put(KafkaAvroDeserializerConfig.SCHEMA_REFLECTION_CONFIG, "true");

    KafkaConsumer<Long, UserV2> consumer = new KafkaConsumer<>(props);
    TopicPartition topicPartition = new TopicPartition("my-topic", 0);
    consumer.assign(Collections.singletonList(topicPartition));
    consumer.seekToBeginning(Collections.singletonList(topicPartition));
    System.out.println(consumer.assignment());
    ConsumerRecords<Long, UserV2> records = consumer.poll(Duration.ofSeconds(5));
    System.out.println("Got records: " + records.count());
    for (ConsumerRecord<Long, UserV2> record : records) {
      UserV2 userV2 = record.value();
      System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(),
          userV2 + "\n");
    }
    // class example.avro.User cannot be cast to class example.avro.UserV2
    // For some reason the KafkaConsumer can figure out the message is example.avro.User.
    // This will not happen in production scenario and there is only latest UserV2 is available.
    consumer.commitSync();
    consumer.close();
  }

  public static void main(String[] args) {
    consume();
  }

}
