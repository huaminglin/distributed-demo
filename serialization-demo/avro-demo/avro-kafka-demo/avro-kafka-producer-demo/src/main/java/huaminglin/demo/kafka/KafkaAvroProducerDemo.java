package huaminglin.demo.kafka;

import example.avro.User;
import example.avro.UserV2;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.LongSerializer;

public final class KafkaAvroProducerDemo {

  public static void publishV1(Map<String, Object> props)
      throws ExecutionException, InterruptedException {
    Producer<Long, User> producer = new KafkaProducer<>(props);
    String topic = "my-topic";
    User user = User.newBuilder()
        .setName("Charlie")
        .setFavoriteColor("blue")
        .setFavoriteNumber(null)
        .build();
    ProducerRecord<Long, User> record = new ProducerRecord<>(topic, Math.round(Math.random() * 100), user);
    MyCallback callback = new MyCallback();
    Future future = producer.send(record, callback);
    RecordMetadata result = (RecordMetadata) future.get();
    long offset = result.offset();
    System.out.println(result.partition());
    System.out.println(offset);
    producer.close();
  }
  public static void publishV2(Map<String, Object> props)
      throws ExecutionException, InterruptedException {
    Producer<Long, UserV2> producer = new KafkaProducer<>(props);
    String topic = "my-topic";
    UserV2 user = UserV2.newBuilder()
        .setName("Charlie")
        .setFavoriteColor("blue")
        .setFavoriteNumber(null)
        .build();
    ProducerRecord<Long, UserV2> record = new ProducerRecord<>(topic, Math.round(Math.random() * 100), user);
    MyCallback callback = new MyCallback();
    Future future = producer.send(record, callback);
    RecordMetadata result = (RecordMetadata) future.get();
    long offset = result.offset();
    System.out.println(result.partition());
    System.out.println(offset);
    producer.close();
  }

  public static void publish() throws ExecutionException, InterruptedException {
    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    props.put(ProducerConfig.RETRIES_CONFIG, 1);
    props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true); // Must set retries to non-zero when using the idempotent producer.
    props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
    props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
    props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
    props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "lz4");
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
    props.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://127.0.0.1:8085");
    publishV1(props);
    publishV2(props);
  }

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    publish();
  }

}
