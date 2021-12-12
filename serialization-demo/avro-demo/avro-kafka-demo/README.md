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
