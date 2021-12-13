# Avro and Kafka

## http://packages.confluent.io/maven/

io.confluent.kafka.serializers.KafkaAvroSerializer for production needs license.

## io.confluent.kafka.serializers.AbstractKafkaAvroDeserializer.getReaderSchema

```
Caused by: org.apache.kafka.common.errors.SerializationException: Could not find class example.avro.User specified in writer's schema whilst finding reader's schema for a SpecificRecord.
	at io.confluent.kafka.serializers.AbstractKafkaAvroDeserializer.getSpecificReaderSchema(AbstractKafkaAvroDeserializer.java:265)
	at io.confluent.kafka.serializers.AbstractKafkaAvroDeserializer.getReaderSchema(AbstractKafkaAvroDeserializer.java:247)
	at io.confluent.kafka.serializers.AbstractKafkaAvroDeserializer.getDatumReader(AbstractKafkaAvroDeserializer.java:194)
	at io.confluent.kafka.serializers.AbstractKafkaAvroDeserializer$DeserializationContext.read(AbstractKafkaAvroDeserializer.java:375)
	at io.confluent.kafka.serializers.AbstractKafkaAvroDeserializer.deserialize(AbstractKafkaAvroDeserializer.java:112)
	at io.confluent.kafka.serializers.AbstractKafkaAvroDeserializer.deserialize(AbstractKafkaAvroDeserializer.java:86)
	at io.confluent.kafka.serializers.KafkaAvroDeserializer.deserialize(KafkaAvroDeserializer.java:55)
	at org.apache.kafka.common.serialization.Deserializer.deserialize(Deserializer.java:60)
	at org.apache.kafka.clients.consumer.internals.Fetcher.parseRecord(Fetcher.java:1420)
	... 10 more
```

```

  private Schema getReaderSchema(Schema writerSchema, Schema readerSchema) {
    if (readerSchema != null) {
      return readerSchema;
    }
    readerSchema = readerSchemaCache.get(writerSchema.getFullName());
    if (readerSchema != null) {
      return readerSchema;
    }
    boolean writerSchemaIsPrimitive =
        AvroSchemaUtils.getPrimitiveSchemas().values().contains(writerSchema);
    if (writerSchemaIsPrimitive) {
      readerSchema = writerSchema;
    } else if (useSchemaReflection) {
      readerSchema = getReflectionReaderSchema(writerSchema);
      readerSchemaCache.put(writerSchema.getFullName(), readerSchema);
    } else if (useSpecificAvroReader) {
      readerSchema = getSpecificReaderSchema(writerSchema);
      readerSchemaCache.put(writerSchema.getFullName(), readerSchema);
    } else {
      readerSchema = writerSchema;
    }
    return readerSchema;
  }
```

Conclusion: The Kafka consumer detects the target class name from the message schema, not from the consumer definition.

The Kafka producer can publish User and UserV2 successfully. This means the schema upgrade is compatible.
But the Kafka consumer can't read User data as UserV2. It's not friendly.

## Schema Registry API Reference

http://127.0.0.1:8085/schemas/ids/1
```
{"schema":"{\"type\":\"record\",\"name\":\"User\",\"namespace\":\"example.avro\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"favoriteNumber\",\"type\":[\"int\",\"null\"]},{\"name\":\"favoriteColor\",\"type\":[\"string\",\"null\"]}]}"}
```

http://127.0.0.1:8085/schemas/ids/2
```
{"schema":"{\"type\":\"record\",\"name\":\"UserV2\",\"namespace\":\"example.avro\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"favoriteNumber\",\"type\":[\"int\",\"null\"]},{\"name\":\"favoriteColor\",\"type\":[\"string\",\"null\"]},{\"name\":\"favoriteMovie\",\"type\":[\"string\",\"null\"],\"default\":\"007\"}]}"}
```

## io.confluent.kafka.serializers.AbstractKafkaAvroDeserializer and 2 Schema

```
"main@1" prio=5 tid=0x1 nid=NA runnable
  java.lang.Thread.State: RUNNABLE
	  at io.confluent.kafka.serializers.AbstractKafkaAvroDeserializer$DeserializationContext.read(AbstractKafkaAvroDeserializer.java:375)
	  at io.confluent.kafka.serializers.AbstractKafkaAvroDeserializer.deserialize(AbstractKafkaAvroDeserializer.java:112)
	  at io.confluent.kafka.serializers.AbstractKafkaAvroDeserializer.deserialize(AbstractKafkaAvroDeserializer.java:86)
	  at io.confluent.kafka.serializers.KafkaAvroDeserializer.deserialize(KafkaAvroDeserializer.java:55)
	  at org.apache.kafka.common.serialization.Deserializer.deserialize(Deserializer.java:60)
	  at org.apache.kafka.clients.consumer.internals.Fetcher.parseRecord(Fetcher.java:1420)
	  at org.apache.kafka.clients.consumer.internals.Fetcher.access$3400(Fetcher.java:134)
	  at org.apache.kafka.clients.consumer.internals.Fetcher$CompletedFetch.fetchRecords(Fetcher.java:1652)
	  at org.apache.kafka.clients.consumer.internals.Fetcher$CompletedFetch.access$1800(Fetcher.java:1488)
	  at org.apache.kafka.clients.consumer.internals.Fetcher.fetchRecords(Fetcher.java:721)
	  at org.apache.kafka.clients.consumer.internals.Fetcher.fetchedRecords(Fetcher.java:672)
	  at org.apache.kafka.clients.consumer.KafkaConsumer.pollForFetches(KafkaConsumer.java:1304)
	  at org.apache.kafka.clients.consumer.KafkaConsumer.poll(KafkaConsumer.java:1238)
	  at org.apache.kafka.clients.consumer.KafkaConsumer.poll(KafkaConsumer.java:1211)
	  at huaminglin.demo.kafka.KafkaAvroConsumerDemo.consume(KafkaAvroConsumerDemo.java:73)
	  at huaminglin.demo.kafka.KafkaAvroConsumerDemo.main(KafkaAvroConsumerDemo.java:87)

```

## io.confluent.kafka.serializers.AbstractKafkaAvroDeserializer.deserialize

```

  /**
   * Deserializes the payload without including schema information for primitive types, maps, and
   * arrays. Just the resulting deserialized object is returned.
   *
   * <p>This behavior is the norm for Decoders/Deserializers.
   *
   * @param payload serialized data
   * @return the deserialized object
   */
  protected Object deserialize(byte[] payload) throws SerializationException {
    return deserialize(null, null, payload, null);
  }

  /**
   * Just like single-parameter version but accepts an Avro schema to use for reading
   *
   * @param payload      serialized data
   * @param readerSchema schema to use for Avro read (optional, enables Avro projection)
   * @return the deserialized object
   */
  protected Object deserialize(byte[] payload, Schema readerSchema) throws SerializationException {
    return deserialize(null, null, payload, readerSchema);
  }
```

Deserializes the payload without including schema information for primitive types, maps, and arrays.

readerSchema: schema to use for Avro read (optional, enables Avro projection)

## Call KafkaAvroDeserializer: Object deserialize(String s, byte[] bytes, Schema readerSchema) to provide the second schema

In this way, we can pare old data into the new schema.
It's weird we can't provide the target schema for "props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);"

Actually we can't find any call to KafkaAvroDeserializer: Object deserialize(String s, byte[] bytes, Schema readerSchema) in the Kafka source code.
