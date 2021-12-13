package huaminglin.demo.kafka;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.apache.avro.Schema;
import org.apache.avro.Schema.Parser;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericData.Record;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.LongSerializer;

public final class KafkaAvroProducerDemo {

  public static void publishV1(Map<String, Object> props)
      throws ExecutionException, InterruptedException, IOException {

    Schema oldSchema;
    {
      InputStream inputStream = KafkaAvroProducerDemo.class.getClassLoader()
          .getResourceAsStream("avro/user.avsc");
      oldSchema = new Parser().parse(inputStream);
    }
    GenericRecord genericRecord = new Record(oldSchema);
    genericRecord.put("name", "Charlie");
    genericRecord.put("favoriteColor", "blue");
    GenericData.EnumSymbol symbol = new GenericData.EnumSymbol(oldSchema, "male");
    genericRecord.put("sex", symbol);

    Producer<Long, GenericRecord> producer = new KafkaProducer<>(props);
    String topic = "my-topic";
    ProducerRecord<Long, GenericRecord> record = new ProducerRecord<>(topic, Math.round(Math.random() * 100), genericRecord);
    MyCallback callback = new MyCallback();
    Future future = producer.send(record, callback);
    RecordMetadata result = (RecordMetadata) future.get();
    long offset = result.offset();
    System.out.println(result.partition());
    System.out.println(offset);
    producer.close();
  }

  public static void publishV2(Map<String, Object> props)
      throws ExecutionException, InterruptedException, IOException {
    Schema newSchema;
    {
      InputStream inputStream = KafkaAvroProducerDemo.class.getClassLoader()
          .getResourceAsStream("avro/v2/userV2.avsc");
      newSchema = new Parser().parse(inputStream);
    }
    GenericRecord genericRecord = new Record(newSchema);
    genericRecord.put("name", "Charlie");
    genericRecord.put("favoriteColor", "blue");
    GenericData.EnumSymbol symbol = new GenericData.EnumSymbol(newSchema, "male");
    genericRecord.put("sex", symbol);

    Producer<Long, GenericRecord> producer = new KafkaProducer<>(props);
    String topic = "my-topic";
    ProducerRecord<Long, GenericRecord> record = new ProducerRecord<>(topic, Math.round(Math.random() * 100), genericRecord);
    MyCallback callback = new MyCallback();
    Future future = producer.send(record, callback);
    RecordMetadata result = (RecordMetadata) future.get();
    long offset = result.offset();
    System.out.println(result.partition());
    System.out.println(offset);
    producer.close();
  }

  public static void publish() throws ExecutionException, InterruptedException, IOException {
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

  public static void main(String[] args)
      throws ExecutionException, InterruptedException, IOException {
    publish();
  }

}
